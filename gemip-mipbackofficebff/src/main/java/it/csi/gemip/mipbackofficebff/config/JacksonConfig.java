package it.csi.gemip.mipbackofficebff.config;

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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.ContextResolver;
import jakarta.ws.rs.ext.Provider;

import java.util.Locale;
import java.util.TimeZone;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonConfig implements ContextResolver<ObjectMapper>
    {
      private final ObjectMapper objectMapper;
      public JacksonConfig()
      {
        objectMapper = new ObjectMapper();
//        this.objectMapper.setDateFormat(new SimpleDateFormat(“dd.MM.yyyy”));
//        this.objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // StdDateFormat is ISO8601 since jackson 2.9
        TimeZone tz = TimeZone.getTimeZone("GMT+2");
        StdDateFormat sdf=new StdDateFormat(tz, Locale.ENGLISH);
        objectMapper.setDateFormat(sdf.withColonInTimeZone(true));
      }
      @Override
      public ObjectMapper getContext(Class<?> objectType)
      {
        return objectMapper;
      }
    }