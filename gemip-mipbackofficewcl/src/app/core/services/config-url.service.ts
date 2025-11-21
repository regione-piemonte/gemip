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
  get rootPath() {
    return this.getBERootUrl()
  }
  //comuni
  get comuniUrl() {
    return this.rootPath + "/comuni"
  }

  get comuniByProvinciaUrl() {
    return this.comuniUrl + "/provincia"
  }

  //Logged-User
  get userInfoUrl() {
    return this.rootPath + "/user-info"
  }
  get ruoliUrl() {
    return this.rootPath + "/ruoli"
  }


  //province
  get provinceUrl() {
    return this.rootPath + "/province"
  }

  get provinciaByRegioneUrl() {
    return this.provinceUrl + "/regione"
  }

  //cittadinanze
  get cittadinanzeUrl() {
    return this.rootPath + "/cittadinanze"
  }

  //stati esteri
  get statiEsteriUrl() {
    return this.rootPath + "/stati-esteri"
  }

  //anagrafiche
  get anagraficheUrl() {
    return this.rootPath + "/anagrafiche"
  }

  get anagraficaUrl() {
    return this.anagraficheUrl + "/anagrafica"
  }
  get condizioneOccUrl() {
    return this.anagraficheUrl + "/condizione-occupazionale"
  }
  get condizioneFamiliareUrl() {
    return this.anagraficheUrl + "/condizione-familiare"
  }

  get titoloDIstudioUrl() {
    return this.anagraficheUrl + "/titolo-studio"
  }

  get svantaggiAbitativiUrl() {
    return this.anagraficheUrl + "/svantaggi-abitativo"
  }

  //Incontro pre accoglienza
  get incontriPreaccoglienzaUrl() {
    return this.rootPath + "/incontri-preaccoglienza"
  }
  get incontroPreAccoglienzaUrl() {
    return this.incontriPreaccoglienzaUrl + "/incontro-preaccoglienza"
  }
  get incontriPreaccoglienzaPerAreaTerritorialeUrl() {
    return this.incontriPreaccoglienzaUrl + "/incontri-preaccoglienza-disponibile"
  }

  get associaIncontriAreaTerritorialeUrl() {
    return this.incontriPreaccoglienzaUrl + "/associazione-area-territoriale"
  }

  get incontroPreaccoglienzaForCittadino() {
    return this.incontriPreaccoglienzaUrl + "/cittadino-incontro-preaccoglienza"
  }
  get incontroByIdeaUrl() {
    return this.incontriPreaccoglienzaUrl + "/incontro-preaccoglienza-per-idea-impresa"
  }
  get incontroPreaccoglienzaInviaComunicato() {
    return this.incontriPreaccoglienzaUrl + "/incontro-preaccoglienza-invia-comunicato"
  }
  get incontroPreaccoglienzaControlloEsistenzaIncontro() {
    return this.incontriPreaccoglienzaUrl + "/controllo-incontro-esistente"
  }

  //luoghi incontro
  get luoghiUrl() {
    return this.rootPath + "/luoghi-incontro"
  }

  get luoghiAreaTerittorialeUrl() {
    return this.luoghiUrl + "/luoghi-incontro-area-territoriale"
  }
  //cittadino
  get cittadiniUrl() {
    return this.rootPath + "/cittadini"
  }


  get cittadinoUrl() {
    return this.cittadiniUrl + "/cittadino"
  }
  get inviaDatiUrl() {
    return this.cittadinoUrl + "/invia-dati"
  }
  get verificaDashboardUrl() {
    return this.cittadinoUrl + "/dashboard"
  }

  //idee d'impresa


  get ideeDiImpresaRicercaUrl() {
    return this.ideeDiImpresaUrl + "/ricerca"
  }

  get ideeDiImpresaPerCittadinoUrl() {
    return this.ideeDiImpresaUrl + "/idea-di-impresa-cittadino"
  }

  get statiIdeaDiImpresaUrl() {
    return this.ideeDiImpresaUrl + "/stati-idea-di-impresa"
  }

  get fontiConoscienzaMipUrl() {
    return this.ideeDiImpresaUrl + "/fonti-conoscenza-mip"
  }


  get tutorUrl() {
    return this.rootPath + "/tutor"
  }
  get tutorByAreaUrl() {
    return this.tutorUrl + "/tutor-area-territoriale"
  }
  get sceltaTutorUrl() {
    return this.tutorUrl + "/scelta-tutor"
  }
  get resetTutorUrl() {
    return this.tutorUrl + "/reset-tutor"
  }



  //area-territoriale
  get areaTerritorialeUrl() {
    return this.rootPath + "/area-territoriale"
  }


  //Idee di impresa
  //  get cittadiniUrl(){
  //   return this.rootPath + "/cittadini"
  //  }
  get cittadiniByIdeaUrl() {
    return this.cittadiniUrl + "/cittadino-idea-impresa"
  }



  //Idee di impresa
  get ideeDiImpresaUrl() {
    return this.rootPath + "/idee-di-impresa"
  }

  get registraPresenzeUrl() {
    return this.ideeDiImpresaUrl + "/registra-presenze"
  }
  get ricercaIdeeDiImpresaUrl() {
    return this.ideeDiImpresaUrl + "/ricerca"
  }
  get ideaDiImpresaUrl() {
    return this.ideeDiImpresaUrl + "/idea-di-impresa"
  }
  get ideeDiImpresaPerIncontroUrl() {
    return this.ideeDiImpresaUrl + "/incontro-pre-accoglienza"
  }
  get statoImpresaUrl() {
    return this.ideeDiImpresaUrl + "/stati-idea-di-impresa"
  }
  get fontiConoscenzaUrl() {
    return this.ideeDiImpresaUrl + "/fonti-conoscenza-mip"
  }


  ///soggetti-affidatario
  get soggettiAffidatarioUrl() {
    return this.rootPath + "/soggetti-affidatario"
  }
  get operatoreSoggAffUrl() {
    return this.soggettiAffidatariUrl + "/operatore-soggetti-affidatario"
  }
  ///soggetti-attuatore
  get soggettiAttuatoreUrl() {
    return this.rootPath + "/soggetti-attuatore"
  }

  get operatoreSoggUrl() {
    return this.soggettiAttuatoreUrl + "/operatore-soggetto-attuatore"
  }
  get soggettiAttuatorePerOperatoreUrl() {
    return this.soggettiAttuatoreUrl + "/soggetto-attuatore-per-operatore"
  }

  get OperatoriSoggAffidatarioById() {
    return this.operatoriUrl + "/operatori-sogg-affidatario";
  }

  //Enti
  get entiUrl() {
    return this.rootPath + "/enti"
  }
  get OperatoriEntiUrl() {
    return this.entiUrl + "/operatore-ente"
  }

  //operatori
  get operatoriUrl() {
    return this.rootPath + "/operatori"
  }
  get ricercaOperatoreUrl() {
    return this.operatoriUrl + "/ricerca-operatori"
  }
  get operatoreUrl() {
    return this.operatoriUrl + "/operatore"
  }
  get soggettiAttuatoriForOperatorUrl() {
    return this.operatoreUrl + "/soggetti-attutatori"
  }

  get operatoriInocntroPreaccoglienzaUrl() {
    return this.operatoriUrl + "/operatore-incontro-preaccoglienza"
  }


  //soggetti affidatari
  get soggettiAffidatariUrl() {
    return this.rootPath + "/soggetti-affidatario"
  }

  get operatoriSoggettiAffidatariUrl() {
    return this.soggettiAffidatariUrl + "/operatore-soggetti-affidatario"
  }

  //upload ics

  get icsUrl() {
    return this.rootPath + "/file-ics"
  }

  get calendarioEventiUrl() {
    return this.icsUrl + "/eventi-calendario"
  }

  get storicoCalendarioUrl() {
    return this.icsUrl + "/storico"
  }

  get downLoadIcsUrl() {
    return this.icsUrl + "/download"
  }
  //documenti
  get documentiUrl() {
    return this.rootPath + "/documenti"
  }

  get tipoDocumentiUrl() {
    return this.documentiUrl + "/tipi-documento"
  }

  get caricaDocumentoUrl() {
    return this.documentiUrl + "/carica-documento"
  }

  get documentoUrl() {
    return this.documentiUrl + "/documento"
  }

  get excelUrl() {
    return this.rootPath + "/excel"
  }

  get excelCittadiniIncontroUrl() {
    return this.excelUrl + "/esporta-excel-anagrafica-cittadino"
  }
  get getExportIncontriPartecipantiAnagrafica() {
    return this.excelUrl + "/export-preaccoglienza-anagrafica"
  }
  get pdfUrl() {
    return this.rootPath + "/pdf"
  }
  get pdfPresenzeUrl() {
    return this.pdfUrl + "/esporta-pdf-presenze"
  }

  //reportisticaUrl
  get reportisticaUrl() {
    return this.rootPath + "/reportistica"
  }
  get reportisticaIdeeImpresaUrl() {
    return this.reportisticaUrl + "/idee-impresa"
  }
  get reportisticaExcelIdeeImpresaUrl() {
    return this.reportisticaUrl + "/esporta-excel-idee-impresa"
  }
  get reportisticaQuestionario() {
    return this.reportisticaUrl + "/questionari"
  }

  get reportisticaExcelQuestionari() {
    return this.reportisticaUrl + "/esporta-excel-questionari"
  }
  //questionario
  get questionarioUrl() {
    return this.rootPath + "/questionario"
  }
  get questionarioEmailQ1Url() {
    return this.questionarioUrl + "/send-email-cittadini"
  }

}
