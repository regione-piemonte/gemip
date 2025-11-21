create table if not exists csi_log_audit
(
   data_ora       timestamp       not null,
   id_app         varchar(100)    not null,
   ip_address     varchar(40),
   utente         bytea           not null,
   operazione     varchar(50)     not null,
   resource_oper  varchar(4000),
   tab_oper       varchar(100),
   key_oper       varchar(500),
   ogg_oper       varchar(4000)
);

comment on column csi_log_audit.data_ora is 'Data e ora dell''evento (formato nativo, con precisione al secondo)';
comment on column csi_log_audit.id_app is 'Codice identificativo dell''applicazione utilizzata dall''utente; da comporre con i valori presenti in Anagrafica Prodotti: <codice prodotto>_<codice linea cliente>_<codice ambiente>_<codice Unità di Installazione>';
comment on column csi_log_audit.ip_address is 'Ip del client utente (se possibile)';
comment on column csi_log_audit.utente is 'Identificativo univoco (cifrato) dell''utente che ha effettuato l''operazione (es. login / codice fiscale / matricola / ecc.)';
comment on column csi_log_audit.operazione is 'Questo campo dovrà contenere l''informazione circa l''operazione effettuata; utilizzare uno dei seguenti valori:  login / logout / read / insert / update / delete / merge  Nei casi in cui il nome dell''operazione di business sia significativo e non riconducibile all''elenco di cui sopra, è possibile indicare il nome dell''operazione.';
comment on column csi_log_audit.resource_oper is 'Questa campo dovrà contenere l''endpoint con l''azione effettuata sulla risorsa';
comment on column csi_log_audit.tab_oper is 'Questo campo dovrà contenere il nome della tabella interessata dall''operazione effettuata';
comment on column csi_log_audit.key_oper is 'Questo campo dovrà contenere l''identificativo univoco dell''oggetto dell''operazione oppure nel caso di aggiornamenti multipli del valore che caratterizza l''insieme di oggetti (es: modifica di un dato in tutta una categoria merceologica)';
comment on column csi_log_audit.ogg_oper is 'Questo campo consentirà di identificare i dati e le informazioni trattati dall''operazione. Se la funzionalità lo permette inserire il nome delle tabelle (o in alternativa degli oggetti/entità) su cui viene eseguita l''operazione;  l''indicazione della colonna è opzionale e andrà indicata nel formato tabella.colonna. Se l''applicativo prevede accessi a schemi dati esterni premettere il nome dello schema proprietario al nome della tabella.';

create table if not exists ext_gmop_d_area_territoriale
(
   cod_area_territoriale    varchar(2)     not null,
   descr_area_territoriale  varchar(100)   not null,
   d_inizio                 timestamp      not null,
   d_fine                   timestamp,
   descr_breve_area_terr    varchar(30)
);

alter table ext_gmop_d_area_territoriale
   add constraint pk_ext_gmop_d_area_territoriale
   primary key (cod_area_territoriale);

create table if not exists ext_stati_esteri
(
   cod_stato          numeric(3)    not null,
   descrizione_stato  varchar(30)   not null,
   stato_cod_fiscale  varchar(4),
   sigla_nazione      varchar(2)
);

alter table ext_stati_esteri
   add constraint pk_ext_stati_esteri
   primary key (cod_stato);

create table if not exists ext_tab_cittadinanza
(
   cod_istat_cittadinanza  varchar(3)     not null,
   descr_cittadinanza      varchar(100)   not null,
   cod_nazionalita         varchar(1),
   flg_sett_lav            varchar(1),
   codice                  varchar(3),
   descrizione_precedente  varchar(100),
   d_inizio                timestamp      not null,
   d_fine                  timestamp
);

alter table ext_tab_cittadinanza
   add constraint pk_ext_tab_cittadinanza
   primary key (cod_istat_cittadinanza);

create table if not exists ext_tab_nazionalita
(
   cod_nazionalita  varchar(1)    not null,
   descrizione      varchar(20)   not null
);

alter table ext_tab_nazionalita
   add constraint pk_ext_tab_nazionalita
   primary key (cod_nazionalita);

create table if not exists ext_tt_comune
(
   comune       varchar(6)    not null,
   prov         varchar(3)    not null,
   descom       varchar(30)   not null,
   cap          varchar(5),
   codfisc      varchar(4),
   montana      varchar(2),
   ussl         varchar(3),
   zonaalt      varchar(1),
   zonaalts1    varchar(1),
   zonaalts2    varchar(1),
   regagri      varchar(2),
   prov_old     varchar(3),
   comune_old   varchar(6),
   usl_old      varchar(3),
   prov_new     varchar(1),
   popolazione  numeric(10),
   prefisso     varchar(5),
   cod_bacino   numeric(2)
);

alter table ext_tt_comune
   add constraint pk_ext_tt_comune
   primary key (comune);

create index if not exists ie_ext_tt_comune_01 on ext_tt_comune using btree (cod_bacino);

create table if not exists ext_tt_provincia
(
   prov     char(3)       not null,
   regione  varchar(2)    not null,
   desprov  varchar(30)   not null,
   sigprov  varchar(2)    not null
);

alter table ext_tt_provincia
   add constraint pk_ext_tt_provincia
   primary key (prov);

create table if not exists mip_d_condizione_familiare
(
   id_condizione_familiare     numeric(2)     not null,
   descr_condizione_familiare  varchar(200)   not null,
   d_inizio                    timestamp      not null,
   d_fine                      timestamp
);

alter table mip_d_condizione_familiare
   add constraint pk_mip_d_condizione_familiare
   primary key (id_condizione_familiare);

create table if not exists mip_d_condizione_occupazionale
(
   cod_condizione_occupazionale    varchar(2)     not null,
   descr_condizione_occupazionale  varchar(100)   not null,
   d_inizio                        timestamp      not null,
   d_fine                          timestamp
);

alter table mip_d_condizione_occupazionale
   add constraint pk_mip_d_condizione_occupazionale
   primary key (cod_condizione_occupazionale);

create table if not exists mip_d_cpi
(
   id_cpi            numeric(3)     not null,
   codice_cpi        varchar(11)    not null,
   cpi               varchar(50)    not null,
   gruppo_operatore  varchar(1)     not null,
   cod_operatore     numeric(5)     not null,
   sede              varchar(4)     not null,
   indirizzo         varchar(300)   not null,
   cap               varchar(5)     not null,
   cod_istat_comune  varchar(6)     not null,
   cod_provincia     varchar(3)     not null,
   telefono          varchar(20)    not null,
   telefono_2        varchar(20),
   telefono_3        varchar(20),
   numero_verde      varchar(20),
   email             varchar(60),
   indirizzo_web     varchar(50)
);

alter table mip_d_cpi
   add constraint pk_mip_d_cpi
   primary key (id_cpi);

create unique index ak_mip_d_cpi_01 on mip_d_cpi using btree (codice_cpi);

create index if not exists ie_mip_d_cpi_02 on mip_d_cpi using btree (gruppo_operatore, cod_operatore);



comment on table mip_d_cpi is 'Tavola dei CPI: dati ereditati da SILP';

create table if not exists mip_d_domanda
(
   id_domanda                numeric(10)    not null,
   id_questionario           numeric(10)    not null,
   testo_domanda             varchar(255)   not null,
   cod_user_inserim          varchar(16)    not null,
   d_inserim                 timestamp      not null,
   cod_user_aggiorn          varchar(16)    not null,
   d_aggiorn                 timestamp      not null,
   id_fase_questionario      numeric(2)     not null,
   tipo_domanda              varchar(1),
   placeholder               varchar(100),
   id_risposta_condizionale  numeric(10)
);

alter table mip_d_domanda
   add constraint pk_mip_d_domanda
   primary key (id_domanda);

comment on column mip_d_domanda.tipo_domanda is 'Se valorizzato, puo'' valere M (= Risposta Multipla) o S (= Risposta Singola)';
comment on column mip_d_domanda.id_risposta_condizionale is 'Indica l''eventuale risposta che condiziona la visualizzazione di questa domanda';

create table if not exists mip_d_ente
(
   id_ente           numeric(2)    not null,
   descrizione_ente  varchar(50)   not null,
   gruppo_operatore  varchar(1)    not null,
   cod_operatore     numeric(5)    not null,
   telefono          varchar(50),
   email             varchar(80),
   cod_user_inserim  varchar(16)   not null,
   d_inserim         timestamp     not null,
   cod_user_aggiorn  varchar(16)   not null,
   d_aggiorn         timestamp     not null
);

alter table mip_d_ente
   add constraint pk_mip_d_ente
   primary key (id_ente);

create unique index ak_mip_d_ente_01 on mip_d_ente using btree (gruppo_operatore, cod_operatore);

create table if not exists mip_d_fase_questionario
(
   id_fase_questionario     numeric(2)     not null,
   descr_fase_questionario  varchar(250)   not null
);

alter table mip_d_fase_questionario
   add constraint pk_mip_d_fase_questionario
   primary key (id_fase_questionario);

create table if not exists mip_d_fonte_conoscenza_mip
(
   cod_fonte_conoscenza_mip    varchar(2)     not null,
   descr_fonte_conoscenza_mip  varchar(100)   not null,
   d_inizio                    timestamp      not null,
   d_fine                      timestamp
);

alter table mip_d_fonte_conoscenza_mip
   add constraint pk_mip_d_fonte_conoscenza_mip
   primary key (cod_fonte_conoscenza_mip);

create table if not exists mip_d_luogo_incontro
(
   id_luogo_incontro      numeric(10)    not null,
   cod_area_territoriale  varchar(2)     not null,
   denominazione          varchar(100)   not null,
   indirizzo              varchar(255)   not null,
   cap                    varchar(5)     not null,
   cod_istat_comune       varchar(6)     not null
);

alter table mip_d_luogo_incontro
   add constraint pk_mip_d_luogo_incontro
   primary key (id_luogo_incontro);

create index if not exists ie_mip_d_luogo_incontro_01 on mip_d_luogo_incontro using btree (cod_area_territoriale);
create index if not exists ie_mip_d_luogo_incontro_02 on mip_d_luogo_incontro using btree (cod_istat_comune);

create table if not exists mip_d_operatore
(
   id_operatore                  numeric(10)    not null,
   cod_fiscale_utente            varchar(16)    not null,
   cognome                       varchar(100)   not null,
   nome                          varchar(100)   not null,
   telefono                      varchar(100),
   email                         varchar(250),
   d_registrazione               timestamp      not null,
   d_disabilitazione             timestamp,
   id_operatore_disabilitazione  numeric(10),
   cod_user_inserim              varchar(16)    not null,
   d_inserim                     timestamp      not null,
   cod_user_aggiorn              varchar(16)    not null,
   d_aggiorn                     timestamp      not null
);

alter table mip_d_operatore
   add constraint pk_mip_d_operatore
   primary key (id_operatore);

create unique index ak_mip_d_operatore_01 on mip_d_operatore using btree (cod_fiscale_utente);

create index if not exists ie_mip_d_operatore_01 on mip_d_operatore using btree (id_operatore_disabilitazione);



comment on column mip_d_operatore.cod_fiscale_utente is 'Stiamo ipotizzando che il cod_fiscale_utente corrisponda al cod_fiscale della tabella utente_abilitato di flaidoor';

create table if not exists mip_d_questionario
(
   id_questionario   numeric(10)    not null,
   descrizione       varchar(255)   not null,
   cod_user_inserim  varchar(16)    not null,
   d_inserim         timestamp      not null,
   cod_user_aggiorn  varchar(16)    not null,
   d_aggiorn         timestamp      not null
);

alter table mip_d_questionario
   add constraint pk_mip_d_questionario
   primary key (id_questionario);

create table if not exists mip_d_risposta
(
   id_risposta              numeric(10)    not null,
   id_domanda               numeric(10)    not null,
   testo_risposta           varchar(255),
   cod_user_inserim         varchar(16)    not null,
   d_inserim                timestamp      not null,
   cod_user_aggiorn         varchar(16)    not null,
   d_aggiorn                timestamp      not null,
   flg_richiesto_dettaglio  varchar(1)     not null,
   placeholder              varchar(100)
);

alter table mip_d_risposta
   add constraint pk_mip_d_risposta
   primary key (id_risposta);

comment on column mip_d_risposta.flg_richiesto_dettaglio is 'Puo'' valere S/N';

create table if not exists mip_d_soggetto_affidatario
(
   id_soggetto_affidatario  numeric(10)    not null,
   gruppo_operatore         varchar(1)     not null,
   cod_operatore            numeric(5)     not null,
   denominazione            varchar(250)   not null,
   telefono                 varchar(50),
   email                    varchar(80),
   cod_user_inserim         varchar(16)    not null,
   d_inserim                timestamp      not null,
   cod_user_aggiorn         varchar(16)    not null,
   d_aggiorn                timestamp      not null
);

alter table mip_d_soggetto_affidatario
   add constraint pk_mip_d_soggetto_affidatario
   primary key (id_soggetto_affidatario);

create unique index ak_mip_d_soggetto_affidatario_01 on mip_d_soggetto_affidatario using btree (gruppo_operatore, cod_operatore);

create table if not exists mip_d_soggetto_attuatore
(
   id_soggetto_attuatore  integer        not null,
   gruppo_operatore       varchar(1)     not null,
   cod_operatore          numeric(5)     not null,
   denominazione          varchar(250)   not null,
   telefono               varchar(50),
   email                  varchar(80),
   cod_area_territoriale  varchar(2),
   cod_user_inserim       varchar(16)    not null,
   d_inserim              timestamp      not null,
   cod_user_aggiorn       varchar(16)    not null,
   d_aggiorn              timestamp      not null,
   d_disabilitazione      timestamp
);

alter table mip_d_soggetto_attuatore
   add constraint pk_serse_t_soggetto_attuatore
   primary key (id_soggetto_attuatore);

create unique index ak_mip_d_soggetto_attuatore_01 on mip_d_soggetto_attuatore using btree (gruppo_operatore, cod_operatore, cod_area_territoriale);
create index if not exists ie_mip_d_soggetto_attuatore_01 on mip_d_soggetto_attuatore using btree (cod_area_territoriale);

comment on column mip_d_soggetto_attuatore.d_disabilitazione is 'Se valorizzata, il cittadino non puo'' selezionare il soggetto attuatore come tutor, a partire dalla data valorizzata. L''operatore invece puÃ² comunque ad accedere al BO';

create table if not exists mip_d_stato_idea_di_impresa
(
   id_stato_idea_di_impresa     numeric(2)     not null,
   descr_stato_idea_di_impresa  varchar(100)   not null,
   d_inizio                     timestamp      not null,
   d_fine                       timestamp
);

alter table mip_d_stato_idea_di_impresa
   add constraint pk_mip_d_stato_idea_di_impresa
   primary key (id_stato_idea_di_impresa);

create table if not exists mip_d_svantaggio_abitativo
(
   id_svantaggio_abitativo     numeric(2)     not null,
   descr_svantaggio_abitativo  varchar(200)   not null,
   d_inizio                    timestamp      not null,
   d_fine                      timestamp
);

alter table mip_d_svantaggio_abitativo
   add constraint pk_mip_d_svantaggio_abitativo
   primary key (id_svantaggio_abitativo);

comment on table mip_d_svantaggio_abitativo is 'Analoga alla tabella progr_d_svantaggio_abitativo di FLAIDOM';

create table if not exists mip_d_testo_email
(
   cod_testo_email  varchar(20)     not null,
   mittente         varchar(100)    not null,
   oggetto          varchar(100)    not null,
   corpo            varchar(4000)   not null,
   flg_html         varchar(1)      not null
);

alter table mip_d_testo_email
   add constraint pk_mip_d_testo_email
   primary key (cod_testo_email);

comment on column mip_d_testo_email.flg_html is 'Puo'' valere S o N';

create table if not exists mip_d_tipo_documento
(
   cod_tipo_documento    varchar(2)     not null,
   descr_tipo_documento  varchar(100)   not null,
   d_inizio              timestamp      not null,
   d_fine                timestamp
);

alter table mip_d_tipo_documento
   add constraint pk_mip_d_tipo_documento
   primary key (cod_tipo_documento);

create table if not exists mip_d_titolo_di_studio
(
   cod_titolo_di_studio    varchar(2)     not null,
   descr_titolo_di_studio  varchar(200)   not null,
   d_inizio                timestamp      not null,
   d_fine                  timestamp
);

alter table mip_d_titolo_di_studio
   add constraint pk_mip_d_titolo_di_studio
   primary key (cod_titolo_di_studio);

create table if not exists mip_r_cittadino_fase_questionario
(
   id_cittadino          numeric(10)   not null,
   id_fase_questionario  numeric(2)    not null,
   d_inizio              timestamp     not null,
   d_fine                timestamp,
   cod_user_inserim      varchar(16)   not null,
   d_inserim             timestamp     not null,
   cod_user_aggiorn      varchar(16)   not null,
   d_aggiorn             timestamp     not null
);

alter table mip_r_cittadino_fase_questionario
   add constraint pk_mip_r_cittadino_fase_questionario
   primary key (id_cittadino, id_fase_questionario);

create table if not exists mip_r_cittadino_incontro_preacc
(
   id_cittadino_incontro_preacc       numeric(10)     not null,
   id_cittadino                       numeric(10)     not null,
   id_incontro_preaccoglienza         numeric(10)     not null,
   cod_area_territoriale_selezionata  varchar(2)      not null,
   flg_cittadino_presente             varchar(1),
   d_annullamento                     timestamp,
   note                               varchar(4000),
   cod_user_inserim                   varchar(16)     not null,
   d_inserim                          timestamp       not null,
   cod_user_aggiorn                   varchar(16)     not null,
   d_aggiorn                          timestamp       not null
);

alter table mip_r_cittadino_incontro_preacc
   add constraint pk_mip_r_cittadino_incontro_preacc
   primary key (id_cittadino_incontro_preacc);

create unique index ak_mip_r_cittadino_incontro_preacc_01 on mip_r_cittadino_incontro_preacc using btree (id_cittadino, id_incontro_preaccoglienza);

create index if not exists ie_mip_r_cittadino_incontro_preacc_02 on mip_r_cittadino_incontro_preacc using btree (id_incontro_preaccoglienza);

create index if not exists ie_mip_r_cittadino_incontro_preacc_03 on mip_r_cittadino_incontro_preacc using btree (cod_area_territoriale_selezionata);



comment on column mip_r_cittadino_incontro_preacc.cod_area_territoriale_selezionata is 'Rappresenta l''area territoriale scelta dal cittadino in fase di scelta dell''incontro di preaccoglienza a cui partecipare';
comment on column mip_r_cittadino_incontro_preacc.flg_cittadino_presente is 'Indica se il cittadino ha effettivamente partecipato all''incontro.';

create table if not exists mip_r_compilazione_questionario_fase
(
   id_compilazione_questionario  numeric(10)   not null,
   id_fase_questionario          numeric(2)    not null,
   d_compilazione                timestamp     not null,
   cod_user_inserim              varchar(16)   not null,
   d_inserim                     timestamp     not null,
   cod_user_aggiorn              varchar(16)   not null,
   d_aggiorn                     timestamp     not null
);

alter table mip_r_compilazione_questionario_fase
   add constraint pk_mip_r_compilazione_questionario_fase
   primary key (id_compilazione_questionario, id_fase_questionario);

create table if not exists mip_r_idea_di_impresa_cittadino
(
   id_idea_di_impresa  numeric(10)   not null,
   id_cittadino        numeric(10)   not null,
   d_associazione      timestamp     not null,
   cod_user_inserim    varchar(16)   not null,
   d_inserim           timestamp     not null,
   cod_user_aggiorn    varchar(16)   not null,
   d_aggiorn           timestamp     not null
);

alter table mip_r_idea_di_impresa_cittadino
   add constraint pk_mip_r_idea_di_impresa_cittadino
   primary key (id_idea_di_impresa, id_cittadino);

create index if not exists ie_mip_r_idea_di_impresa_cittadino_01 on mip_r_idea_di_impresa_cittadino using btree (id_cittadino);

create table if not exists mip_r_incontro_preacc_area_terr
(
   id_incontro_preaccoglienza  numeric(10)   not null,
   cod_area_territoriale       varchar(2)    not null,
   cod_user_inserim            varchar(16)   not null,
   d_inserim                   timestamp     not null,
   cod_user_aggiorn            varchar(16)   not null,
   d_aggiorn                   timestamp     not null
);

alter table mip_r_incontro_preacc_area_terr
   add constraint pk_mip_r_incontro_preacc_area_terr
   primary key (id_incontro_preaccoglienza, cod_area_territoriale);

create index if not exists ie_mip_r_incontro_preacc_area_terr_01 on mip_r_incontro_preacc_area_terr using btree (cod_area_territoriale);

create table if not exists mip_r_operatore_cpi
(
   id_operatore_cpi  numeric(10)   not null,
   id_operatore      numeric(10)   not null,
   id_cpi            numeric(3)    not null,
   cod_user_inserim  varchar(16)   not null,
   d_inserim         timestamp     not null,
   cod_user_aggiorn  varchar(16)   not null,
   d_aggiorn         timestamp     not null
);

alter table mip_r_operatore_cpi
   add constraint pk_mip_r_operatore_cpi
   primary key (id_operatore_cpi);

create unique index ak_mip_r_operatore_cpi_01 on mip_r_operatore_cpi using btree (id_operatore);
create index if not exists ie_mip_r_operatore_cpi_01 on mip_r_operatore_cpi using btree (id_cpi);

create table if not exists mip_r_operatore_ente
(
   id_operatore_ente  numeric(10)   not null,
   id_operatore       numeric(10)   not null,
   id_ente            numeric       not null,
   cod_user_inserim   varchar(16)   not null,
   d_inserim          timestamp     not null,
   cod_user_aggiorn   varchar(16)   not null,
   d_aggiorn          timestamp     not null
);

alter table mip_r_operatore_ente
   add constraint pk_mip_r_operatore_ente
   primary key (id_operatore_ente);

create unique index ak_mip_r_operatore_ente_01 on mip_r_operatore_ente using btree (id_operatore);
create index if not exists ie_mip_r_operatore_ente_01 on mip_r_operatore_ente using btree (id_ente);

create table if not exists mip_r_operatore_idea_di_impresa_dismessa
(
   id_operatore_attuatore  numeric(10)   not null,
   id_idea_di_impresa      numeric(10)   not null,
   cod_user_inserim        varchar(16)   not null,
   d_inserim               timestamp     not null,
   cod_user_aggiorn        varchar(16)   not null,
   d_aggiorn               timestamp     not null
);

alter table mip_r_operatore_idea_di_impresa_dismessa
   add constraint pk_mip_r_operatore_idea_di_impresa
   primary key (id_operatore_attuatore, id_idea_di_impresa);

create index if not exists ie_mip_r_operatore_idea_di_impresa_01 on mip_r_operatore_idea_di_impresa_dismessa using btree (id_idea_di_impresa);

create table if not exists mip_r_operatore_incontro_preaccoglienza
(
   id_incontro_preaccoglienza  numeric(10)   not null,
   id_operatore_affidatario    numeric(10)   not null,
   cod_user_inserim            varchar(16)   not null,
   d_inserim                   timestamp     not null,
   cod_user_aggiorn            varchar(16)   not null,
   d_aggiorn                   timestamp     not null
);

alter table mip_r_operatore_incontro_preaccoglienza
   add constraint pk_mip_r_operatore_incontro_preaccoglienza
   primary key (id_incontro_preaccoglienza, id_operatore_affidatario);

create index if not exists ie_mip_r_operatore_incontro_preaccoglienza_01 on mip_r_operatore_incontro_preaccoglienza using btree (id_operatore_affidatario);

create table if not exists mip_r_operatore_sogg_affidatario
(
   id_operatore_affidatario  numeric(10)   not null,
   id_operatore              numeric(10)   not null,
   id_soggetto_affidatario   numeric(10),
   cod_user_inserim          varchar(16)   not null,
   d_inserim                 timestamp     not null,
   cod_user_aggiorn          varchar(16)   not null,
   d_aggiorn                 timestamp     not null
);

alter table mip_r_operatore_sogg_affidatario
   add constraint pk_mip_r_operatore_sogg_affidatario
   primary key (id_operatore_affidatario);

create unique index ak_mip_r_operatore_sogg_affidatario_01 on mip_r_operatore_sogg_affidatario using btree (id_operatore);
create index if not exists ie_mip_r_operatore_sogg_affidatario_01 on mip_r_operatore_sogg_affidatario using btree (id_soggetto_affidatario);

create table if not exists mip_r_operatore_sogg_attuatore
(
   id_operatore_sogg_attuatore   numeric(10)   not null,
   id_operatore                  numeric(10)   not null,
   id_soggetto_attuatore         integer       not null,
   d_registrazione               timestamp     not null,
   d_disabilitazione             timestamp,
   id_operatore_disabilitazione  numeric(10),
   cod_user_inserim              varchar(16)   not null,
   d_inserim                     timestamp     not null,
   cod_user_aggiorn              varchar(16)   not null,
   d_aggiorn                     timestamp     not null
);

alter table mip_r_operatore_sogg_attuatore
   add constraint pk_mip_r_operatore_sogg_attuatore
   primary key (id_operatore_sogg_attuatore);

create unique index ak_mip_r_operatore_sogg_attuatore_01 on mip_r_operatore_sogg_attuatore using btree (id_operatore, id_soggetto_attuatore, COALESCE(d_disabilitazione, '2999-12-31 23:59:59'::timestamp without time zone));
create index if not exists ie_mip_r_operatore_sogg_attuatore_01 on mip_r_operatore_sogg_attuatore using btree (id_soggetto_attuatore);
create index if not exists ie_mip_r_operatore_sogg_attuatore_02 on mip_r_operatore_sogg_attuatore using btree (id_operatore_disabilitazione);

create table if not exists mip_t_anagrafica_cittadino
(
   id_cittadino                         numeric(10)     not null,
   sesso                                varchar(1),
   d_nascita                            timestamp       not null,
   cod_istat_comune_nascita             varchar(6),
   cod_stato_estero_nascita             numeric(3),
   descr_citta_estera_nascita           varchar(100),
   cod_istat_cittadinanza               varchar(3),
   descr_cittadinanza_altro             varchar(1000),
   cod_istat_comune_residenza           varchar(6),
   cod_stato_estero_residenza           numeric(3),
   descr_citta_estera_residenza         varchar(100),
   indirizzo_residenza                  varchar(100),
   cap_residenza                        varchar(5),
   cod_istat_comune_domicilio           varchar(6),
   indirizzo_domicilio                  varchar(100),
   cap_domicilio                        varchar(5),
   recapito_email                       varchar(100),
   recapito_email_2                     varchar(100),
   recapito_telefono                    varchar(50),
   recapito_telefono_2                  varchar(50),
   num_docum_identita                   varchar(50),
   ente_rilascio_docum_identita         varchar(150),
   d_scadenza_docum_identita            timestamp,
   num_permesso_di_soggiorno            varchar(50),
   ente_rilascio_permesso_di_soggiorno  varchar(150),
   d_scadenza_permesso_di_soggiorno     timestamp,
   descr_motivo_permesso_di_soggiorno   varchar(1000),
   cod_titolo_di_studio                 varchar(2),
   titolo_di_studio_altro               varchar(1000),
   cod_user_inserim                     varchar(16)     not null,
   d_inserim                            timestamp       not null,
   cod_user_aggiorn                     varchar(16)     not null,
   d_aggiorn                            timestamp       not null,
   tipo_permesso_di_soggiorno           varchar(1)
);

alter table mip_t_anagrafica_cittadino
   add constraint pk_mip_t_anagrafica_cittadino
   primary key (id_cittadino);

comment on column mip_t_anagrafica_cittadino.sesso is 'Puo'' valere ''M'' o ''F''';
comment on column mip_t_anagrafica_cittadino.tipo_permesso_di_soggiorno is 'Puo'' valere ''P'' (= Permesso) o ''C'' (= Carta); obbligatorio se valorizzato il num_permesso_di_soggiorno';

create table if not exists mip_t_anagrafica_cittadino_exten
(
   id_cittadino                    numeric(10)   not null,
   condizione_occupazionale        bytea,
   condizione_occupazionale_altro  bytea,
   svantaggio_abitativo            bytea,
   condizione_familiare            bytea,
   cod_user_inserim                varchar(16)   not null,
   d_inserim                       timestamp     not null,
   cod_user_aggiorn                varchar(16)   not null,
   d_aggiorn                       timestamp     not null
);

alter table mip_t_anagrafica_cittadino_exten
   add constraint pk_mip_t_anagrafica_cittadino_exten
   primary key (id_cittadino);

create table if not exists mip_t_cittadino
(
   id_cittadino      numeric(10)    not null,
   codice_fiscale    varchar(16)    not null,
   cognome           varchar(100)   not null,
   nome              varchar(100)   not null,
   cod_user_inserim  varchar(16)    not null,
   d_inserim         timestamp      not null,
   cod_user_aggiorn  varchar(16)    not null,
   d_aggiorn         timestamp      not null
);

alter table mip_t_cittadino
   add constraint pk_mip_t_cittadino
   primary key (id_cittadino);

create index if not exists ak_mip_t_cittadino_01 on mip_t_cittadino using btree (codice_fiscale);

create table if not exists mip_t_compilazione_questionario
(
   id_compilazione_questionario  numeric(10)   not null,
   id_cittadino                  numeric(10)   not null,
   id_questionario               numeric(10)   not null,
   cod_user_inserim              varchar(16)   not null,
   d_inserim                     timestamp     not null,
   cod_user_aggiorn              varchar(16)   not null,
   d_aggiorn                     timestamp     not null
);

alter table mip_t_compilazione_questionario
   add constraint pk_mip_t_compilazione_questionario
   primary key (id_compilazione_questionario);

create unique index ak_mip_t_compilazione_questionario_01 on mip_t_compilazione_questionario using btree (id_cittadino);
create index if not exists ie_mip_t_compilazione_questionario_01 on mip_t_compilazione_questionario using btree (id_questionario);


create table if not exists mip_t_documento
(
   id_documento              numeric(10)    not null,
   nome_documento            varchar(250)   not null,
   descrizione_documento     varchar(500)   not null,
   cod_tipo_documento        varchar(2)     not null,
   documento                 bytea          not null,
   id_operatore_inserimento  numeric(10)    not null,
   id_idea_di_impresa        numeric(10),
   cod_user_inserim          varchar(16)    not null,
   d_inserim                 timestamp      not null,
   cod_user_aggiorn          varchar(16)    not null,
   d_aggiorn                 timestamp      not null
);

alter table mip_t_documento
   add constraint pk_mip_t_documento
   primary key (id_documento);

create index if not exists ie_mip_t_documento_01 on mip_t_documento using btree (id_operatore_inserimento);
create index if not exists ie_mip_t_documento_02 on mip_t_documento using btree (id_idea_di_impresa);


create table if not exists mip_t_evento_calendario
(
   id_evento_calendario  numeric(10)    not null,
   id_file_ics           numeric(10)    not null,
   data_ora_inizio       timestamp      not null,
   data_ora_fine         timestamp      not null,
   descr_evento          varchar(500),
   dato_uid              varchar(200),
   titolo                varchar(250),
   luogo                 varchar(250),
   cod_user_inserim      varchar(16)    not null,
   d_inserim             timestamp      not null,
   cod_user_aggiorn      varchar(16)    not null,
   d_aggiorn             timestamp      not null
);

alter table mip_t_evento_calendario
   add constraint pk_mip_t_evento_calendario
   primary key (id_evento_calendario);

create index if not exists ie_mip_t_evento_calendario_01 on mip_t_evento_calendario using btree (id_file_ics);



comment on column mip_t_evento_calendario.id_evento_calendario is 'Da generare tramite la sequence seq_mip_t_evento_calendario';

create table if not exists mip_t_evento_calendario_bck_20240521
(
   id_evento_calendario  numeric(10),
   id_file_ics           numeric(10),
   data_ora_inizio       timestamp,
   data_ora_fine         timestamp,
   descr_evento          varchar(500),
   dato_uid              varchar(200),
   titolo                varchar(250),
   luogo                 varchar(250),
   cod_user_inserim      varchar(16),
   d_inserim             timestamp,
   cod_user_aggiorn      varchar(16),
   d_aggiorn             timestamp
);

create table if not exists mip_t_file_ics
(
   id_file_ics                  numeric(10)    not null,
   id_operatore_sogg_attuatore  numeric(10)    not null,
   nome_file                    varchar(250)   not null,
   file_ics                     bytea          not null,
   descr_file                   varchar(250),
   flg_abilitato                varchar(1)     DEFAULT 'S'::character varying,
   cod_user_inserim             varchar(16)    not null,
   d_inserim                    timestamp      not null,
   cod_user_aggiorn             varchar(16)    not null,
   d_aggiorn                    timestamp      not null
);

alter table mip_t_file_ics
   add constraint pk_mip_t_file_ics
   primary key (id_file_ics);

create index if not exists ie_mip_t_file_ics_01 on mip_t_file_ics using btree (id_operatore_sogg_attuatore);

create index if not exists ie_mip_t_file_ics_02 on mip_t_file_ics using btree ((((flg_abilitato)::text = 'S'::text)));



comment on column mip_t_file_ics.id_file_ics is 'Da generare tramite la sequence seq_mip_t_file_ics';
comment on column mip_t_file_ics.id_operatore_sogg_attuatore is 'Indica l''operatore che ha caricato il file lavorando per un certo soggetto attuatore';
comment on column mip_t_file_ics.flg_abilitato is 'Puo'' valere ''S'' o null';

create table if not exists mip_t_file_ics_bck_20240521
(
   id_file_ics                  numeric(10),
   id_operatore_sogg_attuatore  numeric(10),
   nome_file                    varchar(250),
   file_ics                     bytea,
   descr_file                   varchar(250),
   flg_abilitato                varchar(1),
   cod_user_inserim             varchar(16),
   d_inserim                    timestamp,
   cod_user_aggiorn             varchar(16),
   d_aggiorn                    timestamp
);

create table if not exists mip_t_idea_di_impresa
(
   id_idea_di_impresa                numeric(10)     not null,
   titolo                            varchar(150)    not null,
   descrizione_idea_di_impresa       varchar(4000)   not null,
   flg_ricambio_generazionale        varchar(1),
   cod_fonte_conoscenza_mip          varchar(2)      not null,
   descr_altra_fonte_conoscenza_mip  varchar(250),
   id_stato_idea_di_impresa          numeric(2)      not null,
   d_cambio_stato                    timestamp,
   id_tutor                          integer,
   d_validazione_business_plan       timestamp,
   id_idea_di_impresa_sostituente    numeric(10),
   flg_erogazione_prima_ora          varchar(1),
   d_firma_patto_di_servizio         timestamp,
   note_commenti                     varchar(4000),
   commenti_interni                  varchar(4000),
   cod_user_inserim                  varchar(16)     not null,
   d_inserim                         timestamp       not null,
   cod_user_aggiorn                  varchar(16)     not null,
   d_aggiorn                         timestamp       not null,
   flg_sblocco_area_territoriale     varchar(1),
   d_scelta_tutor                    timestamp
);

alter table mip_t_idea_di_impresa
   add constraint pk_mip_t_idea_di_impresa
   primary key (id_idea_di_impresa);

create index if not exists ie_mip_t_idea_di_impresa_01 on mip_t_idea_di_impresa using btree (id_tutor);



comment on column mip_t_idea_di_impresa.flg_ricambio_generazionale is 'Se vale ''S'' indica che l''impresa è un proseguimento di un''impresa già esistente';
comment on column mip_t_idea_di_impresa.d_cambio_stato is 'Indica la data in cui è avvenuto il cambio di stato (ad esempio da "creata" a "sostituita")';
comment on column mip_t_idea_di_impresa.flg_erogazione_prima_ora is 'Puo'''' valere S o null';
comment on column mip_t_idea_di_impresa.flg_sblocco_area_territoriale is 'Puo'' valere S o null; se l''operatore di Back Office valorizza il campo a ''S'', il cittadino puÃ² selezionare un soggetto attuatore di un''area territoriale qualsiasi';
comment on column mip_t_idea_di_impresa.d_scelta_tutor is 'Dovrebbe essere valorizzata contestualmente alla scelta del tutor ma il campo e'' stato aggiunto dopo e non e'' stato possibile aggiungere il vincolo di obbligatorieta'' con il tutor';

create table if not exists mip_t_incontro_preaccoglienza
(
   id_incontro_preaccoglienza      numeric(10)     not null,
   denominazione                   varchar(500)    not null,
   flg_incontro_erogato_da_remoto  varchar(1),
   link_incontro_remoto            varchar(250),
   id_luogo_incontro               numeric(10),
   flg_incontro_telefonico         varchar(1),
   id_operatore_creazione          numeric(10)     not null,
   d_incontro                      timestamp       not null,
   num_max_partecipanti            numeric(3)      not null,
   note                            varchar(4000),
   cod_user_inserim                varchar(16)     not null,
   d_inserim                       timestamp       not null,
   cod_user_aggiorn                varchar(16)     not null,
   d_aggiorn                       timestamp       not null
);

alter table mip_t_incontro_preaccoglienza
   add constraint pk_mip_t_incontro_preaccoglienza
   primary key (id_incontro_preaccoglienza);

create index if not exists ie_mip_t_incontro_preaccoglienza_01 on mip_t_incontro_preaccoglienza using btree (id_luogo_incontro);
create index if not exists ie_mip_t_incontro_preaccoglienza_02 on mip_t_incontro_preaccoglienza using btree (id_operatore_creazione);


create table if not exists mip_t_mail_inviata
(
   id_mail_inviata   numeric(10)   not null,
   d_invio           timestamp     DEFAULT clock_timestamp() not null,
   id_cittadino      numeric(10)   not null,
   cod_testo_email   varchar(20),
   cod_user_inserim  varchar(16)   not null,
   d_inserim         timestamp     not null,
   cod_user_aggiorn  varchar(16)   not null,
   d_aggiorn         timestamp     not null
);

alter table mip_t_mail_inviata
   add constraint pk_mip_t_mail_inviata
   primary key (id_mail_inviata);

create index if not exists ie_mip_t_mail_inviata_01 on mip_t_mail_inviata using btree (id_cittadino);
create index if not exists ie_mip_t_mail_inviata_02 on mip_t_mail_inviata using btree (cod_testo_email);


create table if not exists mip_t_risposta_compilazione
(
   id_risposta_compilazione      numeric(10)     not null,
   id_compilazione_questionario  numeric(10)     not null,
   id_domanda                    numeric(10)     not null,
   id_risposta                   numeric(10),
   risposta_libera               varchar(2000),
   cod_user_inserim              varchar(16)     not null,
   d_inserim                     timestamp       not null,
   cod_user_aggiorn              varchar(16)     not null,
   d_aggiorn                     timestamp       not null
);

alter table mip_t_risposta_compilazione
   add constraint pk_mip_t_risposta_compilazione
   primary key (id_risposta_compilazione);

create unique index ak_mip_t_risposta_compilazione_01 on mip_t_risposta_compilazione using btree (id_compilazione_questionario, id_risposta);

create table if not exists mip_t_tutor_da_eliminare
(
   id_tutor               numeric(10)   not null,
   id_soggetto_attuatore  integer       not null,
   cod_user_inserim       varchar(16)   not null,
   d_inserim              timestamp     not null,
   cod_user_aggiorn       varchar(16)   not null,
   d_aggiorn              timestamp     not null
);

alter table mip_t_tutor_da_eliminare
   add constraint mip_t_tutor_pkey
   primary key (id_tutor);

create unique index ak_mip_t_tutor_01 on mip_t_tutor_da_eliminare using btree (id_soggetto_attuatore);

