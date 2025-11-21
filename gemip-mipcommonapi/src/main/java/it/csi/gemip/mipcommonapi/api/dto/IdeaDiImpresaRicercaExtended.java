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
import it.csi.gemip.mipcommonapi.api.dto.AnagraficaCittadino;
import it.csi.gemip.mipcommonapi.api.dto.AreaTerritoriale;
import it.csi.gemip.mipcommonapi.api.dto.Cittadino;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresa;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicerca;
import it.csi.gemip.mipcommonapi.api.dto.Operatore;
import it.csi.gemip.mipcommonapi.api.dto.Tutor;
import java.util.Date;
import jakarta.validation.constraints.*;


public class IdeaDiImpresaRicercaExtended extends IdeaDiImpresaRicerca  {
  private AnagraficaCittadino cittadinoAnagrafica = null;
  private String utentePrincipale = null;
  private Date dataPreaccoglienza = null;
  private String sedePreaccoglienza = null;
  private String flgIncontroTelefonico = null;

  /**
   **/
  
  @JsonProperty("cittadinoAnagrafica")
  @jakarta.validation.Valid
  public AnagraficaCittadino getCittadinoAnagrafica() {
    return cittadinoAnagrafica;
  }
  public void setCittadinoAnagrafica(AnagraficaCittadino cittadinoAnagrafica) {
    this.cittadinoAnagrafica = cittadinoAnagrafica;
  }

  /**
   **/
  
  @JsonProperty("utentePrincipale")
  @jakarta.validation.Valid
  public String getUtentePrincipale() {
    return utentePrincipale;
  }
  public void setUtentePrincipale(String utentePrincipale) {
    this.utentePrincipale = utentePrincipale;
  }

  /**
   **/
  
  @JsonProperty("dataPreaccoglienza")
  @jakarta.validation.Valid
  public Date getDataPreaccoglienza() {
    return dataPreaccoglienza;
  }
  public void setDataPreaccoglienza(Date dataPreaccoglienza) {
    this.dataPreaccoglienza = dataPreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("sedePreaccoglienza")
  @jakarta.validation.Valid
  public String getSedePreaccoglienza() {
    return sedePreaccoglienza;
  }
  public void setSedePreaccoglienza(String sedePreaccoglienza) {
    this.sedePreaccoglienza = sedePreaccoglienza;
  }

  /**
   **/
  
  @JsonProperty("flgIncontroTelefonico")
  @jakarta.validation.Valid
  public String getFlgIncontroTelefonico() {
    return flgIncontroTelefonico;
  }
  public void setFlgIncontroTelefonico(String flgIncontroTelefonico) {
    this.flgIncontroTelefonico = flgIncontroTelefonico;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    IdeaDiImpresaRicercaExtended ideaDiImpresaRicercaExtended = (IdeaDiImpresaRicercaExtended) o;
    return Objects.equals(cittadinoAnagrafica, ideaDiImpresaRicercaExtended.cittadinoAnagrafica) &&
        Objects.equals(utentePrincipale, ideaDiImpresaRicercaExtended.utentePrincipale) &&
        Objects.equals(dataPreaccoglienza, ideaDiImpresaRicercaExtended.dataPreaccoglienza) &&
        Objects.equals(sedePreaccoglienza, ideaDiImpresaRicercaExtended.sedePreaccoglienza) &&
        Objects.equals(flgIncontroTelefonico, ideaDiImpresaRicercaExtended.flgIncontroTelefonico);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cittadinoAnagrafica, utentePrincipale, dataPreaccoglienza, sedePreaccoglienza, flgIncontroTelefonico);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class IdeaDiImpresaRicercaExtended {\n");
    sb.append("    ").append(toIndentedString(super.toString())).append("\n");
    sb.append("    cittadinoAnagrafica: ").append(toIndentedString(cittadinoAnagrafica)).append("\n");
    sb.append("    utentePrincipale: ").append(toIndentedString(utentePrincipale)).append("\n");
    sb.append("    dataPreaccoglienza: ").append(toIndentedString(dataPreaccoglienza)).append("\n");
    sb.append("    sedePreaccoglienza: ").append(toIndentedString(sedePreaccoglienza)).append("\n");
    sb.append("    flgIncontroTelefonico: ").append(toIndentedString(flgIncontroTelefonico)).append("\n");
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
