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

import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { FiltroRicercaAnagraficheCittadini } from '@shared/model/filtri-ricerca.model';
import { ReportisticaIdeeImpresaService } from '../../service/reportistica-idee-impresa.service';
import { MatTableDataSource } from '@angular/material/table';
import * as FileSaver from 'file-saver';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { Location } from '@angular/common';
import { MatPaginator } from '@angular/material/paginator';
import { TableSettingsReportisticaModel } from '../../model/shered-models';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogSettings } from '@shared/model/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-reportistica-anagrafica-partecipanti',
  templateUrl: './reportistica-anagrafica-partecipanti.component.html',
  styleUrls: ['./reportistica-anagrafica-partecipanti.component.scss']
})
export class ReportisticaAnagraficaPartecipantiComponent implements OnInit,AfterViewInit{

  dataSource : MatTableDataSource<Elem> = new MatTableDataSource();
  rowData : Elem[] | undefined = [];
  columsName : string[] = ['id','descrizione','data','oraInizio','sede','opp'];
  filtri:FiltroRicercaAnagraficheCittadini={};
  selectAll: boolean = false;
  listsalva: Map<number,Elem[]> = new Map<number,Elem[]>([]);
  isButtonDisabled: boolean = false;
  tableSettings:TableSettingsReportisticaModel={
    length: 1000,
    pageSize: 15,
    pageIndex: 0,
    pageSizeOptions: [15, 25, 50],
    showFirstLastButtons: true,
  }
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  visibleTable:boolean=false;
  constructor(
    private bagService:BagService,
    private dialog:MatDialog,
    private reportisticaService: ReportisticaIdeeImpresaService,
    private location: Location
  ) { }
  ngAfterViewInit(): void {
    this.dataSource.paginator=this.paginator;
  }

  ngOnInit(): void {
    this.bagService.titolo = "Reportistica anagrafiche partecipanti"
    this.bagService.icona = IconsSettings.REPORTISTICA_ICON
  }

  onSearch(filtri:FiltroRicercaAnagraficheCittadini,event?:any){
    if(!event || (event && event.pageSize !== this.tableSettings.pageSize)){
      this.listsalva.clear();
      this.selectAll = false;
      this.tableSettings.pageIndex=0;
    }
    if(event){
      this.tableSettings.pageIndex = event.pageIndex;
      this.tableSettings.pageSize = event.pageSize
    }
    this.filtri = filtri;
    this.reportisticaService.ricercaIncontri(filtri,this.tableSettings.pageIndex,this.tableSettings.pageSize).subscribe({
      next: ris => {
        this.tableSettings.length=ris.totaleIncontri!;
        this.visibleTable= true;
        this.rowData=ris.incontriPreaccoglienza?.map(e =>
          {
            return {
              id: e.id!,
              descrizione: e.denominazione!,
              data:new Date(e.dataIncontro!).toLocaleString('it-IT', {day: '2-digit', month:'2-digit', year:'numeric'}),
              oraInizio:new Date(e.dataIncontro!).toLocaleString('it-IT', {hour: '2-digit', minute:'2-digit'}),
              sede:e.luogoIncontro ? e.luogoIncontro.denominazione! : "Da remoto",
              opp:this.calcolaOpp(e)
            }
          }
        )
        this.dataSource.data = this.rowData!;
        this.listsalva.set(
          this.tableSettings.pageIndex,
          this.dataSource.data
        )
      }
    })

  }

  calcolaOpp(element: any){
    if(this.listsalva.has(this.tableSettings.pageIndex)){//se la ricerca della pagina è già stata fatta
      let elementoTorvato=this.listsalva.get(this.tableSettings.pageIndex)?.find(e=>e.id==element.id)
      return elementoTorvato ? elementoTorvato.opp: false;
    }
    if(this.selectAll){//se siamo in select all
      return true
    }
    return false;
  }

  async onDownload() {
    if(!Array.from(this.listsalva.values()).some(value => value.some(e => e.opp))){// questo controllo non bisogna più farlo nel data ma nel salva lista
      this.dialog.open(DialogConfermaComponent,{
        data: new DialogSettings("Avviso!", ["Seleziona uno o più incontri"], "card-body--danger", "",undefined ),
        disableClose: true
      })
      return;
    }
    let stampaElem : string[] = [] ;
    if(this.selectAll){// bisogna prenderle tutte e filtrare su quelle che ha deselezionato
      this.reportisticaService.ricercaIncontri(this.filtri).subscribe({
        next: ris => {
          let elemDaTogliere: any[] = [];
          this.listsalva.forEach((value: any[]) => {
            value.filter(e => !e.opp).forEach(e => elemDaTogliere.push(e.id));
          });
          stampaElem = ris.incontriPreaccoglienza!
            .filter(e => !elemDaTogliere.includes(e.id))
            .map(e => String(e.id!));
          this.onDownloadExcel(stampaElem);
        }
      })
    }else{ // ho tutti gli elementi in lista salva da exportare
      this.listsalva.forEach((value: any[]) => {
        value.filter(e => e.opp).forEach(e => stampaElem.push(e.id));
      });
      this.onDownloadExcel(stampaElem);
    }
  }

  private onDownloadExcel(stampaElem: string[]) {
    const user = JSON.parse(sessionStorage.getItem("_userInfo")!);
    let nomeOperatore = user?.nome + " - " + user?.cognome;
    let soggettoAttuatore: SoggettoAttuatore;
    if (sessionStorage.getItem("soggettoAttuatore")) {
      soggettoAttuatore = JSON.parse(sessionStorage.getItem("soggettoAttuatore")!);
    }
    this.reportisticaService.getExportIncontriPartecipantiAnagrafica(stampaElem, nomeOperatore, sessionStorage.getItem("soggettoAttuatore") ? soggettoAttuatore!.id : undefined).subscribe({
      next: data => {
        FileSaver.saveAs(data, "anagrafiche_partecipanti" + new Date().toLocaleDateString([], { year: 'numeric', month: '2-digit', day: '2-digit' }) + "_mip_report.xlsx");
      }
    });
  }

  onSelectAll(val: boolean){
    //console.log("select ALL : ",this.listsalva)
    this.listsalva.forEach(elem=> elem.forEach(e=>e.opp=val))
    return this.dataSource.data.forEach(e => e.opp=val);
  }


  tornaIndietro(): void{
    this.location.back();
  }
}
export interface Elem{
  id: number,
  descrizione:string,
  data:string,
  oraInizio:string,
  sede:string,
  opp: boolean,
}

