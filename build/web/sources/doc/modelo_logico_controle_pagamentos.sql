CREATE TABLE cadastro_credor (
  id 				SERIAL,
  nome 				VARCHAR(120) NOT NULL,
  cnpj 				VARCHAR(20) NOT NULL,
  endereco 			VARCHAR(200) NOT NULL,
  CONSTRAINT pk_id_credor PRIMARY KEY(id)
);

CREATE TABLE cadastro_contas (
  id 				SERIAL,
  fk_credor 		INTEGER UNSIGNED NOT NULL,
  descricao 		VARCHAR(255) NOT NULL,
  valor 			NUMERIC NOT NULL,
  data_vencimento 	DATE NOT NULL,
  CONSTRAINT pk_id_contas PRIMARY KEY(id),
  CONSTRAINT fk_id_credor FOREIGN KEY(fk_credor) 
  		REFERENCES cadastro_credor(id)

);

CREATE TABLE cadastro_pgto (
  id 				SERIAL,
  fk_conta 			INTEGER UNSIGNED NOT NULL,
  vr_pago 			NUMERIC NOT NULL,
  data_pgto 		DATE NOT NULL,
  CONSTRAINT pk_id_pgto PRIMARY KEY(id),
  CONSTRAINT fk_id_contas FOREIGN KEY(fk_conta) 
  		REFERENCES cadastro_contas(id)
);


