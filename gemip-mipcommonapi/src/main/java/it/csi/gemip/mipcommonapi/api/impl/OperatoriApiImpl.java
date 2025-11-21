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

import it.csi.gemip.mipcommonapi.api.OperatoriApi;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.OperatoreIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.SoggettoAttuatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDOperatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAffidatario;
import it.csi.gemip.mipcommonapi.integration.entities.MipROperatoreIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.entities.MipROperatoreIncontroPreaccoglienzaId;
import it.csi.gemip.mipcommonapi.integration.entities.MipROperatoreSoggAttuatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDOperatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAffidatarioRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipROperatoreIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipROperatoreSoggAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class OperatoriApiImpl extends ParentApiImpl implements OperatoriApi {

    @Inject
    GenericMapper genericMapper;

    @Inject
    MipDOperatoreRepository operatoreRepository;

    @Inject
    MipROperatoreSoggAttuatoreRepository operatoreSoggAttuatoreRepository;

    @Inject
    MipTIncontroPreaccoglienzaRepository incontroPreaccoglienzaRepository;

    @Inject
    MipROperatoreIncontroPreaccoglienzaRepository operatoreIncontroPreaccoglienzaRepository;
    @Inject
    LogAuditUtil logAuditUtil;
    @Inject
    MipDSoggettoAffidatarioRepository soggettoAffidatarioRepository;

    @Transactional
    @Override
    public Response deleteOperatoreIncontroPreaccoglienza(Long idOperatore, Long idIncontroPreaccoglienza,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipROperatoreIncontroPreaccoglienza operatoreIncontroPreaccoglienza = operatoreIncontroPreaccoglienzaRepository
                .getOperatoreIncontroPreaccoglienzaPerId(idOperatore, idIncontroPreaccoglienza);
        operatoreIncontroPreaccoglienza.delete();
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "deleteOperatoreIncontroPreaccoglienza",
                operatoreIncontroPreaccoglienza,
                null,
                httpRequest);
        return Response.ok().build();
    }

    @Override
    public Response getOperatoreById(Long idOperatore, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        return Response.ok(genericMapper.operatoreToVo(operatoreRepository.findById(idOperatore))).build();
    }

    @Override
    public Response getOperatori(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<Operatore> operatoreList = operatoreRepository.listAll()
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();
        return Response.ok(operatoreList).build();
    }

    @Override
    public Response getOperatoriIncontroPreaccoglienza(Long idIncontroPreacc, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Operatore> operatoreList = operatoreRepository.getOperatoriIncontroPreaccoglienza(idIncontroPreacc)
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();
        return Response.ok(operatoreList).build();
    }

    @Override
    public Response getOperatoriRicerca(String nome, String cognome, String email, String codiceFiscale,
            Long idOperatore, String soggetto, String abilitato, Long idSoggettoAttuatore,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();

        if (nome != null) {
            nome = nome.trim();
            params.put("nome", "%" + nome.toLowerCase() + "%");
        }
        if (cognome != null) {
            cognome = cognome.trim();
            params.put("cognome", "%" + cognome.toLowerCase() + "%");
        }
        if (email != null) {
            email = email.trim();
            params.put("email", "%" + email.toLowerCase() + "%");
        }
        if (codiceFiscale != null) {
            codiceFiscale = codiceFiscale.trim().replace(" ", "");
            params.put("codiceFiscale", "%" + codiceFiscale.toLowerCase() + "%");
        }
        if (idOperatore != null) {
            params.put("idOperatore", idOperatore);
        }
        if (idSoggettoAttuatore != null) {
            params.put("idSoggettoAttuatore", idSoggettoAttuatore);
        }

        List<Operatore> operatoreList = operatoreRepository.getOperatorePerParametri(params, abilitato, soggetto)
                .stream().map(from -> genericMapper.operatoreToVo(from))
                .toList();

        return Response.ok(operatoreList).build();
    }

    @Override
    public Response getSoggettiAttuatoriForOperatoreById(Long idOperatore, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<SoggettoAttuatore> soggettiAttuatori = new ArrayList<>();
        // elenco dei soggetti attuatori per cui l'operatore lavora attualmente
        // FIXME usare
        // MipDSoggettoAttuatoreRepository.getSoggettoAttuatoreByIdOperatori(idOperatore)
        // e aggiungere un metodo genericMapper.soggettoAttuatoreToVo(List)
        List<MipROperatoreSoggAttuatore> mipROperatoreSoggAttuatoreList = operatoreSoggAttuatoreRepository
                .list("operatore.id = ?1", idOperatore);
        for (MipROperatoreSoggAttuatore mipROperatoreSoggAttuatore : mipROperatoreSoggAttuatoreList) {
            SoggettoAttuatore tmp = genericMapper
                    .soggettoAttuatoreToVo(mipROperatoreSoggAttuatore.getSoggettoAttuatore());

            soggettiAttuatori.add(tmp);
        }
        return Response.ok(soggettiAttuatori).build();
    }

    @Transactional
    @Override
    public Response insertOperatore(Operatore body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipDOperatore mipDOperatore = genericMapper.operatoreToEnty(body);
        Date adesso = getNow();

        mipDOperatore.setDataInserim(adesso);
        mipDOperatore.setDataAggiorn(adesso);
        mipDOperatore.persist();
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "insertOperatore",
                mipDOperatore,
                body,
                httpRequest);
        return Response.ok(genericMapper.operatoreToVo(mipDOperatore)).build();
    }

    @Transactional
    @Override
    public Response insertOperatoreIncontroPreaccoglienza(OperatoreIncontroPreaccoglienza body,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MipROperatoreIncontroPreaccoglienza operatoreIncontroPreaccoglienza = new MipROperatoreIncontroPreaccoglienza();
        MipTIncontroPreaccoglienza incontroPreaccoglienza = incontroPreaccoglienzaRepository
                .findById(body.getIdIncontroPreaccoglienza());
        MipDOperatore operatore = operatoreRepository.findById(body.getIdOperatore());
        Date adesso = getNow();

        MipROperatoreIncontroPreaccoglienzaId operatoreIncontroPreaccoglienzaId = new MipROperatoreIncontroPreaccoglienzaId();
        operatoreIncontroPreaccoglienzaId.setIdOperatoreAffidatario(body.getIdOperatore());
        operatoreIncontroPreaccoglienzaId.setIdIncontroPreaccoglienza(body.getIdIncontroPreaccoglienza());

        operatoreIncontroPreaccoglienza.setId(operatoreIncontroPreaccoglienzaId);
        operatoreIncontroPreaccoglienza.setIdIncontroPreaccoglienza(incontroPreaccoglienza);
        operatoreIncontroPreaccoglienza.setIdOperatoreAffidatario(operatore);
        operatoreIncontroPreaccoglienza.setDataAggiorn(adesso);
        operatoreIncontroPreaccoglienza.setDataInserim(adesso);
        operatoreIncontroPreaccoglienza.setCodUserAggiorn(body.getCodUserAggiorn());
        operatoreIncontroPreaccoglienza.setCodUserInserim(body.getCodUserInserim());

        operatoreIncontroPreaccoglienza.persist();

        Operatore ris = genericMapper.operatoreToVo(operatore);
        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "insertOperatoreIncontroPreaccoglienza",
                operatoreIncontroPreaccoglienzaId,
                body,
                httpRequest);
        return Response.ok(ris).build();
    }

    @Transactional
    @Override
    public Response updateOperaotre(Operatore body, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipDOperatore mipDOperatore = operatoreRepository.findById(body.getId());
        Date adesso = getNow();

        mipDOperatore.setEmail(body.getEmail());
        mipDOperatore.setTelefono(body.getTelefono());
        if (body.getDataDisabilitazione() != null) {
            mipDOperatore.setDataDisabilitazione(adesso);
            mipDOperatore.setIdOperatoreDisabilitazione(body.getIdOperatoreDisabilitazione());
        } else {
            mipDOperatore.setDataDisabilitazione(null);
            mipDOperatore.setIdOperatoreDisabilitazione(null);
        }
        mipDOperatore.setDataAggiorn(adesso);
        operatoreRepository.getEntityManager().merge(mipDOperatore);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "updateOperatore",
                mipDOperatore,
                body,
                httpRequest);
        return Response.ok(genericMapper.operatoreToVo(mipDOperatore)).build();
    }

    @Override
    public Response getOperatoriSoggAffidatarioById(Long idOperatore, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<MipDSoggettoAffidatario> listaOperatori = soggettoAffidatarioRepository
                .findSoggettiByIdOperatore(idOperatore);
        return Response.ok(listaOperatori).build();
    }
}
