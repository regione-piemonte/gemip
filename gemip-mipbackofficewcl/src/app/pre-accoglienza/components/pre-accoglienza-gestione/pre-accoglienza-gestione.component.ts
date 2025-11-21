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

import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Sort } from '@angular/material/sort';
import { Router } from '@angular/router';
import { BagService } from '@core/services/bag.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';

import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';

//-Models
import { FiltroRicercaPreAccoglienza } from '@shared/model/filtri-ricerca.model';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { IconsSettings } from '@shared/utils/icons-settings';


//-Calendar
import { EventColor } from 'calendar-utils';

const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

@Component({
  selector: 'app-pre-accoglienza-gestione',
  templateUrl: './pre-accoglienza-gestione.component.html',
  styleUrls: ['./pre-accoglienza-gestione.component.scss'],
})
export class PreAccoglienzaGestioneComponent implements OnInit, OnDestroy {

  //--Gestione tabella
  visibleTable = false;

  rowData: RowPreAccoglienza[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  pageIndex?: number;
  pageSize?: number;
  filtri?: FiltroRicercaPreAccoglienza;
  sort?: Sort;
  totRis: number = 0;

  constructor(
    private router: Router,
    private bag: BagService,
    private incontriPreaccoglienzaService: PreAccoglienzaService,
    private incontriPreaccoglienzaBag: PreAccoglienzaBagService,
    private dialog: MatDialog,
    private bagService: BagService,
    private location: Location,
    private ideaService: IdeaImpresaService,
  ) { }

  ngOnDestroy(): void {
    this.incontriPreaccoglienzaBag.updatePagination(5, 0);
  }

  ngOnInit(): void {
    this.bagService.titolo = "Ricerca incontri pre-accoglienza"
    this.bagService.icona = IconsSettings.PRE_ACCOGLIENZA_ICON

    this.incontriPreaccoglienzaBag._pageIndex.subscribe(
      ris => this.pageIndex = ris
    )
    this.incontriPreaccoglienzaBag._pageSize.subscribe(
      ris => this.pageSize = ris
    )
    this.incontriPreaccoglienzaBag._filtriRicerca.subscribe(
      ris => this.filtri = ris
    )
    this.incontriPreaccoglienzaBag._sort.subscribe(
      ris => this.sort = ris
    )
    this.createTable();
  }

  onPageIndexSizeChange(indexSize: number[]) {
    this.incontriPreaccoglienzaBag.updatePagination(indexSize[1], indexSize[0])
    this.onSearch(this.filtri!)
  }

  sortChange(sort: Sort) {
    this.incontriPreaccoglienzaBag.updateSort(sort);
    this.onSearch(this.filtri!)
  }

  onSearch(filtri: FiltroRicercaPreAccoglienza) {

    this.incontriPreaccoglienzaService.ricercaIncontri(filtri, this.pageIndex, this.pageSize, this.sort).subscribe({
      next: ris => {
        this.incontriPreaccoglienzaBag.updateResultRicerca(ris);
        this.rowData = ris.incontriPreaccoglienza!.map(incontro => {
          return {
            id: incontro.id!,
            titolo: incontro.denominazione!,
            data: new Date(incontro.dataIncontro!).toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric' }),
            oraInizio: new Date(incontro.dataIncontro!).toLocaleString('it-IT', { hour: '2-digit', minute: '2-digit' }),
            sede: incontro.luogoIncontro?.denominazione ? incontro.luogoIncontro.denominazione! : "Da remoto",
            areaTerritoriale: incontro.luogoIncontro?.areaTerritoriale?.descrizioneAreaTerritoriale,
            capienza: incontro.numPartecipantiIscritti! + "/" + incontro.numMaxPartecipanti!,
            icons: this.createIcons(),
            numPartecipantiIscritti: incontro.numPartecipantiIscritti!
          }
        })
        this.totRis = ris.totaleIncontri!;
        this.tableSettings.length = this.totRis;
        this.tableSettings.pageIndex = this.pageIndex!;
        this.tableSettings.pageSize = this.pageSize!;
        this.visibleTable = true;
      }
    })
  }

  handleDelete(id: number) {
    this.incontriPreaccoglienzaService.getIdeeDiImpresaAssociate(id).subscribe({
      next: ris => {
        if (ris.ideaDiImpresa?.length! > 0) {
          this.dialog.open(DialogConfermaComponent, {
            data: new DialogSettings(
              "Attenzione",
              ['Non è possibile eliminare l\'incontro', ' Esistono delle idee di impresa associate'],
              "card-body--danger"
            ),
            disableClose: true
          });

        } else {
          let buttons: DialogButton[] = [
            new DialogButton('Annulla', 'btn btn--outline-primary', undefined, "annulla"),
            new DialogButton('Conferma', 'btn btn--danger', () => {
              this.incontriPreaccoglienzaService.deleteIncontroPreaccoglienza(id).subscribe(
                () => {
                  this.onSearch(this.filtri!);
                  this.dialog.open(DialogConfermaComponent, {
                    data: new DialogSettings(
                      "",
                      ['Incontro eliminato correttamente'],
                      "card-body--success"
                    ),
                    disableClose: true
                  });

                }
              )
            })
          ]

          this.dialog.open(DialogConfermaComponent, {
            data: new DialogSettings(
              "Attenzione",
              ['Si è sicuri di voler eliminare l\'incontro selezionato?'],
              "card-body--warning", "", buttons
            ),
            disableClose: true
          });

        }
      }
    })
  }

  //############# Handle Icons #################
  createIcons(): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];
    if (this.bag.isRuoloAdmin()) {
      icons.push(InfoColumnActionSettingModel.getEditIcon(
        (incontro) => {
          this.incontriPreaccoglienzaBag.idIncontroPreaccoglienza = incontro.id;
          this.incontriPreaccoglienzaBag.numeroIdeeAssociate = incontro.numPartecipantiIscritti
          this.router.navigateByUrl('/pre-accoglienza/modifica')
        }));
      icons.push(InfoColumnActionSettingModel.getDeleteIcon((row) => this.handleDelete(row.id)));
    } else
      icons.push(InfoColumnActionSettingModel.getViewIcon(
        (incontro) => {
          this.incontriPreaccoglienzaBag.idIncontroPreaccoglienza = incontro.id;
          this.incontriPreaccoglienzaService.canModify = false;
          this.router.navigateByUrl('/pre-accoglienza/modifica')
        }));
    return icons;
  }

  createTable() {
    this.tableSettings.title = 'Incontri Preaccoglienza ';
    this.tableSettings.enableExport = false;
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = true;
    this.tableSettings.defaultSort = "data"
    this.createColumnsTable();
  }

  createColumnsTable() {
    /**Columns */
    const idColumn = new ColumnSettingsModel('id', 'identificativo', false, 'simple');
    const titoloColumn = new ColumnSettingsModel('titolo', 'Descrizione', true, 'simple')
    const dataColumn = new ColumnSettingsModel('data', 'Data', false, 'simple');
    const oraInizioColumn = new ColumnSettingsModel('oraInizio', 'Ora inizio', true, 'simple');
    const sedeColumn = new ColumnSettingsModel('sede', 'Sede', false, 'simple');
    const capienzaColumn = new ColumnSettingsModel('capienza', 'Idee d\'impresa / Capienza	', true, 'simple');
    const customActionColumn = new ColumnSettingsModel('icone', 'Azioni', true, 'customAction');
    const areaTerritoriale = new ColumnSettingsModel('areaTerritoriale', 'Area Territoriale', true, 'simple');
    this.columnList.push(
      ...[
        idColumn,
        titoloColumn,
        dataColumn,
        oraInizioColumn,
        areaTerritoriale,
        sedeColumn,
        capienzaColumn,
        customActionColumn,
      ]
    );
  }

  tornaIndietro(): void {
    this.location.back();
  }
}

export interface RowPreAccoglienza {
  id: number;
  titolo: string;
  data: string;
  oraInizio: string;
  areaTerritoriale?: string;
  sede: string;
  capienza: string;
  icons: InfoColumnActionSettingModel[];
  numPartecipantiIscritti: number;
  extendedContent?: string[]
}
