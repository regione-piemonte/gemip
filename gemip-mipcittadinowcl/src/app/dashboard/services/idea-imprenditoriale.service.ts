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
import { DashboardCittadino } from 'src/app/core/models/dashboardCittadino';
import { IdeaDiImpresa } from 'src/app/core/models/ideaDiImpresa';
import { ConfigUrlService } from 'src/app/core/services/config-url.service';
import { ManagedHttpClient } from 'src/app/core/services/managed-http-client.service';

@Injectable({
  providedIn: 'root'
})
export class IdeaImprenditorialeService {
  private _ideaDiImpresa?: IdeaDiImpresa;

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }


  getIdeaDiImpresaById(idIdeaDiImpresa: string): Observable<IdeaDiImpresa> {
    let queryParams = new HttpParams().append("id", idIdeaDiImpresa);
    return this.http.get<IdeaDiImpresa>(this.configUrl.ideaDiImpresaUrl, { params: queryParams });
  }

  getIdeaDiImpresaByIdCittadino(idCittadino: number): Observable<any> {
    let queryParams = new HttpParams().append("idCittadino", idCittadino);
    return this.http.get<any>(this.configUrl.ideaDiImpresaByCittadinoUrl, { params: queryParams });
  }

  insertIdeaDiImpresa(ideaDiImpresa: IdeaDiImpresa, idCittadino: string): Observable<IdeaDiImpresa> {
    let queryParams = new HttpParams().append("idCittadino", idCittadino);
    return this.http.post<IdeaDiImpresa>(this.configUrl.ideaDiImpresaUrl, ideaDiImpresa, queryParams);
  }

  updateIdeaDiImpresa(ideaDiImpresa: IdeaDiImpresa): Observable<IdeaDiImpresa> {

    return this.http.put<IdeaDiImpresa>(this.configUrl.ideaDiImpresaUrl, ideaDiImpresa);
  }

  inviaDati(dashboard: DashboardCittadino): Observable<DashboardCittadino> {
    return this.http.put<DashboardCittadino>(this.configUrl.inviaDatiUrl, dashboard);
  }

  get ideaDiImpresa() {
    return JSON.parse(sessionStorage.getItem("ideaImpresa")!)
  }

  set ideaDiImpresa(ideaDiImpresa: IdeaDiImpresa | undefined) {
    sessionStorage.setItem("ideaImpresa", JSON.stringify(ideaDiImpresa))
    this._ideaDiImpresa = ideaDiImpresa
  }
}
