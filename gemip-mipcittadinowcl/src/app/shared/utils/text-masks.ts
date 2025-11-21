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



///////////////////////////////////////
/// MASKS
///////////////////////////////////////
export const GRUPPO_OPERATORE_MASK =   [/[A-Z]/i];

export const COD_OPERATORE_MASK = [/\d/,/\d/,/\d/,/\d/,/\d/,/\d/];

export const IDENTIFICATIVO_MASK = [/\d/,/\d/,/\d/,/\d/,/\d/];
export const CAP_MASK = [/\d/,/\d/,/\d/,/\d/,/\d/];

export const ITALIAN_TELEPHONE_MASK =
[/\d/,/\d/,/\d/,/\d/,/\d/,/\d/,/\d/,/\d/,/\d/,/\d/];


export const GRUPPO_CODICE_MASK =
[/[A-Z]/i,/\d/,/\d/,/\d/,/\d/,/\d/]

export const CF_MASK =
[/[A-Z]/i,/[A-Z]/i,/[A-Z]/i,/[A-Z]/i,/[A-Z]/i,/[A-Z]/i,
 /\d/,/\d/,/[A-Z]/i,/\d/,/\d/,
 /[A-Z]/i,/\d/,/\d/,/\d/,/[A-Z]/i
];

export const IVA_MASK =
[/\d/,/\d/,/\d/,/\d/,
/\d/,/\d/,/\d/,/\d/,
/\d/,/\d/,/\d/]

export const ORARIO_INCONTRO_MASK=[/^([0-9]|0[0-9]|1[0-9]|2[0-3])/,/^([0-9]|0[0-9]|1[0-9]|2[0-3])/,/:/,/[03]/,/[0]/]
///////////////////////////////////////
/// Not Used
///////////////////////////////////////

export const COD_PROVINCIA_MASK =   [/[A-Z]/i, /[A-Z]/i];

export const ITALIAN_DATE_MASK = [
    /\d/, /\d/, '/', /\d/, /\d/, '/', /\d/, /\d/, /\d/, /\d/
];

export const IBAN_TEXTMASK =
['I', 'T', /\d/, /\d/, '-',
/[A-Z]/i, /\d/, /\d/, /\d/, '-',
/\d/, /\d/, /\d/, /\d/, '-',
/\d/, /\d/, /\d/, /\d/, '-',
/\d/, /\d/, /\d/, /\d/, '-',
/\d/, /\d/, /\d/, /\d/, '-',
/\d/, /\d/, /\d/
];

// export const EURO_VALUE_MASK = createNumberMask({
//     allowDecimal: true,
//     prefix: '€',
//     suffix: '',
//     requireDecimal: true,
//     decimalSymbol: ',',
//     thousandsSeparatorSymbol: '.'
// });





export function createDenominationMask(length: number, numbersAllowed: boolean = false) {
    const item = (!numbersAllowed ? /([A-Z]|\s|')/i : /([A-Z]|[0-9]|\s|')/i);
    const pattern: Array<any> = new Array<any>(length);
    for (let i = 0; i < pattern.length; i++) {
        pattern[i] = item;
    }
    return pattern;
}

////////////////////////////////
/// PIPES
////////////////////////////////
// export const ITALIAN_DATE_PIPE = createAutoCorrectedDatePipe('dd/mm/yyyy');

// export const TO_UPPER_PIPE = function(conformedValue: string) {
//     if (conformedValue != null){
//       return conformedValue.toUpperCase();
//     }
//     else return conformedValue;
// }

// export const TO_LOWER_PIPE = function(conformedValue: string) {
//     if (conformedValue != null){
//       return conformedValue.toLowerCase();
//     }
//     else return conformedValue;
// }

// export const ITALIAN_DATE_FORMATTER = function(dateString: string): string{
//     if (dateString){
//         var date = new Date(dateString);
//         console.log(date);
//         var formattedDate = date.getDate()+"/"+(date.getMonth()<10 ? '0'+date.getMonth() : date.getMonth()) +"/"+date.getFullYear();
//         console.log("formatted date:"+formattedDate);
//         return formattedDate;
//     }
//     else{
//         return null;
//     }
// }
