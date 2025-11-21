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

import { IconsSettings } from '@shared/utils/icons-settings';
import { UrlRouter } from '@main/const/url-router.model';

export class MenuItem {
  constructor(
    public label: string,
    public icon?: string,
    public isOpen?: boolean,
    public items?: MenuItem[],
    public url?: string,
    public toolTip?: string,
    public cssClass?: string,
    public disabled?: boolean,
    public disableLabel?: boolean
  ) {}


  //----------------------------------------------
  //- Card
  static PRE_ACCOGLIENZA:MenuItem = {
    icon: IconsSettings.PRE_ACCOGLIENZA_ICON,
    isOpen: false,
    disableLabel: false,
    label: 'Incontri Pre-accoglienza',
    items: [
      {
        label: 'Elenco incontri pre-accoglienza',
        url: `${UrlRouter.urlPreAccoglienza}/${UrlRouter.urlGestione}`,
      },
      {
        label: 'Nuovo incontro',
        url: `${UrlRouter.urlPreAccoglienza}/${UrlRouter.urlNuovo}`,
      },
    ],
  }
  static PRE_ACCOGLIENZA_SOLO_VIEW:MenuItem = {
    icon: IconsSettings.PRE_ACCOGLIENZA_ICON,
    isOpen: false,
    label: 'Incontri Pre-accoglienza',
    url: `${UrlRouter.urlPreAccoglienza}/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }


  //----------------------------------
  static IDEE_IMPRESA:MenuItem = {
    icon: IconsSettings.IDEE_IMPRESA_ICON,
    label: "Idee d'impresa",
    url: `${UrlRouter.urlIdeeImpresa}/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }

  //----------------------------------
  static OPERATORI:MenuItem = {
    icon: IconsSettings.OPERATORI_ICON,
    isOpen: false,
    label: 'Operatori',
    url: `${UrlRouter.urlOperatori}/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }

  //----------------------------------
  static DOCUMENTAZIONE:MenuItem ={
    icon: IconsSettings.DOCUMENTAZIONE_ICON,
    isOpen: false,
    label: 'Documentazione',
    disableLabel: false,
    items: [
      { label: 'Elenco documentazione', url: `${UrlRouter.urlDocumenti}/${UrlRouter.urlGestione}` },
      { label: 'Nuovo', url: `${UrlRouter.urlDocumenti}/${UrlRouter.urlNuovo}` },
    ],
  }
  static DOCUMENTAZIONE_SOLO_VIEW:MenuItem ={
    icon: IconsSettings.DOCUMENTAZIONE_ICON,
    isOpen: false,
    label: 'Documentazione',
    url: `${UrlRouter.urlDocumenti}/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }

  //----------------------------------
  static SOGGEETTI:MenuItem ={
    icon: IconsSettings.SOGG_ICON,
    isOpen: false,
    label: 'Soggetti giuridici',
    url: `utenti/soggetto-attuatore/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }

  //----------------------------------
  static CITTADINO:MenuItem ={
    icon: IconsSettings.UTENTI_ICON,
    isOpen: false,
    label: 'Cittadino',
    url: `utenti/cittadino/${UrlRouter.urlGestione}`,
    disableLabel: true,
  }

  //----------------------------------
  static CALENDARIO:MenuItem ={
    icon: "perm_contact_calendar",
    isOpen: false,
    label: 'Agenda appuntamenti',
    url: `calendario`,
    disableLabel: true,
  }
  //----------------------------------------------
  static REPORTISTICA:MenuItem ={
    icon: IconsSettings.REPORTISTICA_ICON,
    isOpen: false,
    label: 'Reportistica',
    disableLabel: false,
    items: [
      { label: 'Idee d\'impresa', url: `${UrlRouter.urlReportistica}/ideeImpresa` },
      { label: 'Questionari', url: `${UrlRouter.urlReportistica}/questionari` },
      { label: 'Anagrafiche partecipanti', url: `${UrlRouter.urlReportistica}/anagraficapartecipanti` },
    ],
  }
  //----------------------------------------------
  static REPORTISTICA_ATT_BASE:MenuItem ={
    icon: IconsSettings.REPORTISTICA_ICON,
    isOpen: false,
    label: 'Reportistica',
    disableLabel: false,
    items: [
      { label: 'Idee d\'impresa', url: `${UrlRouter.urlReportistica}/ideeImpresa` },
      { label: 'Anagrafiche partecipanti', url: `${UrlRouter.urlReportistica}/anagraficapartecipanti` },
    ],
  }
  //----------------------------------------------

  //- Full menu
  static MENU_ADMIN: MenuItem[] = [
    this.PRE_ACCOGLIENZA,
    this.IDEE_IMPRESA,

    this.CITTADINO,
    this.CALENDARIO,

    this.SOGGEETTI,
    this.OPERATORI,

    this.DOCUMENTAZIONE,
    this.REPORTISTICA
  ];

  static MENU_ALTRO: MenuItem[] = [
    this.PRE_ACCOGLIENZA_SOLO_VIEW,
    this.IDEE_IMPRESA,

    this.CITTADINO,
    this.CALENDARIO,

    this.SOGGEETTI,
    this.OPERATORI,

    this.DOCUMENTAZIONE_SOLO_VIEW
  ];

  static MENU_ATTUATORE: MenuItem[] = [
    this.IDEE_IMPRESA,

    this.CITTADINO,
    this.CALENDARIO,

    this.OPERATORI,

    this.DOCUMENTAZIONE_SOLO_VIEW,
    this.REPORTISTICA_ATT_BASE
  ];

}
