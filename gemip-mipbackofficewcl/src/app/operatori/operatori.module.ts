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

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';

//-Modules
import { SharedModule } from '@shared/shared.module';

//-Components
import { OperatoriGestioneComponent } from './components/operatori-gestione/operatori-gestione.component';
import { OperatoriNuovoComponent } from './components/operatori-nuovo/operatori-nuovo.component';



const routes: Routes = [
  {path: 'gestione', component:OperatoriGestioneComponent,},
  {path: 'nuovo', component:OperatoriNuovoComponent,},
  {path: 'form', component:OperatoriNuovoComponent,},

  {path: '**', redirectTo:'gestione'},
];

@NgModule({
  declarations: [
    OperatoriGestioneComponent,
    OperatoriNuovoComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ]
})
export class OperatoriModule { }
