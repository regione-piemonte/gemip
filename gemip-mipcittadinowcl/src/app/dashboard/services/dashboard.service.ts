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
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { NavBarItem } from '../models/navbar-item.model';

import { ManagedHttpClient } from 'src/app/core/services/managed-http-client.service';

import { ConfigUrlService } from 'src/app/core/services/config-url.service';

import { Cittadino } from 'src/app/core/models/cittadino';
import { DashboardCittadino } from 'src/app/core/models/dashboardCittadino';
import { MENU_DASHBOARD_LIST } from '../models/navbar-items';

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private _dashboard?: DashboardCittadino;
  private _cittadino?: Cittadino
  private user: BehaviorSubject<Cittadino> = new BehaviorSubject<Cittadino>({});
  _user = this.user as Observable<Cittadino>;
  lista: BehaviorSubject<NavBarItem[]> = new BehaviorSubject<NavBarItem[]>([...MENU_DASHBOARD_LIST]);
  modificaContatti: Subject<boolean> = new Subject<boolean>;

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  updateListaPrimafase(index: any) {
    let tmp: NavBarItem[] = [];
    this.lista.subscribe({ next: ris => { tmp = ris } });
    tmp[index].completed = true;
    if (index < 3) { tmp[index + 1].enabled = true }
    this.lista.next(tmp)
  }

  updateUser(user: Cittadino) {
    this.user.next(user)
  }

  clearLista() {
    this.lista.next([...MENU_DASHBOARD_LIST])
  }

  verificaDashboard(cittadino: Cittadino): Observable<DashboardCittadino> {
    return this.http.post<DashboardCittadino>(this.configUrl.verificaDashboardUrl, cittadino)
  }

  get cittadino() {
    return this._cittadino;
  }

  set cittadino(cittadino: Cittadino | undefined) {
    this._cittadino = cittadino;
  }
  get dashboard() {
    return this._dashboard;
  }

  set dashboard(dashboard: DashboardCittadino | undefined) {
    this._dashboard = dashboard;
  }
}
