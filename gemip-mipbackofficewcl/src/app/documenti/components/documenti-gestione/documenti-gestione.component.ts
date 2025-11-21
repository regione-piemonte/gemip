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

import { BagService } from '@core/services/bag.service';
import { Component, OnInit } from '@angular/core';
import { Documento } from '@core/models/documento';
import { Ruolo } from '@core/models/ruolo';
import { TipoDocumento } from '@core/models/tipoDocumento';
import { DocumentiService } from '@documenti/services/documenti.service';
import { FiltroRicercaDocumento } from '@shared/model/filtri-ricerca.model';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import * as FileSaver from 'file-saver';
import { IconsSettings } from '@shared/utils/icons-settings';
import { MatDialog } from '@angular/material/dialog';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { Location } from '@angular/common';

@Component({
  selector: 'app-documenti-gestione',
  templateUrl: './documenti-gestione.component.html',
  styleUrls: ['./documenti-gestione.component.scss']
})
export class DocumentiGestioneComponent implements OnInit {
  documenti: Documento[] = [];
  tipiDocumento: TipoDocumento[] = [];

  //--Gestione tabella
  visibleTable = false;

  rowData: RowDocumenti[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  ruolo?: Ruolo

  constructor(
    private documentiService: DocumentiService,
    private bagService: BagService,
    private dialog: MatDialog,
    private location: Location
  ) { }

  ngOnInit(): void {

    this.bagService.titolo = "Ricerca documenti"
    this.bagService.icona = IconsSettings.DOCUMENTAZIONE_ICON

    this.ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!)
    this.documentiService.getTipologieDocumenti().subscribe(
      ris => { this.tipiDocumento = ris }
    )
    this.createTable();
  }

  onSearch(filtri: FiltroRicercaDocumento) {
    this.documentiService.getDocumenti(filtri).subscribe({
      next: ris => {
        this.documenti = ris;
        this.rowData = this.documenti.map(doc => {
          return {
            id: doc.idDocumento!,
            nomefile: doc.nomeDocumento!,
            descrizione: doc.descrizioneDocumento!,
            icons: this.createIcons(),
          }
        })
        this.visibleTable = true;
      }
    })
  }

  //############# Handle Icons #################
  createIcons(): InfoColumnActionSettingModel[] {
    let icons: InfoColumnActionSettingModel[] = [];
    icons.push(InfoColumnActionSettingModel.getFileDownloadIcon((row) => this.downloadFile(row)));
    if (this.ruolo?.codiceRuolo == "GEMIP_AFFIDATARIO_ALL" || this.ruolo?.codiceRuolo == "GEMIP_REGIONALE_ALL")
      icons.push(InfoColumnActionSettingModel.getDeleteIcon((row) => this.handleDelete(row.id)))
    return icons;
  }

  handleDelete(id: number) {
    let buttons: DialogButton[] = [
      new DialogButton('Annulla', 'btn btn--outline-primary', undefined, "annulla"),
      new DialogButton('Conferma', 'btn btn--danger', () =>
        this.documentiService.deleteDocumento(id).subscribe({
          next: () => {
            this.rowData.splice(this.rowData.findIndex(row => row.id == id), 1);
            this.rowData = [...this.rowData]
            this.dialog.open(DialogConfermaComponent, {
              data: new DialogSettings(
                "",
                ['Documento eliminato correttamente'],
                "card-body--success"
              ),
              disableClose: true
            });
          }
        })
      )
    ]

    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(
        "Attenzione",
        ['Si è sicuri di voler eliminare il documento selezionato?'],
        "card-body--warning", "", buttons
      ),
      disableClose: true
    });
  }

  downloadFile(doc: RowDocumenti) {

    this.documentiService.downloadDocumento(doc.id).subscribe({
      next: ris => {
        FileSaver.saveAs(ris, doc.nomefile);
      },
      error(err) {
        console.log(err)
      },
    })
  }

  createTable() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;

    this.createColumnsTable();
  }

  createColumnsTable() {
    /**Columns */

    const descrColumn = new ColumnSettingsModel('descrizione', 'Titolo', false, 'simple');
    const titoloColumn = new ColumnSettingsModel('nomefile', 'Nome File', false, 'simple');
    const customActionColumn = new ColumnSettingsModel('icons', 'Azioni', true, 'customAction');

    this.columnList.push(
      ...[
        descrColumn,
        titoloColumn,
        customActionColumn,
      ]
    );
  }
  tornaIndietro(): void {
    this.location.back();
  }
}
export interface RowDocumenti {
  id: number;
  descrizione: string;
  nomefile: string;
  icons: InfoColumnActionSettingModel[];
}
