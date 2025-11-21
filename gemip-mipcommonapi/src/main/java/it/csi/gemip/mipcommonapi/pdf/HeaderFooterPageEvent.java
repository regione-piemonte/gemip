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

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEvent extends PdfPageEventHelper {
    @ConfigProperty(name = "images.location")
    String imageLocation;

    @Override
    public void onStartPage(PdfWriter writer, Document documento) {

        Image logoMip = null;
        try {
            InputStream is = this.getFileAsIOStream("logomip.jpg");
            logoMip = Image.getInstance(is.readAllBytes());
            logoMip.scalePercent(75);
            logoMip.setAlignment(2);
            documento.add(logoMip);

        } catch (IOException | DocumentException  e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    public void onEndPage(PdfWriter writer, Document documento) {

        Rectangle pageSize = documento.getPageSize();
        float x= pageSize.getLeft() + 50;
        float  y = pageSize.getBottom()+15;
        Image logoMip = null;
        try {
            InputStream is = this.getFileAsIOStream("altrilogo.jpg");
            logoMip = Image.getInstance(is.readAllBytes());
            logoMip.scalePercent(70);
            logoMip.setAbsolutePosition(x, y);

            logoMip.setAlignment(3);
            documento.add(logoMip);
        } catch (IOException | DocumentException e) {
            throw new IllegalStateException(e);
        }
        // Ottieni il numero di pagina corrente
        int pageNumber = writer.getPageNumber();

        // Crea un Chunk con il testo da visualizzare nel footer
        Chunk chunk = new Chunk("Pagina " + pageNumber);

        // Crea un Phrase con il Chunk
        Phrase phrase = new Phrase(chunk);

        // Aggiungi il Phrase al footer
        PdfContentByte content = writer.getDirectContent();

        ColumnText.showTextAligned(content, Element.ALIGN_CENTER, phrase, documento.right() / 2, documento.bottom() - 70, 0);

    }

    @Override
    public void onCloseDocument(PdfWriter writer, Document documento) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("quiqui: " + String.valueOf(writer.getPageNumber() - 1)), documento.right() / 2, documento.bottom() - 70, 0);
    }

    private InputStream getFileAsIOStream(final String fileName)
    {
        java.io.InputStream ioStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}