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

import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheResult;
import io.quarkus.panache.common.Sort;
import it.csi.gemip.mipcommonapi.api.ProvinceApi;
import it.csi.gemip.mipcommonapi.api.dto.Provincia;
import it.csi.gemip.mipcommonapi.integration.repositories.ExtTtProvinciaRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;


public class ProvinceApiImpl extends ParentApiImpl implements ProvinceApi {
  @Inject
  GenericMapper genericMapper;

  @Inject
  ExtTtProvinciaRepository extTtProvinciaRepository;

  @Override
  @CacheResult(cacheName = "getProvince", keyGenerator = MethodNameKeyGenerator.class)
  public Response getProvince(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
    /**
     * Torna l'elenco delle provincie
     */
    List<Provincia> province = extTtProvinciaRepository.findAll(Sort.ascending("descrizioneProvincia")).list()
            .stream().map(from->genericMapper.provinciaEntyToVo(from))
            .toList();
    return Response.ok(province).build();
  }

  @Override
  @CacheResult(cacheName = "getProvincePerRegione")
  public Response getProvincePerRegione(@CacheKey String regione, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
    /**
     * Torna l'elenco delle province associate a una regione
     */
    List<Provincia> province = extTtProvinciaRepository.find("codiceRegione",regione).list()
            .stream().map(from->genericMapper.provinciaEntyToVo(from))
            .toList();
    return Response.ok(province).build();
  }


}
