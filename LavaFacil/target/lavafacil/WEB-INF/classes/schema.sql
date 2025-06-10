CREATE DATABASE lava_facil;

\c lava_facil;

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    ativo BOOLEAN DEFAULT true
);

CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,
    placa VARCHAR(10) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cliente_id INTEGER REFERENCES cliente(id)
);

CREATE TABLE agendamento (
    id SERIAL PRIMARY KEY,
    data_hora TIMESTAMP NOT NULL,
    tipo_lavagem VARCHAR(20) NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    veiculo_id INTEGER REFERENCES veiculo(id),
    cliente_id INTEGER REFERENCES cliente(id)
); 