package it.csi.gemip.services;

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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import io.quarkus.panache.common.Parameters;
import io.quarkus.scheduler.Scheduled;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIdeaDiImpresa;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDEnteRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRIdeaDiImpresaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoExtenRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTDocumentoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIdeaDiImpresaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTMailInviataRepository;
import it.csi.gemip.utils.LogAuditUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CleanupScheduler {
        @Inject
        LogAuditUtil logAuditUtil;
        @Inject
        MipTCittadinoRepository mipTCittadinoRepository;
        @Inject
        MipDEnteRepository mipDEnteRepository;
        @Inject
        MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;
        @Inject
        MipTAnagraficaCittadinoExtenRepository mipTAnagraficaCittadinoExtenRepository;
        @Inject
        MipRCittadinoIncontroPreaccRepository mipRCittadinoIncontroPreaccRepository;
        @Inject
        MipTIdeaDiImpresaRepository mipTIdeaDiImpresaRepository;
        @Inject
        MipTDocumentoRepository mipTDocumentoRepository;
        @Inject
        MipRIdeaDiImpresaCittadinoRepository mipRIdeaDiImpresaCittadinoRepository;
        @Inject
        MipTMailInviataRepository mipTMailInviataRepository;
        @Inject
        Logger logger;

        @Scheduled(cron = "{cron.expr.cleanupscheduler}")
        @Transactional
        public void cleanupOldData() {
                logger.info("cleanupOldDataScheduler start");
                Date now = mipDEnteRepository.getNow();
                List<MipTCittadino> cittadiniList = mipTCittadinoRepository.getCittadiniCleanup(now);

                cittadiniList.forEach(cittadino -> {
                        Long idCittadino = cittadino.getIdCittadino();
                        logger.info("Processing cittadino with id: " + idCittadino);

                        // Delete MipTAnagraficaCittadinoExten if it exists
                        if (mipTAnagraficaCittadinoExtenRepository.findById(idCittadino) != null) {
                                mipTAnagraficaCittadinoExtenRepository.delete(idCittadino);
                        }

                        // Delete MipTAnagraficaCittadino if it exists
                        if (mipTAnagraficaCittadinoRepository.findById(idCittadino) != null) {
                                mipTAnagraficaCittadinoRepository.deleteById(idCittadino);
                        }

                        // Delete MipRCittadinoIncontroPreacc if it exists
                        mipRCittadinoIncontroPreaccRepository
                                        .find("cittadino.idCittadino = :idCittadino",
                                                        Parameters.with("idCittadino", idCittadino))
                                        .firstResultOptional()
                                        .ifPresent(mipRCittadinoIncontroPreaccRepository::delete);

                        // Delete related MipTIdeaDiImpresa and its documents if they exist
                        MipTIdeaDiImpresa mipTIdeaDiImpresa = mipTIdeaDiImpresaRepository
                                        .getIdeaDiImpresaByIdCittadino(idCittadino);
                        if (mipTIdeaDiImpresa != null) {
                                // Delete MipTDocumento entries
                                mipTDocumentoRepository.delete("idIdeaDiImpresa = :idIdeaDiImpresa",
                                                Parameters.with("idIdeaDiImpresa", mipTIdeaDiImpresa.getId()));
                                mipRIdeaDiImpresaCittadinoRepository.delete("idCittadino.idCittadino = :idCittadino",
                                                Parameters.with("idCittadino", idCittadino));
                                mipTIdeaDiImpresaRepository.delete(mipTIdeaDiImpresa);
                        }

                        // Delete MipTMailInviata entries
                        mipTMailInviataRepository.delete("idCittadino = :idCittadino",
                                        Parameters.with("idCittadino", idCittadino));

                        // Finally, delete the MipTCittadino entry
                        mipTCittadinoRepository.delete("idCittadino = :idCittadino",
                                        Parameters.with("idCittadino", idCittadino));
                });

                if (!cittadiniList.isEmpty()) {
                        logAuditUtil.insertLogAudit(now, null, "cleanupOldDataScheduler",
                                        cittadiniList.stream().map(MipTCittadino::getIdCittadino)
                                                        .collect(Collectors.toList()),
                                        null, null);
                }

                logger.info("cleanupOldDataScheduler end");
        }
}