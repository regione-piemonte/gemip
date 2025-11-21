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
import { BehaviorSubject, Observable } from 'rxjs';
import { AreaTerritoriale } from 'src/app/core/models/areaTerritoriale';
import { CittadinoIncontroPreaccoglienza } from 'src/app/core/models/cittadinoIncontroPreaccoglienza';
import { IncontroPreaccoglienza } from 'src/app/core/models/incontroPreaccoglienza';
import { ConfigUrlService } from 'src/app/core/services/config-url.service';
import { ManagedHttpClient } from 'src/app/core/services/managed-http-client.service';

@Injectable({
  providedIn: 'root'
})
export class IncontroPreaccoglienzaService {

  incontri: BehaviorSubject<IncontroPreaccoglienza[]> = new BehaviorSubject<IncontroPreaccoglienza[]>([])

  private _incontroPreaccoglienza?: CittadinoIncontroPreaccoglienza
  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  updateElencoIncontri(elenco: IncontroPreaccoglienza[]) {
    this.incontri.next(elenco);
  }

  getAreeTerritoriali(): Observable<AreaTerritoriale[]> {
    return this.http.get<AreaTerritoriale[]>(this.configUrl.areaTerritorialeUrl);
  }

  getIncontriForAreaTerritoriale(codAreaTerritoriale: string): Observable<IncontroPreaccoglienza[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("codAreaTerritoriale", codAreaTerritoriale);

    return this.http.get<IncontroPreaccoglienza[]>(this.configUrl.incontriPreaccoglienzaPerAreaTerritorialeUrl, { params: queryParams })
  }

  getIncontroForCittadino(idCittadino: string): Observable<CittadinoIncontroPreaccoglienza> {
    let queryParams = new HttpParams().append("id", idCittadino);
    return this.http.get<CittadinoIncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaForCittadino, { params: queryParams });
  }

  getIncontroPerCittadino(idCittadino: number): Observable<IncontroPreaccoglienza> {
    let queryParams = new HttpParams().append("idCittadino", idCittadino);
    return this.http.get<IncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaPerCittadino, { params: queryParams })
  }

  insertIncontroPreAccoglienzaSelezionato(citaddinoIncontro: CittadinoIncontroPreaccoglienza): Observable<CittadinoIncontroPreaccoglienza> {
    return this.http.post<CittadinoIncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaForCittadino, citaddinoIncontro);
  }

  updateIncontroPreAccoglienzaSelezionato(citaddinoIncontro: CittadinoIncontroPreaccoglienza, statoIdeaImpresa: string): Observable<CittadinoIncontroPreaccoglienza> {
    return this.http.put<CittadinoIncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaForCittadino, citaddinoIncontro, new HttpParams().append("statoIdeaImpresa",statoIdeaImpresa));
  }

  get cittadinoIncontroPreaccoglienza() {
    this._incontroPreaccoglienza = JSON.parse(sessionStorage.getItem("_incontroPreaccoglienza")!)
    return this._incontroPreaccoglienza;
  }

  set cittadinoIncontroPreaccoglienza(incontro: CittadinoIncontroPreaccoglienza | undefined) {
    this._incontroPreaccoglienza = incontro;
    sessionStorage.setItem("_incontroPreaccoglienza", JSON.stringify(this._incontroPreaccoglienza))
  }
}
