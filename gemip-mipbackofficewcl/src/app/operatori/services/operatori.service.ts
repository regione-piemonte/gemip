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

import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { Observable } from 'rxjs';

import { FiltroRicercaOperatore } from '@shared/model/filtri-ricerca.model';
import { Operatore } from '@core/models/operatore';

@Injectable({
  providedIn: 'root'
})
export class OperatoriService {

  operatore?: Operatore;
  canModify: boolean = true;
  leftComponent: boolean = true;
  filtriOperatori: FiltroRicercaOperatore = {};

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  ricercaOperatore(filtri: FiltroRicercaOperatore): Observable<Operatore[]> {
    let queryParams = new HttpParams();

    if (filtri.abilitato) {
      queryParams = queryParams.append("abilitato", filtri.abilitato);
    }
    if (filtri.idOperatore) {
      queryParams = queryParams.append("idOperatore", filtri.idOperatore);
    }
    if (filtri.soggetto) {
      queryParams = queryParams.append("soggetto", filtri.soggetto);
    }
    if (filtri.nome) {
      queryParams = queryParams.append("nome", filtri.nome);
    }
    if (filtri.cognome) {
      queryParams = queryParams.append("cognome", filtri.cognome);
    }
    if (filtri.email) {
      queryParams = queryParams.append("email", filtri.email);
    }
    if (filtri.codiceFiscale) {
      queryParams = queryParams.append("codiceFiscale", filtri.codiceFiscale);
    }
    if (filtri.idSoggettoAttuatore) {
      queryParams = queryParams.append("idSoggettoAttuatore", filtri.idSoggettoAttuatore);
    }

    return this.http.get<Operatore[]>(this.configUrl.ricercaOperatoreUrl, { params: queryParams });
  }


  updateOperartore(operatore: Operatore): Observable<Operatore> {
    return this.http.put<Operatore>(this.configUrl.operatoreUrl, operatore);
  }

  getPartnerOperatori(idOperatore: string): Observable<any> {
    let queryParams = new HttpParams().append('idOperatore', idOperatore);

    return this.http.get<any[]>(this.configUrl.soggettiAttuatorePerOperatoreUrl, { params: queryParams });
  }


}
