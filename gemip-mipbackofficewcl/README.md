# Prodotto - Componente
Prodotto GEMIP, componente MIPBACKOFFICEWCL

# Descrizione della componente
Repository per lo sviluppo cloud native della componente in tecnologia Angular.
Versione Angular di riferimento 14.x.

  
# Opzioni per lo sviluppo
Trattandosi di una componente Angular standard si rimanda alle normali procedure previste per la compilazione e l'esecuzione.

Le seguenti indicazioni possono essere utili nel caso la componente debba essere eseguita tramite container e non si voglia fare ricorso a soluzioni più articolate come Docker Compose o il test locale su Kubernetes (tramite, per esempio, minikube, KinD...).


## Sviluppo componente

Viene fornito un Dockerfile compatibile con le versioni richieste di Node e Angular.

Assumiamo che per lo sviluppo locale la componente rimanga in attesa sulla porta 16000.

Effettuare il build dell'immagine definita nel Dockerfile

`docker build -t mipbackofficewcl .`

e quindi effettuare il run del container

`docker run -p 4205:16000 mipcittadinowcl`

Per accedere alla pagina iniziale del sito digitare http://localhost:4205/


Se è stato configurato un proxypass in modo da puntare alla componente MIPBACKOFFICEBFF tramite /bff, il sistema sarà in grado di recuperare le risorse dal db, altrimenti la pagina richiesta verrà caricata ma il sistema mostrerà un messaggio di errore.

Sarà necessario, inoltre, adeguare l'applicativo in modo da gestire la fase di autorizzazione e profilazione che, in CSI, viene effettuata tramite il ricorso ai servizi della componente ORCHFLAI.

Tale componente è parte di una soluzione che non costituisce parte del presente rilascio ma viene comunque consegnato il relativo WSDL per l'eventuale implementazione.


# Dispiegamento
Vengono utilizzate le normali procedure previste per l'installazione sul _runtime_ di destinazione.

**NOTA**  
Per la messa in **produzione**, è necessario che i valori delle porte siano quelli di default. 


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