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

// This interface contains properties of table pagination settings
/**
 * @description SqTablePaginationSettingsModel is a custom type which
 *    is used in `app-table` for pagination properties
 */

import { SortDirection } from "@angular/material/sort";
import { IconsSettings } from "@shared/utils/icons-settings";

export class TableSettingsModel {

  constructor(
    /**@description table title*/
    public title: null |string = null,

    /**@description show header table [title, length, resetOrder]  */
    public listBoolView: boolean[] = [true, true, true],

    /**@description enable Expansion of rows*/
    public enableExport: boolean = true,

    /**@description enable Expansion of rows*/
    public enableExpansion: boolean = false,

    /**@description enable Pagination of rows*/
    public enablePagination: boolean = true,

    /**@description Number of items to display on a page. By default, set to 5.*/
    public pageSize: number = 5,

    /**@description Index of pages.*/
    public pageIndex: number = 0,

    /**@description the set of provided page size options to display to the user.*/
    public pageSizeOptions: number[] = [5, 10, 15],

    /** @description Whether to show the first/last buttons UI to the user.*/
    public showFirstLastButtons: boolean = true,

    /** @description enable radio buttons for rows.*/
    public enableRadioButton: boolean = false,

    /** @description enable buttons  to the buttom table.*/
    public enableButtons: boolean = false,

    public length: number = 0,

    public innerTableSettings: Table|null=null,

    /** @description per identificare se bisogna restituire un dato selezionato da radio/select button */
    public isRadio:boolean  = true,
    /**@description se l'ordinamento di default non è sulla prima colonna */
    public defaultSort?:string,
    /**@description se l'ordinamento di default non è sulla prima colonna */
    public defaultSortDirection:SortDirection = 'asc',
    ) {}
}
/** @description table wrapper */
export class Table{
  constructor(
    public rowData: object[],
    public tableSettings: TableSettingsModel,
    public columns: ColumnSettingsModel[],
    public buttons: ButtonActionSettingModel[]=[]
  ){}
}
// This interface contains properties of table column settings
/**
* @description ColumnSettingsModel is a custom type which is used in `-table` for column properties
*/
export class ColumnSettingsModel {

  constructor(
    /**@description The column name to be used for fetching/binding data */
    public name:string='',

    /**@description The column name to be displayed*/
    public displayName: string = '',

    /**@description Property for disabling sorting*/
    public disableSorting: boolean = true,

    /**@description Type of column*/
    public type: string = 'string',

    /**@description Icon for the column header row*/
    public icon?:string,

    /**@description Field sorting*/
    public sortBy?: string,

    /**@description */
    public nestedProperties?: string[],

    /**@description Format date */
    public formatDate: string = 'dd/MM/yyyy',

    /**@description list of columns action */
    public iconAction: InfoColumnActionSettingModel[] = [],

    public toggle?: {checked: boolean; label:string},

    ) {}
}

// This interface contains properties of table icon settings
/**
* @description InfoColumnActionSettingModel is a custom type which for icon properties
*/
export class InfoColumnActionSettingModel {

  constructor(
    /**@description name of Angular Material Icon (<mat-icon>)*/
    public matIcon: string = '',

    /**@description name of CSS class selector*/
    public cssClass?: string,

    /**@description matTooltip*/
    public matTooltip: string = '',

    /**@description function of event click*/
    public actionClick?: (row:any) => void,


    /**@description function of event click*/
    public always: boolean = true,

    /**@description function of event click*/
    public viewCondition?: (row:any) => boolean,



    /**@description object of CSS Properties*/
    public style?: object,
    public ariaLabel?: string
  ){}

  //############# STANDARD Icons #################
  static getEditIcon(action:(row:any) => void) {
    return new InfoColumnActionSettingModel(
      IconsSettings.PEN_ICON,
      'icon',
      'modifica',
      action,undefined,undefined,undefined,"modifica"
    );
  }
  static getDeleteIcon(action:(row:any) => void) {
    return new InfoColumnActionSettingModel(
      IconsSettings.TRS_ICON,
      'icon',
      'Elimina',
      action,undefined,undefined,undefined,"elimina"
    );
  }
  static getViewIcon(action:(row:any) => void) {
    return new InfoColumnActionSettingModel(
      IconsSettings.EYE_ICON,
      'icon',
      'visualizza',
      action,undefined,undefined,undefined,"visualizza"
    );
  }
  static getFileDownloadIcon(action:(row:any) => void) {
    return new InfoColumnActionSettingModel(
      IconsSettings.FILE_DOWNLOAD_ICON,
      'icon',
      'scarica',
      action,undefined,undefined,undefined,"scarica file"
    );
  }
}

export class ButtonActionSettingModel{
ariaLabel: any;
 constructor(
  public text: string = '',
  public actionClick:(data?:any) => void,
  public cssClass?:string,
  public matTooltip?:string,
  // public aria-label?:string
 ){}
}
