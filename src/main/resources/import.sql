insert into cozinha(id,nome) values (1,"Tailandesa")
insert into cozinha(id,nome) values (2,"Indiana")
insert into cozinha(id,nome) values (3,"Brasileira")

insert into restaurante(nome,taxa_frete,cozinha_id) values ("Thai Gourmet",8.80,1)
insert into restaurante(nome,taxa_frete,cozinha_id) values ("Thai Delivery",5.50,1)
insert into restaurante(nome,taxa_frete,cozinha_id) values ("Tuk In Comidas",2.80,2)


insert into estado(nome) values ("CEARÁ")
insert into estado(nome) values ("AMAZONAS")

insert into cidade(nome,estado_id) values ("Manaus",2)
insert into cidade(nome,estado_id) values ("Fortaleza",1)

insert into forma_pagamento(descricao) values ("Boleto bancário")

insert into permissao(nome,descricao) values ("usuario normal","apenas funcões simples")
insert into permissao(nome,descricao) values ("usuario premium","funcões simples e pagamento de cartão")


