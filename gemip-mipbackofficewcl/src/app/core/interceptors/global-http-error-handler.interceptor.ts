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

import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse,
  HttpResponse
} from '@angular/common/http';
import { catchError, debounceTime, Observable, of, retry, switchMap, throwError, timer } from 'rxjs';
import { ErrorHandlerService } from '@core/services/error-handler.service';

@Injectable()
export class GlobalHttpErrorHandler implements HttpInterceptor {

  constructor(
    private errorService: ErrorHandlerService
  ) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      retry({
        count: 2,
        delay: (_, retryCount) => timer(retryCount * 300),

      }),
      catchError(err => {
        return of(err)
      }),
      switchMap(response => {
        debounceTime(500)
        if (response instanceof HttpErrorResponse) {
          this.errorService.handleError(of(response))
          return throwError(() => {
            return response;
          });
        } else if (response instanceof HttpResponse && response.status === 200) {
          this.errorService.errorUrl = ""
        }
        return of(response)
      })
    );
  }
}
