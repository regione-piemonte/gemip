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

import { CommonService } from '@core/services/common.service';
import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { UrlRouter } from '@main/const/url-router.model';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { FonteConoscenzaMip } from '@core/models/fonteConoscenzaMip';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { MatDialog } from '@angular/material/dialog';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogSettings } from '@shared/model/dialog-settings.model';
import { StatoIdeaDiImpresa } from '@core/models/statoIdeaDiImpresa';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { Ruolo } from '@core/models/ruolo';
import { SoggettoAttuatoreService } from '@utenti/services/soggetto-attuatore.service';
import { Location } from '@angular/common';
import { UtentiService } from '@utenti/services/utenti.service';

@Component({
  selector: 'app-idee-impresa-nuovo',
  templateUrl: './idee-impresa-nuovo.component.html',
  styleUrls: ['./idee-impresa-nuovo.component.scss']
})
export class IdeeImpresaNuovoComponent implements OnInit {

  altroSelezionato = false
  selectedTab = 0;
  disableAll = false
  maxLengthDescrizione = 4000;
  areeTerritoriali: AreaTerritoriale[] = []
  fonteConoscenzaList: FonteConoscenzaMip[] = []
  maxDate: Date = new Date();
  disableToggle: boolean = false;
  disableSaveButton: boolean = false;
  disableSbloccoArea: boolean = false;
  superuser: boolean = false;

  form = this.fb.group({
    titolo: ['', [Validators.required, Validators.maxLength(150)]],
    data: [''],
    area: [''],
    tipo: [''],
    descrizione: ['', [Validators.required, Validators.maxLength(4000)]],
    noteCommenti: ['', Validators.maxLength(4000)],
    commentiInterni: ['', Validators.maxLength(4000)],
    checkPrimoIncontroSvolto: [false],
    checkPattoServiziFSvolto: [false],
    checkValidazioneBusinessPlan: [false],
    dateValidazioneBusinessPlan: new FormControl<Date | undefined>({ value: undefined, disabled: false }),
    checkNonAmmissibileSvolto: [false],
    sbloccoAreaTerritoriale: [false],
    dataBP: [false],
    stati: [0],
    conoscenza: ['', [Validators.required]],
    altro: new FormControl({ value: "", disabled: true }), //modifica punto 7 post demo
    commento: [''],
    note: [''],
  });

  cambioGen = false;
  businessPlan = false;
  statoIdeaList: StatoIdeaDiImpresa[] = []
  statoIdea?: StatoIdeaDiImpresa;
  previousUrl: string = "";

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private fb: FormBuilder,
    private ideaImpService: IdeaImpresaService,
    private commonService: CommonService,
    private bagService: BagService,
    private soggettoAttuatoreService: SoggettoAttuatoreService,
    private location: Location,
    private utentiService: UtentiService
  ) { }

  ngOnInit(): void {
    this.bagService.titolo = "Modifica idee impresa"
    this.bagService.icona = IconsSettings.IDEE_IMPRESA_ICON

    this.ideaImpService.leftComponent = false;
    this.utentiService.leftComponent = false;

    if (!this.ideaImpService.ideeImp) {
      this.router.navigateByUrl(`${UrlRouter.urlIdeeImpresa}/gestione`)
    }

    this.form.controls.data.disable()
    this.form.controls.area.disable()
    this.form.controls.data.disable()
    this.form.controls.area.disable()
    this.form.controls.altro.disable() //richiesta punto 7 post demo

    this.commonService.getAreeTerritoriali().subscribe(
      r => this.areeTerritoriali = r
    )
    this.commonService.getFontiConoscenzaMip().subscribe(
      r => {
        this.fonteConoscenzaList = r

      }
    )
    this.commonService.getStatiIdeaImpresa().subscribe(r => {
      this.statoIdeaList = r;
      this.form.controls.stati.setValue(this.ideaImpService.ideeImp?.ideaDiImpresa?.statoIdeaDiImpresa?.id!)
    })
    this.setInitValueForm()

    if (!this.ideaImpService.canRegBaseModify) {
      this.form.controls.titolo.disable();
      this.form.controls.descrizione.disable();
      this.form.controls.commentiInterni.disable();
      this.form.controls.noteCommenti.disable();
      this.disableToggle = !this.disableToggle;
      this.disableSaveButton = true;
    }

    if (this.ideaImpService.ideeImp?.ideaDiImpresa?.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa === 'Inserito') {
      this.disableToggle = !this.disableToggle;
    }

    const ruolo: Ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!);
    if (ruolo.codiceRuolo && ruolo.codiceRuolo.includes('ALL')) {
      this.superuser = true;
    }
  }

  private getPreviousUrl(event: NavigationEnd): string {
    const currentUrlTree = this.router.parseUrl(event.url);
    const previousUrl = currentUrlTree.queryParams['_returnUrl'] || '/';
    return previousUrl;
  }

  print() {
    window.print()
  }

  ideaImpToPass?: IdeaDiImpresa

  get ideaImp() {
    return this.ideaImpToPass
  }
  areaTerritoriale: string = ""

  setInitValueForm() {
    let ideaImp = this.ideaImpService.ideeImp!.ideaDiImpresa!;
    this.ideaImpToPass = this.ideaImpService.ideeImp!.ideaDiImpresa!;
    this.areaTerritoriale = this.ideaImpService.ideeImp!.areaTerritoriale!.descrizioneAreaTerritoriale!;

    this.form.controls.titolo.setValue(ideaImp.titolo!);
    this.form.controls.data.setValue(ideaImp.dataInserim!.toLocaleString('it-IT',));
    this.form.controls.area.setValue(this.ideaImpService.ideeImp?.areaTerritoriale?.codiceAreaTerritoriale!);
    this.form.controls.tipo.setValue("");
    this.cambioGen = !!ideaImp.flgRicambioGenerazionale;
    this.form.controls.descrizione.setValue(ideaImp.descrizioneIdeaDiImpresa!);
    this.form.controls.noteCommenti.setValue(ideaImp.noteCommenti!);
    this.form.controls.commentiInterni.setValue(ideaImp.commentiInterni!);
    this.form.controls.sbloccoAreaTerritoriale.setValue(ideaImp.sbloccoAreaTerritoriale!)

    this.statoIdea = ideaImp.statoIdeaDiImpresa;

    if (ideaImp.statoIdeaDiImpresa?.id == 9) {
      this.form.controls.checkPrimoIncontroSvolto.setValue(true);
    }
    if (ideaImp.statoIdeaDiImpresa?.id! == 8) {
      this.form.controls.checkPrimoIncontroSvolto.setValue(true);
      this.form.controls.checkPattoServiziFSvolto.setValue(true);
    }

    if (ideaImp.statoIdeaDiImpresa?.id == 5) {
      this.form.controls.checkPrimoIncontroSvolto.setValue(true);
      this.form.controls.checkNonAmmissibileSvolto.setValue(true)
    } else if ((ideaImp.statoIdeaDiImpresa?.id!) < 8) {
      this.disableAll = true
    }
    if (ideaImp.statoIdeaDiImpresa?.id == 13) {
      this.form.controls.checkPrimoIncontroSvolto.setValue(true);
      this.form.controls.checkPattoServiziFSvolto.setValue(true);
      this.form.controls.checkValidazioneBusinessPlan.setValue(true);
      this.form.controls.checkValidazioneBusinessPlan.setValue(true);
      this.form.controls.checkValidazioneBusinessPlan.disable();
      this.form.controls.dateValidazioneBusinessPlan.setValue(ideaImp.dataValidBusinessPlan!);
      this.form.controls.dateValidazioneBusinessPlan.addValidators(Validators.required);
      this.form.controls.dateValidazioneBusinessPlan.updateValueAndValidity();
      this.disableSbloccoArea = true;
    }
    this.form.controls.conoscenza.setValue(ideaImp.fonteConoscenzaMip?.codiceFonteConoscenzaMip!);
    this.form.controls.altro.setValue(ideaImp.descrizioneAltraFonteConoscenzaMip!);
    ideaImp.descrizioneAltraFonteConoscenzaMip ? this.form.controls.altro.setValue(ideaImp.descrizioneAltraFonteConoscenzaMip) : this.form.controls.altro.setValue(null);
    this.selctionChange();
  }

  onToggle() {
    this.cambioGen = !this.cambioGen
  }

  onSalva() {
    this.bagService.resetError();
    if (!this.form.valid) {
      this.form.markAllAsTouched();
      return;
    }
    this.ideaImpService.putIdeaImpresa(this.updateIdeaImpresa()).subscribe(
      r => {

        this.ideaImpService.ideeImp!.ideaDiImpresa = r;
        this.ideaImpToPass = r
        this.dialog.open(DialogConfermaComponent, {
          data: new DialogSettings(
            "Avviso!",
            ['Idea d\'impresa aggiornata correttamente!'],
            "card-body--success"
          ),
          disableClose: true
        });

      }
    );
  }
  updateIdeaImpresa(): IdeaDiImpresa {
    let ideaImp = this.ideaImpService.ideeImp?.ideaDiImpresa!;

    ideaImp.flgRicambioGenerazionale = this.cambioGen == true ? "S" : undefined;
    ideaImp.titolo = this.form.controls.titolo.value!;
    ideaImp.descrizioneIdeaDiImpresa = this.form.controls.descrizione.value!;
    ideaImp.noteCommenti = this.form.controls.noteCommenti.value!;
    ideaImp.commentiInterni = this.form.controls.commentiInterni.value!;
    ideaImp.businessPlanValidato = this.form.controls.checkValidazioneBusinessPlan.value!;
    ideaImp.sbloccoAreaTerritoriale = this.form.controls.sbloccoAreaTerritoriale.value!;
    ideaImp.fonteConoscenzaMip!.codiceFonteConoscenzaMip = this.form.controls.conoscenza.value!;
    ideaImp.descrizioneAltraFonteConoscenzaMip = this.form.controls.altro.value!;
    ideaImp.codUserAggiorn = this.bagService.userInfo.codFisc
    let idStatoIdea = ideaImp.statoIdeaDiImpresa!.id!;

    if (idStatoIdea == 10) {
      if (ideaImp.idTutor) {
        idStatoIdea = 12
      } else {
        idStatoIdea = 7
      }
    }
    if (this.form.controls.checkValidazioneBusinessPlan.value && this.form.controls.dateValidazioneBusinessPlan.value!) {
      idStatoIdea = 13;
      this.form.controls.checkValidazioneBusinessPlan.disable();
      this.ideaImp!.businessPlanValidato = true;
      this.ideaImp!.dataValidBusinessPlan! = this.form.controls.dateValidazioneBusinessPlan.value!;
    } else
      if (this.form.controls.checkNonAmmissibileSvolto.value) {
        idStatoIdea = 5
      } else if (this.form.controls.checkPattoServiziFSvolto.value) {
        idStatoIdea = 8
      } else if (this.form.controls.checkPrimoIncontroSvolto.value) {
        idStatoIdea = 9
      } else if (ideaImp.idTutor) {
        idStatoIdea = 12
      }

    this.statoIdea = this.statoIdeaList.find(s => s.id == idStatoIdea)

    return {
      ...ideaImp,
      statoIdeaDiImpresa: this.statoIdea
    }
  }


  @ViewChild('stampa') stampa!: TemplateRef<any>;

  apriStampa() {
    this.dialog.open(this.stampa, {
      maxHeight: "80vh",
      width: "90vw",
      panelClass: "ClasseCss",
      disableClose: true
    });
  }

  selctionChange() {

    if (this.form.controls['conoscenza'].value == "07") {
      this.form.controls["altro"].addValidators([Validators.required, Validators.maxLength(250)])
      this.altroSelezionato = true
      this.form.controls["altro"].enable();
      this.form.controls["altro"].updateValueAndValidity()
      this.form.controls["altro"].disable(); //modifica punto 7 post demo
    } else {
      this.form.controls["altro"].clearValidators()
      this.altroSelezionato = false
      this.form.controls["altro"].setValue(null);
      this.form.controls["altro"].updateValueAndValidity()
      this.form.controls["altro"].disable();
    }
  }

  get isSaved() {
    return false;
  }

  get disabledCheck(): boolean {
    //return this.form.controls.checkAbbandonataSvolto.value! || this.form.controls.checkNonAmmissibileSvolto.value!
    return this.form.controls.checkNonAmmissibileSvolto.value!
  }

  onBP() {
    if (this.form.controls.checkValidazioneBusinessPlan.value) {
      this.form.controls.dateValidazioneBusinessPlan.addValidators(Validators.required)
      this.form.controls.dateValidazioneBusinessPlan.updateValueAndValidity();
    } else {
      this.form.controls.dateValidazioneBusinessPlan.clearValidators();
      this.form.controls.dateValidazioneBusinessPlan.updateValueAndValidity();
    }
  }

  deleteSoggettoAttuatore() {
    let ideaImp = this.ideaImpService.ideeImp!.ideaDiImpresa!;
    this.soggettoAttuatoreService.resetTutor(ideaImp.id!).subscribe({
      next: (response) => {
        // do nothing
      }
    });
  }
  tornaIndietro(): void{
    this.location.back();
  }
}
