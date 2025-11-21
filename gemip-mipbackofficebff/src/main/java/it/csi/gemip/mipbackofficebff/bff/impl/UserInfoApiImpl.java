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

import it.csi.gemip.mipbackofficebff.bff.UserInfoApi;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.entities.MipDOperatore;
import it.csi.gemip.mipbackofficebff.exception.UtenteNonAbilitatoException;
import it.csi.gemip.mipbackofficebff.integration.SecurityHelper;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.time.Instant;
import java.util.Date;

import org.jboss.logging.Logger;

import io.quarkus.runtime.configuration.ConfigUtils;

public class UserInfoApiImpl extends ParentApiImpl implements UserInfoApi {
    @Inject
    private SecurityHelper securityHelper;
    @Inject
    Logger LOG;

    @Override
    @Transactional
    public Response getCurrentUser(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        
        LOG.info("PROFILO CORRENTE: " + ConfigUtils.getProfiles().get(0));

        UserInfo response=securityHelper.getCurrentUserInfo(httpRequest);
        if(!MipDOperatore.find("codFiscaleUtente =? 1",response.getCodFisc()).list().isEmpty()) {
            MipDOperatore operatore = (MipDOperatore) MipDOperatore.find("codFiscaleUtente =? 1", response.getCodFisc()).list().get(0);
            if(operatore.getIdOperatoreDisabilitazione()!=null){
                throw new UtenteNonAbilitatoException("operatore disabilitato");
            }
            response.setIdOperatore(operatore.getId());

        }else if(response.getCodFisc()!=null){
            Instant adesso = (Instant) MipDOperatore.getEntityManager().createQuery("select now()").getResultList().get(0);
            Date now=Date.from(adesso);
            MipDOperatore tmp= new MipDOperatore();
            tmp.setCodFiscaleUtente(response.getCodFisc());
            tmp.setCognome(response.getCognome());
            tmp.setNome(response.getNome());
            tmp.setCodUserAggiorn("SISTEMA");
            tmp.setCodUserInserim("SISTEMA");
            tmp.setDataInserim(now);
            tmp.setDataAggiorn(now);
            tmp.setDataRegistrazione(now);
            tmp.persist();
            response.setIdOperatore(tmp.getId());
        }

        return Response.ok(response).build();
    }

}
