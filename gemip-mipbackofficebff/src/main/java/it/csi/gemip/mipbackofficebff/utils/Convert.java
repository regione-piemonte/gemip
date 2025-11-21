package it.csi.gemip.mipbackofficebff.utils;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import it.csi.gemip.mipbackofficebff.bff.dto.InfoOperatore;
import jakarta.inject.Inject;


public class Convert {
    @Inject
    private static Logger logger = Logger.getLogger(Convert.class);

    public static Date convertStringToDate(String dataStringa) {
        Date dataDate = null;

        try {
            dataDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ITALY).parse(dataStringa);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return dataDate;
    }

    public static List<InfoOperatore> convertFromXml(String xml) {
        List<String[]> par = Arrays.stream(xml.split("<"))
                .map(part -> part.split("/"))
                .map(p -> p[0].split(">"))
                .collect(Collectors.toList());

        List<InfoOperatore> infoOperatoreList = new ArrayList<>();
        String codOperatore = "";
        String descrizioneOperatore = "";
        String gruppoOperatore = "";
        String componente = ""; // campo opzionale
        boolean codParsed = false;
        boolean descParsed = false;
        boolean gruppParsed = false;

        logger.info("risposta da flaidor: " + xml);

        for (int i = 0; i < par.size(); i++) {

            if (par.get(i)[0].equals("codiceOperatore")) {
                codParsed = true;
                if (par.get(i).length > 1)
                    codOperatore = par.get(i)[1];
            }
            if (par.get(i)[0].equals("descrizioneOperatore")) {
                descParsed = true;
                if (par.get(i).length > 1)
                    descrizioneOperatore = par.get(i)[1];
            }

            if (par.get(i)[0].equals("gruppoOperatore")) {
                gruppParsed = true;
                if (par.get(i).length > 1) {
                    gruppoOperatore = par.get(i)[1];
                }
            }

            if (par.get(i)[0].equals("componente")) {
                if (par.get(i).length > 1) {
                    componente = par.get(i)[1];
                }
            }

            if (par.get(i)[0].startsWith("info-persona") || i == par.size() - 1) {
                if (codParsed && descParsed && gruppParsed) {

                    InfoOperatore tmp = new InfoOperatore();

                    tmp.setCodOperatore(Integer.parseInt(codOperatore));
                    tmp.setDescrOperatore(descrizioneOperatore);
                    tmp.setGruppoOperatore(gruppoOperatore);
                    tmp.setAreaTerritoriale(componente);
                    infoOperatoreList.add(tmp);
                    codOperatore = "";
                    descrizioneOperatore = "";
                    gruppoOperatore = "";
                    componente = "";
                    codParsed = false;
                    gruppParsed = false;
                    descParsed = false;
                }
            }
        }

        return infoOperatoreList;
    }
}
