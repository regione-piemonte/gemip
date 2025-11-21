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

import { AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { DashboardService } from '../../services/dashboard.service';
import { Comune } from 'src/app/core/models/comune';
import { Provincia } from 'src/app/core/models/provincia';
import { Cittadinanza } from 'src/app/core/models/cittadinanza';
import { SvantaggioAbitativo } from 'src/app/core/models/svantaggioAbitativo';
import { TitoloDiStudio } from 'src/app/core/models/titoloDiStudio';
import { CondizioneOccupazionale } from 'src/app/core/models/condizioneOccupazionale';
import { AnagraficaService } from '../../services/anagrafica.service';
import { AnagraficaCittadino } from 'src/app/core/models/anagraficaCittadino';
import { Validation } from 'src/app/shared/utils/validation';
import { Cittadino } from 'src/app/core/models/cittadino';
import { StatoEstero } from 'src/app/core/models/statoEstero';
import { DialogConfermaComponent } from 'src/app/shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from 'src/app/shared/models/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';
import { BagService } from 'src/app/core/services/bag.service';
import { AnagraficheService, DatiComuniService } from 'src/app/integration';

@Component({
  selector: 'app-modifica-anagrafica',
  templateUrl: './modifica-anagrafica.component.html',
  styleUrls: ['./modifica-anagrafica.component.scss']
})
export class ModificaAnagraficaComponent implements OnInit, AfterViewInit {

  @ViewChild('scrollTop') scrollTop!: ElementRef;
  cittadino!: Cittadino;
  anagrafica?: AnagraficaCittadino;
  suggested?: AnagraficaCittadino;
  domicilioCoincide = true;
  toggleChanged = false;
  isExtraComunitario = false;

  soggiornoSelect: string[] = [
    "Permesso di Soggiorno",
    "Carta di Soggiorno"
  ]
  permessoSelected: boolean = false;
  cartaSelected: boolean = false;
  disableToggle: boolean = true;

  residenza: FormGroup = this.fb.group({
    indirizzoResidenza: [{ value: '', disabled: true }, [Validators.required, Validators.maxLength(100)]],
    cittaResidenza: [{ value: '', disabled: true }, [Validators.required]],
    provinciaResidenza: [{ value: '', disabled: true }, [Validators.required]],
    capResidenza: [{ value: { value: '', disabled: true }, disabled: true }, [Validators.required, Validators.pattern('^[0-9]{5}$'), Validators.maxLength(5), Validators.minLength(5)]]
  });

  domicilio: FormGroup = this.fb.group({
    indirizzoDomicilio: [{ value: '', disabled: true }, [Validators.maxLength(100)]],
    cittaDomicilio: [{ value: '', disabled: true }],
    provinciaDomicilio: [{ value: '', disabled: true }],
    capDomicilio: [{ value: '', disabled: true }, [Validators.pattern('^[0-9]{5}$'), Validators.maxLength(5), Validators.minLength(5)]]
  });

  natalitaEstero = this.fb.group({
    stato: [{ value: '', disabled: true },],
    citta: [{ value: '', disabled: true }, [Validators.maxLength(100)]],
  });



  cittadinanza: FormGroup = this.fb.group({
    cittadinanza: [{ value: "100", disabled: true }, [Validators.required]],
    tipoSoggiorno: [{ value: '', disabled: true }],
    cittadinanzaSpecifica: [{ value: '', disabled: true }, [Validators.maxLength(1000)]],
    permessoDiSoggiorno: [{ value: '', disabled: true }],
    permessoDiSoggiornoRilasciatoDa: [{ value: '', disabled: true }, [Validators.maxLength(1000)]],
    scadenzaSoggiorno: [{ value: null, disabled: true }],
    motivoPermessoDiSoggiorno: [{ value: null, disabled: true }],
    documentoIdentità: [null],
    documentoIdentitàRilascio: [null],
    scadenzaDocumento: [null],
  })


  form: FormGroup = this.fb.group({
    dataNascita: [{ value: null, disabled: true }, [Validators.required]],
    luogoNascita: [{ value: '1', disabled: true }, [Validators.required]],
    comuneNascita: [{ value: null, disabled: true }, [Validators.required]],
    provinciaNascita: [{ value: null, disabled: true }, [Validators.required]],
    codiceFiscale: [{ value: null, disabled: true }, [Validation.cFiscaleValidator, Validators.maxLength(16)]],
    telefono: ['', [Validation.phoneNumberRequired, Validators.maxLength(12), Validators.required]],
    telefonoAlternativo: ['', [Validation.phoneNumberValidator, Validators.maxLength(12)]],
    email: ['', [Validation.emailRequired, Validators.maxLength(100), Validators.required]],
    emailAlternativo: ['', [Validation.emailValidator, Validators.maxLength(100),]],
    residenza: this.residenza,
    domicilio: this.domicilio,
    natalitaEstero: this.natalitaEstero,
    cittadinanza: this.cittadinanza,
    titoloDiStudio: [{ value: '', disabled: true }, [Validators.required]],
    titoloDiStudioSpecifica: [{ value: '', disabled: true }, [Validators.maxLength(1000)]],
    indiceVulnerabilitaAbitativo: [{ value: null, disabled: true }, [Validators.required]],
    situazioneLavorativa: [{ value: '', disabled: true }, [Validators.required]],
    situazioneLavorativaSpecifica: [{ value: '', disabled: true }, [Validators.maxLength(1000)]],
    sesso: [{ value: null, disabled: true }, [Validators.required]]
  });



  listProvince: Provincia[] = [];

  listCittadinanza: Cittadinanza[] = [];


  listTitoliStudio: TitoloDiStudio[] = [];
  listIndiciVulnerabilitaAbitativo: SvantaggioAbitativo[] = [];
  listCondizioneOccupazionale: CondizioneOccupazionale[] = [];

  listCitta: Comune[] = [];
  listCittaResidenzaFiltrate: Comune[] = [];
  listCittaDomicilioFiltrate?: Comune[];

  statiEsteriList: StatoEstero[] = []

  versioneMobile: boolean = false;

  constructor(private fb: FormBuilder,
    private dashboardService: DashboardService,
    private anagraficaService: AnagraficaService,
    private anagraficheService: AnagraficheService,
    private datiComuniService: DatiComuniService,
    private router: Router,
    private dialog: MatDialog,
    private bagService: BagService) { }


  ngAfterViewInit(): void {
    this.scrollTop.nativeElement.scrollIntoView();
    window.scrollTo(0, 150)
  }


  ngOnInit(): void {

    this.cittadino = JSON.parse(sessionStorage.getItem("cittadino")!);

    this.datiComuniService.getProvince().subscribe({
      next: ris => {
        this.listProvince = ris;
        this.anagraficaService.getAnagraficaById(this.cittadino.idCittadino!).subscribe({
          next: ris => {
            if (ris) {
              this.anagrafica = ris;
              this.anagraficaService.anagraficaCittadino = ris

              this.popolaDati()
            } else {

              console.log("NO RIS");
              this.anagraficaService.getAnagraficaSpid().subscribe({
                next: ana => {
                  if (ana) {
                    this.suggested = ana;
                    this.popolaDatiDaSpid();
                  }
                }
              })
            }
          }
        })
      }
    })

    this.datiComuniService.getCittadinanze().subscribe({
      next: ris => {
        this.listCittadinanza = ris;
      }
    })

    this.datiComuniService.getStatiEsteri().subscribe({
      next: ris => {
        this.statiEsteriList = ris
      }
    })

    this.anagraficheService.getSvantaggiAbitativo().subscribe({
      next: ris => {
        this.listIndiciVulnerabilitaAbitativo = ris;
      }
    })

    this.anagraficheService.getCondizioneOccupazionale().subscribe({
      next: ris => {
        this.listCondizioneOccupazionale = ris;
      }
    })

    this.anagraficheService.getTitoliStudio().subscribe({
      next: ris => {
        this.listTitoliStudio = ris
      }
    })
    this.checkStatoModifica();
  }

  popolaDati() {
    this.form.controls["dataNascita"].setValue(this.anagrafica?.dataNascita)
    this.form.controls["telefono"].setValue(this.anagrafica?.recapitoTelefono)
    this.form.controls["telefonoAlternativo"].setValue(this.anagrafica?.recapitoTelefono2)
    this.form.controls["email"].setValue(this.anagrafica?.recapitoEmail)
    this.form.controls["emailAlternativo"].setValue(this.anagrafica?.recapitoEmail2)
    this.form.controls["indiceVulnerabilitaAbitativo"].setValue(this.anagrafica?.svantaggioAbitativo?.id)
    this.form.controls["titoloDiStudioSpecifica"].setValue(this.anagrafica?.titoloDiStudioAltro)
    this.form.controls["situazioneLavorativaSpecifica"].setValue(this.anagrafica?.condizioneOccupazionaleAltro)
    this.form.controls["sesso"].setValue(this.anagrafica?.sesso)

    this.residenza.controls["indirizzoResidenza"].setValue(this.anagrafica?.indirizzoResidenza)
    this.fetchComuniResidenza(this.anagrafica?.comuneResidenza?.provincia?.codiceProvincia!)
    this.residenza.controls["cittaResidenza"].setValue(this.anagrafica?.comuneResidenza?.codiceIstatComune)
    this.residenza.controls["provinciaResidenza"].setValue(this.anagrafica?.comuneResidenza?.provincia?.codiceProvincia)
    this.residenza.controls["capResidenza"].setValue(this.anagrafica?.capResidenza)

    if (this.anagrafica?.indirizzoDomicilio) {
      this.domicilio.controls["indirizzoDomicilio"].setValue(this.anagrafica?.indirizzoDomicilio)
      this.fetchComuniDomicilio(this.anagrafica?.comuneDomicilio?.provincia?.codiceProvincia!)
      this.domicilio.controls["cittaDomicilio"].setValue(this.anagrafica?.comuneDomicilio?.codiceIstatComune)
      this.domicilio.controls["provinciaDomicilio"].setValue(this.anagrafica?.comuneDomicilio?.provincia?.codiceProvincia)
      this.domicilio.controls["capDomicilio"].setValue(this.anagrafica?.capDomicilio)
      this.domicilioCoincide = false;
    }

    this.cittadinanza.controls["documentoIdentità"].setValue(this.anagrafica?.numeroDocumentoIdentita)
    this.cittadinanza.controls["documentoIdentitàRilascio"].setValue(this.anagrafica?.documentoRilasciatoDa)
    this.cittadinanza.controls["scadenzaDocumento"].setValue(this.anagrafica?.dataScadenzaDocumento)

    this.cittadinanza.controls["cittadinanza"].setValue(this.anagrafica?.codiceCittadinanza?.codiceCittadinanza!)
    this.cittadinanza.controls["cittadinanzaSpecifica"].setValue(this.anagrafica?.descCittadinanzaAltro)

    this.form.controls["titoloDiStudio"].setValue(this.anagrafica?.codiceTitoloDiStudio?.codiceTitoloDiStudio!)

    this.form.controls["situazioneLavorativa"].setValue(this.anagrafica?.codiceCondizioneOccupazionale?.codiceCondizioneOccupazionale!)
    console.log(this.anagrafica!.statoEsteroNascita);
    if (this.anagrafica!.statoEsteroNascita) {
      this.natalitaEstero.controls["stato"].setValue(this.anagrafica?.statoEsteroNascita?.codStato!.toString()!);
      this.natalitaEstero.controls["citta"].setValue(this.anagrafica?.descrizioneCittaEsteraNascita!);
      this.form.controls["luogoNascita"].setValue("2");
      this.onLuogoDiNascitaChange();
    } else {
      this.form.controls["luogoNascita"].setValue("1");
      this.form.controls["provinciaNascita"].setValue(this.anagrafica?.comuneNascita?.provincia?.codiceProvincia)
      this.fetchComuni(this.anagrafica?.comuneNascita?.provincia?.codiceProvincia!)
      this.form.controls["comuneNascita"].setValue(this.anagrafica?.comuneNascita?.codiceIstatComune)
    }
    if (this.anagrafica!.codiceCittadinanza?.codiceNazionalita?.codiceNazionalita == "3") {
      this.isExtraComunitario = true
      if (this.anagrafica?.tipoPermessoDiSoggiorno === 'C') {
        this.cittadinanza.controls["tipoSoggiorno"].setValue(this.soggiornoSelect[1]);
        this.cartaSelected = true;
        this.cittadinanza.controls["permessoDiSoggiorno"].setValue(this.anagrafica?.numeroPermessoDiSoggiorno!);
        this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.cartaDiSoggiornoValidator])
      } else {
        this.cittadinanza.controls["tipoSoggiorno"].setValue(this.soggiornoSelect[0]);
        this.permessoSelected = true;
        this.cittadinanza.controls["permessoDiSoggiorno"].setValue(this.anagrafica?.numeroPermessoDiSoggiorno!);
        this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.permessoDiSoggiornoValidator])
        this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].setValue(this.anagrafica?.permessoDiSoggiornoRilasciatoDa)
        this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].addValidators([Validators.required])
        this.cittadinanza.controls["motivoPermessoDiSoggiorno"].setValue(this.anagrafica?.descrMotivoPermessoDiSoggiorno)
        this.cittadinanza.controls["motivoPermessoDiSoggiorno"].addValidators([Validators.required])
        this.cittadinanza.controls["scadenzaSoggiorno"].setValue(this.anagrafica?.dataScadPermessoSoggiorno)
        this.cittadinanza.controls["scadenzaSoggiorno"].addValidators([Validators.required])
      }
      this.cittadinanza.updateValueAndValidity();
    }
  }

  popolaDatiDaSpid() {
    this.form.controls["email"].setValue(this.suggested?.recapitoEmail)
  }


  onLuogoDiNascitaChange() {
    if (this.form.controls['luogoNascita'].value == 1) {
      this.natalitaEstero.controls["citta"].clearValidators();
      this.natalitaEstero.controls["citta"].updateValueAndValidity()
      this.natalitaEstero.controls["stato"].clearValidators();
      this.natalitaEstero.controls["stato"].updateValueAndValidity();

      this.form.controls["comuneNascita"].addValidators(Validators.required);
      this.form.controls["comuneNascita"].updateValueAndValidity();
      this.form.controls['provinciaNascita'].addValidators(Validators.required);
      this.form.controls['provinciaNascita'].updateValueAndValidity();

    } else {
      this.natalitaEstero.controls["citta"].addValidators([Validators.required, Validators.maxLength(100)]);
      this.natalitaEstero.controls["citta"].updateValueAndValidity();
      this.natalitaEstero.controls["stato"].addValidators([Validators.required]);
      this.natalitaEstero.controls["stato"].updateValueAndValidity();

      this.form.controls["comuneNascita"].clearValidators();
      this.form.controls["comuneNascita"].updateValueAndValidity();
      this.form.controls['provinciaNascita'].clearValidators();
      this.form.controls['provinciaNascita'].updateValueAndValidity();
    }
  }
  onDomicilioDiversoResidenzaChange() {

    this.domicilioCoincide = !this.domicilioCoincide
    this.toggleChanged = !this.toggleChanged
    console.log("domicilio coincide", this.domicilioCoincide)
    if (this.domicilioCoincide) {
      this.domicilio.controls["indirizzoDomicilio"].clearValidators();
      this.domicilio.controls["indirizzoDomicilio"].updateValueAndValidity();
      this.domicilio.controls["cittaDomicilio"].clearValidators();
      this.domicilio.controls["cittaDomicilio"].updateValueAndValidity();
      this.domicilio.controls["provinciaDomicilio"].clearValidators();
      this.domicilio.controls["provinciaDomicilio"].updateValueAndValidity();
      this.domicilio.controls["capDomicilio"].clearValidators();
      this.domicilio.controls["capDomicilio"].updateValueAndValidity();
    } else {
      this.domicilio.controls["indirizzoDomicilio"].addValidators([Validators.required, Validators.maxLength(100)]);
      this.domicilio.controls["indirizzoDomicilio"].updateValueAndValidity();
      this.domicilio.controls["cittaDomicilio"].addValidators(Validators.required);
      this.domicilio.controls["cittaDomicilio"].updateValueAndValidity();
      this.domicilio.controls["provinciaDomicilio"].addValidators(Validators.required);
      this.domicilio.controls["provinciaDomicilio"].updateValueAndValidity();
      this.domicilio.controls["capDomicilio"].addValidators([Validators.required, Validators.pattern('^[0-9]{5}$'), Validators.maxLength(5), Validators.minLength(5)]);
      this.domicilio.controls["capDomicilio"].updateValueAndValidity();
    }
  }
  fetchComuni(codProv: string) {
    this.form.get("comuneNascita")?.setValue(null)
    this.datiComuniService.getComuniPerProvincia(codProv).subscribe({
      next: ris => {
        this.listCitta = ris;
      }
    })
  }

  fetchComuniResidenza(codProv: string) {
    this.residenza.get("cittaResidenza")?.setValue(null)
    this.datiComuniService.getComuniPerProvincia(codProv).subscribe({
      next: ris => {
        this.listCittaResidenzaFiltrate = ris;
      }
    })
  }

  fetchComuniDomicilio(codProv: string) {
    this.domicilio.get("cittaDomicilio")?.setValue(null)
    this.datiComuniService.getComuniPerProvincia(codProv).subscribe({
      next: ris => {
        this.listCittaDomicilioFiltrate = ris;
      }
    })
  }

  checkCittadinanza(codiceCittadinanza: string) {
    let codiceNazionalita =
      this.listCittadinanza.filter(citt => citt.codiceCittadinanza == codiceCittadinanza)[0].codiceNazionalita?.codiceNazionalita;
    if (codiceNazionalita == "3") {
      this.isExtraComunitario = true;
      this.cittadinanza.controls["tipoSoggiorno"].addValidators([Validators.required])
      this.cittadinanza.controls["tipoSoggiorno"].updateValueAndValidity();
    } else {
      this.isExtraComunitario = false;
      this.permessoSelected = false;
      this.cartaSelected = false;
      this.cittadinanza.controls["tipoSoggiorno"].clearValidators();
      this.cittadinanza.controls["tipoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].updateValueAndValidity();
      this.cittadinanza.controls["scadenzaSoggiorno"].clearValidators();
      this.cittadinanza.controls["scadenzaSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].updateValueAndValidity();
    }
  }

  myFilter = (d: any): boolean => {
    let day: Date;
    if (d)
      day = new Date(d)
    else
      day = new Date()
    return day < new Date();
  };

  save(): void {
    this.anagraficaService.updateAnagrafica(this.createAnagrafica()).subscribe({
      next: ris => {
        this.anagraficaService.anagraficaCittadino = ris;
        this.anagraficaService.getAnagraficaById(this.cittadino.idCittadino!).subscribe({
          next: ris => {
            if (ris) {
              this.anagrafica = ris;
              this.anagraficaService.anagraficaCittadino = ris
            }
          }
        })

        this.confermaSalvataggio()
      }
    });
  }

  createAnagrafica(): AnagraficaCittadino {
    return {
      ...this.anagrafica,
      cittadino: this.cittadino,
      dataNascita: this.form.get("dataNascita")?.value,
      comuneNascita: this.form.get("comuneNascita")?.value && this.form.get("luogoNascita")!.value == "1" ? {
        codiceIstatComune: this.form.get("comuneNascita")?.value,
        provincia: undefined,
        cap: undefined,
        codiceFiscaleComune: undefined,
        descrizioneComune: undefined
      } : undefined,
      codiceCittadinanza: {
        codiceCittadinanza: this.cittadinanza.get("cittadinanza")!.value!,
        codiceNazionalita: undefined,
        descrizione: "",
        codiceCittadinanzaOld: "",
        descrizionePrecedente: ""
      },
      comuneResidenza:
        this.residenza.get("cittaResidenza")?.value ? {
          codiceIstatComune: this.residenza.get("cittaResidenza")?.value,
          provincia: undefined,
          cap: undefined,
          codiceFiscaleComune: undefined,
          descrizioneComune: undefined
        } : undefined,


      indirizzoResidenza: this.residenza.get("indirizzoResidenza")?.value,
      capResidenza: this.residenza.get("capResidenza")?.value,

      comuneDomicilio: !this.domicilioCoincide ? {
        codiceIstatComune: this.domicilio.get("cittaDomicilio")?.value,
        provincia: undefined,
        cap: undefined,
        codiceFiscaleComune: undefined,
        descrizioneComune: undefined
      } : undefined,
      indirizzoDomicilio: !this.domicilioCoincide ? this.domicilio.get("indirizzoDomicilio")?.value : "",
      capDomicilio: !this.domicilioCoincide ? this.domicilio.get("capDomicilio")?.value : "",
      recapitoEmail: this.form.get("email")?.value,
      recapitoEmail2: this.form.get("emailAlternativo")?.value,
      recapitoTelefono: this.form.get("telefono")?.value,
      recapitoTelefono2: this.form.get("telefonoAlternativo")?.value,
      codiceTitoloDiStudio: {
        codiceTitoloDiStudio: this.form.get("titoloDiStudio")?.value!,
        descrizioneTitoloDiStudio: ""
      },
      svantaggioAbitativo: {
        id: this.form.get("indiceVulnerabilitaAbitativo")?.value,
        descrizioneSvantaggioAbitativo: ""
      },
      codiceCondizioneOccupazionale: {
        codiceCondizioneOccupazionale: this.form.get("situazioneLavorativa")?.value!,
        descrizioneCondizioneOccupazionale: "",
      },
      condizioneOccupazionaleAltro: this.form.get("situazioneLavorativaSpecifica")?.value,
      descCittadinanzaAltro: this.cittadinanza.get("cittadinanzaSpecifica")?.value,
      numeroDocumentoIdentita: this.isExtraComunitario ? "" : this.cittadinanza.get("documentoIdentità")?.value,
      documentoRilasciatoDa: this.isExtraComunitario ? "" : this.cittadinanza.get("documentoIdentitàRilascio")?.value,
      dataScadenzaDocumento: this.isExtraComunitario ? "" : this.cittadinanza.get("scadenzaDocumento")?.value,
      numeroPermessoDiSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("permessoDiSoggiorno")?.value! : null,
      permessoDiSoggiornoRilasciatoDa: this.isExtraComunitario ? this.cittadinanza.get("permessoDiSoggiornoRilasciatoDa")?.value : null,
      dataScadPermessoSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("scadenzaSoggiorno")?.value : null,
      descrMotivoPermessoDiSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("motivoPermessoDiSoggiorno")?.value : null,
      tipoPermessoDiSoggiorno: this.permessoSelected ? 'P' : (this.cartaSelected ? 'C' : undefined),
      titoloDiStudioAltro: this.form.get("titoloDiStudioSpecifica")?.value,
      codUserInserim: this.cittadino.codiceFiscale,
      codUserAggiorn: this.cittadino.codiceFiscale,
      sesso: this.form.get("sesso")?.value,
      //non presente nel form al momento
      codiceStatoEsteroResidenza: undefined,
      descrizioneCittaEsteraResidenza: undefined,
      statoEsteroNascita: this.natalitaEstero.get("stato")?.value && this.form.get("luogoNascita")!.value == "2" ?
        {
          codStato: +this.natalitaEstero.get("stato")!.value!,
          descrizioneStato: "",
          siglaNazione: "",
          statoCodFiscale: "",
        } : undefined,
      descrizioneCittaEsteraNascita: this.natalitaEstero.get("citta")!.value! && this.form.get("luogoNascita")!.value == "2" ? this.natalitaEstero.get("citta")!.value! : undefined,
      //
    }
  }

  onSalvaNuovaAnagrafica() {
    this.form.markAllAsTouched()
    if (this.form?.invalid) {
      this.datiMancanti()
      return;
    }
    if (this.form.dirty || this.toggleChanged) { this.save() }
    this.router.navigate(['/dashboard/home']);
  }

  onIndietro() {
    this.router.navigate(['/dashboard/home']);
  }

  get sessoIcon() {
    return sessionStorage.getItem("sesso") == "male_user" ? "male_user" : "female_user"
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

  onTipoSoggiornoSelected(tipoSoggiorno: string) {
    if (tipoSoggiorno === this.soggiornoSelect[0]) {
      this.permessoSelected = true;
      this.cartaSelected = false;
      this.cittadinanza.controls["permessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.permessoDiSoggiornoValidator])
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].addValidators([Validators.required]);
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].updateValueAndValidity();
      this.cittadinanza.controls["scadenzaSoggiorno"].addValidators([Validators.required]);
      this.cittadinanza.controls["scadenzaSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].addValidators([Validators.required]);
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].updateValueAndValidity();
      this.svuotaCampi();
    } else {
      this.cartaSelected = true;
      this.permessoSelected = false;
      this.cittadinanza.controls["permessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].updateValueAndValidity();
      this.cittadinanza.controls["scadenzaSoggiorno"].clearValidators();
      this.cittadinanza.controls["scadenzaSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.cartaDiSoggiornoValidator])
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.svuotaCampi();
    }
  }

  svuotaCampi() {
    this.cittadinanza.controls["permessoDiSoggiorno"].setValue("");
    this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].setValue("");
    this.cittadinanza.controls["scadenzaSoggiorno"].setValue("");
    this.cittadinanza.controls["motivoPermessoDiSoggiorno"].setValue("");
  }

  checkStatoModifica() {
    let stato = JSON.parse(sessionStorage.getItem('ideaImpresa')!).statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
    if (stato === 'Incontro pre-accoglienza') {
      this.disableToggle = false;

      this.form.controls["sesso"].enable();
      this.form.controls["dataNascita"].enable();
      this.form.controls["luogoNascita"].enable();
      this.form.controls["provinciaNascita"].enable();
      this.form.controls["comuneNascita"].enable();
      this.form.controls["titoloDiStudio"].enable();
      this.form.controls["titoloDiStudioSpecifica"].enable();

      this.residenza.controls["indirizzoResidenza"].enable();
      this.residenza.controls["cittaResidenza"].enable();
      this.residenza.controls["provinciaResidenza"].enable();
      this.residenza.controls["capResidenza"].enable();

      this.domicilio.controls["indirizzoDomicilio"].enable();
      this.domicilio.controls["cittaDomicilio"].enable();
      this.domicilio.controls["provinciaDomicilio"].enable();
      this.domicilio.controls["capDomicilio"].enable();

      this.cittadinanza.controls["cittadinanza"].enable();
      this.cittadinanza.controls["tipoSoggiorno"].enable();
      this.cittadinanza.controls["cittadinanzaSpecifica"].enable();
      this.cittadinanza.controls["permessoDiSoggiorno"].enable();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].enable();
      this.cittadinanza.controls["scadenzaSoggiorno"].enable();
      this.cittadinanza.controls["motivoPermessoDiSoggiorno"].enable();

      this.natalitaEstero.controls["stato"].enable();
      this.natalitaEstero.controls["citta"].enable();

      this.cittadinanza.updateValueAndValidity();
      this.domicilio.updateValueAndValidity();
      this.residenza.updateValueAndValidity();
      this.form.updateValueAndValidity();
      this.natalitaEstero.updateValueAndValidity();
    }
  }

}
