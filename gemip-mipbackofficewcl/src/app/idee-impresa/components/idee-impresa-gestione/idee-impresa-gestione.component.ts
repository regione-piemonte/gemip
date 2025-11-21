/**
 * @license
 *
 * Copyright © 2025 Regione Piemonte
 *
 * Licensed under the EUPL, Version 1.2 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * https://interoperable-europe.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 * or
 * https://opensource.org/license/EUPL-1.2
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 */

import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BagService } from '@core/services/bag.service';
import { UrlRouter } from '@main/const/url-router.model';

//-Models
import { FiltroRicercaIdeaImpresa } from '@shared/model/filtri-ricerca.model';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { IdeaDiImpresaRicerca } from '@core/models/ideaDiImpresaRicerca';
import { Sort } from '@angular/material/sort';
import { IconsSettings } from '@shared/utils/icons-settings';
import { Location } from '@angular/common';

@Component({
  selector: 'app-idee-impresa-gestione',
  templateUrl: './idee-impresa-gestione.component.html',
  styleUrls: ['./idee-impresa-gestione.component.scss']
})
export class IdeeImpresaGestioneComponent implements OnInit, OnDestroy {

  //--Gestione tabella
  visibleTable = false;
  rowData: IdeeImpSimple[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  pageIndex: number = 0
  pageSize: number = 5
  totRis: number = 0;
  sort!: Sort
  ideeImpList: IdeaDiImpresaRicerca[] = []
  filtri!: FiltroRicercaIdeaImpresa
  constructor(
    private router: Router,
    private bag: BagService,
    private ideaImpService: IdeaImpresaService,
    private bagService: BagService,
    private location: Location,
  ) { }

  ngOnDestroy(): void {
    this.ideaImpService.updatePagination(5, 0);
    this.ideaImpService.updateSort(undefined);
  }

  ngOnInit(): void {
    this.bagService.titolo = "Ricerca idee impresa"
    this.bagService.icona = IconsSettings.IDEE_IMPRESA_ICON

    this.ideaImpService._pageIndex.subscribe(
      ris => this.pageIndex = ris
    )
    this.ideaImpService._pageSize.subscribe(
      ris => this.pageSize = ris
    )
    this.ideaImpService._filtriRicerca.subscribe(
      ris => this.filtri = ris
    )
    this.ideaImpService._sort.subscribe(
      ris => this.sort = ris
    )
    this.createTable();
  }

  onPageIndexSizeChange(indexSize: number[]) {
    this.ideaImpService.updatePagination(indexSize[1], indexSize[0])
    this.onSearch(this.filtri)
  }

  sortChange(sort: Sort) {
    this.ideaImpService.updateSort(sort);
    this.onSearch(this.filtri);
  }

  onSearch(filtri: FiltroRicercaIdeaImpresa) {
    if (sessionStorage.getItem("soggettoAttuatore") && !filtri.idSoggettoAttuatore) {
      this.rowData = [];

    } else {
      this.ideaImpService.getIdeaImpresaByFilter(filtri, this.pageIndex, this.pageSize, this.sort).subscribe(r => {
        this.ideeImpList = r.ideeDiImpresa!

        this.rowData = this.ideeImpList.map(r => ({
          id: r.ideaDiImpresa?.id,
          titolo: r.ideaDiImpresa?.titolo,
          cittadino: r.citadino?.nome + " " + r.citadino?.cognome, // Cittadino
          stato: r.ideaDiImpresa?.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa,
          soggetto: r.tutor?.soggettoAttuatore?.denominazione,
          associazione: r.ideaDiImpresa?.dataSceltaTutor ? new Date(r.ideaDiImpresa?.dataSceltaTutor!).toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric' }) : "",
          area: r.areaTerritoriale?.descrizioneAreaTerritoriale,
          icons: this.createIcons(r) //
        }));
        this.totRis = r.risultatiTotali!
        this.tableSettings.length = this.totRis;
        this.tableSettings.pageIndex = this.pageIndex!;
        this.tableSettings.pageSize = this.pageSize!;
        this.tableSettings.defaultSortDirection = 'desc';

      })
      this.visibleTable = true;
    }
  }

  //############# Handle Icons #################
  createIcons(ideaImp: IdeaDiImpresaRicerca): InfoColumnActionSettingModel[] {
    let icons: InfoColumnActionSettingModel[] = [];
    if (this.bag.ruolo.codiceRuolo !== "GEMIP_REGIONALE_BASE") {
      icons.push(InfoColumnActionSettingModel.getEditIcon(() => {
        this.ideaImpService.ideeImp = ideaImp;
        this.router.navigateByUrl(`${UrlRouter.urlIdeeImpresa}/form/${ideaImp.ideaDiImpresa?.id}`)
      }
      ));
    } else {
      icons.push(InfoColumnActionSettingModel.getEditIcon(() => {
        this.ideaImpService.ideeImp = ideaImp;
        this.ideaImpService.canRegBaseModify = false;
        this.router.navigateByUrl(`${UrlRouter.urlIdeeImpresa}/form/${ideaImp.ideaDiImpresa?.id}`)
      }));
    }
    return icons;
  }

  createTable() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;
    this.tableSettings.length = this.totRis;
    this.createColumnsTable();
  }

  createColumnsTable() {
    /**Columns */

    const idColumn = new ColumnSettingsModel('id', 'ID', false, 'simple');
    const titoloColumn = new ColumnSettingsModel('titolo', 'Titolo idea d\'impresa', false, 'simple');
    const utenteColumn = new ColumnSettingsModel('cittadino', 'Cittadino', true, 'simple');
    const statoColumn = new ColumnSettingsModel('stato', 'Stato', true, 'simple');
    const operatoreColumn = new ColumnSettingsModel('soggetto', 'Soggetto attuatore', true, 'simple');
    const dataAssociazione = new ColumnSettingsModel('associazione', 'Data associazione soggetto attuatore', false, 'simple');
    const areaTerritorialeColumn = new ColumnSettingsModel('area', 'Area territoriale incontro pre-accoglienza', true, 'simple');
    const customActionColumn = new ColumnSettingsModel('icone', 'Azioni', true, 'customAction');

    this.columnList.push(
      ...[
        idColumn,
        titoloColumn,
        utenteColumn,
        statoColumn,
        operatoreColumn,
        dataAssociazione,
        areaTerritorialeColumn,
        customActionColumn,
      ]
    );
  }

  tornaIndietro(): void {
    this.location.back();
  }
}

export interface IdeeImpSimple {
  id?: number;
  titolo?: string;
  cittadino?: string;
  stato?: string;
  soggetto?: string;
  dataAssociazione?: Date;
  area?: string;
  soggettoAttuattore?: string;
  icons?: InfoColumnActionSettingModel[];
}
