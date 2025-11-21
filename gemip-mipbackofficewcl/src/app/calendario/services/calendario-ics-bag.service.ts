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
import { EventoCalendarioRicerca } from '@core/models/eventoCalendarioRicerca';


import { FiltroRicercaEventoCalendario} from '@shared/model/filtri-ricerca.model';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IcsBagService {

  private pageSize: BehaviorSubject<number> = new BehaviorSubject<number>(5);
  private pageIndex: BehaviorSubject<number> = new BehaviorSubject<number>(0);
  private filtriRicerca: BehaviorSubject<FiltroRicercaEventoCalendario> = new BehaviorSubject<FiltroRicercaEventoCalendario>({});
  private sort: BehaviorSubject<Sort|undefined> = new BehaviorSubject<Sort|undefined>(undefined);
  private idFileics?: number;
  private resultSet: BehaviorSubject<EventoCalendarioRicerca> = new BehaviorSubject<EventoCalendarioRicerca>({});


  _sort = this.sort as Observable<Sort>
  _pageSize = this.pageSize as Observable<number>
  _pageIndex = this.pageIndex as Observable<number>
  _filtriRicerca = this.filtriRicerca as Observable<FiltroRicercaEventoCalendario>
  _resultSet = this.resultSet as Observable<EventoCalendarioRicerca>

  constructor() { }

  updatePagination(pgS: number, psI: number) {
    this.pageIndex.next(psI);
    this.pageSize.next(pgS);
  }

  updateFiltri(filtri: FiltroRicercaEventoCalendario) {
    this.filtriRicerca.next(filtri);
  }

  updateSort(sort: Sort) {
    this.sort.next(sort);
  }

  updateRisultatoRicerca(ris: EventoCalendarioRicerca) {
    this.resultSet.next(ris);
  }

  get _idFileIcs() {
    return this.idFileics
  }

  set _idFileIcs(id: number | undefined) {
    this.idFileics = id
  }
}
