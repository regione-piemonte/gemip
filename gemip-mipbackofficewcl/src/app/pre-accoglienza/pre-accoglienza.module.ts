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
import { PreAccoglienzaGestioneComponent } from './components/pre-accoglienza-gestione/pre-accoglienza-gestione.component';
import { PreAccoglienzaNuovoComponent } from './components/pre-accoglienza-nuovo/pre-accoglienza-nuovo.component';
import { IdeeDiImpresaIncontroComponent } from './components/idee-di-impresa-incontro/idee-di-impresa-incontro.component';
import { OperatoriIncontroComponent } from './components/operatori-incontro/operatori-incontro.component';
import { CalendarioPreAccoglienzaComponent } from './components/calendario-pre-accoglienza/calendario-pre-accoglienza.component';


const routes: Routes = [
  {path: 'gestione', component:PreAccoglienzaGestioneComponent},
  {path: 'nuovo', component:PreAccoglienzaNuovoComponent},
  {path: 'modifica', component:PreAccoglienzaNuovoComponent},

  {path: '**', redirectTo:'gestione'},
];

@NgModule({
  declarations: [
    PreAccoglienzaGestioneComponent,
    PreAccoglienzaNuovoComponent,
    IdeeDiImpresaIncontroComponent,
    OperatoriIncontroComponent,
    CalendarioPreAccoglienzaComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ]
})
export class PreAccoglienzaModule { }
