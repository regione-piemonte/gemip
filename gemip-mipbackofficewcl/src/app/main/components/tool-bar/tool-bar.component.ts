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

import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { BagService } from '@core/services/bag.service';
import { InfoService } from '@main/services/info.service';
import { UserInfo } from '@core/models/userInfo';
import { Ruolo } from '@core/models/ruolo';
import { environment } from '@environment/environment';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';

@Component({
  selector: 'app-tool-bar',
  templateUrl: './tool-bar.component.html',
  styleUrls: ['./tool-bar.component.scss']
})
export class ToolBarComponent implements OnInit {

  userInfo?: UserInfo;
  ruolo?: Ruolo;
  soggettiAttuatori: SoggettoAttuatore[] = []
  constructor(
    private router: Router,
    private infoService: InfoService,
    private bagService: BagService
  ) { }

  ngOnInit(): void {
    this.infoService.getUserInfo()
      .subscribe(r => {
        this.userInfo = this.bagService.userInfo = r
        this.infoService.getRuoli(r.idOperatore!)
          .subscribe(r => {
            this.ruolo = r
            if (r.codiceRuolo == "GEMIP_ATTUATORE_BASE") {
              this.infoService.getSoggettiAttuatoriForOperatore(this.userInfo?.idOperatore!).subscribe({
                next: soggetti => {
                  this.riordinaSoggettiAttuatori(soggetti);
                  this.soggettiAttuatori = soggetti;
                  this.bagService.soggettittuatori = soggetti;
                  if (!sessionStorage.getItem("soggettoAttuatore")) {
                    if (soggetti.length > 1) {
                      this.router.navigateByUrl("/home/1")
                    } else {
                      sessionStorage.setItem("soggettoAttuatore", JSON.stringify(soggetti[0]))
                    }
                  }
                }
              })
            }
          })
      })
  }

  logOut() {

    this.infoService.clear()
    sessionStorage.clear();
    localStorage.clear();
    window.location.href = window.location.origin + environment.logoutUrl
  }

  cambiaSoggetto() {
    sessionStorage.removeItem("soggettoAttuatore")
    this.router.navigateByUrl("/home/1")
  }
  get titolo() {
    return this.bagService.titolo;
  }
  get icona() {
    return this.bagService.icona;
  }

  get isSoggettoAttuatore() {
    return this.soggettiAttuatori.length > 1
  }

  riordinaSoggettiAttuatori(soggetti: SoggettoAttuatore[]) {
    soggetti.sort((a, b) => {
      const keyA = (a.denominazione || '') || (a.codiceAreaTerritoriale || '');
      const keyB = (b.denominazione || '') || (b.codiceAreaTerritoriale || '');
      if (keyA < keyB) return -1;
      if (keyA > keyB) return +1;
      return 0;
    });
  }

  onDownloadManuale() {
    const url = '/assets/manuale/MIP Operatori - Manuale d’uso.pdf';
    const link = document.createElement('a');
    link.href = url;
    link.target = '_blank';
    link.download = 'MIP Operatori - Manuale d’uso.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

}
