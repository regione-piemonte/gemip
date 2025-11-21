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
import { Sort } from '@angular/material/sort';
import { CittadinoIncontroPreaccoglienza } from '@core/models/cittadinoIncontroPreaccoglienza';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { IdeaDiImpresaIncontroPreaccoglienza } from '@core/models/ideaDiImpresaIncontroPreaccoglienza';
import { IncontroPreaccoglienza } from '@core/models/incontroPreaccoglienza';
import { IncontroPreaccoglienzaRicerca } from '@core/models/incontroPreaccoglienzaRicerca';
import { RowPreAccoglienza } from '@pre-accoglienza/components/pre-accoglienza-gestione/pre-accoglienza-gestione.component';
import { FiltroRicercaIdeaImpresa, FiltroRicercaPreAccoglienza } from '@shared/model/filtri-ricerca.model';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PreAccoglienzaBagService {
  private _incontroPreaccoglienza?: IncontroPreaccoglienza
  private _idIncontroPreaccoglienza?: number
  private _numeroIdeeAssociate: number = 0

  private pageSize: BehaviorSubject<number> = new BehaviorSubject<number>(5);
  private pageIndex: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  private filtriRicerca: BehaviorSubject<FiltroRicercaPreAccoglienza> = new BehaviorSubject<FiltroRicercaPreAccoglienza>({});
  private sort: BehaviorSubject<Sort | undefined> = new BehaviorSubject<Sort | undefined>(undefined);
  private incontriRicercati: BehaviorSubject<IncontroPreaccoglienzaRicerca> = new BehaviorSubject<IncontroPreaccoglienzaRicerca>({});
  private ideeImpresaIncontro: BehaviorSubject<CittadinoIncontroPreaccoglienza[]> = new BehaviorSubject<CittadinoIncontroPreaccoglienza[]>([{}]);

  _incontriRicercati = this.incontriRicercati as Observable<IncontroPreaccoglienzaRicerca>
  _sort = this.sort as Observable<Sort>
  _pageSize = this.pageSize as Observable<number>
  _pageIndex = this.pageIndex as Observable<number>
  _filtriRicerca = this.filtriRicerca as Observable<FiltroRicercaPreAccoglienza>
  _ideeImpresaIncontro = this.ideeImpresaIncontro as Observable<CittadinoIncontroPreaccoglienza[]>
  aggiornaOpertoriStampa: Subject<Boolean> = new Subject<Boolean>();

  leftComponent: boolean = false;

  constructor() { }
  resetBag() {
    this.pageIndex.next(0);
    this.pageSize.next(5);
    this.filtriRicerca.next({});
    this.sort.next(undefined);
    this.incontriRicercati.next({});
    this.ideeImpresaIncontro.next([]);
    this._incontroPreaccoglienza = undefined;
    this._idIncontroPreaccoglienza = undefined;
    this.numeroIdeeAssociate = 0;
  }
  updatePagination(pgS: number, psI: number) {
    this.pageIndex.next(psI);
    this.pageSize.next(pgS);
  }

  updateFiltri(filtri: FiltroRicercaPreAccoglienza) {
    this.filtriRicerca.next(filtri);
  }

  updateSort(sort: Sort) {
    this.sort.next(sort);
  }

  updateResultRicerca(ris: IncontroPreaccoglienzaRicerca) {
    this.incontriRicercati.next(ris);
  }

  get incontroPreaccoglienza() {
    return this._incontroPreaccoglienza;
  }

  set incontroPreaccoglienza(incontro: IncontroPreaccoglienza | undefined) {
    this._incontroPreaccoglienza = incontro;
  }

  get idIncontroPreaccoglienza() {
    return this._idIncontroPreaccoglienza;
  }

  set idIncontroPreaccoglienza(id: number | undefined) {
    this._idIncontroPreaccoglienza = id;
  }

  get numeroIdeeAssociate() {
    return this._numeroIdeeAssociate;
  }

  set numeroIdeeAssociate(num: number) {
    this._numeroIdeeAssociate = num;
  }

  getIdeeImpresaIncontro(): Observable<CittadinoIncontroPreaccoglienza[]> {
    return this.ideeImpresaIncontro;
  }

  // Setter for ideeImpresaIncontro
  setIdeeImpresaIncontro(idee: CittadinoIncontroPreaccoglienza[]) {
    this.ideeImpresaIncontro.next(idee);
  }

}
