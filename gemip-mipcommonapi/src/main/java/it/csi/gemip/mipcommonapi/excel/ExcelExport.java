package it.csi.gemip.mipcommonapi.excel;

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

import it.csi.gemip.mipcommonapi.api.impl.ParentApiImpl;
import it.csi.gemip.model.ExcelCittadinoAnagraficaModel;
import it.csi.gemip.model.ExcelReportisticaIdeaDiImpresa;
import it.csi.gemip.model.ExcelReportisticaQuestionari;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ExcelExport {
    public ExcelExport() {
        //

    }


    public byte[] getBytes(Workbook workbook) {

        byte[] bytes;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            workbook.write(byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert Excel to byte array: " + e.getMessage());
        }

        return bytes;
    }


    public <T> Response addDataToWorkbook(List<T> dati, List<String> headers, SortedMap<String, String> altriDati,
                                          String nomeSheet)
    {
        Workbook workbook = new XSSFWorkbook();
        Sheet dataSheet = workbook.createSheet(nomeSheet);
        int riga = 0;
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setWrapText(true);
        dataStyle.setVerticalAlignment(VerticalAlignment.TOP);
        dataStyle.setShrinkToFit(true);
        for (String key : altriDati.keySet()) {
            Row row = dataSheet.createRow(riga);
            Cell label = row.createCell(0);
            label.setCellValue(key.split("]")[1]);

            Cell valore = row.createCell(1);
            valore.setCellValue(altriDati.get(key));
            riga++;
        }
        riga++;
        dataSheet = inizializzaHeaders(dataSheet, headers, riga);
        CellStyle dateCellStyle = workbook.createCellStyle();
        CellStyle dateHourCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
        dateHourCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy HH:mm"));
        for (int i = 1; i <= dati.size(); i++) {
            Row row = dataSheet.createRow(i + riga);
            if (dati.get(i - 1) instanceof ExcelCittadinoAnagraficaModel) {
                inserisciDatoInRigaCittadinoAnagrafica((ExcelCittadinoAnagraficaModel) dati.get(i - 1), row, dateCellStyle);
            }
            if (dati.get(i - 1) instanceof ExcelReportisticaIdeaDiImpresa) {
                inserisciDatoInRigaReportisticaIdeaDiImpresa((ExcelReportisticaIdeaDiImpresa) dati.get(i - 1), row, dateCellStyle, dateHourCellStyle);
            }
            if(dati.get(i-1) instanceof ExcelReportisticaQuestionari){
                inserisciDatoInRigaReportisticaQuestionario((ExcelReportisticaQuestionari) dati.get(i - 1), row,dateCellStyle);
            }
            row.setRowStyle(dataStyle);
        }
        for (int i = 0; i < headers.size(); i++) {
            int size = dataSheet.getRow(riga).getCell(i).getStringCellValue().length();
            if (size < 25) {
                size = 25;
            }
            //dobbiamo moltiplicare il valore per 256
            //perchè il parametro che passiamo equivale a 1/256 di dimensione di un carattere
            dataSheet.setColumnWidth(i, (size + 2) * 256);
        }

        return Response.ok(getBytes(workbook)).build();

    }

    public Sheet inizializzaHeaders(Sheet sheet, List<String> headers, int riga) {
        Row row = sheet.createRow(riga);
        Font fonte = row.getSheet().getWorkbook().createFont();
        fonte.setBold(true);
        fonte.setFontName("Calibri");
        fonte.setFontHeightInPoints((short) 11);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setFont(fonte);
        for (int i = 0; i < headers.size(); i++) {
            row.createCell(i);

            Cell tmp = row.createCell(i);

            tmp.setCellValue(headers.get(i));
            tmp.setCellStyle(style);

        }
        return sheet;
    }

    public void inserisciDatoInRigaCittadinoAnagrafica(ExcelCittadinoAnagraficaModel dato, Row row, CellStyle style) {
        Cell cognome = row.createCell(0);
        Cell nome = row.createCell(1);
        Cell sesso = row.createCell(2);
        Cell dataDiNascita = row.createCell(3,CellType.NUMERIC);
        dataDiNascita.setCellStyle(style);
        Cell luogoDiNascita = row.createCell(4);
        Cell provinciaDiNascita = row.createCell(5);
        Cell nazioneDiNascita = row.createCell(6);
        Cell codiceFiscale = row.createCell(7);
        Cell recapitoTelefono = row.createCell(8);
        Cell cellulare = row.createCell(9);
        Cell recapitoEmail = row.createCell(10);
        Cell emailAlternativa = row.createCell(11);
        Cell cittadinanza = row.createCell(12);
        Cell citadinanzaAltroSpecificare = row.createCell(13);
        Cell documentoIdentitaNumero = row.createCell(14);
        Cell documentoIdentitaRilasciatoDa = row.createCell(15);
        Cell documentoIdentitaScadenza = row.createCell(16);
        Cell numPermessoDiSoggiorno = row.createCell(17);
        Cell permessoDiSoggiornoRilasciatoDa = row.createCell(18);
        Cell scadenzaTitoloDiSoggiorno = row.createCell(19);
        Cell numCartaDiSoggiorno = row.createCell(20);
        Cell cartaDiSoggiornoRilasciatoDa = row.createCell(21);
        Cell scadenzaCartaDiSoggiorno = row.createCell(22);
        Cell indirizzoResidenza = row.createCell(23);
        Cell cittaResidenza = row.createCell(24);
        Cell provinciaDiResidenza = row.createCell(25);
        Cell nazioneDiResidenza = row.createCell(26);
        Cell capResidenza = row.createCell(27);
        Cell indirizzoDomicilio = row.createCell(28);
        Cell cittaDomicilio = row.createCell(29);
        Cell provinciaDiDomicilio = row.createCell(30);
        Cell nazioneDiDomicilio = row.createCell(31);
        Cell capDomicilio = row.createCell(32);
        Cell titoloDiStudio = row.createCell(33);
        Cell titoloDiStudioAltroSpecificare = row.createCell(34);
        Cell svantaggioAbitativo = row.createCell(35);
        Cell condizioneFamiliare = row.createCell(36);
        Cell condizioneOccupazionale = row.createCell(37);
        Cell situazioneLavorativaAltroSpecificare = row.createCell(38);

        cognome.setCellValue(dato.getCognome());
        nome.setCellValue(dato.getNome());
        sesso.setCellValue(dato.getSesso());
        dataDiNascita.setCellValue(dato.getDataDiNascita());
        luogoDiNascita.setCellValue(dato.getLuogoDiNascita());
        provinciaDiNascita.setCellValue(dato.getProvinciaDiNascita());
        nazioneDiNascita.setCellValue(dato.getNazioneDiNascita());
        codiceFiscale.setCellValue(dato.getCodiceFiscale());
        recapitoTelefono.setCellValue(dato.getRecapitoTelefono());
        cellulare.setCellValue(dato.getCellulare());
        recapitoEmail.setCellValue(dato.getRecapitoEmail());
        emailAlternativa.setCellValue(dato.getEmailAlternativa());
        cittadinanza.setCellValue(dato.getCittadinanza());
        citadinanzaAltroSpecificare.setCellValue(dato.getCitadinanzaAltroSpecificare());
        documentoIdentitaNumero.setCellValue(dato.getDocumentoIdentitaNumero());
        documentoIdentitaRilasciatoDa.setCellValue(dato.getDocumentoIdentitaRilasciatoDa());
        documentoIdentitaScadenza.setCellValue(dato.getDocumentoIdentitaScadenza());
        if(dato.getTipoPermessoDiSoggiorno().equals("P")){
            numPermessoDiSoggiorno.setCellValue(dato.getNumPermessoDiSoggiorno());
            permessoDiSoggiornoRilasciatoDa.setCellValue(dato.getPermessoDiSoggiornoRilasciatoDa());
            scadenzaTitoloDiSoggiorno.setCellValue(dato.getScadenzaTitoloDiSoggiorno());
        }else if(dato.getTipoPermessoDiSoggiorno().equals("C")){
            numCartaDiSoggiorno.setCellValue(dato.getNumPermessoDiSoggiorno());
            cartaDiSoggiornoRilasciatoDa.setCellValue(dato.getPermessoDiSoggiornoRilasciatoDa());
            scadenzaCartaDiSoggiorno.setCellValue(dato.getScadenzaTitoloDiSoggiorno());
        }
        indirizzoResidenza.setCellValue(dato.getIndirizzoResidenza());
        cittaResidenza.setCellValue(dato.getCittaResidenza());
        provinciaDiResidenza.setCellValue(dato.getProvinciaDiResidenza());
        nazioneDiResidenza.setCellValue(dato.getNazioneDiResidenza());
        capResidenza.setCellValue(dato.getCapResidenza());
        indirizzoDomicilio.setCellValue(dato.getIndirizzoDomicilio());
        cittaDomicilio.setCellValue(dato.getCittaDomicilio());
        provinciaDiDomicilio.setCellValue(dato.getProvinciaDiDomicilio());
        nazioneDiDomicilio.setCellValue(dato.getNazioneDiDomicilio());
        capDomicilio.setCellValue(dato.getCapDomicilio());
        titoloDiStudio.setCellValue(dato.getTitoloDiStudio());
        titoloDiStudioAltroSpecificare.setCellValue(dato.getTitoloDiStudioAltroSpecificare());
        svantaggioAbitativo.setCellValue(dato.getSvantaggioAbitativo());
        condizioneFamiliare.setCellValue(dato.getCondizioneFamiliare());
        condizioneOccupazionale.setCellValue(dato.getCondizioneOccupazionale());
        situazioneLavorativaAltroSpecificare.setCellValue(dato.getSituazioneLavorativaAltroSpecificare());

    }

    protected void inserisciDatoInRigaReportisticaIdeaDiImpresa(ExcelReportisticaIdeaDiImpresa dato, Row row, CellStyle dateStyle, CellStyle dateHourStyle) {

        Cell idIdea = row.createCell(0);
        Cell titolo = row.createCell(1);
        Cell tipoProj = row.createCell(2);
        Cell dataInserim = row.createCell(3, CellType.NUMERIC);
        dataInserim.setCellStyle(dateStyle);
        Cell descrizioneAreaTerritoriale = row.createCell(4);
        Cell descrizioneIdeaDiImpresa = row.createCell(5);
        Cell statoIdeaDiImpresa = row.createCell(6);
        Cell utentePrincipale = row.createCell(7);
        Cell dataPreaccoglienza = row.createCell(8, CellType.NUMERIC);
        dataPreaccoglienza.setCellStyle(dateHourStyle);
        Cell sedePreaccoglienza = row.createCell(9);
        Cell incontroTelefonico = row.createCell(10);
        Cell soggettoAttuatoreDenominazione = row.createCell(11);
        Cell dataSoggettoAttuatoreAssociazione = row.createCell(12,CellType.NUMERIC);
        dataSoggettoAttuatoreAssociazione.setCellStyle(dateStyle);
        Cell erogazionePrimaOra = row.createCell(13);
        Cell firmaPattoServizio = row.createCell(14);
        Cell dataFirmaPattoServizio = row.createCell(15,CellType.NUMERIC);
        dataFirmaPattoServizio.setCellStyle(dateStyle);
        Cell BPValidato = row.createCell(16);
        Cell dataBPValidato = row.createCell(17,CellType.NUMERIC);
        dataBPValidato.setCellStyle(dateStyle);
        Cell misuraRicambio = row.createCell(18);
        Cell idUtente = row.createCell(19);
        Cell cognome = row.createCell(20);
        Cell nome = row.createCell(21);
        Cell codiceFiscale = row.createCell(22);
        Cell sesso = row.createCell(23);
        Cell cittadinanza = row.createCell(24);
        Cell cittadinanzaAltro = row.createCell(25);
        Cell dataNascita = row.createCell(26,CellType.NUMERIC);
        dataNascita.setCellStyle(dateStyle);
        Cell luogoNascita = row.createCell(27);
        Cell provinciaNascita = row.createCell(28);
        Cell recapitoTelefono = row.createCell(29);
        Cell recapitoTelefono2 = row.createCell(30);
        Cell email = row.createCell(31);
        Cell email2 = row.createCell(32);
        Cell indirizzoResidenza = row.createCell(33);
        Cell cittaResidenza = row.createCell(34);
        Cell capResidenza = row.createCell(35);
        Cell provinciaResidenza = row.createCell(36);
        Cell indirizzoDomicilio = row.createCell(37);
        Cell capDomicilio = row.createCell(38);
        Cell cittaDomicilio = row.createCell(39);
        Cell provinciaDomicilio = row.createCell(40);
        Cell titoloStudio = row.createCell(41);
        Cell titoloStudioAltro = row.createCell(42);
        Cell situazioneLavorativa = row.createCell(43);
        Cell situazioneLavorativaAltro = row.createCell(44);
        Cell svantaggioAbitativo = row.createCell(45);
        Cell condizioneFamiliare = row.createCell(46);
        Cell noteCommenti = row.createCell(47);
        Cell creato = row.createCell(48, CellType.NUMERIC);
        creato.setCellStyle(dateStyle);
        Cell inseritoDa = row.createCell(49);
        Cell modificato = row.createCell(50, CellType.NUMERIC);
        modificato.setCellStyle(dateStyle);
        Cell modificatoDa = row.createCell(51);
        Cell conoscenzaMip = row.createCell(52);
        Cell altroConoscenzaMip = row.createCell(53);
        Cell commentiInterni = row.createCell(54);
        Cell titoloSoggiorno = row.createCell(55);
        Cell motivoTitoloSoggiorno = row.createCell(56);
        Cell titoloCarta = row.createCell(57);
        Cell motivoCartaSoggiorno = row.createCell(58);


        // Imposta i valori delle celle con gli attributi dell'oggetto
        idIdea.setCellValue(dato.getIdIdea());
        titolo.setCellValue(dato.getTitolo());
        tipoProj.setCellValue(dato.getTipoProj());
        dataInserim.setCellValue(dato.getDataInserim());
        descrizioneAreaTerritoriale.setCellValue(dato.getDescrizioneAreaTerritoriale());
        descrizioneIdeaDiImpresa.setCellValue(dato.getDescrizioneIdeaDiImpresa());
        statoIdeaDiImpresa.setCellValue(dato.getStatoIdeaDiImpresa());
        utentePrincipale.setCellValue(dato.getUtentePrincipale());
        dataPreaccoglienza.setCellValue(dato.getDataPreaccoglienza());
        sedePreaccoglienza.setCellValue(dato.getSedePreaccoglienza());
        incontroTelefonico.setCellValue(dato.getFlgIncontroTelefonico());
        soggettoAttuatoreDenominazione.setCellValue(dato.getSoggettoAttuatoreDenominazione());
        dataSoggettoAttuatoreAssociazione.setCellValue(dato.getDataSoggettoAttuatoreAssociazione());
        erogazionePrimaOra.setCellValue(dato.getErogazionePrimaOra());
        firmaPattoServizio.setCellValue(dato.getFirmaPattoServizio());
        dataFirmaPattoServizio.setCellValue(dato.getDataFirmaPattoServizio());
        BPValidato.setCellValue(dato.getBPValidato());
        dataBPValidato.setCellValue(dato.getDataBPValidato());
        misuraRicambio.setCellValue(dato.getMisuraRicambio());
        idUtente.setCellValue(dato.getIdUtente());
        cognome.setCellValue(dato.getCognome());
        nome.setCellValue(dato.getNome());
        codiceFiscale.setCellValue(dato.getCodiceFiscale());
        sesso.setCellValue(dato.getSesso());
        cittadinanza.setCellValue(dato.getCittadinanza());
        cittadinanzaAltro.setCellValue(dato.getCittadinanzaAltro());
        dataNascita.setCellValue(dato.getDataNascita());
        luogoNascita.setCellValue(dato.getLuogoNascita());
        provinciaNascita.setCellValue(dato.getProvinciaNascita());
        recapitoTelefono.setCellValue(dato.getRecapitoTelefono2());
        recapitoTelefono2.setCellValue(dato.getRecapitoTelefono());
        email.setCellValue(dato.getEmail());
        email2.setCellValue(dato.getEmail2());
        indirizzoResidenza.setCellValue(dato.getIndirizzoResidenza());
        cittaResidenza.setCellValue(dato.getCittaResidenza());
        capResidenza.setCellValue(dato.getCapResidenza());
        provinciaResidenza.setCellValue(dato.getProvinciaResidenza());
        indirizzoDomicilio.setCellValue(dato.getIndirizzoDomicilio());
        capDomicilio.setCellValue(dato.getCapDomicilio());
        cittaDomicilio.setCellValue(dato.getCittaDomicilio());
        provinciaDomicilio.setCellValue(dato.getProvinciaDomicilio());
        titoloStudio.setCellValue(dato.getTitoloStudio());
        titoloStudioAltro.setCellValue(dato.getTitoloStudioAltro());
        situazioneLavorativa.setCellValue(dato.getSituazioneLavorativa());
        situazioneLavorativaAltro.setCellValue(dato.getSituazioneLavorativaAltro());
        svantaggioAbitativo.setCellValue(dato.getSvantaggioAbitativo());
        condizioneFamiliare.setCellValue(dato.getCondizioneFamiliare());
        noteCommenti.setCellValue(dato.getNoteCommenti());
        creato.setCellValue(dato.getCreato());
        inseritoDa.setCellValue(dato.getInseritoDa());
        modificato.setCellValue(dato.getModificato());
        modificatoDa.setCellValue(dato.getModificatoDa());
        conoscenzaMip.setCellValue(dato.getConoscenzaMip());
        altroConoscenzaMip.setCellValue(dato.getAltroConoscenzaMip());
        commentiInterni.setCellValue(dato.getCommentiInterni());
        if(dato.getTipoPermessoDiSoggiorno() != null && !dato.getTipoPermessoDiSoggiorno().equals("")){
            if(dato.getTipoPermessoDiSoggiorno().equals("C")){
                titoloCarta.setCellValue(dato.getTitoloSoggiorno());
                motivoCartaSoggiorno.setCellValue(dato.getMotivoTitoloSoggiorno());
            }else if(dato.getTipoPermessoDiSoggiorno().equals("P")){
                titoloSoggiorno.setCellValue(dato.getTitoloSoggiorno());
                motivoTitoloSoggiorno.setCellValue(dato.getMotivoTitoloSoggiorno());
            }
        }else{
            titoloCarta.setCellValue(dato.getTitoloSoggiorno());
            motivoCartaSoggiorno.setCellValue(dato.getMotivoTitoloSoggiorno());
            titoloSoggiorno.setCellValue(dato.getTitoloSoggiorno());
            motivoTitoloSoggiorno.setCellValue(dato.getMotivoTitoloSoggiorno());
        }
    }

    protected void inserisciDatoInRigaReportisticaQuestionario(ExcelReportisticaQuestionari dato, Row row,CellStyle style) {
        Cell id = row.createCell(0);
        Cell dataCompletamento = row.createCell(1,CellType.NUMERIC);
        dataCompletamento.setCellStyle(style);
        Cell areaTerritoriale = row.createCell(2);
        Cell soggettoAttuatore = row.createCell(3);

        int index = 4;
        Map<String, Cell> mappaCelle = new HashMap<>();
        for(String r : dato.getRisposte()) {
            String nomeCella = "risposta" + index;
            Cell cella = row.createCell(index);
            mappaCelle.put(nomeCella, cella); // Associazione del nome della cella alla cella stessa nella mappa
            index++;
        }

        id.setCellValue(dato.getIdQuestionario());
        dataCompletamento.setCellValue(dato.getDataInserim());
        areaTerritoriale.setCellValue(dato.getAreaTerritoriale());
        soggettoAttuatore.setCellValue(dato.getSoggettoAttuatore());
        for (Map.Entry<String, Cell> entry : mappaCelle.entrySet()) {
            String nomeCella = entry.getKey();
            Cell cella = entry.getValue();
            int indiceRisposta = Integer.parseInt(nomeCella.replace("risposta", "")) - 4;
            String risposta = dato.getRisposte().get(indiceRisposta);
            cella.setCellValue(risposta);
        }
    }

}
