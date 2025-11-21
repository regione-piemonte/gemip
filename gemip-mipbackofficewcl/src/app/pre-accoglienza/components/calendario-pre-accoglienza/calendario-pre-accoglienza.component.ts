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
import { CalendarView, CalendarEvent, CalendarEventTimesChangedEvent, CalendarMonthViewDay } from 'angular-calendar';
import { addHours, subDays, startOfDay, addDays, endOfMonth, isSameMonth, isSameDay, endOfDay, addMonths, subMonths, addWeeks, endOfWeek, startOfMonth, startOfWeek, subWeeks } from 'date-fns';
import { Subject } from 'rxjs';
import { EventColor } from 'calendar-utils';
import { PreAccoglienzaBagService } from '@pre-accoglienza/services/pre-accoglienza-bag.service';
import { PreAccoglienzaService } from '@pre-accoglienza/services/pre-accoglienza.service';
import { FiltroRicercaPreAccoglienza } from '@shared/model/filtri-ricerca.model';
import { Router } from '@angular/router';
import { IncontroPreaccoglienzaRicerca } from '@core/models/incontroPreaccoglienzaRicerca';

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
  selector: 'app-calendario-pre-accoglienza',
  templateUrl: './calendario-pre-accoglienza.component.html',
  styleUrls: ['./calendario-pre-accoglienza.component.scss'],
  //encapsulation: ViewEncapsulation.None,
})
export class CalendarioPreAccoglienzaComponent implements OnInit {

  filtroCalendario: FiltroRicercaPreAccoglienza = {}
  events: CalendarEvent[] = [];
  risultatoRicerca!: IncontroPreaccoglienzaRicerca;
  minDate: Date = subMonths(new Date(), 1);
  maxDate: Date = addMonths(new Date(), 1);

  prevBtnDisabled: boolean = false;

  nextBtnDisabled: boolean = false;
  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  activeDayIsOpen: boolean = true;

  constructor(
    private incontriPreaccoglienzaService: PreAccoglienzaService,
    private incontriPreaccoglienzaBag: PreAccoglienzaBagService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.incontriPreaccoglienzaBag._incontriRicercati.subscribe(
      ris => {
        this.risultatoRicerca = ris;
        if (ris.incontriPreaccoglienza) {
          let incontri = ris.incontriPreaccoglienza!
          if (ris.incontriPreaccoglienza!.length > 0) {
            this.minDate = new Date(ris.incontriPreaccoglienza![0].dataIncontro!);
            this.maxDate = new Date(ris.incontriPreaccoglienza![ris.incontriPreaccoglienza!.length - 1].dataIncontro!);
            this.viewDate = new Date(ris.incontriPreaccoglienza![0].dataIncontro!);
          }
          this.events = incontri!.map(inc => {
            return {
              id: inc.id!,
              start: addHours(startOfDay(new Date(inc.dataIncontro!)), new Date(inc.dataIncontro!).getHours()),
              end: addHours(startOfDay(new Date(inc.dataIncontro!)), new Date(inc.dataIncontro!).getHours() + 2),
              title: inc.denominazione!,
              color: inc.flgIncontroErogatoDaRemoto ? { ...colors['yellow'] } : { ...colors['blue'] },
              draggable: false,
              actions: []
            }
          })
        }
        this.dateOrViewChanged();
      }
    )

    this.startSearch();
  }

  startSearch() {
    let dataDa: Date = new Date()
    dataDa.setHours(0);

    let dataA: Date = new Date();
    dataA.setMonth(dataDa.getMonth() + 1);
    dataA.setDate(0);
    dataA.setHours(23);

    this.filtroCalendario.dataDa = dataDa

    this.incontriPreaccoglienzaService.ricercaIncontri(this.filtroCalendario).subscribe({
      next: ris => {
        let incontri = ris.incontriPreaccoglienza!
        if (ris.incontriPreaccoglienza!.length > 0) {
          this.minDate = new Date(ris.incontriPreaccoglienza![0].dataIncontro!);
          this.maxDate = new Date(ris.incontriPreaccoglienza![ris.incontriPreaccoglienza!.length - 1].dataIncontro!);
          this.viewDate = new Date(ris.incontriPreaccoglienza![0].dataIncontro!);
        }
        this.events = incontri!.map(inc => {
          return {
            id: inc.id!,
            start: addHours(startOfDay(new Date(inc.dataIncontro!)), new Date(inc.dataIncontro!).getHours()),
            end: addHours(startOfDay(new Date(inc.dataIncontro!)), new Date(inc.dataIncontro!).getHours() + 2),
            title: inc.denominazione!,
            color: inc.flgIncontroErogatoDaRemoto ? { ...colors['yellow'] } : { ...colors['blue'] },
            draggable: false,
            actions: []
          }
        })
        this.dateOrViewChanged();
      }
    },
    )
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = true;
  }

  setView(view: CalendarView) {
    this.view = view;
  }

  handleEvent(action: string, event: CalendarEvent): void {
    if (action == 'Clicked') {
      this.incontriPreaccoglienzaBag.idIncontroPreaccoglienza = +event.id!
      this.router.navigateByUrl('/pre-accoglienza/modifica')
    }
  }

  increment(): void {
    this.changeDate(addPeriod(this.view, this.viewDate, 1));
    this.activeDayIsOpen = false
  }

  decrement(): void {
    this.changeDate(subPeriod(this.view, this.viewDate, 1));
    this.activeDayIsOpen = false
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

  eventTimesChanged({ event, newStart, newEnd, }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
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

  get todayBtnDisabled() {
    let today = new Date();
    return today < this.minDate || today > this.maxDate
  }
}
