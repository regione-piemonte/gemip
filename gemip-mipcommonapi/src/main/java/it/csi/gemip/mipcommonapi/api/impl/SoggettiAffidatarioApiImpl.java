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

import it.csi.gemip.mipcommonapi.api.SoggettiAffidatarioApi;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAffidatario;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAffidatario;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDOperatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAffidatarioRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.Date;
import java.util.List;

import io.quarkus.cache.CacheResult;

public class SoggettiAffidatarioApiImpl implements SoggettiAffidatarioApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDOperatoreRepository operatoreRepository;

    @Inject
    MipDSoggettoAffidatarioRepository soggettoAffidatarioRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    @Override
    public Response getOperatoreSoggettoAffidatario(Integer idSoggettoAffidatario, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Operatore> operatoreList = operatoreRepository.getOperatoriSoggettiAffidatari(idSoggettoAffidatario)
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();
        return Response.ok(operatoreList).build();
    }

    @Override
    @CacheResult(cacheName = "getSoggettiAffidatario", keyGenerator = MethodNameKeyGenerator.class)
    public Response getSoggettiAffidatario(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<SoggettoAffidatario> soggettoAffidatarioList = soggettoAffidatarioRepository.findAll().list()
                .stream().map(from -> genericMapper.soggettoAffidatarioToVo(from))
                .toList();
        return Response.ok(soggettoAffidatarioList).build();
    }

    @Override
    @Transactional
    public Response updateSoggettoAffidatario(SoggettoAffidatario body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = new Date();
        MipDSoggettoAffidatario tmp = soggettoAffidatarioRepository.findById(body.getId());
        tmp.setDataAggiorn(soggettoAffidatarioRepository.getNow());
        tmp.setCodUserAggiorn(body.getCodUserAggiorn());
        tmp.setTelefono(body.getTelefono());
        tmp.setEmail(body.getEmail());
        soggettoAffidatarioRepository.getEntityManager().merge(tmp);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateSoggettoAffidatario",
                tmp,
                body,
                httpRequest);

        return Response.ok().build();
    }
}
