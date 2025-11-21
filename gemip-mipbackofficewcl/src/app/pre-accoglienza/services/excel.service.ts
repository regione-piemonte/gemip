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

import { HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ExcelService {

  constructor(
    private http: ManagedHttpClient,
    private configUrl: ConfigUrlService
  ) { }

  getExcel(idIncontro: number, IdOperatore: string, idSoggettoAttuatore?: string): Observable<Blob> {
    let queryParams = new HttpParams();
    queryParams = queryParams.append("idIncontroPreaccoglienza", idIncontro);
    queryParams = queryParams.append("codOperatore", IdOperatore);
    if (idSoggettoAttuatore)
      queryParams = queryParams.append("idSoggettoAttuatore", idSoggettoAttuatore);
    return this.http.getResponseType<Blob>(this.configUrl.excelCittadiniIncontroUrl, queryParams, 'blob')
  }
}
