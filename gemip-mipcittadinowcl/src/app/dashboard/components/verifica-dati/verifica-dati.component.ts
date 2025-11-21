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

import { AfterContentChecked, AfterViewInit, Component, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Cittadino } from 'src/app/core/models/cittadino';
import { AnagraficaCittadino, DashboardCittadino, IdeaDiImpresa } from 'src/app/core/models/models';
import { BagService } from 'src/app/core/services/bag.service';
import { DialogAlertComponent } from 'src/app/shared/components/dialog-alert/dialog-alert.component';
import { DialogButton, DialogSettings } from 'src/app/shared/models/dialog-settings.model';
import { AnagraficaService } from '../../services/anagrafica.service';
import { DashboardService } from '../../services/dashboard.service';
import { IdeaImprenditorialeService } from '../../services/idea-imprenditoriale.service';
import { IncontroPreaccoglienzaService } from '../../services/incontro-preaccoglienza.service';

@Component({
  selector: 'c-mip-verifica-dati',
  templateUrl: './verifica-dati.component.html',
  styleUrls: ['./verifica-dati.component.scss']
})
export class VerificaDatiComponent implements OnInit, AfterViewInit, AfterContentChecked {
  anagrafica?: AnagraficaCittadino = this.anagraficaService.anagraficaCittadino;
  ideaDiImpresa?: IdeaDiImpresa = this.ideaDiImpresaService.ideaDiImpresa;
  incontroPreaccoglienza = this.incontroPreaccoglienzaService.cittadinoIncontroPreaccoglienza?.incontroPreaccoglienza;
  areaTerritorialeSelezionata = this.incontroPreaccoglienzaService.cittadinoIncontroPreaccoglienza?.codiceAreaTerritorialeSelezionata?.descrizioneAreaTerritoriale
  cittadino: Cittadino = JSON.parse(sessionStorage.getItem("cittadino")!)
  statoIdeaImpresa: string = "";

  constructor(
    private dialog: MatDialog,
    private router: Router,
    private anagraficaService: AnagraficaService,
    private ideaDiImpresaService: IdeaImprenditorialeService,
    private dashboardService: DashboardService,
    private incontroPreaccoglienzaService: IncontroPreaccoglienzaService,
    private bagService: BagService
  ) {

  }

  ngAfterContentChecked(): void {
    window.scrollTo(0, 0);
  }

  ngAfterViewInit(): void {
    window.scrollTo(0, 0);
  }

  ngOnInit(): void {

    this.dashboardService.modificaContatti.next(false);
  }

  @ViewChild('conferma') conferma!: TemplateRef<any>;
  @ViewChild('attenzione') attenzione!: TemplateRef<any>;
  isOnline = false;

  openDialogConferma() {
    this.dialog.open(this.conferma, {
      width: "80vw",
      panelClass: "ClasseCss",
      disableClose: true
    });
  }

  onConfermaVerifica() {
    this.ideaDiImpresaService.getIdeaDiImpresaById(this.ideaDiImpresa?.id?.toString()!).subscribe({
      next: ris => {
        console.log(ris);
        this.statoIdeaImpresa = ris.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa!;
        if (this.statoIdeaImpresa !== 'Inserito' && this.statoIdeaImpresa !== 'Creata') {
          this.dialog.open(this.attenzione, {
            width: "40vw",
            panelClass: "ClasseCss",
            disableClose: true
          });
        } else {
          this.bagService.resetError();
          this.incontroPreaccoglienzaService.getIncontroForCittadino(sessionStorage.getItem("idCittadinoIncontroPreacc")!).subscribe({
            next: ris => {
              let url = "/dashboard/incontro-pre-accoglienza";
              if (new Date(ris.incontroPreaccoglienza?.dataIncontro!) < new Date()) {
                this.dialog.open(DialogAlertComponent, {
                  width: "810px",
                  data: new DialogSettings(
                    "Attenzione!",
                    [" <span class=\"evidenziato p-3\" role=\"alert\"><strong >La tua prenotazione non è andata a buon fine.</strong></span>", " La data selezionata è passata, seleziona un nuovo incontro."],
                    "", "", [new DialogButton("CONTINUA", "btn btn--primary align-end", () => {
                      window.scrollTo(0, 0)
                      setTimeout(() => this.router.navigate([url]), 300)
                    }, "chiudi finestra")], [], "", "warning"
                  ),
                  disableClose: true
                });
                return;
              }

              const dataConfronto: Date = new Date(ris.incontroPreaccoglienza?.dataIncontro!);
              dataConfronto?.setDate(dataConfronto.getDate() - 2);
              if (new Date() >= dataConfronto) {
                this.dialog.open(DialogAlertComponent, {
                  width: "810px",
                  data: new DialogSettings(
                    "Attenzione!",
                    [" <span class=\"evidenziato p-3\" role=\"alert\"><strong >La tua prenotazione non è andata a buon fine.</strong></span>", " Superati i termini di scadenza di prenotazione. Seleziona un nuovo incontro."],
                    "", "", [new DialogButton("CONTINUA", "btn btn--primary align-end", () => {
                      window.scrollTo(0, 0)
                      setTimeout(() => this.router.navigate([url]), 300)
                    }, "chiudi finestra")], [], "", "warning"
                  ),
                  disableClose: true
                });
                return;
              }

              if (ris.incontroPreaccoglienza?.numMaxPartecipanti! <= ris.incontroPreaccoglienza?.numPartecipantiIscritti!) {
                this.dialog.open(DialogAlertComponent, {
                  width: "810px",
                  data: new DialogSettings(
                    "Attenzione!",
                    [" <span class=\"evidenziato p-3\" role=\"alert\"><strong >La tua prenotazione non è andata a buon fine.</strong></span>", " L'incontro selezionato non ha più posti disponibili, seleziona un nuovo incontro."],
                    "", "", [new DialogButton("CONTINUA", "btn btn--primary align-end", () => {
                      window.scrollTo(0, 0)
                      setTimeout(() => this.router.navigate([url]), 300)
                    }, "chiudi finestra")], [], "", "warning"
                  ),
                  disableClose: true
                });
                return;
              }

              if (this.dashboardService.dashboard != undefined) {
                let dash: DashboardCittadino = {
                  ...this.dashboardService.dashboard,
                  idIdeaDiImpresa: +sessionStorage.getItem("idIdeaDiImpresa")!
                }
                this.ideaDiImpresaService.inviaDati(dash).subscribe(
                  r => {
                    this.dashboardService.dashboard = r
                    sessionStorage.setItem('verifica', "true");
                    this.openDialogConferma();
                  }
                )
              } else {
                let dash: DashboardCittadino = {
                  cittadino: this.cittadino,
                  idIdeaDiImpresa: +sessionStorage.getItem("idIdeaDiImpresa")!
                }
                this.ideaDiImpresaService.inviaDati(dash).subscribe(
                  r => {
                    this.dashboardService.dashboard = r
                    sessionStorage.setItem('verifica', "true");
                    this.openDialogConferma();
                  }
                )
              }
            }
          })
        }
      }
    })
  }

  onSuccess() {
    this.router.navigate(['/dashboard/home']).finally(() => window.scrollTo(0, 0));
  }

  get isInviato() {
    return !!sessionStorage.getItem("isInviato");
  }

  onIndietro() {
    this.ideaDiImpresaService.getIdeaDiImpresaById(this.ideaDiImpresa?.id?.toString()!).subscribe({
      next: ris => {
        this.statoIdeaImpresa = ris.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa!;
        if (this.statoIdeaImpresa !== 'Inserito' && this.statoIdeaImpresa !== 'Creata') {
          this.dialog.open(this.attenzione, {
            width: "40vw",
            panelClass: "ClasseCss",
            disableClose: true
          });
        } else {
          this.router.navigate(['/dashboard/idea-imprenditoriale']).finally(() => window.scrollTo(0, 0));
        }
      }
    })
  }

  onModificaIncontro() {
    this.ideaDiImpresaService.getIdeaDiImpresaById(this.ideaDiImpresa?.id?.toString()!).subscribe({
      next: ris => {
        this.statoIdeaImpresa = ris.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa!;
        if (this.statoIdeaImpresa !== 'Inserito' && this.statoIdeaImpresa !== 'Creata' && this.statoIdeaImpresa !== 'Incontro pre-accoglienza') {
          this.dialog.open(this.attenzione, {
            width: "40vw",
            panelClass: "ClasseCss",
            disableClose: true
          });
        } else {
          this.router.navigate(['/dashboard/incontro-pre-accoglienza']).finally(() => window.scrollTo(0, 0));
        }
      }
    })
  }

  onModificaAnagrafica() {
    this.ideaDiImpresaService.getIdeaDiImpresaById(this.ideaDiImpresa?.id?.toString()!).subscribe({
      next: ris => {
        this.statoIdeaImpresa = ris.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa!;
        if (this.statoIdeaImpresa !== 'Inserito' && this.statoIdeaImpresa !== 'Creata') {
          this.dialog.open(this.attenzione, {
            width: "40vw",
            panelClass: "ClasseCss",
            disableClose: true
          });
        } else {
          this.router.navigate(['/dashboard/dati-anagrafica']).finally(() => window.scrollTo(0, 0));
        }
      }
    })
  }

  onModificaIdeaImpresa() {
    this.ideaDiImpresaService.getIdeaDiImpresaById(this.ideaDiImpresa?.id?.toString()!).subscribe({
      next: ris => {
        this.statoIdeaImpresa = ris.statoIdeaDiImpresa?.descrizioneStatoIdeaDiImpresa!;
        if (this.statoIdeaImpresa !== 'Inserito' && this.statoIdeaImpresa !== 'Creata') {
          this.dialog.open(this.attenzione, {
            width: "40vw",
            panelClass: "ClasseCss",
            disableClose: true
          });
        } else {
          this.router.navigate(['/dashboard/idea-imprenditoriale']);
        }
      }
    })
  }
}
