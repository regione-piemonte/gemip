SET client_min_messages TO ERROR;
SET client_encoding = 'UTF8'; 

create sequence seq_mip_d_luogo_incontro
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_d_operatore
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_d_soggetto_attuatore
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_r_cittadino_incontro_preacc
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_r_operatore_cpi
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_r_operatore_ente
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_r_operatore_sogg_affidatario
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_r_operatore_sogg_attuatore
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_cittadino
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_compilazione_questionario
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_documento
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_evento_calendario
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_file_ics
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_idea_di_impresa
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_incontro_preaccoglienza
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_mail_inviata
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_risposta_compilazione
       increment by 1
       minvalue 1
       cache 1
       no cycle;

create sequence seq_mip_t_tutor
       increment by 1
       minvalue 1
       cache 1
       no cycle;

commit;