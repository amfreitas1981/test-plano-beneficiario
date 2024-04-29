
DROP TABLE usuarios_perfis;
DROP TABLE perfis;
DROP TABLE usuarios;

CREATE TABLE usuarios(

    id bigint not null auto_increment,
    nome varchar(100) not null,
    login varchar(100) not null,
    password varchar(255) not null,

    primary key(id)

);

CREATE TABLE perfis(
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL UNIQUE,

    PRIMARY KEY(id)
);

CREATE TABLE usuarios_perfis(
    usuario_id BIGINT NOT NULL,
    perfil_id BIGINT NOT NULL,

    PRIMARY KEY(usuario_id, perfil_id),
    CONSTRAINT USUARIOS_PERFIS_FK_USUARIO FOREIGN KEY(usuario_id) REFERENCES usuarios(id),
    CONSTRAINT USUARIOS_PERFIS_FK_PERFIL FOREIGN KEY(perfil_id) REFERENCES perfis(id)

);

INSERT INTO perfis VALUES(1, 'ROLE_ADMIN');
INSERT INTO perfis VALUES(2, 'ROLE_USER');

INSERT INTO usuarios VALUES(1, 'Administrador', 'admin@email.com.br', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');
INSERT INTO usuarios VALUES(2, 'Test', 'user.test@plano.saude', '$2a$12$T86.rJH1ddP1glns2js0I.iC4.Ncs71W0ySGP6Gm2wZL2hqHrFMcK');

INSERT INTO usuarios_perfis values(1, 1);
