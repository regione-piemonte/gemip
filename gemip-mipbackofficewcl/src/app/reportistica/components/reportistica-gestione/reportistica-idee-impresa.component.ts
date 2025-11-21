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

import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { FiltriRicerca, FiltroRicercaEventoRicercaIdeeImpresa } from '@shared/model/filtri-ricerca.model';
import { ReportisticaIdeeImpresaService } from '../../service/reportistica-idee-impresa.service';
import * as FileSaver from 'file-saver';
import { UserInfo } from '@core/models/userInfo';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { Location } from '@angular/common';
import { TableSettingsReportisticaModel } from '../../model/shered-models';
import { MatPaginator } from '@angular/material/paginator';
@Component({
  selector: 'app-reportistica-idee-impresa',
  templateUrl: './reportistica-idee-impresa.component.html',
  styleUrls: ['./reportistica-idee-impresa.component.scss']
})
export class ReportisticaIdeeImpresaComponent implements OnInit, AfterViewInit {

  cartaSelected: boolean = false;
  permessoSelected: boolean = false;
  dataSource: MatTableDataSource<{}> = new MatTableDataSource();
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  tableSettings: TableSettingsReportisticaModel = {
    length: 1000,
    pageSize: 15,
    pageIndex: 0,
    pageSizeOptions: [15, 25, 50],
    showFirstLastButtons: true,
  }
  columnNames: string[] = ['idIdea', 'titolo', 'dataInserim', 'descrizioneAreaTerritoriale',
    'descrizioneIdeaDiImpresa', 'statoIdeaDiImpresa', 'utentePrincipale', 'dataIncontroPreAccoglienza',
    'sedeIncontroPreAccoglienza', 'incontroTelefonico', 'soggettoAttuatoreDenominazione', 'dataSoggettoAttuatoreAssociazione',
    'erogazionePrimaOra', 'firmaPattoServizio', 'dataFirmaPattoServizio', 'BPvalidato', 'dataBPvalidato', 'misuraRicambio',
    'idUtente', 'cognome', 'nome', 'codiceFiscale', 'sesso', 'cittadinanza', 'cittadinanzaAltro', 'dataNascita', 'luogoNascita',
    'provinciaNascita', 'recapitoTelefono2', 'recapitoTelefono', 'email', 'email2', 'indirizzoResidenza', 'citta', 'cap', 'provincia',
    'indirizzoDomicilio', 'capDomicilio', 'cittaDomicilio', 'provinciaDomicilio', 'titoloStudio', 'titoloStudioAltro',
    'situazioneLavorativa', 'situazioneLavorativaAltro', 'svantaggioAbitativo', 'condizioneFamiliare', 'noteCommenti', 'creato', 'inseritoDa', 'modificato', 'modificatoDa',
    'conoscenzaMip', 'altroConoscenzaMip', 'commentiInterni', 'titoloSoggiorno', 'motivoTitoloSoggiorno', 'titoloCarta', 'motivoCartaSoggiorno'];

  filtro: FiltroRicercaEventoRicercaIdeeImpresa = {};
  visibileTabella = false;
  constructor(
    private bagService: BagService,
    private reportisticaService: ReportisticaIdeeImpresaService,
    private location: Location
  ) { }

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.bagService.titolo = "Reportistica idee d'impresa"
    this.bagService.icona = IconsSettings.REPORTISTICA_ICON
  }

  onSearch(filtro: FiltroRicercaEventoRicercaIdeeImpresa, eventPag?: any) {
    this.filtro = filtro;
    if (eventPag) {
      this.tableSettings.pageIndex = eventPag.pageIndex;
      this.tableSettings.pageSize = eventPag.pageSize;
    }
    this.reportisticaService.getIdeeImpresa(filtro, this.tableSettings.pageIndex, this.tableSettings.pageSize).subscribe({
      next: data => {
        this.tableSettings.length = data.totalNumberResult!;
        this.dataSource = new MatTableDataSource(data.ideaDiImpresaRicercaList);
        this.dataSource.filteredData.forEach(el => {

        });
        this.visibileTabella = true;
      }
    })
  }

  onDownload() {
    let user: UserInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)
    let nomeOperatore = user?.nome + " - " + user?.cognome;
    this.reportisticaService.getExcelIdeeImpresa(this.filtro, nomeOperatore).subscribe({
      next: data => { FileSaver.saveAs(data, "ideedimpresa_" + new Date().toLocaleDateString([], { year: 'numeric', month: '2-digit', day: '2-digit' }) + "_mip-report.xlsx",); }
    })
  }

  tornaIndietro(): void {
    this.location.back();
  }

  checkPermesso(element: any) {
    return element.cittadinoAnagrafica?.tipoPermessoDiSoggiorno === 'P';
  }

  checkCarta(element: any) {
    return element.cittadinoAnagrafica?.tipoPermessoDiSoggiorno === 'C';
  }
}
