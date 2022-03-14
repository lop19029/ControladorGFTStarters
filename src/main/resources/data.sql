-- Popular Tecnologia
INSERT INTO tecnologia
VALUES(1,
'Uma linguagem de programação orientada a objetos desenvolvida na década de 90 por uma equipe de programadores chefiada por James Gosling, na empresa Sun Microsystems. Em 2008 o Java foi adquirido pela empresa Oracle Corporation.',
'Java');

INSERT INTO tecnologia
VALUES(2,
'É uma iniciativa da empresa Microsoft, que visa uma plataforma única para desenvolvimento e execução de sistemas e aplicações. Todo e qualquer código gerado para .NET pode ser executado em qualquer dispositivo que possua um framework de tal plataforma.',
'.NET');

-- Popular Modulo
INSERT INTO modulo
VALUES(1,'MVC Avaliação');

INSERT INTO modulo
VALUES(2,'API Avaliação');

INSERT INTO modulo
VALUES(3,'TDD Estudo');

-- Popular Programa Start
INSERT INTO programa_start
VALUES(1, '2022-01-30', '2021-11-03', 'Starter #5');

-- Popular User
INSERT INTO user
VALUES
(1, 'FULL', 'Clecio', 'clecio.silva@gft.com','Gft2021', 'cwsq', 'Silva'),
(2,'RESTRICTED', 'Scrum','scrumMaster@gft.com','password', 'smsm', 'Master'),
(3,'RESTRICTED', 'Scrum2','scrumMaster2@gft.com','password', 'smsd', 'Master2'),
(4, 'FULL', 'admin', 'admin@gft.com','password', 'admi', 'Test');

-- Popular grupo
INSERT INTO grupo
VALUES
(1, 1, 2, 1),
(2, 1, 3, 2);

-- Popular Starter
INSERT INTO starter
VALUES
(1,'MyProfilePicture.png','Alex', 3, 'aolj', 'Lopez', 1),
(2,'ariadne.jpg','Ariadne', 2, 'apcr','Costa', 1),
(3,'guilherme.jpg','Guilherme',2, 'goat', 'Andrade', 2),
(4,'fernanda.jpg','Fernanda',3, 'fqsl','Sousa', 2);

-- Popular Projeto
INSERT INTO projeto
VALUES
(1, 3,'Fez um relatorio de starters', 'MVC', 1, 1),
(2, 2, default, 'MVC', 1, 2),
(3, 2, default, 'MVC', 1, 3),
(4, 3, default, 'MVC', 1, 4);

-- Popular Daily
INSERT INTO daily
VALUES(1, TO_TIMESTAMP('2021-11-23', 'yyyy-MM-dd'), 'Populando o banco de dados', 'Criar entidades da aplicação', 'Sem impedimento', 3, 1, 1);


