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

export * from './anagrafiche.service';
import { AnagraficheService } from './anagrafiche.service';
export * from './areaTerritoriale.service';
import { AreaTerritorialeService } from './areaTerritoriale.service';
export * from './backoffice.service';
import { BackofficeService } from './backoffice.service';
export * from './cittadini.service';
import { CittadiniService } from './cittadini.service';
export * from './datiComuni.service';
import { DatiComuniService } from './datiComuni.service';
export * from './documenti.service';
import { DocumentiService } from './documenti.service';
export * from './email.service';
import { EmailService } from './email.service';
export * from './ente.service';
import { EnteService } from './ente.service';
export * from './excel.service';
import { ExcelService } from './excel.service';
export * from './ideeDiImpresa.service';
import { IdeeDiImpresaService } from './ideeDiImpresa.service';
export * from './incontriPreaccoglienza.service';
import { IncontriPreaccoglienzaService } from './incontriPreaccoglienza.service';
export * from './luoghiIncontro.service';
import { LuoghiIncontroService } from './luoghiIncontro.service';
export * from './operatori.service';
import { OperatoriService } from './operatori.service';
export * from './questionario.service';
import { QuestionarioService } from './questionario.service';
export * from './soggettiAffidatario.service';
import { SoggettiAffidatarioService } from './soggettiAffidatario.service';
export * from './soggettiAttuatore.service';
import { SoggettiAttuatoreService } from './soggettiAttuatore.service';
export * from './system.service';
import { SystemService } from './system.service';
export * from './tutor.service';
import { TutorService } from './tutor.service';
export * from './user.service';
import { UserService } from './user.service';
export * from './validation.service';
import { ValidationService } from './validation.service';
export const APIS = [AnagraficheService, AreaTerritorialeService, BackofficeService, CittadiniService, DatiComuniService, DocumentiService, EmailService, EnteService, ExcelService, IdeeDiImpresaService, IncontriPreaccoglienzaService, LuoghiIncontroService, OperatoriService, QuestionarioService, SoggettiAffidatarioService, SoggettiAttuatoreService, SystemService, TutorService, UserService, ValidationService];
