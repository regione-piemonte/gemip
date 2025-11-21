package it.csi.gemip.utils;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.panache.common.Parameters;
import io.quarkus.runtime.configuration.ConfigUtils;
import io.quarkus.scheduler.Scheduled;
import it.csi.gemip.mipcommonapi.integration.entities.MipDSoggettoAttuatore;
import it.csi.gemip.mipcommonapi.integration.entities.MipDTestoEmail;
import it.csi.gemip.mipcommonapi.integration.entities.MipRCittadinoIncontroPreacc;
import it.csi.gemip.mipcommonapi.integration.entities.MipTAnagraficaCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTCittadino;
import it.csi.gemip.mipcommonapi.integration.entities.MipTIncontroPreaccoglienza;
import it.csi.gemip.mipcommonapi.integration.entities.MipTMailInviata;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDSoggettoAttuatoreRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipDTestoEmailRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipRCittadinoIncontroPreaccRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTAnagraficaCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTCittadinoRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTIncontroPreaccoglienzaRepository;
import it.csi.gemip.mipcommonapi.integration.repositories.MipTMailInviataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.HttpHeaders;

@ApplicationScoped
public class MailUtil {
    public static final String COD_MAIL_10_POST_REGISTRAZIONE_ONLINE = "10-POST-REG-ONLINE";
    public static final String COD_MAIL_10_POST_REGISTRAZIONE_PRESENZA = "10-POST-REG-PRESENZA";
    public static final String COD_MAIL_20_REMINDER_PREACCOGLIENZA = "20-REMINDER-PREACC";
    public static final String COD_MAIL_30_QUESTIONARIO_1 = "30-QUESTIONARIO-1";
    public static final String COD_MAIL_40_SCELTA_TUTOR = "40-SCELTA-TUTOR";
    public static final String COD_MAIL_50_SCELTA_TUTOR_CONFERMA_CITTADINO = "50-CONF_TUTOR_CITTAD";
    public static final String COD_MAIL_50_SCELTA_TUTOR_CONFERMA_TUTOR = "50-CONF_TUTOR_TUTOR";
    public static final String COD_MAIL_55_RESET_TUTOR = "55-RESET_TUTOR";
    public static final String COD_MAIL_60_QUESTIONARIO_3 = "60-QUESTIONARIO-3";

    @Inject
    MipDTestoEmailRepository mipDTestoEmailRepository;
    @Inject
    MipDSoggettoAttuatoreRepository tutorRepository;
    @Inject
    MipTCittadinoRepository mipTCittadinoRepository;
    @Inject
    MipRCittadinoIncontroPreaccRepository mipRCittadinoIncontroPreaccRepository;
    @Inject
    MipTAnagraficaCittadinoRepository mipTAnagraficaCittadinoRepository;
    @Inject
    MipTIncontroPreaccoglienzaRepository mipTIncontroPreaccoglienzaRepository;
    @Inject
    MipTMailInviataRepository mipTMailInviataRepository;
    @Inject
    Mailer mailer;
    @Inject
    Logger logger;

    @ConfigProperty(name = "cron.expr")
    String cron;

    @Inject
    @ConfigProperty(name = "base.url")
    String baseUrl;

    @Inject
    @ConfigProperty(name = "mail.mailFrom")
    String mailFrom;

    @Inject
    @ConfigProperty(name = "mail.cc")
    String mailCC;

    /* nuovo metodo con i custom params da caricare per le mail pre accoglienza */
    public void sendEmailCittadino(String codTestoEmail, long idCittadino, Map<String, String> customParams,
            HttpHeaders httpHeaders) {
        registraEmailInviata(codTestoEmail, idCittadino, httpHeaders);
        MipDTestoEmail testoTemp = null;
        if (codTestoEmail != null)
            testoTemp = mipDTestoEmailRepository.findById(codTestoEmail);
        Map<String, String> params = makeMeParams(testoTemp, idCittadino);
        if (customParams != null)
            params.putAll(customParams); /* aggiungo anche per i customs */

        boolean html = testoTemp != null && "S".equals(testoTemp.getFlgHtml());
        sendEmail(params.get("cittadinoEmail"), params, testoTemp, html);
        if (params.containsKey("cittadinoEmail2")) {
            sendEmail(params.get("cittadinoEmail2"), params, testoTemp, html);
        }
    }

    private void registraEmailInviata(String codTestoEmail, long idCittadino, HttpHeaders httpHeaders) {
        Date adesso = mipTMailInviataRepository.getNow();
        if (codTestoEmail == null // Invio comunicazione
             || mipTMailInviataRepository.count("idCittadino = :idCittadino AND codTestoEmail = :codTestoEmail",
                Parameters.with("idCittadino", idCittadino).and("codTestoEmail", codTestoEmail)) == 0) { // Tutte le
                                                                                                         // altre mail
            MipTMailInviata mipTMailInviata = new MipTMailInviata();
            mipTMailInviata.setIdCittadino(idCittadino);
            mipTMailInviata.setCodTestoEmail(codTestoEmail);
            mipTMailInviata
                    .setCodUserAggiorn(httpHeaders != null && httpHeaders.getHeaderString("X_Current_User") != null
                            ? httpHeaders.getHeaderString("X_Current_User")
                            : "utente nullo");
            mipTMailInviata
                    .setCodUserInserim(httpHeaders != null && httpHeaders.getHeaderString("X_Current_User") != null
                            ? httpHeaders.getHeaderString("X_Current_User")
                            : "utente nullo");
            mipTMailInviata.setdInvio(adesso);
            mipTMailInviata.setDataInserim(adesso);
            mipTMailInviata.setDataAggiorn(adesso);
            mipTMailInviataRepository.persist(mipTMailInviata);
        }
    }

    /* nuovo metodo con i custom params da caricare per le mail pre accoglienza */
    public void sendEmailTutor(String codTestoEmail, long idCittadino, long idTutor, Map<String, String> customParams) {
        MipDTestoEmail testoTemp = mipDTestoEmailRepository.findById(codTestoEmail);
        Map<String, String> params = makeMeParams(testoTemp, idCittadino);
        MipDSoggettoAttuatore soggettoAttuatore = tutorRepository.findById(idTutor);
        customParams.put("mailCittadino.soggettoAttuatoreNome", soggettoAttuatore.getDenominazione());
        String destinatario = soggettoAttuatore.getEmail();
        params.putAll(customParams);
        boolean html = "S".equals(testoTemp.getFlgHtml());
        sendEmail(destinatario, params, testoTemp, html);
    }

    /**
     * Crea una mappa con i dati del cittadino
     */
    public Map<String, String> makeMeParams(MipDTestoEmail mipDTestoEmail, long idCittadino) {
        Map<String, String> params = new HashMap<>();
        MipTCittadino mipTCittadino = mipTCittadinoRepository.findById(idCittadino);
        MipRCittadinoIncontroPreacc mipRCittadinoIncontroPreacc = mipRCittadinoIncontroPreaccRepository
                .getUltimoIncontroPreaccoglienzaPerIdCittadino(
                        idCittadino);
        MipTAnagraficaCittadino anagrafica = mipTAnagraficaCittadinoRepository.findById(idCittadino);
        if (mipDTestoEmail != null) {
            params.put("fromEmail", mipDTestoEmail.getMittente());
        } else {
            params.put("fromEmail", mailFrom);
        }
        params.put("cittadinoEmail", anagrafica.getRecapitoEmail());
        if (anagrafica.getRecapitoEmail2() != null && !anagrafica.getRecapitoEmail2().isEmpty())
            params.put("cittadinoEmail2", anagrafica.getRecapitoEmail2());
        params.put("mailCittadino.cittadino", mipTCittadino.getNome() + " " + mipTCittadino.getCognome());
        params.put("mailCittadino.data",
                new SimpleDateFormat("dd/MM/yyyy").format(mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza()
                        .getDataIncontro()));
        if ("S".equals(mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getFlgIncontroErogatoDaRemoto())){
            params.put("mailCittadino.linkRiunione",
                    mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getLinkIncontroRemoto());
        }else{
            params.put("mailCittadino.sede",
                    mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getLuogoIncontro().getDenominazione() +
                    " - " + mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getLuogoIncontro().getIndirizzo() +
                    " " + mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getLuogoIncontro().getCodIstatComune().getDescrizioneComune()
                    + " (" + mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza().getLuogoIncontro().getCodIstatComune().getProvincia().getSiglaProvincia()+")");
        }
        params.put("mailCittadino.ora",
                new SimpleDateFormat("HH:mm:ss").format(mipRCittadinoIncontroPreacc.getIncontroPreaccoglienza()
                        .getDataIncontro()));
        params.put("mailCittadino.maillongo", "longo@consorziospazioformazione.it");
        params.put("mailCittadino.cf", mipTCittadino.getCodiceFiscale());
        params.put("mailCittadino.telefono", anagrafica.getRecapitoTelefono());
        params.put("mailCittadino.emailCittadino", anagrafica.getRecapitoEmail());
        params.put("mailCittadino.mailinfo", "infomip@mettersinproprio.it");
        params.put("mailCittadino.telinfo", "800.146.766");
        params.put("linkQuestionario", baseUrl + "/questionario");
        return params;
    }

    /**
     * Nel template, sostituisce tutti i parametri {{key}} con il relativo valore
     * params.get(key)
     */
    public String sostituisciParametri(String template, Map<String, String> params) {
        if (template != null)
            for (Map.Entry<String, String> entry : params.entrySet()) {
                // Usa replace al posto di replaceAll
                template = template.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        return template;
    }

    /**
     * Invia una mail in formato HTML. Il destinatario deve essere indicato nella
     * mappa dei parametri
     * con chiave "cittadinoEmail", il mittente con chiave "fromEmail".
     */
    public void sendEmail(String emailDestinatario, Map<String, String> params, MipDTestoEmail testoTemp,
            boolean html) {
        // in integrazione non inviamo le mail
        if (!"int".equals(ConfigUtils.getProfiles().get(0))) {
            try {
                String oggetto = (testoTemp == null) ? params.get("oggetto")
                        : sostituisciParametri(testoTemp.getOggetto(), params);
                String corpo = (testoTemp == null) ? params.get("corpo")
                        : sostituisciParametri(testoTemp.getCorpo(), params);

                Mail mail = html ? Mail.withHtml(emailDestinatario, oggetto, corpo)
                        : Mail.withText(emailDestinatario, oggetto, corpo);
                mail.setFrom(params.get("fromEmail"));

                List<String> ccList = new ArrayList<>();
                if (!"disabled".equals(this.mailCC)) {
                    ccList.addAll(split(this.mailCC));
                }
                if (params.containsKey("cc.vecchioTutor")) {
                    ccList.addAll(split(params.get("cc.vecchioTutor")));
                }
                if (!ccList.isEmpty()) {
                    mail.setCc(ccList);
                }
                mailer.send(mail);
            } catch (Exception exc) {
                logger.error("Cannot send mail to " + emailDestinatario, exc);
            }
        }
    }

    private List<String> split(String s) {
        if (s == null)
            return new ArrayList<>();
        return Arrays.asList(s.split(","));
    }

    @Transactional
    @Scheduled(cron = "{cron.expr}")
    void jobEmailAvvisoIncontriDiDomani() {
        if (!"int".equals(ConfigUtils.getProfiles().get(0))) {
            List<MipTIncontroPreaccoglienza> mipTIncontroPreaccoglienzaList = mipTIncontroPreaccoglienzaRepository
                    .findIncontriPreaccoglienzaDiDomani();
            mipTIncontroPreaccoglienzaList.forEach(incontroDiPreaccoglienza -> {
                List<MipTCittadino> mipTCittadinoList = mipRCittadinoIncontroPreaccRepository
                        .findCittadinoByIncontroPreaccoglienzaIscritto(
                                incontroDiPreaccoglienza);
                mipTCittadinoList.forEach(elem -> {
                    MipDTestoEmail codEmail = mipDTestoEmailRepository.findById(COD_MAIL_20_REMINDER_PREACCOGLIENZA);
                    Map<String, String> params = new HashMap<>();

                    params.put("mailCittadino.data",
                            new SimpleDateFormat("dd/MM/yyyy").format(incontroDiPreaccoglienza.getDataIncontro()));
                    params.put("mailCittadino.ora",
                            new SimpleDateFormat("HH:mm").format(incontroDiPreaccoglienza.getDataIncontro()));

                    sendEmailCittadino(codEmail.getCodTestoEmail(), elem.getIdCittadino(), params, null);
                });
            });
        }
    }
}
