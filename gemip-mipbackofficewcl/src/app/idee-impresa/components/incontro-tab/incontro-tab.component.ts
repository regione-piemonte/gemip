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

import { Component, Input, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { AreaTerritoriale } from '@core/models/areaTerritoriale';
import { CittadinoIncontroPreaccoglienza } from '@core/models/cittadinoIncontroPreaccoglienza';
import { IncontroPreaccoglienza } from '@core/models/incontroPreaccoglienza';
import { Ruolo } from '@core/models/ruolo';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { BagService } from '@core/services/bag.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { ComuniAreeService } from '@shared/services/comuni-aree.service';

@Component({
  selector: 'app-incontro-tab',
  templateUrl: './incontro-tab.component.html',
  styleUrls: ['./incontro-tab.component.scss']
})
export class IncontroTabComponent implements OnInit {

  @Input() isStampa = false
  @ViewChild('sceltaIncontro') sceltaIncontro!: TemplateRef<any>;

  formIncontro = this.fb.group({
    areaTerritoriale: ['', Validators.required],
    incontroPreaccoglienza: ['', Validators.required]
  });

  soggetto: SoggettoAttuatore | undefined;
  superUser: boolean = false;
  incontro?: IncontroPreaccoglienza;

  canCambiareIncontro: boolean = false;
  areeTerritoriali: AreaTerritoriale[] = [];
  incontroPreaccoglienzaList: IncontroPreaccoglienza[] = [];

  constructor(
    private ideaImpService: IdeaImpresaService,
    private router: Router,
    private incontriPreaccoglienzaBag: PreAccoglienzaBagService,
    private incontriPreaccoglienzaService: PreAccoglienzaService,
    private bag: BagService,
    private dialoge: MatDialog,
    private fb: FormBuilder,
    private comuniAreeService: ComuniAreeService,
  ) { }

  ngOnInit(): void {
    let idea = this.ideaImpService.ideeImp?.ideaDiImpresa!
    this.soggetto = JSON.parse(sessionStorage.getItem('soggettoAttuatore')!)
    const ruolo: Ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!);
    if (ruolo.codiceRuolo && ruolo.codiceRuolo.includes('ALL')) {
      this.superUser = true;
    }
    if (idea.statoIdeaDiImpresa?.id == 6) {
      this.canCambiareIncontro = true;
    }
    this.ideaImpService.getIncontroByIdea(idea.id!.toString()).subscribe(r => {
      this.incontro = r;
    });
  }

  onModifica() {
    if (this.incontro) {
      this.incontriPreaccoglienzaService.canModify = this.bag.isRuoloAdmin()
      this.incontriPreaccoglienzaBag.idIncontroPreaccoglienza = this.incontro.id
      this.router.navigateByUrl('/pre-accoglienza/modifica')
    }
  }

  onModificaIncontroAssociato() {
    this.comuniAreeService.getAreeTerritoriali().subscribe({
      next: (response) => {
        this.areeTerritoriali = response;
      }
    });

    this.dialoge.open(this.sceltaIncontro, {
      disableClose: true,
    })
  }

  onScheltaAreaTerritoriale(event: any) {
    console.log(event)
    this.ideaImpService.getIncontriForAreaTerritoriale(event.value).subscribe({
      next: (risp) => {
        this.incontroPreaccoglienzaList = risp;
      }
    })
  }

  onSceltaIncontroPreaccoglienza(event: IncontroPreaccoglienza) {

  }

  modificaIncontroPreaccoglienza() {
    this.formIncontro.markAllAsTouched();

    if (!this.formIncontro.valid) {
      return;
    }

    console.log("modificaIncontroPreaccoglienza", this.incontro);

    const cittadinoIncontro: CittadinoIncontroPreaccoglienza = {
      cittadino: this.ideaImpService.ideeImp?.citadino!, // Assicurati che cittadino non sia undefined
      incontroPreaccoglienza: this.incontroPreaccoglienzaList.find(
        e => e.id === Number(this.formIncontro.controls.incontroPreaccoglienza.value)
      ), // Assicurati che trovi l'elemento
      codUserAggiorn: this.bag.userInfo.codFisc, // Assicurati che codFisc esista
      codiceAreaTerritorialeSelezionata: this.areeTerritoriali.find(
        e => e.codiceAreaTerritoriale === this.formIncontro.controls.areaTerritoriale.value
      ) // Assicurati che trovi l'elemento
    };

    this.ideaImpService.updateCittadinoIncontroPreaccoglienza(cittadinoIncontro).subscribe({
      next: response => {
        console.log(response);
        this.refreshIncontro();
      }
    });
  }

  private refreshIncontro() {
    const ideaId = this.ideaImpService.ideeImp?.ideaDiImpresa?.id?.toString();
    if (ideaId) {
      this.ideaImpService.getIncontroByIdea(ideaId).subscribe({
        next: r => {
          this.incontro = r;
        }
      });
    }
  }

  get canModify() {
    return this.bag.isRuoloAdmin();
  }
}
