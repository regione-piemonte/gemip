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

public abstract class Constants {

	public static final String COD_COMPONENTE = "gemip";
	
	public static final int MAX_LOG_LENGTH = 5000;

	public static abstract class IRIDE_ATTRIBUTES {
		public static final String COMPONENT_NAME = "gemip";
		public static final String USERINFO_REQ_ATTR = "appDatacurrentUser";
		public static final String ID_REQ_ATTR = "iride2_id";
	}
	
	public static abstract class FLAIANAG {
		public final static String COD_MATERIA_FP = "FP";
	}

	public static abstract class CODE_ERROR {
		public static final int ERRORE_SQL = 1;
		public static final int ERRORE_PARAMETRO_MALFORMED = 2;
		public static final int ERRORE_PARAMETRO_OBLIGATORIO = 3;
	}

	public static abstract class LOGGING {
		public static final String LOGGER_NAME = "gemip";
		public static final int MAX_LOG_LENGTH = 5000;
	}

	public static final class TipoProtocollazione {
		public static final String DOM_ESP = "DOM_ESP";
	}
	
	public abstract static class INPUT_MISSING {
		public static final String CODICE_CONDIZIONE_OCCUPAZIONALE= "codiceCondizioneOccupazionale";
		public static final String ID_IDEA_DI_IMPRESA= "idIdeaDiImpresa";
		public static final String CITTADINO_ID_CITTADINO= "cittadino.idCittadino";
	}

	public abstract static class RISPOSTE_RESPONSE {
		public static final String INSERIMENTO_SUCCES="inserimento avvenuto con successo";
		public static final String MODIFICA_SUCCES="modifica avvenuta con successo";
		public static final String CANCELLATO_SUCCES="cancellazione avvenuta con successo";
		public static final String ASSOCIATO_SUCCES="associato con successo";
		public static final String DISASSOCIATO_SUCCES="disassociato con successo";
		public static final String ERRORE="errore";
	}
	
	public static abstract class STATO_IDEA_IMPRESA {

		public static final long INSERITO = 1L;
		public static final long ANNULLATA = 2L;
		public static final long MODIFICATA = 3L;
		public static final long ACCORPATA = 4L;
		public static final long NON_AMMISSIBILE = 5L;
		public static final long INCONTRO_PREACC = 6L;
		public static final long POST_PREACC = 7L;
		public static final long PATTO_SERVIZIO_FIRMATO = 8L;
		public static final long ACC_INDIVID_SVOLTA = 9L;
		public static final long ABBANDONATO = 10L;
		public static final long QUESTIONARIO_COMPILATO = 11L;
		public static final long ASSOC_SOGG_ATTUATORE = 12L;
		public static final long CONCLUSA = 13L;
	}
}
