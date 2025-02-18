create table usuarios(
    id bigserial not null,
    cpf varchar(30) unique not null,
    nome varchar(100) not null,
    email varchar(100) not null,

    primary key(id)
);