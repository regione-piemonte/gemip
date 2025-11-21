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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.api.FileIcsApi;
import it.csi.gemip.mipcommonapi.api.dto.EventoCalendario;
import it.csi.gemip.mipcommonapi.api.dto.EventoCalendarioRicerca;
import it.csi.gemip.mipcommonapi.api.dto.EventoCalendarioWrapper;
import it.csi.gemip.mipcommonapi.api.dto.FileIcs;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatore;
import it.csi.gemip.mipcommonapi.api.dto.StoricoCalendario;
import it.csi.gemip.mipcommonapi.integration.entities.MipTEventoCalendario;
import it.csi.gemip.mipcommonapi.integration.entities.MipTFileIc;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTEventoCalendarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTFileIcRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.Convert;
import it.csi.gemip.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class FileIcsApiImpl extends ParentApiImpl implements FileIcsApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipTFileIcRepository fileIcRepository;

    @Inject
    MipTEventoCalendarioRepository eventoCalendarioRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    public Response getEventiCalendario(String dataDa, String dataA, Long idOperatore, Long idSoggettoAttuatore,
            Integer pageIndex, Integer pageSize, String sortDirection, String orderBy, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();
        if (dataDa != null) {
            params.put("dataDa", Convert.convertStringToDate(dataDa));

        }
        if (dataA != null) {
            params.put("dataA", Convert.convertStringToDate(dataA));
        }
        if (idOperatore != null) {
            params.put("idOperatore", idOperatore);
        }
        if (idSoggettoAttuatore != null) {
            params.put("idSoggettoAttuatore", idSoggettoAttuatore);
        }
        orderBy = orderBy != null ? orderBy : "data";
        sortDirection = sortDirection != null ? sortDirection : "asc";
        PanacheQuery<MipTEventoCalendario> query = eventoCalendarioRepository
                .getEventiCalendarioPerParametri((HashMap) params, orderBy, sortDirection);
        List<EventoCalendario> eventoCalendarioList;
        if (pageIndex != null && pageSize != null) {
            eventoCalendarioList = query.page(pageIndex, pageSize).list()
                    .stream().map(from -> genericMapper.eventoCalendarioToVo(from))
                    .toList();
        } else {
            eventoCalendarioList = query.list()
                    .stream().map(from -> genericMapper.eventoCalendarioToVo(from))
                    .toList();
        }
        EventoCalendarioRicerca response = new EventoCalendarioRicerca();
        List<EventoCalendarioWrapper> eventoCalendarioWrapperList = new ArrayList<>();
        for (EventoCalendario evento : eventoCalendarioList) {
            MipTFileIc file = fileIcRepository.findById(evento.getIdFileIcs());
            Operatore operatore = genericMapper.operatoreToVo(file.getOperatoreSoggettoAttuatore().getOperatore());
            SoggettoAttuatore soggettoAttuatore = genericMapper
                    .soggettoAttuatoreToVo(file.getOperatoreSoggettoAttuatore().getSoggettoAttuatore());
            EventoCalendarioWrapper tmp = new EventoCalendarioWrapper();
            tmp.setEvento(evento);
            tmp.setOperatore(operatore);
            tmp.setSoggettoAttuatore(soggettoAttuatore);
            eventoCalendarioWrapperList.add(tmp);

        }
        response.setEventiCalendario(eventoCalendarioWrapperList);
        response.setTotRis(query.count());
        return Response.ok(response).build();
    }

    @Override
    public Response getFileIcsById(Long idFileIcs, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipTFileIc fileIc = fileIcRepository.findById(idFileIcs);
        byte[] response = fileIc.getFileIcsByte();

        return Response.ok(response).build();
    }

    @Override
    public Response getStoricoCalendario(Long idSoggettoAttuatore, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<StoricoCalendario> response = new ArrayList<>();
        List<MipTFileIc> fileIcs;
        if (idSoggettoAttuatore == null) {
            fileIcs = fileIcRepository.findAll(Sort.descending("dataInserim")).list();
        } else {
            fileIcs = fileIcRepository.find("operatoreSoggettoAttuatore.soggettoAttuatore.id = ?1", idSoggettoAttuatore)
                    .list();
        }

        for (MipTFileIc fileic : fileIcs) {
            StoricoCalendario tmp = genericMapper.fileIcsToVo(fileic);
            tmp.setIdDocumentoIcs(fileic.getIdFileIcs());
            tmp.setOperatoreInserimento(
                    genericMapper.operatoreToVo(fileic.getOperatoreSoggettoAttuatore().getOperatore()));
            tmp.setSoggettoAttuatore(
                    genericMapper.soggettoAttuatoreToVo(fileic.getOperatoreSoggettoAttuatore().getSoggettoAttuatore()));

            Long nEventi = eventoCalendarioRepository.count("idFileIcs = ?1", fileic.getIdFileIcs());
            tmp.setNumeroEventi(nEventi);
            response.add(tmp);
        }

        return Response.ok(response).build();
    }

    @Override
    public Response getStoricoFileIcsById(Long idFileIcs, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipTFileIc mipTFileIc = fileIcRepository.findById(idFileIcs);
        StoricoCalendario res = genericMapper.fileIcsToVo(mipTFileIc);
        res.setNumeroEventi(eventoCalendarioRepository.count("idFileIcs = ?1", idFileIcs));
        res.setFlgAbilitato("S".equals(mipTFileIc.getFlgAbilitato()));
        return Response.ok(res).build();
    }

    @Override
    @Transactional
    public Response updateFileIcs(FileIcs body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTFileIc mipTFileIc = fileIcRepository.findById(body.getIdFileIcs());

        mipTFileIc.setFlgAbilitato(body.isFlgAbilitato() ? "S" : null);
        mipTFileIc.setDescrizioneFile(body.getDescrizioneFile());
        mipTFileIc.setDataAggiorn(getNow());
        fileIcRepository.getEntityManager().merge(mipTFileIc);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateFileIcs",
                mipTFileIc,
                body,
                httpRequest);

        return Response.ok().build();
    }

}
