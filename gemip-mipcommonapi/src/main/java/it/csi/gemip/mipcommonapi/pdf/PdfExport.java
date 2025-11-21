package it.csi.gemip.mipcommonapi.pdf;

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

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import it.csi.gemip.mipcommonapi.integration.entities.MipRIdeaDiImpresaCittadino;

public class PdfExport {
    @ConfigProperty(name = "images.location")
    String imageLocation;

    private static Font titoloFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
    private static Font normaleFont = FontFactory.getFont(FontFactory.HELVETICA, 12, BaseColor.BLACK);
    private static Font subFont =  FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 14, BaseColor.BLACK);
    private static Font normaleBold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);

    public byte[] getPdfPresenze(List<MipRIdeaDiImpresaCittadino> dati, List<String> headers, SortedMap<String,String> altriDati) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document documento = new Document(PageSize.LETTER,50,50,5,90);
            PdfWriter writer =  PdfWriter.getInstance(documento, byteArrayOutputStream);

            // Creazione dell'istanza della classe personalizzata per header e footer
            HeaderFooterPageEvent event = new HeaderFooterPageEvent();
            writer.setPageEvent(event);

            documento.open();

            // aggiunge title
            Paragraph title = new Paragraph("Incontro di pre-accoglienza - foglio registro presenze", titoloFont);
            title.setSpacingAfter(10);
            documento.add(title);

            // aggiunge detagli generazione meeting details
            documento.add(tableDatiGenerici(altriDati));

            Paragraph subTitle = new Paragraph("Idee d’impresa", subFont);
            subTitle.setSpacingAfter(10);
            subTitle.setSpacingBefore(10);
            documento.add(subTitle);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setComplete(true);
            // aggiunge table header
            for (String header : headers) {
                PdfPCell cella = new PdfPCell(new Phrase(header, normaleBold));
                cella.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cella);
            }
            table.setHeaderRows(1);

            for (MipRIdeaDiImpresaCittadino citta : dati) {
                PdfPCell cella = new PdfPCell(new Phrase(citta.getIdCittadino().getCognome(), normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase(citta.getIdCittadino().getNome(), normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase(citta.getIdIdeaDiImpresa().getTitolo(), normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase("", normaleFont));
                cella.setMinimumHeight(30);
                table.addCell(cella);
            }
            /*
            // per testare 100 dati
            for (int i = 0; i <= 100; i++){
                PdfPCell cella = new PdfPCell(new Phrase("Paparelo", normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase("Pippo" + i, normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase("\\\"FERMESSE\\\" Colazioni, Aperitivi e Drink", normaleFont));
                table.addCell(cella);
                cella = new PdfPCell(new Phrase("", normaleFont));
                table.addCell(cella);

            }*/

            documento.add(table);

            documento.close();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PdfPTable tableDatiGenerici(SortedMap<String,String> dati) {
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        for (Entry<String, String> entry : dati.entrySet()) {
            PdfPCell c1 = new PdfPCell(new Phrase(entry.getKey().split("]")[1], normaleBold));

            table.addCell(c1);
            c1 = new PdfPCell(new Phrase(entry.getValue(), normaleFont));
            c1.setColspan(2);
            table.addCell(c1);
        }
        return table;
    }
}
