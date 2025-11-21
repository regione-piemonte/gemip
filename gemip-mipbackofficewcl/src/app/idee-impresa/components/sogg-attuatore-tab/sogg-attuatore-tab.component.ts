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

import { Router } from '@angular/router';
import { Component, EventEmitter, Input, OnInit, Output, TemplateRef, ViewChild } from '@angular/core';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { SoggettoAttuatoreService } from '@utenti/services/soggetto-attuatore.service';
import { InfoColumnActionSettingModel } from '@shared/model/table-setting.model';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { Ruolo } from '@core/models/ruolo';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { ComuniAreeService } from '@shared/services/comuni-aree.service';
import { MatSelectChange } from '@angular/material/select';
import { CommonService } from '@core/services/common.service';
import { FormBuilder, Validators } from '@angular/forms';
import { IdeaDiImpresaRicerca } from '@core/models/ideaDiImpresaRicerca';

@Component({
  selector: 'app-sogg-attuatore-tab',
  templateUrl: './sogg-attuatore-tab.component.html',
  styleUrls: ['./sogg-attuatore-tab.component.scss']
})
export class SoggAttuatoreTabComponent implements OnInit {

  @Input() isStampa = false
  @Input() ideaImpresa?: IdeaDiImpresa;
  @Output() ricaricaIdea = new EventEmitter<undefined>();
  disableRegBase: boolean = false;

  // Riciclo le icone edit/delete usate per app-table
  superuser = false;
  soggettoModificabileInQuestoStato = false;
  sbloccoAreaTerritoriale = false;
  canSbloccoAreaTerritoriale = false;
  iconDelete = InfoColumnActionSettingModel.getDeleteIcon(() => { });
  iconEdit = InfoColumnActionSettingModel.getEditIcon(() => { });
  areeTerritoriali: AreaTerritoriale[] = [];
  soggettiAttuatori: SoggettoAttuatore[] = [];
  soggettoForm = this.fb.group({
    areaTerritoriale: ['', Validators.required],
    soggettoAttuatore: ['', Validators.required]
  })
  soggetto?: SoggettoAttuatore;
  idSoggettoSulForm?: number;

  constructor(
    private ideeImpresaService: IdeaImpresaService,
    private commonService: CommonService,
    private soggettoAttuatoreService: SoggettoAttuatoreService,
    private comuniAreeService: ComuniAreeService,
    private fb: FormBuilder,
    private router: Router,
    private dialog: MatDialog,
  ) { }

  ngOnInit(): void {
    this.soggetto = this.ideeImpresaService.ideeImp?.tutor?.soggettoAttuatore
    this.sbloccoAreaTerritoriale = this.ideeImpresaService.ideeImp?.ideaDiImpresa?.sbloccoAreaTerritoriale!;
    const ruolo: Ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!);
    if (ruolo.codiceRuolo && ruolo.codiceRuolo.includes('ALL')) {
      this.superuser = true;
    }
    if (this.ideaImpresa?.statoIdeaDiImpresa?.id !== undefined && [5, 8, 9, 12].includes(this.ideaImpresa.statoIdeaDiImpresa.id)) {
      this.canSbloccoAreaTerritoriale = true
    }
    this.disableRegBase = this.ideeImpresaService.canRegBaseModify;
  }

  @ViewChild('sceltaTutor') sceltaTutor!: TemplateRef<any>;
  confermaModificaSoggettoAttuatore() {

    this.comuniAreeService.getAreeTerritoriali().subscribe({
      next: (response) => {
        this.areeTerritoriali = response;
      }
    });
    this.soggettoForm.reset();
    this.dialog.open(this.sceltaTutor, {
      disableClose: true,
    });
  }

  loadSoggettiAttuatori($event: MatSelectChange) {
    this.commonService.getSoggettiAttuatoriByAreaTerritoriale($event.value).subscribe({
      next: (response) => {
        this.soggettiAttuatori = response.map(t => t.soggettoAttuatore!);
      }
    });
  }

  storeSoggettoAttuatore($event: MatSelectChange) {
    this.idSoggettoSulForm = $event.value;
  }

  modificaSoggettoAttuatore() {
    this.soggettoAttuatoreService.sceltaTutor(this.ideaImpresa?.id!, this.idSoggettoSulForm!).subscribe({
      next: ris => {
        let prova: IdeaDiImpresaRicerca;
        this.soggettoAttuatoreService.getTutorPerId(ris!.idTutor!).subscribe({
          next: ris => {
            this.ideeImpresaService.ideeImp!.tutor = ris!;
            this.soggetto = ris.soggettoAttuatore;
          }
        });
      }
    });
  }

  onResetTutor() {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(
        "Attenzione!",
        ['Il reset del tutor permetterà al cittadino di scegliere un nuovo tutor anche su area territoriale diversa. Verrà inviata una mail al cittadino e all\'attuale tutor. Si vuole procedere col reset?'],
        "card-body--warning", "",
        [
          new DialogButton('Annulla', 'btn btn--outline-primary', undefined, "annulla"),
          new DialogButton('Conferma', 'btn btn--danger', () => {
            this.soggettoAttuatoreService.resetTutor(this.ideaImpresa!.id!).subscribe()
            this.sbloccoAreaTerritoriale = true;
            this.ideeImpresaService.ideeImp!.ideaDiImpresa!.sbloccoAreaTerritoriale = true;
          }
          )
        ]
      ),
      disableClose: true,
      maxWidth: 650,
    })
  }
}
