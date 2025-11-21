package it.csi.gemip.mipbackofficebff.bff.impl;

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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.gemip.mipbackofficebff.bff.FileIcsApi;
import it.csi.gemip.mipbackofficebff.bff.dto.FileIcs;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.entities.MipROperatoreSoggAttuatore;
import it.csi.gemip.mipbackofficebff.entities.MipTEventoCalendario;
import it.csi.gemip.mipbackofficebff.entities.MipTFileIc;
import it.csi.gemip.mipbackofficebff.repositories.MipTEventoCalendarioRepository;
import it.csi.gemip.mipbackofficebff.repositories.MipTFileIcRepository;
import it.csi.gemip.mipbackofficebff.utils.LogAuditUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;
public class FileIcsApiImpl extends ParentApiImpl implements FileIcsApi {

    @Inject
    MipTFileIcRepository fileIcRepository;

    @Inject
    MipTEventoCalendarioRepository eventoCalendarioRepository;

    @Inject
    LogAuditUtil logAuditUtil;
    

    @Override
    public Response getEventiCalendario(String dataDa, String dataA, Long idOperatore, Long idSoggettoAttuatore, Integer pageIndex, Integer pageSize, String sortDirection,String orderBy, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getFileIcsById(Long idFileIcs, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getStoricoCalendario(Long idSoggettoAttuatore, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getStoricoFileIcsById(Long idFileIcs, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    @Transactional
    @RolesAllowed({"GEMIP_AFFIDATARIO_ALL", "GEMIP_REGIONALE_ALL", "GEMIP_ATTUATORE_BASE"})
    public Response insertFileIcs(MultipartFormDataInput input, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest)  {

        try {

            MipROperatoreSoggAttuatore operatoreSoggAttuatore = (MipROperatoreSoggAttuatore) MipROperatoreSoggAttuatore.find("operatore.id = ?1 and soggettoAttuatore.id=?2",Long.parseLong(input.getFormDataMap().get("idOperatore").get(0).getBodyAsString()), Long.parseLong(input.getFormDataMap().get("idSoggettoAttuatore").get(0).getBodyAsString())).list().get(0);
            Date adesso=fileIcRepository.getNow();

            String codeUser=input.getFormDataMap().get("codUserInserim").get(0).getBodyAsString();
            MipTFileIc mipTFileIc = createMipTFileIc(input, codeUser, operatoreSoggAttuatore, adesso);

            InputStream inputStream = input.getFormDataMap().get("fileIcs").get(0).getBody(InputStream.class, null);

            Long idFileIcs = mipTFileIc.getIdFileIcs();

            CalendarBuilder builder = new CalendarBuilder();
            net.fortuna.ical4j.model.Calendar calendar = builder.build(inputStream);
            ArrayList<Long> idEventiList = new ArrayList<>();

            for (Component component:  calendar.getComponents(Component.VEVENT)) {

                VEvent vEvent = (VEvent)component;

                MipTEventoCalendario mipTEventoCalendario = new MipTEventoCalendario();
                mipTEventoCalendario.setIdFileIcs(idFileIcs);
                mipTEventoCalendario.setDescrizioneEvento("descrizione mancante"); // chiesto espressamente da un utente...
                mipTEventoCalendario.setDatoUid("-");
                mipTEventoCalendario.setLuogo("-");
                mipTEventoCalendario.setTitolo("-");
                mipTEventoCalendario.setCodUserAggiorn(codeUser);
                mipTEventoCalendario.setCodUserInserim(codeUser);
                mipTEventoCalendario.setDataInserim(adesso);
                mipTEventoCalendario.setDataAggiorn(adesso);

                if (vEvent.getStartDate() != null) {
                    // campo DTSTART
                    // dovrebbe tenere conto anche di TZID e X-WR-TIMEZONE
                    mipTEventoCalendario.setDataOraInizio(vEvent.getStartDate().getDate());
                }
                if (vEvent.getEndDate() != null) {
                    // campo DTEND oppure DURATION
                    // dovrebbe tenere conto anche di TZID e X-WR-TIMEZONE
                    mipTEventoCalendario.setDataOraFine(vEvent.getEndDate().getDate());
                }

                // campo UID
                String uid = notBlank(vEvent.getUid() != null ? vEvent.getUid().getValue() : null);
                if (uid != null) {
                    mipTEventoCalendario.setDatoUid(stringTroncate(uid, 200));
                }

                // campo SUMMARY
                String summary = notBlank(vEvent.getSummary() != null ? vEvent.getSummary().getValue() : null);
                if (summary != null) {
                    mipTEventoCalendario.setTitolo(stringTroncate(summary, 250));
                }

                // campo LOCATION
                String location = notBlank(vEvent.getLocation() != null ? vEvent.getLocation().getValue() : null);
                if (location != null) {
                    mipTEventoCalendario.setLuogo(stringTroncate(location, 250));
                }

                // campo DESCRIPTION
                String description = notBlank(vEvent.getDescription() != null ? vEvent.getDescription().getValue() : null);
                if (description != null) {
                    mipTEventoCalendario.setDescrizioneEvento(stringTroncate(description, 500));
                }

                eventoCalendarioRepository.persist(mipTEventoCalendario);
                idEventiList.add(mipTEventoCalendario.getIdEventoCalendario());

               
            }

            logAuditUtil.insertLogAudit(
                adesso,
                ((UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR)).getCodFisc(),
                getIncomingIPAddress(httpRequest),
                "insertFileIcs",
                new StringBuilder("fileIcsId : "+mipTFileIc.getIdFileIcs()+" idEventi : "+idEventiList.toString()).toString(),
                httpRequest);
            
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserException e) {
            throw new RuntimeException(e);
        }
        

        return Response.ok().build();
    }

    private String notBlank(String s) {
        return s == null || s.trim().isEmpty() ? null : s.trim();
    }

    private MipTFileIc createMipTFileIc(MultipartFormDataInput input, String codeUser,
            MipROperatoreSoggAttuatore operatoreSoggAttuatore, Date adesso) throws IOException {
        MipTFileIc mipTFileIc = new MipTFileIc();
        mipTFileIc.setNomeFile(input.getFormDataMap().get("nomeFile").get(0).getBodyAsString());
        mipTFileIc.setDescrizioneFile(input.getFormDataMap().get("nomeFile").get(0).getBodyAsString());
        mipTFileIc.setOperatoreSoggettoAttuatore(operatoreSoggAttuatore);
        mipTFileIc.setCodUserInserim(codeUser);
        mipTFileIc.setCodUserAggiorn(codeUser);
        mipTFileIc.setDataInserim(adesso);
        mipTFileIc.setDataAggiorn(adesso);

        File file = input.getFormDataMap().get("fileIcs").get(0).getBody(File.class, null);
        mipTFileIc.setFileIcsByte(FileUtils.readFileToByteArray(file));

        fileIcRepository.persist(mipTFileIc);
        return mipTFileIc;
    }

    @Override
    public Response updateFileIcs(FileIcs body, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamatePut(uriInfo,body);
    }

    private String stringTroncate (String s, int n){
        return s.length() > n ? s.substring(0,n) : s;
    }
}
