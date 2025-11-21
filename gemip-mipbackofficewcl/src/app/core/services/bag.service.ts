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
import { Ruolo } from '@core/models/ruolo';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { UserInfo } from '@core/models/userInfo';
import { ErrorHandlerService } from './error-handler.service';

@Injectable({
  providedIn: 'root'
})
export class BagService {

  userInfo!: UserInfo;
  private _soggettittuatori: SoggettoAttuatore[] = []

  listCodRuoli = [
    "GEMIP_AFFIDATARIO_ALL",
    "GEMIP_REGIONALE_ALL",
    "GEMIP_REGIONALE_BASE",
    "GEMIP_CPI_BASE",
    "GEMIP_ATTUATORE_BASE",
  ];

  titolo = "home";
  icona = "home";
  constructor(
    private errorService: ErrorHandlerService
  ) { }

  resetError() {
    this.errorService.errorUrl = ""
  }
  isRuoloAdmin(): boolean {
    return this.ruolo.codiceRuolo == this.listCodRuoli[0] || this.ruolo.codiceRuolo == this.listCodRuoli[1]
  }

  get ruolo(): Ruolo {
    return JSON.parse(sessionStorage.getItem("_ruolo")!)
  }
  get soggettoAttuatore(): SoggettoAttuatore | undefined {
    return sessionStorage.getItem("soggettoAttuatore") ? JSON.parse(sessionStorage.getItem("soggettoAttuatore")!) : undefined
  }
  get soggettittuatori() {
    return this._soggettittuatori;
  }
  set soggettittuatori(soggetti: SoggettoAttuatore[]) {
    this._soggettittuatori = soggetti;
  }

}
