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

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.jboss.logging.Logger;

import io.smallrye.jwt.build.Jwt;
import it.csi.gemip.mipbackofficebff.bff.RuoliApi;
import it.csi.gemip.mipbackofficebff.bff.dto.InfoOperatore;
import it.csi.gemip.mipbackofficebff.bff.dto.UserInfo;
import it.csi.gemip.mipbackofficebff.entities.MipDEnte;
import it.csi.gemip.mipbackofficebff.entities.MipDOperatore;
import it.csi.gemip.mipbackofficebff.entities.MipDSoggettoAffidatario;
import it.csi.gemip.mipbackofficebff.entities.MipDSoggettoAttuatore;
import it.csi.gemip.mipbackofficebff.entities.MipROperatoreEnte;
import it.csi.gemip.mipbackofficebff.entities.MipROperatoreSoggAffidatario;
import it.csi.gemip.mipbackofficebff.entities.MipROperatoreSoggAttuatore;
import it.csi.gemip.mipbackofficebff.exception.UtenteNonAbilitatoException;
import it.csi.gemip.mipbackofficebff.integration.SecurityHelper;
import it.csi.gemip.mipbackofficebff.integration.ws.cxf.flaidoor.Ruolo;
import it.csi.gemip.mipbackofficebff.utils.LogAuditUtil;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;


public class RuoliApiImpl extends  ParentApiImpl implements RuoliApi {
    @Inject
    SecurityHelper securityHelper;
    @Inject
    LogAuditUtil logAuditUtil;
    @Inject
    Logger logger = Logger.getLogger(RuoliApiImpl.class);

    @Override
    @Transactional
    public Response getRuoli(Long idOperatore,SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest httpRequest) {
        List<Ruolo> ruoli = securityHelper.findRuoliForPersonaInApplication(httpRequest);
        List<InfoOperatore> infoOperatoreList=securityHelper.getInfoOperatoreForRuolo(securityContext,httpHeaders,httpRequest);
        it.csi.gemip.mipbackofficebff.bff.dto.Ruolo ruolo=new it.csi.gemip.mipbackofficebff.bff.dto.Ruolo();
        ruolo.setCodiceRuolo(ruoli.get(0).getCodiceRuolo());
        ruolo.setCodiceDominio(ruoli.get(0).getCodiceDominio());
        ruolo.setMnemonico(ruoli.get(0).getMnemonico());
        ruolo.setEntiAssociati(infoOperatoreList);
        String codiceRuolo=ruoli.get(0).getCodiceRuolo();
        UserInfo userInfo= securityHelper.getCurrentUserInfo(httpRequest);
        userInfo.setIdOperatore(idOperatore);
        if(infoOperatoreList.isEmpty()){
            throw new UtenteNonAbilitatoException("Non sono stati trovati enti associati all'operatore");
        }
        Instant adesso = (Instant) MipDOperatore.getEntityManager().createQuery("select now()").getResultList().get(0);
        Date now=Date.from(adesso);
        switch (codiceRuolo){
            case "GEMIP_AFFIDATARIO_ALL": {
                for(InfoOperatore infoOperatore: infoOperatoreList) {
                        logger.info("ricerca soggetto affidatario: "+infoOperatore.getGruppoOperatore()+infoOperatore.getCodOperatore() );
                        MipDSoggettoAffidatario tmpSoggettoAffidatario = MipDSoggettoAffidatario.find("gruppoOperatore = ?1 and codOperatore=?2", infoOperatore.getGruppoOperatore(),infoOperatore.getCodOperatore()).firstResult();
                        if(tmpSoggettoAffidatario==null){
                            throw new UtenteNonAbilitatoException("I dati in locale non sono coerenti con quelli richiamati da flaidoor. Da Flaidoor:" + infoOperatore.getGruppoOperatore() + infoOperatore.getCodOperatore());
                        }else{
                            MipROperatoreSoggAffidatario tmpOpSog= MipROperatoreSoggAffidatario.find("idOperatore.id =?1 and idSoggettoAffidatario.id =?2 ",userInfo.getIdOperatore(),tmpSoggettoAffidatario.getId()).firstResult();
                            if(tmpOpSog==null){
                                tmpOpSog=new MipROperatoreSoggAffidatario();

                                MipDOperatore  tmpOp=MipDOperatore.findById(userInfo.getIdOperatore());
                                tmpOpSog.setIdOperatore(tmpOp);
                                tmpOpSog.setIdSoggettoAffidatario(tmpSoggettoAffidatario);
                                tmpOpSog.setCodUserInserim("SISTEMA");
                                tmpOpSog.setCodUserAggiorn("SISTEMA");
                                tmpOpSog.setDataAggiorn(now);
                                tmpOpSog.setDataInserim(now);
                                tmpOpSog.persist();
                                logAuditUtil.insertLogAudit(now,
                                    ((UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR)).getCodFisc(),
                                    getIncomingIPAddress(httpRequest),
                                    "getRuoli:GEMIP_AFFIDATARIO_ALL",
                                    tmpOp,
                                    httpRequest);
                            }
                        }
                    }
                break;
            }
                case "GEMIP_REGIONALE_ALL", "GEMIP_REGIONALE_BASE":{
                for(InfoOperatore infoOperatore: infoOperatoreList) {
                    logger.info("ricerca ente regionale: "+infoOperatore.getGruppoOperatore()+infoOperatore.getCodOperatore() );
                    MipDEnte tmpEnte = MipDEnte.find("gruppoOperatore = ?1 and codOperatore=?2", infoOperatore.getGruppoOperatore(),infoOperatore.getCodOperatore()).firstResult();
                    if(tmpEnte==null){
                        throw new UtenteNonAbilitatoException("I dati in locale non sono coerenti con quelli richiamati da flaidoor. Da Flaidoor: " + infoOperatore.getGruppoOperatore() + infoOperatore.getCodOperatore());
                    }else{
                        MipROperatoreEnte tmpOpEnte= MipROperatoreEnte.find("idOperatore.id =?1 and idEnte.id =?2 ",userInfo.getIdOperatore(),tmpEnte.getId()).firstResult();
                        if(tmpOpEnte==null){
                            tmpOpEnte=new MipROperatoreEnte();

                            MipDOperatore  tmpOp=MipDOperatore.findById(userInfo.getIdOperatore());
                            tmpOpEnte.setIdOperatore(tmpOp);
                            tmpOpEnte.setIdEnte(tmpEnte);
                            tmpOpEnte.setCodUserInserim("SISTEMA");
                            tmpOpEnte.setCodUserAggiorn("SISTEMA");
                            tmpOpEnte.setDataInserim(now);
                            tmpOpEnte.setDataAggiorn(now);
                            tmpOpEnte.persist();
                            logAuditUtil.insertLogAudit(now,
                                ((UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR)).getCodFisc(),
                                getIncomingIPAddress(httpRequest),
                                "getRuoli:GEMIP_REGIONALE_ALL/BASE",
                                tmpOp,
                                httpRequest);
                        }
                    }
                }
                break;
            }
            case "GEMIP_ATTUATORE_BASE":{
                for(InfoOperatore infoOperatore: infoOperatoreList) {
                    logger.info("ricerca soggetto attuatore: "+infoOperatore.getGruppoOperatore()+infoOperatore.getCodOperatore()+"-"+infoOperatore.getAreaTerritoriale());
                    MipDSoggettoAttuatore tmpSoggettoAttuatore= MipDSoggettoAttuatore.find("gruppoOperatore = ?1 and codOperatore=?2 and codiceAreaTerritoriale.codiceAreaTerritoriale = ?3",
                        infoOperatore.getGruppoOperatore(),
                        infoOperatore.getCodOperatore(),
                        infoOperatore.getAreaTerritoriale()).firstResult();
                    if(tmpSoggettoAttuatore==null){
                        String msg = "I dati in locale non sono coerenti con quelli richiamati da flaidoor. Da Flaidoor: " + infoOperatore.getGruppoOperatore() + infoOperatore.getCodOperatore()
                            + "-" + infoOperatore.getAreaTerritoriale();
                        logger.error(msg);
                        throw new UtenteNonAbilitatoException(msg);
                    }else{
                        logger.info("Soggetto trovato");
                        MipROperatoreSoggAttuatore tmpOpSogg= MipROperatoreSoggAttuatore.find("operatore.id =?1 and soggettoAttuatore.id =?2 ",userInfo.getIdOperatore(),tmpSoggettoAttuatore.getId()).firstResult();
                        if(tmpOpSogg==null){
                            tmpOpSogg=new MipROperatoreSoggAttuatore();

                            MipDOperatore  tmpOp=MipDOperatore.findById(userInfo.getIdOperatore());
                            tmpOpSogg.setOperatore(tmpOp);

                            tmpOpSogg.setSoggettoAttuatore(tmpSoggettoAttuatore);
                            tmpOpSogg.setCodUserInserim("SISTEMA");
                            tmpOpSogg.setCodUserAggiorn("SISTEMA");
                            tmpOpSogg.setDataAggiorn(now);
                            tmpOpSogg.setDataInserim(now);
                            tmpOpSogg.setDataRegistrazione(now);
                            tmpOpSogg.persist();
                            logAuditUtil.insertLogAudit(now,
                                ((UserInfo) httpRequest.getAttribute(USERINFO_SESSIONATTR)).getCodFisc(),
                                getIncomingIPAddress(httpRequest),
                                "getRuoli:GEMIP_ATTUATORE_BASE",
                                tmpOp,
                                httpRequest);
                        }
                    }
                }
                break;
            }
            default:{
                throw new UtenteNonAbilitatoException("non ci sono ruoli abilitati per l'utente");

            }
        }

        ruolo.setJwt(Jwt
            .issuer("http://localhost")
            .groups(codiceRuolo)
            .subject(codiceRuolo)
            .expiresIn(3600)
            .sign());

        return Response.ok(ruolo).build();
    }


}
