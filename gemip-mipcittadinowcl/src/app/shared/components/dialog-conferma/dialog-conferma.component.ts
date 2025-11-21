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

import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { DialogButton, DialogSettings } from '../../models/dialog-settings.model';


@Component({
  selector: 'app-dialog-conferma',
  templateUrl: './dialog-conferma.component.html',
  styleUrls: ['./dialog-conferma.component.scss']
})
export class DialogConfermaComponent implements OnInit {

  icon = "";
  titolo = "";
  tipoMessaggio = "";
  message: string[] = [];
  action?: () => void;
  buttonList: DialogButton[] = [new DialogButton("CHIUDI", "btn btn--outline-primary", undefined, "chiudi finestra")];

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: DialogSettings
  ) { }

  ngOnInit(): void {


    this.titolo = this.data.title ? this.data?.title : "";
    this.message = this.data.content ? this.data?.content : [];
    this.buttonList = this.data.buttons ? this.data.buttons : this.buttonList;
    this.icon = this.data.dialogIcon ? this.data.dialogIcon : ""
  }

  get typeContent() {
    return this.data.contentType;
  }

  get contentClass() {
    return this.data.contentClass;
  }
}
