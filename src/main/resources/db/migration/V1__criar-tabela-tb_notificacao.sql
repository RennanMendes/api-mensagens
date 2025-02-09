CREATE TABLE tb_notificacao(
     id UUID PRIMARY KEY,
     data_hora DATE,
     nome VARCHAR,
     email VARCHAR,
     telefone VARCHAR,
     mensagem VARCHAR,
     canal VARCHAR(8),
     status VARCHAR(9)
);