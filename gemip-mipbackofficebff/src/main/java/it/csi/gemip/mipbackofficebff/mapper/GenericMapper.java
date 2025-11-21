package it.csi.gemip.mipbackofficebff.mapper;

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


import it.csi.gemip.mipbackofficebff.bff.dto.*;
import it.csi.gemip.mipbackofficebff.entities.*;
import jakarta.enterprise.context.ApplicationScoped;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@ApplicationScoped
@Mapper(componentModel = "cdi")
public interface GenericMapper {

    public Operatore operatoreToVo(MipDOperatore operatore);

    public MipDOperatore operatoreToEnty(Operatore operatore);

    public SoggettoAffidatarioOperatore soggettoAffOperatoreToVo(MipROperatoreSoggAttuatore operatoreSoggAttuatore);

    public MipROperatoreSoggAttuatore soggettoAffOperatoreToEnty(SoggettoAffidatarioOperatore soggettoAffidatarioOperatore);

    @Mapping(target = "idDocumento", source = "id")
    public Documento documentoToVo(MipTDocumento documento);
    @Mapping(target = "id", source = "idDocumento")
    public MipTDocumento documentoToEnty(Documento documento);

    @Mapping(target = "codiceTipoDocumento", source = "codTipoDocumento")
    @Mapping(target = "descrizioneTipoDocumento", source = "descrTipoDocumento")
    @Mapping(target = "dataInizio", source = "dataInizio")
    @Mapping(target = "dataFine", source = "dataFine")
    public TipoDocumento tipoDocumentoToVo(MipDTipoDocumento tipoDocumento);

    public EventoCalendario eventoCalendarioToVo(MipTEventoCalendario eventoCalendario);


}
