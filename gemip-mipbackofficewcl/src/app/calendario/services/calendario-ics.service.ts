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

import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Sort } from '@angular/material/sort';
import { EventoCalendarioRicerca } from '@core/models/eventoCalendarioRicerca';
import { FileIcs } from '@core/models/fileIcs';
import { StoricoCalendario } from '@core/models/storicoCalendario';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { FiltroRicercaEventoCalendario } from '@shared/model/filtri-ricerca.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CalendarioIcsService {

  constructor(
    private configUrl: ConfigUrlService,
    private http: ManagedHttpClient
  ) { }

  uploadFileIcs(fileIcs: FileIcs) {
    let formData: FormData = new FormData();
    formData.append("fileIcs", fileIcs.fileIcs as Blob)
    formData.append("nomeFile", fileIcs.nomeFile!)
    formData.append("descrizioneFile", fileIcs.descrizioneFile!)
    formData.append("codUserInserim", fileIcs.codUserInserim!)
    formData.append("idOperatore", fileIcs.idOperatore!.toString())
    formData.append("idSoggettoAttuatore", fileIcs.idSoggettoAttuatore!.toString())
    return this.http.post(this.configUrl.icsUrl, formData);
  }

  getEventiCalendario(idSoggettoAttuatore?: number): Observable<EventoCalendarioRicerca> {
    let queryParams: HttpParams = new HttpParams()
    if (idSoggettoAttuatore) { queryParams = queryParams.append("idSoggettoAttuatore", idSoggettoAttuatore); }
    return this.http.get<EventoCalendarioRicerca>(this.configUrl.calendarioEventiUrl, { params: queryParams });
  }

  ricercaEventiCalendario(filtri: FiltroRicercaEventoCalendario, pageIndex?: number, pageSize?: number, sort?: Sort): Observable<EventoCalendarioRicerca> {
    let queryParams: HttpParams = new HttpParams()
    if (filtri.idOperatore) {
      queryParams = queryParams.append("idOperatore", filtri.idOperatore);
    }
    if (filtri.idSoggettoAttuatore) {
      queryParams = queryParams.append("idSoggettoAttuatore", filtri.idSoggettoAttuatore);
    }
    if (filtri.dataDa) {
      let dataString = filtri.dataDa.toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric' });
      queryParams = queryParams.append("dataDa", dataString);
    }
    if (filtri.dataA) {
      let dataString = filtri.dataA.toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric' });
      queryParams = queryParams.append("dataA", dataString);
    }
    if (pageIndex || pageIndex == 0) {
      queryParams = queryParams.append("pageIndex", pageIndex)
    }
    if (pageSize) {
      queryParams = queryParams.append("pageSize", pageSize)
    }

    if (sort) {
      queryParams = queryParams.append("orderBy", sort.active)
      queryParams = queryParams.append("sortDirection", sort.direction)
    }

    return this.http.get<EventoCalendarioRicerca>(this.configUrl.calendarioEventiUrl, { params: queryParams });
  }

  getStoricoCaricamenti(idSoggettoAttuatore?: number): Observable<StoricoCalendario[]> {
    let queryParams: HttpParams = new HttpParams()
    if (idSoggettoAttuatore) {
      queryParams = queryParams.append("idSoggettoAttuatore", idSoggettoAttuatore)
    }
    return this.http.get<StoricoCalendario[]>(this.configUrl.storicoCalendarioUrl, { params: queryParams })
  }

  downloadFileICs(idFile: number) {
    let queryParams: HttpParams = new HttpParams().append("idFileIcs", idFile)
    return this.http.getResponseType<Blob>(this.configUrl.downLoadIcsUrl, queryParams, 'blob')
  }

  getFileStoricoById(idFile: number): Observable<StoricoCalendario> {
    let queryParams: HttpParams = new HttpParams().append("idFileIcs", idFile)
    return this.http.get<StoricoCalendario>(this.configUrl.icsUrl, { params: queryParams })
  }

  updateFileIcs(file: FileIcs) {
    return this.http.put(this.configUrl.icsUrl, file)
  }
}
