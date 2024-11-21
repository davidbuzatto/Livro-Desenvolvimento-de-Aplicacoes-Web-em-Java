CREATE TABLE IF NOT EXISTS cliente (
  id INT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45) NOT NULL,
  sobrenome VARCHAR(45) NOT NULL,
  data_nascimento DATE NOT NULL,
  cpf VARCHAR(14) NOT NULL,
  email VARCHAR(60) NOT NULL,
  logradouro VARCHAR(50) NOT NULL,
  numero VARCHAR(6) NOT NULL,
  bairro VARCHAR(30) NOT NULL,
  cep VARCHAR(9) NOT NULL,
  cidade_id INT NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC),
  INDEX fk_cliente_cidade_idx (cidade_id ASC),
  CONSTRAINT fk_cliente_cidade
    FOREIGN KEY (cidade_id)
    REFERENCES cidade (id)
    ON DELETE RESTRICT
    ON UPDATE CASCADE
) ENGINE = InnoDB;