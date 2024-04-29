create table medicos (

    id bigint not null auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    crm varchar(6) not null unique,
    especialidade varchar(100) not null,
    telefone varchar(20) not null,
    logradouro varchar(100) not null,
    bairro varchar(100) not null,
    cep varchar(9) not null,
    complemento varchar(100),
    numero varchar(20),
    uf char(2) not null,
    cidade varchar(100) not null,

    ativo tinyint,

    primary key(id)

);

update medicos set ativo = 1;

create table consultas(

	id bigint not null auto_increment,
	medico_id bigint not null,
	beneficiario_id bigint not null,
	data datetime not null,

	primary key(id),

	constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id),
	constraint fk_consultas_beneficiario_id foreign key (beneficiario_id) references beneficiarios(id)

);
