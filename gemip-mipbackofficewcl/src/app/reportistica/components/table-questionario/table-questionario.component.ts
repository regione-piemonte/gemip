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

import { Component, Input } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { DomandeRisposte, RigheReportistica } from '../../model/reportistica-questionario';

@Component({
  selector: 'app-table-questionario',
  templateUrl: './table-questionario.component.html',
  styleUrls: ['./table-questionario.component.scss']
})
export class TableQuestionarioComponent {
  @Input() dataSurce: MatTableDataSource<RigheReportistica> = new MatTableDataSource();
  @Input() merge: Array<string> = [];
  @Input() addColumn: Array<string> = [];
  @Input() addColumnIndex: Array<string> = [];

  constructor() { }

  getRisp(item: string, testo: RigheReportistica, index: string) {
    let prova: DomandeRisposte[] | undefined = testo.compilazione;
    let risposte: Array<string> = this.makeReport(prova);
    return risposte[Number(index)];
  }

  getDom(i:string){
    return this.addColumn[Number(i)];
  }

  String(i: number): string {
    return i.toString();
  }

  makeReport(righe: DomandeRisposte[]) {
    let risposte: Array<string> = [];
    righe.forEach(riga => {
      if (riga.testoRisposta) {
        risposte.push(riga.testoRisposta);
      }
      if (riga.rispostaLibera) {
        risposte.push(riga.rispostaLibera);
      } else {
        risposte.push(" ");
      }
    });
    return risposte;
  }
}
