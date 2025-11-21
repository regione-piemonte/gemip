package it.csi.gemip.mipbackofficebff.bff.impl;

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

import static it.csi.gemip.mipbackofficebff.filter.IrideIdAdapterFilter.USERINFO_SESSIONATTR;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import it.csi.gemip.mipbackofficebff.bff.DocumentiApi;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.entities.MipDTipoDocumento;
import it.csi.gemip.mipbackofficebff.entities.MipTDocumento;
import it.csi.gemip.mipbackofficebff.mapper.GenericMapper;
import it.csi.gemip.mipbackofficebff.repositories.MipDTipoDocumentoRepository;
import it.csi.gemip.mipbackofficebff.repositories.MipTDocumentoRepository;
import it.csi.gemip.mipbackofficebff.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class DocumentiApiImpl extends ParentApiImpl implements DocumentiApi {
    @Inject
    GenericMapper genericMapper;

    @Inject
    MipTDocumentoRepository mipTDocumentoRepository;
    @Inject
    LogAuditUtil logAuditUtil;

    @Inject
    MipDTipoDocumentoRepository mipDTipoDocumentoRepository;
    @Transactional
    @Override
    public Response caricaDocumento(MultipartFormDataInput input, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MipTDocumento documento = new MipTDocumento();
        Date adesso = mipTDocumentoRepository.getNow();
        try {
            String codTipoDocumento=input.getFormDataMap().get("codeTipoDocumento").get(0).getBodyAsString().replace("\"","");
            MipDTipoDocumento tipoDocumento = MipDTipoDocumento.findById(codTipoDocumento);
            documento.setNomeDocumento(input.getFormDataMap().get("nomeDocumento").get(0).getBodyAsString());
            documento.setDescrizioneDocumento(input.getFormDataMap().get("descrizioneDocumento").get(0).getBodyAsString());
            documento.setCodTipoDocumento(tipoDocumento);
            documento.setDataInserim(adesso);
            documento.setCodUserInserim(input.getFormDataMap().get("codUserInserim").get(0).getBodyAsString());
            documento.setDataAggiorn(adesso);
            documento.setCodUserAggiorn(input.getFormDataMap().get("codUserInserim").get(0).getBodyAsString());
            if(input.getFormDataMap().get("idIdeaDiImpresa")!=null) {
                documento.setIdIdeaDiImpresa(Long.parseLong(input.getFormDataMap().get("idIdeaDiImpresa").get(0).getBodyAsString()));
            }
            documento.setIdOperatoreInserimento(Long.parseLong(input.getFormDataMap().get("idOperatoreInserimento").get(0).getBodyAsString()));

            File file = input.getFormDataMap().get("documento").get(0).getBody(File.class, null);
            //InputPart file = input.getFormDataMap().get("documento").get(0);
            //InputStream inputStream = file.getBody(InputStream.class, null);


            documento.setDocumentoByte(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        mipTDocumentoRepository.persist(documento);
        logAuditUtil.insertLogAudit(
            adesso,
            ((UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR)).getCodFisc(),
            getIncomingIPAddress(httpRequest),
            "caricaDocumento",
            documento,
            httpRequest);

        return Response.ok(genericMapper.documentoToVo(documento)).build();
    }

    @Override
    public Response deleteDocumento(Long idDocumento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateDelete(uriInfo);
    }

    @Override
    public Response getDocumentiByParameters(String tipoDocumento, String titolo, Long idIdeaDiImpresa, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getDocumentoById(Long idDocumento, SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }

    @Override
    public Response getTipiDocumento(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        return restUtils.chiamateGet(uriInfo);
    }
}
