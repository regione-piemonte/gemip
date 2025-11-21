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

import {
  AfterContentChecked,
  AfterViewInit,
  Component,
  OnInit,
} from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { NavBarItem } from '../../models/navbar-item.model';
import { Cittadino } from 'src/app/core/models/cittadino';
import { AnagraficaService } from '../../services/anagrafica.service';
import { IdeaImprenditorialeService } from '../../services/idea-imprenditoriale.service';
import { IncontroPreaccoglienzaService } from '../../services/incontro-preaccoglienza.service';
import { IncontroPreaccoglienza } from 'src/app/core/models/incontroPreaccoglienza';
import { Tutor } from 'src/app/core/models/tutor';
import { TutorService } from '../../services/tutor.service';
import { QuestionarioService } from 'src/app/integration';

@Component({
  selector: 'c-mip-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, AfterViewInit,AfterContentChecked {
  cittadino?: Cittadino;
  lista: NavBarItem[] = [];
  tutor?: NavBarItem;
  questionario?: NavBarItem;
  lista2: NavBarItem[] = [];

  doveQuando: boolean = false;
  anagrafica: boolean = false;
  idea: boolean = false;
  verifica: boolean = false;
  tutorSelezionato?: Tutor;
  incontro?: IncontroPreaccoglienza;
  isRemoto = false;
  isPresenteAIncontro = false;
  isTutorReset = false;
  isAssenteAIncontro = false;
  isIncontroDisdetto = false;
  isTutorSelezionato = false;
  sessoIcon: string = '';
  questionarioIf: number | undefined | null = undefined;
  isAccorpata: boolean = false;

  constructor(
    private dashboardService: DashboardService,
    private questionarioService: QuestionarioService,
    private router: Router,
    private anagraficaService: AnagraficaService,
    private incontriService: IncontroPreaccoglienzaService,
    private ideaDiImpresaService: IdeaImprenditorialeService,
    private tutorService: TutorService
  ) {}
  ngAfterContentChecked(): void {
    window.scrollTo(0,0);
  }

  ngAfterViewInit(): void {
    window.scrollTo(0,0);
    this.dashboardService.lista.subscribe((lista) => {
      this.lista = [
        ...[this.lista2[0], this.lista2[1], this.lista2[2], this.lista2[3]],
      ];
      this.tutor = this.lista2[5];
      this.questionario = this.lista2[4];

      if (this.doveQuando) {
        this.lista[0].completed = true;
        this.lista[1].enabled = true;
      }
      if (this.anagrafica) {
        this.lista[1].completed = true;
        this.lista[2].enabled = true;
      }
      if (this.idea) {
        this.lista[2].completed = true;
        this.lista[3].enabled = true;
      }
      if (this.verifica) {
        this.lista[3].completed = true;
      }
    });
  }

  /**
     * Idea impresa
     * 1	Creata
2	Annullata
3	Modificata
4	Accorpata
5	Non ammissibile
6	Inviata
7	Partecipazione incontro
8	Patto di servizio firmato
9	Accoglienza individuale svolta (No firma PdS)
10	Abbandonato
     */

  ngOnInit(): void {
    this.verificaDashboard();
    setTimeout(() => {
      let ideaImpresa = JSON.parse(sessionStorage.getItem('ideaImpresa')!);
    if(ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneIdeaDiImpresa === 'Creata' || ideaImpresa.statoIdeaDiImpresa.descrizioneIdeaDiImpresa === 'Inserito')){
      this.dashboardService.modificaContatti.next(false);
    }else if(ideaImpresa && (ideaImpresa.statoIdeaDiImpresa.descrizioneIdeaDiImpresa !== 'Creata' || ideaImpresa.statoIdeaDiImpresa.descrizioneIdeaDiImpresa !== 'Inserito')){
      this.dashboardService.modificaContatti.next(true);
    }else{
      this.dashboardService.modificaContatti.next(false);
    }
    }, 1500)
  }

  verificaDashboard() {
    this.dashboardService.verificaDashboard(this.mockCittadino()).subscribe({
      next: (ris) => {
        this.dashboardService.lista.subscribe((lista) => {
          this.lista2 = lista;
          this.lista = [
            ...[this.lista2[0], this.lista2[1], this.lista2[2], this.lista2[3]],
          ];
          this.tutor = this.lista2[5];
          this.questionario = this.lista2[4];

          if (ris.idCittadinoIncontroPreacc) {
            this.lista[0].completed = true;
            this.lista[1].enabled = true;
          }
          if (ris.idAnagraficaCittadino) {
            this.lista[1].completed = true;
            this.lista[2].enabled = true;
          }
          if (ris.idIdeaDiImpresa) {
            this.lista[2].completed = true;
            this.lista[3].enabled = true;
          }
          if (ris.registrazioneIncontro) {
            this.lista[3].completed = true;
          }
          if (ris.partecipzioneIncontro) {
            this.isPresenteAIncontro = true;
          }
          if (ris.assenzaIncontro) {
            this.isAssenteAIncontro = true;
          }
          if (ris.disdicimentoIncontro) {
            this.isIncontroDisdetto = true;
          }
          if (ris.idTuttor) {
            this.isTutorSelezionato = true;
          }
        });
        this.dashboardService.dashboard = ris;
        this.cittadino = this.dashboardService.dashboard.cittadino!;
        this.dashboardService.updateUser(this.cittadino);
        sessionStorage.setItem('cittadino', JSON.stringify(ris.cittadino));
        this.questionarioService
          .getQuestionarioFaseCorrente(Number(this.cittadino.idCittadino))
          .subscribe({
            next: (data) => {
              console.log("data  ",data)
              this.questionarioIf = data;
            },
            error: (error) => console.log(error),
          });
        if (this.cittadino.codiceFiscale) {
          let giornoNascita =
            this.cittadino.codiceFiscale.charAt(9) +
            this.cittadino.codiceFiscale.charAt(10);
          this.sessoIcon =
            Number(giornoNascita) > 40 ? 'female_user' : 'male_user';
          sessionStorage.setItem('sesso', this.sessoIcon);
        }
        if (ris.idIdeaDiImpresa) {
          sessionStorage.setItem(
            'idIdeaDiImpresa',
            ris.idIdeaDiImpresa?.toString()
          );
          this.ideaDiImpresaService
            .getIdeaDiImpresaById(ris.idIdeaDiImpresa.toString()!)
            .subscribe({
              next: (ris) => {
                this.ideaDiImpresaService.ideaDiImpresa = ris;
                if (
                  Number(
                    this.ideaDiImpresaService.ideaDiImpresa.statoIdeaDiImpresa
                      ?.id
                  ) >= 4
                ) {
                  sessionStorage.setItem('isInviato', 'S');
                  this.lista[3].completed = true;
                  if (
                    this.ideaDiImpresaService.ideaDiImpresa.statoIdeaDiImpresa
                      ?.id == 4
                  ) {
                    this.isAccorpata = true;
                  }
                }


                if (this.ideaDiImpresaService.ideaDiImpresa.idTutor) {
                  this.isTutorSelezionato = true;
                  this.tutorService
                    .getTutorById(
                      this.ideaDiImpresaService.ideaDiImpresa.idTutor
                    )
                    .subscribe((ris) => (this.tutorSelezionato = ris));
                } else {
                  this.isTutorSelezionato = false;
                }

                if(this.ideaDiImpresaService.ideaDiImpresa.sbloccoAreaTerritoriale){
                  this.isPresenteAIncontro=true;
                  this.isTutorSelezionato=false;
                }
              },
            });
        }

        if (ris.idAnagraficaCittadino) {
          this.anagrafica = true;
          sessionStorage.setItem(
            'idAnagraficaCittadino',
            ris.idAnagraficaCittadino?.toString()
          );
          this.anagraficaService
            .getAnagraficaById(ris.idAnagraficaCittadino!)
            .subscribe({
              next: (ris) => {
                this.anagraficaService.anagraficaCittadino = ris;
              },
            });
        }

        if (ris.idCittadinoIncontroPreacc) {
          this.doveQuando = true;

          sessionStorage.setItem(
            'idCittadinoIncontroPreacc',
            ris.idCittadinoIncontroPreacc?.toString()
          );
          this.incontriService
            .getIncontroForCittadino(ris.idCittadinoIncontroPreacc.toString())
            .subscribe({
              next: (ris) => {
                this.incontriService.cittadinoIncontroPreaccoglienza = ris;
                this.incontro = ris.incontroPreaccoglienza!;
                if (this.incontro.flgIncontroErogatoDaRemoto) {
                  this.isRemoto = true;
                } else {
                  this.isRemoto = false;
                }
                if (ris.flgCittadinoPresente == 'S') {
                  this.isPresenteAIncontro = true;
                } else {
                  this.isPresenteAIncontro = false;
                }
              },
            });
        } else {
          this.doveQuando = false;
        }

        if (ris.idIdeaDiImpresa) {
          this.idea = true;
        } else {
          this.idea = false;
        }
      },
    });
  }
  onPrenotaNuovoIncontro() {
    this.lista = [];
    sessionStorage.removeItem('idCittadinoIncontroPreacc');
    this.router.navigate(['/dashboard/incontro-pre-accoglienza']);
  }
  mockCittadino(): Cittadino {
    if (sessionStorage.getItem('cittadino')) {
      return JSON.parse(sessionStorage.getItem('cittadino')!);
    } else {
      return { codiceFiscale: 'PPPLLL80A41L219V' }; // mock
    }
  }

  navigatePath(path: string) {
    console.log(path);
    this.router.navigateByUrl(path);
  }
}
