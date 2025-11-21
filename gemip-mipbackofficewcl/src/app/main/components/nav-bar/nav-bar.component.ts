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
import { Component, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';

//-Models
import { NavBarItem } from '@main/models/navbar-item.model';

@Component({
  selector: 'app-nav-bar',
  templateUrl: './nav-bar.component.html',
  styleUrls: ['./nav-bar.component.scss']
})
export class NavBarComponent implements OnInit {
  menuMap: { [key: string]: NavBarItem[] } = {
    "GEMIP_AFFIDATARIO_ALL": NavBarItem.ITEMS_ADMIN,
    "GEMIP_REGIONALE_ALL": NavBarItem.ITEMS_ADMIN,
    "GEMIP_REGIONALE_BASE": NavBarItem.ITEMS_ALTRO,
    "GEMIP_CPI_BASE": NavBarItem.ITEMS_ALTRO,
    "GEMIP_ATTUATORE_BASE": NavBarItem.ITEMS_ATTUATORE,
  };
  navbarItemsList: NavBarItem[] = [];

  modeSideNav = true;

  isExpanded = true;

  constructor(
    public router: Router,
    private bagService: BagService
  ) { }

  ngOnInit(): void {
    if (!this.bagService.ruolo) {
      // fallback, se non abbiamo ancora chiamato flaidoor riprovo tra mezzo secondo
      setTimeout(() => {
        this.navbarItemsList = this.menuMap[this.bagService.ruolo.codiceRuolo!];
      }, 500);
    } else {
      this.navbarItemsList = this.menuMap[this.bagService.ruolo.codiceRuolo!];
    }

    this.ricalcolaFlagIsActive();
    this.router.events.subscribe(
      evt => {
        if (evt instanceof NavigationEnd) {
          this.ricalcolaFlagIsActive();
        }
      }
    );

  }

  navigate(path?: string) {
    if (path)
      this.router.navigateByUrl(path);
  }

  ricalcolaFlagIsActive() {
    this.navbarItemsList.forEach(
      item => {
        item.isActive = this.isActive(item.path);
      }
    );
  }

  isActive(url?: string): boolean {
    if (!url || !this.router.url) return false;
    return this.getSezione(url) == this.getSezione(this.router.url);
  }

  getSezione(s: string): string {
    if (s.startsWith('/')) s = s.substring(1);
    let i = s.indexOf('/gestione');
    if (i > 0) return s.substring(0, i);
    i = s.indexOf('/form');
    if (i > 0) return s.substring(0, i);
    return s;
  }
}
