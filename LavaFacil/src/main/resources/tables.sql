CREATE TABLE IF NOT EXISTS usuario (
    id SERIAL PRIMARY KEY,
    email TEXT NOT NULL,
    senha TEXT NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS cliente (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf TEXT NOT NULL,
    telefone TEXT NOT NULL,
    endereco TEXT NOT NULL,
    usuario_id INTEGER UNIQUE NOT NULL REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS dependente (
    id SERIAL PRIMARY KEY,
    nome TEXT NOT NULL,
    cpf TEXT NOT NULL,
    telefone TEXT NOT NULL,
    cliente_id INTEGER NOT NULL REFERENCES cliente(id) ON DELETE CASCADE
);
