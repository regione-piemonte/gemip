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

import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { ConfigUrlService } from './../../core/services/config-url.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpParams } from '@angular/common/http';
import { IdeaDiImpresaRicerca } from '@core/models/ideaDiImpresaRicerca';
import { Cittadino } from '@core/models/cittadino';
import { IncontroPreAccoglienzaInsert } from '@core/models/incontroPreAccoglienzaInsert';
import { FiltroRicercaIdeaImpresa } from '@shared/model/filtri-ricerca.model';
import { IdeaDiImpresaRicercaConTotale } from '@core/models/ideaDiImpresaRicercaConTotale';
import { Sort } from '@angular/material/sort';
import { IncontroPreaccoglienza } from '@core/models/incontroPreaccoglienza';
import { CittadinoIncontroPreaccoglienza } from '@core/models/cittadinoIncontroPreaccoglienza';

@Injectable({
  providedIn: 'root'
})
export class IdeaImpresaService {


  ideeImp?: IdeaDiImpresaRicerca;
  private pageSize: BehaviorSubject<number> = new BehaviorSubject<number>(5);
  private pageIndex: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  private filtriRicerca: BehaviorSubject<FiltroRicercaIdeaImpresa> = new BehaviorSubject<FiltroRicercaIdeaImpresa>({});
  private sort: BehaviorSubject<Sort | undefined> = new BehaviorSubject<Sort | undefined>(undefined);
  canRegBaseModify: boolean = true;
  _sort = this.sort as Observable<Sort>
  _pageSize = this.pageSize as Observable<number>
  _pageIndex = this.pageIndex as Observable<number>
  _filtriRicerca = this.filtriRicerca as Observable<FiltroRicercaIdeaImpresa>

  leftComponent: boolean = true;

  constructor(
    private configUrl: ConfigUrlService,
    private http: ManagedHttpClient
  ) { }
  updatePagination(pgS: number, psI: number) {
    this.pageIndex.next(psI);
    this.pageSize.next(pgS);
  }

  updateFiltri(filtri: FiltroRicercaIdeaImpresa) {
    this.filtriRicerca.next(filtri);
  }
  updateSort(sort: Sort | undefined) {
    this.sort.next(sort);
  }
  getIdeaImpresaById(id: string): Observable<IdeaDiImpresa> {
    let queryParams = new HttpParams().append("id", id);
    return this.http.get<IdeaDiImpresa>(this.configUrl.ideaDiImpresaUrl, { params: queryParams });
  }

  putIdeaImpresa(ideaImpresa: IdeaDiImpresa): Observable<IdeaDiImpresa> {
    return this.http.put<IdeaDiImpresa>(this.configUrl.ideaDiImpresaUrl, ideaImpresa);
  }

  getIdeaImpresaByIdIncontroPreAccoglienza(idIncontroPreaccoglienza: string): Observable<IdeaDiImpresa> {
    let queryParams = new HttpParams().append("idIncontroPreaccoglienza", idIncontroPreaccoglienza);
    return this.http.get<IdeaDiImpresa>(this.configUrl.ideeDiImpresaUrl, { params: queryParams });
  }

  //Idea di impresa per cittadino --
  getIdeaImpresaByIdCittadino(idCittadino: string): Observable<IdeaDiImpresaRicerca> {
    let queryParams = new HttpParams().append("idCittadino", idCittadino);
    return this.http.get<IdeaDiImpresaRicerca>(this.configUrl.ideeDiImpresaPerCittadinoUrl, { params: queryParams });
  }

  getIdeaImpresaByFilter(filter: any, pageIndex: number, pageSize: number, sort?: Sort): Observable<IdeaDiImpresaRicercaConTotale> {
    let queryParams = new HttpParams();
    Object.keys(filter).forEach(key => {
      if (filter[key] &&
        filter[key] != "undefined" && filter[key] != undefined &&
        filter[key] != null && filter[key] != "null") {

        if (key == "dataInseritaDa" || key == "dataInseritaA") {
          let dataString = new Date(filter[key]).toLocaleString('it-IT', { day: '2-digit', month: '2-digit', year: 'numeric' });
          queryParams = queryParams.append(key, dataString)
        }
        else {
          queryParams = queryParams.append(key, filter[key])
        }
      }
    });
    queryParams = queryParams.append("pageIndex", pageIndex);
    queryParams = queryParams.append("pageSize", pageSize);
    if (sort) {
      queryParams = queryParams.append("orderBy", sort.active)
      queryParams = queryParams.append("sortDirection", sort.direction)
    }
    return this.http.get<IdeaDiImpresaRicercaConTotale>(this.configUrl.ricercaIdeeDiImpresaUrl, { params: queryParams });
  }


  // /idee-di-impresa/fonti-conoscenza-mip


  getCittadinoByIdea(idIdeaDiImpresa: string): Observable<Cittadino[]> {
    let queryParams = new HttpParams().append("idIdeaDiImpresa", idIdeaDiImpresa);
    // /cittadini/cittadino-idea.impresa

    return this.http.get<Cittadino[]>(this.configUrl.cittadiniByIdeaUrl, { params: queryParams });
  }

  getIncontroByIdea(idIdeaImpresa: string): Observable<IncontroPreAccoglienzaInsert> {
    let queryParams = new HttpParams().append("idIdeaImpresa", idIdeaImpresa);
    return this.http.get<IncontroPreAccoglienzaInsert>(this.configUrl.incontroByIdeaUrl, { params: queryParams });
  }

  getIncontriForAreaTerritoriale(codAreaTerritoriale: string): Observable<IncontroPreaccoglienza[]> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("codAreaTerritoriale", codAreaTerritoriale);
    queryParams = queryParams.append("completo", true);

    return this.http.get<IncontroPreaccoglienza[]>(this.configUrl.incontriPreaccoglienzaPerAreaTerritorialeUrl, { params: queryParams })
  }
  updateCittadinoIncontroPreaccoglienza(cittadinoIncontroPreaccoglienza: CittadinoIncontroPreaccoglienza): Observable<CittadinoIncontroPreaccoglienza> {
    return this.http.put<CittadinoIncontroPreaccoglienza>(this.configUrl.incontroPreaccoglienzaForCittadino, cittadinoIncontroPreaccoglienza);
  }
}
