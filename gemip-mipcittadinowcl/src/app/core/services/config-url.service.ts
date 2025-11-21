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

import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConfigUrlService {

  constructor() { }

  getBERootUrl(): string {
    return environment.beServerPrefix + environment.beRootPath;
}
  get rootPath(){
    return this.getBERootUrl()
  }

  //comuni
  get comuniUrl(){
    return this.rootPath + "/comuni"
  }

  get comuniByProvinciaUrl(){
    return this.comuniUrl + "/provincia"
  }

  //province
   get provinceUrl(){
    return this.rootPath + "/province"
   }

   get provinciaByRegioneUrl(){
    return this.provinceUrl + "/regione"
   }

   //cittadinanze
   get cittadinanzeUrl(){
    return this.rootPath + "/cittadinanze"
   }

   //stati esteri
   get statiEsteriUrl(){
    return this.rootPath + "/stati-esteri"
   }

   //anagrafiche
   get anagraficheUrl(){
    return this.rootPath + "/anagrafiche"
   }

   get anagraficaUrl(){
    return this.anagraficheUrl + "/anagrafica"
   }
   get anagraficaSpidUrl(){
    return this.anagraficheUrl + "/anagrafica-suggest"
   }
   get condizioneOccUrl(){
    return this.anagraficheUrl + "/condizione-occupazionale"
   }

   get titoloDIstudioUrl(){
    return this.anagraficheUrl + "/titolo-studio"
   }

   get svantaggiAbitativiUrl(){
    return this.anagraficheUrl + "/svantaggi-abitativo"
   }

   get condizioniFamiliareUrl(){
    return this.anagraficheUrl + "/condizione-familiare"
   }

   //aree territoriali
   get areaTerritorialeUrl(){
    return this.rootPath + "/area-territoriale"
   }

   get incontriPreaccoglienzaUrl(){
    return this.rootPath + "/incontri-preaccoglienza"
   }

   get incontriPreaccoglienzaPerAreaTerritorialeUrl(){
    return this.incontriPreaccoglienzaUrl + "/incontri-preaccoglienza-disponibile"
   }

   get incontroPreaccoglienzaForCittadino(){
    return this.incontriPreaccoglienzaUrl + "/cittadino-incontro-preaccoglienza"
   }

   get incontroPreaccoglienzaPerCittadino(){
    return this.incontriPreaccoglienzaUrl+ "/incontro-preaccoglienza-per-cittadino"
   }
   //cittadino
   get cittadiniUrl(){
    return this.rootPath + "/cittadini"
   }

   get cittadinoUrl(){
    return this.cittadiniUrl + "/cittadino"
   }
   get inviaDatiUrl(){
    return this.cittadinoUrl+"/invia-dati"
   }
   get verificaDashboardUrl(){
    return this.cittadinoUrl + "/dashboard"
   }

   //idee d'impresa
   get ideeDiImpresaUrl(){
    return this.rootPath+"/idee-di-impresa"
   }

   get ideaDiImpresaUrl(){
    return this.ideeDiImpresaUrl+"/idea-di-impresa"
   }

   get ideaDiImpresaByCittadinoUrl(){
    return this.ideeDiImpresaUrl+"/idea-di-impresa-cittadino"
   }

   get ideaDiImpresaCittadinoUrl(){
    return this.ideeDiImpresaUrl+"/idea-di-impresa-cittadino"
   }

   get statiIdeaDiImpresaUrl(){
    return this.ideeDiImpresaUrl + "/stati-idea-di-impresa"
   }

   get fontiConoscienzaMipUrl(){
    return this.ideeDiImpresaUrl + "/fonti-conoscenza-mip"
   }


   get tutorUrl(){
    return this.rootPath + "/tutor"
   }
   get tutorAbilitatiByAreaUrl(){
    return this.tutorUrl + "/tutorAbilitati-area-territoriale"
   }
   get sceltaTutorUrl(){
    return this.tutorUrl + "/scelta-tutor"
   }

   //Questionario
   get questionarioUrl(){
    return this.rootPath + "/questionario"
   }
   get getQuestionario(){
    return this.questionarioUrl + "/corrente"
   }


}
