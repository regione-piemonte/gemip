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

import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  ViewChild,
  SimpleChanges,
} from '@angular/core';

// Angualr Animation
import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';

// Angular material
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatPaginator, PageEvent } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { SelectionModel } from '@angular/cdk/collections';

// Model Table
import {
  TableSettingsModel,
  ColumnSettingsModel,
  ButtonActionSettingModel,
} from '@shared/model/table-setting.model';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],

  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition(
        'expanded <=> collapsed',
        animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')
      ),
    ]),
  ],
})
export class TableComponent implements OnInit, OnChanges {
  /**@description Data which will be displayed in tabular format */
  @Input() rowData: object[] = []; //Rows
  @Input('tableSettings') tableSettings!: TableSettingsModel; //TableSettings
  @Input('columnList') columns: ColumnSettingsModel[] = []; //Columns

  @Input() keepPagination: boolean = false; //Keep pagination

  @Input('buttonList') buttons: ButtonActionSettingModel[] = []; //Buttons table

  @Input() isStampa: boolean = false;

  pageSize: number = 5;
  selection = new SelectionModel<{}>(true, []);

  @Output() esportaExcel: EventEmitter<void> = new EventEmitter<void>();
  @Output() pageIndexSizeChange: EventEmitter<number[]> = new EventEmitter<
    number[]
  >();
  @Output() sortChange: EventEmitter<Sort> = new EventEmitter<Sort>();
  //@Output() pageSizeChange:EventEmitter<number>=new EventEmitter<number>()
  @ViewChild('sort', { static: false }) sort!: MatSort;

  //######- Gestione Caso di tabella con select -#####
  selectedRadio: any; // Per la gestione dei radio button
  maxLength: number = 30;

  constructor() {}

  @ViewChild(MatTable) table!: MatTable<any>;

  /**@description ViewChild to get the MatPaginator directive from DOM */
  @ViewChild(MatPaginator, { static: false }) paginator!: MatPaginator;
  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource(this.rowData);

    if (this.paginator) {
      if(this.tableSettings.length){
        this.paginator.length=this.tableSettings.length
      }else{
        this.dataSource.paginator=this.paginator
      }
      if(this.tableSettings.pageIndex && this.paginator){

        this.paginator.pageIndex=this.tableSettings.pageIndex
      }
      if(this.tableSettings.pageSize && this.paginator){
        this.paginator.pageSize=this.tableSettings.pageSize
      }
    }
    this.dataSource.sort = this.sort;
    this.dataSource.sortData=this.sortData();

  }

  /**@description Column names for the table*/
  columnNames: string[] = [];
  /**@description Local variable to convert JSON data object to MatTableDataSource*/
  @Input() dataSource!: MatTableDataSource<{}>;

  expandedElement: object | null | undefined;

  disabledCheckbox: boolean = true;

  ngOnInit(): void {
    for (const column of this.columns) {
      this.columnNames.push(column.name);
    }
    if (this.isStampa) {
      // DISABILITIAMO TUTTO
      let t2: TableSettingsModel = {
        title: this.tableSettings.title,
        listBoolView: [true, true, false],
        enableExport: false,
        enableExpansion: false,
        enablePagination: false,
        pageSize: 1000,
        pageIndex: 0,
        pageSizeOptions: [],
        showFirstLastButtons: false,
        enableRadioButton: false,
        enableButtons: false,
        length: this.tableSettings.length,
        innerTableSettings: this.tableSettings.innerTableSettings,
        isRadio: false,
        defaultSortDirection:'asc'
      };
      this.tableSettings = t2;

      // NELLE COLONNE DISABILITIAMO L'ORDINAMENTO
      let c2: ColumnSettingsModel[] = this.columns.map(c => {
        let newcol: ColumnSettingsModel = Object.assign({}, c);
        newcol.disableSorting = true;
        return newcol;
      });
      this.columns = c2;
    }
  }

  sortData() {
    return (items: any[], sort: MatSort): object[] => {
      if (!sort.active || sort.direction === '') {
        return items;
      }

      return items.sort((a: any, b: any) => {
        let comparatorResult = 0;
          if (!isNaN(Number(a[String(sort.active)]))) {
            comparatorResult = (+a[String(sort.active)])>(+b[String(sort.active)])? 1 : -1;
          }else if((sort.active=="data")){
            let tmpA=a[String(sort.active)].split("/");
            let tmpdataA=new Date(tmpA[2], tmpA[1] - 1, tmpA[0]);

            let tmpB=b[String(sort.active)].split("/");
            let tmpdataB=new Date(tmpB[2], tmpB[1] - 1, tmpB[0]);


            comparatorResult=tmpdataA>tmpdataB ? 1 : -1;

          }else{
            const lowercaseA = String(a[String(sort.active)]).toLowerCase();
            const lowercaseB = String(b[String(sort.active)]).toLowerCase();
            comparatorResult = lowercaseA.localeCompare(lowercaseB, undefined, { sensitivity: 'base' });
          }


        return comparatorResult * (sort.direction == 'asc' ? 1 : -1);
      });
    };
  }

  allSelected = false;
  /** Whether the number of selected elements matches the total number of rows. */
  isAllSelected() {
    const numSelected = this.selection.selected.length;
    const numRows = this.dataSource.data.length;
    return numSelected === numRows;
  }

  toggleAllRows() {
    this.isAllSelected()
      ? this.selection.clear()
      : this.selection.select(...this.dataSource.data);
    this.allSelected = this.isAllSelected();
  }
  updateSelected() {
    this.allSelected = this.isAllSelected();
  }

  clearSelection() {
    this.selection.clear();
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['rowData']) {
      this.selectedRadio = undefined;
      this.dataSource = new MatTableDataSource(this.rowData);

      if (this.tableSettings.length && this.paginator) {
        this.paginator.length = this.tableSettings.length;
        if (this.tableSettings.pageIndex) {
          this.paginator.pageIndex = this.tableSettings.pageIndex;
        }
        if (this.tableSettings.pageSize) {
          this.paginator.pageSize = this.tableSettings.pageSize;
        }
      } else {
        this.dataSource.paginator = this.paginator;
      }
    }

  }
  updatePage(event: PageEvent) {
    this.pageIndexSizeChange.emit([event.pageIndex, event.pageSize]);
  }
  //-Sorting
  sortDataDirection(sort:Sort){

    switch (this.sort.direction) {
      case 'asc':
        this.sort.direction = 'asc';
        break;
      case 'desc':
        this.sort.direction = 'desc';
        break;
      default:
        this.sort.direction = 'asc';
        break;
    }
    this.sortChange.emit(sort);
  }
  resetOrdinamento(){
    this.sort.direction='asc';
    if(this.tableSettings.defaultSortDirection){
      this.sort.direction=this.tableSettings.defaultSortDirection
    }
    if(this.tableSettings.defaultSort){
      this.sort.active=this.tableSettings.defaultSort
    }else{
      this.sort.active=this.columnNames[0]
    }
    this.sort.sortChange.emit(this.sort);
  }

  export() {
    this.esportaExcel.emit();
  }

  //- usato sulle modali principalmente per avere i pulsanti separati
  clickButton(index: number) {
    this.buttons[index].actionClick(
      this.tableSettings.isRadio ? this.selectedRadio : this.selection.selected
    );
  }

  updateCheckMail(checked: boolean, element: any) {
    if (element.statoPartecipante.stato !== 'HA PARTECIPATO') {
      element.checkMail = false;
    } else {
      element.checkMail = checked;
    }
  }
}
