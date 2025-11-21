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

import { Observable, map, of, tap } from 'rxjs';
import { Injectable } from '@angular/core';
import { ConfigUrlService } from '@core/services/config-url.service';
import { ManagedHttpClient } from '@core/services/managed-http-client.service';
import { UserInfo } from '@core/models/userInfo';
import { Ruolo } from '@core/models/ruolo';
import { environment } from '@environment/environment';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { HttpParams } from '@angular/common/http';
import { tokenSetter } from '@core/utils/token-handler';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class InfoService {

  private _userInfo?: UserInfo;
  private _ruolo?: Ruolo;

  /**
   * RUOLI SETTING
   *                                              //Lettura Modifica
   * "GEMIP_AFFIDATARIO_ALL":MenuItem.MENU_ADMIN, //LM ALL
   * "GEMIP_REGIONALE_ALL":MenuItem.MENU_ADMIN,   //LM ALL
   * "GEMIP_REGIONALE_BASE":MenuItem.MENU_ALTRO,  //L ALL M anagrafica/idee
   * "GEMIP_CPI_BASE":MenuItem.MENU_ALTRO,        //L ALL M anagrafica/idee
   * "GEMIP_ATTUATORE_BASE":MenuItem.MENU_ALTRO,  //L ALL M anagrafica/idee
   */


  constructor(
    private http:ManagedHttpClient,
    private configUrl:ConfigUrlService
  ) { }


  getUserInfo():Observable<UserInfo>{
    if(this._userInfo)
      return of(this._userInfo)
    if(sessionStorage.getItem("_userInfo") != null){
      return of( (this._userInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)) )
    }

    return this.http.get<UserInfo>(this.configUrl.userInfoUrl).pipe(
      tap(r=> {
        this._userInfo = r
        sessionStorage.setItem("_userInfo",  JSON.stringify(this._userInfo))
      })
    );
  }


  private helper = new JwtHelperService();

  getRuoli(idOperatore:number):Observable<Ruolo>{
    if(this._ruolo)
      return of(this._ruolo)
    if(sessionStorage.getItem("_ruolo") != null){
      return of( (this._ruolo = JSON.parse(sessionStorage.getItem("_ruolo")!)) )
    }

    let queryParams=new HttpParams().append("idOperatore",idOperatore)
    return this.http.get<Ruolo>(this.configUrl.ruoliUrl,{params:queryParams}).pipe(
      //map(r=> r),
     tap(r=> {
        tokenSetter(r.jwt);

        console.log(r.jwt)
        console.log(this.helper.getTokenExpirationDate(r.jwt!)!.valueOf())
        console.log(this.helper.getTokenExpirationDate(r.jwt!)!.valueOf() - new Date().valueOf())
        console.log(this.helper.isTokenExpired(r.jwt!))
      })
    );
  }

  getSoggettiAttuatoriForOperatore(idOperatore:number):Observable<SoggettoAttuatore[]>{

    let params=new HttpParams().append("idOperatore",idOperatore);
    return this.http.get<SoggettoAttuatore[]>(this.configUrl.soggettiAttuatoriForOperatorUrl,{params});
  }

  clear(){
    sessionStorage.clear();
    this._userInfo = undefined;
    this._ruolo = undefined;
  }


  refreshToken(){
    this._userInfo = JSON.parse(sessionStorage.getItem("_userInfo")!)
    let queryParams=new HttpParams().append("idOperatore",this._userInfo?.idOperatore!)

    this.http
        .get<Ruolo>(this.configUrl.ruoliUrl,{params:queryParams})
        .subscribe(r=> {
          tokenSetter(r.jwt);
        });
  }
}
