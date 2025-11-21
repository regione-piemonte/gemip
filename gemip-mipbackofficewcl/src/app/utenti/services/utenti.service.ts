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

import { AnagraficaCittadino } from '@core/models/anagraficaCittadino';
import { BehaviorSubject, Observable } from 'rxjs';

import { FiltroRicercaUtentiCittadino } from '@shared/model/filtri-ricerca.model'
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { AnagraficaCittadinoRicerca } from '@core/models/anagraficaCittadinoRicerca';
import { Sort } from '@angular/material/sort';


@Injectable({
  providedIn: 'root'
})
export class UtentiService {
  anagraficaSelected!: AnagraficaCittadino;
  private filtriCIttadino: BehaviorSubject<FiltroRicercaUtentiCittadino> = new BehaviorSubject<FiltroRicercaUtentiCittadino>({});
  _filtriCittadino: Observable<FiltroRicercaUtentiCittadino> = this.filtriCIttadino as Observable<FiltroRicercaUtentiCittadino>;

  leftComponent: boolean = true;
  filtriCittadino: FiltroRicercaUtentiCittadino = {};

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  updateFiltri(filtri: any) {
    this.filtriCIttadino.next(filtri);
  }
  ricercaUtentiCittadino(filtri: FiltroRicercaUtentiCittadino, pageIndex: number, pageSize: number, sort?: Sort): Observable<AnagraficaCittadinoRicerca> {
    let queryParams = new HttpParams();
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
    if (sessionStorage.getItem("soggettoAttuatore")) {
      let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
      queryParams = queryParams.append("idSoggettoAttuatore", sogg.id!);
    }
    queryParams = queryParams.append("pageIndex", pageIndex);
    queryParams = queryParams.append("pageSize", pageSize);
    if (sort) {
      queryParams = queryParams.append("orderBy", sort.active)
      queryParams = queryParams.append("sortDirection", sort.direction)
    }
    return this.http.get<AnagraficaCittadinoRicerca>(this.configUrl.cittadiniUrl, { params: queryParams });
  }

}
