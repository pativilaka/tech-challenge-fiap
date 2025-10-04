CREATE TABLE usuarios
(
    id serial PRIMARY KEY,
    -- UUID em formato string
    nome VARCHAR(150) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    data_de_nascimento DATE NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    telefone VARCHAR(15),
    tipo_usuario VARCHAR(20) NOT NULL,
    logradouro VARCHAR(150),
    numero VARCHAR(10),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    uf CHAR(2),
    cep VARCHAR(10),
    complemento VARCHAR(255),
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE medicos
(
    id serial NOT NULL PRIMARY KEY,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    CONSTRAINT fk_medico_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE pacientes
(
    id serial NOT NULL PRIMARY KEY,
    plano_saude VARCHAR(100),
    CONSTRAINT fk_paciente_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE enfermeiros
(
    id serial NOT NULL PRIMARY KEY,
    coren VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT fk_enfermeiro_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE consultas
(
    id serial PRIMARY KEY,
    paciente_id int NOT NULL,
    medico_id int NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES usuarios(id),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES usuarios(id)
);

INSERT INTO usuarios
    (nome, cpf, data_de_nascimento, email, senha, tipo_usuario, telefone)
VALUES
    ('Dr. João Silva', '03005175057', '1980-05-15', 'joao@email.com', '$2a$12$OP8oRA2P0bCFdP275gMYreJpgdzlrolgaiZYJPCnVWBBE/YDkptKO', 'MEDICO', '11 98765-4321'),
    ('Maria Oliveira', '51547389001', '1990-08-22', 'maria@email.com', '$2a$12$VPjg.w3Z2qPVE3aew73vC.RO3SMlkbuU9Zo1UJmYs8wipspchHjCO', 'PACIENTE', '11 98765-4321'),
    ('Enf. Carlos Souza', '89524329085', '1985-03-10', 'carlos@email.com', '$2a$12$sePKOsJcYwZf1sa6F4UJauDhKcceYFjT4uj48DHjM95/7N5tYCN4m', 'ENFERMEIRO', '11 98765-4321'),
    ('Ana Pereira', '29913698090', '1992-11-30', 'ana@email.com', '$2a$12$UN3HQD23ZsZ7R3a7XlSG8.ov9zJbPqR4fvbMEzdIZk9W7rWJcCZHm', 'PACIENTE', '11 98765-4321');

INSERT INTO medicos
    (id, crm, especialidade)
VALUES
    (1, 'CRM123456', 'Cardiologia');

INSERT INTO pacientes
    (id, plano_saude)
VALUES
    (2, 'Unimed');

INSERT INTO enfermeiros
    (id, coren)
VALUES
    (3, 'COREN654321');

INSERT INTO pacientes
    (id, plano_saude)
VALUES
    (4, 'Amil');

insert into consultas
    (paciente_id, medico_id, data_inicio, data_fim, status)
values
    (2, 1, '2025-11-20 00:00:00', '2025-11-20 01:00:00', 'AGENDADA');



