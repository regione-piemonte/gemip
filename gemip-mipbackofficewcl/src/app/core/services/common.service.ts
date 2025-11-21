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

import { Injectable } from '@angular/core';
import { ManagedHttpClient } from './managed-http-client.service';
import { ConfigUrlService } from './config-url.service';
import { Observable, of, tap } from 'rxjs';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { StatoIdeaDiImpresa } from '@core/models/statoIdeaDiImpresa';
import { Tutor } from '@core/models/tutor';
import { HttpParams } from '@angular/common/http';
import { FonteConoscenzaMip } from '@core/models/fonteConoscenzaMip';

@Injectable({
  providedIn: 'root'
})
export class CommonService {


  private _areeTerriList: AreaTerritoriale[] = []
  private _statoIdeaList: StatoIdeaDiImpresa[] = []
  private _fonteConoList: FonteConoscenzaMip[] = []


  constructor(
    private configUrl: ConfigUrlService,
    private http: ManagedHttpClient
  ) { }

  getAreeTerritoriali(): Observable<AreaTerritoriale[]> {
    if (this._areeTerriList.length)
      return of(this._areeTerriList)
    return this.http.get<AreaTerritoriale[]>(this.configUrl.areaTerritorialeUrl).pipe(
      tap((r) => this._areeTerriList = r)
    );
  }

  getStatiIdeaImpresa(): Observable<StatoIdeaDiImpresa[]> {
    if (this._statoIdeaList.length) {
      console.log("sono qui 1");
      return of(this._statoIdeaList);
    }
    console.log("sono qui 2");
    return this.http.get<StatoIdeaDiImpresa[]>(this.configUrl.statoImpresaUrl).pipe(
      tap((r) => this._statoIdeaList = r)
    );
  }

  getSoggettiAttuatoriByAreaTerritoriale(codAreaTerritoriale?: string): Observable<Tutor[]> {
    let queryParams = new HttpParams()
    if (codAreaTerritoriale) { queryParams = queryParams.append("codAreaTerritoriale", codAreaTerritoriale); }
    return this.http.get<Tutor[]>(this.configUrl.tutorByAreaUrl, { params: queryParams });
  }

  getFontiConoscenzaMip(): Observable<FonteConoscenzaMip[]> {
    if (this._fonteConoList.length)
      return of(this._fonteConoList)
    return this.http.get<FonteConoscenzaMip[]>(this.configUrl.fontiConoscenzaUrl).pipe(
      tap((r) => this._fonteConoList = r)
    );
  }
}
