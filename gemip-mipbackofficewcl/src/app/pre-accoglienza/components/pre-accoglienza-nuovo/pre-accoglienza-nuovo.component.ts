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

import { BagService } from '@core/services/bag.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { IncontroPreAccoglienzaInsert } from '@core/models/incontroPreAccoglienzaInsert';
import { IncontroPreaccoglienza } from '@core/models/incontroPreaccoglienza';
import { IncontroPreaccoglienzaAreaTerritoriale } from '@core/models/incontroPreaccoglienzaAreaTerritoriale';
import { LuogoIncontro } from '@core/models/luogoIncontro';
import { UserInfo } from '@core/models/userInfo';
import { Location } from '@angular/common';

import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';

import { ComuniAreeService } from '@shared/services/comuni-aree.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { CittadinoIncontroPreaccoglienza } from '@core/models/cittadinoIncontroPreaccoglienza';


@Component({
  selector: 'app-pre-accoglienza-nuovo',
  templateUrl: './pre-accoglienza-nuovo.component.html',
  styleUrls: ['./pre-accoglienza-nuovo.component.scss']
})

export class PreAccoglienzaNuovoComponent implements OnInit {
  incontro?: IncontroPreaccoglienza;
  selectedTab = 0;
  maxLengthDescrizione = 500;
  areeTerritoriali: AreaTerritoriale[] = []
  areeTerritorialiSelezionate: AreaTerritoriale[] = []
  flgDaRemoto = false;
  sedi: LuogoIncontro[] = []
  sediFiltrate: LuogoIncontro[] = []
  //descDefault: string = "pre-accoglienza in presenza"
  flgIncontroTelefonico: boolean = false;
  disableIncontroTelefonico: boolean = true;
  listaIdeeImpresa: CittadinoIncontroPreaccoglienza[] = [];

  isNuovo: boolean = true;
  isModifica: boolean = true;
  codUser: string = ""
  user!: UserInfo
  isRegionaleBase: boolean = false;
  numeroIdeessociate: number = 0

  pattern = new RegExp(
    '^([a-zA-Z]+:\\/\\/)?' + // protocol
    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
    '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR IP (v4) address
    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
    '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
    '(\\#[-a-z\\d_]*)?$', // fragment locator
    'i'
  );

  form = this.fb.group({
    data: new FormControl<Date | undefined>({ value: undefined, disabled: true }, Validators.required),
    numMax: ['', [Validators.required, Validators.pattern('^[0-9]{1,2}')]],
    oraInizio: [{ value: '', disabled: true }, [Validators.required]],
    area: [{ value: '', disabled: true }, [Validators.required]],
    descrizione: ['pre-accoglienza in presenza', [Validators.required]],
    luogo: [{ value: '', disabled: true }, [Validators.required]],
    incontroTelefonico: [false],
    note: [''],
    link: [{ value: '', disabled: false }, Validators.pattern(this.pattern)]
  });

  myformSendComunicazione: FormGroup = this.fb.group({
    comunicazioneInviareA: [''],
    comunicatoOggetto: ['', Validators.required],
    comunicatoCorpo: ['', Validators.required]
  });;

  //--------------------------------------

  constructor(
    private fb: FormBuilder,
    private dialog: MatDialog,
    private comuniAreeService: ComuniAreeService,
    private incontriPreAccoglienzaService: PreAccoglienzaService,
    private IncontriPreaccoglienzaBag: PreAccoglienzaBagService,
    private router: Router,
    private bagService: BagService,
    private location: Location
  ) {

  }

  ngOnInit(): void {
    if (this.router.url.endsWith("nuovo")) {
      this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza = undefined
      this.isModifica = false;
      this.IncontriPreaccoglienzaBag.leftComponent = true;
    }
    if (this.router.url === "/pre-accoglienza/modifica") {
      this.IncontriPreaccoglienzaBag.leftComponent = false;
    }
    if (this.router.url.endsWith("modifica") && !this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
      this.router.navigateByUrl("/pre-accoglienza/gestione")
    }
    this.bagService.titolo = "Incontro pre-accoglienza"
    this.bagService.icona = IconsSettings.PRE_ACCOGLIENZA_ICON

    this.IncontriPreaccoglienzaBag.getIdeeImpresaIncontro().subscribe(
      data => this.listaIdeeImpresa = data
    )
    this.user = JSON.parse(sessionStorage.getItem("_userInfo")!)
    this.isRegionaleBase = (JSON.parse(sessionStorage.getItem("_ruolo")!)).codiceRuolo! == 'GEMIP_REGIONALE_BASE';
    this.codUser = this.user.codFisc!
    this.comuniAreeService.getAreeTerritoriali().subscribe({
      next: ris => {
        this.areeTerritoriali = ris;
      }
    })
    this.incontriPreAccoglienzaService.getLuoghiIncontro().subscribe({
      next: ris => {
        this.sedi = ris;
        if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
          this.incontriPreAccoglienzaService.getIncontroById(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza).subscribe({
            next: ris => {
              this.IncontriPreaccoglienzaBag.incontroPreaccoglienza = ris.incontroPreaccoglienza;
              this.incontro = this.IncontriPreaccoglienzaBag.incontroPreaccoglienza;
              this.numeroIdeessociate = this.IncontriPreaccoglienzaBag.numeroIdeeAssociate;
              this.popolaDatiIncontro(ris);
            }
          })
          this.isNuovo = false

        }
      }
    })

    if (!this.canModify) {
      this.form.disable()
    }
    if (!this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
      this.form.enable()
    }
  }

  print() {
    window.print()
  }

  incontroInsert!: IncontroPreAccoglienzaInsert
  popolaDatiIncontro(incontro: IncontroPreAccoglienzaInsert) {
    this.incontroInsert = incontro;

    this.form.controls["data"].setValue(new Date(incontro.incontroPreaccoglienza?.dataIncontro!))
    this.form.controls["oraInizio"].setValue(new Date(incontro.incontroPreaccoglienza?.dataIncontro!).toLocaleString('it-IT', { hour: "2-digit", minute: "2-digit" }))
    this.form.controls["numMax"].setValue(incontro.incontroPreaccoglienza?.numMaxPartecipanti!.toString()!);
    if (this.incontroInsert.incontroPreaccoglienza?.flgIncontroTelefonico === "S") {
      this.form.controls["incontroTelefonico"].setValue(true);
    } else {
      this.form.controls["incontroTelefonico"].setValue(false);
    }

    if (!incontro.incontroPreaccoglienza?.flgIncontroErogatoDaRemoto) {
      this.form.controls["area"].setValue(incontro!.areaTerritoriale![0].codiceAreaTerritoriale!)
      this.sediFiltrate = this.sedi.filter(sede => sede.areaTerritoriale?.codiceAreaTerritoriale == incontro!.areaTerritoriale![0].codiceAreaTerritoriale!)
      this.form.controls["luogo"].setValue(incontro.incontroPreaccoglienza?.luogoIncontro!.id!.toString()!)
      this.flgDaRemoto = false;
      this.form.controls["link"].clearValidators();
      this.form.controls["link"].updateValueAndValidity();
      this.form.controls["link"].setValue(null);
      this.form.controls["luogo"].addValidators(Validators.required);
      this.form.controls["luogo"].updateValueAndValidity();
    } else {
      this.areeTerritorialiSelezionate = incontro.areaTerritoriale!;
      this.form.controls["link"].setValue(incontro.incontroPreaccoglienza?.linkIncontroRemoto!)
      this.flgDaRemoto = true;
      this.form.controls["link"].addValidators([Validators.required, Validators.pattern(this.pattern)]);
      this.form.controls["link"].updateValueAndValidity();
      this.form.controls["luogo"].clearValidators();
      this.form.controls["luogo"].setValue(null);
      this.form.controls["luogo"].updateValueAndValidity();
    }
    this.form.controls["descrizione"].setValue(incontro.incontroPreaccoglienza?.denominazione!)
    this.form.controls["note"].setValue(incontro.incontroPreaccoglienza?.note!)
  }

  onAreaTerritorialeSelected() {
    if (!this.flgDaRemoto) {
      this.sediFiltrate = this.sedi.filter(luogo => luogo.areaTerritoriale?.codiceAreaTerritoriale == this.form.controls["area"].value)
    }
  }

  onSalva() {
    this.bagService.resetError();
    this.form.markAllAsTouched()

    if (this.form.valid && this.isNuovo) {
      if (+this.form.controls['numMax'].value! > 50) {
        this.openDialog("Attenzione", ["il numero massimo di partecipanti non può essere più di 50"], "card-body--danger")
        return;
      }
      if (!this.flgDaRemoto) {

        let dInc: Date = new Date(this.form.controls["data"].value!)
        let oraIncontro = this.form.controls["oraInizio"].value!.split(":")
        dInc.setHours(+oraIncontro[0]);
        dInc.setMinutes(+oraIncontro[1]);
        this.incontriPreAccoglienzaService.getIncontriPreaccoglienzaForControlloEsistente(this.sedi.filter(luogo => luogo.id == +this.form.controls["luogo"].value!)[0].id!, dInc).subscribe({
          next: date => {
            if (!date) {
              this.incontriPreAccoglienzaService.insertIncontroPreAccoglienza(this.createIncontroInsert()).subscribe({
                next: ris => {
                  this.incontro = ris.incontroPreaccoglienza;
                  this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza = ris.incontroPreaccoglienza!.id!
                  this.confermaSalvataggio()
                  this.isNuovo = false;
                  this.form.markAsUntouched();
                }
              })
            }
            else {
              this.openDialog("Attenzione", ["Non puoi creare questo incontro, è gia presente un altro incontro con la stessa data/ora e luogo"], "card-body--danger")
              return;
            }
          }
        })
      }
      else {
        this.incontriPreAccoglienzaService.insertIncontroPreAccoglienza(this.createIncontroInsert()).subscribe({
          next: ris => {
            this.incontro = ris.incontroPreaccoglienza;
            this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza = ris.incontroPreaccoglienza!.id!
            this.confermaSalvataggio()
            this.isNuovo = false;
            this.form.markAsUntouched();
          }
        })
      }
    } else if (this.form.valid) {
      if (+this.form.controls['numMax'].value! > 50) {
        this.openDialog("Attenzione", ["il numero massimo di partecipanti non può essere più di 50"], "card-body--danger")
        return;
      }
      if (+this.form.controls['numMax'].value! === 0) {
        this.openDialog("Attenzione", ["il numero massimo di partecipanti non può essere 0"], "card-body--danger")
        return;
      }
      if (!(+this.form.controls["numMax"].value! < this.numeroIdeessociate)) {
        this.incontriPreAccoglienzaService.updateIncontroPreaccoglienza(this.createIncontroInsert()).subscribe({
          next: ris => {
            this.incontro = ris.incontroPreaccoglienza;
            this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza = ris.incontroPreaccoglienza!.id!
            this.confermaSalvataggio()
            this.form.markAsUntouched();
          }
        })
      } else {
        this.openDialog("Attenzione", ["il numero massimo di partecipanti non può essere inferiore al numero di partecipanti già iscritti all'incontro"], "card-body--danger")
      }
    }
  }

  createAssociazioneAreaTerritoriale(inc: IncontroPreaccoglienza, area: AreaTerritoriale): IncontroPreaccoglienzaAreaTerritoriale {
    return {
      areaTerritoriale: area,
      incontroPreaccoglienza: inc,
      codUserInserim: this.codUser,
      codUserAggiorn: this.codUser
    }
  }

  createIncontroPreaccoglienza(): IncontroPreaccoglienza {
    let dInc: Date = new Date(this.form.controls["data"].value!)
    let oraIncontro = this.form.controls["oraInizio"].value!.split(":")
    dInc.setHours(+oraIncontro[0]);
    dInc.setMinutes(+oraIncontro[1]);
    return {
      ...this.incontro,
      denominazione: this.form.controls["descrizione"].value!,
      flgIncontroErogatoDaRemoto: this.flgDaRemoto ? "S" : undefined,
      flgIncontroTelefonico: this.form.controls["incontroTelefonico"].value ? "S" : undefined,
      numMaxPartecipanti: +this.form.controls["numMax"].value!,
      operatoreCreazione: {
        id: this.user.idOperatore,
        codFiscaleUtente: this.codUser
      },
      dataIncontro: dInc,
      luogoIncontro: this.flgDaRemoto ? undefined : this.sedi.filter(luogo => luogo.id == +this.form.controls["luogo"].value!)[0],
      linkIncontroRemoto: this.flgDaRemoto ? this.form.controls["link"].value! : undefined,
      note: this.form.controls["note"].value!,
      codUserAggiorn: this.codUser,
      codUserInserim: this.codUser,
      dataInserim: this.incontro?.dataInserim

    }
  }

  createIncontroInsert(): IncontroPreAccoglienzaInsert {
    let tmp: IncontroPreAccoglienzaInsert
    if (this.flgDaRemoto) {

      let areeSelezionate: any = (this.form.controls["area"]!.value)

      tmp = {
        incontroPreaccoglienza: this.createIncontroPreaccoglienza(),
        areaTerritoriale: (areeSelezionate as AreaTerritoriale[]),
        codUserAggiorn: this.codUser,
        codUserInserim: this.codUser,
      }
    } else {
      tmp = {
        incontroPreaccoglienza: this.createIncontroPreaccoglienza(),
        areaTerritoriale: this.areeTerritoriali.filter(area => area.codiceAreaTerritoriale == this.form.controls["area"]!.value),
        codUserAggiorn: this.codUser,
        codUserInserim: this.codUser,
      }
    }
    if (!this.isNuovo) {
      if (this.flgDaRemoto) {

        let areeSelezionate: any = (this.form.controls["area"]!.value)

        tmp = {
          incontroPreaccoglienza: this.createIncontroPreaccoglienzaUpdate(),
          areaTerritoriale: (areeSelezionate as AreaTerritoriale[]),
          codUserAggiorn: this.codUser,
          codUserInserim: this.codUser,
        }
      } else {
        tmp = {
          incontroPreaccoglienza: this.createIncontroPreaccoglienzaUpdate(),
          areaTerritoriale: this.areeTerritoriali.filter(area => area.codiceAreaTerritoriale == this.form.controls["area"]!.value),
          codUserAggiorn: this.codUser,
          codUserInserim: this.codUser,
        }
      }
    }
    return tmp;
  }

  createIncontroPreaccoglienzaUpdate() {
    return {
      ...this.incontro,
      numMaxPartecipanti: +this.form.controls["numMax"].value!,
      note: this.form.controls["note"].value!,
      denominazione: this.form.controls["descrizione"].value!,
      linkIncontroRemoto: this.flgDaRemoto ? this.form.controls["link"].value! : undefined,
      codUserAggiorn: this.codUser,
    }
  }

  myFilter = (d: any): boolean => {
    let day: Date;
    if (d)
      day = new Date(d)
    else
      day = new Date()
    return day > new Date();
  };

  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose: true
    })
  }

  confermaSalvataggio() {
    this.openDialog("Avviso", ["I dati sono stati salvati correttamente"], "card-body--success")
  }

  onToggleDaRemotoChange() {
    this.flgDaRemoto = !this.flgDaRemoto;
    if (this.flgDaRemoto) {
      this.form.controls["link"].addValidators(Validators.required);
      this.form.controls["link"].updateValueAndValidity();
      this.form.controls["luogo"].clearValidators();
      this.form.controls["luogo"].setValue(null);
      this.form.controls["luogo"].updateValueAndValidity();
      this.form.controls["descrizione"].setValue("pre-accoglienza online");
      this.disableIncontroTelefonico = false;
    } else {
      this.form.controls["link"].clearValidators();
      this.form.controls["link"].updateValueAndValidity();
      this.form.controls["link"].setValue(null);
      this.form.controls["luogo"].addValidators(Validators.required);
      this.form.controls["luogo"].updateValueAndValidity();
      this.form.controls["descrizione"].setValue("pre-accoglienza in presenza");
      this.form.controls["incontroTelefonico"].setValue(false);
      this.disableIncontroTelefonico = true;
      this.flgIncontroTelefonico = false;
    }
  }

  onIncontroTelefonico() {
    this.flgIncontroTelefonico = !this.flgIncontroTelefonico;
    if (this.flgIncontroTelefonico) {
      this.form.controls["descrizione"].setValue("pre-accoglienza telefonica");
    } else {
      this.form.controls["descrizione"].setValue("pre-accoglienza online");
    }
  }

  compareFnIncontro(a: AreaTerritoriale, b: AreaTerritoriale) {

    return a.codiceAreaTerritoriale == b.codiceAreaTerritoriale
  }

  get isSaved(): boolean {
    return this.incontro != undefined;
  }

  get canModify() {
    return this.incontriPreAccoglienzaService.canModify;
  }

  inviaComunicazione() {
    const encodedText = this.encodeContextLink(this.myformSendComunicazione.get('comunicatoCorpo')?.value);
    console.log(encodedText)
    if (this.myformSendComunicazione.valid && this.myformSendComunicazione.get('comunicatoOggetto')?.value &&
      encodedText)
      if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
        this.incontriPreAccoglienzaService.inviaComunicazione(
          this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza,
          this.myformSendComunicazione.get('comunicatoOggetto')?.value,
          encodedText,
          this.myformSendComunicazione.get('comunicazioneInviareA')?.value
        ).subscribe({
          next: (data) => {
            this.openDialog(
              '',
              ["Invio mail ai partecipanti selezionati "],
              'card-body--success'
            );
            this.myformSendComunicazione.reset({
              comunicazioneInviareA: "",
            });
          },
          error: (error) => {
            let msg = ["Si è verificato un errore nell'invio delle mail."];
            if (error && error.status == 406) msg.push("Nessun destinatario.");

            this.openDialog(
              '',
              msg,
              'card-body--danger'
            )
          }
        });
      }
  }

  encodeContextLink(text: string) {
    const urlRegex = /(https?:\/\/[^\s]+)/g; // RegEx per trovare l'URL nel testo
    const matches = [...text.matchAll(urlRegex)]; // Trova tutti gli URL nel testo
    if (matches.length > 0) {
      matches.forEach(match => {
        const url = match[0];
        const urlObj = new URL(url);
        const contextParam = urlObj.searchParams.get("context");
        if (contextParam) {
          const encodedContext = encodeURIComponent(contextParam);
          urlObj.searchParams.set("context", encodedContext);
          text = text.replace(url, urlObj.toString());
        }
      });
    }
    return text;
  }

  onTabSelected(tabIndex: number) {
    if (tabIndex === 4 || tabIndex === 1) {
      this.incontriPreAccoglienzaService.getLuoghiIncontro().subscribe({
        next: ris => {
          this.sedi = ris;
          if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
            this.incontriPreAccoglienzaService.getIncontroById(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza).subscribe({
              next: ris => {
                this.IncontriPreaccoglienzaBag.incontroPreaccoglienza = ris.incontroPreaccoglienza;
                this.incontro = this.IncontriPreaccoglienzaBag.incontroPreaccoglienza;
                this.numeroIdeessociate = this.IncontriPreaccoglienzaBag.numeroIdeeAssociate;
                this.popolaDatiIncontro(ris);
              }
            })
            this.isNuovo = false
          }
        }
      });
      this.IncontriPreaccoglienzaBag.aggiornaOpertoriStampa.next(true);
    }
  }

  tornaIndietro(): void {
    this.location.back();
  }

  onGoToNew() {
    if (this.form.touched) {
      this.openDialog("Attenzione", ["Se continui con un nuovo incontro i dati non salvati verranno persi. Vuoi continuare?"], "card-body--warning",
        [
          new DialogButton('No', 'btn btn--outline-primary'),
          new DialogButton('Si', 'btn btn--outline-primary', () => this.onReset()),
        ]
      );
    } else {
      this.onReset();
    }
  }

  onReset() {
    this.form.reset({
      descrizione: 'pre-accoglienza in presenza'
    });
    this.flgDaRemoto = false;
    this.isNuovo = true
    this.incontro = undefined;
    this.IncontriPreaccoglienzaBag.resetBag();
  }
}
