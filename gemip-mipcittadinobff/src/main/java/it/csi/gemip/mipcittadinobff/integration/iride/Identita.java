package it.csi.gemip.mipcittadinobff.integration.iride;

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

import java.text.ParseException;

public class Identita {

    private String nome;
    private String cognome;
    private String codFiscale;
    private String idProvider;
    private String timestamp;
    private int livelloAutenticazione;
    private String mac;

    public static final int AUTENTICAZIONE_USERNAME_PASSWORD_UNVERIFIED = 1;
    public static final int AUTENTICAZIONE_USERNAME_PASSWORD = 2;
    public static final int AUTENTICAZIONE_USERNAME_PASSWORD_PIN = 4;
    public static final int AUTENTICAZIONE_CERTIFICATO = 8;
    public static final int AUTENTICAZIONE_CERTIFICATO_FORTE = 16;
 
    public Identita(String codFiscale, String nome, String cognome, String idProvider, String timestamp, int livelloAutenticazione) {
       this.codFiscale = codFiscale;
       this.nome = nome;
       this.cognome = cognome;
       this.idProvider = idProvider;
       this.timestamp = timestamp;
       this.livelloAutenticazione = livelloAutenticazione;
    }
 
    public Identita() {
       this.codFiscale = null;
       this.nome = null;
       this.cognome = null;
       this.idProvider = null;
       this.timestamp = null;
       this.livelloAutenticazione = 0;
    }
 
    public Identita(String token) throws ParseException {
       int slash1Index = token.indexOf(47);
       if (slash1Index == -1) {
          throw new ParseException(token, 0);
       } else {
          this.codFiscale = token.substring(0, slash1Index);
          int slash2Index = token.indexOf(47, slash1Index + 1);
          if (slash2Index == -1) {
             throw new ParseException(token, slash1Index + 1);
          } else {
             this.nome = token.substring(slash1Index + 1, slash2Index);
             int slash3Index = token.indexOf(47, slash2Index + 1);
             if (slash3Index == -1) {
                throw new ParseException(token, slash1Index + 1);
             } else {
                this.cognome = token.substring(slash2Index + 1, slash3Index);
                int slash4Index = token.indexOf(47, slash3Index + 1);
                if (slash4Index == -1) {
                   throw new ParseException(token, slash1Index + 1);
                } else {
                   this.idProvider = token.substring(slash3Index + 1, slash4Index);
                   int slash5Index = token.indexOf(47, slash4Index + 1);
                   if (slash5Index == -1) {
                      throw new ParseException(token, slash1Index + 1);
                   } else {
                      this.timestamp = token.substring(slash4Index + 1, slash5Index);
                      int slash6Index = token.indexOf(47, slash5Index + 1);
                      if (slash6Index == -1) {
                         throw new ParseException(token, slash1Index + 1);
                      } else {
                         this.livelloAutenticazione = Integer.parseInt(token.substring(slash5Index + 1, slash6Index));
                         this.mac = token.substring(slash6Index + 1);
                      }
                   }
                }
             }
          }
       }
    }
 
    public String getNome() {
       return this.nome;
    }
 
    public String getCognome() {
       return this.cognome;
    }
 
    public String getCodFiscale() {
       return this.codFiscale;
    }
 
    public String getIdProvider() {
       return this.idProvider;
    }
 
    public String getTimestamp() {
       return this.timestamp;
    }
 
    public int getLivelloAutenticazione() {
       return this.livelloAutenticazione;
    }
 
    public String getMac() {
       return this.mac;
    }
 
    public void setMac(String mac) {
       this.mac = mac;
    }
 
    public boolean equals(Object obj) {
       if (obj != null && this.getClass().equals(obj.getClass())) {
          Identita that = (Identita)obj;
          return (this.nome == null && that.nome == null || this.nome != null && this.nome.equals(that.nome)) && (this.cognome == null && that.cognome == null || this.cognome != null && this.cognome.equals(that.cognome)) && (this.codFiscale == null && that.codFiscale == null || this.codFiscale != null && this.codFiscale.equals(that.codFiscale));
       } else {
          return false;
       }
    }
 
    public int hashCode() {
       return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(this.codFiscale)))).append(this.nome).append(this.cognome).append(this.mac))).hashCode();
    }
 
    public String toString() {
       return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(this.codFiscale)))).append("/").append(this.nome).append("/").append(this.cognome).append("/").append(this.idProvider).append("/").append(this.timestamp).append("/").append(this.livelloAutenticazione).append("/").append(this.mac)));
    }
 
    public String getRappresentazioneInterna() {
       return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(this.codFiscale)))).append("/").append(this.nome).append("/").append(this.cognome).append("/").append(this.idProvider).append("/").append(this.timestamp).append("/").append(this.livelloAutenticazione)));
    }
 
    public void setNome(String nome) {
       this.nome = nome;
    }
 
    public void setTimestamp(String timestamp) {
       this.timestamp = timestamp;
    }
 
    public void setLivelloAutenticazione(int livelloAutenticazione) {
       this.livelloAutenticazione = livelloAutenticazione;
    }
 
    public void setIdProvider(String idProvider) {
       this.idProvider = idProvider;
    }
 
    public void setCognome(String cognome) {
       this.cognome = cognome;
    }
 
    public void setCodFiscale(String codFiscale) {
       this.codFiscale = codFiscale;
    }
 }
 