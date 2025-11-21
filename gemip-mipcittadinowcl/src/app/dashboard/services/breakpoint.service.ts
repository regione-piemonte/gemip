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
import { BreakpointObserver, BreakpointState, Breakpoints } from '@angular/cdk/layout';
import { Observable, map } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BreakpointService {
  constructor(private breakpointObserver: BreakpointObserver) { }

  observeBreakpoints(breakpoints: string[]): Observable<{ [key: string]: boolean }> {
    return this.breakpointObserver.observe(breakpoints)
      .pipe(
        map((state: BreakpointState) => {
          const result: { [key: string]: boolean } = {};
          for (const breakpoint of breakpoints) {
            result[breakpoint] = state.breakpoints[breakpoint];
          }
          return result;
        })
      );
  }
}
