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
import { FormBuilder, FormControl } from '@angular/forms';
import { IcsBagService } from '../../services/calendario-ics-bag.service';
import { CalendarioIcsService } from '../../services/calendario-ics.service';
import { StoricoCalendario } from '@core/models/storicoCalendario';
import { Router } from '@angular/router';
import { FileIcs } from '@core/models/fileIcs';
import { BagService } from '@core/services/bag.service';

@Component({
  selector: 'app-calendario-modifica',
  templateUrl: './calendario-modifica.component.html',
  styleUrls: ['./calendario-modifica.component.scss']
})
export class CalendarioModificaComponent implements OnInit {
  storico?:StoricoCalendario
  form = this.fb.group(
    {
      descrizione:[''],
      abilitato:new FormControl<boolean>(true)
    }
  )
  constructor(
    private fb:FormBuilder,
    private icsBagService:IcsBagService,
    private icsService:CalendarioIcsService,
    private router:Router,
    private bagService:BagService
    ) { }

  ngOnInit(): void {
   if(this.icsBagService._idFileIcs){
    this.icsService.getFileStoricoById(this.icsBagService._idFileIcs).subscribe({
      next:ris => {
        this.storico = ris
        this.form.controls.descrizione.setValue(ris.descrizioneFile!)
        this.form.controls.abilitato.setValue(!!ris.flgAbilitato)
      }
    })
   }
  }

  onAnnulla(){
    this.router.navigateByUrl("/calendario")
  }

  onSalva(){
    this.bagService.resetError()
    this.icsService.updateFileIcs(this.createFile()).subscribe({
      next:() => {this.router.navigateByUrl("/calendario")}
    })

  }

  createFile():FileIcs{
    return {
      idFileIcs:this.icsBagService._idFileIcs,
      nomeFile:this.storico?.nomeFile,
      idOperatore:this.storico?.operatoreInserimento?.id,
      idSoggettoAttuatore:this.storico?.soggettoAttuatore?.id,
      codUserInserim:this.storico?.codUserInserim,
      codUserAggiorn:this.bagService.userInfo.codFisc,
      dataInserim:this.storico?.dataInserim,
      descrizioneFile: this.form.controls.descrizione.value!,
      flgAbilitato: this.form.controls.abilitato.value!
    }
  }
}
