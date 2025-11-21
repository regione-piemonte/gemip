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
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Documento } from '@core/models/documento';
import { TipoDocumento } from '@core/models/tipoDocumento';
import { BagService } from '@core/services/bag.service';
import { DocumentiService } from '@documenti/services/documenti.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { IconsSettings } from '@shared/utils/icons-settings';

@Component({
  selector: 'app-documenti-nuovo',
  templateUrl: './documenti-nuovo.component.html',
  styleUrls: ['./documenti-nuovo.component.scss']
})
export class DocumentiNuovoComponent implements OnInit {

  estensioniPermesse = ["doc", "docx", "xls", "xlsx", "ppt", "pptx", "pps", "ppsx", "pdf", "zip", "odb", "odg", "odp", "ods", "odf", "odt", "otg", "otp", "ots", "ott", "dot", "dotx", "gif", "jpg", "jpeg", "png", "svg", "mp4"]
  file?: File
  tipiDocumenti: TipoDocumento[] = []
  idOperatore!: number
  codUser: string = ""
  MAX_SIZE = 10 * 1024 * 1024;

  form = this.fb.group({
    titolo: ['', [Validators.required]],
    gruppo: ['', [Validators.required]],
  });

  constructor(
    private fb: FormBuilder,
    private documentiService: DocumentiService,
    private dialog: MatDialog,
    private router: Router,
    private bagService: BagService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.bagService.titolo = "Nuovo documento"
    this.bagService.icona = IconsSettings.DOCUMENTAZIONE_ICON

    this.idOperatore = JSON.parse(sessionStorage.getItem("_userInfo")!).idOperatore;
    this.codUser = JSON.parse(sessionStorage.getItem("_userInfo")!).codFisc;
    this.documentiService.getTipologieDocumenti().subscribe({
      next: ris => {
        this.tipiDocumenti = ris;
      }
    })
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

  createDocumento(): Documento {

    return {
      idOperatoreInserimento: this.idOperatore,
      nomeDocumento: this.file?.name,
      descrizioneDocumento: this.form.controls["titolo"].value!,
      codeTipoDocumento: this.form.controls["gruppo"].value!,
      documento: this.file,
      codUserInserim: this.codUser
    }
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
      return;
    } else if (!this.file) {
      this.openDialog("Attenzione", ["Selezionare un documento da caricare"], "card-body--danger");
      return;
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
    this.bagService.resetError(); 
    this.documentiService.uplooadFile(this.createDocumento()).subscribe(
      () => {
        this.confermaSalvataggio();
        this.router.navigateByUrl("/documenti/gestione");
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

  tornaIndietro(): void {
    this.location.back();
  }
}
