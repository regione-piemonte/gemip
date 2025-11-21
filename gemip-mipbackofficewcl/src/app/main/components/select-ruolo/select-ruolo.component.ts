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
import { FormControl, Validators } from '@angular/forms';
import { BagService } from '@core/services/bag.service';
import { UserInfo } from '@core/models/userInfo';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { InfoService } from '@main/services/info.service';

@Component({
  selector: 'app-select-ruolo',
  templateUrl: './select-ruolo.component.html',
  styleUrls: ['./select-ruolo.component.scss']
})
export class SelectRuoloComponent implements OnInit {

  userInfo!: UserInfo
  soggettiAttuatori: SoggettoAttuatore[] = []
  viewProfilo = true;

  constructor(
    private bagService: BagService,
    private router: Router,
    private infoService: InfoService,
  ) { }

  ngOnInit() {
    this.bagService.titolo = "selezionare l'ente con cui accedere"
    setTimeout(() => {
      this.soggettiAttuatori = this.bagService.soggettittuatori
    }, 500);
    this.infoService.getUserInfo().subscribe(
      response => {
        this.userInfo = response;
      }
    );
  }

  formSoggetti: FormControl = new FormControl('', Validators.required);

  onConfermaSoggetto() {
    if (this.formSoggetti.valid) {
      console.log(this.formSoggetti.value)
      console.log(this.soggettiAttuatori)
      sessionStorage.setItem("soggettoAttuatore", JSON.stringify(this.soggettiAttuatori.filter(s => s.id == this.formSoggetti.value)[0]))
      this.router.navigateByUrl('home/2')
    }
  }

}
