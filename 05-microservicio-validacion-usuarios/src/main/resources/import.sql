INSERT INTO usuarios(username, password, enabled,nombre,apellido,email,intentos) VALUES('user', '$2a$10$pld9FqoIoL6uRkk1OTqO.esnIq0Jh3x/723PxljHd62lbyb6qQUW.',1,'usuarionombre','usuarioapellido','usuario@email',0);
INSERT INTO usuarios(username, password, enabled,nombre,apellido,email,intentos) VALUES('admin', '$2a$10$jWkRQ3b/xaz3hxBGxs8k0OZMFRD2FsOvyIBsfBpKbn9GN7Q6o39gC',1,'adminnombre','adminapellido','admin@email',0);
INSERT INTO usuarios(username, password, enabled,nombre,apellido,email,intentos) VALUES('adminuser', '$2a$10$Jxy6WJIJI64jMXeva7CZEu8Xa23A30OExd6HXG3qjv7tXHei515mu',1,'adminusernombre','adminuserapellido','adminuser@email',0);
INSERT INTO roles(nombre_rol) VALUES('ROLE_USER');
INSERT INTO roles(nombre_rol) VALUES('ROLE_ADMIN');
INSERT INTO usuario_y_role(user_id,role_id) VALUES(1,1);
INSERT INTO usuario_y_role(user_id,role_id) VALUES(2,2);
INSERT INTO usuario_y_role(user_id,role_id) VALUES(3,1);
INSERT INTO usuario_y_role(user_id,role_id) VALUES(3,2);