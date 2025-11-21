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

import static it.csi.gemip.utils.Constants.INPUT_MISSING.ID_IDEA_DI_IMPRESA;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.csi.gemip.utils.LogAuditUtil;
import it.csi.gemip.utils.cache.MethodNameKeyGenerator;

import org.apache.commons.io.FileUtils;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import io.quarkus.cache.CacheResult;
import it.csi.gemip.mipcommonapi.api.DocumentiApi;
import it.csi.gemip.mipcommonapi.api.dto.Documento;
import it.csi.gemip.mipcommonapi.api.dto.TipoDocumento;
import it.csi.gemip.mipcommonapi.integration.entities.MipDTipoDocumento;
import it.csi.gemip.mipcommonapi.integration.entities.MipTDocumento;
import it.csi.gemip.mipcommonapi.integration.reflection.MipTDocumentoReflection;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDTipoDocumentoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTDocumentoRepository;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
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
    MipDTipoDocumentoRepository mipDTipoDocumentoRepository;

    @Inject
    LogAuditUtil logAuditUtil;

    @Transactional
    @Override
    public Response caricaDocumento(MultipartFormDataInput input, SecurityContext securityContext,
            HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        MipTDocumento documento = new MipTDocumento();
        Date adesso = mipTDocumentoRepository.getNow();
        try {
            String codTipoDocumento = input.getFormDataMap().get("codeTipoDocumento").get(0).getBodyAsString()
                    .replace("\"", "");
            MipDTipoDocumento tipoDocumento = MipDTipoDocumento.findById(codTipoDocumento);
            documento.setNomeDocumento(input.getFormDataMap().get("nomeDocumento").get(0).getBodyAsString());
            documento.setDescrizioneDocumento(
                    input.getFormDataMap().get("descrizioneDocumento").get(0).getBodyAsString());
            documento.setCodTipoDocumento(tipoDocumento);
            documento.setDataInserim(adesso);
            documento.setCodUserInserim(input.getFormDataMap().get("codUserInserim").get(0).getBodyAsString());
            documento.setDataAggiorn(adesso);
            documento.setCodUserAggiorn(input.getFormDataMap().get("codUserInserim").get(0).getBodyAsString());
            if (input.getFormDataMap().get(ID_IDEA_DI_IMPRESA) != null) {
                documento.setIdIdeaDiImpresa(
                        Long.parseLong(input.getFormDataMap().get(ID_IDEA_DI_IMPRESA).get(0).getBodyAsString()));
            }
            documento.setIdOperatoreInserimento(
                    Long.parseLong(input.getFormDataMap().get("idOperatoreInserimento").get(0).getBodyAsString()));

            File file = input.getFormDataMap().get("documento").get(0).getBody(File.class, null);
            documento.setDocumentoByte(FileUtils.readFileToByteArray(file));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

        mipTDocumentoRepository.persist(documento);

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "caricaDocumento",
                documento,
                null,
                httpRequest);

        return Response.ok(genericMapper.documentoToVo(documento)).build();
    }

    @Transactional
    @Override
    public Response deleteDocumento(Long idDocumento, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        Date adesso = getNow();
        MipTDocumento documento = mipTDocumentoRepository.findById(idDocumento);
        documento.persist();
        documento.delete();

        logAuditUtil.insertLogAudit(
                adesso,
                httpHeaders,
                "deleteDocumento",
                documento,
                null,
                httpRequest);

        return Response.ok().build();
    }

    @Override
    public Response getDocumentiByParameters(String tipoDocumento, String titolo, Long idIdeaDiImpresa,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();
        if (tipoDocumento != null && !tipoDocumento.isBlank()) {
            params.put("tipoDocumento", tipoDocumento);
        }
        if (titolo != null && !titolo.isBlank()) {
            params.put("titolo", "%" + titolo + "%");
        }

        if (idIdeaDiImpresa != null) {
            params.put(ID_IDEA_DI_IMPRESA, idIdeaDiImpresa);
        }
        List<MipTDocumentoReflection> mipTDocumenti = mipTDocumentoRepository
                .findByParameters((HashMap<String, Object>) params);
        List<Documento> response = new ArrayList<>();
        for (MipTDocumentoReflection mipTDocumento : mipTDocumenti) {
            Documento tmp = new Documento();
            tmp.setIdDocumento(mipTDocumento.getId());
            tmp.setIdOperatoreInserimento(mipTDocumento.getIdOperatoreInserimento());
            tmp.setIdIdeaDiImpresa(mipTDocumento.getIdIdeaDiImpresa());
            tmp.setDescrizioneDocumento(mipTDocumento.getDescrizioneDocumento());
            tmp.setNomeDocumento(mipTDocumento.getNomeDocumento());
            response.add(tmp);
        }

        return Response.ok(response).build();
    }

    @Override
    public Response getDocumentoById(Long idDocumento, SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {
        MipTDocumento doc = mipTDocumentoRepository.findById(idDocumento);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(doc.getDocumentoByte());
            return Response.ok(doc.getDocumentoByte()).build();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }

    }

    @Override
    @CacheResult(cacheName = "getTipiDocumento", keyGenerator = MethodNameKeyGenerator.class)
    public Response getTipiDocumento(SecurityContext securityContext, HttpHeaders httpHeaders,
            HttpServletRequest httpRequest) {

        List<TipoDocumento> tipoDocumentoList = mipDTipoDocumentoRepository.findAll().list()
                .stream().map(from -> genericMapper.tipoDocumentoToVo(from))
                .toList();
        return Response.ok(tipoDocumentoList).build();
    }
}
