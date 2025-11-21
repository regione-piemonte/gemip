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
import { CalendarioIcsService } from '../../services/calendario-ics.service';
import { TableSettingsModel, ColumnSettingsModel } from '@shared/model/table-setting.model';
import { FiltroRicercaEventoCalendario } from '@shared/model/filtri-ricerca.model';
import { IcsBagService } from '../../services/calendario-ics-bag.service';
import { Sort } from '@angular/material/sort';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';

@Component({
  selector: 'app-calendario-elenco',
  templateUrl: './calendario-elenco.component.html',
  styleUrls: ['./calendario-elenco.component.scss'],
})
export class CalendarioElencoComponent implements OnInit {
  visibleTable = true;
  rowDataEvento: RowEventoCalendario[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  pageIndex!:number;
  pageSize!:number;
  soggettoAttuatore?: SoggettoAttuatore;
  sort?:Sort;
  totRis:number=0;
  filtri?:FiltroRicercaEventoCalendario
  constructor(
    private icsService:CalendarioIcsService,
    private icsBag:IcsBagService
  ) { }

  ngOnInit(): void {
    this.soggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!);
    this.createTable();
    let dataDa: Date = new Date()
    dataDa.setHours(0);

    let dataA: Date = new Date();
    dataA.setMonth(dataDa.getMonth() + 1);
    dataA.setHours(23);
    let filtro: FiltroRicercaEventoCalendario = {
      dataA: dataA,
      dataDa: dataDa,
      idSoggettoAttuatore: this.soggettoAttuatore?.id?.toString()
    }
    this.icsBag._pageIndex.subscribe(
      ris => this.pageIndex = ris
    )
    this.icsBag._pageSize.subscribe(
      ris => this.pageSize = ris
    )
    this.icsBag._filtriRicerca.subscribe(
      ris => this.filtri = ris
    )
    this.icsBag._sort.subscribe(
      ris => this.sort = ris
    )

    this.onSearch(filtro);
  }
  onSearch(filtro: FiltroRicercaEventoCalendario) {
    this.filtri = filtro;
    this.icsService.ricercaEventiCalendario(filtro, this.pageIndex, this.pageSize, this.sort).subscribe({
      next: ris => {

        this.rowDataEvento = ris.eventiCalendario!.map(evento => {
          return {
            idEvento: evento.evento!.idEventoCalendario!,
            data: new Date(evento.evento!.dataOraInizio!).toLocaleDateString([], { day: '2-digit', month: '2-digit', year: 'numeric' }),
            oraInizio: new Date(evento.evento!.dataOraInizio!).toLocaleDateString([], { hour: '2-digit', minute: '2-digit' }).split(",")[1],
            oraFine: new Date(evento.evento!.dataOraFine!).toLocaleDateString([], { hour: '2-digit', minute: '2-digit' }).split(",")[1],
            operatoreSoggetto: evento.operatore!.cognome! + " " + evento.operatore!.nome! + "(" + evento.soggettoAttuatore?.denominazione! + ")",
            titoloDescrizione: ""+evento.evento!.titolo!+" --- "+evento.evento?.descrizioneEvento!,
            luogo: evento.evento!.luogo!

          }
        })
        this.totRis = ris.totRis!;
        this.tableSettings.length = this.totRis;
        this.tableSettings.pageIndex = this.pageIndex!;
        this.tableSettings.pageSize = this.pageSize!;
        this.visibleTable = true
        this.icsBag.updateFiltri(filtro);
      }
    }
    )
  }
  onPageIndexSizeChange(indexSize: number[]) {
    this.icsBag.updatePagination(indexSize[1], indexSize[0])
    this.onSearch(this.filtri!)
  }
  sortChange(sort: Sort) {
    this.icsBag.updateSort(sort);
    this.onSearch(this.filtri!)
  }
  createTable() {
    this.tableSettings.title = 'Elenco appuntamenti ';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;

    this.createColumnsTable();
  }
  createColumnsTable() {
    /**Columns */
    const dataColumn = new ColumnSettingsModel('data', 'Data', false, 'simple');
    const oraInizioColumn = new ColumnSettingsModel('oraInizio', 'Ora inizio', true, 'simple');
    const oraFineColumn = new ColumnSettingsModel('oraFine', 'Ora fine', true, 'simple');
    const titoloDescrizioneColumn = new ColumnSettingsModel('titoloDescrizione', 'Titolo - Descrizione', true, 'simple');
    const operatoreColumn = new ColumnSettingsModel('operatoreSoggetto', 'Operatore', false, 'simple');
    const luogoColumn = new ColumnSettingsModel('luogo', 'Luogo', false, 'simple');

    this.columnList.push(
      ...[
        dataColumn,
        oraInizioColumn,
        oraFineColumn,
        luogoColumn,
        titoloDescrizioneColumn,
        operatoreColumn
      ]
    );
  }

}
export interface RowEventoCalendario{
  idEvento: number,
  data: string,
  oraInizio: string,
  oraFine: string,
  titoloDescrizione:string,
  operatoreSoggetto: string,
  luogo: string
}
