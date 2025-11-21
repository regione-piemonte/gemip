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

import { NavBarItem } from "../models/navbar-item.model";


export const MENU_DASHBOARD_LIST : NavBarItem[] =
[
  {
    label: 'Scegli dove e quando',
    path: '/dashboard/incontro-pre-accoglienza',
    completed: false,
    enabled: true,
    icon: 'dove_quando'
  }, //Dove e quando
  {
    label: 'Inserisci i tuoi dati personali',
    path: '/dashboard/dati-anagrafica',
    completed: false,
    enabled: false,
    icon: 'anagrafica'
  }, //anagrafica
  {
    label: 'Descrivi la tua idea d\'impresa',
    path: '/dashboard/idea-imprenditoriale',
    completed: false,
    enabled: false,
    icon: 'idea_impresa'
  }, //idea impresa
  {
    label: 'Verifica i dati e invia',
    path: '/dashboard/verifica-dati',
    completed: false,
    enabled: false,
    icon: 'verifica_invia'
  }, //verifica dati


  {
    label: 'Aiutaci a migliorare il servizio',
    path: '/dashboard/questionario-iniziale',
    completed: false,
    enabled: false,
    icon: 'questionario'
  }, //questionario
  {
    label: 'Scegli il soggetto attuatore (tutor)',
    path: '/dashboard/scelta-tutor',
    completed: false,
    enabled: false,
    icon: 'tutor'
  }, //tutor
];




