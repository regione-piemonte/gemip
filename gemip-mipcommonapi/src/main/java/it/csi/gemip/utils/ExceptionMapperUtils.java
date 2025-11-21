package it.csi.gemip.utils;

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


import it.csi.gemip.mipcommonapi.api.dto.Errore;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class ExceptionMapperUtils {

    public static final String MIME_TYPE = "application/problem+json";

    public Response toResponse(Throwable exception) {
        Errore errorBean = exception2bean(exception);

        return Response.status(errorBean.getStatus())
                .entity(errorBean)
                .type(MIME_TYPE)
                .build();
    }

    public Errore exception2bean(Throwable exception) {
        Status status = exception2status(exception);

        String message;
        if (exception.getMessage() != null && !exception.getMessage().isEmpty() && status.getStatusCode()!=500) {
            message = exception.getMessage();
        } else {
            message = status.getReasonPhrase();
        }

        Errore errorBean = new Errore();
        errorBean.setStatus(status.getStatusCode());
        errorBean.setTitle(message);
        return errorBean;
    }

    public Status exception2status(Throwable exception) {
        Status status;
        if (exception instanceof IllegalArgumentException) {
            status = Status.BAD_REQUEST;
        } else if (exception instanceof WebApplicationException) {
            // es. NotFoundException, NotAllowedException, NotAuthorizedException
            status = Status.fromStatusCode(((WebApplicationException) exception).getResponse().getStatus());
        } else {
            // es. PersistenceException
            status = Status.INTERNAL_SERVER_ERROR;
        }
        return status;
    }
}
