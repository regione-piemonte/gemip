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
import { FiltroRicercaPreAccoglienza } from '@shared/model/filtri-ricerca.model';
import { EventColor } from 'calendar-utils';
import { CalendarEvent, CalendarView, CalendarMonthViewDay, CalendarEventTimesChangedEvent } from 'angular-calendar';
import { subMonths, addMonths, addHours, startOfDay, isSameMonth, isSameDay, addDays, addWeeks, endOfDay, endOfMonth, endOfWeek, startOfMonth, startOfWeek, subDays, subWeeks } from 'date-fns';
import { Subject } from 'rxjs';
import { DialogConfermaComponent } from '@shared/components/dialog-conferma/dialog-conferma.component';
import { DialogButton, DialogSettings } from '@shared/model/dialog-settings.model';
import { MatDialog } from '@angular/material/dialog';
import { CalendarioIcsService } from '../../services/calendario-ics.service';
import { UserInfo } from '@core/models/userInfo';
import { SoggettoAttuatore } from '@core/models/soggettoAttuatore';
import { IcsBagService } from '../../services/calendario-ics-bag.service';


const colors: Record<string, EventColor> = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};

type CalendarPeriod = 'day' | 'week' | 'month';

function addPeriod(period: CalendarPeriod, date: Date, amount: number): Date {
  return {
    day: addDays,
    week: addWeeks,
    month: addMonths,
  }[period](date, amount);
}

function subPeriod(period: CalendarPeriod, date: Date, amount: number): Date {
  return {
    day: subDays,
    week: subWeeks,
    month: subMonths,
  }[period](date, amount);
}

function startOfPeriod(period: CalendarPeriod, date: Date): Date {
  return {
    day: startOfDay,
    week: startOfWeek,
    month: startOfMonth,
  }[period](date);
}

function endOfPeriod(period: CalendarPeriod, date: Date): Date {
  return {
    day: endOfDay,
    week: endOfWeek,
    month: endOfMonth,
  }[period](date);
}
@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrls: ['./calendario.component.scss']
})


export class CalendarioComponent implements OnInit {
  file?:File;
  filtroCalendario:FiltroRicercaPreAccoglienza={}
  events: CalendarEvent[]=[];
  user!:UserInfo
  soggettoAttuatore?: SoggettoAttuatore
  minDate: Date = subMonths(new Date(), 1);
  maxDate: Date = addMonths(new Date(), 1);

  prevBtnDisabled: boolean = false;

  nextBtnDisabled: boolean = false;
  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  activeDayIsOpen: boolean = false;
  constructor(
    private dialog: MatDialog,
    private icsService:CalendarioIcsService,
    private icsBag:IcsBagService
  ) { }

  ngOnInit(): void {
    this.user=JSON.parse(sessionStorage.getItem("_userInfo")!);
    this.soggettoAttuatore=JSON.parse(sessionStorage.getItem("soggettoAttuatore")!);

    this.icsBag._filtriRicerca.subscribe({
      next: (ris) => {

        if (this.soggettoAttuatore?.id) {
          ris.idSoggettoAttuatore = this.soggettoAttuatore.id.toString();
        }

        this.icsService.ricercaEventiCalendario(ris).subscribe({
            next: (ris) => {
              if (ris.eventiCalendario!.length > 0) {
                this.minDate = new Date(ris.eventiCalendario![0].evento?.dataOraInizio!);
                this.maxDate = new Date(ris.eventiCalendario![ris.eventiCalendario!.length - 1].evento?.dataOraInizio!);
                this.viewDate = new Date(ris.eventiCalendario![0].evento?.dataOraInizio!);
              }
              this.events = ris.eventiCalendario!.map((evento) => {
                return {
                  id: evento.evento!.idEventoCalendario!,
                  start: addHours(
                    startOfDay(new Date(evento.evento!.dataOraInizio!)),
                    new Date(evento.evento!.dataOraInizio!).getHours()
                  ),
                  end: addHours(
                    startOfDay(new Date(evento.evento!.dataOraFine!)),
                    new Date(evento.evento!.dataOraFine!).getHours()
                  ),
                  title: evento.evento!.descrizioneEvento!,
                  color: colors['blue'],
                };
              });
              this.dateOrViewChanged();
              this.activeDayIsOpen = false;
            },
          });
      },
    });
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  handleEvent(action: string, event: CalendarEvent): void {
    // nothing to do so far
  }

  increment(): void {
    this.changeDate(addPeriod(this.view, this.viewDate, 1));
    this.activeDayIsOpen=false;
  }

  decrement(): void {
    this.changeDate(subPeriod(this.view, this.viewDate, 1));
    this.activeDayIsOpen=false;
  }

  changeDate(date: Date): void {
    this.viewDate = date;
    this.dateOrViewChanged();
  }

  changeView(view: CalendarView): void {
    this.view = view;
    this.dateOrViewChanged();
  }

  dateIsValid(date: Date): boolean {
    return date >= this.minDate && date <= this.maxDate;
  }

  dateOrViewChanged(): void {
   this.prevBtnDisabled = !this.dateIsValid(
      endOfPeriod(this.view, subPeriod(this.view, this.viewDate, 1))
    );

    this.nextBtnDisabled = !this.dateIsValid(
      startOfPeriod(this.view, addPeriod(this.view, this.viewDate, 1))
    );
      if(this.events.length==0){
        this.nextBtnDisabled=true
        this.prevBtnDisabled=true
      }
  }

  beforeMonthViewRender({ body }: { body: CalendarMonthViewDay[] }): void {
    body.forEach((day) => {
      if (!this.dateIsValid(day.date)) {
        day.cssClass = 'cal-disabled';
      }
    });
  }
  refresh = new Subject<void>();
  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({ event, newStart, newEnd,}: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
    this.handleEvent('Dropped or resized', event);
  }

  deleteEvent(eventToDelete: CalendarEvent) {
    this.events = this.events.filter((event) => event !== eventToDelete);
  }

  openDialog(title: string, msg: string[], cssClass: string, buttons?: DialogButton[]) {
    this.dialog.open(DialogConfermaComponent,{
      data: new DialogSettings(title, msg, cssClass, "", buttons),
      disableClose:true
    })
  }

  get todayBtnDisabled() {
    let today = new Date();
    return today < this.minDate || today > this.maxDate || this.events.length == 0
  }
}
