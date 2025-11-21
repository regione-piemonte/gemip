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

import { Component, Inject, Injectable } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogSettings } from '@shared/model/dialog-settings.model';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(
    private dialog: MatDialog,
    private snackBar: MatSnackBar
  ) {
  }

  notify(message: string) {
    this.snackBar.open(message, 'Ok', { duration: 2000 });
  }

  alert(message: string, title: string) {

    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(
        "Errore",
        [title],
        "card-body--danger"
      ),
      disableClose: true
    });
  }

  confirm(message: string, title: string, action: () => void) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message, title },
      panelClass: "custom-matdialog",
      disableClose: true
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result) {
        action();
      }
    });

  }

}

@Component({
  template: `
    <div mat-dialog-title>{{data.title}}</div>
    <div mat-dialog-content>{{data.message}}</div>
    <div mat-dialog-actions>
      <button mat-button (click)="onNoClick()">No</button>
   <!--   <button mat-button [mat-dialog-close]="true">Sì</button>-->
    </div>`
})
export class ConfirmDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<ConfirmDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string, title: string }) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }
}

@Component({
  template: `
    <div mat-dialog-title>{{data.title}}</div>
    <div mat-dialog-content>{{data.message}}</div>
    <div mat-dialog-actions>
    <!--  <button mat-button [mat-dialog-close]="true">Ok</button>-->
    </div>`
})
export class AlertDialogComponent {
  constructor(
    public dialogRef: MatDialogRef<AlertDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { message: string, title: string }) {
  }

}
