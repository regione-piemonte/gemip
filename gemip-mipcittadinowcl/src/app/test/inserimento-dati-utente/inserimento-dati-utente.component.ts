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

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Cittadino } from 'src/app/core/models/cittadino';

@Component({
  selector: 'app-inserimento-dati-utente',
  templateUrl: './inserimento-dati-utente.component.html',
  styleUrls: ['./inserimento-dati-utente.component.scss']
})
export class InserimentoDatiUtenteComponent implements OnInit {

  constructor(
    private fb:FormBuilder,
    private router:Router
    ) { }
  formPerAntonio:FormGroup=this.fb.group(
    {
      codfisc:["",[Validators.maxLength(16),Validators.minLength(16),Validators.pattern("[a-zA-Z0-9]{9}[0-9]{2}[a-zA-Z0-9]{5}")]],
      nome:["",],
      cognome:["",],
    }
  )

  ngOnInit(): void {
  }
  click(){

    if(this.formPerAntonio.valid)
    {
      sessionStorage.setItem("cittadino",JSON.stringify(this.mockCittadino()));
      this.router.navigateByUrl("dashboard/home");
  }
  }
  mockCittadino():Cittadino{
    return {
      codiceFiscale:this.formPerAntonio.controls["codfisc"].value,
      nome:this.formPerAntonio.controls["nome"].value,
      cognome:this.formPerAntonio.controls["cognome"].value,
      idCittadino:undefined
    }
  }
}
