create database cine;
use cine;



create table salas(
	id_sala int (5) not null,
	fila int(2) not null,
	columna varchar(2) not null,
	primary key(id_sala)
	);


create table peliculas(
	id_pelicula int (5) AUTO_INCREMENT,
	titulo varchar(20) not null,
	duracion int(10) not null,
	sinopsis varchar(50) not null,
	fecha_estreno date,
	fecha_finalizacion date,
	primary key(id_pelicula)
);

create table sesiones(
	id_sesion int (5) AUTO_INCREMENT,
	id_sala int (5) not  null,
	nombre_pelicula varchar(20) not null,
	fecha date,
	hora varchar(5) not null,
	duracion int(3) not null,
	primary key(id_sesion),
	foreign key (id_sala) references salas (id_sala) on update cascade on delete cascade
);


create table entradas(
	id_ticket int (5) AUTO_INCREMENT,
	id_sala int (5) not null,
	id_sesion int (5) not null,
	titulo varchar(20) not null,
	fila int(2) not null,
	asiento int(2) not null,
	hora varchar(5) not null,
	fecha date,
	primary key(id_ticket),
	constraint id foreign key (id_sala) references salas(id_sala),
	constraint fk_id_sesion foreign key (id_sesion) references sesiones(id_sesion)
);

insert into salas (id_sala, fila, columna) VALUES (1, 10, 14);
insert into salas (id_sala, fila, columna) VALUES (2, 12, 16);
insert into salas (id_sala, fila, columna) VALUES (3, 15, 18);
insert into salas (id_sala, fila, columna) VALUES (4, 15, 20);

--PELICULAS--
INSERT INTO peliculas (id_pelicula, titulo, duracion, sinopsis, fecha_estreno, fecha_finalizacion) VALUES
(1, 'Los Vengadores', 160, 'Tony Stark es un crack', '2020-05-06', '2020-05-08'),
(2, 'Black Panther', 120, 'Black Panther araña', '2020-05-22', '2020-12-22');




