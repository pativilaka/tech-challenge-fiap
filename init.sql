CREATE TABLE usuarios
(
    id INT PRIMARY KEY,
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
    id INT NOT NULL PRIMARY KEY,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(100) NOT NULL,
    CONSTRAINT fk_medico_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE pacientes
(
    id INT NOT NULL PRIMARY KEY,
    plano_saude VARCHAR(100),
    CONSTRAINT fk_paciente_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE enfermeiros
(
    id INT NOT NULL PRIMARY KEY,
    coren VARCHAR(20) NOT NULL UNIQUE,
    CONSTRAINT fk_enfermeiro_usuario FOREIGN KEY (id) REFERENCES usuarios(id)
);

CREATE TABLE consultas
(
    id int PRIMARY KEY,
    paciente_id int NOT NULL,
    medico_id int NOT NULL,
    data_inicio TIMESTAMP NOT NULL,
    data_fim TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES usuarios(id),
    CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES usuarios(id)
);

INSERT INTO usuarios
    (id, nome, cpf, data_de_nascimento, email, senha, tipo_usuario, telefone)
VALUES
    (1, 'Dr. João Silva', '03005175057', '1980-05-15', 'joao@email.com', '$2a$12$MRPaPizvzQSC4qJJIK3N3uXnigIPMfYNVkDjPHUecG52cN9aL8v/q', 'MEDICO', '11 98765-4321'),
    (2, 'Maria Oliveira', '51547389001', '1990-08-22', 'maria@email.com', '$2a$12$8.Nrp.ByAEikJrHpZJcJEuWYzIwaepLBnTAzlqvlibADq/EdskFQu', 'PACIENTE', '11 98765-4321'),
    (3, 'Enf. Carlos Souza', '89524329085', '1985-03-10', 'carlos@email.com', '$2a$12$.9cnS93Vj7p3qPow5nUuPO9AwUDL3TLqZq2Ky6kzH0lnHl4A1KOIK', 'ENFERMEIRO', '11 98765-4321'),
    (4, 'Ana Pereira', '29913698090', '1992-11-30', 'ana@email.com', '$2a$12$TMzPcKQDAVOtDAHuYG4cJuRevS1anNd4pz7SMuVi4/Nx/W9DURaiu', 'PACIENTE', '11 98765-4321');

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
    (id, paciente_id, medico_id, data_inicio, data_fim, status)
values
    (1, 2, 1, '2025-11-20 00:00:00', '2025-11-20 01:00:00', 'AGENDADA');



