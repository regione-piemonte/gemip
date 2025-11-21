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

import { Router } from '@angular/router';
import { SoggettoAttuatoreService } from './../../services/soggetto-attuatore.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { Operatore } from '@core/models/operatore';
import { ColumnSettingsModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { SoggettoAffidatario } from '@core/models/soggettoAffidatario';
import { Ente } from '@core/models/ente';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { UserInfo } from '@core/models/userInfo';
import { Validation } from '@shared/components/_validation/validation';
import { Ruolo } from '@core/models/ruolo';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { Location } from '@angular/common';

@Component({
  selector: 'app-soggetto-attuatore-form',
  templateUrl: './soggetto-attuatore-form.component.html',
  styleUrls: ['./soggetto-attuatore-form.component.scss']
})
export class SoggettoAttuatoreFormComponent implements OnInit {

  form: FormGroup = this.fb.group({
    telefono: [{ value: '', disabled: true }, [Validation.phoneNumberValidator, Validators.maxLength(12)]],
    email: [{ value: '', disabled: true }, [Validation.emailValidator, Validators.maxLength(100)]],
  })

  selectedTab = 0;

  soggetto!: SoggettoAttuatore | SoggettoAffidatario | Ente;

  rowData: Operatore[] = []
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  user!: UserInfo
  ruolo!: Ruolo

  constructor(
    private fb: FormBuilder,
    private soggAttService: SoggettoAttuatoreService,
    private router: Router,
    private bagService: BagService,
    private dialog: MatDialog,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.soggAttService.leftComponent = false;
    this.soggetto = this.soggAttService.soggAtt!;
    this.bagService.titolo = "Modifica soggetto"
    this.bagService.icona = IconsSettings.SOGG_ICON
    this.user = JSON.parse(sessionStorage.getItem("_userInfo")!)
    this.ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!)

    if (!this.soggAttService.soggAtt) {
      this.router.navigateByUrl('utenti/soggetto-attuatore/gestione')
    }

    this.form.controls["email"].setValue(this.soggetto.email!)
    this.form.controls["telefono"].setValue(this.soggetto.telefono!)
    this.createTable();
    if (this.soggAttService.canModify) {
      this.form.enable()
    }
    if (this.soggAttService.isAffidatario) {
      this.soggAttService.getOperatoriSoggAff(this.soggetto.id!.toString())
        .subscribe(r => this.rowData = r)
    } else if (this.soggAttService.isAttuatore) {
      this.soggAttService.getOperatoriSoggAtt(this.soggetto.id!.toString())
        .subscribe(r => this.rowData = r)
    } else {
      this.soggAttService.getOperatoriEnti(this.soggetto.id!.toString())
        .subscribe(r => this.rowData = r)
    }
  }


  print() {
    window.print()
  }

  get isAttuatore() {
    return this.soggAttService.isAttuatore;
  }

  createTable() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;
    this.tableSettings.enablePagination = false;
    this.createColumnsTable();
  }

  createColumnsTable() {
    /**Columns */
    const idColumn = new ColumnSettingsModel('id', 'ID', false, 'simple');
    const cognomeColumn = new ColumnSettingsModel('cognome', 'Cognome', false, 'simple');
    const nomeColumn = new ColumnSettingsModel('nome', 'Nome', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', false, 'simple');
    const telefonoColumn = new ColumnSettingsModel('telefono', 'Telefono', false, 'simple');

    this.columnList.push(
      ...[
        idColumn,
        cognomeColumn,
        nomeColumn,
        telefonoColumn,
        emailColumn,
      ]
    );
  }

  aggiornaDati() {
    console.log(this.soggAttService.isAttuatore)
    if (this.soggAttService.isAffidatario) {
      this.soggAttService.updateSoggettoAff(this.createSoggettoForUpdate()).subscribe({
        next: r => {
          this.confermaSalvataggio()
        }
      })
    } else if (this.soggAttService.isAttuatore) {
      this.soggAttService.updateSoggettoAtt(this.createSoggettoForUpdate()).subscribe({
        next: r => {
          this.confermaSalvataggio()
        }
      })
    } else {
      this.soggAttService.updateEnte(this.createEnteForUpdate()).subscribe({
        next: r => {
          this.confermaSalvataggio()
        }
      })
    }
  }

  createSoggettoForUpdate(): SoggettoAffidatario | SoggettoAttuatore {
    return {
      ...this.soggetto,
      email: this.form.get("email")?.value,
      telefono: this.form.get("telefono")?.value,
      codUserAggiorn: this.user.codFisc!
    }
  }

  createEnteForUpdate() {
    return {
      id: this.soggetto.id,
      codUserAggiorn: this.user.codFisc,
      email: this.form.get("email")?.value,
      telefono: this.form.get("telefono")?.value,
    }
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

  get canModify() {
    return this.soggAttService.canModify;
  }

  tornaIndietro(): void {
    this.location.back();
  }
}
