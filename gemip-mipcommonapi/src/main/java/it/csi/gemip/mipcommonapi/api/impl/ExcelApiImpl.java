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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.csi.gemip.mipcommonapi.api.ExcelApi;
import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadino;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneFamiliare;
import it.csi.gemip.mipcommonapi.api.dto.CondizioneOccupazionale;
import it.csi.gemip.mipcommonapi.api.dto.SvantaggioAbitativo;
import it.csi.gemip.mipcommonapi.excel.ExcelExport;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadinoExten;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneFamiliareRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDCondizioneOccupazionaleRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSvantaggioAbitativoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDTitoloDiStudioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoExtenRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.model.ExcelCittadinoAnagraficaModel;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class ExcelApiImpl extends ParentApiImpl implements ExcelApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDCondizioneOccupazionaleRepository condizioneOccupazionaleRepository;

    @Inject
    MipDTitoloDiStudioRepository titoloDiStudioRepository;

    @Inject
    MipDSvantaggioAbitativoRepository svantaggioAbitativoRepository;

    @Inject
    MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;

    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;

    @Inject
    MipTAnagraficaCittadinoExtenRepository anagraficaCittadinoExtenRepository;
    @Inject
    MipDCondizioneFamiliareRepository condizioneFamiliareRepository;

    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;


    @Override
    public Response exportExcelAnagraficaCittadino(Long idIncontroPreaccoglienza, String codOperatore,String idSoggettoAttuatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        ExcelExport excelExport = new ExcelExport();
        MipTIncontroPreaccoglienza incontroPreaccoglienza = mipTIncontroPreaccoglienzaRepository.findById(idIncontroPreaccoglienza);
        SortedMap<String,String> altriDati = new TreeMap<>();
        SimpleDateFormat outDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);

        altriDati.put("[1]Report","Cittadino - Cittadino incontro di pre-accoglienza");
        altriDati.put("[2]Operatore", codOperatore);
        altriDati.put("[3]Creato il",outDateTime.format(getNow()));

        List<ExcelCittadinoAnagraficaModel> excelModelli= new ArrayList<>();
        List<String> headers=new ArrayList<>();
        headers.add("Cognome");
        headers.add("Nome");
        headers.add("Sesso");
        headers.add("Data di nascita");
        headers.add("Luogo di nascita");
        headers.add("Provincia di nascita");
        headers.add("Nazione di nascita");
        headers.add("Codice fiscale");
        headers.add("Telefono fisso");
        headers.add("Cellulare");
        headers.add("Email");
        headers.add("Email alternativa");
        headers.add("Cittadinanza");
        headers.add("Cittadinanza altro specificare");
        headers.add("Documento di identità numero");
        headers.add("Documento identità rilasciato da");
        headers.add("Documento identità scadenza");
        headers.add("N. permesso di soggiorno");
        headers.add("Permesso di soggiorno rilasciato da");
        headers.add("Scadenza titolo di soggiorno");
        headers.add("N. carta di soggiorno");
        headers.add("Carta di soggiorno rilasciato da");
        headers.add("Scadenza carta di soggiorno");
        headers.add("Indirizzo");
        headers.add("Città");
        headers.add("Provincia");
        headers.add("Nazione");
        headers.add("Cap");
        headers.add("Indirizzo domicilio");
        headers.add("Città domicilio");
        headers.add("Provincia domicilio");
        headers.add("Nazione");
        headers.add("Cap domicilio");
        headers.add("Titolo di studio");
        headers.add("Titolo di studio altro specificare");
        headers.add("Abitativo");
        headers.add("Condizione familiare");
        headers.add("Situazione lavorativa");
        headers.add("Situazione lavorativa altro specificare");
        HashMap<String, Object> map = new HashMap<>();
        if(idIncontroPreaccoglienza != null)
            map.put("idIncontroPreaccoglienza",idIncontroPreaccoglienza);
        if(idSoggettoAttuatore != null)
            map.put("idSoggettoAttuatore",idSoggettoAttuatore);
        List<MipTAnagraficaCittadino> anagraficaCittadinoList = mipTAnagraficaCittadinoRepository.getCittadinoPerIdIncontro(map);
        altriDati.put("[4]N. record", String.valueOf(anagraficaCittadinoList.size()));
        altriDati.put("[5] ", " ");
        altriDati.put("[6]CRITERI FILTRO", " ");
        if(incontroPreaccoglienza.getFlgIncontroErogatoDaRemoto()!=null){
            altriDati.put("[7]Sede incontro", "PRE ACCOGLIENZA ONLINE");
        }else{
            altriDati.put("[7]Sede incontro", incontroPreaccoglienza.getLuogoIncontro().getDenominazione());
        }
        altriDati.put("[8]Data incontro", outDateTime.format(incontroPreaccoglienza.getDataIncontro()));
        for (MipTAnagraficaCittadino anagraficaCittadino: anagraficaCittadinoList) {
            excelModelli.add(getExcelCittaAnagraModel(anagraficaCittadino));
        }

        return excelExport.addDataToWorkbook(excelModelli,headers,altriDati, "Anagrafica utenti");
    }


    private ExcelCittadinoAnagraficaModel getExcelCittaAnagraModel(MipTAnagraficaCittadino mipTAnagraficaCittadino){
        SimpleDateFormat outDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALY);
        String nonApplicabile = "[Non applicabile]";
        ExcelCittadinoAnagraficaModel excelCittadinoAnagraficaModel = new ExcelCittadinoAnagraficaModel();
        excelCittadinoAnagraficaModel.setNome(mipTAnagraficaCittadino.getMipTCittadino().getNome());
        excelCittadinoAnagraficaModel.setCognome(mipTAnagraficaCittadino.getMipTCittadino().getCognome());
        excelCittadinoAnagraficaModel.setDataDiNascita(mipTAnagraficaCittadino.getDataNascita());
        String cf = mipTAnagraficaCittadino.getMipTCittadino().getCodiceFiscale();
        excelCittadinoAnagraficaModel.setSesso(getSessoDaCF(cf));
        if(mipTAnagraficaCittadino.getComuneNascita()!=null){
            excelCittadinoAnagraficaModel.setLuogoDiNascita(mipTAnagraficaCittadino.getComuneNascita().getDescrizioneComune());
            excelCittadinoAnagraficaModel.setProvinciaDiNascita(mipTAnagraficaCittadino.getComuneNascita().getProvincia().getDescrizioneProvincia());
            excelCittadinoAnagraficaModel.setNazioneDiNascita("Italia");
        }else{
            excelCittadinoAnagraficaModel.setLuogoDiNascita(mipTAnagraficaCittadino.getDescrizioneCittaEsteraNascita());
            excelCittadinoAnagraficaModel.setProvinciaDiNascita(nonApplicabile);
            excelCittadinoAnagraficaModel.setNazioneDiNascita(mipTAnagraficaCittadino.getStatoEsteroNascita().getDescrizioneStato());
        }
        excelCittadinoAnagraficaModel.setCodiceFiscale(mipTAnagraficaCittadino.getMipTCittadino().getCodiceFiscale());
        excelCittadinoAnagraficaModel.setRecapitoTelefono(mipTAnagraficaCittadino.getRecapitoTelefono());
        excelCittadinoAnagraficaModel.setCellulare(mipTAnagraficaCittadino.getRecapitoTelefono2());
        excelCittadinoAnagraficaModel.setRecapitoEmail(mipTAnagraficaCittadino.getRecapitoEmail());
        excelCittadinoAnagraficaModel.setEmailAlternativa(mipTAnagraficaCittadino.getRecapitoEmail2());
        excelCittadinoAnagraficaModel.setCittadinanza(mipTAnagraficaCittadino.getCodiceCittadinanza() != null ? mipTAnagraficaCittadino.getCodiceCittadinanza().getDescrizione() : null);
        excelCittadinoAnagraficaModel.setCitadinanzaAltroSpecificare(mipTAnagraficaCittadino.getDescCittadinanzaAltro());
        excelCittadinoAnagraficaModel.setDocumentoIdentitaNumero(mipTAnagraficaCittadino.getNumDocumIdentita());
        excelCittadinoAnagraficaModel.setDocumentoIdentitaRilasciatoDa(mipTAnagraficaCittadino.getEnteRilascioDocumIdentita());
        excelCittadinoAnagraficaModel.setDocumentoIdentitaScadenza(mipTAnagraficaCittadino.getDataScadDocumIdentita()!=null?outDate.format(mipTAnagraficaCittadino.getDataScadDocumIdentita()):"");

        excelCittadinoAnagraficaModel.setTipoPermessoDiSoggiorno(mipTAnagraficaCittadino.getCodiceCittadinanza() != null && mipTAnagraficaCittadino.getCodiceCittadinanza().getCodiceNazionalita().getCodiceNazionalita().equals("3") ? mipTAnagraficaCittadino.getTipoPermessoDiSoggiorno() : "");
        excelCittadinoAnagraficaModel.setNumPermessoDiSoggiorno(mipTAnagraficaCittadino.getCodiceCittadinanza() != null && mipTAnagraficaCittadino.getCodiceCittadinanza().getCodiceNazionalita().getCodiceNazionalita().equals("3") ? mipTAnagraficaCittadino.getNumeroPermessoDiSoggiorno() : nonApplicabile);
        excelCittadinoAnagraficaModel.setPermessoDiSoggiornoRilasciatoDa(mipTAnagraficaCittadino.getEnteRilascioPermessoDiSoggiorno());
        excelCittadinoAnagraficaModel.setScadenzaTitoloDiSoggiorno(mipTAnagraficaCittadino.getDataScadPermessoSoggiorno()!=null?outDate.format(mipTAnagraficaCittadino.getDataScadPermessoSoggiorno()):"");

        excelCittadinoAnagraficaModel.setIndirizzoResidenza(mipTAnagraficaCittadino.getIndirizzoResidenza());
        if(mipTAnagraficaCittadino.getCodiceStatoEsteroResidenza()==null){
            excelCittadinoAnagraficaModel.setNazioneDiResidenza("Italia");
            excelCittadinoAnagraficaModel.setCittaResidenza(mipTAnagraficaCittadino.getComuneResidenza().getDescrizioneComune());
            excelCittadinoAnagraficaModel.setProvinciaDiResidenza(mipTAnagraficaCittadino.getComuneResidenza().getProvincia().getDescrizioneProvincia());
            excelCittadinoAnagraficaModel.setCapResidenza(mipTAnagraficaCittadino.getCapResidenza().toString());
        }else{
            //cercare codice stato stero
            excelCittadinoAnagraficaModel.setNazioneDiResidenza(mipTAnagraficaCittadino.getCodiceStatoEsteroResidenza().toString());
            excelCittadinoAnagraficaModel.setCittaResidenza(mipTAnagraficaCittadino.getDescrizioneCittaEsteraResidenza());
            excelCittadinoAnagraficaModel.setProvinciaDiResidenza(nonApplicabile);
            excelCittadinoAnagraficaModel.setCapResidenza(nonApplicabile);
        }

        if(mipTAnagraficaCittadino.getComuneDomicilio()!=null) {
            excelCittadinoAnagraficaModel.setIndirizzoDomicilio(mipTAnagraficaCittadino.getIndirizzoDomicilio());
            excelCittadinoAnagraficaModel.setCittaDomicilio(mipTAnagraficaCittadino.getComuneDomicilio().getDescrizioneComune());
            excelCittadinoAnagraficaModel.setProvinciaDiDomicilio(mipTAnagraficaCittadino.getComuneDomicilio().getProvincia().getDescrizioneProvincia());
            excelCittadinoAnagraficaModel.setCapDomicilio(mipTAnagraficaCittadino.getCapDomicilio().toString());
        }else{
            excelCittadinoAnagraficaModel.setIndirizzoDomicilio(excelCittadinoAnagraficaModel.getIndirizzoResidenza());
            excelCittadinoAnagraficaModel.setCittaDomicilio(excelCittadinoAnagraficaModel.getCittaResidenza());
            excelCittadinoAnagraficaModel.setProvinciaDiDomicilio(excelCittadinoAnagraficaModel.getProvinciaDiResidenza());
            excelCittadinoAnagraficaModel.setCapDomicilio(excelCittadinoAnagraficaModel.getCapResidenza());
        }
        excelCittadinoAnagraficaModel.setTitoloDiStudio(mipTAnagraficaCittadino.getCodiceTitoloDiStudio() != null ? mipTAnagraficaCittadino.getCodiceTitoloDiStudio().getDescrizioneTitoloDiStudio() : null);
        excelCittadinoAnagraficaModel.setTitoloDiStudioAltroSpecificare(mipTAnagraficaCittadino.getTitoloDiStudioAltro());
        AnagraficaCittadino anagraficaCittadinoVo = getAnagraficaExtend(mipTAnagraficaCittadino.getId());


        excelCittadinoAnagraficaModel.setSvantaggioAbitativo(anagraficaCittadinoVo.getSvantaggioAbitativo() != null ? anagraficaCittadinoVo.getSvantaggioAbitativo().getDescrizioneSvantaggioAbitativo(): null);
        excelCittadinoAnagraficaModel.setCondizioneFamiliare(anagraficaCittadinoVo.getCondizioneFamiliare() != null ? anagraficaCittadinoVo.getCondizioneFamiliare().getDescrCondizioneFamiliare(): null );
        excelCittadinoAnagraficaModel.setCondizioneOccupazionale(anagraficaCittadinoVo.getCodiceCondizioneOccupazionale() != null ? anagraficaCittadinoVo.getCodiceCondizioneOccupazionale().getDescrizioneCondizioneOccupazionale(): null);
        excelCittadinoAnagraficaModel.setSituazioneLavorativaAltroSpecificare(anagraficaCittadinoVo.getCondizioneOccupazionaleAltro() != null ? anagraficaCittadinoVo.getCondizioneOccupazionaleAltro(): null);
        return excelCittadinoAnagraficaModel;
    }

    private AnagraficaCittadino getAnagraficaExtend(Long idCittadino){
        MipTAnagraficaCittadinoExten anagraficaCittadinoExten = anagraficaCittadinoExtenRepository.findById(idCittadino);
        AnagraficaCittadino anagraficaCittadinoVo = new AnagraficaCittadino();
        if(anagraficaCittadinoExten != null){
            if(anagraficaCittadinoExten.getCondizioneOccupazionale()!=null) {
                String codiceCondizioneOccupazionale = anagraficaCittadinoExten.getCondizioneOccupazionale();
                CondizioneOccupazionale condizioneOccupazionale = genericMapper.condizioneOccupazionaleEntyToVo(condizioneOccupazionaleRepository.find("codiceCondizioneOccupazionale", codiceCondizioneOccupazionale).firstResult());
                anagraficaCittadinoVo.setCodiceCondizioneOccupazionale(condizioneOccupazionale);
            }
            if(anagraficaCittadinoExten.getSvantaggioAbitativo()!=null) {
                Long idSvantaggioAbitativo = (new BigDecimal(anagraficaCittadinoExten.getSvantaggioAbitativo())).longValue();
                SvantaggioAbitativo svantaggioAbitativo = genericMapper.svantaggioAbitativoEntyToVo(svantaggioAbitativoRepository.find("id",idSvantaggioAbitativo).firstResult());
                anagraficaCittadinoVo.setSvantaggioAbitativo(svantaggioAbitativo);
            }
            if(anagraficaCittadinoExten.getCondizioneFamiliare()!=null && !anagraficaCittadinoExten.getCondizioneFamiliare().isEmpty()) {
                Long idCondizioneFamiliare = (new BigDecimal(new String(anagraficaCittadinoExten.getCondizioneFamiliare()))).longValue();
                CondizioneFamiliare condizioneFamiliare = genericMapper.condizioneFamiliareEntyToVo(condizioneFamiliareRepository.findById(idCondizioneFamiliare));
                anagraficaCittadinoVo.setCondizioneFamiliare(condizioneFamiliare);
            }
            if(anagraficaCittadinoExten.getCondizioneOccupazionaleAltro()!=null) {
                String  condiString = anagraficaCittadinoExten.getCondizioneOccupazionaleAltro();
                anagraficaCittadinoVo.setCondizioneOccupazionaleAltro(condiString);
            }
        }else {
            CondizioneOccupazionale condizioneOccupazionale = new CondizioneOccupazionale();
            SvantaggioAbitativo svantaggioAbitativo = new SvantaggioAbitativo();
            condizioneOccupazionale.setDescrizioneCondizioneOccupazionale("");
            svantaggioAbitativo.setDescrizioneSvantaggioAbitativo("");
            anagraficaCittadinoVo.setSvantaggioAbitativo(svantaggioAbitativo);
            anagraficaCittadinoVo.setCodiceCondizioneOccupazionale(condizioneOccupazionale);
            CondizioneFamiliare condizioneFamiliare = new CondizioneFamiliare();
            condizioneFamiliare.setDescrCondizioneFamiliare("");
            anagraficaCittadinoVo.setCondizioneFamiliare(condizioneFamiliare);
            anagraficaCittadinoVo.setCondizioneOccupazionaleAltro("");

        }
        return anagraficaCittadinoVo;
    }

    private String getSessoDaCF(String cf){
        String sesso = "[N/A]";
        if(cf.length()>10){
            try{
                int number = Integer.parseInt(cf.substring(9,11));
                sesso = number > 40 ? "F": "M";
            }
            catch (NumberFormatException ex){
                ex.printStackTrace();
                throw new NumberFormatException();
            }
        }
        return sesso;
    }

    public Response getExportIncontriPreaccoglienzaAnagrafica(String idIncontriPreaccoglienza,
                                                              String codOperatore, String idSoggettoAttuatore,
                                                              SecurityContext securityContext, HttpHeaders httpHeaders,
                                                              HttpServletRequest httpRequest){
        List<Long> list =  Arrays.stream(idIncontriPreaccoglienza.split(",")).map(Long::parseLong).toList();
        SimpleDateFormat outDateTime = new SimpleDateFormat("dd-MM-yyyy HH.mm", Locale.ITALY);
        ExcelExport excelExport =new ExcelExport();
        Workbook workbook = new XSSFWorkbook();
        for (Long Item: list) {
            List<ExcelCittadinoAnagraficaModel> excelModelli= new ArrayList<>();
            MipTIncontroPreaccoglienza incontroPreaccoglienza = mipTIncontroPreaccoglienzaRepository.findById(Item);

            HashMap<String, Object> map = new HashMap<>();
            if(Item != null)
                map.put("idIncontroPreaccoglienza",Item);
            if(idSoggettoAttuatore != null)
                map.put("idSoggettoAttuatore",idSoggettoAttuatore);
            List<MipTAnagraficaCittadino> anagraficaCittadinoList = mipTAnagraficaCittadinoRepository.getCittadinoPerIdIncontro(map);

            int riga = 0;
            Sheet dataSheet = workbook.createSheet(incontroPreaccoglienza.getId()+" "+outDateTime.format(incontroPreaccoglienza.getDataIncontro()));
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setWrapText(true);
            dataStyle.setVerticalAlignment(VerticalAlignment.TOP);
            dataStyle.setShrinkToFit(true);
            SortedMap<String, String> altriDati = getAltridatiAnagrafiche(codOperatore,anagraficaCittadinoList.size(),incontroPreaccoglienza);
            for (String key : altriDati.keySet()) {
                Row row = dataSheet.createRow(riga);
                Cell label = row.createCell(0);
                label.setCellValue(key.split("]")[1]);

                Cell valore = row.createCell(1);
                valore.setCellValue(altriDati.get(key));
                riga++;
            }
            riga++;
            dataSheet = excelExport.inizializzaHeaders(dataSheet, HEADERANAGRAFICA, riga);
            for (MipTAnagraficaCittadino anagraficaCittadino: anagraficaCittadinoList) {
                excelModelli.add(getExcelCittaAnagraModel(anagraficaCittadino));
            }
            for (int i = 1; i <= excelModelli.size(); i++) {
                Row row = dataSheet.createRow(i + riga);
                if (excelModelli.get(i - 1) != null) {
                    CellStyle dateCellStyle = workbook.createCellStyle();
                    CreationHelper creationHelper = workbook.getCreationHelper();
                    dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
                    excelExport.inserisciDatoInRigaCittadinoAnagrafica(excelModelli.get(i - 1), row, dateCellStyle);
                }
                row.setRowStyle(dataStyle);
            }
            for (int i = 0; i < HEADERANAGRAFICA.size(); i++) {
                int size = dataSheet.getRow(riga).getCell(i).getStringCellValue().length();
                if (size < 25) {
                    size = 25;
                }
                dataSheet.setColumnWidth(i, (size + 2) * 256);
            }
        }
        return Response.ok(excelExport.getBytes(workbook)).build();
    }

    private SortedMap<String, String> getAltridatiAnagrafiche(String codOperatore, int size,
                                                              MipTIncontroPreaccoglienza incontroPreaccoglienza) {
        SortedMap<String,String> altriDati = new TreeMap<>();
        SimpleDateFormat outDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.ITALY);

        altriDati.put("[1]Report","Cittadino - Cittadino incontro di pre-accoglienza");
        altriDati.put("[2]Operatore", codOperatore);
        altriDati.put("[3]Creato il",outDateTime.format(mipRIdeaDiImpresaCittadinoRepository.getNow()));
        altriDati.put("[4]N. record", String.valueOf(size));
        altriDati.put("[5] ", " ");
        altriDati.put("[6]CRITERI FILTRO", " ");
        if(incontroPreaccoglienza.getFlgIncontroErogatoDaRemoto()!=null){
            altriDati.put("[7]Sede incontro", "PRE ACCOGLIENZA ONLINE");
        }else{
            altriDati.put("[7]Sede incontro", incontroPreaccoglienza.getLuogoIncontro().getDenominazione());
        }
        altriDati.put("[8]Data incontro", outDateTime.format(incontroPreaccoglienza.getDataIncontro()));
        return altriDati;
    }
    public static final List<String> HEADERANAGRAFICA =Arrays.asList(
            "Cognome", "Nome", "Sesso", "Data di nascita", "Luogo di nascita",
            "Provincia di nascita", "Nazione di nascita", "Codice fiscale", "Telefono fisso",
            "Cellulare", "Email", "Email alternativa", "Cittadinanza",
            "Cittadinanza altro specificare", "Documento di identità numero",
            "Documento identità rilasciato da", "Documento identità scadenza",
            "N. permesso di soggiorno", "Permesso di soggiorno rilasciato da",
            "Scadenza titolo di soggiorno","N. carta di soggiorno","Carta di soggiorno rilasciato da","Scadenza carta di soggiorno", "Indirizzo", "Città", "Provincia",
            "Nazione", "Cap", "Indirizzo domicilio", "Città domicilio",
            "Provincia domicilio", "Nazione", "Cap domicilio", "Titolo di studio",
            "Titolo di studio altro specificare", "Abitativo", "Condizione familiare",
            "Situazione lavorativa", "Situazione lavorativa altro specificare"
                                                                    );

}
