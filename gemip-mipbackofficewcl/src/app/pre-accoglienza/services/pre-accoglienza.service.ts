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
import { IdeaDiImpresaIncontroPreaccoglienza } from '@core/models/ideaDiImpresaIncontroPreaccoglienza';
import { IncontroPreAccoglienzaInsert } from '@core/models/incontroPreAccoglienzaInsert';
import { IncontroPreaccoglienza } from '@core/models/incontroPreaccoglienza';
import { IncontroPreaccoglienzaAreaTerritoriale } from '@core/models/incontroPreaccoglienzaAreaTerritoriale';
import { IncontroPreaccoglienzaRicerca } from '@core/models/incontroPreaccoglienzaRicerca';
import { LuogoIncontro } from '@core/models/luogoIncontro';
import { Operatore } from '@core/models/operatore';
import { OperatoreIncontroPreaccoglienza } from '@core/models/operatoreIncontroPreaccoglienza';
import { ResgistraPresenze } from '@core/models/resgistraPresenze';
import { SoggettoAffidatario } from '@core/models/soggettoAffidatario';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { FiltroRicercaPreAccoglienza } from '@shared/model/filtri-ricerca.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class PreAccoglienzaService {
  canModify: boolean = true;

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  insertIncontroPreAccoglienza(
    incontro: IncontroPreAccoglienzaInsert
  ): Observable<IncontroPreAccoglienzaInsert> {
    return this.http.post<IncontroPreAccoglienzaInsert>(
      this.configUrl.incontroPreAccoglienzaUrl,
      incontro
    );
  }

  updateIncontroPreaccoglienza(
    incontro: IncontroPreAccoglienzaInsert
  ): Observable<IncontroPreAccoglienzaInsert> {
    return this.http.put<IncontroPreAccoglienzaInsert>(
      this.configUrl.incontroPreAccoglienzaUrl,
      incontro
    );
  }

  deleteIncontroPreaccoglienza(idIncontro: number) {
    let queryParams = new HttpParams().append(
      'idIncontroPreaccoglienza',
      idIncontro
    );
    return this.http.delete(
      this.configUrl.incontroPreAccoglienzaUrl,
      undefined,
      queryParams
    );
  }
  associaIncontroAreaTerritoriale(
    incontroArea: IncontroPreaccoglienzaAreaTerritoriale
  ): Observable<IncontroPreaccoglienzaAreaTerritoriale> {
    return this.http.post<IncontroPreaccoglienzaAreaTerritoriale>(
      this.configUrl.associaIncontriAreaTerritorialeUrl,
      incontroArea
    );
  }
  getLuoghiIncontro(): Observable<LuogoIncontro[]> {
    return this.http.get<LuogoIncontro[]>(
      this.configUrl.luoghiAreaTerittorialeUrl
    );
  }

  getIncontroById(id: number): Observable<IncontroPreAccoglienzaInsert> {
    let queryParams = new HttpParams().append('id', id);
    return this.http.get<IncontroPreAccoglienzaInsert>(
      this.configUrl.incontroPreAccoglienzaUrl,
      { params: queryParams }
    );
  }

  getIdeeDiImpresaAssociate(
    id: number, pageIndex?: number, pageSize?: number
  ): Observable<IdeaDiImpresaIncontroPreaccoglienza> {

    let queryParams = new HttpParams();
    queryParams = queryParams.append('idIncontroPreaccoglienza', id);
    if (pageIndex != null) queryParams = queryParams.append('pageIndex', pageIndex);
    if (pageSize != null) queryParams = queryParams.append('pageSize', pageSize);
    return this.http.get<IdeaDiImpresaIncontroPreaccoglienza>(
      this.configUrl.ideeDiImpresaPerIncontroUrl,
      { params: queryParams }
    );
  }

  getOperatoriAssociati(id: number): Observable<Operatore[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append('idIncontroPreacc', id);
    return this.http.get<Operatore[]>(
      this.configUrl.operatoriInocntroPreaccoglienzaUrl,
      { params: queryParams }
    );
  }

  getOperatoriSoggettoAffiddatario(idSoggettoAffidatario?: number): Observable<Operatore[]> {
    if (idSoggettoAffidatario) {
      let queryParams = new HttpParams();
      queryParams = queryParams.append('idSoggettoAffidatario', idSoggettoAffidatario);
      return this.http.get<Operatore[]>(
        this.configUrl.operatoriSoggettiAffidatariUrl, { params: queryParams }
      );
    } else {
      return this.http.get<Operatore[]>(
        this.configUrl.operatoriSoggettiAffidatariUrl
      );
    }
  }

  registraPresenze(body: ResgistraPresenze[]) {
    return this.http.put(this.configUrl.registraPresenzeUrl, body);
  }
  associaOperatoreIncontro(
    operatore: OperatoreIncontroPreaccoglienza
  ): Observable<Operatore> {
    return this.http.post<Operatore>(
      this.configUrl.operatoriInocntroPreaccoglienzaUrl,
      operatore
    );
  }

  disassociaOperatoreIncontro(
    idOperatore: number,
    idIncontroPreaccoglienza: number
  ): Observable<string> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append(
      'idIncontroPreaccoglienza',
      idIncontroPreaccoglienza
    );
    queryParams = queryParams.append('idOperatore', idOperatore);
    return this.http.delete<string>(
      this.configUrl.operatoriInocntroPreaccoglienzaUrl,
      undefined,
      queryParams
    );
  }

  ricercaIncontri(
    filtri: FiltroRicercaPreAccoglienza,
    pageIndex?: number,
    pageSize?: number, sort?: Sort
  ): Observable<IncontroPreaccoglienzaRicerca> {
    let queryParams = new HttpParams();
    if (filtri.areaTerritoriale) {
      queryParams = queryParams.append(
        'codAreaTerritoriale',
        filtri.areaTerritoriale
      );
    }
    if (filtri.codFiscOperatore) {
      queryParams = queryParams.append('idOperatore', filtri.codFiscOperatore);
    }
    if (filtri.sede) {
      queryParams = queryParams.append('luogo', filtri.sede);
    }
    if (filtri.dataDa) {
      let dataString = filtri.dataDa.toLocaleString('it-IT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
      });
      queryParams = queryParams.append('dataIncontroDa', dataString);
    }
    if (filtri.dataA) {
      let dataString = filtri.dataA.toLocaleString('it-IT', {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
      });
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
    return this.http.get<IncontroPreaccoglienzaRicerca>(
      this.configUrl.incontriPreaccoglienzaUrl,
      { params: queryParams }
    );
  }

  inviaComunicazione(
    idIncontroPreaccoglienza: number,
    oggetto: string,
    corpo: string,
    idCittadino?: number
  ): Observable<IncontroPreaccoglienzaRicerca> {
    let queryParams = new HttpParams();
    if (
      idIncontroPreaccoglienza !== undefined &&
      idIncontroPreaccoglienza !== null
    ) {
      queryParams = queryParams.append(
        'idIncontroPreaccoglienza',
        idIncontroPreaccoglienza
      );
    }
    if (oggetto !== undefined && oggetto !== null) {
      queryParams = queryParams.append('oggetto', oggetto);
    }
    if (corpo !== undefined && corpo !== null) {
      queryParams = queryParams.append('corpo', corpo);
    }
    if (idCittadino) queryParams = queryParams.append('idCittadino', idCittadino)
    return this.http.post<IncontroPreaccoglienzaRicerca>(
      this.configUrl.incontroPreaccoglienzaInviaComunicato,
      {}, queryParams
    );
  }

  exportPdfPresenze(idIncontroPreaccoglienza: number): Observable<Blob> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("idIncontroPreaccoglienza", idIncontroPreaccoglienza);
    return this.http.getResponseType<Blob>(this.configUrl.pdfPresenzeUrl, queryParams, 'blob')
  }

  getOperatoriSoggAffidatarioById(idOperatore: number): Observable<SoggettoAffidatario[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("idOperatore", idOperatore);
    return this.http.get<SoggettoAffidatario[]>(this.configUrl.OperatoriSoggAffidatarioById, { params: queryParams });
  }

  getIncontriPreaccoglienzaForControlloEsistente(idLuogo: number, dataInizio: Date): Observable<IncontroPreaccoglienza> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("idLuogo", idLuogo);
    queryParams = queryParams.append("dataIncontro", dataInizio.toLocaleString("it-IT", {
      year: "numeric",
      month: "numeric",
      day: "numeric",
      hour: "numeric",
      minute: "numeric",
      second: "numeric",
    }));
    return this.http.get<IncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaControlloEsistenzaIncontro, { params: queryParams });
  }
}
