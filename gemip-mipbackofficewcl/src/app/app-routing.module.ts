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

//-Components
import { MainComponent } from './main/components/main/main.component';
import { DashboardComponent } from '@main/components/dashboard/dashboard.component';
import { SelectRuoloComponent } from '@main/components/select-ruolo/select-ruolo.component';
import { NavBarComponent } from '@main/components/nav-bar/nav-bar.component';

//-Utils
import { UrlRouter } from '@main/const/url-router.model';

const routes: Routes = [
  { path:'', component:MainComponent,
    children:[
      { path:'home/1', component:SelectRuoloComponent },
      { path:'home/2', component:DashboardComponent },
      {
        path:'', component:NavBarComponent,
        children:[
          { path: UrlRouter.urlPreAccoglienza,
            loadChildren:()=> import('./pre-accoglienza/pre-accoglienza.module').then(m => m.PreAccoglienzaModule)
          },
          { path: UrlRouter.urlIdeeImpresa,
            loadChildren:()=> import('./idee-impresa/idee-impresa.module').then(m => m.IdeeImpresaModule)
          },
          { path: UrlRouter.urlOperatori,
            loadChildren:()=> import('./operatori/operatori.module').then(m => m.OperatoriModule)
          },
          { path: UrlRouter.urlDocumenti,
            loadChildren:()=> import('./documenti/documenti.module').then(m => m.DocumentiModule)
          },
          { path: UrlRouter.urlReportistica,
            loadChildren:()=> import('./reportistica/reportistica.module').then(m => m.ReportisticaModule)
          },
          { path: UrlRouter.urlCalendario,
            loadChildren:()=> import('./calendario/calendario.module').then(m => m.CalendarioModule)
          },
          { path: UrlRouter.urlUtenti,
            loadChildren:()=> import('./utenti/utenti.module').then(m => m.UtentiModule)
          },
          { path:'**', redirectTo:'/home/2' }
        ]
      },
      { path:'**', redirectTo:'/home/2' }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes,{ useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
