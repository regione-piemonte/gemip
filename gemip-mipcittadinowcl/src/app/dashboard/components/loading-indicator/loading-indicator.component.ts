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

import { OverlayConfig, OverlayRef } from '@angular/cdk/overlay';
import { Component, DoCheck, Input, OnInit, ViewContainerRef, TemplateRef, ViewChild } from '@angular/core';
import { ThemePalette } from '@angular/material/core';
import { ProgressSpinnerMode } from '@angular/material/progress-spinner';
import { OverlayService } from 'src/app/core/services/overlay.service';


@Component({
  selector: 'app-loading-indicator',
  templateUrl: './loading-indicator.component.html',
  styleUrls: ['./loading-indicator.component.scss']
})
export class LoadingIndicatorComponent implements OnInit, DoCheck {

  @Input() color?: ThemePalette;
  @Input() diameter?: number = 100;
  @Input() mode: ProgressSpinnerMode = 'indeterminate' ;
  @Input() strokeWidth?: number;
  @Input() value?: number;
  @Input() backdropEnabled = true;
  @Input() positionGloballyCenter = true;

  @ViewChild('progressSpinnerRef') private progressSpinnerRef!: TemplateRef<any>;
  private progressSpinnerOverlayConfig!: OverlayConfig;
  private overlayRef!: OverlayRef;

  constructor(private vcRef: ViewContainerRef,
              private overlayService: OverlayService
              ) {
  }

  ngOnInit() {
    this.progressSpinnerOverlayConfig = { hasBackdrop: this.backdropEnabled};
    if (this.positionGloballyCenter) {
      this.progressSpinnerOverlayConfig.positionStrategy = this.overlayService.positionGloballyCenter();
    }
    this.overlayRef = this.overlayService.createOverlay(this.progressSpinnerOverlayConfig);
  }

  ngDoCheck() {
    if (this.show && !this.overlayRef.hasAttached()) {
      this.overlayService.attachTemplatePortal(this.overlayRef, this.progressSpinnerRef, this.vcRef);
    } else if (!this.show && this.overlayRef.hasAttached()) {
      this.overlayRef.detach();
    }
  }

  get show(): boolean {
    return this.overlayService.show;
  }

}
