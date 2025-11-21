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

import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Documento } from '@core/models/documento';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { Ruolo } from '@core/models/ruolo';
import { TipoDocumento } from '@core/models/tipoDocumento';
import { RowDocumenti } from '@documenti/components/documenti-gestione/documenti-gestione.component';
import { DocumentiService } from '@documenti/services/documenti.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { FiltroRicercaDocumento } from '@shared/model/filtri-ricerca.model';
import { TableSettingsModel, ColumnSettingsModel, InfoColumnActionSettingModel } from '@shared/model/table-setting.model';
import * as FileSaver from 'file-saver';

@Component({
  selector: 'app-file-tab',
  templateUrl: './file-tab.component.html',
  styleUrls: ['./file-tab.component.scss']
})
export class FileTabComponent implements OnInit {

  @Input() isStampa = false
  estensioniPermesse = ["doc", "docx", "xls", "xlsx", "ppt", "pptx", "pps", "ppsx", "pdf", "zip", "odb", "odg", "odp", "ods", "odf", "odt", "otg", "otp", "ots", "ott", "dot", "dotx", "gif", "jpg", "jpeg", "png", "svg", "mp4"]

  documenti: Documento[] = [];
  tipiDocumento: TipoDocumento[] = [];
  file?: File
  idOperatore!: number
  codUser: string = ""
  //--Gestione tabella
  visibleTable = false;
  tipiDocumenti: TipoDocumento[] = []
  ruolo!: Ruolo
  disableAddFile: boolean = false;
  disableSaveButton: boolean = false;
  MAX_SIZE = 10 * 1024 * 1024; // 10 MB in byte

  rowData: RowDocumenti[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  ideaDiImpresa!: IdeaDiImpresa

  form = this.fb.group({
    titolo: ['', [Validators.required, Validators.maxLength(250)]],
    gruppo: ['', [Validators.required]],
  });

  constructor(
    private fb: FormBuilder,
    private documentiService: DocumentiService,
    private ideaDiImpresaService: IdeaImpresaService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!)
    this.idOperatore = JSON.parse(sessionStorage.getItem("_userInfo")!).idOperatore;
    this.codUser = JSON.parse(sessionStorage.getItem("_userInfo")!).codFisc;
    this.ideaDiImpresa = this.ideaDiImpresaService.ideeImp?.ideaDiImpresa!
    this.createTable();
    let filtri: FiltroRicercaDocumento = {
      idIdeaDiImpresa: this.ideaDiImpresa.id!
    }

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
        this.visibleTable = true
      }
    })
    this.documentiService.getTipologieDocumenti().subscribe({
      next: ris => {
        this.tipiDocumenti = ris;
      }
    })
    if (!this.ideaDiImpresaService.canRegBaseModify) {
      this.form.controls.titolo.disable();
      this.form.controls.gruppo.disable();
      this.disableAddFile = !this.disableAddFile;
      this.disableSaveButton = !this.disableSaveButton;
    }
  }

  createDocumento(): Documento {

    return {
      idOperatoreInserimento: this.idOperatore,
      idIdeaDiImpresa: this.ideaDiImpresa.id,
      nomeDocumento: this.file?.name,
      descrizioneDocumento: this.form.controls["titolo"].value!,
      codeTipoDocumento: this.form.controls["gruppo"].value!,
      documento: this.file,
      codUserInserim: this.codUser
    }
  }

  createIcons(): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];

    icons.push(InfoColumnActionSettingModel.getFileDownloadIcon((row) => this.downloadFile(row)));
    if (this.ruolo?.codiceRuolo != "GEMIP_REGIONALE_BASE")
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
  createRowDocument(doc: Documento): RowDocumenti {
    return {
      id: doc.idDocumento!,
      nomefile: doc.nomeDocumento!,
      descrizione: doc.descrizioneDocumento!,
      icons: this.createIcons()
    }
  }

  addFile(event: any) {

    const originalFile = event.target.files[0];
    const cleanedName = this.cleanFilename(originalFile.name);
    
    this.file = new File([originalFile], cleanedName, {
      type: originalFile.type,
      lastModified: originalFile.lastModified
    });

  }

  cleanFilename(name: string): string {
  return name
    .normalize('NFKD')
    .replace(/[’‘]/g, "'")
    .replace(/[“”]/g, '"')
    .replace(/[…]/g, '...')
    .replace(/[–—]/g, '-')
    .replace(/[^\w\d\-_.()' ]+/g, '');
  }

  onSalva() {
    let chk: string = ""
    if (this.file) {
      let checkExtension = this.file?.name.split(".");
      chk = checkExtension![checkExtension?.length! - 1]
    }
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      this.openDialog("Attenzione", ["Compilare tutti i campi obbligatori"], "card-body--danger");
      return
    } else if (!this.file) {
      this.openDialog("Attenzione", ["Selezionare un documento da caricare"], "card-body--danger");
      return
    } else if (!this.estensioniPermesse.includes(chk)) {
      this.openDialog("Attenzione", ["Estensione del documento non corretta", "Tipi di file validi: doc, docx, xls, xlsx, ppt, pptx, pps, ppsx, pdf, zip, odb, odg, odp, ods, odf, odt, otg, otp, ots, ott, dot, dotx, gif, jpg, jpeg, png, svg, mp4"], "card-body--danger");
      return;
    } else if (!this.form.controls["titolo"].value?.trim()) {
      this.form.controls["titolo"].setValue("");
      this.form.markAllAsTouched();
      this.openDialog("Attenzione", ["Compilare tutti i campi obbligatori"], "card-body--danger");
      return;
    }else if(this.file.size > this.MAX_SIZE){
      this.openDialog("Attenzione", ["Dimensione del file troppo grande","Dimensione massima: 10MB"], "card-body--danger");
      return;
    }
    this.documentiService.uplooadFile(this.createDocumento()).subscribe(
      (ris) => {
        this.confermaSalvataggio();
        this.form.controls["gruppo"].setValue(null)
        this.form.controls["titolo"].setValue("")
        this.form.markAsUntouched();
        this.form.controls["gruppo"].setErrors(null)
        this.form.controls["titolo"].setErrors(null)
        this.file = undefined
        this.rowData.push(this.createRowDocument(ris));
        this.rowData = [...this.rowData]
      }
    );
  }

  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose: true
    })
  }

  confermaSalvataggio() {
    this.openDialog("Avviso", ["I dati sono stati salvati correttamente"], "card-body--success")
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
    const customActionColumn = new ColumnSettingsModel('icons', '', true, 'customAction');

    this.columnList.push(
      ...[
        descrColumn,
        titoloColumn,
      ]
    );
    if (!this.isStampa) {
      this.columnList.push(customActionColumn);
    }
  }

}
