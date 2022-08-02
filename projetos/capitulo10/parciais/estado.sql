create table estado (
    id bigint not null auto_increment,
    nome varchar(30) not null,
    sigla varchar(2) not null,
    primary key (id)
) engine=InnoDB

alter table estado 
      add constraint UK_abc unique (sigla)