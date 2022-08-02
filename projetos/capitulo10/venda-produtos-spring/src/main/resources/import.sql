delete from item_venda;
delete from venda;
delete from produto;
delete from unidade_medida;
delete from cliente;
delete from fornecedor
delete from cidade;
delete from estado;

INSERT INTO estado( id, nome, sigla ) VALUES( 1, 'São Paulo', 'SP' );
INSERT INTO estado( id, nome, sigla ) VALUES( 2, 'Minas Gerais', 'MG' );

INSERT INTO cidade( id, nome, estado_id ) VALUES( 1, 'Vargem Grande do Sul', 1 );
INSERT INTO cidade( id, nome, estado_id ) VALUES( 2, 'São João da Boa Vista', 1 );
INSERT INTO cidade( id, nome, estado_id ) VALUES( 3, 'Poços de Caldas', 2 );

INSERT INTO cliente( id, nome, sobrenome, data_nascimento, cpf, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 1, 'João', 'da Silva', '1999-10-10', '111.111.111-11', 'joao@joao.com.br', 'Rua Hermenegildo Cossi', '750', 'Jardim Fortaleza', '13880-000', 1 );
INSERT INTO cliente( id, nome, sobrenome, data_nascimento, cpf, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 2, 'Maria', 'Rodrigues', '1974-05-16', '222.222.222-22', 'mariarod@gmail.com', 'Rua Patrocínio Rodrigues', '120', 'Centro', '13880-000', 1 );
INSERT INTO cliente( id, nome, sobrenome, data_nascimento, cpf, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 3, 'Marcela', 'dos Santos', '1985-09-25', '333.333.333-33', 'mdossantos@uol.com.br', 'Rua Primeirod de Maio', '219', 'Centro', '13880-000', 1 );

INSERT INTO fornecedor( id, razao_social, cnpj, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 1, 'Fruta Madura', '11.111.111/1111-11', 'atendimento@frutamadura.com.br', 'Rua João da Silva', '79', 'Centro', '13825-789', 2 );
INSERT INTO fornecedor( id, razao_social, cnpj, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 2, 'Eletrônicos Dois Irmãos', '22.222.222/2222-22', 'sac@edi.com.br', 'Rua do Comércio', '188', 'Centro', '13880-000', 1 );
INSERT INTO fornecedor( id, razao_social, cnpj, email, logradouro, numero, bairro, cep, cidade_id ) VALUES( 3, 'Distribuidora de Carnes do Sul', '33.333.333/3333-33', 'contato@distcarnesdosul.com.br', 'Rua Manoel Junqueira', '874', 'Jardim Industrial', '16859-789', 3 );

INSERT INTO unidade_medida( id, descricao, sigla ) VALUES( 1, 'quilograma', 'kg' );
INSERT INTO unidade_medida( id, descricao, sigla ) VALUES( 2, 'unidade', 'un' );

INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 1, 'maçã', '1111111111111', 3.78, 20, 1, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 2, 'pera', '2222222222222', 5.45, 30, 1, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 3, 'uva', '3333333333333', 9.50, 20, 1, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 4, 'computador', '4444444444444', 3700.00, 10, 2, 2 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 5, 'rádio', '5555555555555', 150.00, 15, 2, 2 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 6, 'lava louças', '6666666666666', 4499.99, 5, 2, 2 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 7, 'alcatra', '7777777777777', 59.99, 150.00, 3, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 8, 'picanha', '8888888888888', 72.25, 120.00, 3, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 9, 'costela bovina', '9999999999999', 30.45, 200.00, 3, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 10, 'patinho', '1234567891234', 60.99, 150.00, 3, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 11, 'lombo suíno', '9876543219876', 34.99, 100.00, 3, 1 );
INSERT INTO produto( id, descricao, codigo_barras, valor_venda, estoque, fornecedor_id, unidade_medida_id ) VALUES( 12, 'peito de frango sem osso', '7958426317591', 20.99, 250.00, 3, 1 );

INSERT INTO venda( id, cancelada, data, cliente_id ) VALUES( 1, FALSE, "2022-01-13", 1 )