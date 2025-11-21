package it.csi.gemip.mipcommonapi.api.dto;

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

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import it.csi.gemip.mipcommonapi.api.dto.CittadinoIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresa;
import jakarta.validation.constraints.*;


public class IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa   {
  private IdeaDiImpresa ideaDiImpresa = null;
  private CittadinoIncontroPreaccoglienza cittadinoIncontro = null;

  /**
   **/
  
  @JsonProperty("ideaDiImpresa")
  @jakarta.validation.Valid
  public IdeaDiImpresa getIdeaDiImpresa() {
    return ideaDiImpresa;
  }
  public void setIdeaDiImpresa(IdeaDiImpresa ideaDiImpresa) {
    this.ideaDiImpresa = ideaDiImpresa;
  }

  /**
   **/
  
  @JsonProperty("cittadinoIncontro")
  @jakarta.validation.Valid
  public CittadinoIncontroPreaccoglienza getCittadinoIncontro() {
    return cittadinoIncontro;
  }
  public void setCittadinoIncontro(CittadinoIncontroPreaccoglienza cittadinoIncontro) {
    this.cittadinoIncontro = cittadinoIncontro;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa ideaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa = (IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa) o;
    return Objects.equals(ideaDiImpresa, ideaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa.ideaDiImpresa) &&
        Objects.equals(cittadinoIncontro, ideaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa.cittadinoIncontro);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ideaDiImpresa, cittadinoIncontro);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdeaDiImpresaIncontroPreaccoglienzaIdeaDiImpresa {\n");
    
    sb.append("    ideaDiImpresa: ").append(toIndentedString(ideaDiImpresa)).append("\n");
    sb.append("    cittadinoIncontro: ").append(toIndentedString(cittadinoIncontro)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
