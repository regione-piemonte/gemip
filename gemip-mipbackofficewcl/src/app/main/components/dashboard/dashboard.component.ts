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

import { BagService } from '@core/services/bag.service';
import { Component, OnInit, HostListener, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { MenuItem } from '@main/models/menu-item.model';
import { InfoService } from '@main/services/info.service';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, AfterViewInit {

  menuMap: { [key: string]: MenuItem[] } = {//Lettura Modifica
    "GEMIP_AFFIDATARIO_ALL": MenuItem.MENU_ADMIN, //LM ALL
    "GEMIP_REGIONALE_ALL": MenuItem.MENU_ADMIN,   //LM ALL
    "GEMIP_REGIONALE_BASE": MenuItem.MENU_ALTRO,            //L ALL M anagrafica/idee
    "GEMIP_CPI_BASE": MenuItem.MENU_ALTRO,                  //L ALL M anagrafica/idee
    "GEMIP_ATTUATORE_BASE": MenuItem.MENU_ATTUATORE,        //L ALL M anagrafica/idee
  };

  menuList = this.menuMap[""];

  menuListOP: boolean[] = [false, false, false, false];
  menuWidth: number = 0;

  constructor(
    private router: Router,
    private infoService: InfoService,
    private bagService: BagService,
    private incontriServiceBag: PreAccoglienzaBagService
  ) { }

  ngOnInit(): void {
    console.log("CIAO 2")
    this.bagService.titolo = "Home"
    this.bagService.icona = "home"

    this.infoService.getUserInfo().subscribe(
      response => {
        let idOperatore = response.idOperatore!;
        this.infoService.getRuoli(idOperatore).subscribe(r => {
          console.log(r);
          sessionStorage.setItem("_ruolo", JSON.stringify(r));
          this.menuList = this.menuMap[r.codiceRuolo!]
        })
      }
    );
  }

  ngAfterViewInit(): void {
    this.getDivWidth();
  }

  @HostListener('window:resize')
  onWindowResize() {
    this.getDivWidth();
  }

  getDivWidth() {
    const element = document.getElementById('card-menu');
    if (element) {
      this.menuWidth = element.offsetWidth;
    }
  }

  navigate(url: string) {
    console.log(url)
    this.router.navigateByUrl(url);
  }

  onClick(disableLabel: boolean, url: string) {
    if (disableLabel) this.navigate(url);
  }

}
