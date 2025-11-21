# Prodotto - Componente
Prodotto GEMIP, componente MIPBACKOFFICEBFF

# Descrizione della componente
Componente Cloud Native che opera come BFF (Backend For Frontend) per l'applicativo _operatori_. \
Si basa sulla versione 3.4.2 di Quarkus.


# Generazione interfacce di servizio
Il progetto è impostato per lo sviluppo in modalità contract-first:
* Nella cartella ```src/main/resources/META-INF``` è presente il file openapi.yaml contenente la specifica openapi 3 dei servizi esposti da questa componente;
* Il pom.xml prevede l'utilizzo del plugin swagger (customizzato CSI) per la generazione delle sole interfacce JAX-RS a partire da tale file di specifiche. La generazione avviene in fase di compilazione (quindi può essere scatenata con il comando ```mvn clean compile```);
* Il generatore non genera le classi di implementazione delle API, che devono essere create manualmente;
* Le API sono definite sotto il prefisso ```/api```;
* Poiché l'uso del generatore crea una dipendenza non soddisfatta verso il registry CSI, questa sezione del POM dovrà essere disattivata: da riattivare qualora, in seguito ad apposita richiesta, si avesse a disposizione la libreria del generatore.

# Implementazioni predefinite

Sono predefiniti:
* il filtro per il recepimento dell'header Shibboleth (se autenticazione prevista - di default è disattivato: se necessario scommentare la annotation ```@Provider```)
* i filtri per la protezione cross site request forgery (XSRF)


# API strumentali

Il progetto è impostato per esporre una implementazione minimale di default delle API strumentali per:
* health check:
  * liveness: ```/q/health/live```
  * readyness: ```/q/health/ready```
* metrics:  ```/q/metrics```

  
# Opzioni per lo sviluppo
Trattandosi di una componente Quarkus standard basata su Maven si rimanda alle normali procedure previste per la compilazione e l'esecuzione.

Le seguenti indicazioni possono essere utili nel caso la componente debba essere eseguita tramite container e non si voglia fare ricorso a soluzioni più articolate come Docker Compose o il test locale su Kubernetes (tramite, per esempio, minikube, KinD...).


## Sviluppo componente isolata

Avvio progetto:
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -it -w /usr/src/mymaven maven:3.9.2-eclipse-temurin-17 mvn clean compile 
```
Esecuzione:

Lanciando il comando docker che utilizza una cartella .m2 di progetto
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8080:8080 -p 5005:5005 -it -w /usr/src/mymaven maven:3.9.2-eclipse-temurin-17 mvn quarkus:dev "-DdebugHost=0.0.0.0" "-Ddebug=5005" "-Dquarkus.http.host=0.0.0.0"
```
Lanciando il comando docker che utilizza la cartella .m2 globale
```
docker run -v ${PWD}:/usr/src/mymaven -v ${HOME}/.m2:/root/.m2 -p 8080:8080 -p 5005:5005 -it -w /usr/src/mymaven maven:3.9.2-eclipse-temurin-17 mvn quarkus:dev "-DdebugHost=0.0.0.0" "-Ddebug=5005" "-Dquarkus.http.host=0.0.0.0"
```

Se non è stato installato e reso raggiungibile il db, l'applicativo allo startup andra in eccezione ma sarà comunque possibile verificare che, per il resto, il dispiegamento sia stato effettuato correttamente puntando, per esempio a `http://localhost:8082/api/status` o a `http://localhost:8082/api/status/versione`.

Esempio di output nel caso di problemi allo startup invocando http://localhost:8082/api/status:

`{"title":null,"status":null,"code":500,"messaggioCittadino":null,"errorMessage":"Attenzione si e' verificato un errore inaspettato","fields":null}`


## Nel caso di sviluppo contemporaneo di più componenti
Effettuare il bind alla rete dell'host utilizzando l'opzione `--network host`. 

Il comando risultante è il seguente
```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8082:8082 -p 5005:5005 -it --network host -w /usr/src/mymaven maven:3.9.2-eclipse-temurin-17 mvn quarkus:dev "-DdebugHost=0.0.0.0" "-Ddebug=5005" "-Dquarkus.http.host=0.0.0.0"
```
Utilizzando questo comando, il container sarà disponibile alla porta configurata dal parametro `quarkus.http.port=<porta>` presente nel file application.properties (o configurabile aggiungendo `"-Dquarkus.http.port=<porta>"` al fondo del comando scritto sopra).

Il container della componente MIPBACKOFFICEBFF, lanciato con 

```
docker run -v ${PWD}:/usr/src/mymaven -v ${PWD}/.m2:/root/.m2 -p 8082:8082 -p 5005:5005 -it --network host -w /usr/src/mymaven maven:3.9.2-eclipse-temurin-17 mvn quarkus:dev "-DdebugHost=0.0.0.0" "-Ddebug=5005" "-Dquarkus.http.host=0.0.0.0" "-Dquarkus.http.port=8082"
```

potrà comunicare con la componente MIPCOMMONAPI in ascolto sulla porta 8080, invocando per esempio:
`localhost:8082/api/cittadini`


La coppia di valori `"-Ddebug"` e `"-Dquarkus.http.port"` deve essere diversa per ogni container, in modo da evitare sovrapposizioni di porte sull'host.

# Dispiegamento
Vengono utilizzate le normali procedure previste per l'installazione sul _runtime_ di destinazione.


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