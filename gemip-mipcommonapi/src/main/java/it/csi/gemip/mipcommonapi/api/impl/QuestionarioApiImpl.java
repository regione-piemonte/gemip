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
import java.util.Optional;

import it.csi.gemip.mipcommonapi.api.dto.Questionario;
import it.csi.gemip.mipcommonapi.api.dto.RispostaCompilazione;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTMailInviataRepository;
import it.csi.gemip.services.QuestionariService;
import it.csi.gemip.utils.MailUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class QuestionarioApiImpl extends ParentApiImpl implements it.csi.gemip.mipcommonapi.api.QuestionarioApi {

    @Inject
    QuestionariService questionariService;
    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;
    @Inject
    MipTMailInviataRepository mipTMailInviataRepository;
    @Inject
    MailUtil mailUtil;

    @Override
    public Response getQuestionarioCorrente(@QueryParam("idCittadino") Long idCittadino,
                                            SecurityContext securityContext, HttpHeaders httpHeaders,
                                            HttpServletRequest httpRequest)
    {
        MipTCittadino mipTCittadino = retrieveMipTCittadinoById(idCittadino);
        Questionario questionarioDTO = questionariService.getQuestionario(mipTCittadino);
        return Response.ok(questionarioDTO).build();
    }

    @Override
    public Response salvaRisposteQuestionario(List<RispostaCompilazione> body,
                                              @QueryParam("idCittadino") Long idCittadino, Long idQuestionario,
                                              SecurityContext securityContext, HttpHeaders httpHeaders,
                                              HttpServletRequest httpRequest)
    {
        MipTCittadino mipTCittadino = retrieveMipTCittadinoById(idCittadino);
        questionariService.salvaRisposte(body, mipTCittadino, idQuestionario);
        return Response.noContent().build();
    }

    @Override
    @Transactional
    public Response sendMail(String codEmail, List<Long> body, SecurityContext securityContext, HttpHeaders httpHeaders,
                             HttpServletRequest httpRequest)
    {
        // se volessero il timestamp puoi usare getNow()
        for (Long idCittadino : body) {
            mailUtil.sendEmailCittadino(codEmail, idCittadino, null, httpHeaders);
        }
        return Response.ok().build();
    }


    @Override
    public Response getQuestionarioFaseCorrente(@QueryParam("idCittadino") Long idCittadino,
                                                SecurityContext securityContext, HttpHeaders httpHeaders,
                                                HttpServletRequest httpRequest)
    {
        MipTCittadino mipTCittadino = retrieveMipTCittadinoById(idCittadino);
        Integer fase = questionariService.findFaseCorrente(mipTCittadino);
        return Response.ok(fase).build();
    }

    private MipTCittadino retrieveMipTCittadinoById(Long xCittadino) {
        Optional<MipTCittadino> mipTCittadinoOptional = mipTCittadinoRepository.findByIdOptional(xCittadino);
        return mipTCittadinoOptional.orElseThrow(() -> new RuntimeException("ERRORE Cittadino non presente"));
    }
}
