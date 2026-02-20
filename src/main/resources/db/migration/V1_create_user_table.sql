create table users (
    id bigserial primary key,

    empresa_tenant_id bigint not null,

    nome varchar(100) not null,
    sobrenome varchar(100) not null,

    cpf varchar(11) not null,
    email varchar(60) not null,
    telefone varchar(11),

    data_nascimento date not null,

    senha varchar(300) not null,

    ativo boolean default true,
    email_verificado boolean default false,
    bloqueado boolean default false,

    data_cadastro timestamp default current_timestamp,
    ultimo_login timestamp,

    tentativas_login integer default 0,

    constraint uk_usuario_cpf unique (empresa_tenant_id, cpf),
    constraint uk_usuario_email unique (empresa_tenant_id, email)
);
