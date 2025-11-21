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
import { Documento } from '@core/models/documento';
import { TipoDocumento } from '@core/models/tipoDocumento';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { FiltroRicercaDocumento } from '@shared/model/filtri-ricerca.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DocumentiService {

  constructor(
    private configUrl: ConfigUrlService,
    private http: ManagedHttpClient
  ) { }

  getTipologieDocumenti(): Observable<TipoDocumento[]> {
    return this.http.get<TipoDocumento[]>(this.configUrl.tipoDocumentiUrl)
  }

  uplooadFile(documento: Documento): Observable<Documento> {
    let formData: FormData = new FormData();
    formData.append("descrizioneDocumento", documento.descrizioneDocumento!);
    formData.append("documento", documento.documento!);
    formData.append("codUserInserim", documento.codUserInserim!);
    formData.append("codeTipoDocumento", JSON.stringify(documento.codeTipoDocumento!));
    formData.append("idOperatoreInserimento", documento.idOperatoreInserimento!.toString());
    formData.append("nomeDocumento", documento.nomeDocumento!);
    if (documento.idIdeaDiImpresa) {
      formData.append("idIdeaDiImpresa", documento.idIdeaDiImpresa.toString());
    }
    return this.http.post<Documento>(this.configUrl.caricaDocumentoUrl, formData);
  }

  getDocumenti(filtriRicerca: FiltroRicercaDocumento): Observable<Documento[]> {
    let params = new HttpParams()
    if (filtriRicerca.idIdeaDiImpresa) {
      params = params.append("idIdeaDiImpresa", filtriRicerca.idIdeaDiImpresa);
    }
    if (filtriRicerca.codiceTipoDocumento) {
      params = params.append("tipoDocumento", filtriRicerca.codiceTipoDocumento);
    }
    if (filtriRicerca.titolo) {
      params = params.append("titolo", filtriRicerca.titolo);
    }
    return this.http.get<Documento[]>(this.configUrl.documentiUrl, { params });
  }

  downloadDocumento(idDocumento: number) {
    let params = new HttpParams().append("idDocumento", idDocumento);
    return this.http.getResponseType<Blob>(this.configUrl.documentoUrl, params, 'blob');
  }

  deleteDocumento(idDocumento: number) {
    let params = new HttpParams().append("idDocumento", idDocumento);
    return this.http.delete(this.configUrl.documentoUrl, undefined, params);
  }
}
