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

import java.util.Date;
import java.util.List;

import io.quarkus.cache.CacheResult;
import it.csi.gemip.mipcommonapi.api.SoggettiAttuatoreApi;
import it.csi.gemip.mipcommonapi.api.dto.Ente;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAffidatario;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatore;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatoreOperatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAttuatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipROperatoreSoggAttuatore;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDOperatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipROperatoreEnteRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipROperatoreSoggAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRSoggettoAffidatariorepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class SoggettiAttuatoreApiImpl extends ParentApiImpl implements SoggettiAttuatoreApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDSoggettoAttuatoreRepository soggettoAttuatoreRepository;

    @Inject
    MipDOperatoreRepository operatoreRepository;

    @Inject
    MipROperatoreSoggAttuatoreRepository operatoreSoggAttuatoreRepository;

    @Inject
    MipRSoggettoAffidatariorepository mipRSoggettoAffidatariorepository;

    @Inject
    MipROperatoreEnteRepository mipROperatoreEnteRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    @Transactional
    @Override
    public Response cambiaStatoOperatoriSoggettoAttuatore(Long idSoggettoAttuatore, Long idOperatore,
            Long idOperatoreDisabilitazione, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipROperatoreSoggAttuatore mipROperatoreSoggAttuatore = operatoreSoggAttuatoreRepository
                .find("operatore.id = ?1 and soggettoAttuatore.id = ?2", idOperatore, idSoggettoAttuatore)
                .firstResult();
        Date adesso = getNow();
        if (mipROperatoreSoggAttuatore.getDataDisabilitazione() == null) {
            mipROperatoreSoggAttuatore.setDataDisabilitazione(adesso);
            mipROperatoreSoggAttuatore.setIdOperatoreDisabilitazione(idOperatoreDisabilitazione);
        } else {
            mipROperatoreSoggAttuatore.setDataDisabilitazione(null);
            mipROperatoreSoggAttuatore.setIdOperatoreDisabilitazione(null);
        }
        mipROperatoreSoggAttuatore.setDataAggiorn(adesso);
        operatoreSoggAttuatoreRepository.getEntityManager().merge(mipROperatoreSoggAttuatore);
        SoggettoAttuatoreOperatore soggettoAttuatoreOperatore = genericMapper
                .soggettoAttuatoreOperatoreToVo(mipROperatoreSoggAttuatore);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "cambiaStatoOperatoriSoggettoAttuatore",
                mipROperatoreSoggAttuatore,
                null,
                httpRequest);

        return Response.ok(soggettoAttuatoreOperatore).build();
    }

    @Override
    public Response getOperatoriByIdSoggettoAttuatore(Long idSoggettoAttuatore, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Operatore> operatoreList = operatoreRepository.getOperatoriByIdSoggettoAttuatore(idSoggettoAttuatore)
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();
        return Response.ok(operatoreList).build();
    }

    @Override
    @CacheResult(cacheName = "getSoggettiAttuatori", keyGenerator = MethodNameKeyGenerator.class)
    public Response getSoggettiAttuatori(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<SoggettoAttuatore> soggettoAttuatoreList = soggettoAttuatoreRepository.listAll()
                .stream().map(from -> genericMapper.soggettoAttuatoreToVo(from))
                .toList();
        return Response.ok(soggettoAttuatoreList).build();
    }

    @Override
    public Response getSoggettoAttuatoreByIdOperatori(Long idOperatore, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<SoggettoAttuatore> soggettoAttuatoreList = soggettoAttuatoreRepository
                .getSoggettoAttuatoreByIdOperatori(idOperatore)
                .stream().map(from -> genericMapper.soggettoAttuatoreToVo(from))
                .toList();

        if (!soggettoAttuatoreList.isEmpty()) {
            return Response.ok(soggettoAttuatoreList).build();
        } else {
            List<SoggettoAffidatario> soggettoAffidatarioList = mipRSoggettoAffidatariorepository
                    .getByidOperatore(idOperatore).stream()
                    .map(r -> genericMapper.soggettoAffidatarioToVo(r.getIdSoggettoAffidatario())).toList();
            if (!soggettoAffidatarioList.isEmpty()) {
                return Response.ok(soggettoAffidatarioList).build();
            } else {
                List<Ente> entiList = mipROperatoreEnteRepository.getByIdOperatore(idOperatore).stream()
                        .map(r -> genericMapper.enteToVo(r.getIdEnte())).toList();
                return Response.ok(entiList).build();
            }
        }

    }

    @Override
    @Transactional
    public Response updateSoggettoAttuatore(SoggettoAttuatore body, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipDSoggettoAttuatore tmp = soggettoAttuatoreRepository.findById(body.getId());
        tmp.setDataAggiorn(soggettoAttuatoreRepository.getNow());
        tmp.setCodUserAggiorn(body.getCodUserAggiorn());
        tmp.setTelefono(body.getTelefono());
        tmp.setEmail(body.getEmail());
        soggettoAttuatoreRepository.getEntityManager().merge(tmp);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateSoggettoAttuatore",
                tmp,
                body,
                httpRequest);

        return Response.ok().build();
    }
}
