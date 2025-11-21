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

import { LuogoIncontro } from '@core/models/luogoIncontro';

import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavigationEnd, Router } from '@angular/router';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { Operatore } from '@core/models/operatore';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { StatoIdeaDiImpresa } from '@core/models/statoIdeaDiImpresa';
import { TipoDocumento } from '@core/models/tipoDocumento';
import { Tutor } from '@core/models/tutor';
import { BagService } from '@core/services/bag.service';
import { CommonService } from '@core/services/common.service';
import { DocumentiService } from '@documenti/services/documenti.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { OperatoriService } from '@operatori/services/operatori.service';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { FiltriRicerca, FiltroRicercaAnagraficheCittadini, FiltroRicercaDocumento, FiltroRicercaEventoCalendario, FiltroRicercaEventoRicercaIdeeImpresa, FiltroRicercaEventoRicercaQuestionari, FiltroRicercaIdeaImpresa, FiltroRicercaOperatore, FiltroRicercaPreAccoglienza, FiltroRicercaUtentiCittadino } from '@shared/model/filtri-ricerca.model';
import { SoggettoAttuatoreService } from '@utenti/services/soggetto-attuatore.service';
import { UtentiService } from '@utenti/services/utenti.service';
import { IcsBagService } from 'src/app/calendario/services/calendario-ics-bag.service';
import { MatSelectChange } from '@angular/material/select';
import { ReportisticaIdeeImpresaService } from 'src/app/reportistica/service/reportistica-idee-impresa.service';


@Component({
  selector: 'app-criteri-ricerca',
  templateUrl: './criteri-ricerca.component.html',
  styleUrls: ['./criteri-ricerca.component.scss']
})
export class CriteriRicercaComponent implements OnInit {

  @Input('filterFor') filterFor: string = '';
  @Output('onSearch') onSearch: EventEmitter<FiltriRicerca> = new EventEmitter();

  panelOpenState = true;
  areeTerriList: AreaTerritoriale[] = [];
  statoIdeaList: StatoIdeaDiImpresa[] = [];
  statoIdeaListRidotto: StatoIdeaDiImpresa[] = [];
  tutorList: Tutor[] = [];


  areeTerritoriali: AreaTerritoriale[] = [];
  sedi: LuogoIncontro[] = [];
  sediFiltred: LuogoIncontro[] = [];
  operatori: Operatore[] = [];
  status: StatoIdeaDiImpresa[] = [];
  statoPresenze: string[] = ["DISDETTO", "HA PARTECIPATO", "ISCRITTO", "NON PRESENTATO"];
  tipiDocumento: TipoDocumento[] = [];
  filtriRicerca?: FiltriRicerca
  repoTipoReport: Array<{ label: string, value: any }> = [];
  form!: FormGroup;
  abilitato = true;
  cambioTipoReport: boolean = false;

  constructor(
    private fb: FormBuilder,
    private commonService: CommonService,
    private incontriService: PreAccoglienzaService,
    private incontriServiceBag: PreAccoglienzaBagService,
    private documentiService: DocumentiService,
    private ideaImpService: IdeaImpresaService,
    private utentiService: UtentiService,
    private elencoIcsBag: IcsBagService,
    private bagService: BagService,
    private router: Router,
    private operatoriService: OperatoriService,
    private attuatoreService: SoggettoAttuatoreService,
    private reportisticaService: ReportisticaIdeeImpresaService,
  ) { }

  ngOnInit(): void {
    this.buildForm();
    this.router.events.subscribe((event) => {
      if (event instanceof NavigationEnd) {
        if (!event.url.startsWith('/pre-accoglienza')) {
          this.incontriServiceBag.leftComponent = true;
        }
        if (!event.url.startsWith('/idee-impresa')) {
          this.ideaImpService.leftComponent = true;
        }
        if (!event.url.startsWith('/utenti/cittadino') && !event.url.startsWith('/idee-impresa/form')) {
          this.utentiService.leftComponent = true;
        }
        if (!event.url.startsWith('/operatori')) {
          this.operatoriService.leftComponent = true;
        }
      }
    });
    //Caching incontri pre-accoglienza
    if ((!this.incontriServiceBag.leftComponent)) {
      this.incontriServiceBag._filtriRicerca.subscribe({
        next: ris => {
          if (ris) {
            this.form.controls["dataInizio"].setValue(ris.dataDa);
            this.form.controls["dataFine"].setValue(ris.dataA);
            this.form.controls["operatore"].setValue(ris.codFiscOperatore);
            this.form.controls["areaTerritoriale"].setValue(ris.areaTerritoriale);
            this.incontriService.getLuoghiIncontro().subscribe(
              {
                next: ris => {
                  this.sedi = ris
                  if (this.form.controls["areaTerritoriale"].value)
                    this.sediFiltred = this.sedi.filter(luogo => luogo.areaTerritoriale?.codiceAreaTerritoriale == this.form.controls["areaTerritoriale"].value)
                  else
                    this.sediFiltred = []
                }
              }
            )
            this.form.controls["sede"].setValue(ris.sede);
          }
          this.onSearch.emit(ris);
        }
      })
    }
    //caching idee d'impresa
    if (!this.ideaImpService.leftComponent) {
      this.ideaImpService._filtriRicerca.subscribe({
        next: (ris: FiltroRicercaIdeaImpresa) => {
          console.log(ris);
          if (ris) {
            this.form.controls["dataInizio"].setValue(ris.dataInseritaDa && ris.dataInseritaDa.trim() != '' && ris.dataInseritaDa != "null" && ris.dataInseritaDa !== 'Invalid Date' ? new Date(ris.dataInseritaDa) : "");
            this.form.controls["dataFine"].setValue(ris.dataInseritaA && ris.dataInseritaA.trim() != '' && ris.dataInseritaA != "null" && ris.dataInseritaA !== 'Invalid Date' ? new Date(ris.dataInseritaA) : "");
            if (ris.ideaDiImpresa && ris.ideaDiImpresa != "null") this.form.controls["ideaImpresa"].setValue(ris.ideaDiImpresa);
            if (ris.cittadinoNome && ris.cittadinoNome != "null") this.form.controls["utenteNome"].setValue(ris.cittadinoNome);
            if (ris.cittadinoCognome && ris.cittadinoCognome != "null") this.form.controls["utenteCognome"].setValue(ris.cittadinoCognome);
            if (ris.codAreaTerritoriale && ris.codAreaTerritoriale != "null") this.form.controls["areaTerritoriale"].setValue(ris.codAreaTerritoriale);
            if (ris.idStatoIdea && ris.idStatoIdea != "null") this.form.controls["status"].setValue(Number(ris.idStatoIdea));
            if (ris.statoPresenze && ris.statoPresenze != "null") {
              let tmp: string[] = [];
              if (ris.statoPresenze.includes("DISDETTO")) {
                tmp.push("DISDETTO");
              }
              if (ris.statoPresenze.includes("HA PARTECIPATO")) {
                tmp.push("HA PARTECIPATO");
              }
              if (ris.statoPresenze.includes("ISCRITTO")) {
                tmp.push("ISCRITTO");
              }
              if (ris.statoPresenze.includes("NON PRESENTATO")) {
                tmp.push("NON PRESENTATO");
              }
              this.form.controls["statoPresenze"].setValue(tmp);
            }
            this.commonService.getSoggettiAttuatoriByAreaTerritoriale(ris.codAreaTerritoriale)
              .subscribe(
                r => {
                  this.tutorList = r;
                  let sogg = r.find(s => s.soggettoAttuatore?.id == ris.idSoggettoAttuatore);
                  if (sogg) {
                    this.form.controls["soggettoAttuatore"].setValue(sogg?.id)
                  }
                }
              )
            //this.form.controls["soggettoAttuatore"].setValue(ris.idSoggettoAttuatore);
          }
          this.onSearch.emit(ris);
        }
      })
    }
    //caching cittadino
    if (!this.utentiService.leftComponent) {
      this.form.controls["cognome"].setValue(this.utentiService.filtriCittadino.cognome);
      this.form.controls["nome"].setValue(this.utentiService.filtriCittadino.nome);
      this.form.controls["email"].setValue(this.utentiService.filtriCittadino.email);
      this.form.controls["codiceFiscale"].setValue(this.utentiService.filtriCittadino.codiceFiscale);
      this.onSearch.emit(this.utentiService.filtriCittadino);
    }
    //caching operatori
    if (!this.operatoriService.leftComponent) {
      this.form.controls["codiceFiscale"].setValue(this.operatoriService.filtriOperatori.codiceFiscale);
      this.form.controls["cognome"].setValue(this.operatoriService.filtriOperatori.cognome);
      this.form.controls["nome"].setValue(this.operatoriService.filtriOperatori.nome);
      this.form.controls["email"].setValue(this.operatoriService.filtriOperatori.email);
      if (this.operatoriService.filtriOperatori.abilitato == "si") {
        this.form.controls["abilitato"].setValue("si");
        this.abilitato = true;
      } else {
        this.form.controls["abilitato"].setValue("no");
        this.abilitato = false;
      }
      this.form.controls["identificativo"].setValue(this.operatoriService.filtriOperatori.idOperatore);
      this.form.controls["soggetto"].setValue(this.operatoriService.filtriOperatori.soggetto);
      this.onSearch.emit(this.operatoriService.filtriOperatori);
    }

  }

  buildForm() {
    if (this.filterFor.toLowerCase() == "pre-accoglienza") {
      this.incontriService.getOperatoriSoggettoAffiddatario().subscribe(r => this.operatori = r);
      this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);

      this.form = this.fb.group({
        dataInizio: ['',],
        dataFine: ['',],
        sede: ['',],
        areaTerritoriale: ['',],
        operatore: ['',]
      })
    }

    if (this.filterFor.toLowerCase() == "anagrafica-partecipanti") {
      if (sessionStorage.getItem('soggettoAttuatore')) {
        this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
        this.form = this.fb.group({
          dataInizio: ['',],
          dataFine: ['',],
          areaTerritoriale: [{ value: '', disabled: true }],
          soggettoAttuatore: [{ value: '', disabled: true }]
        })
        let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
        this.commonService.getSoggettiAttuatoriByAreaTerritoriale(sogg.codiceAreaTerritoriale?.codiceAreaTerritoriale)
          .subscribe(
            r => {
              this.tutorList = r
              this.form.controls["soggettoAttuatore"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].id)
              this.commonService.getAreeTerritoriali().subscribe(ra => {
                this.areeTerriList = ra
                this.form.controls["areaTerritoriale"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].soggettoAttuatore?.codiceAreaTerritoriale?.codiceAreaTerritoriale)
              })
            })
      } else {
        this.incontriService.getOperatoriSoggettoAffiddatario().subscribe(r => this.operatori = r);
        this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
        this.form = this.fb.group({
          dataInizio: ['',],
          dataFine: ['',],
          sede: ['',],
          areaTerritoriale: ['',],
          operatore: ['',]
        })
      }
    }
    if (this.filterFor.toLowerCase() == "idee-impresa") {
      this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
      this.commonService.getStatiIdeaImpresa().subscribe(r => this.statoIdeaList = r);
      this.statoIdeaList.forEach(stato => {
        if (!(stato.descrizioneStatoIdeaDiImpresa === "Annullata" || stato.descrizioneStatoIdeaDiImpresa === "Modificata" || stato.descrizioneStatoIdeaDiImpresa === "Accorpata" ||
          stato.descrizioneStatoIdeaDiImpresa === "Abbandonato" || stato.descrizioneStatoIdeaDiImpresa === "Questionario compilato")) {
          this.statoIdeaListRidotto.push(stato);
        }
      });
      this.statoIdeaList = this.statoIdeaListRidotto;
      this.form = this.fb.group({
        dataInizio: ['',],
        dataFine: ['',],
        ideaImpresa: ['',],
        utenteNome: ['',],
        utenteCognome: ['',],
        areaTerritoriale: [{ value: '', disabled: true },],
        status: ['',],
        statoPresenze: ['',],
        soggettoAttuatore: [{ value: '', disabled: true },]
      })
      if (sessionStorage.getItem("soggettoAttuatore")) {
        let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
        this.commonService.getSoggettiAttuatoriByAreaTerritoriale(sogg.codiceAreaTerritoriale?.codiceAreaTerritoriale)
          .subscribe(
            r => {
              this.tutorList = r
              this.form.controls["soggettoAttuatore"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].id)
              this.commonService.getAreeTerritoriali().subscribe(ra => {
                this.areeTerriList = ra
                this.form.controls["areaTerritoriale"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].soggettoAttuatore?.codiceAreaTerritoriale?.codiceAreaTerritoriale)
              })
            }
          )
      } else {
        this.form.controls["soggettoAttuatore"].enable()
        this.form.controls["areaTerritoriale"].enable()
      }
    }
    if (this.filterFor.toLowerCase() == "operatore") {
      this.form = this.fb.group({
        identificativo: ['',],
        cognome: ['',],
        nome: ['',],
        email: ['',],
        soggetto: ['',],
        codiceFiscale: ['',],
        abilitato: ['si',]
      })
    }
    if (this.filterFor.toLowerCase() == "documenti") {
      this.documentiService.getTipologieDocumenti().subscribe({
        next: ris => {
          this.tipiDocumento = ris;
        }
      })
      this.form = this.fb.group({
        titolo: ['',],
        gruppo: ['',]
      })
    }
    if (this.filterFor.toLowerCase() == "utenti") {
      this.form = this.fb.group({
        cognome: ['',],
        nome: ['',],
        email: ['',],
        codiceFiscale: ['',],
      })
    }
    if (this.filterFor.toLowerCase() == "evento-calendario") {
      let dataDa: Date = new Date()
      dataDa.setHours(0);

      let dataA: Date = new Date();
      dataA.setMonth(dataDa.getMonth() + 1);
      dataA.setHours(23);
      this.commonService.getSoggettiAttuatoriByAreaTerritoriale().subscribe(
        ris => this.tutorList = ris
      )
      this.form = this.fb.group({
        dataInizio: [dataDa,],
        dataFine: [dataA,],
        soggettoAttuatore: [{ value: '', disabled: true },],
      })

      if (!sessionStorage.getItem("soggettoAttuatore")) {
        this.form.controls["soggettoAttuatore"].enable()
        this.commonService.getSoggettiAttuatoriByAreaTerritoriale().subscribe(
          ris => this.tutorList = ris
        )
      } else {

        this.commonService.getSoggettiAttuatoriByAreaTerritoriale().subscribe(
          ris => {
            this.tutorList = ris
            let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!);
            this.tutorList = this.tutorList.filter(tutor => tutor.soggettoAttuatore?.id == sogg.id);
            this.form.controls["soggettoAttuatore"].setValue(this.tutorList[0].id);

          }
        )

      }
    }
    if (this.filterFor.toLowerCase() == "ricerca_idea_impresa") {
      this.commonService.getStatiIdeaImpresa().subscribe(r => this.statoIdeaList = r);
      this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
      this.repoTipoReport = [{ label: 'elenco completo', value: 'elenco completo' }];
      this.form = this.fb.group({
        dataInizio: ['',],
        dataFine: ['',],
        areaTerritoriale: [{ value: '', disabled: true, }],
        soggettoAttuatore: [{ value: '', disabled: true, }],
        tipoReport: ['', Validators.required],
        status: ['',],
      })

      if (sessionStorage.getItem("soggettoAttuatore")) {
        let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!);
        this.commonService.getSoggettiAttuatoriByAreaTerritoriale(sogg.codiceAreaTerritoriale?.codiceAreaTerritoriale)
          .subscribe(
            r => {
              this.tutorList = r
              this.form.controls["soggettoAttuatore"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].id)
              this.commonService.getAreeTerritoriali().subscribe(ra => {
                this.areeTerriList = ra
                this.form.controls["areaTerritoriale"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].soggettoAttuatore?.codiceAreaTerritoriale?.codiceAreaTerritoriale)
              })
            }
          )
      } else {
        this.form.controls["soggettoAttuatore"].enable()
        this.form.controls["areaTerritoriale"].enable()
      }
    }
    if (this.filterFor.toLowerCase() == "ricerca_questionari") {
      this.commonService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
      this.repoTipoReport = [{ label: 'Fase 1', value: 1 }, { label: 'Fase 2', value: 2 }, { label: 'Fase 3', value: 3 }];
      this.form = this.fb.group({
        dataInizio: ['',],
        dataFine: ['',],
        soggettoAttuatore: [{ value: '', disabled: true },],
        areaTerritoriale: [{ value: '', disabled: true },],
        tipoReport: ['', Validators.required],
      })

      if (sessionStorage.getItem("soggettoAttuatore")) {
        let sogg: SoggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
        this.commonService.getSoggettiAttuatoriByAreaTerritoriale(sogg.codiceAreaTerritoriale?.codiceAreaTerritoriale)
          .subscribe(
            r => {
              this.tutorList = r
              this.form.controls["soggettoAttuatore"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].id)
              this.commonService.getAreeTerritoriali().subscribe(ra => {
                this.areeTerriList = ra
                this.form.controls["areaTerritoriale"].setValue(r.filter(s => s.soggettoAttuatore?.id == sogg.id)[0].soggettoAttuatore?.codiceAreaTerritoriale?.codiceAreaTerritoriale)
              })
            }
          )
      } else {
        this.form.controls["soggettoAttuatore"].enable()
        this.form.controls["areaTerritoriale"].enable()
      }
    }
  }

  //-Panel handling
  openPanel() {
    this.panelOpenState = true
  }

  closePanel() {
    this.panelOpenState = false
  }

  clear() {
    this.sediFiltred = [];
    Object.keys(this.form.controls).forEach((key: string) => {
      const abstractControl = this.form.controls[key];
      if (abstractControl.enabled) {
        this.form.controls[key].reset()
      }
    });
    //this.form.reset();
  }

  onCerca() {
    this.bagService.resetError();
    this.incontriServiceBag.updatePagination(5, 0);
    this.ideaImpService.updatePagination(5, 0);
    this.elencoIcsBag.updatePagination(5, 0);
    if (this.filterFor.toLowerCase() == "pre-accoglienza") {
      let filtri = this.createFiltriIncontri();
      this.incontriServiceBag.updateFiltri(filtri);
      this.onSearch.emit(filtri);
    }
    if (this.filterFor.toLowerCase() == "idee-impresa") {
      this.onSearch.emit(this.createFiltriIdeaImp());
    }
    if (this.filterFor.toLowerCase() == "utenti") {
      this.utentiService.updateFiltri(this.createFiltriUtenti());
      this.utentiService.filtriCittadino = this.createFiltriUtenti();
      this.onSearch.emit(this.createFiltriUtenti());
    }
    if (this.filterFor.toLowerCase() == "operatore") {
      this.operatoriService.filtriOperatori = this.createFiltriOperatore();
      this.onSearch.emit(this.createFiltriOperatore());
    }
    if (this.filterFor.toLowerCase() == "documenti") {
      this.onSearch.emit(this.createFiltriDocumenti());
    }
    if (this.filterFor.toLowerCase() == "evento-calendario") {
      this.onSearch.emit(this.createFiltroEventiCalendario());
    }
    if (this.filterFor.toLowerCase() == "ricerca_idea_impresa" && this.form.valid) {
      this.onSearch.emit(this.createFiltriReportisticaIdeeImpresa());
    }
    if (this.filterFor.toLowerCase() == "ricerca_questionari" && this.form.valid) {
      this.onSearch.emit(this.createFiltriReportisticaQuestionari());
    }
    if (this.filterFor.toLowerCase() == "anagrafica-partecipanti") {
      let filtri = this.createFiltriAnagraficheCittadini();
      this.onSearch.emit(filtri);
    }
  }

  createFiltroEventiCalendario(): FiltroRicercaEventoCalendario {
    return {
      dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
      dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,
      idSoggettoAttuatore: this.form.controls["soggettoAttuatore"].value ? this.form.controls["soggettoAttuatore"]!.value : undefined,
    }
  }
  createFiltriUtenti(): FiltroRicercaUtentiCittadino {
    return {
      codiceFiscale: this.form.controls["codiceFiscale"]!.value ? this.form.controls["codiceFiscale"]!.value : undefined,
      cognome: this.form.controls["cognome"]!.value ? this.form.controls["cognome"]!.value : undefined,
      nome: this.form.controls["nome"]!.value ? this.form.controls["nome"]!.value : undefined,
      email: this.form.controls["email"]!.value ? this.form.controls["email"]!.value : undefined,

    }
  }

  createFiltriOperatore(): FiltroRicercaOperatore {
    return {
      codiceFiscale: this.form.controls["codiceFiscale"]!.value ? this.form.controls["codiceFiscale"]!.value : undefined,
      cognome: this.form.controls["cognome"]!.value ? this.form.controls["cognome"]!.value : undefined,
      nome: this.form.controls["nome"]!.value ? this.form.controls["nome"]!.value : undefined,
      email: this.form.controls["email"]!.value ? this.form.controls["email"]!.value : undefined,
      abilitato: this.abilitato == true ? "si" : "no",
      idOperatore: this.form.controls["identificativo"].value ? this.form.controls["identificativo"]!.value : undefined,
      soggetto: this.form.controls["soggetto"].value ? this.form.controls["soggetto"]!.value : undefined,
    }
  }

  createFiltriIncontri(): FiltroRicercaPreAccoglienza {
    return {
      areaTerritoriale: this.form.controls["areaTerritoriale"]!.value ? this.form.controls["areaTerritoriale"]!.value : undefined,
      codFiscOperatore: this.form.controls["operatore"]!.value ? this.form.controls["operatore"]!.value : undefined,
      sede: this.form.controls["sede"]!.value ? this.form.controls["sede"]!.value : undefined,
      dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
      dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,

    }
  }


  createFiltriDocumenti(): FiltroRicercaDocumento {
    return {
      titolo: this.form.controls["titolo"].value,
      codiceTipoDocumento: this.form.controls["gruppo"].value,
    }
  }

  createFiltriReportisticaIdeeImpresa(): FiltroRicercaEventoRicercaIdeeImpresa {
    let sogg: SoggettoAttuatore | undefined = undefined;
    if (sessionStorage.getItem('soggettoAttuatore')) {
      sogg = JSON.parse(sessionStorage.getItem('soggettoAttuatore')!);
    }
    return {
      dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,
      dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
      idSoggettoAttuatore: this.form.controls["soggettoAttuatore"]!.value ? this.form.controls["soggettoAttuatore"]!.value : undefined,
      areaTerritoriale: sessionStorage.getItem("soggettoAttuatore") ? sogg!.codiceAreaTerritoriale?.codiceAreaTerritoriale : undefined,
      tipoReport: this.form.controls["tipoReport"]!.value ? this.form.controls["tipoReport"]!.value : undefined,
      idStatoIdea: this.form.controls["status"]!.value ? this.form.controls["status"]!.value : undefined,
    }
  }

  createFiltriReportisticaQuestionari(): FiltroRicercaEventoRicercaQuestionari {
    return {
      dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,
      dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
      idSoggettoAttuatore: this.form.controls["soggettoAttuatore"]!.value ? this.form.controls["soggettoAttuatore"]!.value : undefined,
      tipoReport: this.form.controls["tipoReport"]!.value ? this.form.controls["tipoReport"]!.value : undefined,
      codAreaTerritoriale: this.form.controls["areaTerritoriale"]!.value ? this.form.controls["areaTerritoriale"]!.value : undefined
    }
  }
  createFiltriAnagraficheCittadini(): FiltroRicercaAnagraficheCittadini {
    if (sessionStorage.getItem('soggettoAttuatore'))
      return {
        areaTerritoriale: this.form.controls["areaTerritoriale"]!.value ? this.form.controls["areaTerritoriale"]!.value : undefined,
        idSoggettoAttuatore: this.form.controls["soggettoAttuatore"]!.value ? this.form.controls["soggettoAttuatore"]!.value : undefined,
        dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
        dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,
      }
    else
      return {
        areaTerritoriale: this.form.controls["areaTerritoriale"]!.value ? this.form.controls["areaTerritoriale"]!.value : undefined,
        codFiscOperatore: this.form.controls["operatore"]!.value ? this.form.controls["operatore"]!.value : undefined,
        sede: this.form.controls["sede"]!.value ? this.form.controls["sede"]!.value : undefined,
        dataA: this.form.controls["dataFine"]!.value ? this.form.controls["dataFine"]!.value : undefined,
        dataDa: this.form.controls["dataInizio"]!.value ? this.form.controls["dataInizio"]!.value : undefined,
      }
  }
  createFiltriIdeaImp() {
    type Mappa = {
      [key: string]: string;
    };
    const miaMappa: Mappa = {
      "areaTerritoriale": "codAreaTerritoriale",
      "dataInizio": "dataInseritaDa",
      "dataFine": "dataInseritaA",
      "ideaImpresa": "ideaDiImpresa",
      "utenteNome": "cittadinoNome",
      "utenteCognome": "cittadinoCognome",
      "status": "idStatoIdea",
      "statoPresenze": "statoPresenze",
      "soggettoAttuatore": "idSoggettoAttuatore",
    };

    const controlsMap: { [key: string]: string } = {};
    // let filtri: any = {}//{for:"IdeaImpresa"}
    Object.keys(this.form.controls).forEach(key => {
      if (['dataInizio', 'dataFine'].includes(key)) {
        if (isNaN(this.form.controls[key].value)) {
          this.form.controls[key].setValue("");
          this.form.updateValueAndValidity();
        }
      }
      controlsMap[miaMappa[key]] = String(this.form.controls[key].value);
    });

    this.ideaImpService.updateFiltri(controlsMap);
    return controlsMap;
  }

  onChangeStatoIdea() {
    if (this.form.controls['status'].value! !== 6) {
      this.form.controls['statoPresenze'].setValue('');
      this.form.controls['statoPresenze'].updateValueAndValidity()
    }
  }
  onAreaTerritorialeChange() {

    if (this.form.controls['sede']) {
      this.form.controls['sede'].setValue('');
    }

    this.incontriService.getLuoghiIncontro().subscribe(
      {
        next: ris => {
          this.sedi = ris
          if (this.form.controls["areaTerritoriale"].value)
            this.sediFiltred = this.sedi.filter(luogo => luogo.areaTerritoriale?.codiceAreaTerritoriale == this.form.controls["areaTerritoriale"].value)
          else
            this.sediFiltred = []
        }
      }
    )

    if (this.form.controls['soggettoAttuatore']) {
      this.form.controls['soggettoAttuatore'].setValue('');
      this.commonService.getSoggettiAttuatoriByAreaTerritoriale(String(this.form.controls['areaTerritoriale'].value))
        .subscribe(r => this.tutorList = r)
    }
  }

  checkDate() {
    this.form.controls["dataInizio"].markAsTouched()
    this.form.controls["dataFine"].markAsTouched()
    if (new Date(this.form.get("dataInizio")?.value) > new Date(this.form.get("dataFine")?.value)) {
      this.form.controls["dataInizio"].setErrors({ msg: "la data inizio non può superare la data di fine" })
      this.form.controls["dataFine"].setErrors({ msg: "la data Fine non può precedere la data di inizio" })
    } else {
      this.form.controls["dataInizio"].setErrors(null)
      this.form.controls["dataFine"].setErrors(null)
      this.form.controls["dataInizio"].markAsUntouched()
      this.form.controls["dataFine"].markAsUntouched()
    }
  }

  onChangeDFine() {
    this.checkDate()
  }


  myFilterDFine = (d: any): boolean => {
    let day: Date;
    let dayF: Date;
    if (this.form.get("dataInizio")?.value) {
      dayF = new Date(this.form.get("dataInizio")?.value)
      day = new Date(d)
      return day >= dayF
    } else {
      day = new Date()
      return true
    }
    // return day > new Date();
  };

  // filtri data fine
  onChangeDInizio() {
    this.form.get("dataFine")?.enable()
    this.myFilterDFine(this.form.get("dataInizio")?.value)
    this.checkDate()
  }

  myFilterDinizio = (d: any): boolean => {
    let day: Date;
    let dayF: Date;

    if (this.form.get("dataFine")?.value) {
      dayF = new Date(this.form.get("dataFine")?.value)
      day = new Date(d)
      return day <= dayF
    } else {
      day = new Date(d)
    }
    return true;
  }

  onToggle() {
    this.abilitato = !this.abilitato
  }

  onTipoReportSelezionato(event: MatSelectChange): void {
    this.reportisticaService.cambioTipoReportSubject.next(true);
  }
}
