package it.csi.gemip.mipcommonapi.integration.reflection;

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

import io.quarkus.runtime.annotations.RegisterForReflection;
import it.csi.gemip.mipcommonapi.integration.entities.MipDTipoDocumento;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@RegisterForReflection
public class MipTDocumentoReflection {

    private Long id;

    private String nomeDocumento;


    private String descrizioneDocumento;


    private Long idOperatoreInserimento;


    private Long idIdeaDiImpresa;


    public MipTDocumentoReflection(Long id, String nomeDocumento, String descrizioneDocumento, Long idOperatoreInserimento, Long idIdeaDiImpresa) {
        this.id = id;
        this.nomeDocumento = nomeDocumento;
        this.descrizioneDocumento = descrizioneDocumento;

        this.idOperatoreInserimento = idOperatoreInserimento;
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeDocumento() {
        return nomeDocumento;
    }

    public void setNomeDocumento(String nomeDocumento) {
        this.nomeDocumento = nomeDocumento;
    }

    public String getDescrizioneDocumento() {
        return descrizioneDocumento;
    }

    public void setDescrizioneDocumento(String descrizioneDocumento) {
        this.descrizioneDocumento = descrizioneDocumento;
    }

    public Long getIdOperatoreInserimento() {
        return idOperatoreInserimento;
    }

    public void setIdOperatoreInserimento(Long idOperatoreInserimento) {
        this.idOperatoreInserimento = idOperatoreInserimento;
    }

    public Long getIdIdeaDiImpresa() {
        return idIdeaDiImpresa;
    }

    public void setIdIdeaDiImpresa(Long idIdeaDiImpresa) {
        this.idIdeaDiImpresa = idIdeaDiImpresa;
    }
}
