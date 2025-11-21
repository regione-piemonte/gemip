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
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { IncontroPreaccoglienzaService } from '../../services/incontro-preaccoglienza.service';
import { TutorService } from '../../services/tutor.service';
import { Tutor } from 'src/app/core/models/tutor';
import { BagService } from 'src/app/core/services/bag.service';
import { IdeaImprenditorialeService } from '../../services/idea-imprenditoriale.service';
import { AreaTerritoriale } from 'src/app/core/models/areaTerritoriale';

@Component({
  selector: 'c-mip-scelta-tutor',
  templateUrl: './scelta-tutor.component.html',
  styleUrls: ['./scelta-tutor.component.scss']
})
export class SceltaTutorComponent implements OnInit {
  formScelta: any;


  sbloccoAreaTerritoriale: boolean = false;
  areeTerriList: AreaTerritoriale[] = [];
  soggettiAttuatori: Tutor[] = [];

  constructor(
    private router: Router,
    private dialog: MatDialog,
    private incontroPreaccoglienzaService: IncontroPreaccoglienzaService,
    private ideaImprenditorialeService: IdeaImprenditorialeService,
    private tutorService: TutorService,
    private fb: FormBuilder,
    private bagService: BagService
  ) { }

  ngOnInit(): void {
    this.sbloccoAreaTerritoriale = this.ideaImprenditorialeService.ideaDiImpresa!.sbloccoAreaTerritoriale!;

    if (this.sbloccoAreaTerritoriale) {
      //sbloccoTutor
      this.formScelta = this.fb.group({
        soggettoArea: ['', Validators.required],
        soggettoAttuatore: ['', Validators.required]
      })
      this.tutorService.getAreeTerritoriali().subscribe(r => this.areeTerriList = r);
    } else {
      //normaleflusso
      this.formScelta = this.fb.group({
        soggettoAttuatore: ['', Validators.required]
      })
      const codArea = this.incontroPreaccoglienzaService.cittadinoIncontroPreaccoglienza!.codiceAreaTerritorialeSelezionata!.codiceAreaTerritoriale!.toString();
      this.tutorService.getTutorAbilitati(codArea, this.sbloccoAreaTerritoriale).subscribe(
        r => this.soggettiAttuatori = r
      )
    }
  }

  @ViewChild('conferma') conferma!: TemplateRef<any>;
  @ViewChild('attenzione') attenzione!: TemplateRef<any>;

  openDialogConferma() {
    if (JSON.parse(sessionStorage.getItem('ideaImpresa')!).idTutor && !this.sbloccoAreaTerritoriale) {
      console.log("sono qui 1");
      this.dialog.open(this.attenzione, {
        width: "40vw",
        panelClass: "ClasseCss",
        disableClose: true
      });
    } else {
      let idTutor: string = (<Tutor>this.formScelta.get('soggettoAttuatore')!.value).soggettoAttuatore!.id!.toString();

      let idImpresa: string = sessionStorage.getItem("idIdeaDiImpresa")!
      this.bagService.resetError();
      this.ideaImprenditorialeService.getIdeaDiImpresaById(idImpresa).subscribe({
        next: ris => {
          if (ris.idTutor && !this.sbloccoAreaTerritoriale) {
            this.dialog.open(this.attenzione, {
              width: "40vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          } else {
            this.tutorService.selectTutor(idTutor, idImpresa).subscribe(r => console.log("OK--", r));

            this.dialog.open(this.conferma, {
              width: "60vw",
              panelClass: "ClasseCss",
              disableClose: true
            });
          }
        }
      })
    }
  }
  onConfermaVerifica() {

    sessionStorage.setItem('sceltaTutor', 'true');


    this.router.navigate(['/dashboard/home']);
  }

  onAreaTerritorialeChange() {
    this.formScelta.controls['soggettoAttuatore'].setValue('');
    this.tutorService.getSoggettiAttuatoriByAreaTerritoriale(String(this.formScelta.controls['soggettoArea'].value))
      .subscribe(r => this.soggettiAttuatori = r)
  }
}
