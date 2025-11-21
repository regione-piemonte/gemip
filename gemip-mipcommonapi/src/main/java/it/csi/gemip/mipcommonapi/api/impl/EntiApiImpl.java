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

import it.csi.gemip.mipcommonapi.api.EntiApi;
import it.csi.gemip.mipcommonapi.api.dto.Ente;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDEnte;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDEnteRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDOperatoreRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntiApiImpl extends ParentApiImpl implements EntiApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDEnteRepository mipDEnteRepository;

    @Inject
    MipDOperatoreRepository mipDOperatoreRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    @Override
    public Response getEnti(String tipoEnte, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<Ente> enteList = new ArrayList<>();
        List<MipDEnte> mipDEnteList = mipDEnteRepository.findAll().list();
        if (tipoEnte == null) {
            enteList = mipDEnteRepository.findAll().list()
                    .stream().map(from -> genericMapper.enteToVo(from))
                    .toList();
            return Response.ok(enteList).build();
        }
        for (MipDEnte mipDEnte : mipDEnteList) {
            if (mipDEnte.getGruppoOperatore().equals("A") && mipDEnte.getCodOperatore().intValue() == 1535
                    && tipoEnte.equals("apl")) {
                enteList.add(genericMapper.enteToVo(mipDEnte));
            }
            if (mipDEnte.getGruppoOperatore().equals("A") && mipDEnte.getCodOperatore().intValue() == 1
                    && tipoEnte.equals("regione")) {
                enteList.add(genericMapper.enteToVo(mipDEnte));
            }
        }
        return Response.ok(enteList).build();
    }

    @Override
    public Response getOperatoreEnte(Long idEnte, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<Operatore> operatoreList = mipDOperatoreRepository.getOperatoriByIdEnte(idEnte)
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();
        return Response.ok(operatoreList).build();
    }

    @Override
    @Transactional
    public Response updateEnte(Ente body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipDEnte tmp = mipDEnteRepository.findById(body.getId());
        tmp.setDataAggiorn(mipDEnteRepository.getNow());
        tmp.setCodUserAggiorn(body.getCodUserAggiorn());
        tmp.setTelefono(body.getTelefono());
        tmp.setEmail(body.getEmail());
        mipDEnteRepository.getEntityManager().merge(tmp);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateEnte",
                tmp,
                body,
                httpRequest);

        return Response.ok().build();
    }
}
