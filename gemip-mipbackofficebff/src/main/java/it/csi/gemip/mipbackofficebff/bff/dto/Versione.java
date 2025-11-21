package it.csi.gemip.mipbackofficebff.bff.dto;

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
import jakarta.validation.constraints.*;

public class Versione   {
  private String versioneDev = null;
  private String versioneTest = null;
  private String versioneProd = null;

  /**
   **/
  
  @JsonProperty("versioneDev")
  public String getVersioneDev() {
    return versioneDev;
  }
  public void setVersioneDev(String versioneDev) {
    this.versioneDev = versioneDev;
  }

  /**
   **/
  
  @JsonProperty("versioneTest")
  public String getVersioneTest() {
    return versioneTest;
  }
  public void setVersioneTest(String versioneTest) {
    this.versioneTest = versioneTest;
  }

  /**
   **/
  
  @JsonProperty("versioneProd")
  public String getVersioneProd() {
    return versioneProd;
  }
  public void setVersioneProd(String versioneProd) {
    this.versioneProd = versioneProd;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Versione versione = (Versione) o;
    return Objects.equals(versioneDev, versione.versioneDev) &&
        Objects.equals(versioneTest, versione.versioneTest) &&
        Objects.equals(versioneProd, versione.versioneProd);
  }

  @Override
  public int hashCode() {
    return Objects.hash(versioneDev, versioneTest, versioneProd);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Versione {\n");
    
    sb.append("    versioneDev: ").append(toIndentedString(versioneDev)).append("\n");
    sb.append("    versioneTest: ").append(toIndentedString(versioneTest)).append("\n");
    sb.append("    versioneProd: ").append(toIndentedString(versioneProd)).append("\n");
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
