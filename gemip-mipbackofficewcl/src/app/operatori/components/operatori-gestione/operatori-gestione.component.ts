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
import { Router } from '@angular/router';
import { BagService } from '@core/services/bag.service';
import { OperatoriService } from '@operatori/services/operatori.service';

//-Models
import { FiltroRicercaOperatore } from '@shared/model/filtri-ricerca.model';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { Operatore } from '@core/models/operatore';
import { IconsSettings } from '@shared/utils/icons-settings';
import { SoggettoAttuatore } from '@core/models/models';
import { Location } from '@angular/common';

@Component({
  selector: 'app-operatori-gestione',
  templateUrl: './operatori-gestione.component.html',
  styleUrls: ['./operatori-gestione.component.scss']
})
export class OperatoriGestioneComponent implements OnInit {

  //--Gestione tabella
  visibleTable = false;
  rowData: RowOperatore[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  soggettoAttuatore?: SoggettoAttuatore;
  operatoreList: Operatore[] = [];

  constructor(
    private bag: BagService,
    private router: Router,
    private operatoreService: OperatoriService,
    private bagService: BagService,
    private location: Location,
    private operatoriService: OperatoriService
  ) { }

  ngOnInit(): void {

    this.bagService.titolo = "Ricerca operatori"
    this.bagService.icona = IconsSettings.OPERATORI_ICON
    this.soggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)

    this.createTable();
  }

  onSearch(filtri: FiltroRicercaOperatore) {
    if (this.soggettoAttuatore) {
      filtri.idSoggettoAttuatore = this.soggettoAttuatore.id
    }
    this.operatoreService.ricercaOperatore(filtri).subscribe({
      next: ris => {
        this.operatoreList = ris;
        this.rowData = ris.map(operatore => {
          return {
            id: operatore.id!,
            codFiscaleUtente: operatore.codFiscaleUtente!,
            cognome: operatore.cognome!,
            nome: operatore.nome!,
            email: operatore.email!,
            telefono: operatore.telefono,
            dataDisabilitazione: operatore.dataDisabilitazione,
            icons: this.createIcons(),
          }

        })
        this.visibleTable = true;
      }
    });
  }

  //############# Handle Icons #################
  createIcons(): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];
    if (this.bag.isRuoloAdmin()) {
      icons.push(InfoColumnActionSettingModel.getEditIcon((riga) => {
        this.operatoreService.operatore = riga;
        this.router.navigateByUrl('operatori/form');
      }));
    } else {
      icons.push(InfoColumnActionSettingModel.getViewIcon((riga) => {
        this.operatoreService.operatore = riga;
        this.operatoreService.canModify = false;
        this.router.navigateByUrl('operatori/form');
      }));
    }

    return icons;
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
    const idColumn = new ColumnSettingsModel('id', 'ID Utente', false, 'simple');
    const cognomeColumn = new ColumnSettingsModel('cognome', 'Cognome', false, 'simple');
    const nomeColumn = new ColumnSettingsModel('nome', 'Nome principale', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', false, 'simple');
    const telefonoColumn = new ColumnSettingsModel('telefono', 'Telefono', false, 'simple');

    const customActionColumn = new ColumnSettingsModel('icone', 'Azioni', true, 'customAction');

    this.columnList.push(
      ...[
        idColumn,
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
export interface RowOperatore {
  id?: number;
  cognome?: string;
  nome?: string;
  email?: string;
  telefono?: string;
  dataDisabilitazione?: Date;
  icons?: InfoColumnActionSettingModel[];
}
