create table beneficiarios (

    id bigint not null auto_increment,
    nome varchar(100) not null,
    telefone varchar(20) not null,
    dataNascimento date not null,
    dataInclusao date not null,
    dataAtualizacao date not null,
    ativo tinyint not null,

    primary key(id)

);

create table documentos (

    id bigint not null auto_increment,
	tipoDocumento varchar(10) not null,
	descricao varchar(100),
    dataInclusao date not null,
    dataAtualizacao date not null,
    ativo tinyint not null,

    primary key(id)

);
