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

import { UrlRouter } from "@main/const/url-router.model";
import { IconsSettings } from "@shared/utils/icons-settings";

export class NavBarItem{
  constructor(
    public label: string,
    public isSubSection: boolean,
    public icon?: string,
    public path?: string,
    public subItems?: NavBarItem[],
    public disabled?: boolean,
    public isActive?: boolean
  ){}

  //--Sections
  //##########--DASHBOARD--#############
  static  DASHBOARD_ITEM: NavBarItem = {
    icon: "dashboard",
    label: "Home",
    path: "/home/2",
    isSubSection: false
  };

  //##########--PRE-ACCOGLIENZA--#############
  static  PRE_ACCOGLIENZA_G_ITEM :NavBarItem = {
    label: "Elenco incontri pre-accoglienza",
    path: "pre-accoglienza/gestione",
    isSubSection: true
  };
  static  PRE_ACCOGLIENZA_N_ITEM :NavBarItem = {
    label: "Nuovo",
    path: "pre-accoglienza/nuovo",
    isSubSection: true
  };
  static  PRE_ACCOGLIENZA_ITEM :NavBarItem = {
    icon: "date_range",
    label: "Pre accoglienza",
    isSubSection: false,
    subItems: [NavBarItem.PRE_ACCOGLIENZA_G_ITEM, NavBarItem.PRE_ACCOGLIENZA_N_ITEM]
  };
  static  PRE_ACCOGLIENZA_VIEW_ITEM :NavBarItem = {
    icon: "date_range",
    label: "Pre accoglienza",
    isSubSection: false,
    subItems: [NavBarItem.PRE_ACCOGLIENZA_G_ITEM]
  };


  //##########--IDEE-IMPRESA--#############
  static  IDEE_IMPRESA_ITEM :NavBarItem = {
    icon: "business",
    label: "Idee d'impresa",
    path: "idee-impresa/gestione",
    isSubSection: false,
  };
  //##########--OPERATORI--#############
  static  OPERATORi_ITEM :NavBarItem = {
    icon: "people_outline",
    label: "Operatori",
    path: "operatori/gestione",
    isSubSection: false,
    //subItems: [NavBarItem.OPERATORi_G_ITEM]
  };

  //##########--DOCUMENTI--#############
  static  DOCUMENTI_G_ITEM :NavBarItem = {
    label: "Elenco documentazione",
    path: "documenti/gestione",
    isSubSection: true
  };
  static  DOCUMENTI_N_ITEM :NavBarItem = {
    label: "Nuovo",
    path: "documenti/nuovo",
    isSubSection: true
  };
  static  DOCUMENTI_ITEM :NavBarItem = {
    icon: "ballot",
    label: "Documentazione",
    isSubSection: false,
    subItems: [ NavBarItem.DOCUMENTI_G_ITEM, NavBarItem.DOCUMENTI_N_ITEM]
  };
  static  DOCUMENTI_VIEW_ITEM :NavBarItem = {
    icon: "ballot",
    label: "Documentazione",
    isSubSection: false,
    subItems: [ NavBarItem.DOCUMENTI_G_ITEM]
  };
  //##########--REPORTISTICA--#############
  static  REPORTISTICA_QUESTIONARI :NavBarItem = {
    label: "Questionari",
    path: "reportistica/questionari",
    isSubSection: true
  };
  static  REPORTISTICA_IDEA_IMPRESA :NavBarItem = {
    label: "Idee d'impresa",
    path: "reportistica/ideeImpresa",
    isSubSection: true
  };
  static  REPORTISTICA_ANAGRAFICA_PARTECIPANTI :NavBarItem = {
    label: "Anagrafiche partecipanti ",
    path: "reportistica/anagraficapartecipanti",
    isSubSection: true
  };
  static  REPORTISTICA_ITEM :NavBarItem = {
    icon: IconsSettings.REPORTISTICA_ICON,
    label: "Reportistica",
    isSubSection: false,
    subItems: [NavBarItem.REPORTISTICA_IDEA_IMPRESA, NavBarItem.REPORTISTICA_QUESTIONARI,NavBarItem.REPORTISTICA_ANAGRAFICA_PARTECIPANTI]
  };
  static  REPORTISTICA_ITEM_SOGGATT :NavBarItem = {
    icon: IconsSettings.REPORTISTICA_ICON,
    label: "Reportistica",
    isSubSection: false,
    subItems: [NavBarItem.REPORTISTICA_IDEA_IMPRESA,NavBarItem.REPORTISTICA_ANAGRAFICA_PARTECIPANTI]
  };
  //##########--CALENDARIO--#############
  static  CALENDARIO_ITEM :NavBarItem = {
    icon: IconsSettings.CALENDAR_ICON,
    label: "Agenda appuntamenti",
    isSubSection: false,
    path: `calendario`,
  };

  //##########--UTENTI--#############
  static  SOGGETTI_ITEM :NavBarItem = {
    icon: IconsSettings.SOGG_ICON,
    label: "Soggetti giuridici",
    isSubSection: false,
    path: `${UrlRouter.urlUtenti}/soggetto-attuatore/${UrlRouter.urlGestione}`,
  };
  static  CITTADINO_ITEM :NavBarItem = {
    icon: IconsSettings.UTENTI_ICON,
    label: "Cittadino",
    isSubSection: false,
    path: `/${UrlRouter.urlUtenti}/${UrlRouter.urlCittadino}/${UrlRouter.urlGestione}`,
  };

  //--MENU
  static  ITEMS_ADMIN: NavBarItem[] = [
    NavBarItem.DASHBOARD_ITEM,
    NavBarItem.PRE_ACCOGLIENZA_ITEM,
    NavBarItem.IDEE_IMPRESA_ITEM,

    NavBarItem.CITTADINO_ITEM,
    NavBarItem.CALENDARIO_ITEM,

    NavBarItem.SOGGETTI_ITEM,
    NavBarItem.OPERATORi_ITEM,

    NavBarItem.DOCUMENTI_ITEM,
    NavBarItem.REPORTISTICA_ITEM
  ]
  static  ITEMS_ALTRO: NavBarItem[] = [
    NavBarItem.DASHBOARD_ITEM,
    NavBarItem.PRE_ACCOGLIENZA_VIEW_ITEM,
    NavBarItem.IDEE_IMPRESA_ITEM,

    NavBarItem.CITTADINO_ITEM,
    NavBarItem.CALENDARIO_ITEM,

    NavBarItem.SOGGETTI_ITEM,
    NavBarItem.OPERATORi_ITEM,

    NavBarItem.DOCUMENTI_VIEW_ITEM
  ]
  static  ITEMS_ATTUATORE: NavBarItem[] = [
    NavBarItem.DASHBOARD_ITEM,
    NavBarItem.IDEE_IMPRESA_ITEM,

    NavBarItem.CITTADINO_ITEM,
    NavBarItem.CALENDARIO_ITEM,

    NavBarItem.OPERATORi_ITEM,

    NavBarItem.DOCUMENTI_VIEW_ITEM,
    NavBarItem.REPORTISTICA_ITEM_SOGGATT
  ]

}
