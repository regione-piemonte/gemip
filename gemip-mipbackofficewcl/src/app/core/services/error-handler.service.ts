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

import { ErrorHandler, Injectable } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from './notification.service';
import { switchMap, debounceTime, of, throwError, Observable } from 'rxjs';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class ErrorHandlerService implements ErrorHandler {

  private _errorUrl: string = ""
  errorStatus: number | null = null
  constructor(
    private notificationService: NotificationService
  ) {

  }

  handleError(error: Observable<HttpErrorResponse>) {
    //la switchmap qui è usata per ottenere solo l'ultima chiamata effettuata al metodo e scartare le altre.
    //la variabile errorUrl serve da controllo per evitare che chiamate da request diverse facciano apparire più di una modale
    //di errore, la scelta di usare url è anche per comodità in caso di debug. questo metodo viene riachiamato solo dall'error handler interceptor
    if (error && !(error instanceof Observable))
      console.log(error)
    if (error instanceof Observable) {
      error.pipe(
        switchMap(response => {
          debounceTime(500)
          if (response instanceof HttpErrorResponse) {
            return of(response)
          }
          return throwError(() => {
            return response;
          });
        })
      ).subscribe({
        next: error => {
          if (error instanceof HttpErrorResponse)
            if (this.errorUrl) {
              this.errorUrl = error.url!
            } else {
              this.errorUrl = error.url!
              this.handleHttpError(error)
            }
        }
      })
    }
  }

  private handleHttpError(error: HttpErrorResponse) {

    switch (error.status) {
      case 400: {
        this.notificationService.alert(error.message, 'Errore contatta l\'assistenza o riprova più tardi');
        break;
      }
      case 401: {
        this.notificationService.alert(error.message, 'Credenziali non valide, devi effettuare nuovamente l\'accesso');
        break;
      }
      case 403: {
        this.notificationService.alert(error.message, 'Non possiedi i privilegi necessari per l\'operazione richiesta');
        break;
      }
      case 404: {
        this.notificationService.alert(error.message, 'Pagina o servizio non disponibile, contattare amministratore');
        break;
      }

      case 500: {
        this.notificationService.alert(error.message, 'Errore di sistema contattare l\'amministratore o riprovare più tardi');
        break;
      }
      case 504: {
        this.notificationService.alert(error.message, '504 Pagina o servizio non disponibile, contattare amministratore');
        break;
      }
      case 501: {
        this.notificationService.alert(error.message, "");
        break;
      }
      default: {
        this.notificationService.alert(error.message, 'Il servizio non è disponibile, riprova più tardi!');
        break;
      }
    }
  }

  set errorUrl(url: string) {
    this._errorUrl = url
  }

  get errorUrl(): string {
    return this._errorUrl
  }
}
