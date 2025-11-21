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

import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-faccina',
  templateUrl: './faccina.component.html',
  styleUrls: ['./faccina.component.scss']
})
export class FaccinaComponent {

  map: {[key: string] : any} = {
    'Molto soddisfacente': {
      image: 'assets/dashboard/questionari/sentiment_satisfied_black_24dp.svg',
      image_checked: 'assets/dashboard/questionari/sentiment_satisfied_white_24dp.svg',
      image_checked_background: '#007e00'
    },
    'Soddisfacente': {
      image: 'assets/dashboard/questionari/sentiment_neutral_black_24dp.svg',
      image_checked: 'assets/dashboard/questionari/sentiment_neutral_white_24dp.svg',
      image_checked_background: '#faba00'
    },
    'Non soddisfacente': {
      image: 'assets/dashboard/questionari/sentiment_dissatisfied_black_24dp.svg',
      image_checked: 'assets/dashboard/questionari/sentiment_dissatisfied_white_24dp.svg',
      image_checked_background: '#b20000'
    }
  };

  @Input()
  label!: string; // 'Molto soddisfacente'|'Soddisfacente'|'Non soddisfacente';

  @Input()
  checked = false;

  @Output()
  changeCheck = new EventEmitter<boolean>();

  constructor() { }

  toggle() {
    this.checked = !this.checked;
    this.changeCheck.emit(this.checked);
  }
}
