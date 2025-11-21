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

import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.api.CittadinanzeApi;
import it.csi.gemip.mipcommonapi.api.dto.Cittadinanza;
import it.csi.gemip.mipcommonapi.integration.repositories.ExtTabCittadinanzaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

public class CittadinanzeApiImpl extends ParentApiImpl implements CittadinanzeApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    ExtTabCittadinanzaRepository extTabCittadinanzaRepository;

    @Override
    @CacheResult(cacheName = "getCittadinanze", keyGenerator = MethodNameKeyGenerator.class)
    public Response getCittadinanze(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        List<Cittadinanza> statiEsteri = extTabCittadinanzaRepository.findAll(Sort.ascending("descrizione")).list()
                .stream().map(from -> genericMapper.cittadinanzaEntyToVo(from))
                .toList();
        return Response.ok(statiEsteri).build();
    }
}
