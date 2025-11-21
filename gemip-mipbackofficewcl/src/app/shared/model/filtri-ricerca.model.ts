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

export interface FiltriRicerca{


}

export interface FiltroRicercaPreAccoglienza extends FiltriRicerca{
  dataDa?:Date,
  dataA?:Date,

  sede?:string,
  areaTerritoriale?: string,
  codFiscOperatore?:string,
}

export interface FiltroRicercaIdeaImpresa extends FiltriRicerca{
  codAreaTerritoriale?: string,
  dataInseritaDa?: string,
  dataInseritaA?: string,
  ideaDiImpresa?: string,
  cittadinoNome?: string,
  cittadinoCognome?: string,
  idStatoIdea?: string,
  statoPresenze?:string,
  idSoggettoAttuatore?: string
}

export interface FiltroRicercaUtentiCittadino extends FiltriRicerca{
  nome?:string,
  cognome?:string,
  email?:string,
  codiceFiscale?:string,
}

export interface FiltroRicercaOperatore extends FiltriRicerca{
  idOperatore?:string,
  nome?:string,
  cognome?:string,
  email?:string,
  codiceFiscale?:string,
  abilitato?:string,
  soggetto?:string,
  idSoggettoAttuatore?:number,
}

export interface FiltroRicercaDocumento extends FiltriRicerca{
  idIdeaDiImpresa?:number,
  titolo?: string,
  codiceTipoDocumento?: string
}

export interface FiltroRicercaEventoCalendario extends FiltriRicerca{
  dataDa?: Date,
  dataA?: Date,
  idSoggettoAttuatore?: string,
  idOperatore?:string
}

export interface FiltroRicercaEventoRicercaIdeeImpresa extends FiltriRicerca{
  dataDa?: Date,
  dataA?: Date,
  idSoggettoAttuatore?: string,
  areaTerritoriale?: string,
  tipoReport?:any,
  idStatoIdea?:string,
}
export interface FiltroRicercaEventoRicercaQuestionari extends FiltriRicerca{
  dataDa?: Date,
  dataA?: Date,
  idSoggettoAttuatore?: string,
  tipoReport?:any,
  codAreaTerritoriale?:string
}
export interface FiltroRicercaAnagraficheCittadini extends FiltriRicerca{
  dataDa?:Date,
  dataA?:Date,
  idSoggettoAttuatore?:string,
  sede?:string,
  areaTerritoriale?: string,
  codFiscOperatore?:string,
}
