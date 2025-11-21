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
import { Ente } from '@core/models/ente';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { Operatore } from '@core/models/operatore';
import { SoggettoAffidatario } from '@core/models/soggettoAffidatario';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { Tutor } from '@core/models/tutor';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { RowSogg } from '@utenti/components/soggetto-attuator-gestione/soggetto-attuator-gestione.component';
import { Observable, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SoggettoAttuatoreService {

  private _soggAttList: SoggettoAttuatore[] = []
  private _soggAffList: SoggettoAttuatore[] = []
  private _soggRegList: SoggettoAttuatore[] = []
  private _soggAplList: SoggettoAttuatore[] = []

  soggAtt?: SoggettoAttuatore | SoggettoAffidatario | Ente;
  isAffidatario = false;
  isAttuatore = false;
  canModify: boolean = true;

  leftComponent: boolean = true;
  filtriRicerca: any = {
    denom: "",
    email: "",
    tipo: "",
  };
  rowSoggetiAttList: RowSogg[] = []
  rowSoggetiAffList: RowSogg[] = []
  rowEntiRegioneList: RowSogg[] = []
  rowEntiAplList: RowSogg[] = []

  constructor(
    private configUrl: ConfigUrlService,
    private http: ManagedHttpClient
  ) {

  }

  public getTutorPerId(idTutor: number): Observable<Tutor> {
    if (idTutor === null || idTutor === undefined) {
      throw new Error('Required parameter idTutor was null or undefined when calling getTutorPerId.');
    }
    let queryParameters = new HttpParams();
    if (idTutor !== undefined && idTutor !== null) {
      queryParameters = queryParameters.set('idTutor', <any>idTutor);
    }
    return this.http.get<Tutor>(this.configUrl.tutorUrl,
      {
        params: queryParameters,
      }
    );
  }

  getSoggettiAttuatori(): Observable<SoggettoAttuatore[]> {

    return this.http.get<SoggettoAttuatore[]>(this.configUrl.soggettiAttuatoreUrl).pipe(tap(
      r => this._soggAttList = r
    ));
  }

  getOperatoriSoggAtt(idSoggettoAttuatore: string): Observable<Operatore[]> {
    let queryParams = new HttpParams().append("idSoggettoAttuatore", idSoggettoAttuatore);
    return this.http.get<Operatore[]>(this.configUrl.operatoreSoggUrl, { params: queryParams });
  }


  getSoggettiAffidatari(): Observable<SoggettoAffidatario[]> {

    return this.http.get<SoggettoAttuatore[]>(this.configUrl.soggettiAffidatarioUrl).pipe(tap(
      r => this._soggAffList = r
    ));
  }

  getOperatoriSoggAff(idSoggettoAttuatore: string): Observable<Operatore[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("idSoggettoAffidatario", idSoggettoAttuatore);
    return this.http.get<Operatore[]>(this.configUrl.operatoreSoggAffUrl, { params: queryParams });
  }

  getEnti(tipoEnte: string): Observable<Ente[]> {

    let queryParams = new HttpParams().append("tipoEnte", tipoEnte);
    return this.http.get<Ente[]>(this.configUrl.entiUrl, { params: queryParams }).pipe(tap(r => {
      r.forEach(el => el.denominazione = el.descrizioneEnte)
      if (tipoEnte == "regione")
        this._soggRegList = r;
      if (tipoEnte == "apl")
        this._soggAplList = r;
    }
    ));
  }

  getOperatoriEnti(idEnte: string): Observable<Operatore[]> {
    let queryParams = new HttpParams().append("idEnte", idEnte);
    return this.http.get<Operatore[]>(this.configUrl.OperatoriEntiUrl, { params: queryParams });
  }

  updateSoggettoAff(body: SoggettoAffidatario) {
    return this.http.put(this.configUrl.soggettiAffidatarioUrl, body)
  }

  updateSoggettoAtt(body: SoggettoAttuatore) {
    console.log(body)
    return this.http.put(this.configUrl.soggettiAttuatoreUrl, body)
  }

  updateEnte(body: Ente) {
    return this.http.put(this.configUrl.entiUrl, body)
  }

  resetTutor(idIdeaImpresa: number) {
    let params = new HttpParams();
    params = params.append('idIdeaImpresa', idIdeaImpresa);
    return this.http.delete(this.configUrl.resetTutorUrl, undefined, params);
  }

  sceltaTutor(idIdeaImpresa: number, idTutor: number): Observable<IdeaDiImpresa> {
    let params = new HttpParams();
    params = params.append('idImpresa', idIdeaImpresa);
    params = params.append('idTutor', idTutor);
    return this.http.put(this.configUrl.sceltaTutorUrl, undefined, params);
  }

}

