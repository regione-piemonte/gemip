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

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';

//- Material
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatGridListModule } from '@angular/material/grid-list';
import { MAT_SELECT_CONFIG, MatSelectConfig, MatSelectModule } from '@angular/material/select';
import { MatTabsModule } from '@angular/material/tabs';
import { MatMenuModule } from '@angular/material/menu';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatStepperModule } from '@angular/material/stepper';
import { MatListModule } from '@angular/material/list';
import { MatRadioModule } from '@angular/material/radio';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';

//- CDK
import { CdkMenuModule } from '@angular/cdk/menu';

//-DATE PICKER
import { DateAdapter, MAT_DATE_FORMATS, MAT_DATE_LOCALE } from '@angular/material/core';
import { MAT_MOMENT_DATE_FORMATS } from '@angular/material-moment-adapter';
import { CustomDateAdapter } from './utils/custom-date-adapter';

//-Pipes
import { CastFromControlPipe } from './pipes/cast-from-control.pipe';

//-components
import { CriteriRicercaComponent } from './components/criteri-ricerca/criteri-ricerca.component';
import { ControlMessagesComponent } from './components/_validation/validation';
import { TableComponent } from './components/table/table.component';
import { DialogConfermaComponent } from './components/dialog-conferma/dialog-conferma.component';

//-Calendar
import { CalendarCommonModule, CalendarDayModule, CalendarMonthModule, CalendarWeekModule } from 'angular-calendar';
import { FlatpickrModule } from 'angularx-flatpickr';

const components = [
  CriteriRicercaComponent,
  ControlMessagesComponent,
  TableComponent,
  DialogConfermaComponent
]

const pipes = [
  CastFromControlPipe,
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
  MatAutocompleteModule,
  CdkMenuModule,
  MatSlideToggleModule,
];
const modules = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,

  //-Calendar
  CalendarCommonModule,
  CalendarMonthModule,
  CalendarWeekModule,
  CalendarDayModule,
  FlatpickrModule.forRoot()
]

const matSelectConfig: MatSelectConfig = {
  typeaheadDebounceInterval: 400  // questo parametro regola l'evento KEY_DOWN sulla mat-select
}

@NgModule({
  declarations: [ components, pipes],
  imports: [ materialModules, modules,

  ],
  exports: [ materialModules, modules,
              components, pipes,

  ],
  providers: [
    {provide: MAT_DATE_LOCALE, useValue: 'it-IT'},
    // https://www.concretepage.com/angular-material/angular-material-datepicker-moment
    {provide: DateAdapter, useClass: CustomDateAdapter, deps: [MAT_DATE_LOCALE]},
    {provide: MAT_DATE_FORMATS, useValue: MAT_MOMENT_DATE_FORMATS},
    {provide: MAT_SELECT_CONFIG, useValue: matSelectConfig}
  ],
})
export class SharedModule { }
