CREATE SCHEMA Tablas1;
USE Tablas1;


CREATE table Cliente(
  Dni VARCHAR(45) not null,
  Nombre VARCHAR(45) not null,
  Apellido VARCHAR(45) not null,
  PRIMARY KEY (Dni)
) engine = InnoDB;


create table Pedidos(
  nPedidos Integer not null,
  fecha DATE not null,
  Cantidad DOUBLE not null,
  cliente_Dni VARCHAR(45) not null,
  PRIMARY KEY (nPedidos),
  FOREIGN KEY (cliente_Dni) REFERENCES Cliente(Dni)
) engine = InnoDB;


insert into Cliente
values ("123457678", "Rodrigo", "Lopez");
insert into Cliente
values ("87654321", "Ana", "Gomez");
insert into Pedidos
values (1, "2024-01-15", 10, "12345678");
insert into Pedidos
values (2, "2024-02-20", 5, "87654321");


update pedidos
  set cliente_Dni = "87654321"
  where nPedidos = "12345678";


update cliente
  set cliente_Dni = "87654321"
  where dni = "12345678";

delete from pedidos where cliente_Dni = "87654321";
delete from cliente where dni = "87654321";

SELECT * FROM Cliente;
SELECT * FROM Pedidos;

delete from pedidos where cliente_Dni = "12345678";
