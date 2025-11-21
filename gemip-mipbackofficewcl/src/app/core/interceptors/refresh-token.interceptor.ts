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

import { InfoService } from '@main/services/info.service';
import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

import { JwtHelperService } from '@auth0/angular-jwt';

import { tokenGetter } from '@core/utils/token-handler';
import { environment } from '@environment/environment';

@Injectable()
export class RefreshTokenInterceptor implements HttpInterceptor {

  private helper = new JwtHelperService();


  constructor(private router: Router,
              // private authService: AuthService
              private infoService: InfoService
              )
  {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    const token = tokenGetter();

    if (req.url.indexOf('ruoli') == -1 && token) {
      const expiryTime = this.helper.getTokenExpirationDate(token)!.valueOf() - new Date().valueOf();

      if (this.helper.isTokenExpired(token)) {
        sessionStorage.clear()
        window.location.href = environment.logoutUrl;
      } else if (expiryTime <= 900000) { //900000 15 minuti 900s
        this.infoService.refreshToken()
      }

    }

    return next.handle(req);
  }

}
