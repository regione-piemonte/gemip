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

import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ConfirmationDialogComponent } from './components/confirmation-dialog/confirmation-dialog.component';
import { GetFirstLetterPipe } from './pipes/get-first-letter.pipe';
import { MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { MatButtonModule } from '@angular/material/button';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatCardModule } from '@angular/material/card';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatDialogModule } from '@angular/material/dialog';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatListModule } from '@angular/material/list';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatRadioModule } from '@angular/material/radio';
import { MatSelectModule } from '@angular/material/select';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatStepperModule } from '@angular/material/stepper';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { InserimentoDatiUtenteComponent } from '../test/inserimento-dati-utente/inserimento-dati-utente.component';
import { DialogAlertComponent } from './components/dialog-alert/dialog-alert.component';
import { DialogConfermaComponent } from './components/dialog-conferma/dialog-conferma.component';
import { CastFromControlPipe } from './pipes/cast-from-control.pipe';
import { CustomDateAdapter } from './utils/custom-date-adapter';
import { ControlMessagesComponent } from './utils/validation';


const formModule = [
  FormsModule,
  ReactiveFormsModule
]

const materialModules = [
  MatIconModule,
  MatButtonModule,
  MatCardModule,
  MatDialogModule,
  MatExpansionModule,
  MatToolbarModule,
  MatSidenavModule,
  MatTabsModule,
  MatStepperModule,
  MatTableModule,
  MatSortModule,
  MatSnackBarModule,
  MatSelectModule,
  MatProgressSpinnerModule,
  MatPaginatorModule,
  MatInputModule,
  MatCardModule,
  MatListModule,
  MatRadioModule,
  MatCheckboxModule,
  MatTooltipModule,
  MatMenuModule,
  MatGridListModule,
  MatDatepickerModule,
  MatSlideToggleModule,
  MatButtonToggleModule,
];

@NgModule({
  declarations: [
    CastFromControlPipe,
    GetFirstLetterPipe,
    ConfirmationDialogComponent,
    ControlMessagesComponent,
    DialogConfermaComponent,
    DialogAlertComponent,
    InserimentoDatiUtenteComponent,

  ],
  imports: [
    CommonModule,
    RouterModule,
    formModule,
    materialModules
  ],
  exports: [
    formModule,
    materialModules,
    GetFirstLetterPipe,
    CastFromControlPipe,
    ConfirmationDialogComponent,
    ControlMessagesComponent
  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'it-IT'},
    {provide: DateAdapter, useClass: CustomDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
  ]
})
export class SharedModule {
  static forRoot(): ModuleWithProviders<SharedModule> {
    return {
      ngModule: SharedModule
    };
  }
}
