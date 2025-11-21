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

import { Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import {
  FormArray,
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import {
  Cittadino,
  Domanda,
  QuestionarioService,
  Risposta,
  RispostaCompilazione,
} from 'src/app/integration';
import { NavBarItem } from '../../models/navbar-item.model';
import { Router } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { DialogConfermaComponent } from 'src/app/shared/components/dialog-conferma/dialog-conferma.component';
import {
  DialogButton,
  DialogSettings,
} from 'src/app/shared/models/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';
import { MatRadioChange } from '@angular/material/radio';

@Component({
  selector: 'app-questionario',
  templateUrl: './questionario.component.html',
  styleUrls: ['./questionario.component.scss'],
})
export class QuestionarioComponent implements OnInit {
  domande: Domanda[] = [];
  reMapDomandeArray: Domanda[] = [];
  risposte_questionario!: FormGroup | any;
  show_textarea: boolean = false;
  questionario!: NavBarItem;
  lista2: NavBarItem[] = [];
  cittadino!: Cittadino | null;
  risposte: RispostaCompilazione[] = [];
  idQuestionario?: number;
  showDom = false;
  noConditionSelected = false;
  requiredTextarea = false;
  textAreaMap: { [idDomanda: number]: string } = {};
  textAreaMapFaccine: { [idDomanda: number]: string } = {};
  faccineMap: { [idDomanda: number]: boolean } = {};
  textareaValue: string = '';
  isTextareaClicked: boolean = false;
  idRispostaMulti!: number;
  questionarioInviato: boolean = false;
  @ViewChild('attenzione') attenzione!: TemplateRef<any>;

  constructor(
    private fb: FormBuilder,
    private questionarioService: QuestionarioService,
    private router: Router,
    private dashboardService: DashboardService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.initForm();
    this.getQuestionario();
    this.scrollToTop();
    this.dashboardService.lista.subscribe((lista) => {
      this.lista2 = lista;
      this.questionario = this.lista2[4];
    });
  }

  scrollToTop(): void {
    try {
      window.scrollTo(0, 0);
    } catch (err) {
      console.error(err);
    }
  }

  getQuestionario() {
    // get questionario cittadino
    this.cittadino = JSON.parse(sessionStorage.getItem('cittadino')!);
    this.questionarioService
      .getQuestionarioCorrente(Number(this.cittadino?.idCittadino))
      .subscribe({
        next: (data) => {
          this.domande = data.domande;
          if (this.domande.length == 0) this.questionarioInviato = true;
          this.idQuestionario = data.idQuestionario;
          this.initForm();
        },
      });
  }

  initForm() {
    const questionarioControls: { [key: string]: any } = {};
    this.domande.forEach((d) => {
      if (!d.idRispostaCondizionale) {
        if (
          d.tipoDomanda === Domanda.TipoDomandaEnum.Aperta ||
          d.tipoDomanda === Domanda.TipoDomandaEnum.Chiusa
        ) {
          questionarioControls[d.idDomanda] = new FormControl(
            undefined,
            Validators.required
          );
        } else {
          questionarioControls[d.idDomanda] = new FormArray<FormControl>([]);

          d.risposte.forEach((risposta) => {
            if (
              !this.UtilVoid(risposta.flgRichiestoDettaglio) &&
              risposta.flgRichiestoDettaglio
            ) {
              questionarioControls[d.idDomanda].push(
                new FormControl(undefined)
              );
            } else {
              questionarioControls[d.idDomanda].push(new FormControl(false));
            }
          });
        }
      }
    });
    this.risposte_questionario = this.fb.group(questionarioControls);
  }

  reloadForm() {
    const newQuestionarioControls: { [key: string]: any } = {};
    if (this.showDom) {
      //aggiungo le domande in piu (quelle che hanno risposta condizionale)
      this.domande.forEach((d) => {
        if (d.idRispostaCondizionale) {
          if (
            d.tipoDomanda === Domanda.TipoDomandaEnum.Aperta ||
            d.tipoDomanda === Domanda.TipoDomandaEnum.Chiusa
          ) {
            newQuestionarioControls[d.idDomanda] = new FormControl(
              undefined,
              Validators.required
            );
          } else {
            newQuestionarioControls[d.idDomanda] = new FormArray<FormControl>(
              []
            );

            d.risposte.forEach((risposta) => {
              if (
                !this.UtilVoid(risposta.flgRichiestoDettaglio) &&
                risposta.flgRichiestoDettaglio
              ) {
                newQuestionarioControls[d.idDomanda].push(
                  new FormControl(undefined)
                );
              } else {
                newQuestionarioControls[d.idDomanda].push(
                  new FormControl(false)
                );
              }
            });
          }
        }
      });
      //aggiungo il nuovo form group a quello originale
      for (const key in newQuestionarioControls) {
        //this.risposte_questionario.controls[key] = newQuestionarioControls[key];
        this.risposte_questionario.addControl(
          key,
          newQuestionarioControls[key]
        );
      }
    } else {
      //tolgo le domande in più dal form group (quelle che iniziano per una lettera)
      this.domande.forEach((d) => {
        if (d.idRispostaCondizionale) {
          for (const key in this.risposte_questionario.controls) {
            if (key == d.idDomanda.toString()) {
              this.risposte_questionario.removeControl(d.idDomanda);
            }
          }
        }
      });
    }
  }

  onSubmit() {
    this.onSaveRisposte();

    if (this.idQuestionario) {
      this.scrollToTop();
      this.questionarioService.getQuestionarioFaseCorrente(Number(this.cittadino?.idCittadino)).subscribe({
        next: ris => {
          if (ris !== null) {
            this.questionarioService
              .salvaRisposteQuestionario(
                this.risposte,
                Number(this.cittadino?.idCittadino),
                this.idQuestionario!
              )
              .subscribe({
                next: (data) => {
                  this.confermaSalvataggio();
                  this.router.navigate(['/dashboard/home']);
                },
              });
          } else {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          }
        }
      })

    }

  }

  onSaveRisposte() {
    //risposte form
    let textMap = { ...this.textAreaMap, ...this.textAreaMapFaccine };
    Object.keys(this.risposte_questionario.value).forEach((key, i) => {
      const value = this.risposte_questionario.value[key];
      //caso checkbox
      if (value instanceof Array) {
        value.forEach((elem, i) => {
          if (elem == true) {
            const idRisposta = this.getIdRisposta(key, i);
            if (idRisposta !== undefined)
              this.risposte.push({
                idDomanda: parseInt(key),
                idRispostaScelta: Number(idRisposta),
              });
          } else if (elem != false && !this.UtilVoid(elem)) {
            const rispostaLibera = value[i];
            this.domande.forEach((domanda) => {
              domanda.risposte.forEach((risposta) => {
                if (risposta.flgRichiestoDettaglio) {
                  this.idRispostaMulti = risposta.idRisposta;
                }
              });
            });
            this.risposte.push({
              idDomanda: parseInt(key),
              idRispostaScelta: this.idRispostaMulti,
              rispostaLibera: rispostaLibera,
            });
          }
        });
      } else if (this.domande[i].tipoDomanda == 'chiusa') {
        //caso radio button
        if (!this.showDom) {
          this.reMapDomandeArray = this.reMapDomande();
          this.reMapDomandeArray[i].risposte.forEach((risposta) => {
            if (risposta.testoRisposta == value) {
              const idRisposta = risposta.idRisposta;
              if (risposta.flgRichiestoDettaglio) {
                this.risposte.push({
                  idDomanda: parseInt(key),
                  idRispostaScelta: idRisposta,
                  rispostaLibera: textMap[this.reMapDomandeArray[i].idDomanda],
                });
              } else {
                this.risposte.push({
                  idDomanda: parseInt(key),
                  idRispostaScelta: idRisposta,
                });
              }
            }
          });
        } else {
          this.domande[i].risposte.forEach((risposta) => {
            if (risposta.testoRisposta == value) {
              const idRisposta = risposta.idRisposta;
              if (risposta.flgRichiestoDettaglio) {
                this.risposte.push({
                  idDomanda: parseInt(key),
                  idRispostaScelta: idRisposta,
                  rispostaLibera: textMap[this.domande[i].idDomanda],
                });
              } else {
                this.risposte.push({
                  idDomanda: parseInt(key),
                  idRispostaScelta: idRisposta,
                });
              }
            }
          });
        }
      } else {
        //caso textatea
        this.risposte.push({
          idDomanda: parseInt(key),
          rispostaLibera: value,
        });
      }
    });
  }

  getIdRisposta(idDomanda: string, index: number) {
    let domanda: Domanda | undefined = this.domande.find(
      (elem) => elem.idDomanda == Number(idDomanda)
    );
    if (domanda) {
      const idRisposta: Number | undefined = domanda.risposte.find(
        (elem, i) => i == index
      )?.idRisposta;
      return idRisposta;
    }
    return undefined;
  }

  getTipoDomanda(idDomanda: string) {
    return this.domande[Number(idDomanda)].tipoDomanda;
  }

  showTextArea(event: any, domandeIndex: number, indexCtrl: number) {
    this.show_textarea = !this.show_textarea;
    let idDomanda: string = '';
    Object.keys(this.risposte_questionario.value).forEach((key, i) => {
      if (i == domandeIndex) {
        idDomanda = key;
      }
    });
    const formArray = this.risposte_questionario.get(
      idDomanda.toString()
    ) as FormArray;
    if (event.checked) {
      formArray.controls.forEach((control, i) => {
        if (i == indexCtrl) {
          control.setValidators(Validators.required);
        }
      });
    } else {
      formArray.controls.forEach((control, i) => {
        if (i == indexCtrl) {
          control.clearValidators();
        }
      });
    }
  }

  checkCampi() {
    if (this.show_textarea) {
      return this.risposte_questionario.status !== 'VALID';
    } else if (this.esisteMultiRisposta()) {
      return this.risposte_questionario.status == 'VALID' &&
        !this.controllaCheckbox();
    } else if (this.requiredTextarea) {
      let check = false;
      //controllo se le textaree non sono vuote
      for (let idDomanda in this.textAreaMap) {
        if (
          this.textAreaMap[idDomanda] == '' ||
          this.textAreaMap[idDomanda] == undefined
        ) {
          check = check || true;
        }
      }
      //controllo se le faccine textaree non sono vuote
      let check1 = false;
      for (let idDomanda in this.textAreaMapFaccine) {
        if (this.textAreaMapFaccine[idDomanda] == '') {
          check1 = check1 || true;
        }
      }
      if (!check) {
        let check2 = false;
        for (let key in this.faccineMap) {
          check2 = check2 || this.faccineMap[key];
        }
        if (check2) {
          if (Object.keys(this.textAreaMapFaccine).length == 0) {
            return true;
          } else if (check) {
            return true;
          } else if (check1) {
            return true;
          } else {
            return this.risposte_questionario.status !== 'VALID';
          }
        } else {
          return this.risposte_questionario.status !== 'VALID';
        }
      } else {
        return true;
      }
    } else {
      //controllo i valori della mappa delle text area faccine
      let check1 = false;
      for (let idDomanda in this.textAreaMapFaccine) {
        if (this.textAreaMapFaccine[idDomanda] == '') {
          check1 = check1 || true;
        }
      }
      //controllo se alemeno una faccina =( e' cliccata, cioe' nella mappa e' true)
      let check2 = false;
      for (let key in this.faccineMap) {
        check2 = check2 || this.faccineMap[key];
      }
      if (check2) {
        if (Object.keys(this.textAreaMapFaccine).length == 0) {
          return true;
        } else if (check1) {
          return true;
        } else {
          return this.risposte_questionario.status !== 'VALID';
        }
      } else {
        return this.risposte_questionario.status !== 'VALID';
      }
    }
  }

  controllaCheckbox() {
    let check = false;
    this.domande.forEach((domanda, i) => {
      if (domanda.tipoDomanda == Domanda.TipoDomandaEnum.ChiusaSceltaMultipla) {
        domanda.risposte.forEach((risposta, j) => {
          if (!risposta.flgRichiestoDettaglio) {
            check =
              check ||
              this.risposte_questionario?.get(domanda.idDomanda.toString())
                ?.value[j];
          }
        });
      }
    });
    return check;
  }

  esisteMultiRisposta() {
    let check = false;
    this.domande.forEach((domanda) => {
      if (domanda.tipoDomanda == 'chiusa_scelta_multipla') check = true;
    });
    return check;
  }

  UtilVoid(elem: any) {
    return elem === null || elem === undefined;
  }

  isDomandaFaccine(d: Domanda) {
    if (
      d.risposte == undefined ||
      d.risposte == null ||
      d.risposte.length == 0 ||
      d.risposte.length > 3
    ) {
      return false;
    }
    let x = d.risposte.find(
      (r) =>
        r.testoRisposta != 'Molto soddisfacente' &&
        r.testoRisposta != 'Soddisfacente' &&
        r.testoRisposta != 'Non soddisfacente'
    );
    return x === undefined;
  }

  changeRisposta(idDomanda: number, r: Risposta, checked: boolean) {
    if (r.testoRisposta === 'Non soddisfacente') {
      this.faccineMap[idDomanda] = true;
      this.textAreaMapFaccine[idDomanda] = '';
    } else {
      this.faccineMap[idDomanda] = false;
      delete this.textAreaMapFaccine[idDomanda];
    }
    if (checked) {
      this.risposte_questionario
        .get(idDomanda.toString())
        .setValue(r.testoRisposta);
    } else {
      this.risposte_questionario.get(idDomanda.toString()).reset();
    }
  }

  openDialog(
    title: string,
    msg: string[],
    cssClass: string,
    buttons?: DialogButton[]
  ) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, '', buttons),
      disableClose: true
    });
  }

  confermaSalvataggio() {
    this.openDialog(
      'Grazie per aver compilato il questionario!',
      ['I dati sono stati salvati correttamente'],
      'card-body--success'
    );
  }

  onSelectedRadio(e: MatRadioChange, idDomanda: number) {
    if (this.getTestoRispostaCondizionale() !== undefined) {
      if (e.value !== this.getTestoRispostaCondizionale()) {
        this.noConditionSelected = true;
        this.showDom = false;
        this.reloadForm();
      } else {
        this.showDom = true;
        this.noConditionSelected = false;
        delete this.textAreaMap[idDomanda];
        //creo il form group con le nuove domande
        this.reloadForm();
      }
    } else {
      if (this.hasFlgRichiestaDettagio(idDomanda)) {
        if (e.value !== this.getTestoRichiestaDettaglio(idDomanda)) {
          this.showDom = true;
          this.noConditionSelected = false;
          delete this.textAreaMap[idDomanda];
          //creo il form group con le nuove domande
          this.reloadForm();
        } else {
          this.requiredTextarea = true;
          this.textAreaMap[idDomanda] = '';
          this.noConditionSelected = true;
          this.showDom = false;
          this.reloadForm();
        }
      }
    }
  }

  lettera(s: string): boolean {
    return s.charCodeAt(0) >= 97 && s.charCodeAt(0) <= 122;
  }

  onSelectedIntraRadio(e: MatRadioChange, idDomanda: number) {
    if (
      e.value !== this.getTestoRichiestaDettaglio(idDomanda) &&
      this.hasFlgRichiestaDettagio(idDomanda)
    ) {
      this.requiredTextarea = false;
      this.textAreaMap[idDomanda] = '';
      this.textareaValue = this.textAreaMap[idDomanda];
    }
    if (e.value === this.getTestoRichiestaDettaglio(idDomanda)) {
      this.textAreaMap[idDomanda] = '';
      this.requiredTextarea = true;
    }
  }

  onTextareaInput(e: Event, idDomanda: number) {
    const target = e.target as HTMLTextAreaElement;
    this.textAreaMap[idDomanda] = target.value;
    this.textareaValue = target.value;
  }

  onTextareaInputFaccine(e: Event, idDomanda: number) {
    const target = e.target as HTMLTextAreaElement;
    this.textAreaMapFaccine[idDomanda] = target.value;
  }

  reMapDomande(): Domanda[] {
    let temp: Domanda[] = [];
    this.domande.forEach((d) => {
      if (!d.idRispostaCondizionale) {
        temp.push(d);
      }
    });
    return temp;
  }

  showDomande(idDomandaCondizionale?: number, idRispostaCondizionale?: number) {
    if (idDomandaCondizionale == null || idRispostaCondizionale == null) {
      this.showDom = false;
    } else {
      Object.keys(this.risposte_questionario.value).forEach((key) => {
        if (parseInt(key) === idDomandaCondizionale) {
          const value = this.risposte_questionario.value[key];
          if (value === this.getTestoRispostaCondizionale()) {
            this.showDom = true;
          } else {
            this.showDom = false;
          }
        }
      });
    }
  }

  getTestoRispostaCondizionale() {
    let idDomandaCondizionale: number | undefined;
    let idRispostaCondizionale: number | undefined;
    let testoRispostaCondizionale: string | undefined;

    for (const d of this.domande) {
      if (
        d.idDomandaCondizionale !== null &&
        d.idDomandaCondizionale !== undefined
      ) {
        idDomandaCondizionale = d.idDomandaCondizionale;
        idRispostaCondizionale = d.idRispostaCondizionale;
        break;
      }
    }
    this.domande.forEach((d) => {
      if (d.idDomanda === idDomandaCondizionale) {
        d.risposte.forEach((r) => {
          if (r.idRisposta === idRispostaCondizionale) {
            testoRispostaCondizionale = r.testoRisposta;
          }
        });
      }
    });
    return testoRispostaCondizionale;
  }

  getTestoRichiestaDettaglio(idDomanda: number) {
    let testoDettaglio: string | undefined;
    this.domande.forEach((d) => {
      if (d.idDomanda === idDomanda) {
        d.risposte.forEach((r) => {
          if (r.flgRichiestoDettaglio) {
            testoDettaglio = r.testoRisposta;
          }
        });
      }
    });
    return testoDettaglio;
  }

  hasFlgRichiestaDettagio(idDomanda: number) {
    let check = false;
    this.domande.forEach((d) => {
      d.risposte.forEach((r) => {
        if (d.idDomanda == idDomanda) {
          check = check || r.flgRichiestoDettaglio;
        }
      });
    });
    return check;
  }
}
