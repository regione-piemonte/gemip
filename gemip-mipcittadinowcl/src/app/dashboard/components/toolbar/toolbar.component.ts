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

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DashboardService } from '../../services/dashboard.service';
import { Cittadino } from 'src/app/core/models/cittadino';
import { environment } from 'src/environments/environment';
import { BreakpointService } from '../../services/breakpoint.service';
import { Breakpoints } from '@angular/cdk/layout';

@Component({
  selector: 'c-mip-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {

  user?: Cittadino;
  modificaContatti?: boolean;

  constructor(private router: Router, private dashboardService: DashboardService,
    private breakpointService: BreakpointService,
  ) {
    this.breakpointService.observeBreakpoints([
      Breakpoints.XSmall,
      Breakpoints.Small,
      Breakpoints.Medium,
      Breakpoints.Large,
      Breakpoints.XLarge
    ]).subscribe(result => {
      this.isXSmallScreen = result[Breakpoints.XSmall];
      this.isSmallScreen = result[Breakpoints.Small];
      this.isMediumScreen = result[Breakpoints.Medium];
      this.isLargeScreen = result[Breakpoints.Large];
      this.isXLargeScreen = result[Breakpoints.XLarge];
      this.showTitleResponsive();
    });
  }


  ngOnInit(): void {
    //this.user=JSON.parse(sessionStorage.getItem("cittadino")!)
    this.dashboardService.modificaContatti.subscribe({
      next: ris => {
        this.modificaContatti = ris;
      }
    })
    this.dashboardService._user.subscribe(
      r => {
        this.user = r
      }
    )
  }

  logout() {
    sessionStorage.clear();
    localStorage.clear();
    this.dashboardService.clearLista();
    window.location.href = window.location.origin + environment.logoutUrl;
  }

  modificaAnagrafica() {
    this.router.navigate(['/dashboard/modifica-anagrafica']);
  }

  onClear() {
    sessionStorage.clear();
    localStorage.clear();
    this.dashboardService.clearLista();
    this.router.navigate(['/test']);
  }

  isXSmallScreen!: boolean;
  isSmallScreen!: boolean;
  isMediumScreen!: boolean;
  isLargeScreen!: boolean;
  isXLargeScreen!: boolean;

  showTitleResponsive(): boolean {
    if (this.isXSmallScreen) return true;
    if (this.isSmallScreen) return true;
    return false;
  }

  onDownloadManuale() {
    const url = '/assets/manuale/MIP Cittadino - Manuale d’uso.pdf';
    const link = document.createElement('a');
    link.href = url;
    link.target = '_blank';
    link.download = 'MIP Cittadino - Manuale d’uso.pdf';
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  }

  getVoceMenu() {
    let stato = JSON.parse(sessionStorage.getItem('ideaImpresa')!).statoIdeaDiImpresa.descrizioneStatoIdeaDiImpresa;
    if (stato === 'Inserito') {
      this.modificaContatti = false;
      return "";
    } else if (stato === 'Incontro pre-accoglienza') {
      this.modificaContatti = true;
      return "Modifica dati personali"
    } else {
      this.modificaContatti = true;
      return "Modifica contatti personali";
    }
  }
}
