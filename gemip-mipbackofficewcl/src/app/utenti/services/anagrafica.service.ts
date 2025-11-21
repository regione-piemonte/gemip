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
import { Observable } from 'rxjs';
import { AnagraficaCittadino } from '@core/models/anagraficaCittadino';
import { Cittadinanza } from '@core/models/cittadinanza';
import { Comune } from '@core/models/comune';
import { CondizioneOccupazionale } from '@core/models/condizioneOccupazionale';
import { Provincia } from '@core/models/provincia';
import { StatoEstero } from '@core/models/statoEstero';
import { SvantaggioAbitativo } from '@core/models/svantaggioAbitativo';
import { TitoloDiStudio } from '@core/models/titoloDiStudio';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { CondizioneFamiliare } from '@core/models/condizioneFamiliare';


@Injectable({
  providedIn: 'root'
})
export class AnagraficaService {
  private _anagraficaCittadino?: AnagraficaCittadino;
  constructor(private http: ManagedHttpClient,
    private configUrl: ConfigUrlService) { }

  getComuni(): Observable<Comune[]> {
    return this.http.get<Comune[]>(this.configUrl.comuniUrl);
  }

  getProvince(): Observable<Provincia[]> {
    return this.http.get<Provincia[]>(this.configUrl.provinceUrl);
  }

  getComuniForProvincia(codProv: string): Observable<Comune[]> {
    let queryParams = new HttpParams().append("provincia", codProv);
    return this.http.get<Comune[]>(this.configUrl.comuniByProvinciaUrl, { params: queryParams });
  }

  getStatiEsteri(): Observable<StatoEstero[]> {
    return this.http.get<StatoEstero[]>(this.configUrl.statiEsteriUrl);
  }
  getCittadinanze(): Observable<Cittadinanza[]> {
    return this.http.get<Cittadinanza[]>(this.configUrl.cittadinanzeUrl);
  }


  getTitoliDiStudio(): Observable<TitoloDiStudio[]> {
    return this.http.get<TitoloDiStudio[]>(this.configUrl.titoloDIstudioUrl);
  }

  getCondizioniOccupazionali(): Observable<CondizioneOccupazionale[]> {
    return this.http.get<CondizioneOccupazionale[]>(this.configUrl.condizioneOccUrl);
  }

  public getCondizioniFamiliare(): Observable<CondizioneFamiliare[]> {
    return this.http.get<CondizioneFamiliare[]>(this.configUrl.condizioneFamiliareUrl);
  }
  getSvantaggiAbitativi(): Observable<SvantaggioAbitativo[]> {
    return this.http.get<SvantaggioAbitativo[]>(this.configUrl.svantaggiAbitativiUrl);
  }

  getAnagraficaById(idCittadino: number): Observable<AnagraficaCittadino> {
    let queryParams = new HttpParams().append("idAnagraficaCittadino", idCittadino)
    return this.http.get<AnagraficaCittadino>(this.configUrl.anagraficaUrl, { params: queryParams });
  }
  insertAnagrafica(anagrafica: AnagraficaCittadino): Observable<AnagraficaCittadino> {
    return this.http.post<AnagraficaCittadino>(this.configUrl.anagraficaUrl, anagrafica);
  }

  updateAnagrafica(anagrafica: AnagraficaCittadino): Observable<AnagraficaCittadino> {
    return this.http.put<AnagraficaCittadino>(this.configUrl.anagraficaUrl, anagrafica);
  }

  get anagraficaCittadino() {
    return this._anagraficaCittadino;
  }

  set anagraficaCittadino(anagraficaCittadino: AnagraficaCittadino | undefined) {
    this._anagraficaCittadino = anagraficaCittadino;
  }
}
