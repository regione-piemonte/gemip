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

import { Component, OnInit, ViewChild } from '@angular/core';
import { FiltroRicercaEventoRicercaQuestionari } from '@shared/model/filtri-ricerca.model';
import { ReportisticaIdeeImpresaService } from '../../service/reportistica-idee-impresa.service';
import { RigheReportistica } from '../../model/reportistica-questionario';
import { MatTableDataSource } from '@angular/material/table';
import * as FileSaver from 'file-saver';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { Location } from '@angular/common';
import { TableSettingsReportisticaModel } from '../../model/shered-models';
import { MatPaginator } from '@angular/material/paginator';


@Component({
  selector: 'app-reportistica-questionari',
  templateUrl: './reportistica-questionari.component.html',
  styleUrls: ['./reportistica-questionari.component.scss']
})
export class ReportisticaQuestionariComponent implements OnInit {

  dataSource: MatTableDataSource<RigheReportistica> = new MatTableDataSource();
  startcolumnNames: string[] = ['id', 'dataCompletamento', 'areaTerritoriale', 'soggettoAttuatore',];
  merge: string[] = [];
  addColumn: Array<string> = [];
  addColumnIndex: Array<string> = [];
  filtro: FiltroRicercaEventoRicercaQuestionari = {};
  idOperatore: any;
  cambioTipoReport: boolean = false;
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  tableSettings: TableSettingsReportisticaModel = {
    length: 1000,
    pageSize: 15,
    pageIndex: 0,
    pageSizeOptions: [15, 25, 50],
    showFirstLastButtons: true,
  };
  visibileTabella = false;

  constructor(
    private reportisticaService: ReportisticaIdeeImpresaService,
    private bagService: BagService,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.bagService.titolo = "Reportistica questionari"
    this.bagService.icona = IconsSettings.REPORTISTICA_ICON
    this.reportisticaService.cambioTipoReport$.subscribe(next => {
      this.cambioTipoReport = next;
    })
  }
  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }
  onSearch(filtro: FiltroRicercaEventoRicercaQuestionari, event?: any) {
    this.filtro = filtro;
    if (event) {
      this.cambioTipoReport = false;
      this.tableSettings.pageIndex = event.pageIndex
      this.tableSettings.pageSize = event.pageSize
    }
    if(this.cambioTipoReport){
      this.tableSettings.pageIndex = 0;
    }
    this.reportisticaService.getQuestionari(filtro, this.tableSettings.pageIndex, this.tableSettings.pageSize).subscribe({
      next: data => {
        this.tableSettings.length = data.totaleRisposte;
        this.addColumn = [];
        this.addColumnIndex = [];
        this.merge = [];
        this.dataSource = new MatTableDataSource(data.righeReportistica);
        this.addColumn = data.domande;
        this.merge.push(...this.startcolumnNames)
        this.merge.push(...this.addColumn.map((_, index) => String(index)));
        this.addColumnIndex.push(...this.addColumn.map((_, index) => String(index)))
        this.visibileTabella = true;
      }
    });
  }
  onDownload() {
    let nomeOperatore = JSON.parse(sessionStorage.getItem("_userInfo")!).nome + " - " + JSON.parse(sessionStorage.getItem("_userInfo")!).cognome;
    this.reportisticaService.getExcelQuestionari(this.filtro, nomeOperatore).subscribe({
      next: data => { FileSaver.saveAs(data, "questionari_" + new Date().toLocaleDateString([], { year: 'numeric', month: '2-digit', day: '2-digit' }) + "_mip-report.xlsx",); }
    })
  }

  tornaIndietro(): void {
    this.location.back();
  }
}
