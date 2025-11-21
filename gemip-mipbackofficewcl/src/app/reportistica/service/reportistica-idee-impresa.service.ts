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

import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigUrlService } from '@core/services/config-url.service';
import { FiltroRicercaAnagraficheCittadini, FiltroRicercaEventoRicercaIdeeImpresa, FiltroRicercaEventoRicercaQuestionari } from '@shared/model/filtri-ricerca.model';
import { Observable, of, Subject } from 'rxjs';
import { ReportQuestionario } from '../model/reportistica-questionario';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { IncontroPreaccoglienzaRicerca } from '@core/models/incontroPreaccoglienzaRicerca';
import { Sort } from '@angular/material/sort';
import { IdeaDiImpresaRicercaExtendedConTotale } from '../model/reportistica-idee-impresa';

@Injectable({
  providedIn: 'root'
})
export class ReportisticaIdeeImpresaService {

  cambioTipoReportSubject = new Subject<boolean>();
  cambioTipoReport$ = this.cambioTipoReportSubject.asObservable();

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  getIdeeImpresa(event: FiltroRicercaEventoRicercaIdeeImpresa, pageIndex: number, pageSize: number): Observable<IdeaDiImpresaRicercaExtendedConTotale> {
    let queryParams: HttpParams = new HttpParams()
    if (event.dataDa) queryParams = queryParams.append("dataDa", event.dataDa.toDateString());
    if (event.dataA) queryParams = queryParams.append("dataA", event.dataA.toDateString());
    if (event.idSoggettoAttuatore) queryParams = queryParams.append("idSoggettoAttuatore", event.idSoggettoAttuatore);
    if (event.areaTerritoriale) queryParams = queryParams.append("idCodAreaTerritoriale", event.areaTerritoriale);
    if (event.tipoReport) queryParams = queryParams.append("tipoReport", event.tipoReport);
    if (event.idStatoIdea) queryParams = queryParams.append("idStatoIdea", event.idStatoIdea);
    if (pageIndex != undefined) queryParams = queryParams.append("pageIndex", pageIndex);
    if (pageSize) queryParams = queryParams.append("pageSize", pageSize);
    return this.http.get<IdeaDiImpresaRicercaExtendedConTotale>(this.configUrl.reportisticaIdeeImpresaUrl, { params: queryParams });
  }

  getExcelIdeeImpresa(event: FiltroRicercaEventoRicercaIdeeImpresa, nomeOperatore?: string): Observable<Blob> {
    let queryParams: HttpParams = new HttpParams()
    if (event.dataDa) queryParams = queryParams.append("dataDa", event.dataDa.toDateString());
    if (event.dataA) queryParams = queryParams.append("dataA", event.dataA.toDateString());
    if (event.idSoggettoAttuatore) queryParams = queryParams.append("idSoggettoAttuatore", event.idSoggettoAttuatore);
    if (event.tipoReport) queryParams = queryParams.append("tipoReport", event.tipoReport);
    if (event.idStatoIdea) queryParams = queryParams.append("idStatoIdea", event.idStatoIdea);
    if (nomeOperatore) queryParams = queryParams.append("nomeOperatore", nomeOperatore);
    return this.http.get<Blob>(this.configUrl.reportisticaExcelIdeeImpresaUrl, { params: queryParams, responseType: 'blob' as 'json' });
  }

  getQuestionari(event: FiltroRicercaEventoRicercaQuestionari, pageIndex: number, pageSize: number): Observable<ReportQuestionario> {
    let queryParams: HttpParams = new HttpParams()
    if (event.dataDa) queryParams = queryParams.append("dataDa", event.dataDa.toDateString());
    if (event.dataA) queryParams = queryParams.append("dataA", event.dataA.toDateString());
    if (event.idSoggettoAttuatore) queryParams = queryParams.append("idSoggettoAttuatore", event.idSoggettoAttuatore);
    if (event.tipoReport) queryParams = queryParams.append("idFase", event.tipoReport);
    if (event.codAreaTerritoriale) queryParams = queryParams.append("idCodAreaTerritoriale", event.codAreaTerritoriale);
    if (pageIndex != undefined) queryParams = queryParams.append("pageIndex", pageIndex);
    if (pageSize) queryParams = queryParams.append("pageSize", pageSize);
    return this.http.get<ReportQuestionario>(this.configUrl.reportisticaQuestionario, { params: queryParams });
  }

  getExcelQuestionari(event: FiltroRicercaEventoRicercaQuestionari, nomeOperatore: string): Observable<Blob> {
    let queryParams: HttpParams = new HttpParams()
    if (event.dataDa) queryParams = queryParams.append("dataDa", event.dataDa.toDateString());
    if (event.dataA) queryParams = queryParams.append("dataA", event.dataA.toDateString());
    if (event.idSoggettoAttuatore) queryParams = queryParams.append("idSoggettoAttuatore", event.idSoggettoAttuatore);
    if (event.tipoReport) queryParams = queryParams.append("idFase", event.tipoReport);
    if (event.codAreaTerritoriale) queryParams = queryParams.append("idCodAreaTerritoriale", event.codAreaTerritoriale);
    queryParams = queryParams.append("nomeOperatore", nomeOperatore);
    return this.http.get<Blob>(this.configUrl.reportisticaExcelQuestionari, { params: queryParams, responseType: 'blob' as 'json' });
  }

  ricercaIncontri(filtri: FiltroRicercaAnagraficheCittadini, pageIndex?: number, pageSize?: number, sort?: Sort): Observable<IncontroPreaccoglienzaRicerca> {
    let queryParams = new HttpParams();
    if (filtri.areaTerritoriale) {
      queryParams = queryParams.append('codAreaTerritoriale', filtri.areaTerritoriale);
    }
    if (filtri.codFiscOperatore) {
      queryParams = queryParams.append('idOperatore', filtri.codFiscOperatore);
    }
    if (filtri.idSoggettoAttuatore) {
      queryParams = queryParams.append('idSoggettoAttuatore', filtri.idSoggettoAttuatore);
    }
    if (filtri.sede) {
      queryParams = queryParams.append('luogo', filtri.sede);
    }
    if (filtri.dataDa) {
      let dataString = filtri.dataDa.toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric', });
      queryParams = queryParams.append('dataIncontroDa', dataString);
    }
    if (filtri.dataA) {
      let dataString = filtri.dataA.toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric', });
      queryParams = queryParams.append('dataIncontroA', dataString);
    }
    if (pageIndex || pageIndex == 0) {
      queryParams = queryParams.append('pageIndex', pageIndex);
    }
    if (pageSize) {
      queryParams = queryParams.append('pageSize', pageSize);
    }
    if (sort) {
      queryParams = queryParams.append("orderBy", sort.active)
      queryParams = queryParams.append("sortDirection", sort.direction)
    }
    return this.http.get<IncontroPreaccoglienzaRicerca>(this.configUrl.incontriPreaccoglienzaUrl, { params: queryParams });
  }

  getExportIncontriPartecipantiAnagrafica(idIncontriPreaccoglienza: Array<string>, codOperatore: string, idSoggettoAttuatore?: number): Observable<Blob> {
    let queryParams = new HttpParams();
    if (idIncontriPreaccoglienza)
      queryParams = queryParams.append("idIncontriPreaccoglienza", idIncontriPreaccoglienza.join(','));
    if (codOperatore)
      queryParams = queryParams.append("codOperatore", codOperatore);
    if (idSoggettoAttuatore)
      queryParams = queryParams.append("idSoggettoAttuatore", idSoggettoAttuatore)

    return this.http.get<any>(this.configUrl.getExportIncontriPartecipantiAnagrafica, { params: queryParams, responseType: 'blob' as 'json' })
  }

}
