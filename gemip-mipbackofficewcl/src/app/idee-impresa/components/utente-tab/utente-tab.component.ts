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

import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AnagraficaCittadino } from '@core/models/anagraficaCittadino';
import { Cittadino } from '@core/models/cittadino';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { AnagraficaService } from '@utenti/services/anagrafica.service';
import { UtentiService } from '@utenti/services/utenti.service';

@Component({
  selector: 'app-utente-tab',
  templateUrl: './utente-tab.component.html',
  styleUrls: ['./utente-tab.component.scss']
})
export class UtenteTabComponent implements OnInit {
  @Input() isStampa = false

  cittadini!: Cittadino[];

  angrafiche: CittAn[] = [];
  constructor(
    private ideaImpService: IdeaImpresaService,
    private anagraficaService: AnagraficaService,
    private utenteService: UtentiService,
    private router: Router
  ) { }

  ngOnInit(): void {

    this.ideaImpService.getCittadinoByIdea(String(this.ideaImpService.ideeImp?.ideaDiImpresa?.id!))
      .subscribe(r => {
        this.cittadini = r
        this.cittadini.forEach(ci => {
          this.anagraficaService.getAnagraficaById(ci.idCittadino!).subscribe(r => {
            let tmpCittadino: Cittadino = ci;
            let tmpAnagrafica: AnagraficaCittadino = r
            let anagraficaCittadino: CittAn = {
              cittadino: tmpCittadino,
              angrafica: tmpAnagrafica
            }
            this.angrafiche.push(anagraficaCittadino);
          });
        })
      })
  }

  onModifica(idCittadino: number) {
    this.anagraficaService.getAnagraficaById(idCittadino).subscribe(r => {
      this.utenteService.anagraficaSelected = r;
      this.router.navigateByUrl('utenti/cittadino/form/' + idCittadino);
    });
  }
}

export interface CittAn {
  cittadino: Cittadino,
  angrafica: AnagraficaCittadino
}

