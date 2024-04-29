drop table consultas;

create table consultas(

	id bigint not null auto_increment,
	medico_id bigint not null,
	beneficiario_id bigint not null,
	data datetime not null,
	motivo_cancelamento varchar(100),

	primary key(id),

	constraint fk_consultas_medico_id foreign key(medico_id) references medicos(id),
	constraint fk_consultas_beneficiario_id foreign key (beneficiario_id) references beneficiarios(id)

);
