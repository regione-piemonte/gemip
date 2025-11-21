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

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { Operatore } from '@core/models/operatore';
import { OperatoriService } from '@operatori/services/operatori.service';
import { DialogSettings } from '@shared/model/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { Validation } from '@shared/components/_validation/validation';
import { ColumnSettingsModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { UserInfo } from '@core/models/userInfo';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { SoggettoAttuatoreService } from '@utenti/services/soggetto-attuatore.service';
import { Ente } from '@core/models/models';
import { Location } from '@angular/common';


@Component({
  selector: 'app-operatori-nuovo',
  templateUrl: './operatori-nuovo.component.html',
  styleUrls: ['./operatori-nuovo.component.scss']
})
export class OperatoriNuovoComponent implements OnInit {
  operatore?: Operatore;
  selectedTab = 0;
  maxLengthDescrizione = 200;
  userInfo!: UserInfo
  form: FormGroup = this.fb.group({

    email: ['', [Validation.emailValidator, Validators.maxLength(100)]],

    telefonoF: ['', [Validation.phoneNumberValidator, Validators.maxLength(12)]],

    // abilitato:['']
  });

  rowData: any[] = []
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];

  abilitato = false

  onToggle() {
    this.abilitato = !this.abilitato
  }

  print() {
    window.print()
  }

  constructor(
    private operatoriService: OperatoriService,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private bagService: BagService,
    private router: Router,
    private soggAttService: SoggettoAttuatoreService,
    private location: Location
  ) {

    if (router.url.endsWith("/form") && !operatoriService.operatore) {
      router.navigateByUrl("/operatori/gestione")
    }
  }

  ngOnInit(): void {
    this.bagService.titolo = "Modifica operatore"
    this.bagService.icona = IconsSettings.OPERATORI_ICON

    this.operatoriService.leftComponent = false;

    this.userInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)
    this.operatore = this.operatoriService.operatore;

    this.operatoriService.getPartnerOperatori(this.operatore!.id!.toString())
      .subscribe(r => {
        if (!r[0].denominazione) {
          this.rowData = r.map((e: Ente) => {
            return {
              ...e,
              denominazione: e.descrizioneEnte
            }
          }
          )
        } else {
          this.rowData = r;
        }
      })

    console.log(this.operatore);
    this.popolaDati();

    if (!this.canModify) {
      this.form.disable()
    }
    this.createTable();

  }

  createTable() {
    this.tableSettings.title = '';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;
    this.tableSettings.enablePagination = false;

    this.createColumnsTable();
  }
  createColumnsTable() {
    /**Columns */
    const cognomeColumn = new ColumnSettingsModel('denominazione', 'Denominazione', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', false, 'simple');

    this.columnList.push(
      ...[
        cognomeColumn,
        emailColumn,
      ]
    );
  }

  popolaDati() {
    this.form.controls["email"].setValue(this.operatore?.email);
    this.form.controls["telefonoF"].setValue(this.operatore?.telefono);
    console.log("disabilitazione: " + this.operatore?.dataDisabilitazione)
    this.abilitato = this.operatore?.dataDisabilitazione == null

  }

  onSalva() {
    this.bagService.resetError();
    console.log(this.form)
    if (this.form.invalid) {
      return;
    }
    let tmp = this.createOperatore();
    tmp = { ...tmp, id: this.operatore!.id }

    console.log("Dati per update:" + tmp + this.operatore!.id);

    this.operatoriService.updateOperartore(tmp).subscribe({
      next: ris => {
        this.operatoriService.operatore = ris;
        this.dialog.open(DialogConfermaComponent, {
          data: new DialogSettings(
            "Avviso!",
            ['I dati sono stati salvati correttamente!'],
            "card-body--success"
          ),
          disableClose: true
        });
      }
    });
  }

  createOperatore(): Operatore {
    return {
      id: undefined,
      codFiscaleUtente: undefined,
      cognome: undefined,
      nome: undefined,
      telefono: this.form.get("telefonoF")?.value,
      email: this.form.get("email")?.value,
      dataRegistrazione: undefined,
      dataDisabilitazione: this.abilitato ? undefined : new Date(),
      idOperatoreDisabilitazione: this.abilitato ? undefined : this.userInfo.idOperatore,
      codUserInserim: undefined,
      dataInserim: undefined,
      codUserAggiorn: this.userInfo.codFisc!,
      dataAggiorn: undefined,
    }
  }

  get isSaved() {
    return false;
  }
  get canModify() {
    return this.operatoriService.canModify;
  }

  tornaIndietro(): void {
    this.location.back();
  }
}
