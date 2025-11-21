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

import { Component, Input, OnDestroy, OnInit, TemplateRef, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { Sort } from '@angular/material/sort';
import { CittadinoIncontroPreaccoglienza } from '@core/models/cittadinoIncontroPreaccoglienza';
import { IdeaDiImpresa } from '@core/models/ideaDiImpresa';
import { IdeaDiImpresaIncontroPreaccoglienza } from '@core/models/ideaDiImpresaIncontroPreaccoglienza';
import { ResgistraPresenze } from '@core/models/resgistraPresenze';
import { UserInfo } from '@core/models/userInfo';
import { BagService } from '@core/services/bag.service';
import { IdeaImpresaService } from '@idee-impresa/services/idea-impresa.service';
import { ExcelService } from '@pre-accoglienza/services/excel.service';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { QuestionarioService } from '@pre-accoglienza/services/questionario.service';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { COD_MAIL_30_QUESTIONARIO_1, COD_MAIL_40_SCELTA_TUTOR } from '@shared/model/mail-code-const.model';
import { TableSettingsModel, ColumnSettingsModel, ButtonActionSettingModel, InfoColumnActionSettingModel } from '@shared/model/table-setting.model';
import { IconsSettings } from '@shared/utils/icons-settings';
import * as FileSaver from 'file-saver';


@Component({
  selector: 'app-idee-di-impresa-incontro',
  templateUrl: './idee-di-impresa-incontro.component.html',
  styleUrls: ['./idee-di-impresa-incontro.component.scss']
})
export class IdeeDiImpresaIncontroComponent implements OnInit, OnDestroy {

  @Input() isStampa: boolean = false

  user?: UserInfo
  visibleTableIdeeImpresa = false
  rowDataIdeeImp: IdeaImpresaSimple[] = []
  pageIndex?: number;
  pageSize?: number;
  tableSettingsIdeeImp: TableSettingsModel = new TableSettingsModel();
  columnListIdeeImp: ColumnSettingsModel[] = [];
  buttonsTabellaIdeeImp: ButtonActionSettingModel[] = [];
  ideeImp: IdeaDiImpresa[] = []
  status: string[] = ["DISDETTO", "HA PARTECIPATO", "ISCRITTO", "NON PRESENTATO"]
  ideeAccorpate: IdeaDiImpresaIncontroPreaccoglienza[] = [];

  constructor(
    private dialog: MatDialog,
    private incontriPreAccoglienzaService: PreAccoglienzaService,
    private IncontriPreaccoglienzaBag: PreAccoglienzaBagService,
    private ideaService: IdeaImpresaService,
    private excelService: ExcelService,
    private bagService: BagService,
    private questionarioService: QuestionarioService
  ) { }

  ngOnDestroy(): void {
    this.ideaService.updatePagination(5, 0);
  }

  ngOnInit(): void {
    this.createTableIdee()
    this.ideaService._pageIndex.subscribe(
      ris => {
        this.pageIndex = ris;
      }
    )
    this.ideaService._pageSize.subscribe(
      ris => {
        this.pageSize = ris;
      }
    )
    this.getIdeeAssociate(this.pageIndex!, this.pageSize!)
    this.setListaIdeeComunicazione();
  }

  onPageIndexSizeChange(indexSize: number[]) {
    this.ideaService.updatePagination(indexSize[1], indexSize[0])
    this.getIdeeAssociate(this.pageIndex!, this.pageSize!)
  }

  setListaIdeeComunicazione() {
    if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
      this.incontriPreAccoglienzaService.getIdeeDiImpresaAssociate(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza).subscribe({
        next: ris => {
          ris.ideaDiImpresa = ris.ideaDiImpresa!.filter(ideaWarp => ideaWarp.ideaDiImpresa?.statoIdeaDiImpresa?.id != 1 && ideaWarp.ideaDiImpresa?.statoIdeaDiImpresa?.id != 2)
          let ideeNonAccorpate = ris.ideaDiImpresa.filter(idea => !idea.ideaDiImpresa?.idIdeaDiImpresaSostituente)
          this.IncontriPreaccoglienzaBag.setIdeeImpresaIncontro(ideeNonAccorpate.map(idea => idea.cittadinoIncontro!));
        }
      })
    }
  }

  getIdeeAssociate(pageIndex: number, pageSize: number) {
    if (this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza) {
      this.incontriPreAccoglienzaService.getIdeeDiImpresaAssociate(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza, pageIndex, pageSize).subscribe({
        next: ris => {
          //this.ideeImp=ris.map(ideaWrap=>ideaWrap.ideaDiImpresa!).filter(idea=>{idea.statoIdeaDiImpresa?.id!=1 && idea.statoIdeaDiImpresa?.id!=2 && idea.statoIdeaDiImpresa?.id!=4})
          ris.ideaDiImpresa = ris.ideaDiImpresa!.filter(ideaWarp => ideaWarp.ideaDiImpresa?.statoIdeaDiImpresa?.id != 1 && ideaWarp.ideaDiImpresa?.statoIdeaDiImpresa?.id != 2)
          let ideeNonAccorpate = ris.ideaDiImpresa.filter(idea => !idea.ideaDiImpresa?.idIdeaDiImpresaSostituente)
          //let ideeAccorpate=ris.ideaDiImpresa.filter(idea=>idea.ideaDiImpresa?.idIdeaDiImpresaSostituente)
          this.ideeImp = ideeNonAccorpate.map(idea => idea.ideaDiImpresa!)
          this.rowDataIdeeImp = ideeNonAccorpate.map(
            ideaWrap => {
              return {
                idIdea: ideaWrap.ideaDiImpresa!.id!,
                idCIttadino: ideaWrap.cittadinoIncontro?.cittadino?.idCittadino!,
                titolo: ideaWrap.ideaDiImpresa!.titolo!,
                nominativoCittadino: ideaWrap.cittadinoIncontro?.cittadino!.cognome! + " " + ideaWrap.cittadinoIncontro?.cittadino!.nome!,
                areaSelezionata: ideaWrap.cittadinoIncontro?.codiceAreaTerritorialeSelezionata!.descrizioneAreaTerritoriale!,
                idStatoIdea: ideaWrap.ideaDiImpresa?.statoIdeaDiImpresa?.id!,
                checkMail: false,
                statoPartecipante: { stato: getStato(ideaWrap.cittadinoIncontro)!, list: this.status, disabled: !!ideaWrap.ideaDiImpresa?.idTutor },
                mergedWith: [],
              }
            }
          )
          this.tableSettingsIdeeImp.length = ris.risultatiTotali!
          this.tableSettingsIdeeImp.pageIndex = this.pageIndex!;
          this.tableSettingsIdeeImp.pageSize = this.pageSize!;
          this.visibleTableIdeeImpresa = true;
        }
      })
    }
  }

  createTableIdee() {
    this.tableSettingsIdeeImp.enableExpansion = true;
    this.tableSettingsIdeeImp.enableExport = false;
    this.tableSettingsIdeeImp.enableButtons = true;

    this.tableSettingsIdeeImp.title = "Idee di impresa iscritte";
    this.createColumnsTableIdeeImp();

    if (this.canModify)
      this.createButtonTable();
  }

  createColumnsTableIdeeImp() {
    /**Columns */
    const titoloColumn = new ColumnSettingsModel('titolo', 'Titolo', false, 'simple');
    const areaSelezionataColumn = new ColumnSettingsModel('areaSelezionata', 'Area selezionata', false, 'simple');
    const nominativoCittadinoColumn = new ColumnSettingsModel('nominativoCittadino', 'Nominativo', false, 'simple');
    const statoColumn = new ColumnSettingsModel('statoPartecipante', 'Status presenza incontro', true, 'select');
    const checkPresenza = new ColumnSettingsModel('checkQuestionario', 'Questionario', false, 'checkbox');
    const checkPresenza1 = new ColumnSettingsModel('checkTutor', 'Scelta Tutor', false, 'checkbox1');


    this.columnListIdeeImp.push(...[titoloColumn, nominativoCittadinoColumn, areaSelezionataColumn]);

    if (this.canModify)
      this.columnListIdeeImp.push(statoColumn, checkPresenza, checkPresenza1);

  }

  presenzeConfermate = false;

  createButtonTable() {
    let button = new ButtonActionSettingModel(
      'REINVIO COMUNICAZIONI',
      (data) => {
        if (this.rowDataIdeeImp.length > 0) {
          let sendMailTo: number[] = [];
          let sendMailTo1: number[] = [];

          this.rowDataIdeeImp.forEach(r => {
            if (r.statoPartecipante?.stato == "HA PARTECIPATO" && r.checkMail == true) {
              sendMailTo.push(r.idCIttadino);
            }
          })
          this.rowDataIdeeImp.forEach(r => {
            if (r.checkMail1) {
              sendMailTo1.push(r.idCIttadino);
            }
          })

          if (sendMailTo.length > 0) {
            this.questionarioService.postEmail(sendMailTo, COD_MAIL_30_QUESTIONARIO_1).subscribe({
              next: () => this.dialog.open(DialogConfermaComponent, {
                data: new DialogSettings(
                  "Avviso!",
                  ['Email di richiesta compilazione questionario inviata correttamente!'],
                  "card-body--success"
                ),
                disableClose: true
              }),
            });
          }
          if (sendMailTo1.length > 0) {
            this.questionarioService.postEmail(sendMailTo1, COD_MAIL_40_SCELTA_TUTOR).subscribe({
              next: () => this.dialog.open(DialogConfermaComponent, {
                data: new DialogSettings(
                  "Avviso!",
                  ['Email di scelta del tutor inviata correttamente!'],
                  "card-body--success"
                ),
                disableClose: true
              }),
            });
          }
          if (sendMailTo.length == 0 && sendMailTo1.length == 0) {
            this.dialog.open(DialogConfermaComponent, {
              data: new DialogSettings(
                "Attenzione!",
                ['Seleziona almeno un cittadino!'],
                "card-body--danger"
              ),
              disableClose: true
            });
          }
        }
        else {
          this.dialog.open(DialogConfermaComponent, {
            data: new DialogSettings(
              "Attenzione",
              ['Non sono presenti iscritti'],
              "card-body--warning"
            ),
            disableClose: true
          });
        }
      },
      'btn btn--primary'
    );
    let button_conferma = new ButtonActionSettingModel(
      'REGISTRA PRESENZE',
      (data) => {
        let registro: ResgistraPresenze[] = [];
        if (this.rowDataIdeeImp.length > 0) {

          this.rowDataIdeeImp.forEach(r => {
            if (!r.statoPartecipante?.disabled) {
              let tmp: ResgistraPresenze = {
                idCittadino: r.idCIttadino,
                idIncotroPreaccoglienza: this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza,
                stato: r.statoPartecipante?.stato
              };
              registro.push(tmp);
            }
          }
          )
        } else {
          this.dialog.open(DialogConfermaComponent, {
            data: new DialogSettings(
              "Attenzione",
              ['Non sono presenti iscritti'],
              "card-body--warning"
            ),
            disableClose: true
          });
          return;
        }
        this.incontriPreAccoglienzaService.registraPresenze(registro).subscribe({
          next: () => {
            this.getIdeeAssociate(this.pageIndex!, this.pageSize!)
            this.dialog.open(DialogConfermaComponent, {
              data: new DialogSettings(
                "Avviso !",
                ['Presenze salvate correttamene!'],
                "card-body--success"
              ),
              disableClose: true
            });
          }
        })
      },
      'btn btn--primary'
    );
    if (!this.isStampa)
      this.buttonsTabellaIdeeImp.push(...[button_conferma, button]);
  }

  get canModify() {
    return this.incontriPreAccoglienzaService.canModify && !this.isStampa;
  }



  @ViewChild('utentiIdee') utentiIdee!: TemplateRef<any>;
  ideeImpToMerge: IdeaImpresaSimple[] = [];
  toMergeWith: any;
  createIconsIdeeImp(iAccorpate: number): InfoColumnActionSettingModel[] {

    let icons: InfoColumnActionSettingModel[] = [];
    if (this.canModify) {
      icons.push(new InfoColumnActionSettingModel(
        IconsSettings.PEOPLE_OUTLINE,
        'icon',
        'raggruppa',
        (row) => {
          this.ideeImpToMerge = []
          this.toMergeWith = row;
          this.rowDataIdeeImp.forEach(r => {
            if (r.idIdea != this.toMergeWith.idIdea
              && r.mergedWith?.length == 0
              && r.idStatoIdea != 6
              && r.idStatoIdea != 5
              && r.idStatoIdea != 8
              && r.idStatoIdea != 10)
              this.ideeImpToMerge.push(r);
          });
          if (row.idStatoIdea == 7 || row.idStatoIdea == 12 || row.idStatoIdea == 8) {
            if (this.ideeImpToMerge.length > 0) {
              this.dialog.open(this.utentiIdee, {
                maxHeight: "800px",
                width: "540px",
                panelClass: "ClasseCss",
                disableClose: true
              });
            } else {
              this.openDialog("Attenzione", ["non ci sono idee di impresa raggruppabili"], "card-body--warning")
            }
          } else {
            this.openDialog("Attenzione", ["il cittadino deve aver partecipato all'incontro per potersi raggruppare con altre idee"], "card-body--warning")
          }
        }
      ));
    }

    if (iAccorpate > 0) {
      icons.push(new InfoColumnActionSettingModel(
        'expand',
        'icon',
        '',
        (row) => {
          row.extendedContent = [];
          row.mergedWith.forEach((e: any) => {
            row.extendedContent.push(e.titolo + " " + e.nominativoCittadino);
          })
        }
      )
      );
    }

    return icons;
  }


  ragruppaPersone() {
    this.bagService.resetError();
    let peopleToMerge = this.rowDataIdeeImp.filter((element) => element.merge);
    this.rowDataIdeeImp = this.rowDataIdeeImp.filter((element) => !element.merge);
    this.toMergeWith['mergedWith'] = peopleToMerge;
    peopleToMerge.forEach(idea => this.ideaService.putIdeaImpresa(this.createIdeaUpdate(idea.idIdea))
      .subscribe(
        {
          next: ris => {
            this.rowDataIdeeImp = this.rowDataIdeeImp.map(ideaSimple => {
              return {
                ...ideaSimple,
                mergedWith: peopleToMerge,
                icons: this.createIconsIdeeImp(peopleToMerge.length)
              }
            })
          }
        }
      ))

  }

  onClickRaggruppa() {
    this.bagService.resetError();
    let buttons: DialogButton[] =
      [
        new DialogButton(
          "Annulla",
          "btn btn--outline-primary", undefined, "annulla"),
        new DialogButton(
          "Conferma",
          "btn btn--danger",
          () => { this.ragruppaPersone() },
          "conferma ")
      ]

    this.openDialog("Attenzione", ["Si è sicuri di voler accorpare queste idee di impresa?", " L'azione non è reversibile"], "card-body--danger", buttons);

  }

  createIdeaUpdate(id: number): IdeaDiImpresa {
    let idea = this.ideeImp.filter(idea => idea.id == id)[0];

    return {
      ...idea,
      idIdeaDiImpresaSostituente: this.toMergeWith.idIdea,
      statoIdeaDiImpresa: {
        id: 4,
        descrizioneStatoIdeaDiImpresa: "Accorpata"
      },
      codUserAggiorn: this.bagService.userInfo.codFisc,
      dataCambioStato: new Date()
    }
  }

  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent, {
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose: true
    })
  }

  exportExcel() {
    this.user = JSON.parse(sessionStorage.getItem("_userInfo")!)
    this.excelService.getExcel(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza!, this.user?.codFisc!).subscribe({
      next: ris => {
        FileSaver.saveAs(ris, "utent_preaccoglienza" + new Date().toLocaleDateString([], { day: '2-digit', month: '2-digit', year: 'numeric' }) + "mip_report.xlsx",);
      }
    })
  }

  exportPdf() {
    this.incontriPreAccoglienzaService.exportPdfPresenze(this.IncontriPreaccoglienzaBag.idIncontroPreaccoglienza!).subscribe({
      next: ris => {
        FileSaver.saveAs(ris, "foglio_presenze" + new Date().toLocaleDateString([], { day: '2-digit', month: '2-digit', year: 'numeric' }) + ".pdf",);
      }
    })
  }

}

export interface IdeaImpresaSimple {
  idIdea: number,
  idCIttadino: number,
  titolo: string,
  nominativoCittadino: string,
  areaSelezionata: string,
  merge?: boolean,
  mergedWith?: any[],
  idStatoIdea: number,
  statoPartecipante?: { list: string[], stato: string, disabled: boolean },
  checkMail?: boolean,
  checkMail1?: boolean,
  icons?: InfoColumnActionSettingModel[];
}
function getStato(cittadino: CittadinoIncontroPreaccoglienza | undefined): string | undefined {
  if (cittadino) {
    if (cittadino.dAnnullamento != undefined && cittadino.dAnnullamento != null) return 'DISDETTO';
    if (cittadino.flgCittadinoPresente == 'S') return 'HA PARTECIPATO';
    if (cittadino.flgCittadinoPresente == 'N') return 'NON PRESENTATO';
    return 'ISCRITTO';
  }
  return undefined;
}

