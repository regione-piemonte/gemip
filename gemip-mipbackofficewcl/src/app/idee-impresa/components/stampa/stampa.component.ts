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
import { Cittadino } from '@core/models/cittadino';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { StatoIdeaDiImpresa } from '@core/models/statoIdeaDiImpresa';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { AnagraficaService } from '@utenti/services/anagrafica.service';
import { CittAn } from '../utente-tab/utente-tab.component';

@Component({
  selector: 'stampa-idea',
  templateUrl: './stampa.component.html',
  styleUrls: ['./stampa.component.scss']
})
export class StampaComponent implements OnInit {

  @Input() ideaImp?: IdeaDiImpresa
  @Input() areaTerritoriale: string =""
  @Input() statoIdea?: StatoIdeaDiImpresa
  @Input() cambioGen?: boolean

  cittadini!:Cittadino[];
  angrafiche:CittAn[]=[];

  constructor(
    private ideaImpService: IdeaImpresaService,
    private anagraficaService:AnagraficaService,

  ) { }

  ngOnInit(): void {
  }

}
