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

import { Location } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { AnagraficaCittadino } from '@core/models/anagraficaCittadino';
import { Cittadinanza } from '@core/models/cittadinanza';
import { Cittadino } from '@core/models/cittadino';
import { Comune } from '@core/models/comune';
import { CondizioneOccupazionale } from '@core/models/condizioneOccupazionale';
import { Provincia } from '@core/models/provincia';
import { StatoEstero } from '@core/models/statoEstero';
import { SvantaggioAbitativo } from '@core/models/svantaggioAbitativo';
import { TitoloDiStudio } from '@core/models/titoloDiStudio';
import { UserInfo } from '@core/models/userInfo';
import { BagService } from '@core/services/bag.service';
import { Validation } from '@shared/components/_validation/validation';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { IconsSettings } from '@shared/utils/icons-settings';
import { AnagraficaService } from '@utenti/services/anagrafica.service';
import { UtentiService } from '@utenti/services/utenti.service';


@Component({
  selector: 'app-cittadino-form',
  templateUrl: './cittadino-form.component.html',
  styleUrls: ['./cittadino-form.component.scss']
})
export class CittadinoFormComponent implements OnInit {

  selectedTab = 0

  cittadino?: Cittadino;
  anagrafica?: AnagraficaCittadino;
  domicilioCoincide = true;
  isExtraComunitario = false;
  userInfo!: UserInfo;

  permessoSelected: boolean = false;
  cartaSelected: boolean = false;

  soggiornoSelect: string[] = [
    "Permesso di Soggiorno",
    "Carta di Soggiorno"
  ]

  residenza: FormGroup = this.fb.group({
    indirizzoResidenza: ['', [Validators.required]],
    cittaResidenza: ['', [Validators.required]],
    provinciaResidenza: ['', [Validators.required]],
    capResidenza: ['', [Validators.required, Validators.pattern('^[0-9]{5}$')]]
  });

  domicilio: FormGroup = this.fb.group({
    indirizzoDomicilio: [''],
    cittaDomicilio: [''],
    provinciaDomicilio: [''],
    capDomicilio: ['', [Validators.pattern('^[0-9]{5}$')]]
  });

  natalitaEstero = this.fb.group({
    stato: [{ value: '', disabled: true },],
    citta: [{ value: '', disabled: true }, [Validators.maxLength(100)]],
  });



  cittadinanza: FormGroup = this.fb.group({
    cittadinanza: ["", [Validators.required]],
    descCittadinanzaAltro: [""],
    tipoSoggiorno: [null],
    permessoDiSoggiorno: [null],
    permessoDiSoggiornoRilasciatoDa: [null],
    dataScadPermessoSoggiorno: [{ value: null, disabled: false }],
    descrMotivoPermessoDiSoggiorno: [null],
  })


  form: FormGroup = this.fb.group({
    dataNascita: [{ value: null, disabled: true }, [Validators.required]],
    sesso: ['', Validators.required],
    luogoNascita: [{ value: '1', disabled: true }, [Validators.required]],
    comuneNascita: [{ value: null, disabled: true }, [Validators.required]],
    provinciaNascita: [{ value: null, disabled: true }, [Validators.required]],
    codiceFiscale: ['', [Validation.cFiscaleValidator, Validators.maxLength(16)]],
    telefono: ['', [Validation.phoneNumberRequired, Validators.maxLength(14)]],
    telefono2: ['', [Validation.phoneNumberValidator, Validators.maxLength(14)]],
    email: ['', [Validation.emailRequired, Validators.maxLength(100)]],
    email2: ['', [Validation.emailValidator, Validators.maxLength(100)]],
    residenza: this.residenza,
    domicilio: this.domicilio,
    natalitaEstero: this.natalitaEstero,
    cittadinanza: this.cittadinanza,
    titoloDiStudio: ["", [Validators.required]],
    titoloDiStudioAltro: [""],
    indiceVulnerabilitaAbitativo: [null, [Validators.required]],
    situazioneLavorativa: ["", [Validators.required]],
    situazioneLavorativaAltro: [""],
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

  testoTitoloStudio: string = "";
  testoIndiceAbitativo: string = "";
  testoSituazioneAbitativa: string = "";
  constructor(private fb: FormBuilder,
    private anagraficaService: AnagraficaService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private utenteService: UtentiService,
    private bagService: BagService,
    private location: Location) { }

  ngOnInit(): void {
    this.bagService.titolo = "Modifica cittadino"
    this.bagService.icona = IconsSettings.UTENTI_ICON

    this.utenteService.leftComponent = false;

    this.userInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)

    this.anagrafica = this.utenteService.anagraficaSelected;
    if (!this.anagrafica) {
      // se l'utente preme F5, prendiamo i dati dalla query
      const id = this.route.snapshot.paramMap.get("id");
      if (id) {
        this.anagraficaService.getAnagraficaById(+id).subscribe({
          next: (response) => {
            this.anagrafica = response;
            this.popolaDati();

          }
        });
      }
    } else {
      this.popolaDati();
    }

    this.anagraficaService.getProvince().subscribe({
      next: ris => {
        this.listProvince = ris;
      }
    })

    this.anagraficaService.getCittadinanze().subscribe({
      next: ris => {
        this.listCittadinanza = ris;
      }
    })

    this.anagraficaService.getStatiEsteri().subscribe({
      next: ris => {
        this.statiEsteriList = ris
      }
    })

    this.anagraficaService.getSvantaggiAbitativi().subscribe({
      next: ris => {
        this.listIndiciVulnerabilitaAbitativo = ris;
      }
    })

    this.anagraficaService.getCondizioniOccupazionali().subscribe({
      next: ris => {
        this.listCondizioneOccupazionale = ris;
      }
    })

    this.anagraficaService.getTitoliDiStudio().subscribe({
      next: ris => {
        this.listTitoliStudio = ris
      }
    })
  }

  print() {
    window.print()
  }

  popolaDati() {
    this.form.controls["dataNascita"].setValue(this.anagrafica?.dataNascita)
    this.form.controls["sesso"].setValue(this.anagrafica?.sesso)
    this.fetchComuni(this.anagrafica?.comuneNascita?.provincia?.codiceProvincia!)
    this.form.controls["comuneNascita"].setValue(this.anagrafica?.comuneNascita?.codiceIstatComune)
    this.form.controls["provinciaNascita"].setValue(this.anagrafica?.comuneNascita?.provincia?.codiceProvincia)
    this.form.controls["telefono"].setValue(this.anagrafica?.recapitoTelefono)
    this.form.controls["telefono2"].setValue(this.anagrafica?.recapitoTelefono2)
    this.form.controls["email"].setValue(this.anagrafica?.recapitoEmail)
    this.form.controls["email2"].setValue(this.anagrafica?.recapitoEmail2)
    this.form.controls["indiceVulnerabilitaAbitativo"].setValue(this.anagrafica?.svantaggioAbitativo?.id)

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

    this.cittadinanza.controls["cittadinanza"].setValue(this.anagrafica?.codiceCittadinanza?.codiceCittadinanza!)
    this.cittadinanza.controls["descCittadinanzaAltro"].setValue(this.anagrafica?.descCittadinanzaAltro!);

    this.form.controls["titoloDiStudio"].setValue(this.anagrafica?.codiceTitoloDiStudio?.codiceTitoloDiStudio!)
    this.form.controls["titoloDiStudioAltro"].setValue(this.anagrafica?.titoloDiStudioAltro)

    this.form.controls["situazioneLavorativa"].setValue(this.anagrafica?.codiceCondizioneOccupazionale?.codiceCondizioneOccupazionale!)
    this.form.controls["situazioneLavorativaAltro"].setValue(this.anagrafica?.condizioneOccupazionaleAltro)

    if (this.anagrafica!.statoEsteroNascita) {
      this.natalitaEstero.controls["stato"].setValue(this.anagrafica?.statoEsteroNascita?.codStato!.toString()!)
      this.natalitaEstero.controls["citta"].setValue(this.anagrafica?.descrizioneCittaEsteraNascita!)
      this.form.controls["luogoNascita"].setValue("2")
    }
    if (this.anagrafica!.codiceCittadinanza?.codiceNazionalita?.codiceNazionalita == "3") {
      if (this.anagrafica?.tipoPermessoDiSoggiorno === 'C') {
        this.cartaSelected = true;
        this.isExtraComunitario = true;
        this.cittadinanza.controls["tipoSoggiorno"].setValue(this.soggiornoSelect[1])
        this.cittadinanza.controls["permessoDiSoggiorno"].setValue(this.anagrafica?.numeroPermessoDiSoggiorno!);
        this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.cartaDiSoggiornoValidator])
      } else {
        this.cittadinanza.controls["tipoSoggiorno"].setValue(this.soggiornoSelect[0])
        this.permessoSelected = true;
        this.isExtraComunitario = true;
        this.cittadinanza.controls["permessoDiSoggiorno"].setValue(this.anagrafica?.numeroPermessoDiSoggiorno!);
        this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].setValue(this.anagrafica?.permessoDiSoggiornoRilasciatoDa!);
        this.cittadinanza.controls["dataScadPermessoSoggiorno"].setValue(this.anagrafica?.dataScadPermessoSoggiorno!);
        this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].setValue(this.anagrafica?.descrMotivoPermessoDiSoggiorno!);
        this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.permessoDiSoggiornoValidator])
        this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].addValidators(Validators.required)
        this.cittadinanza.controls["dataScadPermessoSoggiorno"].addValidators(Validators.required)
      }
      this.cittadinanza.updateValueAndValidity();
    } else {
      this.isExtraComunitario = false;
      this.cittadinanza.controls["tipoSoggiorno"].clearValidators();
      this.cittadinanza.controls["tipoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].updateValueAndValidity();
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].clearValidators();
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].updateValueAndValidity();

    }
    this.form.updateValueAndValidity();
  }
  fetchComuni(codProv: string) {
    this.anagraficaService.getComuniForProvincia(codProv).subscribe({
      next: ris => {
        this.listCitta = ris;
      }
    })
  }

  fetchComuniResidenza(codProv: string) {

    this.anagraficaService.getComuniForProvincia(codProv).subscribe({
      next: ris => {
        this.listCittaResidenzaFiltrate = ris;
      }
    })
  }

  fetchComuniDomicilio(codProv: string) {

    this.anagraficaService.getComuniForProvincia(codProv).subscribe({
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
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].clearValidators();
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].updateValueAndValidity();
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

  onProvinciaResidenzaChange(codProv: string) {
    this.residenza.controls["cittaResidenza"].setValue(null);
    this.residenza.controls["cittaResidenza"].updateValueAndValidity();
    this.fetchComuniResidenza(codProv)
  }
  onProvinciaDomicilioChange(codProv: string) {
    this.domicilio.controls["cittaDomicilio"].setValue(null);
    this.domicilio.controls["cittaDomicilio"].updateValueAndValidity();
    this.fetchComuniDomicilio(codProv)
  }
  save(): void {
    this.bagService.resetError();
    if (!this.anagrafica) {
      this.anagraficaService.insertAnagrafica(this.createAnagrafica()).subscribe({
        next: ris => {
          this.anagraficaService.anagraficaCittadino = ris
          this.confermaSalvataggio()
        }
      });
    } else {
      let tmp = this.createAnagrafica();
      tmp.dataInserim = this.anagrafica.dataInserim;
      tmp = { ...tmp, id: this.anagrafica.id }
      this.anagraficaService.updateAnagrafica(tmp).subscribe({
        next: ris => {
          this.anagraficaService.anagraficaCittadino = ris
          this.confermaSalvataggio()
        }
      });
    }
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
  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose: true
    })
  }
  confermaSalvataggio() {
    this.openDialog("Avviso", ["I dati sono stati salvati correttamente"], "card-body--success")
  }

  createAnagrafica(): AnagraficaCittadino {
    return {
      ...this.anagrafica,
      cittadino: this.anagrafica?.cittadino,
      dataNascita: this.form.get("dataNascita")?.value,
      sesso: this.form.get('sesso')?.value,
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
      recapitoEmail2: this.form.get("email2")?.value,
      recapitoTelefono: this.form.get("telefono")?.value,
      recapitoTelefono2: this.form.get("telefono2")?.value,
      codiceTitoloDiStudio: {
        codiceTitoloDiStudio: this.form.get("titoloDiStudio")?.value!,
        descrizioneTitoloDiStudio: ""
      },
      titoloDiStudioAltro: this.form.get('titoloDiStudioAltro')?.value!,
      svantaggioAbitativo: {
        id: this.form.get("indiceVulnerabilitaAbitativo")?.value,
        descrizioneSvantaggioAbitativo: ""
      },
      codiceCondizioneOccupazionale: {
        codiceCondizioneOccupazionale: this.form.get("situazioneLavorativa")?.value!,
        descrizioneCondizioneOccupazionale: ""
      },
      condizioneOccupazionaleAltro: this.form.get('situazioneLavorativaAltro')?.value!,
      codUserAggiorn: this.userInfo!.codFisc,
      numeroPermessoDiSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("permessoDiSoggiorno")?.value! : null,
      descrMotivoPermessoDiSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("descrMotivoPermessoDiSoggiorno")?.value! : null,
      dataScadPermessoSoggiorno: this.isExtraComunitario ? this.cittadinanza.get("dataScadPermessoSoggiorno")?.value! : null,
      permessoDiSoggiornoRilasciatoDa: this.isExtraComunitario ? this.cittadinanza.get("permessoDiSoggiornoRilasciatoDa")?.value! : null,
      tipoPermessoDiSoggiorno: this.isExtraComunitario ? (this.cartaSelected ? 'C' : 'P') : undefined,
      descCittadinanzaAltro: this.cittadinanza.get("descCittadinanzaAltro")?.value,
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

    }
  }

  onDomicilioDiversoResidenzaChange() {

    this.domicilioCoincide = !this.domicilioCoincide
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
  onSalva() {
    this.form.markAllAsTouched();
    if (this.form?.invalid) {
      return;
    }
    this.save();
  }

  tornaIndietro(): void {
    this.location.back();
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
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].addValidators([Validators.required]);
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].addValidators([Validators.required]);
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].updateValueAndValidity();
      this.svuotaCampi();
    } else {
      this.cartaSelected = true;
      this.permessoSelected = false;
      this.cittadinanza.controls["permessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].clearValidators();
      this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].updateValueAndValidity();
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].clearValidators();
      this.cittadinanza.controls["dataScadPermessoSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].clearValidators();
      this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].updateValueAndValidity();
      this.cittadinanza.controls["permessoDiSoggiorno"].addValidators([Validators.required, Validation.cartaDiSoggiornoValidator])
      this.cittadinanza.controls["permessoDiSoggiorno"].updateValueAndValidity();
      this.svuotaCampi();
    }
  }

  svuotaCampi() {
    this.cittadinanza.controls["permessoDiSoggiorno"].setValue("");
    this.cittadinanza.controls["permessoDiSoggiornoRilasciatoDa"].setValue("");
    this.cittadinanza.controls["dataScadPermessoSoggiorno"].setValue("");
    this.cittadinanza.controls["descrMotivoPermessoDiSoggiorno"].setValue("");
  }

}
