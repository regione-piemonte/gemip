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
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { BagService } from '@core/services/bag.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { UtentiService } from '@utenti/services/utenti.service';

//-Models
import { Location } from '@angular/common';
import { Sort } from '@angular/material/sort';
import { AnagraficaCittadino } from '@core/models/anagraficaCittadino';
import { FiltroRicercaUtentiCittadino } from '@shared/model/filtri-ricerca.model';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { IconsSettings } from '@shared/utils/icons-settings';


@Component({
  selector: 'app-cittadino-gestione',
  templateUrl: './cittadino-gestione.component.html',
  styleUrls: ['./cittadino-gestione.component.scss']
})
export class CittadinoGestioneComponent implements OnInit, OnDestroy {
  panelOpenState = true;
  form = this.fb.group({

  });

  visibleTable = false;
  rowData: any[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  pageSize: number = 5;
  pageIndex: number = 0;
  filtri!: FiltroRicercaUtentiCittadino;
  totRis: number = 0;
  sort?: Sort
  constructor(
    private fb: FormBuilder,
    private bag: BagService,
    private router: Router,
    private utentiService: UtentiService,
    private ideaDiImpresaService: IdeaImpresaService,
    private bagService: BagService,
    private location: Location
  ) { }
  ngOnDestroy(): void {
    this.ideaDiImpresaService.updatePagination(5, 0);
    this.utentiService.updateFiltri({});
  }

  ngOnInit(): void {
    this.bagService.titolo = "Ricerca cittadini"
    this.bagService.icona = IconsSettings.UTENTI_ICON

    this.ideaDiImpresaService._pageIndex.subscribe({
      next: ris => this.pageIndex = ris
    })
    this.ideaDiImpresaService._pageSize.subscribe({
      next: ris => {
        this.pageSize = ris
        this.tableSettings.pageSize = ris;
      }
    })
    this.utentiService._filtriCittadino.subscribe({
      next: ris => this.filtri = ris
    })
    this.ideaDiImpresaService._sort.subscribe(
      ris => this.sort = ris
    )
    this.createTable();
  }

  openPanel() {
    this.panelOpenState = true
  }
  closePanel() {
    this.panelOpenState = false
  }

  clear() {
    this.form.reset();
  }

  pageIndexSizeChange(indexSize: number[]) {
    this.ideaDiImpresaService.updatePagination(indexSize[1], indexSize[0]);
    this.onSearch(this.filtri);
  }

  sortChange(sort: Sort) {
    this.ideaDiImpresaService.updateSort(sort);
    this.onSearch(this.filtri)
  }
  anagraficaCittaList: AnagraficaCittadino[] = [];
  onSearch(filtri: FiltroRicercaUtentiCittadino) {
    this.utentiService.ricercaUtentiCittadino(filtri, this.pageIndex, this.pageSize, this.sort).subscribe({
      next: ris => {
        this.anagraficaCittaList = ris.anagrafiche!;
        this.rowData = ris.anagrafiche!.map(anagrafica => {
          return {
            idCittadino: anagrafica.cittadino!.idCittadino!,
            codiceFiscale: anagrafica.cittadino!.codiceFiscale!,
            cognome: anagrafica.cittadino!.cognome!,
            nome: anagrafica.cittadino!.nome!,
            email: anagrafica.recapitoEmail,
            telefono: anagrafica.recapitoTelefono,
            icons: this.createIcons(anagrafica.flgIdeaImpresa!),
          }
        });
        this.totRis = ris.totRis!;
        this.tableSettings.length = this.totRis;
        this.visibleTable = true;
      }
    });
  }

  //############# Handle Icons #################
  createIcons(flgIdeaImpresa: boolean): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];

    icons.push(this.getIdeeImpresaIcon((riga) => {
      this.ideaDiImpresaService.getIdeaImpresaByIdCittadino(riga.idCittadino).subscribe(
        response => {
          this.ideaDiImpresaService.ideeImp = response;
          this.router.navigateByUrl('/idee-impresa/form/' + this.ideaDiImpresaService.ideeImp.ideaDiImpresa?.id);
        }

      );
    },
      () => { return flgIdeaImpresa }
    ));

    icons.push(InfoColumnActionSettingModel.getEditIcon((riga) => {

      let trovato = this.anagraficaCittaList.find((ana) => {
        return ana.cittadino?.codiceFiscale == riga.codiceFiscale;
      });
      this.utentiService.anagraficaSelected = trovato!;

      this.router.navigateByUrl('utenti/cittadino/form/' + this.utentiService.anagraficaSelected.id!);

    }));
    return icons;
  }
  getIdeeImpresaIcon(action: (row: any) => void, viewCondiction: (row: any) => boolean) {
    return new InfoColumnActionSettingModel(
      "wb_incandescent",
      'icon',
      'idee impresa',
      action,
      undefined,
      viewCondiction,
      undefined,
      'idea di impresa'
    );
  }
  createTable() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;
    this.tableSettings.length = this.totRis;
    this.tableSettings.defaultSort = "codiceFiscale"
    this.createColumnsTable();
  }
  createColumnsTable() {
    /**Columns */
    const cfColumn = new ColumnSettingsModel('codiceFiscale', 'Codice fiscale', false, 'simple');
    const cognomeColumn = new ColumnSettingsModel('cognome', 'Cognome', false, 'simple');
    const nomeColumn = new ColumnSettingsModel('nome', 'Nome', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', false, 'simple');
    const telefonoColumn = new ColumnSettingsModel('telefono', 'Telefono', false, 'simple');
    const customActionColumn = new ColumnSettingsModel('icone', 'Azioni', true, 'customAction');

    this.columnList.push(
      ...[
        cfColumn,
        cognomeColumn,
        nomeColumn,
        emailColumn,
        telefonoColumn,
        customActionColumn,
      ]
    );
  }
  tornaIndietro(): void {
    this.location.back();
  }
}

