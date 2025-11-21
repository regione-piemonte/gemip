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
import { RouterModule, Routes } from '@angular/router';
import { TemplateComponent } from './dashboard/components/template/template.component';
import { DashboardComponent } from './dashboard/components/dashboard/dashboard.component';
import { IncontroPreAccoglienzaComponent } from './dashboard/components/incontro-pre-accoglienza/incontro-pre-accoglienza.component';

import { IdeaImprenditorialeComponent } from './dashboard/components/idea-imprenditoriale/idea-imprenditoriale.component';
import { VerificaDatiComponent } from './dashboard/components/verifica-dati/verifica-dati.component';
import { SceltaTutorComponent } from './dashboard/components/scelta-tutor/scelta-tutor.component';
import { DatiAnagraficaComponent } from './dashboard/components/dati-anagrafica/dati-anagrafica.component';
import { InserimentoDatiUtenteComponent } from './test/inserimento-dati-utente/inserimento-dati-utente.component';
import { QuestionarioComponent } from './dashboard/components/questionario/questionario.component';
import { CookiePolicyComponent } from './dashboard/components/cookie-policy/cookie-policy.component';
import { ModificaAnagraficaComponent } from './dashboard/components/modifica-anagrafica/modifica-anagrafica.component';

const routes: Routes = [
  {
    path: 'dashboard',
    component: TemplateComponent,
    children: [
      { path: 'incontro-pre-accoglienza', component: IncontroPreAccoglienzaComponent },
      { path: 'dati-anagrafica', component: DatiAnagraficaComponent },
      { path: 'idea-imprenditoriale', component: IdeaImprenditorialeComponent },
      { path: 'verifica-dati', component: VerificaDatiComponent },
      { path: 'scelta-tutor', component: SceltaTutorComponent },
      { path: 'home', component: DashboardComponent },
      { path: 'questionario', component: QuestionarioComponent },
      { path: 'cookie-policy', component: CookiePolicyComponent },
      { path: 'modifica-anagrafica', component: ModificaAnagraficaComponent },
      { path: '', redirectTo: '/home', pathMatch: 'full' },
      { path: '**', redirectTo: '/home', pathMatch: 'full' },
    ],
  },
  { path: 'test', component: InserimentoDatiUtenteComponent },
  { path: '**', redirectTo: '/dashboard/home', pathMatch: 'full' },
  { path: '', redirectTo: '/dashboard/home', pathMatch: 'full' },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'top',
      scrollOffset: [0, 0],
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
