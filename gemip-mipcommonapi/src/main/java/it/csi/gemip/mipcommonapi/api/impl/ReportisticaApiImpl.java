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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.SortedMap;
import java.util.TreeMap;

import it.csi.gemip.mipcommonapi.api.ReportisticaApi;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaExtended;
import it.csi.gemip.mipcommonapi.api.dto.IdeaDiImpresaRicercaExtendedConTotale;
import it.csi.gemip.mipcommonapi.api.dto.ReportQuestionario;
import it.csi.gemip.mipcommonapi.excel.ExcelExport;
import it.csi.gemip.mipcommonapi.mapper.GenericMapper;
import it.csi.gemip.mipcommonapi.mapper.ReportisticaMapper;
import it.csi.gemip.model.ExcelReportisticaIdeaDiImpresa;
import it.csi.gemip.model.ExcelReportisticaQuestionari;
import it.csi.gemip.services.ReportisticaService;
import it.csi.gemip.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

public class ReportisticaApiImpl extends ParentApiImpl implements ReportisticaApi {
        @Inject
        GenericMapper genericMapper;
        @Inject
        ReportisticaService service;
        @Inject
        ReportisticaMapper reportisticaMapper;
        @Inject
        LogAuditUtil logAuditUtil;

        @Override
        public Response exportExcelIdeeImpresa(String nomeOperatore, Date dataDa, Date dataA, Long idSoggettoAttuatore,
                        String idCodAreaTerritoriale, Long idStatoIdea, String tipoReport,
                        SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                ExcelExport excelExport = new ExcelExport();
                Date adesso = getNow();
                List<IdeaDiImpresaRicercaExtended> ideaDiImpresaRicercaExtendeds = service
                                .getIdeaDiImpresaRicercaExtendeds(dataDa,
                                                dataA,
                                                idSoggettoAttuatore,
                                                idCodAreaTerritoriale,
                                                idStatoIdea);
                SimpleDateFormat outDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                SimpleDateFormat outDateTitle = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
                SortedMap<String, String> altriDati = new TreeMap<>();
                altriDati.put("[0]Report", "Idee d’impresa - elenco completo");
                altriDati.put("[1]Operatore", nomeOperatore);
                altriDati.put("[2]Creato il", outDateTime.format(adesso));
                altriDati.put("[3]N. record", String.valueOf(ideaDiImpresaRicercaExtendeds.size()));
                altriDati.put("[4] ", " ");
                altriDati.put("[5]CRITERI FILTRO", " ");
                altriDati.put("[6]Data inizio", dataA == null ? "  " : outDateTime.format(dataA));
                altriDati.put("[7]Data fine", dataDa == null ? "  " : outDateTime.format(dataDa));
                altriDati.put("[8]Soggetto attuatore", idSoggettoAttuatore == null ? "  "
                                : service.findSoggettoAttuatoreById(idSoggettoAttuatore));

                altriDati.put("[9]Status", idStatoIdea == null ? " " : service.findStatusById(idStatoIdea));

                List<ExcelReportisticaIdeaDiImpresa> excelModelli = reportisticaMapper
                                .entity2report(ideaDiImpresaRicercaExtendeds);

                int i = 0;
                for (ExcelReportisticaIdeaDiImpresa el : excelModelli) {
                        el.setTipoPermessoDiSoggiorno(ideaDiImpresaRicercaExtendeds.get(i).getCittadinoAnagrafica()
                                        .getTipoPermessoDiSoggiorno());
                        i++;
                }

                return excelExport.addDataToWorkbook(excelModelli,
                                ReportisticaService.HEADERS,
                                altriDati,
                                "ideedimpresa_" + outDateTitle.format(adesso) + "_mip-report");
        }

        @Override
        public Response exportExcelQuestionari(Long idFase, Date dataDa, Date dataA, Long idSoggettoAttuatore,
                        String idCodAreaTerritoriale, String nomeOperatore, SecurityContext securityContext,
                        HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
                ExcelExport excelExport = new ExcelExport();
                Date adesso = getNow();

                SortedMap<String, String> altriDati = new TreeMap<>();
                SimpleDateFormat outDateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ITALY);
                SimpleDateFormat outDateTitle = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
                List<ExcelReportisticaQuestionari> excelModelli;
                excelModelli = service.getExcelRisposte(idFase, dataDa, dataA, idCodAreaTerritoriale,
                                idSoggettoAttuatore);
                ReportQuestionario reportQuestionario = service.makeReportQuestionario(idFase, dataDa, dataA,
                                idSoggettoAttuatore, idCodAreaTerritoriale);
                altriDati.put("[0]Report", "Questionario fase: " + idFase);
                altriDati.put("[1]Operatore", nomeOperatore);
                altriDati.put("[2]Creato il", outDateTime.format(adesso));
                altriDati.put("[3]N. record", String.valueOf(excelModelli.size()));
                altriDati.put("[4] ", " ");
                altriDati.put("[5]CRITERI FILTRO", " ");
                altriDati.put("[6]Data inizio", dataDa == null ? "  " : outDateTime.format(dataDa));
                altriDati.put("[7]Data fine", dataA == null ? "  " : outDateTime.format(dataA));
                altriDati.put("[8]Soggetto attuatore", idSoggettoAttuatore == null ? "  "
                                : service.findSoggettoAttuatoreById(idSoggettoAttuatore));
                altriDati.put("[9]Area territoriale", idCodAreaTerritoriale == null ? "  "
                                : service.findAreaTerritorialeById(idCodAreaTerritoriale));

                return excelExport.addDataToWorkbook(excelModelli,
                                ReportisticaService.getHeaders(reportQuestionario.getDomande()),
                                altriDati,
                                "questionari_" + outDateTitle.format(adesso) + "_mip-report");

        }

        @Override
        public Response getDomandeRisposte(Long idFase, Date dataDa, Date dataA, Long idSoggettoAttuatore,
                        String idCodAreaTerritoriale, Integer pageIndex, Integer pageSize,
                        SecurityContext securityContext, HttpHeaders httpHeaders,
                        HttpServletRequest httpRequest) {
                ReportQuestionario reportQuestionario = service.makeReportQuestionario(idFase, dataDa, dataA,
                                idSoggettoAttuatore, idCodAreaTerritoriale);
                reportQuestionario.setTotaleRisposte(reportQuestionario.getRigheReportistica().size());
                reportQuestionario
                                .setRigheReportistica(!reportQuestionario.getRigheReportistica().isEmpty()
                                                ? reportQuestionario.getRigheReportistica().subList(
                                                                (pageIndex * pageSize),
                                                                Math.min(reportQuestionario.getRigheReportistica()
                                                                                .size(), ((pageIndex + 1) * pageSize)))
                                                : reportQuestionario.getRigheReportistica());
                return Response.ok(reportQuestionario).build();
        }

        @Override
        @Transactional
        public Response getIdeeImpresa(Date dataDa, Date dataA, Long idSoggettoAttuatore, String idCodAreaTerritoriale,
                        Long idStatoIdea, String tipoReport, Integer pageIndex, Integer pageSize,
                        SecurityContext securityContext,
                        HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
                List<IdeaDiImpresaRicercaExtended> ideaDiImpresaRicercaExtendeds = service
                                .getIdeaDiImpresaRicercaExtendeds(dataDa,
                                                dataA,
                                                idSoggettoAttuatore,
                                                idCodAreaTerritoriale,
                                                idStatoIdea);

                IdeaDiImpresaRicercaExtendedConTotale obj = new IdeaDiImpresaRicercaExtendedConTotale();
                obj.setIdeaDiImpresaRicercaList(
                                !ideaDiImpresaRicercaExtendeds.isEmpty()
                                                ? ideaDiImpresaRicercaExtendeds.subList((pageIndex * pageSize),
                                                                Math.min(ideaDiImpresaRicercaExtendeds.size(),
                                                                                ((pageIndex + 1) * pageSize)))
                                                : ideaDiImpresaRicercaExtendeds);
                obj.setTotalNumberResult(ideaDiImpresaRicercaExtendeds.size());

                logAuditUtil.insertLogAudit(getNow(), httpHeaders, "getIdeeImpresaReposrtistica", obj, null,
                                httpRequest);
                return Response.ok(obj).build();
        }
}