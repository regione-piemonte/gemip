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
import { AreaTerritoriale } from 'src/app/core/models/areaTerritoriale';
import { IdeaDiImpresa } from 'src/app/core/models/ideaDiImpresa';
import { Tutor } from 'src/app/core/models/tutor';
import { ConfigUrlService } from 'src/app/core/services/config-url.service';
import { ManagedHttpClient } from 'src/app/core/services/managed-http-client.service';

@Injectable({
  providedIn: 'root'
})
export class TutorService {

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService) { }

  getTutorAbilitati(codAreaTerritoriale: string, sbloccoAreaTerritoriale: boolean): Observable<Tutor[]> {
    let queryParams = new HttpParams();
    if (!sbloccoAreaTerritoriale && codAreaTerritoriale) {
      queryParams = queryParams.append("codAreaTerritoriale", codAreaTerritoriale);
    }

    return this.http.get<Tutor[]>(this.configUrl.tutorAbilitatiByAreaUrl, { params: queryParams });
  }

  selectTutor(idTutor: string, idImpresa: string): Observable<IdeaDiImpresa> {
    let queryParams = new HttpParams().append("idTutor", idTutor).append("idImpresa", idImpresa);

    return this.http.put<IdeaDiImpresa>(this.configUrl.sceltaTutorUrl, null, queryParams);
  }

  getTutorById(id: number): Observable<Tutor> {
    let queryParams = new HttpParams().append("idTutor", id);

    return this.http.get<Tutor>(this.configUrl.tutorUrl, { params: queryParams });
  }

  // /tutor/scelta-tutor

  getSoggettiAttuatoriByAreaTerritoriale(codAreaTerritoriale?: string): Observable<Tutor[]> {
    let queryParams = new HttpParams()
    if (codAreaTerritoriale) { queryParams = queryParams.append("codAreaTerritoriale", codAreaTerritoriale); }
    return this.http.get<Tutor[]>(this.configUrl.tutorAbilitatiByAreaUrl, { params: queryParams });
  }

  getAreeTerritoriali(): Observable<AreaTerritoriale[]> {
    return this.http.get<AreaTerritoriale[]>(this.configUrl.areaTerritorialeUrl)
  }
}
