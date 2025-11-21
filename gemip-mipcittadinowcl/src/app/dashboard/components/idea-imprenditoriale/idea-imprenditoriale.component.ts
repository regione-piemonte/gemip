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

import { AfterViewInit, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { IdeaImprenditorialeService } from '../../services/idea-imprenditoriale.service';
import { FonteConoscenzaMip } from 'src/app/core/models/fonteConoscenzaMip';
import { IdeaDiImpresa } from 'src/app/core/models/ideaDiImpresa';
import { Cittadino } from 'src/app/core/models/cittadino';
import { DashboardService } from '../../services/dashboard.service';
import { DialogConfermaComponent } from 'src/app/shared/components/dialog-conferma/dialog-conferma.component';
import { DialogSettings, DialogButton } from 'src/app/shared/models/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';
import { BagService } from 'src/app/core/services/bag.service';
import { IdeeDiImpresaService } from 'src/app/integration';

@Component({
  selector: 'c-mip-idea-imprenditoriale',
  templateUrl: './idea-imprenditoriale.component.html',
  styleUrls: ['./idea-imprenditoriale.component.scss']
})
export class IdeaImprenditorialeComponent implements OnInit, AfterViewInit {
  altroSelezionato: boolean = false
  cittadino?: Cittadino;
  ideaDiImpresa?: IdeaDiImpresa;
  idIdeaDiImpresa?: string;
  formIdeaDiImpresa: FormGroup = this.fb.group({
    conoscenzaProgrammaMip: ["", [Validators.required]],
    altro: new FormControl({ value: "", disabled: !this.altroSelezionato }),
    titoloIdeaImprenditoriale: [null, [Validators.required, Validators.maxLength(150)]],
    descrizione: [null, [Validators.required, Validators.maxLength(500)]],
    noteCommenti: [null, [Validators.maxLength(4000)]]
  });
  statoIdeaImpresa: string = "";
  listaFonteConoscenza: FonteConoscenzaMip[] = []

  @ViewChild('attenzione') attenzione!: TemplateRef<any>;

  constructor(
    private fb: FormBuilder,
    private ideaDiImpresaService: IdeaImprenditorialeService,
    private ideeDiImpresaService: IdeeDiImpresaService,
    private dashboardService: DashboardService,
    private router: Router,
    private dialog: MatDialog,
    private bagService: BagService
  ) { }

  ngAfterViewInit(): void {
    window.scrollTo(0, 0)
  }

  ngOnInit(): void {
    this.dashboardService.modificaContatti.next(false);
    this.cittadino = JSON.parse(sessionStorage.getItem("cittadino")!);
    this.idIdeaDiImpresa = sessionStorage.getItem("idIdeaDiImpresa")!;
    this.ideeDiImpresaService.getFontiConoscenzaMip().subscribe({
      next: ris => {
        this.listaFonteConoscenza = ris
      }
    })
    if (this.idIdeaDiImpresa)
      this.ideaDiImpresaService.getIdeaDiImpresaById(this.idIdeaDiImpresa).subscribe({
        next: ris => {
          this.ideaDiImpresa = ris;
          this.ideaDiImpresaService.ideaDiImpresa = ris
          this.popolaCampi();
          this.selctionChange();
        }
      })
    window.scrollTo(0, 0);
  }

  onSave(isContinua: boolean = false): void {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Creata') {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaDiImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino?.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaDiImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.bagService.resetError();
            if (this.formIdeaDiImpresa?.valid) {
              if (this.formIdeaDiImpresa?.dirty) {
                if (!this.idIdeaDiImpresa) {
                  this.ideaDiImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino?.idCittadino!).subscribe({
                    next: ris => {
                      if (ris.ideaDiImpresa !== null) {
                        this.dialog.open(this.attenzione, {
                          width: "40vw",
                          panelClass: "ClasseCss",
                          disableClose: true
                        });
                      } else {
                        this.ideaDiImpresaService.insertIdeaDiImpresa(this.createIdeaDiImpresa(), this.cittadino!.idCittadino?.toString()!)
                          .subscribe({
                            next: ris => {
                              this.ideaDiImpresa = ris;
                              this.ideaDiImpresaService.ideaDiImpresa = ris;
                              this.dashboardService.updateListaPrimafase(2);
                              sessionStorage.setItem("idIdeaDiImpresa", ris.id?.toString()!);
                              this.confermaSalvataggio();
                              window.scrollTo(0, 0);
                              if (isContinua)
                                this.router.navigate(['dashboard/verifica-dati']).finally(() => window.scrollTo(0, 0))
                              else
                                this.router.navigate(['dashboard/home']).finally(() => window.scrollTo(0, 0));
                            }
                          })
                      }
                    }
                  })
                } else {
                  this.ideaDiImpresaService.updateIdeaDiImpresa(this.createIdeaDiImpresa())
                    .subscribe({
                      next: ris => {
                        this.ideaDiImpresa = ris;
                        this.ideaDiImpresaService.ideaDiImpresa = ris
                        this.confermaSalvataggio();
                        if (isContinua)
                          this.router.navigate(['dashboard/verifica-dati']);
                        else
                          this.router.navigate(['dashboard/home']);
                      }
                    })
                }
              } else if (isContinua) { this.router.navigate(['dashboard/verifica-dati']); }
              else { this.router.navigate(['dashboard/home']); }

            } else {
              this.datiMancanti();
              return;
            }
          }
        }
      })
    }
  }

  selctionChange() {

    if (this.formIdeaDiImpresa.controls['conoscenzaProgrammaMip'].value == "07") {
      this.formIdeaDiImpresa.controls["altro"].addValidators([Validators.required, Validators.maxLength(250)])
      this.altroSelezionato = true
      this.formIdeaDiImpresa.controls["altro"].enable();
      this.formIdeaDiImpresa.controls["altro"].updateValueAndValidity()
    } else {
      this.formIdeaDiImpresa.controls["altro"].clearValidators()
      this.altroSelezionato = false
      this.formIdeaDiImpresa.controls["altro"].setValue(null);
      this.formIdeaDiImpresa.controls["altro"].updateValueAndValidity()
      this.formIdeaDiImpresa.controls["altro"].disable();
    }
  }

  createIdeaDiImpresa(): IdeaDiImpresa {
    return {
      id: this.idIdeaDiImpresa ? Number(this.idIdeaDiImpresa) : undefined,
      titolo: this.formIdeaDiImpresa.get("titoloIdeaImprenditoriale")?.value,
      fonteConoscenzaMip: {
        descrizioneFonteConoscenzaMip: this.listaFonteConoscenza.filter(c => c.codiceFonteConoscenzaMip == this.formIdeaDiImpresa.get("conoscenzaProgrammaMip")?.value)[0].descrizioneFonteConoscenzaMip,
        codiceFonteConoscenzaMip: this.formIdeaDiImpresa.get("conoscenzaProgrammaMip")?.value
      },
      descrizioneAltraFonteConoscenzaMip: this.formIdeaDiImpresa.get("altro")?.value,
      statoIdeaDiImpresa: {
        id: 1,
        descrizioneStatoIdeaDiImpresa: "Creata"
      },
      descrizioneIdeaDiImpresa: this.formIdeaDiImpresa.get("descrizione")?.value,
      noteCommenti: this.formIdeaDiImpresa.get('noteCommenti')?.value,
      codUserInserim: !this.ideaDiImpresa ? this.cittadino!.codiceFiscale : this.ideaDiImpresa.codUserInserim,
      codUserAggiorn: this.cittadino!.codiceFiscale,
      dataInserim: this.ideaDiImpresa ? this.ideaDiImpresa.dataInserim : undefined
    }
  }

  popolaCampi() {
    this.formIdeaDiImpresa.controls['conoscenzaProgrammaMip'].setValue(this.ideaDiImpresa?.fonteConoscenzaMip?.codiceFonteConoscenzaMip);
    this.formIdeaDiImpresa.controls['descrizione'].setValue(this.ideaDiImpresa?.descrizioneIdeaDiImpresa);
    this.formIdeaDiImpresa.controls['titoloIdeaImprenditoriale'].setValue(this.ideaDiImpresa?.titolo);
    this.formIdeaDiImpresa.controls['altro'].setValue(this.ideaDiImpresa?.descrizioneAltraFonteConoscenzaMip);
    this.formIdeaDiImpresa.controls['noteCommenti'].setValue(this.ideaDiImpresa?.noteCommenti);
  }
  get titolare(): boolean {
    if (this.formIdeaDiImpresa?.get('titolare')?.value === false)
      return false;
    else return true;
  }

  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose: true
    })
  }

  confermaSalvataggio() {
    this.openDialog("Avviso", ["I dati sono stati salvati correttamente"], "card-body--success")
  }

  datiMancanti() {
    this.openDialog("Avviso", ["Dati obbligatori mancanti"], "card-body--danger")
  }

  onIndietro() {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Creata') {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaDiImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino?.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaDiImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.router.navigate(['/dashboard/dati-anagrafica']);
          }
        }
      })
    }
  }

}
