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

import { NgModule, ModuleWithProviders, SkipSelf, Optional } from '@angular/core';
import { Configuration } from './configuration';
import { HttpClient } from '@angular/common/http';


import { AnagraficheService } from './api/anagrafiche.service';
import { AreaTerritorialeService } from './api/areaTerritoriale.service';
import { BackofficeService } from './api/backoffice.service';
import { CittadiniService } from './api/cittadini.service';
import { DatiComuniService } from './api/datiComuni.service';
import { DocumentiService } from './api/documenti.service';
import { IdeeDiImpresaService } from './api/ideeDiImpresa.service';
import { IncontriPreaccoglienzaService } from './api/incontriPreaccoglienza.service';
import { LuoghiIncontroService } from './api/luoghiIncontro.service';
import { OperatoriService } from './api/operatori.service';
import { QuestionarioService } from './api/questionario.service';
import { SoggettiAffidatarioService } from './api/soggettiAffidatario.service';
import { SoggettiAttuatoreService } from './api/soggettiAttuatore.service';
import { SystemService } from './api/system.service';
import { TutorService } from './api/tutor.service';
import { UserService } from './api/user.service';
import { ValidationService } from './api/validation.service';

@NgModule({
  imports:      [],
  declarations: [],
  exports:      [],
  providers: [
    AnagraficheService,
    AreaTerritorialeService,
    BackofficeService,
    CittadiniService,
    DatiComuniService,
    DocumentiService,
    IdeeDiImpresaService,
    IncontriPreaccoglienzaService,
    LuoghiIncontroService,
    OperatoriService,
    QuestionarioService,
    SoggettiAffidatarioService,
    SoggettiAttuatoreService,
    SystemService,
    TutorService,
    UserService,
    ValidationService ]
})
export class ApiModule {
    public static forRoot(configurationFactory: () => Configuration): ModuleWithProviders<ApiModule> {
        return {
            ngModule: ApiModule,
            providers: [ { provide: Configuration, useFactory: configurationFactory } ]
        };
    }

    constructor( @Optional() @SkipSelf() parentModule: ApiModule,
                 @Optional() http: HttpClient) {
        if (parentModule) {
            throw new Error('ApiModule is already loaded. Import in your base AppModule only.');
        }
        if (!http) {
            throw new Error('You need to import the HttpClientModule in your AppModule! \n' +
            'See also https://github.com/angular/angular/issues/20575');
        }
    }
}
