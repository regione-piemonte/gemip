alter table ext_tab_cittadinanza
  add constraint fk_ext_tab_nazionalita_01 foreign key (cod_nazionalita)
  references ext_tab_nazionalita (cod_nazionalita)
  on update no action
  on delete no action;

alter table ext_tt_comune
  add constraint fk_ext_tt_provincia_01 foreign key (prov)
  references ext_tt_provincia (prov)
  on update no action
  on delete no action;

alter table mip_d_domanda
  add constraint fk_mip_d_fase_questionario_02 foreign key (id_fase_questionario)
  references mip_d_fase_questionario (id_fase_questionario)
  on update no action
  on delete no action;

alter table mip_d_domanda
  add constraint fk_mip_d_questionario_01 foreign key (id_questionario)
  references mip_d_questionario (id_questionario)
  on update no action
  on delete no action;

alter table mip_d_domanda
  add constraint fk_mip_d_risposta_02 foreign key (id_risposta_condizionale)
  references mip_d_risposta (id_risposta)
  on update no action
  on delete no action;

alter table mip_d_luogo_incontro
  add constraint fk_ext_gmop_d_area_territoriale_01 foreign key (cod_area_territoriale)
  references ext_gmop_d_area_territoriale (cod_area_territoriale)
  on update no action
  on delete no action;

alter table mip_d_luogo_incontro
  add constraint fk_ext_tt_comune_01 foreign key (cod_istat_comune)
  references ext_tt_comune (comune)
  on update no action
  on delete no action;

alter table mip_d_operatore
  add constraint fk_mip_d_operatore_10 foreign key (id_operatore_disabilitazione)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_d_risposta
  add constraint fk_mip_d_domanda_01 foreign key (id_domanda)
  references mip_d_domanda (id_domanda)
  on update no action
  on delete no action;

alter table mip_d_soggetto_attuatore
  add constraint fk_ext_gmop_d_area_territoriale_04 foreign key (cod_area_territoriale)
  references ext_gmop_d_area_territoriale (cod_area_territoriale)
  on update no action
  on delete no action;

alter table mip_r_cittadino_fase_questionario
  add constraint fk_mip_d_fase_questionario_03 foreign key (id_fase_questionario)
  references mip_d_fase_questionario (id_fase_questionario)
  on update no action
  on delete no action;

alter table mip_r_cittadino_fase_questionario
  add constraint fk_mip_t_cittadino_05 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_r_cittadino_incontro_preacc
  add constraint fk_ext_gmop_d_area_territoriale_03 foreign key (cod_area_territoriale_selezionata)
  references ext_gmop_d_area_territoriale (cod_area_territoriale)
  on update no action
  on delete no action;

alter table mip_r_cittadino_incontro_preacc
  add constraint fk_mip_t_cittadino_01 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_r_cittadino_incontro_preacc
  add constraint fk_mip_t_incontro_preaccoglienza_03 foreign key (id_incontro_preaccoglienza)
  references mip_t_incontro_preaccoglienza (id_incontro_preaccoglienza)
  on update no action
  on delete no action;

alter table mip_r_compilazione_questionario_fase
  add constraint fk_mip_d_fase_questionario_01 foreign key (id_fase_questionario)
  references mip_d_fase_questionario (id_fase_questionario)
  on update no action
  on delete no action;

alter table mip_r_compilazione_questionario_fase
  add constraint fk_mip_t_compilazione_questionario_02 foreign key (id_compilazione_questionario)
  references mip_t_compilazione_questionario (id_compilazione_questionario)
  on update no action
  on delete no action;

alter table mip_r_idea_di_impresa_cittadino
  add constraint fk_mip_t_cittadino_02 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_r_idea_di_impresa_cittadino
  add constraint fk_mip_t_idea_di_impresa_03 foreign key (id_idea_di_impresa)
  references mip_t_idea_di_impresa (id_idea_di_impresa)
  on update no action
  on delete no action;

alter table mip_r_incontro_preacc_area_terr
  add constraint fk_ext_gmop_d_area_territoriale_02 foreign key (cod_area_territoriale)
  references ext_gmop_d_area_territoriale (cod_area_territoriale)
  on update no action
  on delete no action;

alter table mip_r_incontro_preacc_area_terr
  add constraint fk_mip_t_incontro_preaccoglienza_02 foreign key (id_incontro_preaccoglienza)
  references mip_t_incontro_preaccoglienza (id_incontro_preaccoglienza)
  on update no action
  on delete no action;

alter table mip_r_operatore_cpi
  add constraint fk_mip_d_cpi_01 foreign key (id_cpi)
  references mip_d_cpi (id_cpi)
  on update no action
  on delete no action;

alter table mip_r_operatore_cpi
  add constraint fk_mip_d_operatore_02 foreign key (id_operatore)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_ente
  add constraint fk_mip_d_ente_01 foreign key (id_ente)
  references mip_d_ente (id_ente)
  on update no action
  on delete no action;

alter table mip_r_operatore_ente
  add constraint fk_mip_d_operatore_03 foreign key (id_operatore)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_idea_di_impresa_dismessa
  add constraint fk_mip_d_operatore_05 foreign key (id_operatore_attuatore)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_idea_di_impresa_dismessa
  add constraint fk_mip_t_idea_di_impresa_01 foreign key (id_idea_di_impresa)
  references mip_t_idea_di_impresa (id_idea_di_impresa)
  on update no action
  on delete no action;

alter table mip_r_operatore_incontro_preaccoglienza
  add constraint fk_mip_d_operatore_08 foreign key (id_operatore_affidatario)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_incontro_preaccoglienza
  add constraint fk_mip_t_incontro_preaccoglienza_01 foreign key (id_incontro_preaccoglienza)
  references mip_t_incontro_preaccoglienza (id_incontro_preaccoglienza)
  on update no action
  on delete no action;

alter table mip_r_operatore_sogg_affidatario
  add constraint fk_mip_d_operatore_04 foreign key (id_operatore)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_sogg_affidatario
  add constraint fk_mip_d_soggetto_affidatario_01 foreign key (id_soggetto_affidatario)
  references mip_d_soggetto_affidatario (id_soggetto_affidatario)
  on update no action
  on delete no action;

alter table mip_r_operatore_sogg_attuatore
  add constraint fk_mip_d_operatore_01 foreign key (id_operatore)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_sogg_attuatore
  add constraint fk_mip_d_operatore_09 foreign key (id_operatore_disabilitazione)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_r_operatore_sogg_attuatore
  add constraint fk_mip_d_soggetto_attuatore_01 foreign key (id_soggetto_attuatore)
  references mip_d_soggetto_attuatore (id_soggetto_attuatore)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_ext_stati_esteri_01 foreign key (cod_stato_estero_nascita)
  references ext_stati_esteri (cod_stato)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_ext_tab_cittadinanza_01 foreign key (cod_istat_cittadinanza)
  references ext_tab_cittadinanza (cod_istat_cittadinanza)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_ext_tt_comune_02 foreign key (cod_istat_comune_nascita)
  references ext_tt_comune (comune)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_ext_tt_comune_03 foreign key (cod_istat_comune_residenza)
  references ext_tt_comune (comune)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_ext_tt_comune_04 foreign key (cod_istat_comune_domicilio)
  references ext_tt_comune (comune)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_mip_d_titolo_di_studio_01 foreign key (cod_titolo_di_studio)
  references mip_d_titolo_di_studio (cod_titolo_di_studio)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino
  add constraint fk_mip_t_cittadino_04 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_t_anagrafica_cittadino_exten
  add constraint fk_mip_t_anagrafica_cittadino_01 foreign key (id_cittadino)
  references mip_t_anagrafica_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_t_compilazione_questionario
  add constraint fk_mip_d_questionario_02 foreign key (id_questionario)
  references mip_d_questionario (id_questionario)
  on update no action
  on delete no action;

alter table mip_t_compilazione_questionario
  add constraint fk_mip_t_cittadino_03 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_t_documento
  add constraint fk_mip_d_operatore_07 foreign key (id_operatore_inserimento)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_t_documento
  add constraint fk_mip_d_tipo_documento_01 foreign key (cod_tipo_documento)
  references mip_d_tipo_documento (cod_tipo_documento)
  on update no action
  on delete no action;

alter table mip_t_documento
  add constraint fk_mip_t_idea_di_impresa_02 foreign key (id_idea_di_impresa)
  references mip_t_idea_di_impresa (id_idea_di_impresa)
  on update no action
  on delete no action;

alter table mip_t_evento_calendario
  add constraint fk_mip_t_file_ics_01 foreign key (id_file_ics)
  references mip_t_file_ics (id_file_ics)
  on update no action
  on delete no action;

alter table mip_t_file_ics
  add constraint fk_mip_r_operatore_sogg_attuatore_01 foreign key (id_operatore_sogg_attuatore)
  references mip_r_operatore_sogg_attuatore (id_operatore_sogg_attuatore)
  on update no action
  on delete no action;

alter table mip_t_idea_di_impresa
  add constraint fk_mip_d_fonte_conoscenza_mip_01 foreign key (cod_fonte_conoscenza_mip)
  references mip_d_fonte_conoscenza_mip (cod_fonte_conoscenza_mip)
  on update no action
  on delete no action;

alter table mip_t_idea_di_impresa
  add constraint fk_mip_d_soggetto_attuatore_03 foreign key (id_tutor)
  references mip_d_soggetto_attuatore (id_soggetto_attuatore)
  on update no action
  on delete no action;

alter table mip_t_idea_di_impresa
  add constraint fk_mip_d_stato_idea_di_impresa_01 foreign key (id_stato_idea_di_impresa)
  references mip_d_stato_idea_di_impresa (id_stato_idea_di_impresa)
  on update no action
  on delete no action;

alter table mip_t_idea_di_impresa
  add constraint fk_mip_t_idea_di_impresa_04 foreign key (id_idea_di_impresa_sostituente)
  references mip_t_idea_di_impresa (id_idea_di_impresa)
  on update no action
  on delete no action;

alter table mip_t_incontro_preaccoglienza
  add constraint fk_mip_d_luogo_incontro_01 foreign key (id_luogo_incontro)
  references mip_d_luogo_incontro (id_luogo_incontro)
  on update no action
  on delete no action;

alter table mip_t_incontro_preaccoglienza
  add constraint fk_mip_d_operatore_06 foreign key (id_operatore_creazione)
  references mip_d_operatore (id_operatore)
  on update no action
  on delete no action;

alter table mip_t_mail_inviata
  add constraint fk_mip_d_testo_email_01 foreign key (cod_testo_email)
  references mip_d_testo_email (cod_testo_email)
  on update no action
  on delete no action;

alter table mip_t_mail_inviata
  add constraint fk_mip_t_cittadino_06 foreign key (id_cittadino)
  references mip_t_cittadino (id_cittadino)
  on update no action
  on delete no action;

alter table mip_t_risposta_compilazione
  add constraint fk_mip_d_domanda_02 foreign key (id_domanda)
  references mip_d_domanda (id_domanda)
  on update no action
  on delete no action;

alter table mip_t_risposta_compilazione
  add constraint fk_mip_d_risposta_01 foreign key (id_risposta)
  references mip_d_risposta (id_risposta)
  on update no action
  on delete no action;

alter table mip_t_risposta_compilazione
  add constraint fk_mip_t_compilazione_questionario_01 foreign key (id_compilazione_questionario)
  references mip_t_compilazione_questionario (id_compilazione_questionario)
  on update no action
  on delete no action;

alter table mip_t_tutor_da_eliminare
  add constraint fk_mip_d_soggetto_attuatore_02 foreign key (id_soggetto_attuatore)
  references mip_d_soggetto_attuatore (id_soggetto_attuatore)
  on update no action
  on delete no action;


commit;