CREATE TABLE usuarios (
                          id                VARCHAR(36) PRIMARY KEY, -- UUID em formato string
                          nome              VARCHAR(150) NOT NULL,
                          cpf               VARCHAR(11) NOT NULL UNIQUE,
                          data_de_nascimento DATE NOT NULL,
                          email             VARCHAR(150) NOT NULL UNIQUE,
                          senha             VARCHAR(255) NOT NULL,
                          tipo_usuario      VARCHAR(20) NOT NULL, -- Enum no Java, mas no Postgres será String
                          criado_em         TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          atualizado_em     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE consultas (
                           id          VARCHAR(36) PRIMARY KEY,
                           paciente_id VARCHAR(36) NOT NULL,
                           medico_id   VARCHAR(36) NOT NULL,
                           data_hora   TIMESTAMP NOT NULL,
                           status      VARCHAR(20) NOT NULL,
                           CONSTRAINT fk_consulta_paciente FOREIGN KEY (paciente_id) REFERENCES usuarios(id),
                           CONSTRAINT fk_consulta_medico FOREIGN KEY (medico_id) REFERENCES usuarios(id)
);