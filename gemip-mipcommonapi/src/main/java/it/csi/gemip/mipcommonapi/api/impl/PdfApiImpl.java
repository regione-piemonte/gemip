package it.csi.gemip.mipcommonapi.api.impl;

/*-
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import it.csi.gemip.mipcommonapi.api.PdfApi;
import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRIdeaDiImpresaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.pdf.PdfExport;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class PdfApiImpl implements PdfApi {
    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;

    @Inject
    MipRIdeaDiImpresaCittadinoRepository mipRIdeaDiImpresaCittadinoRepository;


    @Override
    public Response exportPdfPresenze(Long idIncontroPreaccoglienza, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        PdfExport pdfExport = new PdfExport();
        MipTIncontroPreaccoglienza incontroPreaccoglienza = mipTIncontroPreaccoglienzaRepository.findById(idIncontroPreaccoglienza);
        List<MipRIdeaDiImpresaCittadino> ideaDiImpresaCittadinoList = mipRIdeaDiImpresaCittadinoRepository.getIdeeDiImpresaCittadiniByIdIncontroPreacc(idIncontroPreaccoglienza);
        List<String> header = new ArrayList<>();
        header.add("Cognome");
        header.add("Nome");
        header.add("Idea d'impresa");
        header.add("Firma");

        SortedMap<String,String> altriDati = new TreeMap<>();
        SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        SimpleDateFormat outTime = new SimpleDateFormat("HH:mm", Locale.ITALY);
        Date dataIncontro = incontroPreaccoglienza.getDataIncontro();
        //aggiunge un'ora alla data tornata
        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(dataIncontro);               // sets calendar time/date
        cal.add(Calendar.HOUR_OF_DAY, 2);      // adds one hour

        String orario = outTime.format(dataIncontro) + " - " + outTime.format( cal.getTime());
        altriDati.put("[1]Data incontro", outDate.format(dataIncontro).toString());
        altriDati.put("[2]Orario incontro", orario);
        altriDati.put("[3]Sede incontro", incontroPreaccoglienza.getLuogoIncontro() != null ? incontroPreaccoglienza.getLuogoIncontro().getDenominazione() : "PRE ACCOGLIENZA ONLINE - TORINO ()");

        return Response.ok(pdfExport.getPdfPresenze(ideaDiImpresaCittadinoList,header,altriDati)).build();
    }
}
