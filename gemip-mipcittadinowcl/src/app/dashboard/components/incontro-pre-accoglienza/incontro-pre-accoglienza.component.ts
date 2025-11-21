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

import { Breakpoints } from '@angular/cdk/layout';
import {
  AfterViewChecked,
  AfterViewInit,
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  ElementRef,
  Inject,
  OnDestroy,
  OnInit,
  TemplateRef,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { MatDialog } from '@angular/material/dialog';

import {
  MatCalendar,
  MatCalendarCellClassFunction,
} from '@angular/material/datepicker';
import { IncontroPreaccoglienzaService } from '../../services/incontro-preaccoglienza.service';
import { AreaTerritoriale } from 'src/app/core/models/areaTerritoriale';
import { IncontroPreaccoglienza } from 'src/app/core/models/incontroPreaccoglienza';
import { Cittadino } from 'src/app/core/models/cittadino';
import { CittadinoIncontroPreaccoglienza } from 'src/app/core/models/cittadinoIncontroPreaccoglienza';
import { LuogoIncontro } from 'src/app/core/models/luogoIncontro';
import { DialogConfermaComponent } from 'src/app/shared/components/dialog-conferma/dialog-conferma.component';
import {
  DialogButton,
  DialogSettings,
} from 'src/app/shared/models/dialog-settings.model';
import { DialogAlertComponent } from 'src/app/shared/components/dialog-alert/dialog-alert.component';
import { DashboardService } from '../../services/dashboard.service';

import {
  DateAdapter,
  MAT_DATE_FORMATS,
  MatDateFormats,
} from '@angular/material/core';
import { Subject, takeUntil } from 'rxjs';
import { BreakpointService } from '../../services/breakpoint.service';
import { BagService } from 'src/app/core/services/bag.service';
import { IdeaImprenditorialeService } from '../../services/idea-imprenditoriale.service';

@Component({
  selector: 'c-mip-incontro-pre-accoglienza',
  templateUrl: './incontro-pre-accoglienza.component.html',
  styleUrls: ['./incontro-pre-accoglienza.component.scss'],
})
export class IncontroPreAccoglienzaComponent
  implements OnInit, AfterViewChecked, AfterViewInit {
  customHeader = CustomHeader;

  form?: FormGroup;
  displayedColumns: string[] = [
    'dataPrenotazione',
    'orario',
    'sede',
    'posti',
    'actions',
  ];
  dataSource: any;
  cittadino: Cittadino = JSON.parse(sessionStorage.getItem('cittadino')!);
  confermato: boolean = false;
  areeTerritoriali: AreaTerritoriale[] = [];
  incontroPreAccoglienzaSelezionato?: IncontroPreaccoglienza;
  primoIncontroOnlineDisponibile?: IncontroPreaccoglienza;
  primoInocntroInPresenzaDisponibile?: IncontroPreaccoglienza;

  incontriDisponibiliOnline: IncontroPreaccoglienza[] = [];
  incontriDisponibiliInPresenza: IncontroPreaccoglienza[] = [];

  incotriDisponibili: IncontroPreaccoglienza[] = [];
  luoghiDIIncontro: LuogoIncontro[] = [];
  orariDisponibili: IncontroPreaccoglienza[] = [];
  orarioSelezionato?: IncontroPreaccoglienza;

  incontroCittadino?: CittadinoIncontroPreaccoglienza;

  zonaSelected?: string;
  localOnline = 0;
  statoIdeaImpresa: string = "";

  minDate: Date = new Date();
  maxDate: Date | null = null;

  @ViewChild('scrollTop') scrollTop!: ElementRef;
  @ViewChild('conferma') conferma!: TemplateRef<any>;
  @ViewChild('attenzione') attenzione!: TemplateRef<any>;

  constructor(
    private fb: FormBuilder,
    private incontroPreAccoglienzaService: IncontroPreaccoglienzaService,
    private router: Router,
    private dialog: MatDialog,
    private dashboardService: DashboardService,
    private breakpointService: BreakpointService,
    private bagService: BagService,
    private ideaImpresaService: IdeaImprenditorialeService,
  ) {
    this.breakpointService
      .observeBreakpoints([
        Breakpoints.XSmall,
        Breakpoints.Small,
        Breakpoints.Medium,
        Breakpoints.Large,
        Breakpoints.XLarge,
      ])
      .subscribe((result) => {
        this.isXSmallScreen = result[Breakpoints.XSmall];
        this.isSmallScreen = result[Breakpoints.Small];
        this.isMediumScreen = result[Breakpoints.Medium];
        this.isLargeScreen = result[Breakpoints.Large];
        this.isXLargeScreen = result[Breakpoints.XLarge];
        this.showRegioneResponsive();
      });
  }

  ngAfterViewInit(): void {
    if (sessionStorage.getItem('_incontroPreaccoglienza')) {
      if (!sessionStorage.getItem('idCittadinoIncontroPreacc')) {
        window.scrollTo(0, 0);
      } else {
        this.scrollTop.nativeElement.scrollIntoView();
      }
    } else {
      window.scrollTo(0, 0);
    }
  }

  ngAfterViewChecked(): void {
    if (this.calendar) {
      this.calendario.nativeElement.scrollIntoView();
    }
  }

  ngOnInit(): void {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa === 'Incontro pre-accoglienza') {
      this.dashboardService.modificaContatti.next(true);
    } else {
      this.dashboardService.modificaContatti.next(false);
    }
    this.incontroPreAccoglienzaService.getAreeTerritoriali().subscribe({
      next: (ris) => {
        this.areeTerritoriali = ris;
      },
    });
    if (sessionStorage.getItem('idCittadinoIncontroPreacc')) {
      this.incontroPreAccoglienzaService
        .getIncontroForCittadino(
          sessionStorage.getItem('idCittadinoIncontroPreacc')!
        )
        .subscribe({
          next: (ris) => {
            this.incontroCittadino = ris;
            this.incontroPreAccoglienzaSelezionato = ris.incontroPreaccoglienza;
            this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza =
              ris;
            this.zonaSelected =
              ris.codiceAreaTerritorialeSelezionata?.codiceAreaTerritoriale;
            if (
              ris.incontroPreaccoglienza &&
              ris.incontroPreaccoglienza.flgIncontroErogatoDaRemoto
            ) {
              this.localOnline = 2;
            } else if (
              ris.incontroPreaccoglienza &&
              !ris.incontroPreaccoglienza.flgIncontroErogatoDaRemoto
            ) {
              this.localOnline = 1;
            } else {
              this.localOnline = 0;
            }
            if (this.zonaSelected) {
              this.onZonaSelected(this.zonaSelected);
            }
          },
        });
    }

    this.form = this.fb.group({
      territorio: [null, [Validators.required]],
    });

    this.bagService.resetDate.subscribe({
      next: () => {
        this.orarioSelezionato = undefined;
        this.orariDisponibili = [];
      }
    })
  }
  onAnnullaModale() {
    this.localOnline = 0;
    this.incontroPreAccoglienzaSelezionato = undefined;
    this.locationSelected = 0;
    this.orarioSelezionato = undefined;
  }
  orarioSelection(incontro: IncontroPreaccoglienza) {
    this.orarioSelezionato = incontro;
  }

  onConfermaSelezionato() {
    if (this.orarioSelezionato) {
      this.incontroPreAccoglienzaSelezionato = this.orarioSelezionato;
      if (this.orarioSelezionato.flgIncontroErogatoDaRemoto) {
        this.localOnline = 2;
      } else {
        this.localOnline = 1;
      }
    }
  }
  dateClassSelected: MatCalendarCellClassFunction<Date> = (cellDate, view) => {
    // highligh dates inside the month view.
    if (view === 'month') {
      const date = cellDate.getDate();
      const month = cellDate.getMonth(); //Giorno delle celle del calendari
      const year = cellDate.getFullYear();
      let check: string[] = this.incotriDisponibili.map(
        (incontro) =>
          new Date(incontro!.dataIncontro!).getDate().toString() +
          new Date(incontro!.dataIncontro!).getMonth().toString() +
          new Date(incontro!.dataIncontro!).getFullYear().toString()
      );
      let checkSelected: string | undefined =
        this.orariDisponibili.length > 0
          ? new Date(this.orariDisponibili[0]!.dataIncontro!)
            .getDate()
            .toString() +
          new Date(this.orariDisponibili[0]!.dataIncontro!).getMonth().toString() +
          new Date(this.orariDisponibili[0]!.dataIncontro!).getFullYear().toString()
          : undefined;
      if (check.includes(date.toString() + month.toString() + year.toString())) {
        if (checkSelected == date.toString() + month.toString() + year.toString()) {
          return 'cell--secondary--active';
        }
        return 'cell--secondary';
      }
    }
    return '';
  };

  @ViewChild(MatCalendar) calendar!: MatCalendar<Date>;
  onDateSelection(event: Date | null) {
    if (event) {
      this.orariDisponibili = this.incotriDisponibili.filter(
        (inc) =>
          new Date(inc.dataIncontro!).getDate() == event.getDate() &&
          new Date(inc.dataIncontro!).getMonth() == event.getMonth() &&
          new Date(inc.dataIncontro!).getFullYear() == event.getFullYear()
      );
      if (this.orariDisponibili.length == 1) {
        this.orarioSelezionato = this.orariDisponibili[0];
      }

      this.calendar.updateTodaysDate();
    } else {
      this.orariDisponibili = [];
    }

    this.calendar.updateTodaysDate();
  }
  @ViewChild('calendario') calendario!: ElementRef;
  onLuogoSelected(luogo: LuogoIncontro) {
    this.locationSelected = luogo.id!;
    this.incotriDisponibili = this.incontriDisponibiliInPresenza.filter(
      (inc) => inc.luogoIncontro!.id == luogo.id
    );
    this.incontroPreAccoglienzaService.updateElencoIncontri(
      this.incotriDisponibili
    );
    this.incontroPreAccoglienzaSelezionato = this.incotriDisponibili[this.incotriDisponibili.length - 1];
    this.orariDisponibili = [];

    this.orarioSelezionato = undefined;
    //this.calendar.activeDate = this.incontroPreAccoglienzaSelezionato.dataIncontro!;
    /*

    */
    if (this.calendar) {
      this.calendar.updateTodaysDate();
      this.calendar._goToDateInView(
        new Date(this.incotriDisponibili[this.incotriDisponibili.length - 1].dataIncontro!),
        'month'
      );
    }

    //this.calendario.nativeElement.scrollIntoView();
  }
  onAnnullaSelezione() {
    this.localOnline = 0;
    this.incontroPreAccoglienzaSelezionato = undefined;
    this.locationSelected = 0;
    this.orarioSelezionato = undefined;
  }

  onSeleziona(isOnline: boolean) {
    if (!this.incontroPreAccoglienzaSelezionato) {
      if (isOnline) {
        this.incontroPreAccoglienzaSelezionato =
          this.primoIncontroOnlineDisponibile;
        this.localOnline = 2;
      } else {
        this.incontroPreAccoglienzaSelezionato =
          this.primoInocntroInPresenzaDisponibile;
        this.localOnline = 1;
      }
    }
  }
  get isInviato() {
    return !!sessionStorage.getItem('isInviato');
  }
  onContinua() {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Incontro pre-accoglienza' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Creata')) {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito' && this.statoIdeaImpresa != 'Incontro pre-accoglienza') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            if (sessionStorage.getItem('isInviato')) {
              this.saveStato('/dashboard/home');
            } else {
              window.scrollTo(0, 0);
              this.saveStato('/dashboard/dati-anagrafica');
            }
          }
        }
      })
    }
  }

  onSalva() {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Incontro pre-accoglienza' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Creata')) {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito' && this.statoIdeaImpresa != 'Incontro pre-accoglienza') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.saveStato('/dashboard/home');
          }
        }
      })
    }
  }

  onSalvaNuovoIncontro() {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Incontro pre-accoglienza') && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Creata') {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito' && this.statoIdeaImpresa != 'Incontro pre-accoglienza') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.incontroPreAccoglienzaService.updateIncontroPreAccoglienzaSelezionato(this.createCittadinoIncontroPreaccoglienza(), ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa).subscribe({
              next: ris => {
                this.incontroPreAccoglienzaSelezionato = ris.incontroPreaccoglienza;
                this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza = ris;
                sessionStorage.setItem(
                  'idCittadinoIncontroPreacc',
                  ris.id!.toString()
                );
                this.dialog.open(DialogConfermaComponent, {
                  data: new DialogSettings(
                    'Avviso!',
                    ['I dati sono stati salvati con successo '],
                    'card card-body--success'
                  ),
                  disableClose: true
                });
                this.router.navigate(['dashboard/home']);
              }
            });
          }
        }
      })
    }
  }

  saveStato(url: string) {
    this.bagService.resetError();
    let statoDescrzioneIdeaImpresa = "";
    if (sessionStorage.getItem('idCittadinoIncontroPreacc')) {
      if (
        this.incontroPreAccoglienzaSelezionato?.id !=
        this.incontroCittadino?.incontroPreaccoglienza?.id
      ) {
        this.incontroPreAccoglienzaService
          .updateIncontroPreAccoglienzaSelezionato(
            this.createCittadinoIncontroPreaccoglienza(),
            statoDescrzioneIdeaImpresa
          )
          .subscribe({
            next: (ris) => {
              this.incontroPreAccoglienzaSelezionato =
                ris.incontroPreaccoglienza;
              this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza =
                ris;
              sessionStorage.setItem(
                'idCittadinoIncontroPreacc',
                ris.id!.toString()
              );
              if (!this.isInviato) {
                this.dialog.open(DialogAlertComponent, {
                  width: '810px',
                  panelClass: 'evidenziato-atenzione',
                  disableClose: true,
                  data: new DialogSettings(
                    'Attenzione!',
                    [
                      ' <span class="evidenziato p-md-3" role="alert"><strong >La tua prenotazione non è ancora stata registrata.</strong></span>',
                      " Per portare a termine la prenotazione dell'incontro di pre-accoglienza, è necessario inserire i <strong>dati personali</strong>, descrivere <strong>l'idea di impresa</strong> e <strong>inviare</strong> il modulo completo. Se <strong>non viene completata</strong> la registrazione entro <strong>30 giorni</strong>, i dati verranno <strong>eliminati</strong>.",
                    ],
                    'container-incontro',
                    '',
                    [
                      new DialogButton(
                        'HO CAPITO, ' +
                        (url == '/dashboard/home'
                          ? 'TORNA ALLA HOME'
                          : 'CONTINUA'),
                        'btn btn--primary align-end',
                        () => {
                          window.scrollTo(0, 0)
                          setTimeout(() => this.router.navigate([url]), 300)
                        },
                        'chiudi finestra'
                      ),
                    ],
                    [],
                    '',
                    'warning'
                  ),
                });
              } else {
                this.dialog.open(DialogConfermaComponent, {
                  data: new DialogSettings(
                    'Avviso!',
                    ['I dati sono stati salvati con successo '],
                    'card card-body--success'
                  ),
                  disableClose: true
                });
                this.router.navigate([url]);
              }
            },
            error: (err) => {
              this.dialog.open(DialogConfermaComponent, {
                data: new DialogSettings(
                  'Errore',
                  [
                    "Errore di sistema contattare l'amministratore o riprovare più tardi",
                  ],
                  'card-body--danger'
                ),
                disableClose: true
              });
            },
          });
      } else {
        this.router.navigate([url]);
      }
    } else {
      //controllo non sia stato già inserito nel db
      this.incontroPreAccoglienzaService.getIncontroPerCittadino(this.cittadino.idCittadino!).subscribe({
        next: ris => {
          if (ris.id !== null) {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.incontroPreAccoglienzaService
              .insertIncontroPreAccoglienzaSelezionato(
                this.createCittadinoIncontroPreaccoglienza()
              )
              .subscribe({
                next: (ris) => {
                  this.incontroPreAccoglienzaSelezionato = ris.incontroPreaccoglienza;
                  this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza =
                    ris;
                  sessionStorage.setItem(
                    'idCittadinoIncontroPreacc',
                    ris.id!.toString()
                  );
                  if (this.isInviato) {
                    this.dialog.open(this.conferma, {
                      width: "40vw",
                      panelClass: "ClasseCss",
                      disableClose: true
                    });
                  } else {
                    this.dialog.open(DialogAlertComponent, {
                      width: '810px',
                      panelClass: 'evidenziato-atenzione',
                      disableClose: true,
                      data: new DialogSettings(
                        'Attenzione!',
                        [
                          ' <span class="evidenziato p-md-3" role="alert"><strong >La tua prenotazione non è ancora stata registrata.</strong></span>',
                          " Per portare a termine la prenotazione dell'incontro di pre-accoglienza, è necessario inserire i <strong>dati personali</strong>, descrivere <strong>l'idea di impresa</strong> e <strong>inviare</strong> il modulo completo. Se <strong>non viene completata</strong> la registrazione entro <strong>30 giorni</strong>, i dati verranno <strong>eliminati</strong>.",
                        ],
                        'container-incontro',
                        '',
                        [
                          new DialogButton(
                            'HO CAPITO, ' +
                            (url == '/dashboard/home'
                              ? 'TORNA ALLA HOME'
                              : 'CONTINUA'),
                            'btn btn--primary align-end',
                            () => {
                              window.scrollTo(0, 0)
                              setTimeout(() => this.router.navigate([url]), 300)
                            },
                            'chiudi finestra'
                          ),
                        ],
                        [],
                        '',
                        'warning'
                      ),
                    });
                  }
                  this.dashboardService.updateListaPrimafase(0);
                },
                error: (err) => {
                  this.dialog.open(DialogConfermaComponent, {
                    data: new DialogSettings(
                      'Errore',
                      [
                        "Errore di sistema contattare l'amministratore o riprovare più tardi",
                      ],
                      'card-body--danger'
                    ),
                    disableClose: true
                  });
                },
              });
          }
        },
        error: e => {
          console.log(e);
        }
      })
    }
    sessionStorage.setItem('zonaSelected', this.zonaSelected!.toString());
    sessionStorage.setItem('localOnline', this.localOnline.toString());
    sessionStorage.setItem('doveQuando', 'true');
  }

  onConfermaNuovoIncontro() {
    this.router.navigate(['/dashboard/home']);
  }

  onIndietro() {
    let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if (ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Inserito' && ideaImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa !== 'Incontro pre-accoglienza')) {
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      this.ideaImpresaService.getIdeaDiImpresaByIdCittadino(this.cittadino.idCittadino!).subscribe({
        next: ris => {
          if (ris.ideaDiImpresa) {
            this.statoIdeaImpresa = ris.ideaDiImpresa.statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
          } else {
            this.statoIdeaImpresa = 'Creata';
          }
          if (ris.ideaImpresa != null && this.statoIdeaImpresa != 'Creata' && this.statoIdeaImpresa != 'Inserito' && this.statoIdeaImpresa != 'Incontro pre-accoglienza') {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.router.navigate(['/dashboard/home']);
          }
        }
      })
    }
  }

  createCittadinoIncontroPreaccoglienza(): CittadinoIncontroPreaccoglienza {
    if (sessionStorage.getItem('idCittadinoIncontroPreacc')) {
      return {
        ...this.incontroCittadino,
        cittadino: this.cittadino,
        codiceAreaTerritorialeSelezionata: this.areeTerritoriali.filter(
          (area) => area.codiceAreaTerritoriale == this.zonaSelected
        )[0],
        incontroPreaccoglienza: this.incontroPreAccoglienzaSelezionato,
        id: sessionStorage.getItem('idCittadinoIncontroPreacc')
          ? +sessionStorage.getItem('idCittadinoIncontroPreacc')!
          : undefined,
        codUserAggiorn: this.cittadino.codiceFiscale,
        codUserInserim: this.cittadino.codiceFiscale,
      };
    } else {
      if (JSON.parse(sessionStorage.getItem('_incontroPreaccoglienza')!)) {
        let incontroPreaccoglienza = JSON.parse(sessionStorage.getItem('_incontroPreaccoglienza')!);
        let dataInserim = incontroPreaccoglienza.dataInserim;
        return {
          ...this.incontroCittadino,
          cittadino: this.cittadino,
          codiceAreaTerritorialeSelezionata: this.areeTerritoriali.filter(
            (area) => area.codiceAreaTerritoriale == this.zonaSelected
          )[0],
          incontroPreaccoglienza: this.incontroPreAccoglienzaSelezionato,
          id: this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza?.id,
          dataInserim: dataInserim,
          codUserAggiorn: this.cittadino.codiceFiscale,
          codUserInserim: this.cittadino.codiceFiscale,
        }
      }
      return {
        ...this.incontroCittadino,
        cittadino: this.cittadino,
        codiceAreaTerritorialeSelezionata: this.areeTerritoriali.filter(
          (area) => area.codiceAreaTerritoriale == this.zonaSelected
        )[0],
        incontroPreaccoglienza: this.incontroPreAccoglienzaSelezionato,
        id: this.incontroPreAccoglienzaService.cittadinoIncontroPreaccoglienza?.id,
        codUserAggiorn: this.cittadino.codiceFiscale,
        codUserInserim: this.cittadino.codiceFiscale,
      };
    }

  }

  @ViewChild('selezionaIncontro') selezionaIncontro!: TemplateRef<any>;
  isOnline = false;
  openDialogIncontri(isOnline: boolean, lista: IncontroPreaccoglienza[]) {
    this.isOnline = isOnline;
    this.incotriDisponibili = lista;
    this.incontroPreAccoglienzaService.updateElencoIncontri(
      this.incotriDisponibili
    );
    this.orariDisponibili = [];
    this.dialog.open(this.selezionaIncontro, {
      panelClass: 'dialog-appuntamenti',
      disableClose: true,
    });
    this.maxDate = new Date(lista[0].dataIncontro!);
    this.minDate = new Date(lista[lista.length - 1].dataIncontro!);
  }

  // DialogSettings
  locationSelected = 0;

  @ViewChild('targetScroll') targetScroll!: ElementRef;
  onZonaSelected(zonaSelected: string) {
    this.zonaSelected = zonaSelected;
    this.incontroPreAccoglienzaService
      .getIncontriForAreaTerritoriale(zonaSelected)
      .subscribe({
        next: (ris) => {
          ris = ris.filter(
            (inc) => inc.numMaxPartecipanti! > inc.numPartecipantiIscritti!
          );
          this.incontriDisponibiliOnline = ris.filter(
            (inc) => inc.flgIncontroErogatoDaRemoto
          );
          this.incontriDisponibiliInPresenza = ris.filter(
            (inc) => !inc.flgIncontroErogatoDaRemoto
          );

          this.luoghiDIIncontro = this.incontriDisponibiliInPresenza.map(
            (inc) => inc.luogoIncontro!
          );
          //filtro per eliminare i doppioni
          this.luoghiDIIncontro = this.luoghiDIIncontro.filter(
            (luogo, i) =>
              this.luoghiDIIncontro.findIndex((l) => luogo.id == l.id) == i
          );

          this.primoIncontroOnlineDisponibile =
            this.incontriDisponibiliOnline[this.incontriDisponibiliOnline.length - 1];
          this.primoInocntroInPresenzaDisponibile =
            this.incontriDisponibiliInPresenza[this.incontriDisponibiliInPresenza.length - 1];
        },
      });
    this.targetScroll.nativeElement.scrollIntoView();
  }

  isXSmallScreen!: boolean;
  isSmallScreen!: boolean;
  isMediumScreen!: boolean;
  isLargeScreen!: boolean;
  isXLargeScreen!: boolean;
  showStepperResponsive(): boolean {
    return this.isMediumScreen || this.isLargeScreen || this.isXLargeScreen;
  }

  showRegioneResponsive(): boolean {
    return this.isLargeScreen || this.isXLargeScreen;
  }
}

// Custom Header
@Component({
  selector: 'custom-header',
  styles: [
    `
      .custom-header {
        display: flex;
        align-items: center;
        padding: 0.5em;
      }

      .custom-header-label {
        flex: 1;
        height: 1em;
        font-weight: 500;
        text-align: center;
      }

      .mat-icon {
        margin: -22%;
      }
    `,
  ],
  template: `
    <div class="custom-header">
      <button
        mat-icon-button
        (click)="previousClicked('month')"
        [disabled]="!canGoPrevious"
      >
        <mat-icon>keyboard_arrow_left</mat-icon>
      </button>
      <span class="custom-header-label">{{ periodLabel }}</span>
      <button
        mat-icon-button
        (click)="nextClicked('month')"
        [disabled]="!canGoNext"
      >
        <mat-icon>keyboard_arrow_right</mat-icon>
      </button>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})

export class CustomHeader<D> implements OnDestroy, OnInit {
  private _destroyed = new Subject<void>();
  elencoIncontri!: IncontroPreaccoglienza[];
  countMesi: number = 0;
  monthUp?: number;

  constructor(
    private _calendar: MatCalendar<Date>,
    private _dateAdapter: DateAdapter<Date>,
    @Inject(MAT_DATE_FORMATS) private _dateFormats: MatDateFormats,
    cdr: ChangeDetectorRef,
    private incontroPreaccoglienzaService: IncontroPreaccoglienzaService,
    private bagService: BagService
  ) {
    _calendar.stateChanges.pipe(takeUntil(this._destroyed)).subscribe(() => {
      cdr.markForCheck();
    });
  }

  ngOnInit(): void {
    this.incontroPreaccoglienzaService.incontri.subscribe({
      next: (inc) => {
        this.elencoIncontri = inc;
        this.countMesi = new Date(this.elencoIncontri[this.elencoIncontri.length - 1].dataIncontro!).getMonth()!;
      },
    });
    this.bagService.canGoPrevious.subscribe({
      next: ris => {
        this.countMesi = this.countMesi + ris;
      }
    })
  }

  ngOnDestroy() {
    this._destroyed.next();
    this._destroyed.complete();
  }

  get periodLabel() {
    return this._calendar.activeDate.toLocaleDateString(undefined, {
      month: 'long',
    });
  }

  previousClicked(mode: 'month' | 'year') {
    this.bagService.canGoPrevious.next(-1);
    this.bagService.resetDate.next()
    this._calendar.activeDate =
      mode === 'month'
        ? this._dateAdapter.addCalendarMonths(this._calendar.activeDate, -1)
        : this._dateAdapter.addCalendarYears(this._calendar.activeDate, -1);
  }

  nextClicked(mode: 'month' | 'year') {
    this.bagService.canGoPrevious.next(1);
    this.bagService.resetDate.next()
    this._calendar.activeDate =
      mode === 'month'
        ? this._dateAdapter.addCalendarMonths(this._calendar.activeDate, 1)
        : this._dateAdapter.addCalendarYears(this._calendar.activeDate, 1);
  }

  get canGoNext() {
    let delta = new Date(this.elencoIncontri[0].dataIncontro!).getFullYear() - new Date(this.elencoIncontri[this.elencoIncontri.length - 1].dataIncontro!).getFullYear()
    if (delta > 0) {
      return this.countMesi < (delta * 12) + new Date(this.elencoIncontri[0].dataIncontro!).getMonth()
    } else {
      return this.countMesi < new Date(this.elencoIncontri[0].dataIncontro!).getMonth();
    }
  }

  get canGoPrevious() {
    return this.countMesi > new Date(this.elencoIncontri[this.elencoIncontri.length - 1].dataIncontro!).getMonth();
  }

}
