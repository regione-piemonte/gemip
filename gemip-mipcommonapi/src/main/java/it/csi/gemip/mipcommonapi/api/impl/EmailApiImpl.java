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

import it.csi.gemip.mipcommonapi.api.dto.Cittadino;
import it.csi.gemip.utils.MailUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

import static it.csi.gemip.utils.MailUtil.COD_MAIL_30_QUESTIONARIO_1;
import static it.csi.gemip.utils.MailUtil.COD_MAIL_40_SCELTA_TUTOR;

public class EmailApiImpl extends ParentApiImpl implements it.csi.gemip.mipcommonapi.api.EmailApi{

    @Inject
    MailUtil mailUtil;

    @Override
    public Response inviaEmailCittadini(List<Cittadino> cittadini, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        for (Cittadino cittadino : cittadini) {
            mailUtil.sendEmailCittadino(COD_MAIL_30_QUESTIONARIO_1, cittadino.getIdCittadino(), null,httpHeaders);
            mailUtil.sendEmailCittadino(COD_MAIL_40_SCELTA_TUTOR,cittadino.getIdCittadino(), null,httpHeaders);
        }
        return Response.ok("Email inviate.").build();
    }
}

