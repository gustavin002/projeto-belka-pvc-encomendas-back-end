create database db_sistema_web;
use db_sistema_web;

create table tb_admin (
id_admin integer primary key auto_increment,
email_admin varchar(100) unique not null,
senha_admin varchar(100) not null
);

create table tb_usuario (
id_usuario integer primary key auto_increment,
nome_usuario varchar(60) not null,
email_usuario varchar(100) unique not null,
senha_usuario varchar(100) not null,
role_usuario enum('operador logistico', 'entregador') not null,
disponibilidade_usuario enum('disponível', 'indisponível') not null
);

create table tb_cliente (
  id_cliente integer primary key auto_increment,
  nome_cliente varchar(60) not null,
  email_cliente varchar(100) unique not null,
  endereco_cliente varchar(120) not null
);

create table tb_encomenda (
id_encomenda integer primary key auto_increment,
codigo_rastreio_encomenda varchar(20) unique not null,
endereco_atual_encomenda varchar(120) not null,
status_encomenda enum('em separação', 'em transporte', 'em rota de entrega', 'entregue') not null,
atribuicao_encomenda enum('atribuída','não atribuída') not null,
id_cliente int not null,
id_operador_logistico int not null,
foreign key (id_cliente) references tb_cliente (id_cliente),
foreign key (id_operador_logistico) references tb_usuario (id_usuario)
);

create table tb_entrega (
id_entrega integer primary key auto_increment,
data_hora_entrega datetime,
codigo_otp_entrega varchar(20),
id_encomenda int not null,
id_usuario int not null,
foreign key (id_encomenda) references tb_encomenda (id_encomenda),
foreign key (id_usuario) references tb_usuario (id_usuario)
);

insert into tb_admin (email_admin, senha_admin) values
('admin@gmail.com', 'admin1010');

insert into tb_usuario (nome_usuario, email_usuario, senha_usuario, role_usuario, disponibilidade_usuario) values
('Daniel', 'operador1@gmail.com', 'operador1010', 'operador logistico', 'disponível'),
('João', 'operador2@gmail.com', 'operador1020', 'operador logistico', 'disponível'),
('Gabriel', 'operador3@gmail.com', 'operador1030', 'operador logistico', 'disponível'),
('Matheus', 'operador4@gmail.com', 'operador1040', 'operador logistico', 'disponível'),
('Luiz', 'operador5@gmail.com', 'operador1050', 'operador logistico', 'disponível'),

('Gustavo', 'gustavo.m.silva87@aluno.senai.br', 'entregador1010', 'entregador', 'disponível'),
('Estevão', 'entregador2@gmail.com', 'entregador1020', 'entregador', 'indisponível'),
('Pedro', 'entregador3@gmail.com', 'entregador1030', 'entregador', 'indisponível'),
('Ananias', 'entregador4@gmail.com', 'entregador1040', 'entregador', 'disponível'),
('José', 'entregador5@gmail.com', 'entregador1050', 'entregador', 'disponível');

insert into tb_cliente (nome_cliente, email_cliente, endereco_cliente) values
('Kaue', 'kaue@email.com', 'Rua das Flores, 123'),
('Fabio', 'fabio@email.com', 'Av. Brasil, 456'),
('Fernando', 'fernando@email.com', 'Rua XV de Novembro 67'),
('Lincoln', 'lincoln@email.com', 'Rua Paraná, 852'),
('Rodrigo', 'beatrice@email.com', 'Av. Tiradentes, 1024');

insert into tb_encomenda (codigo_rastreio_encomenda, endereco_atual_encomenda, status_encomenda, atribuicao_encomenda, id_cliente, id_operador_logistico) values
('67674', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 1, 1),
('77778', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 1, 1),
('33317', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 2, 2),
('99881', 'Rua Joao Batista - Londrina', 'em transporte', 'atribuída', 2, 2),
('35783', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 3, 3),
('25386', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 3, 3),
('23729', 'Rua Jaco Figueiredo - Cambé', 'em rota de entrega', 'atribuída', 4, 4),
('12895', 'Rua Gustavo Lorenzo 23 - Arapiraca', 'entregue', 'atribuída', 4, 4),
('38942', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 5, 5),
('84743', 'Centro de Distribuição de Origem', 'em separação', 'não atribuída', 5, 5);

insert into tb_entrega (data_hora_entrega, codigo_otp_entrega, id_encomenda, id_usuario) values
(null, null, 4, 7),
(null, '456852', 7, 8),
('2026-08-11 14:30:00', '357159', 8, 9);