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

import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CalendarioIcsService } from '../../services/calendario-ics.service';
import { TableSettingsModel, ColumnSettingsModel, InfoColumnActionSettingModel } from '@shared/model/table-setting.model';
import { StoricoCalendario } from '@core/models/storicoCalendario';
import { FileIcs } from '@core/models/fileIcs';
import { UserInfo } from '@core/models/userInfo';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import * as FileSaver from 'file-saver';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { Ruolo } from '@core/models/ruolo';
import { BagService } from '@core/services/bag.service';
import { IconsSettings } from '@shared/utils/icons-settings';
import { Router } from '@angular/router';
import { IcsBagService } from '../../services/calendario-ics-bag.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-calendario-storico-caricamenti',
  templateUrl: './calendario-storico-caricamenti.component.html',
  styleUrls: ['./calendario-storico-caricamenti.component.scss']
})
export class CalendarioStoricoCaricamentiComponent implements OnInit {
  selectedTab = 0;
  visibleTable = false;
  file?:File;
  elencoDocumentiIcs: StoricoCalendario[]=[];
  soggettoAttuatore?: SoggettoAttuatore;
  user!:UserInfo;
  ruolo!:Ruolo
  rowData: RowStorico[] = [];
  tableSettings: TableSettingsModel = new TableSettingsModel();
  columnList: ColumnSettingsModel[] = [];
  constructor(
    private dialog: MatDialog,
    private icsService:CalendarioIcsService,
    private icsBagService:IcsBagService,
    private bagService:BagService,
    private router:Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.bagService.titolo = "Agenda appuntamenti"
    this.bagService.icona = IconsSettings.CALENDAR_ICON

    this.ruolo=JSON.parse(sessionStorage.getItem("_ruolo")!);
    this.user=JSON.parse(sessionStorage.getItem("_userInfo")!);
    this.createTable()
    this.caricaStorico()
  }

  caricaStorico(){
    if(this.isSoggettoAttuatore){
      this.soggettoAttuatore=JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
      this.icsService.getStoricoCaricamenti(this.soggettoAttuatore?.id).subscribe({
        next:ris => {
          this.elencoDocumentiIcs=ris
          this.rowData= this.elencoDocumentiIcs.map(fileIcs=>{
            return {
              idFile:fileIcs.idDocumentoIcs!,
              dCreazione: new Date(fileIcs.dataInserim!).toLocaleDateString([], {day: '2-digit', month:'2-digit', year:'numeric'}),
              nEventi: fileIcs.numeroEventi!,
              descrizione: fileIcs.descrizioneFile!,
              operatoreIns: fileIcs.operatoreInserimento?.cognome! + " " + fileIcs.operatoreInserimento?.nome!,
              soggettoAttuatore: fileIcs.soggettoAttuatore?.denominazione!,
              icons:this.createIcons()
            }
          })
          this.visibleTable=true
        }
      })
    }
  }

  createTable() {
    this.tableSettings.title = 'Storico inserimenti';
    this.tableSettings.enableExpansion = false;
    this.tableSettings.enableButtons = false;
    this.tableSettings.enableExport = false;

    this.createColumnsTable();
  }

  createColumnsTable() {
    /**Columns */

    const dataCreazioneColumn= new ColumnSettingsModel('dCreazione','Data Creazione',false,'simple');
    const nEventiColumn=new ColumnSettingsModel('nEventi','Numero Eventi',false,'simple');
    const descrColumn = new ColumnSettingsModel('descrizione', 'Descrizione', false, 'simple');
    const soggettoAttuatoreColumn=new ColumnSettingsModel('soggettoAttuatore', 'Soggetto Attuatore', false, 'simple');
    const operatoreColmune = new ColumnSettingsModel('operatoreIns', 'Operatore', false, 'simple');
    const customActionColumn = new ColumnSettingsModel( 'icons', '', true, 'customAction');

    this.columnList.push(
      ...[
        dataCreazioneColumn,
        nEventiColumn,
        descrColumn,
        soggettoAttuatoreColumn,
        operatoreColmune,
        customActionColumn
      ]
    );
  }

  createIcons(): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];

    icons.push(InfoColumnActionSettingModel.getFileDownloadIcon((row)=>this.downloadFile(row)));
    if(this.canLoad)
    {icons.push(InfoColumnActionSettingModel.getEditIcon((row)=>{
      this.icsBagService._idFileIcs=row.idFile
      this.router.navigateByUrl("/calendario/modifica-ics")
    }))}
    return icons;
  }

  downloadFile(row:RowStorico){

    this.icsService.downloadFileICs(row.idFile).subscribe({
      next: ris=>{
        FileSaver.saveAs(ris,row.descrizione);
      },
      error(err) {
          console.log(err)
      },
    })
  }

  addfile(event:any){

    this.file=event.target.files[0];
    if(this.file?.type!="text/calendar"){
      this.openDialog("Errore",["formato del file non corretto"],"card-body--danger");
    }
  }

  saveFile(){
    this.bagService.resetError();
    if(this.file)
   { this.icsService.uploadFileIcs(this.createFileIcsObject()).subscribe({
      next:()=>{
        this.openDialog("",["caricamento avvenuto con successo"],"card-body--success")
        this.caricaStorico()
      },
      error:err=>console.log(err)
    })}else{
      this.openDialog("Errore",["selezionare un file"],"card-body--danger");
    }
  }

  createFileIcsObject():FileIcs{

    let user:UserInfo=JSON.parse(sessionStorage.getItem("_userInfo")!)
    let soggettoAttuatore:SoggettoAttuatore=JSON.parse(sessionStorage.getItem("soggettoAttuatore")!)
    if(!soggettoAttuatore){
      this.openDialog("Attenzione",["solo i soggetti attuatori possono caricare dei file ics"],"card-body--danger")
    }
    return {
      fileIcs: this.file,
      nomeFile: this.file?.name,
      descrizioneFile:"prova",
      codUserInserim: user.codFisc,
      idOperatore:user.idOperatore,
      idSoggettoAttuatore:soggettoAttuatore ? soggettoAttuatore.id:undefined
    }
  }

  openDialog(title:string, msg:string[],cssClass:string,buttons?:DialogButton[]){
    this.dialog.open(DialogConfermaComponent,{
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose:true
    })
  }

  get isSoggettoAttuatore(){
    return !!sessionStorage.getItem("soggettoAttuatore")
  }

  get canLoad(){
    return this.ruolo.codiceRuolo!="GEMIP_REGIONALE_BASE"
  }

  tornaIndietro(): void{
    this.location.back();
  }
}

export interface RowStorico{
  idFile: number,
  nEventi: number,
  dCreazione:string,
  descrizione?: string,
  soggettoAttuatore: string,
  operatoreIns: string,
  icons: InfoColumnActionSettingModel[]
}
