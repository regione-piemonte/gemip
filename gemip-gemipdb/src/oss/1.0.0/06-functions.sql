create or replace function cifra(pstringadacifrare character varying, pchiavedicifratura character varying)
  returns bytea
  language sql
as
$body$
select convert_to(public.armor(public.pgp_sym_encrypt_bytea(convert_to(pStringaDaCifrare, 'UTF8'), pChiaveDiCifratura, 'compress-algo=1, cipher-algo=aes128' )), 'UTF8' );
$body$
  VOLATILE
  COST 100;

create or replace function decifra(pvaloredadecifrare bytea, pchiavedicifratura character varying)
  returns character varying
  language sql
as
$body$
select convert_from(public.pgp_sym_decrypt_bytea(public.dearmor(encode(pValoreDaDecifrare,'escape')), pChiaveDiCifratura), 'UTF8' );
$body$
  VOLATILE
  COST 100;


commit;
