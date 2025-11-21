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

import it.csi.gemip.mipcommonapi.api.ComuniApi;
import it.csi.gemip.mipcommonapi.api.dto.Comune;
import it.csi.gemip.mipcommonapi.integration.repositories.ExtTtComuneRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;


public class ComuneApiImpl extends ParentApiImpl implements ComuniApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    ExtTtComuneRepository extTtComuneRepository;
    @Override
    @CacheResult(cacheName = "getComuni", keyGenerator = MethodNameKeyGenerator.class)
    public Response getComuni(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        /**
         *  Torna l'elenco delle comuni
         */
        List<Comune> listComune = extTtComuneRepository.findAll().list()
                .stream().map(from->genericMapper.comuneEntyToVo(from))
                .toList();
        return Response.ok(listComune).build();
    }

    @Override
    @CacheResult(cacheName = "getComuniPerProvincia")
    public Response getComuniPerProvincia(@CacheKey String provincia, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        /**
         *  Torna l'elenco delle comuni associate a una provincia
         */
        List<Comune> listComune = extTtComuneRepository.find("provincia.codiceProvincia", provincia).list()
                .stream().map(from->genericMapper.comuneEntyToVo(from))
                .toList();
        return Response.ok(listComune).build();
    }

}
