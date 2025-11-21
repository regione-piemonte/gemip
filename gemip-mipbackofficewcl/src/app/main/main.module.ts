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

//- Components
import { NavBarComponent } from './components/nav-bar/nav-bar.component';
import { MainComponent } from './components/main/main.component';

import { ToolBarComponent } from './components/tool-bar/tool-bar.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { SelectRuoloComponent } from './components/select-ruolo/select-ruolo.component';

import { RouterModule } from '@angular/router';
import { SharedModule } from '@shared/shared.module';
import { GetFirstLetterPipe } from './pipes/get-first-letter.pipe';
import { LoadingIndicatorComponent } from './components/loading-indicator/loading-indicator.component';
import { FooterComponent } from './components/footer/footer.component';


const compoennts = [
  NavBarComponent,
  MainComponent,
  LoadingIndicatorComponent,
  ToolBarComponent,
  DashboardComponent,
  SelectRuoloComponent
]

@NgModule({
  declarations: [ compoennts, GetFirstLetterPipe, FooterComponent],
  imports: [
    CommonModule,
    RouterModule,
    SharedModule,
  ]
})
export class MainModule { }
