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
import { Operatore } from '@core/models/operatore';
import { OperatoreIncontroPreaccoglienza } from '@core/models/operatoreIncontroPreaccoglienza';
import { UserInfo } from '@core/models/userInfo';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { TableSettingsModel, ColumnSettingsModel, InfoColumnActionSettingModel } from '@shared/model/table-setting.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-operatori-incontro',
  templateUrl: './operatori-incontro.component.html',
  styleUrls: ['./operatori-incontro.component.scss']
})
export class OperatoriIncontroComponent implements OnInit {

  @Input() isStampa: boolean = false

  operatori!: Observable<Operatore[]>;
  operatoriPresenti: Operatore[] = [];
  formOperatori = this.fb.group({ operatore: ['', Validators.required] });
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  rowData: OperatoreSimple[] = [];
  visibleTable = false;
  codUser: string = ""

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private incontriPreAccoglienzaService: PreAccoglienzaService,
    private IncontriPreaccoglienzaBag: PreAccoglienzaBagService
  ) { }

  ngOnInit(): void {
    let user: UserInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)
    this.codUser = user.codFisc!
    this.createTable();
    let ruoloOperatore = JSON.parse(sessionStorage.getItem('_ruolo')!).codiceRuolo;
    if (ruoloOperatore === "GEMIP_AFFIDATARIO_ALL") {
      this.incontriPreAccoglienzaService.getOperatoriSoggAffidatarioById(JSON.parse(sessionStorage.getItem('_userInfo')!).idOperatore).subscribe({
        next: ris => {
          this.operatori = this.incontriPreAccoglienzaService.getOperatoriSoggettoAffiddatario(ris[0].id!);
        }
      })

    } else {
      this.operatori = this.incontriPreAccoglienzaService.getOperatoriSoggettoAffiddatario();
    }
    if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {

      this.incontriPreAccoglienzaService.getOperatoriAssociati(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza).subscribe({
        next: ris => {
          this.operatoriPresenti = ris;
          this.rowData = ris.map(operatore => {
            return {
              id: operatore.id!,
              telefono: operatore.telefono!,
              cognomeNome: operatore.cognome! + " " + operatore.nome!,
              email: operatore.email!,
              icons: this.createIcons()
            }
          })
          this.tableSettings.length = this.rowData.length
          this.visibleTable = true;
        }
      })
    }
    this.IncontriPreaccoglienzaBag.aggiornaOpertoriStampa.subscribe({
      next: ris => {
        if (ris) {
          this.incontriPreAccoglienzaService.getOperatoriAssociati(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza!).subscribe({
            next: ris => {
              this.operatoriPresenti = ris;
              this.rowData = ris.map(operatore => {
                return {
                  id: operatore.id!,
                  telefono: operatore.telefono!,
                  cognomeNome: operatore.cognome! + " " + operatore.nome!,
                  email: operatore.email!,
                  icons: this.createIcons()
                }
              })
              this.tableSettings.length = this.rowData.length
              this.visibleTable = true;
            }
          })
        }
      }
    });
  }

  onAggiungiOperatore() {
    if (this.formOperatori.valid) {
      if (this.rowData.filter(op => op.id == +this.formOperatori.get("operatore")!.value!).length == 0) {
        this.incontriPreAccoglienzaService.associaOperatoreIncontro(this.createOperatoreIncontro()).subscribe({
          next: (operatore) => {
            this.rowData.push({
              id: operatore.id!,
              telefono: operatore.telefono,
              cognomeNome: operatore.cognome! + " " + operatore.nome!,
              email: operatore.email!,
              icons: this.createIcons()
            })
            this.confermaSalvataggio()
            this.rowData = [...this.rowData];
            console.log(this.rowData)
            this.visibleTable = true;
          }
        })
      } else {
        this.openDialog("Attenzione!", ["L'operatore selzionato è già associato a questo incontro"], "card-body--danger");
      }
    } else {
      return;
    }
  }

  createOperatoreIncontro(): OperatoreIncontroPreaccoglienza {
    return {
      idIncontroPreaccoglienza: this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza,
      idOperatore: +this.formOperatori.get("operatore")?.value!,
      codUserAggiorn: this.codUser,
      codUserInserim: this.codUser

    }
  }

  createTable() {
    this.tableSettings.title = 'Elenco operatori associati ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;
    this.tableSettings.enablePagination = false
    this.createColumns()
  }

  createColumns() {
    const telColumn = new ColumnSettingsModel('telefono', 'Telefono', false, 'simple');
    const cognomeNomeCOlumn = new ColumnSettingsModel('cognomeNome', 'Nominativo', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', false, 'simple');
    const customActionColumn = new ColumnSettingsModel('icons', '', true, 'customAction');
    this.columnList.push(
      ...[
        telColumn,
        cognomeNomeCOlumn,
        emailColumn
      ]
    );

    if (this.canModify)
      this.columnList.push(customActionColumn);
  }

  createIcons(): InfoColumnActionSettingModel[] {
    let icons: InfoColumnActionSettingModel[] = [];
    icons.push(InfoColumnActionSettingModel.getDeleteIcon((operatore) => { this.handleDelete(operatore.id) }))
    return icons;
  }


  handleDelete(idOperatore: number) {
    let buttons: DialogButton[] = [
      new DialogButton('Annulla', 'btn btn--outline-primary', undefined, "annulla"),
      new DialogButton('Conferma', 'btn btn--danger', () => {
        this.incontriPreAccoglienzaService.disassociaOperatoreIncontro(idOperatore, this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza!).subscribe({
          next: () => {
            this.rowData.splice(this.rowData.findIndex(o => o.id == idOperatore), 1);
            this.rowData = [...this.rowData];
            console.log(this.rowData)
          }
        })
      }, "disassocia operatore")]
    this.openDialog("Attenzione", ["Sei sicuro di voler disassociare questo operatore dall'incontro?"], "card-body--warning", buttons);
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
    return this.incontriPreAccoglienzaService.canModify && !this.isStampa;
  }

}

export interface OperatoreSimple {
  id: Number,
  cognomeNome: string,
  telefono?: string,
  email: string,
  icons: InfoColumnActionSettingModel[];
}
