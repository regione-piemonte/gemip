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
import { SoggettoAttuatoreService } from './../../services/soggetto-attuatore.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { Ente } from '@core/models/ente';
import { SoggettoAffidatario } from '@core/models/soggettoAffidatario';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { BagService } from '@core/services/bag.service';
import { ColumnSettingsModel, InfoColumnActionSettingModel, TableSettingsModel } from '@shared/model/table-setting.model';
import { IconsSettings } from '@shared/utils/icons-settings';

@Component({
  selector: 'app-soggetto-attuator-gestione',
  templateUrl: './soggetto-attuator-gestione.component.html',
  styleUrls: ['./soggetto-attuator-gestione.component.scss']
})
export class SoggettoAttuatorGestioneComponent implements OnInit {
  panelOpenState = true;
  form = this.fb.group({
    denominazione: [''],
    email: [''],
    tipoSogg: ['Att']
  });

  visibleTable = false;
  visibleTableAttuatori = false;
  soggetiAttList: SoggettoAttuatore[] = []
  soggetiAffList: SoggettoAffidatario[] = []
  EntiRegioneList: Ente[] = []
  EntiAplList: Ente[] = []
  rowData: RowSogg[] = []
  rowSoggetiAttList: RowSogg[] = []
  rowSoggetiAffList: RowSogg[] = []
  rowEntiRegioneList: RowSogg[] = []
  rowEntiAplList: RowSogg[] = []
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  columnListAttuatori: ColumnSettingsModel[] = [];

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private bag: BagService,
    private location: Location,
    private soggAttService: SoggettoAttuatoreService
  ) { }

  ngOnInit(): void {
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (!event.url.startsWith('/utenti/soggetto-attuatore')) {
          this.soggAttService.leftComponent = true;
          this.form.reset();
        }
      }
    });

    this.bag.titolo = "Ricerca soggetti giuridici"
    this.bag.icona = IconsSettings.SOGG_ICON

    this.soggAttService.getSoggettiAttuatori().subscribe(r => {
      this.soggetiAttList = r;
      this.rowSoggetiAttList = this.soggetiAttList.map(s => {
        return { ...s, areaTerritoriale: s.codiceAreaTerritoriale?.descrizioneAreaTerritoriale, icons: this.createIcons(s) }
      });
      this.soggAttService.rowSoggetiAttList = this.rowSoggetiAttList;
    })

    this.soggAttService.getSoggettiAffidatari().subscribe(r => {
      this.soggetiAffList = r;
      this.rowSoggetiAffList = this.soggetiAffList.map(s => {
        return {
          ...s,
          icons: this.createIcons()
        }
      });
      this.soggAttService.rowSoggetiAffList = this.rowSoggetiAffList;
    })
    this.soggAttService.getEnti("regione").subscribe(r => {
      this.EntiRegioneList = r;
      this.rowEntiRegioneList = this.EntiRegioneList.map(s => { return { ...s, icons: this.createIcons() } });
      this.soggAttService.rowEntiRegioneList = this.rowEntiRegioneList;
    })

    this.soggAttService.getEnti("apl").subscribe(r => {
      this.EntiAplList = r;
      this.rowEntiAplList = this.EntiAplList.map(s => { return { ...s, icons: this.createIcons() } });
      this.soggAttService.rowEntiAplList = this.rowEntiAplList;
    })



    this.createTable();
    this.createColumnsTableAttuatori();

    //caching
    if (!this.soggAttService.leftComponent) {
      this.form.controls["denominazione"].setValue(this.soggAttService.filtriRicerca.denom);
      this.form.controls["email"].setValue(this.soggAttService.filtriRicerca.email);
      this.form.controls["tipoSogg"].setValue(this.soggAttService.filtriRicerca.tipo);
      this.visibleTable = true;
      this.onCachingSearch();
    }
  }

  openPanel() {
    this.panelOpenState = true
  }
  closePanel() {
    this.panelOpenState = false
  }

  clear() {
    this.form.reset()
    this.form.controls.tipoSogg.setValue("Att");
  }
  onSearch() {
    let denom: string = this.form.controls.denominazione.value ?? '';
    let email: string = this.form.controls.email.value ?? '';

    switch (this.form.controls.tipoSogg.value) {
      case "Att":
        this.rowData = this.rowSoggetiAttList
        this.visibleTableAttuatori = true;
        this.visibleTable = false;
        break;
      case "Aff":
        this.rowData = this.rowSoggetiAffList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
      case "Rgg":
        this.rowData = this.rowEntiRegioneList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
      case "Apl":
        this.rowData = this.rowEntiAplList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
    }
    if (denom) { this.rowData = this.rowData.filter((r: any) => r.denominazione?.toLocaleLowerCase().includes(denom.toLocaleLowerCase())) }
    if (email) { this.rowData = this.rowData.filter((r: any) => r.email?.includes(email)) }
    this.soggAttService.filtriRicerca.denom = denom;
    this.soggAttService.filtriRicerca.email = email;
    this.soggAttService.filtriRicerca.tipo = this.form.controls.tipoSogg.value;
    this.createTable();
  }
  onCachingSearch() {
    let denom: string = this.form.controls.denominazione.value ?? '';
    let email: string = this.form.controls.email.value ?? '';
    switch (this.form.controls.tipoSogg.value) {
      case "Att":
        this.rowData = this.soggAttService.rowSoggetiAttList
        this.visibleTableAttuatori = true;
        this.visibleTable = false;
        break;
      case "Aff":
        this.rowData = this.soggAttService.rowSoggetiAffList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
      case "Rgg":
        this.rowData = this.soggAttService.rowEntiRegioneList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
      case "Apl":
        this.rowData = this.soggAttService.rowEntiAplList
        this.visibleTableAttuatori = false;
        this.visibleTable = true;
        break;
    }
    if (denom) { this.rowData = this.rowData.filter((r: any) => r.denominazione?.toLocaleLowerCase().includes(denom.toLocaleLowerCase())) }
    if (email) { this.rowData = this.rowData.filter((r: any) => r.email?.includes(email)) }
    this.soggAttService.filtriRicerca.denom = denom;
    this.soggAttService.filtriRicerca.email = email;
    this.soggAttService.filtriRicerca.tipo = this.form.controls.tipoSogg.value;
    this.createTable();
    //this.visibleTable = true;
  }

  //############# Handle Icons #################
  createIcons(att?: SoggettoAttuatore): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];
    if (this.bag.isRuoloAdmin() || ((att && this.bag.ruolo.entiAssociati!.filter(e => (e.gruppoOperatore! + e.codOperatore) == (att.gruppoOperatore! + att.codOperatore)).length > 0) && this.bag.ruolo.codiceRuolo !== "GEMIP_REGIONALE_BASE")) {
      icons.push(InfoColumnActionSettingModel.getEditIcon((r) => {

        this.soggAttService.isAffidatario = this.form.controls.tipoSogg.value == "Aff";
        if (this.soggAttService.isAffidatario) {
          this.soggAttService.soggAtt = this.soggetiAffList.filter(s => s.id == r.id)[0];
        }
        this.soggAttService.isAttuatore = this.form.controls.tipoSogg.value == "Att";
        if (this.soggAttService.isAttuatore) {
          this.soggAttService.soggAtt = this.soggetiAttList.filter(s => s.id == r.id)[0];
        }
        if (this.form.controls.tipoSogg.value == "Rgg") {
          this.soggAttService.soggAtt = this.EntiRegioneList.filter(s => s.id == r.id)[0];
        }
        if (this.form.controls.tipoSogg.value == "Apl") {
          this.soggAttService.soggAtt = this.EntiAplList.filter(s => s.id == r.id)[0];
        }
        this.soggAttService.canModify = true;
        this.router.navigateByUrl('utenti/soggetto-attuatore/form');

      }
      ));
    } else {
      icons.push(InfoColumnActionSettingModel.getViewIcon((r) => {

        this.soggAttService.isAffidatario = this.form.controls.tipoSogg.value == "Aff";
        this.soggAttService.isAttuatore = this.form.controls.tipoSogg.value == "Att";
        this.soggAttService.soggAtt = r;
        this.soggAttService.canModify = false;
        this.router.navigateByUrl('utenti/soggetto-attuatore/form')
      }
      ))
    };

    return icons;
  }

  createTable() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;

    this.createColumnsTable();
  }
  createTableAttuatori() {
    this.tableSettings.title = 'Elenco ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;

    this.createColumnsTableAttuatori();
  }
  createColumnsTable() {
    /**Columns */
    const denominazioneColumn = new ColumnSettingsModel('denominazione', 'Denominazione', false, 'simple');
    const emailColumn = new ColumnSettingsModel('emailSg', 'Email', true, 'simple');
    const customActionColumn = new ColumnSettingsModel('icons', 'Azioni', true, 'customAction');

    this.columnList = []
    this.columnList.push(
      ...[
        denominazioneColumn,
        emailColumn,
        customActionColumn,
      ]
    );

  }
  createColumnsTableAttuatori() {
    const denominazioneColumn = new ColumnSettingsModel('denominazione', 'Denominazione', false, 'simple');
    const emailColumn = new ColumnSettingsModel('email', 'Email', true, 'simple');
    const customActionColumn = new ColumnSettingsModel('icons', 'Azioni', true, 'customAction');
    const areaTerritorialeColumn = new ColumnSettingsModel('areaTerritoriale', 'Area territoriale', false, 'simple');
    this.columnListAttuatori.push(
      ...[
        denominazioneColumn,
        emailColumn,
        areaTerritorialeColumn,
        customActionColumn,
      ]
    );
  }
  tornaIndietro(): void {
    this.location.back();
  }
}
export interface RowSogg {
  gruppoOperatore?: string;
  codOperatore?: number;
  denominazione?: string;
  email?: string;
  telefono?: string;
  codUserInserim?: string;
  dataInserim?: Date;
  codUserAggiorn?: string;
  dataAggiorn?: Date;
  codiceAreaTerritoriale?: AreaTerritoriale;
  areaTerritoriale?: string;
  icons: InfoColumnActionSettingModel[]
}
