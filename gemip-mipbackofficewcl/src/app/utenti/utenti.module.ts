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

import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

//-Modules
import { SharedModule } from '@shared/shared.module';

//-Components
import { CittadinoGestioneComponent } from './components/cittadino-gestione/cittadino-gestione.component';
import { SoggettoAttuatorGestioneComponent } from './components/soggetto-attuator-gestione/soggetto-attuator-gestione.component';
import { CittadinoFormComponent } from './components/cittadino-form/cittadino-form.component';
import { SoggettoAttuatoreFormComponent } from './components/soggetto-attuatore-form/soggetto-attuatore-form.component';


const routes: Routes = [
  {
    path: 'cittadino', children: [
      { path: 'gestione', component: CittadinoGestioneComponent },
      { path: 'form/:id', component: CittadinoFormComponent },]
  },
  {
    path: 'soggetto-attuatore', children: [
      { path: 'gestione', component: SoggettoAttuatorGestioneComponent },
      { path: 'form', component: SoggettoAttuatoreFormComponent },]
  },
  {
    path: 'soggetto-affidatario', children: [
      { path: 'form', component: CittadinoFormComponent },]
  },
  { path: '**', redirectTo: 'cittadino/gestione' },
];


@NgModule({
  declarations: [
    CittadinoGestioneComponent,
    CittadinoFormComponent,
    SoggettoAttuatorGestioneComponent,

    SoggettoAttuatoreFormComponent,
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(routes),
    SharedModule
  ]
})
export class UtentiModule { }
