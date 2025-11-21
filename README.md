# Prodotto

GEMIP : Gestione preaccoglienza e piattaforma operatori nell'ambito dell'iniziativa MIP _mettersi in proprio_ di Regione Piemonte.

# Descrizione del prodotto
Nell’ambito della programmazione e gestione delle misure a favore dell’autoimpiego e della creazione d’impresa/lavoro autonomo, nel corso del 2023 è stato realizzato il prodotto GEMIP (Gestione MIP) per permettere ai cittadini di prenotare un incontro di pre-accoglienza e orientamento.  

Il servizio di pre-accoglienza costituisce una parte della soluzione complessiva più ampia a supporto dell’erogazione dei servizi per l’imprenditoria e si concretizza in un prodotto accessibile al cittadino tramite autenticazione attraverso SPID, CIE, TS-CNS.

La soluzione vede quali destinatari del servizio di registrazione dell’utenza e di gestione della pre-accoglienza sia i cittadini potenzialmente interessati al Programma MIP (Mettersi in proprio), che i soggetti coinvolti nella gestione in senso ampio di tale Programma, ovvero i funzionari pubblici e i soggetti con ruoli di gestione del Programma, ovvero - in primis - l’appaltatore dei servizi trasversali, nonché i soggetti attuatori. 

Il servizio prevede una componente di front-end, (applicazione _partecipa_), rivolta ai cittadini per la gestione della pre-accoglienza ovvero quella fase volta a permettere ai cittadini interessati di prenotare un incontro introduttivo con gli operatori qualificati dei servizi trasversali che descriveranno al potenziale imprenditore le caratteristiche del Programma Mip e forniranno le informazioni preliminari. Il servizio di pre-accoglienza, infatti, secondo le regole definite dall’avviso di riferimento, costituisce la condizione obbligatoria per poter accedere al percorso MIP di Misura 1 con il soggetto attuatore.  

Il cittadino potrà accedere tramite SPID, CIE, TS-CNS alla propria area riservata dalla quale, dopo l’incontro di pre-accoglienza, potrà scegliere il soggetto attuatore per avviare il percorso di Misura 1 e potrà compilare i questionari di gradimento del servizio nei diversi momenti di rilevazione previsti. 

A seguito della prenotazione e dell’inserimento dei dati richiesti, il cittadino utente del sistema riceverà una mail automatica di conferma della prenotazione alla pre-accoglienza. Successivamente alla partecipazione alla pre-accoglienza, l’utente verrà abilitato ad effettuare la scelta dell’Ente Attuatore: in seguito a tale scelta, il soggetto attuatore potrà prendere in carico l’utente, registrando l’avvio, o meno, del percorso di Misura 1.  

Il servizio di back-end, (applicazione _operatori_), consente agli operatori, in base al ruolo, di visualizzare l’elenco delle idee di impresa inserite con possibilità di accedere al dettaglio della scheda registrata, di visualizzare e gli incontri di pre-accoglienza disponibili, di visualizzare tutte le informazioni relative all’incontro (data, orari, sede, capienza massima della sala), di scegliere il nominativo dell’operatore che ha tenuto l’incontro di pre-accoglienza, di visualizzare l’elenco di tutti gli utenti che si sono prenotati per partecipare a quello specifico incontro di pre-accoglienza, di aggiornare lo stato relativo alla partecipazione del destinatario all’incontro, di inviare comunicazioni agli iscritti all’incontro etc. 

Il soggetto attuatore potrà visualizzare gli utenti che hanno scelto di effettuare il percorso individuale con il solo soggetto attuatore loggato, visualizzare il dettaglio della proposta imprenditoriale, il questionario di gradimento compilato e indicare, al termine del primo incontro individuale, se è stata erogata la prima ora di accompagnamento e se è stato firmato il patto di servizio.  


Il prodotto è costituito da due applicativi web: 
* Partecipa \
  Area dedicata ai cittadini per la prenotazione di un incontro di preaccoglienza e la scelta del Soggetto Attuatore
- Operatori \
  Piattaforma a disposizione degli operatori di Back Office

\
Sono applicazioni Cloud Native, realizzate come Single Page Application, in Angular e Java (Quarkus). 

La soluzione è strutturata nelle seguenti componenti applicative:

* [gemipdb](./gemip-gemipdb/README.md): script DDL/DML per la creazione ed il popolamento iniziale del DB ;
* [mipcittadinowcl](./gemip-mipcittadinowcl/README.md): Client Web (Angular 14), front-end applicativo "Partecipa";
* [mipcittadinobff](./gemip-mipcittadinobff/README.md): Componente Quarkus di tipo *Backend For Frontend* che espone servizi REST a mipcittadinowcl;
* [mipbackofficewcl](./gemip-mipbackofficewcl/README.md): Client Web (Angular 14), front-end applicativo "Operatori";
* [mipbackofficebff](./gemip-mipbackofficebff/README.md): Componente Quarkus di tipo *Backend For Frontend* che espone servizi REST a mipbackofficewcl;
* [mipcommonapi](./gemip-mipcommonapi/README.md): Componente Quarkus di esposizione servizi (REST API) che implementano le logiche comuni alle due soluzioni.
	

# Prerequisiti di sistema

* Istanza PostgreSQL (v. 15 o superiore);
* Utenza con privilegi per la creazione degli oggetti DB (tramite le istruzioni DDL messe a disposizione nella componente **gemipdb**);
* Node 16.x e Angular 14.x (per lo sviluppo delle componenti Angular);
* Quarkus 3.4.2, Quarkus 3.8.6 e Maven 3.11.0 (per lo sviluppo delle componenti Java);
* Adoptium Eclipse Temurin 17.0.8.1 (Virtual Machine Java di riferimento);
* Docker per l'eventuale sviluppo e dispiegamento in container.

Per l'autenticazione e la profilazione degli utenti dell'app "Operatori", GEMIP è integrato con servizi trasversali del sistema informativo regionale (rispettivamente **Shibboleth** e **Flaidoor**).
\
Per l'utilizzo in un altro contesto, occorre avere a disposizione servizi analoghi o integrare moduli opportuni che svolgano analoghe funzionalità.

Per quanto riguarda i servizi di profilazione/autorizzazione, vengono messi a disposizione i WSDL con le specifiche del servizio invocato.

Alcune categorie di dati particolari e la tabella che implementa il log di *audit*, vengono cifrati (cifratura applicativa su colonna).
\
Viene utilizzata una chiave di cifratura che dev'essere passata applicativamente, o utilizzata nel tool client del db, in modo che le procedure sql messe a disposizione possano effettuare la cifratura e la decifratura del dato.

La chiave dev'essere generata, trattata come *secret* e veicolata, di conseguenza, nei diversi ambienti di utilizzo.

Le procedure in oggetto utilizzano l'estensione *pgcrypto* di PostgreSQL.

 

# Installazione
Trattandosi di applicativi realizzati secondo l'approccio Cloud Native, le componenti sono dispiegate su cluster Kubernetes e configurate tramite Helm Charts.

Gli artefatti prodotti dalla fase di build, però, non presentano vincoli circa la piattaforma di dispiegamento e, quindi, possono essere installati sia su Virtual Machine che su Container.
 
Creare lo schema del DB, tramite gli script della componente **gemipdb**.

Configurare la connessione al DB di riferimento.

Configurare i web server e definire i Virtual Host per l'esposizione delle componenti WCL.

Le componenti WCL sono configurate in modo da puntare alle componenti di backend Java attraverso path di tipo "/bff".

Configurare un eventuale proxypass da "/bff" a "host:port/api" in modo da indirizzare le componenti di tipo BFF.

A loro volta, le componenti di tipo BFF hanno una configurazione (application.properties o altro) in modo da indirizzare la componente di tipo API con un puntamento del tipo "host:port/api".

Vengono utlizzati certificati SSL per l'utilizzo del protocollo https.

Nel caso si vogliano sfruttare le funzionalità di invio mail, occorre configurare un mail-server.

# Deployment
Indipendentemente dal tipo di piattaforma target, particolare attenzione dev'essere prestata alla valorizzazione delle properties utilizzate nell'ambiente di runtime: es. password di connessione al db, chiave di cifratura dei dati riservati...
\
L'approccio seguito è che le properties presenti in configurazione (es. in application.properties), siano valorizzate sempre come profilo di produzione e solo se non si tratti di secrets: questi ultimi sono comunque reperiti a runtime.

Nell'attuale implementazione tutti i secrets sono forniti al container applicativo tramite *sidecar container* che li recupera da Vault.

# Versioning
Per la gestione del codice sorgente viene utilizzato Git.
\
Per il versionamento del software si usa la tecnica Semantic Versioning (http://semver.org).


# Copyrights
© Copyright Regione Piemonte – 2025


# License
SPDX-License-Identifier: EUPL-1.2-or-later .\
Questo software è distribuito con licenza EUPL-1.2 .\
Consultare il file LICENSE.txt per i dettagli sulla licenza.