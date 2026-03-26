DROP DATABASE IF EXISTS GameZone2;
create database if not exists GameZone2;
use GameZone2;

drop table if exists consolas;
create table consolas
(
ID_Consolas int primary key not null,
Nombre varchar(50) not null,
Marca varchar(50) not null
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

drop table if exists juegos;
create table juegos
(
ID_Juegos int primary key not null,
titulo varchar(50) not null,
precio decimal(8,2) not null,
stock int not null,
id_consola int not null,

constraint fk_Juegos_Consolas
foreign key (id_consola)
references consolas(ID_Consolas)
on delete no action
on update cascade,

constraint chk_precio
check(precio > 100),

constraint chk_stock
check(stock >= 1)
)ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

drop table if exists ventas;
create table ventas
(
ID_Ventas int primary key not null,
fecha date not null,
cantidad int not null,
id_juego int not null,

constraint fk_Ventas_Juegos
foreign key (id_juego)
references juegos(ID_Juegos)
on delete no action
on update cascade,

constraint chk_cantidad
check (cantidad >= 1)
)
ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;

insert into consolas(ID_Consolas,Nombre,Marca)
values (1,'Xbox 360','Xbox');
insert into juegos(ID_Juegos,titulo,precio,stock,id_consola)
values (2,'Red Dead Redemption 2',1199.00,25,1);
insert into juegos(ID_Juegos,titulo,precio,stock,id_consola)
values (3,'Howarts legacy',1199.00,25,1);
insert into ventas(ID_Ventas,fecha,cantidad,id_juego)
values (4,'2026-03-04',10,2);

-- inserción con error:
insert into ventas(ID_Ventas,fecha,cantidad,id_juego) values (5,'2020-01-30',0,2);

Select * from consolas;
Select * from Juegos;
Select * from Ventas;

DELETE FROM Juegos
WHERE ID_Juegos = 1;
DELETE FROM Consola
WHERE id = 1;