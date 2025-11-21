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
import java.util.List;
import jakarta.validation.constraints.*;


public class StringCrit   {
  private String eq = null;
  private String ne = null;
  private String lt = null;
  private String lte = null;
  private String gt = null;
  private String gte = null;
  private List<String> in = new ArrayList<String>();
  private List<String> nin = new ArrayList<String>();
  private String c = null;
  private String ci = null;
  private String s = null;
  private String si = null;
  private String e = null;
  private String ei = null;

  /**
   **/
  
  @JsonProperty("eq")
  @jakarta.validation.Valid
  public String getEq() {
    return eq;
  }
  public void setEq(String eq) {
    this.eq = eq;
  }

  /**
   **/
  
  @JsonProperty("ne")
  @jakarta.validation.Valid
  public String getNe() {
    return ne;
  }
  public void setNe(String ne) {
    this.ne = ne;
  }

  /**
   **/
  
  @JsonProperty("lt")
  @jakarta.validation.Valid
  public String getLt() {
    return lt;
  }
  public void setLt(String lt) {
    this.lt = lt;
  }

  /**
   **/
  
  @JsonProperty("lte")
  @jakarta.validation.Valid
  public String getLte() {
    return lte;
  }
  public void setLte(String lte) {
    this.lte = lte;
  }

  /**
   **/
  
  @JsonProperty("gt")
  @jakarta.validation.Valid
  public String getGt() {
    return gt;
  }
  public void setGt(String gt) {
    this.gt = gt;
  }

  /**
   **/
  
  @JsonProperty("gte")
  @jakarta.validation.Valid
  public String getGte() {
    return gte;
  }
  public void setGte(String gte) {
    this.gte = gte;
  }

  /**
   **/
  
  @JsonProperty("in")
  @jakarta.validation.Valid
  public List<String> getIn() {
    return in;
  }
  public void setIn(List<String> in) {
    this.in = in;
  }

  /**
   **/
  
  @JsonProperty("nin")
  @jakarta.validation.Valid
  public List<String> getNin() {
    return nin;
  }
  public void setNin(List<String> nin) {
    this.nin = nin;
  }

  /**
   **/
  
  @JsonProperty("c")
  @jakarta.validation.Valid
  public String getC() {
    return c;
  }
  public void setC(String c) {
    this.c = c;
  }

  /**
   **/
  
  @JsonProperty("ci")
  @jakarta.validation.Valid
  public String getCi() {
    return ci;
  }
  public void setCi(String ci) {
    this.ci = ci;
  }

  /**
   **/
  
  @JsonProperty("s")
  @jakarta.validation.Valid
  public String getS() {
    return s;
  }
  public void setS(String s) {
    this.s = s;
  }

  /**
   **/
  
  @JsonProperty("si")
  @jakarta.validation.Valid
  public String getSi() {
    return si;
  }
  public void setSi(String si) {
    this.si = si;
  }

  /**
   **/
  
  @JsonProperty("e")
  @jakarta.validation.Valid
  public String getE() {
    return e;
  }
  public void setE(String e) {
    this.e = e;
  }

  /**
   **/
  
  @JsonProperty("ei")
  @jakarta.validation.Valid
  public String getEi() {
    return ei;
  }
  public void setEi(String ei) {
    this.ei = ei;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    StringCrit stringCrit = (StringCrit) o;
    return Objects.equals(eq, stringCrit.eq) &&
        Objects.equals(ne, stringCrit.ne) &&
        Objects.equals(lt, stringCrit.lt) &&
        Objects.equals(lte, stringCrit.lte) &&
        Objects.equals(gt, stringCrit.gt) &&
        Objects.equals(gte, stringCrit.gte) &&
        Objects.equals(in, stringCrit.in) &&
        Objects.equals(nin, stringCrit.nin) &&
        Objects.equals(c, stringCrit.c) &&
        Objects.equals(ci, stringCrit.ci) &&
        Objects.equals(s, stringCrit.s) &&
        Objects.equals(si, stringCrit.si) &&
        Objects.equals(e, stringCrit.e) &&
        Objects.equals(ei, stringCrit.ei);
  }

  @Override
  public int hashCode() {
    return Objects.hash(eq, ne, lt, lte, gt, gte, in, nin, c, ci, s, si, e, ei);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class StringCrit {\n");
    
    sb.append("    eq: ").append(toIndentedString(eq)).append("\n");
    sb.append("    ne: ").append(toIndentedString(ne)).append("\n");
    sb.append("    lt: ").append(toIndentedString(lt)).append("\n");
    sb.append("    lte: ").append(toIndentedString(lte)).append("\n");
    sb.append("    gt: ").append(toIndentedString(gt)).append("\n");
    sb.append("    gte: ").append(toIndentedString(gte)).append("\n");
    sb.append("    in: ").append(toIndentedString(in)).append("\n");
    sb.append("    nin: ").append(toIndentedString(nin)).append("\n");
    sb.append("    c: ").append(toIndentedString(c)).append("\n");
    sb.append("    ci: ").append(toIndentedString(ci)).append("\n");
    sb.append("    s: ").append(toIndentedString(s)).append("\n");
    sb.append("    si: ").append(toIndentedString(si)).append("\n");
    sb.append("    e: ").append(toIndentedString(e)).append("\n");
    sb.append("    ei: ").append(toIndentedString(ei)).append("\n");
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
