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

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { IdeaImprenditorialeComponent } from './components/idea-imprenditoriale/idea-imprenditoriale.component';
import { CustomHeader, IncontroPreAccoglienzaComponent } from './components/incontro-pre-accoglienza/incontro-pre-accoglienza.component';
import { SceltaTutorComponent } from './components/scelta-tutor/scelta-tutor.component';
import { SharedModule } from '../shared/shared.module';
import { TemplateComponent } from './components/template/template.component';
import { CommonModule } from '@angular/common';
import { ToolbarComponent } from './components/toolbar/toolbar.component';
import { DatiAnagraficaComponent } from './components/dati-anagrafica/dati-anagrafica.component';
import { VerificaDatiComponent } from './components/verifica-dati/verifica-dati.component';
import { RouterModule } from '@angular/router';
import { LoadingIndicatorComponent } from './components/loading-indicator/loading-indicator.component';
import { QuestionarioComponent } from './components/questionario/questionario.component';
import { FaccinaComponent } from './components/faccina/faccina.component';
import { CookiePolicyComponent } from './components/cookie-policy/cookie-policy.component';
import { FooterComponent } from './components/footer/footer.component';
import { ModificaAnagraficaComponent } from './components/modifica-anagrafica/modifica-anagrafica.component';

@NgModule({
  declarations: [
    TemplateComponent,
    DashboardComponent,
    IdeaImprenditorialeComponent,
    IncontroPreAccoglienzaComponent,
    SceltaTutorComponent,
    ToolbarComponent,
    DatiAnagraficaComponent,
    VerificaDatiComponent,
    LoadingIndicatorComponent,
    CustomHeader,
    QuestionarioComponent,
    FaccinaComponent,
    CookiePolicyComponent,
    FooterComponent,
    ModificaAnagraficaComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,

  ],
  providers: [],
  bootstrap: [],
})
export class DashboardModule {}
