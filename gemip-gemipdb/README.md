
# Prodotto - Componente
GEMIP - GEMIPDB

# Descrizione della componente
GEMIPDB è la componente DB del prodotto GEMIP.\
Il DBMS di riferimento è PostgreSQL.\
Tramite gli script qui presenti viene creato e popolato lo schema dati usato dalle altre componenti.\
Questa componente include:
- script DDL per la creazione delle sequence;
- script DDL per la creazione dello schema dati;
- script DML per il popolamento iniziale del DB;
- script DDL per la definizione dei vincoli (check + foreign key);
- script DDL per la creazione delle functions PL/pgSQL

Si fa presente che nello script DML per il popolamento iniziale del DB non sono presenti le insert per alcune tabelle necessarie per il funzionamento del sistema ma strettamente collegate alla Regione Piemonte o dipendenti dal contesto operativo.
Oltre alle tabelle previste nello script, vanno popolate anche le seguenti:
- ext_gmop_d_area_territoriale
- mip_d_cpi
- mip_d_domanda
- mip_d_ente
- mip_d_luogo_incontro
- mip_d_operatore
- mip_d_questionario
- mip_d_risposta
- mip_d_soggetto_affidatario
- mip_d_soggetto_attuatore
- mip_d_testo_email

# Configurazioni iniziali
Definire utente "gemip" su una istanza DBMS PostgreSQL (versione 15) proprietario dello schema.

# Getting Started
Una volta prelevata e portata in locale dal repository la componente ("git clone"), predisporsi per poter eseguire gli script nella sequenza indicata nel seguito.

# Prerequisiti di sistema
DBMS nella versione indicata, utente con permessi adeguati ad eseguire istruzioni di creazione tabelle.

# Installazione
Lanciare tutti gli script nella sequenza indicata dal prefisso del nome del file:

    01-sequences.sql
    02-tables.sql
    03-data.sql
    04-cheks.sql
    05-foreign_keys.sql
    06-functions.sql


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