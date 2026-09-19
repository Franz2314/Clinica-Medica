-- =====================================================================
-- SCRIPT UNICO DE ENTREGA - AVANCE 2
-- Proyecto: CORE-SALUD - Sistema de Gestion Hospitalaria
-- Base de datos: clinicamedica_avance2 (PostgreSQL)
--
-- Ejecutar conectado a la base 'postgres' con psql:
--   psql -U postgres -f 00_ate_salud_avance2_completo.sql
-- =====================================================================

DROP DATABASE IF EXISTS clinicamedica_avance2;
CREATE DATABASE clinicamedica_avance2;
\connect clinicamedica_avance2

-- ==================== LIMPIEZA ====================
DROP TABLE IF EXISTS consultas_clinicas;
DROP TABLE IF EXISTS triaje;
DROP TABLE IF EXISTS medicamentos;
DROP TABLE IF EXISTS citas;
DROP TABLE IF EXISTS consultorios;
DROP TABLE IF EXISTS medicos;
DROP TABLE IF EXISTS pacientes;

-- ==================== TABLAS ====================

CREATE TABLE pacientes (
    codigo        VARCHAR(10)  PRIMARY KEY,
    nombres       VARCHAR(60)  NOT NULL,
    apellidos     VARCHAR(60)  NOT NULL,
    telefono      VARCHAR(15)  NOT NULL,
    dni           VARCHAR(12)  NOT NULL UNIQUE,
    edad          INTEGER      NOT NULL,
    genero        VARCHAR(20)  NOT NULL,
    tipo_sangre   VARCHAR(5)   NOT NULL,
    alergias      VARCHAR(120) NOT NULL,
    correo        VARCHAR(120) NOT NULL,
    tiene_sis     BOOLEAN      NOT NULL DEFAULT FALSE,
    numero_sis    VARCHAR(20)
);

CREATE TABLE medicos (
    codigo        VARCHAR(10)  PRIMARY KEY,
    nombres       VARCHAR(60)  NOT NULL,
    apellidos     VARCHAR(60)  NOT NULL,
    telefono      VARCHAR(15)  NOT NULL,
    cmp           VARCHAR(20)  NOT NULL UNIQUE,
    especialidad  VARCHAR(40)  NOT NULL,
    correo        VARCHAR(120) NOT NULL,
    turno         VARCHAR(20)  NOT NULL
);

CREATE TABLE consultorios (
    codigo        VARCHAR(10)  PRIMARY KEY,
    nombre        VARCHAR(80)  NOT NULL,
    piso          INTEGER      NOT NULL,
    especialidad  VARCHAR(40)  NOT NULL,
    disponible    BOOLEAN      NOT NULL DEFAULT TRUE
);

CREATE TABLE citas (
    codigo              VARCHAR(10)   PRIMARY KEY,
    codigo_paciente     VARCHAR(10)   NOT NULL REFERENCES pacientes(codigo) ON DELETE CASCADE,
    codigo_medico       VARCHAR(10)   NOT NULL REFERENCES medicos(codigo) ON DELETE CASCADE,
    codigo_consultorio  VARCHAR(10)   NOT NULL REFERENCES consultorios(codigo) ON DELETE CASCADE,
    fecha               DATE          NOT NULL,
    hora                TIME          NOT NULL,
    motivo              VARCHAR(160)  NOT NULL,
    costo               NUMERIC(10,2) NOT NULL,
    estado              VARCHAR(20)   NOT NULL
);

CREATE TABLE medicamentos (
    codigo      VARCHAR(10)   PRIMARY KEY,
    nombre      VARCHAR(80)   NOT NULL,
    categoria   VARCHAR(50)   NOT NULL,
    descripcion VARCHAR(200),
    precio      NUMERIC(10,2) NOT NULL,
    stock       INTEGER       NOT NULL
);

CREATE TABLE triaje (
    codigo              VARCHAR(10)  PRIMARY KEY,
    codigo_paciente     VARCHAR(10)  NOT NULL REFERENCES pacientes(codigo) ON DELETE CASCADE,
    nombre_paciente     VARCHAR(120) NOT NULL,
    fecha               DATE         NOT NULL,
    peso                NUMERIC(5,2) NOT NULL,
    talla               NUMERIC(4,2) NOT NULL,
    temperatura         NUMERIC(4,1) NOT NULL,
    presion_arterial    VARCHAR(15)  NOT NULL,
    frecuencia_cardiaca INTEGER      NOT NULL,
    nivel_prioridad     VARCHAR(20)  NOT NULL,
    atendido            BOOLEAN      NOT NULL DEFAULT FALSE
);

CREATE TABLE consultas_clinicas (
    codigo                 VARCHAR(10)  PRIMARY KEY,
    codigo_paciente        VARCHAR(10)  NOT NULL REFERENCES pacientes(codigo) ON DELETE CASCADE,
    nombre_paciente        VARCHAR(120) NOT NULL,
    fecha                  DATE         NOT NULL,
    nombre_medico          VARCHAR(120) NOT NULL,
    especialidad           VARCHAR(50)  NOT NULL,
    diagnostico            TEXT         NOT NULL,
    medicamentos_recetados TEXT,
    observaciones          TEXT
);

-- ==================== DATOS INICIALES ====================

-- 20 Pacientes (PAC001..PAC020)
INSERT INTO pacientes (codigo, nombres, apellidos, telefono, dni, edad, genero, tipo_sangre, alergias, correo, tiene_sis, numero_sis) VALUES
('PAC001','Carmen','Palacios Diaz','913368642','77498436',68,'F','B+','Penicilina','carmen.palacios@ate.com',FALSE,NULL),
('PAC002','Andrea','Vega Gomez','919616293','71989385',37,'F','O+','Ninguna','andrea.vega@ate.com',FALSE,NULL),
('PAC003','Alicia','Garcia Ruiz','923119809','76752009',58,'F','A-','Lactosa','alicia.garcia@ate.com',TRUE,'SIS000003'),
('PAC004','Laura','Mendez Cabrera','955356689','77588821',63,'F','B-','Ninguna','laura.mendez@ate.com',TRUE,'SIS000004'),
('PAC005','Carlos','Vega Zapata','964827318','77795760',80,'M','B-','Lactosa','carlos.vega@ate.com',TRUE,'SIS000005'),
('PAC006','Julia','Lopez Perez','910556562','70091288',48,'F','AB+','Penicilina','julia.lopez@ate.com',FALSE,NULL),
('PAC007','Daniel','Villar Herrera','914464977','76334940',66,'M','AB-','Ninguna','daniel.villar@ate.com',FALSE,NULL),
('PAC008','Beatriz','Silva Villar','970168818','72887639',69,'F','A+','Lactosa','beatriz.silva@ate.com',TRUE,'SIS000008'),
('PAC009','Eduardo','Villar Loayza','998616638','71749054',19,'M','A+','Aspirina','eduardo.villar@ate.com',FALSE,NULL),
('PAC010','Gloria','Diaz Lopez','960734295','76847774',39,'F','A-','Polvo','gloria.diaz@ate.com',TRUE,'SIS000010'),
('PAC011','Valentina','Salas Delgado','916860431','74509473',61,'F','O+','Polen','valentina.salas@ate.com',FALSE,NULL),
('PAC012','Sara','Lopez Reyes','912732915','77639421',29,'F','B+','Lactosa','sara.lopez@ate.com',TRUE,'SIS000012'),
('PAC013','Alicia','Garcia Palacios','929560633','79365179',23,'F','O+','Lactosa','alicia.garcia@ate.com',TRUE,'SIS000013'),
('PAC014','Jorge','Perez Mendez','916735574','75697260',46,'M','AB+','Penicilina','jorge.perez@ate.com',TRUE,'SIS000014'),
('PAC015','Laura','Cabrera Castro','940867240','79269060',34,'F','O+','Aspirina','laura.cabrera@ate.com',TRUE,'SIS000015'),
('PAC016','Hugo','Delgado Cabrera','991493702','71682109',67,'M','O-','Ninguna','hugo.delgado@ate.com',TRUE,'SIS000016'),
('PAC017','Rafael','Zapata Garcia','973154024','75757681',76,'M','AB+','Penicilina','rafael.zapata@ate.com',TRUE,'SIS000017'),
('PAC018','Camila','Cruz Lopez','963846309','75944290',54,'F','O+','Polvo','camila.cruz@ate.com',TRUE,'SIS000018'),
('PAC019','Cesar','Mendez Fuentes','984761843','79080054',75,'M','O-','Aspirina','cesar.mendez@ate.com',FALSE,NULL),
('PAC020','Maria','Roman Cabrera','926514196','76401846',62,'F','B-','Aspirina','maria.roman@ate.com',TRUE,'SIS000020'),
('PAC021','Maria','Villar Ruiz','990421217','71444485',39,'F','A-','Polvo','maria.villar@ate.com',FALSE,NULL),
('PAC022','Rosa','Villar Navarro','972724807','78429110',67,'F','B+','Ninguna','rosa.villar@ate.com',FALSE,NULL),
('PAC023','Patricia','Cabrera Roman','917094577','78704567',79,'F','B+','Aspirina','patricia.cabrera@ate.com',FALSE,NULL),
('PAC024','Pedro','Sanchez Zapata','951239220','79519855',33,'M','A+','Mariscos','pedro.sanchez@ate.com',TRUE,'SIS000024'),
('PAC025','Sara','Reyes Aguilar','918771750','74555086',85,'F','O-','Mariscos','sara.reyes@ate.com',FALSE,NULL),
('PAC026','Eduardo','Paredes Gonzalez','960444983','75688235',38,'M','O+','Ninguna','eduardo.paredes@ate.com',TRUE,'SIS000026'),
('PAC027','Martha','Navarro Mendoza','983752660','71016839',28,'F','O-','Ninguna','martha.navarro@ate.com',TRUE,'SIS000027'),
('PAC028','Martha','Reyes Navarro','940042156','76107829',77,'F','O+','Ninguna','martha.reyes@ate.com',TRUE,'SIS000028'),
('PAC029','Martha','Fuentes Quispe','943030696','78693301',79,'F','O+','Polvo','martha.fuentes@ate.com',FALSE,NULL),
('PAC030','Cesar','Silva Villar','942724633','73733094',24,'M','B-','Polvo','cesar.silva@ate.com',TRUE,'SIS000030'),
('PAC031','Juana','Roman Cruz','948972406','70464748',60,'F','A+','Ninguna','juana.roman@ate.com',TRUE,'SIS000031'),
('PAC032','Fernando','Gomez Sanchez','925486562','72715400',31,'M','A+','Lactosa','fernando.gomez@ate.com',FALSE,NULL),
('PAC033','Hugo','Rodriguez Rojas','957148374','73340206',60,'M','O-','Polvo','hugo.rodriguez@ate.com',FALSE,NULL),
('PAC034','Patricia','Rojas Sanchez','910653338','76163371',40,'F','A+','Mariscos','patricia.rojas@ate.com',FALSE,NULL),
('PAC035','Teresa','Flores Navarro','966003044','70471246',33,'F','B-','Ninguna','teresa.flores@ate.com',FALSE,NULL),
('PAC036','Isabel','Lopez Palacios','917709905','71261478',73,'F','O-','Polen','isabel.lopez@ate.com',FALSE,NULL),
('PAC037','Ricardo','Sanchez Roman','967524324','78321921',83,'M','B+','Ninguna','ricardo.sanchez@ate.com',TRUE,'SIS000037'),
('PAC038','Jose','Herrera Palacios','917876312','70676179',75,'M','A-','Ninguna','jose.herrera@ate.com',TRUE,'SIS000038'),
('PAC039','Julio','Zapata Diaz','980041344','73273520',28,'M','B+','Ninguna','julio.zapata@ate.com',FALSE,NULL),
('PAC040','Ricardo','Benites Aguilar','938203981','70861786',75,'M','O-','Aspirina','ricardo.benites@ate.com',FALSE,NULL),
('PAC041','Silvia','Chavez Rojas','926870371','79830476',29,'F','B+','Aspirina','silvia.chavez@ate.com',FALSE,NULL),
('PAC042','Teresa','Paredes Lopez','941555717','74834886',50,'F','A+','Mariscos','teresa.paredes@ate.com',FALSE,NULL),
('PAC043','Oscar','Aguilar Benites','924890594','74748214',53,'M','B-','Ninguna','oscar.aguilar@ate.com',FALSE,NULL),
('PAC044','Andrea','Gomez Molina','926428898','72695548',39,'F','B-','Penicilina','andrea.gomez@ate.com',TRUE,'SIS000044'),
('PAC045','Diana','Mendoza Cruz','965140716','71886842',74,'F','B+','Mariscos','diana.mendoza@ate.com',FALSE,NULL),
('PAC046','Sofia','Valdez Reyes','955818691','73788679',25,'F','A-','Polvo','sofia.valdez@ate.com',TRUE,'SIS000046'),
('PAC047','Alejandro','Paredes Roman','998372461','75920713',74,'M','AB-','Aspirina','alejandro.paredes@ate.com',FALSE,NULL),
('PAC048','Gabriela','Perez Herrera','983023334','73242864',51,'F','B+','Ninguna','gabriela.perez@ate.com',TRUE,'SIS000048'),
('PAC049','Pedro','Carrasco Villar','965956784','71242889',62,'M','AB-','Ninguna','pedro.carrasco@ate.com',FALSE,NULL),
('PAC050','Daniel','Rodriguez Quispe','974997917','70418563',77,'M','A+','Penicilina','daniel.rodriguez@ate.com',FALSE,NULL),
('PAC051','Andrea','Martinez Valdez','910403270','75493008',83,'F','B-','Ninguna','andrea.martinez@ate.com',TRUE,'SIS000051'),
('PAC052','Carlos','Navarro Villar','978050986','76888344',32,'M','O-','Lactosa','carlos.navarro@ate.com',TRUE,'SIS000052'),
('PAC053','Javier','Villar Reyes','921899491','71444149',27,'M','A-','Penicilina','javier.villar@ate.com',TRUE,'SIS000053'),
('PAC054','Teresa','Silva Carrasco','928903137','70290104',48,'F','B-','Mariscos','teresa.silva@ate.com',TRUE,'SIS000054'),
('PAC055','Roberto','Ortiz Flores','942868890','74480620',32,'M','AB-','Mariscos','roberto.ortiz@ate.com',TRUE,'SIS000055'),
('PAC056','Carmen','Navarro Mendoza','937009875','78492832',38,'F','A-','Polvo','carmen.navarro@ate.com',TRUE,'SIS000056'),
('PAC057','Patricia','Roman Quispe','938336692','72954684',77,'F','AB-','Ninguna','patricia.roman@ate.com',FALSE,NULL),
('PAC058','Marcos','Molina Loayza','942568725','72544578',73,'M','A-','Polvo','marcos.molina@ate.com',TRUE,'SIS000058'),
('PAC059','Juan','Diaz Castro','952102497','71554916',59,'M','O-','Polen','juan.diaz@ate.com',TRUE,'SIS000059'),
('PAC060','Raul','Zapata Salas','928766488','72839538',73,'M','O+','Ninguna','raul.zapata@ate.com',TRUE,'SIS000060'),
('PAC061','Oscar','Carrasco Quispe','936320378','71772644',29,'M','O-','Ninguna','oscar.carrasco@ate.com',FALSE,NULL),
('PAC062','Juana','Cabrera Carrasco','950753541','70115148',34,'F','AB+','Ninguna','juana.cabrera@ate.com',TRUE,'SIS000062'),
('PAC063','Mario','Chavez Salas','910198853','70768008',29,'M','O-','Penicilina','mario.chavez@ate.com',TRUE,'SIS000063'),
('PAC064','Valentina','Rojas Mendez','919288675','74267438',47,'F','AB+','Ninguna','valentina.rojas@ate.com',TRUE,'SIS000064'),
('PAC065','Miguel','Zapata Flores','965880117','74343967',18,'M','A+','Aspirina','miguel.zapata@ate.com',FALSE,NULL),
('PAC066','Gabriel','Navarro Rojas','935033562','78175219',83,'M','AB-','Mariscos','gabriel.navarro@ate.com',FALSE,NULL),
('PAC067','Ruben','Gomez Perez','911734146','71491278',38,'M','O+','Ninguna','ruben.gomez@ate.com',TRUE,'SIS000067'),
('PAC068','Francisco','Campos Mendoza','986754332','77632762',39,'M','AB+','Polvo','francisco.campos@ate.com',FALSE,NULL),
('PAC069','Gloria','Molina Martinez','966805749','72191820',69,'F','O+','Ninguna','gloria.molina@ate.com',FALSE,NULL),
('PAC070','Teresa','Cabrera Loayza','911285244','70271562',71,'F','A+','Polvo','teresa.cabrera@ate.com',TRUE,'SIS000070'),
('PAC071','Hugo','Garcia Diaz','974835182','77049326',41,'M','B-','Ninguna','hugo.garcia@ate.com',TRUE,'SIS000071'),
('PAC072','Pedro','Mendoza Ruiz','934506747','74043533',59,'M','B-','Ninguna','pedro.mendoza@ate.com',TRUE,'SIS000072'),
('PAC073','Claudia','Flores Sanchez','963557669','76161451',52,'F','B+','Polvo','claudia.flores@ate.com',TRUE,'SIS000073'),
('PAC074','Claudia','Quispe Mendoza','910748342','70094066',36,'F','A-','Ninguna','claudia.quispe@ate.com',TRUE,'SIS000074'),
('PAC075','Martha','Palacios Chavez','997780192','71475309',37,'F','B+','Polvo','martha.palacios@ate.com',FALSE,NULL),
('PAC076','Sara','Loayza Palacios','944779879','79612702',29,'F','AB-','Ninguna','sara.loayza@ate.com',TRUE,'SIS000076'),
('PAC077','Natalia','Chavez Ruiz','974712278','77319668',61,'F','A-','Ninguna','natalia.chavez@ate.com',FALSE,NULL),
('PAC078','Jose','Herrera Rojas','933627735','70234375',61,'M','O-','Mariscos','jose.herrera@ate.com',FALSE,NULL),
('PAC079','Beatriz','Loayza Gonzalez','927872146','75294017',71,'F','AB+','Polvo','beatriz.loayza@ate.com',TRUE,'SIS000079'),
('PAC080','Roberto','Palacios Herrera','982984619','70724749',78,'M','O-','Ninguna','roberto.palacios@ate.com',TRUE,'SIS000080'),
('PAC081','Camila','Roman Diaz','988696023','76466799',63,'F','O+','Lactosa','camila.roman@ate.com',TRUE,'SIS000081'),
('PAC082','Ana','Delgado Roman','955612813','79066657',78,'F','A+','Polen','ana.delgado@ate.com',FALSE,NULL),
('PAC083','Alicia','Mendoza Zapata','961712099','75196200',32,'F','O-','Ninguna','alicia.mendoza@ate.com',FALSE,NULL),
('PAC084','Fernando','Benites Vega','999941784','73399931',54,'M','O+','Lactosa','fernando.benites@ate.com',FALSE,NULL),
('PAC085','Roberto','Lopez Molina','921371926','74332967',72,'M','AB-','Lactosa','roberto.lopez@ate.com',TRUE,'SIS000085'),
('PAC086','Eduardo','Rojas Molina','985501974','79258550',64,'M','A-','Aspirina','eduardo.rojas@ate.com',FALSE,NULL),
('PAC087','Jose','Roman Navarro','991715348','73476134',71,'M','O-','Mariscos','jose.roman@ate.com',TRUE,'SIS000087'),
('PAC088','Patricia','Cruz Chavez','923950994','73214174',24,'F','AB-','Lactosa','patricia.cruz@ate.com',TRUE,'SIS000088'),
('PAC089','Manuel','Lopez Gomez','987687139','76596535',21,'M','O+','Polvo','manuel.lopez@ate.com',TRUE,'SIS000089'),
('PAC090','Beatriz','Gomez Martinez','911847490','79346120',54,'F','B-','Ninguna','beatriz.gomez@ate.com',TRUE,'SIS000090'),
('PAC091','Eduardo','Vega Sanchez','923959434','75710400',74,'M','A+','Ninguna','eduardo.vega@ate.com',FALSE,NULL),
('PAC092','Luciana','Chavez Gonzalez','915846501','75486727',20,'F','A+','Ninguna','luciana.chavez@ate.com',FALSE,NULL),
('PAC093','Elena','Reyes Molina','923460316','75926913',66,'F','B+','Lactosa','elena.reyes@ate.com',FALSE,NULL),
('PAC094','Luciana','Campos Rodriguez','946573056','70873157',48,'F','AB-','Ninguna','luciana.campos@ate.com',FALSE,NULL),
('PAC095','Alejandro','Diaz Rojas','995958823','73783916',65,'M','B-','Ninguna','alejandro.diaz@ate.com',TRUE,'SIS000095'),
('PAC096','Alicia','Ortiz Rojas','952609870','74924669',38,'F','AB-','Lactosa','alicia.ortiz@ate.com',TRUE,'SIS000096'),
('PAC097','Daniel','Reyes Flores','968455739','78533723',65,'M','O-','Polvo','daniel.reyes@ate.com',FALSE,NULL),
('PAC098','Beatriz','Fuentes Salas','964717017','72088084',78,'F','A-','Ninguna','beatriz.fuentes@ate.com',TRUE,'SIS000098'),
('PAC099','Carlos','Rodriguez Palacios','997516817','79789575',84,'M','B-','Ninguna','carlos.rodriguez@ate.com',FALSE,NULL),
('PAC100','Pedro','Valdez Fuentes','931267155','73936405',60,'M','AB-','Ninguna','pedro.valdez@ate.com',FALSE,NULL);

-- 20 Medicos (MED001..MED020)
INSERT INTO medicos (codigo, nombres, apellidos, telefono, cmp, especialidad, correo, turno) VALUES
('MED001','Carlos','Perez','901111111','CMP12345','CARDIOLOGIA','cperez@ate.com','Mañana'),
('MED002','Maria','Soto','902222222','CMP54321','PEDIATRIA','msoto@ate.com','Tarde'),
('MED003','Julio','Diaz','903333333','CMP88888','DERMATOLOGIA','jdiaz@ate.com','Mañana'),
('MED004','Andrea','Ramos','904444444','CMP11223','MEDICINA_GENERAL','aramos@ate.com','Tarde'),
('MED005','Roberto','Leiva','905555555','CMP22334','TRAUMATOLOGIA','rleiva@ate.com','Mañana'),
('MED006','Veronica','Gomez','906666666','CMP33445','MEDICINA_GENERAL','vgomez@ate.com','Tarde'),
('MED007','Alonso','Ruiz','907777777','CMP44556','GINECOLOGIA','aruiz@ate.com','Mañana'),
('MED008','Pamela','Silva','908888888','CMP55667','CARDIOLOGIA','psilva@ate.com','Tarde'),
('MED009','Jorge','Molina','909999999','CMP66778','PEDIATRIA','jmolina@ate.com','Mañana'),
('MED010','Carmen','Delgado','910101010','CMP77889','DERMATOLOGIA','cdelgado@ate.com','Tarde'),
('MED011','Mauricio','Fuentes','911212121','CMP88990','MEDICINA_GENERAL','mfuentes@ate.com','Mañana'),
('MED012','Natalia','Campos','912323232','CMP99001','TRAUMATOLOGIA','ncampos@ate.com','Tarde'),
('MED013','Hector','Loayza','913434343','CMP10987','MEDICINA_GENERAL','hloayza@ate.com','Mañana'),
('MED014','Paola','Carrasco','914545454','CMP11876','GINECOLOGIA','pcarrasco@ate.com','Tarde'),
('MED015','Martin','Cabrera','915656565','CMP12765','CARDIOLOGIA','mcabrera@ate.com','Mañana'),
('MED016','Renata','Palacios','916767676','CMP13654','PEDIATRIA','rpalacios@ate.com','Tarde'),
('MED017','Ivan','Mendez','917878787','CMP14543','DERMATOLOGIA','imendez@ate.com','Mañana'),
('MED018','Fabiola','Reyes','918989898','CMP15432','MEDICINA_GENERAL','freyes@ate.com','Tarde'),
('MED019','Cristian','Zapata','919090909','CMP16321','TRAUMATOLOGIA','czapata@ate.com','Mañana'),
('MED020','Melissa','Villar','920202020','CMP17210','GINECOLOGIA','mvillar@ate.com','Tarde');

-- 12 Consultorios (CON001..CON012) distribuidos en 3 pisos, 4 por piso
-- El mapa 2D muestra exactamente estos 12: Piso 1 (C1-C4), Piso 2 (C1-C4), Piso 3 (C1-C4)
INSERT INTO consultorios (codigo, nombre, piso, especialidad, disponible) VALUES
('CON001','Consultorio San Gabriel',    1,'CARDIOLOGIA',    TRUE),
('CON002','Consultorio Infantil Norte', 1,'PEDIATRIA',      TRUE),
('CON003','Consultorio Medicina Uno',   1,'MEDICINA_GENERAL',TRUE),
('CON004','Consultorio Trauma Norte',   1,'TRAUMATOLOGIA',  TRUE),
('CON005','Consultorio Cardio Sur',     2,'CARDIOLOGIA',    TRUE),
('CON006','Consultorio Infantil Sol',   2,'PEDIATRIA',      TRUE),
('CON007','Consultorio Trauma Este',    2,'TRAUMATOLOGIA',  TRUE),
('CON008','Consultorio Medicina Dos',   2,'MEDICINA_GENERAL',TRUE),
('CON009','Consultorio Derma Centro',   3,'DERMATOLOGIA',   TRUE),
('CON010','Consultorio Gine Centro',    3,'GINECOLOGIA',    TRUE),
('CON011','Consultorio Cardio Luz',     3,'CARDIOLOGIA',    TRUE),
('CON012','Consultorio Medicina Tres',  3,'MEDICINA_GENERAL',TRUE);

-- 12 Citas (CIT001..CIT012), una por cada consultorio registrado
INSERT INTO citas (codigo, codigo_paciente, codigo_medico, codigo_consultorio, fecha, hora, motivo, costo, estado) VALUES
('CIT001','PAC001','MED001','CON001','2026-04-20','08:00','Control general',90.00,'PROGRAMADA'),
('CIT002','PAC002','MED002','CON002','2026-04-21','08:30','Consulta preventiva',95.00,'PROGRAMADA'),
('CIT003','PAC003','MED004','CON003','2026-04-22','09:00','Dolor recurrente',100.00,'PROGRAMADA'),
('CIT004','PAC004','MED005','CON004','2026-04-23','09:30','Revision anual',105.00,'PROGRAMADA'),
('CIT005','PAC005','MED001','CON005','2026-04-24','10:00','Chequeo medico',110.00,'PROGRAMADA'),
('CIT006','PAC006','MED002','CON006','2026-04-25','10:30','Control de sintomas',115.00,'PROGRAMADA'),
('CIT007','PAC007','MED005','CON007','2026-04-26','11:00','Evaluacion medica',120.00,'PROGRAMADA'),
('CIT008','PAC008','MED008','CON008','2026-04-27','11:30','Consulta especializada',125.00,'PROGRAMADA'),
('CIT009','PAC009','MED003','CON009','2026-04-28','12:00','Seguimiento clinico',130.00,'PROGRAMADA'),
('CIT010','PAC010','MED007','CON010','2026-04-29','12:30','Revision de resultados',135.00,'PROGRAMADA'),
('CIT011','PAC011','MED008','CON011','2026-04-30','14:00','Dolor articular',140.00,'PROGRAMADA'),
('CIT012','PAC012','MED004','CON012','2026-05-01','14:30','Alergia leve',145.00,'PROGRAMADA');

-- 6 Medicamentos (alineados con MemoriaMedicamentoDAO)
INSERT INTO medicamentos (codigo, nombre, categoria, descripcion, precio, stock) VALUES
('MED001','Prednisona 50mg','Corticosteroide','Jarabe / Suspension de uso oral para corticosteroide',6.35,88),
('MED002','Amlodipino 10mg','Cardiovascular','Tableta de administracion oral para cardiovascular',26.22,12),
('MED003','Clonazepam 10mg','Psicotropico','Tableta de administracion oral para psicotropico',12.67,110),
('MED004','Loratadina 1g','Antialergico','Tableta de administracion oral para antialergico',21.57,112),
('MED005','Diclofenaco 100mg','Antiinflamatorio','Tableta de administracion oral para antiinflamatorio',24.69,83),
('MED006','Cetirizina 10mg','Antialergico','Tableta de administracion oral para antialergico',21.98,97),
('MED007','Ibuprofeno 1%','Antiinflamatorio','Crema / Topico para uso cutaneo para antiinflamatorio',24.61,12),
('MED008','Tramadol 50mg','Analgesico','Tableta de administracion oral para analgesico',11.7,31),
('MED009','Enoxaparina 100mg','Cardiovascular','Ampolla / Inyectable de uso intramuscular o endovenoso',41.59,101),
('MED010','Ranitidina 50mg','Gastroprotector','Ampolla / Inyectable de uso intramuscular o endovenoso',12.44,18),
('MED011','Calcio + Vitamina D 10mg','Suplemento','Tableta de administracion oral para suplemento',36.95,94),
('MED012','Clotrimazol 1%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',20.39,144),
('MED013','Diazepam 50mg','Psicotropico','Ampolla / Inyectable de uso intramuscular o endovenoso',43.16,147),
('MED014','Omeprazol 500mg','Gastroprotector','Ampolla / Inyectable de uso intramuscular o endovenoso',14.38,145),
('MED015','Tramadol 250mg','Analgesico','Tableta de administracion oral para analgesico',39.28,122),
('MED016','Glibenclamida 500mg','Antidiabetico','Tableta de administracion oral para antidiabetico',5.04,78),
('MED017','Salbutamol 50mg','Respiratorio','Jarabe / Suspension de uso oral para respiratorio',21.75,61),
('MED018','Diazepam 250mg','Psicotropico','Tableta de administracion oral para psicotropico',20.06,30),
('MED019','Enoxaparina 500mg','Cardiovascular','Ampolla / Inyectable de uso intramuscular o endovenoso',26.12,61),
('MED020','Tramadol 250mg','Analgesico','Ampolla / Inyectable de uso intramuscular o endovenoso',36.88,56),
('MED021','Cetirizina 50mg','Antialergico','Jarabe / Suspension de uso oral para antialergico',6.48,148),
('MED022','Azitromicina 250mg','Antibiotico','Jarabe / Suspension de uso oral para antibiotico',19.55,127),
('MED023','Losartan 50mg','Cardiovascular','Tableta de administracion oral para cardiovascular',13.26,108),
('MED024','Clotrimazol 5%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',39.67,29),
('MED025','Losartan 10mg','Cardiovascular','Tableta de administracion oral para cardiovascular',10.99,128),
('MED026','Hierro Sulfato 20mg','Suplemento','Jarabe / Suspension de uso oral para suplemento',13.56,94),
('MED027','Diazepam 500mg','Psicotropico','Ampolla / Inyectable de uso intramuscular o endovenoso',10.14,87),
('MED028','Furosemida 100mg','Cardiovascular','Ampolla / Inyectable de uso intramuscular o endovenoso',10.4,143),
('MED029','Ranitidina 10mg','Gastroprotector','Tableta de administracion oral para gastroprotector',4.36,27),
('MED030','Ciprofloxacino 500mg','Antibiotico','Tableta de administracion oral para antibiotico',31.09,35),
('MED031','Amlodipino 10mg','Cardiovascular','Tableta de administracion oral para cardiovascular',10.25,92),
('MED032','Enalapril 100mg','Cardiovascular','Tableta de administracion oral para cardiovascular',7.31,18),
('MED033','Fluconazol 1%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',18.33,129),
('MED034','Ketoconazol 500mg','Antimicotico','Tableta de administracion oral para antimicotico',27.88,43),
('MED035','Diazepam 50mg','Psicotropico','Tableta de administracion oral para psicotropico',38.93,48),
('MED036','Amoxicilina 1g','Antibiotico','Tableta de administracion oral para antibiotico',30.03,12),
('MED037','Warfarina 1g','Cardiovascular','Tableta de administracion oral para cardiovascular',12.17,149),
('MED038','Metoclopramida 50mg','Antiemetico','Ampolla / Inyectable de uso intramuscular o endovenoso',38.42,107),
('MED039','Fluticasona','Respiratorio','Inhalador / Spray de uso inhalatorio directo',22.21,78),
('MED040','Hierro Sulfato 50mg','Suplemento','Jarabe / Suspension de uso oral para suplemento',15.66,62),
('MED041','Ibuprofeno 500mg','Antiinflamatorio','Tableta de administracion oral para antiinflamatorio',12.37,35),
('MED042','Salbutamol + Bromuro','Respiratorio','Inhalador / Spray de uso inhalatorio directo',23.92,28),
('MED043','Prednisona 1g','Corticosteroide','Jarabe / Suspension de uso oral para corticosteroide',36.19,120),
('MED044','Glibenclamida 50mg','Antidiabetico','Tableta de administracion oral para antidiabetico',29.92,113),
('MED045','Azitromicina 10mg','Antibiotico','Tableta de administracion oral para antibiotico',30.18,118),
('MED046','Clonazepam 10mg','Psicotropico','Tableta de administracion oral para psicotropico',21.54,120),
('MED047','Calcio + Vitamina D 100mg','Suplemento','Tableta de administracion oral para suplemento',41.21,34),
('MED048','Simvastatina 20mg','Cardiovascular','Tableta de administracion oral para cardiovascular',32.58,133),
('MED049','Fluticasona','Respiratorio','Inhalador / Spray de uso inhalatorio directo',5.37,84),
('MED050','Omeprazol 250mg','Gastroprotector','Tableta de administracion oral para gastroprotector',31.43,28),
('MED051','Omeprazol 1g','Gastroprotector','Tableta de administracion oral para gastroprotector',16.19,10),
('MED052','Enalapril 500mg','Cardiovascular','Tableta de administracion oral para cardiovascular',36.12,78),
('MED053','Warfarina 20mg','Cardiovascular','Tableta de administracion oral para cardiovascular',42.27,59),
('MED054','Claritromicina 20mg','Antibiotico','Tableta de administracion oral para antibiotico',3.0,103),
('MED055','Metformina 50mg','Antidiabetico','Tableta de administracion oral para antidiabetico',16.42,125),
('MED056','Loratadina 250mg','Antialergico','Jarabe / Suspension de uso oral para antialergico',5.85,94),
('MED057','Diazepam 250mg','Psicotropico','Ampolla / Inyectable de uso intramuscular o endovenoso',43.18,39),
('MED058','Clotrimazol 1%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',39.16,96),
('MED059','Acido Folico 10mg','Suplemento','Tableta de administracion oral para suplemento',41.53,89),
('MED060','Dexametasona 20mg','Corticosteroide','Ampolla / Inyectable de uso intramuscular o endovenoso',31.65,43),
('MED061','Clotrimazol 2%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',19.53,71),
('MED062','Claritromicina 1g','Antibiotico','Jarabe / Suspension de uso oral para antibiotico',32.66,31),
('MED063','Amlodipino 100mg','Cardiovascular','Tableta de administracion oral para cardiovascular',3.51,28),
('MED064','Ranitidina 100mg','Gastroprotector','Ampolla / Inyectable de uso intramuscular o endovenoso',24.97,53),
('MED065','Simvastatina 100mg','Cardiovascular','Tableta de administracion oral para cardiovascular',44.73,122),
('MED066','Aciclovir 1g','Antiviral','Tableta de administracion oral para antiviral',44.89,76),
('MED067','Tramadol 10mg','Analgesico','Tableta de administracion oral para analgesico',26.6,141),
('MED068','Metoclopramida 500mg','Antiemetico','Ampolla / Inyectable de uso intramuscular o endovenoso',27.89,48),
('MED069','Warfarina 10mg','Cardiovascular','Tableta de administracion oral para cardiovascular',44.21,37),
('MED070','Salbutamol 100mg','Respiratorio','Jarabe / Suspension de uso oral para respiratorio',6.31,97),
('MED071','Tramadol 50mg','Analgesico','Tableta de administracion oral para analgesico',23.48,98),
('MED072','Calcio + Vitamina D 10mg','Suplemento','Tableta de administracion oral para suplemento',34.49,14),
('MED073','Aciclovir 10mg','Antiviral','Tableta de administracion oral para antiviral',44.92,99),
('MED074','Enalapril 10mg','Cardiovascular','Tableta de administracion oral para cardiovascular',9.38,87),
('MED075','Enoxaparina 50mg','Cardiovascular','Ampolla / Inyectable de uso intramuscular o endovenoso',17.82,10),
('MED076','Budesonida','Respiratorio','Inhalador / Spray de uso inhalatorio directo',27.12,77),
('MED077','Naproxeno 1%','Antiinflamatorio','Crema / Topico para uso cutaneo para antiinflamatorio',38.93,11),
('MED078','Ketoconazol 1g','Antimicotico','Tableta de administracion oral para antimicotico',9.17,36),
('MED079','Clopidogrel 20mg','Cardiovascular','Tableta de administracion oral para cardiovascular',10.25,40),
('MED080','Ciprofloxacino 50mg','Antibiotico','Ampolla / Inyectable de uso intramuscular o endovenoso',28.43,122),
('MED081','Metoclopramida 50mg','Antiemetico','Tableta de administracion oral para antiemetico',2.45,120),
('MED082','Paracetamol 10mg','Analgesico','Tableta de administracion oral para analgesico',2.02,61),
('MED083','Ciprofloxacino 1g','Antibiotico','Tableta de administracion oral para antibiotico',31.66,17),
('MED084','Diclofenaco 250mg','Antiinflamatorio','Ampolla / Inyectable de uso intramuscular o endovenoso',32.07,96),
('MED085','Terbinafina 5%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',39.32,126),
('MED086','Naproxeno 5%','Antiinflamatorio','Crema / Topico para uso cutaneo para antiinflamatorio',31.36,118),
('MED087','Aspirina 20mg','Analgesico','Tableta de administracion oral para analgesico',37.85,60),
('MED088','Simvastatina 1g','Cardiovascular','Tableta de administracion oral para cardiovascular',13.06,98),
('MED089','Fluconazol 5%','Antimicotico','Crema / Topico para uso cutaneo para antimicotico',28.23,118),
('MED090','Simvastatina 500mg','Cardiovascular','Tableta de administracion oral para cardiovascular',18.54,112),
('MED091','Simvastatina 100mg','Cardiovascular','Tableta de administracion oral para cardiovascular',8.13,61),
('MED092','Prednisona 20mg','Corticosteroide','Jarabe / Suspension de uso oral para corticosteroide',44.51,28),
('MED093','Metoclopramida 250mg','Antiemetico','Tableta de administracion oral para antiemetico',13.42,126),
('MED094','Prednisona 250mg','Corticosteroide','Tableta de administracion oral para corticosteroide',9.63,103),
('MED095','Glibenclamida 10mg','Antidiabetico','Tableta de administracion oral para antidiabetico',14.96,123),
('MED096','Warfarina 20mg','Cardiovascular','Tableta de administracion oral para cardiovascular',4.46,29),
('MED097','Claritromicina 10mg','Antibiotico','Tableta de administracion oral para antibiotico',25.62,144),
('MED098','Metoclopramida 100mg','Antiemetico','Ampolla / Inyectable de uso intramuscular o endovenoso',14.33,142),
('MED099','Diazepam 250mg','Psicotropico','Tableta de administracion oral para psicotropico',13.15,29),
('MED100','Acido Folico 250mg','Suplemento','Tableta de administracion oral para suplemento',32.29,59);

-- 13 Consultas Clinicas (alineadas con MemoriaHistorialDAO)
INSERT INTO consultas_clinicas (codigo, codigo_paciente, nombre_paciente, fecha, nombre_medico, especialidad, diagnostico, medicamentos_recetados, observaciones) VALUES
('CC0001','PAC001','Ana Torres','2026-02-10','Dr. Carlos Perez','Cardiologia','Hipertension leve','Losartan 50mg, Amlodipino 5mg','Control en 30 dias'),
('CC0002','PAC001','Ana Torres','2026-03-15','Dr. Carlos Perez','Cardiologia','Hipertension controlada','Losartan 50mg','Reduccion de dosis'),
('CC0003','PAC001','Ana Torres','2026-04-20','Dr. Carlos Perez','Cardiologia','Presion normalizada','Losartan 25mg','Continuar tratamiento'),
('CC0004','PAC002','Luis Rojas','2026-01-08','Dra. Maria Soto','Pediatria','Resfriado comun','Paracetamol 500mg, Vitamina C','Reposo 3 dias'),
('CC0005','PAC002','Luis Rojas','2026-03-22','Dra. Maria Soto','Pediatria','Faringitis aguda','Amoxicilina 500mg, Ibuprofeno','Antibiotico 7 dias'),
('CC0006','PAC002','Luis Rojas','2026-05-01','Dra. Maria Soto','Pediatria','Control rutinario','Multivitaminicos','Paciente en buen estado'),
('CC0007','PAC003','Carla Mendoza','2026-02-14','Dr. Julio Diaz','Dermatologia','Dermatitis alergica','Hidrocortisona crema, Loratadina','Evitar alergenos'),
('CC0008','PAC003','Carla Mendoza','2026-04-05','Dr. Julio Diaz','Dermatologia','Mejoria significativa','Loratadina 10mg','Mantenimiento'),
('CC0009','PAC004','Diego Castro','2026-03-10','Dra. Andrea Ramos','Medicina General','Gastritis aguda','Omeprazol 20mg, Antiacidos','Dieta blanda 1 semana'),
('CC0010','PAC004','Diego Castro','2026-05-05','Dra. Andrea Ramos','Medicina General','Gastritis en remision','Omeprazol 20mg','Continuar dieta'),
('CC0011','PAC005','Rosa Chavez','2026-01-20','Dr. Roberto Leiva','Traumatologia','Lumbalgia mecanica','Ibuprofeno 400mg, Miorrelajante','Fisioterapia 2x semana'),
('CC0012','PAC005','Rosa Chavez','2026-03-18','Dr. Roberto Leiva','Traumatologia','Mejoria parcial','Paracetamol 500mg','Continuar fisioterapia'),
('CC0013','PAC005','Rosa Chavez','2026-05-10','Dr. Roberto Leiva','Traumatologia','Alta medica','Ninguno','Recuperacion completa');

-- Registros iniciales de triaje (10 pacientes con fecha posterior al 24 de junio de 2026)
INSERT INTO triaje (codigo, codigo_paciente, nombre_paciente, fecha, peso, talla, temperatura, presion_arterial, frecuencia_cardiaca, nivel_prioridad, atendido) VALUES
('TR0001', 'PAC001', 'Ana Torres', '2026-06-25', 62.5, 1.62, 36.4, '120/80', 72, 'NORMAL', FALSE),
('TR0002', 'PAC002', 'Luis Rojas', '2026-06-26', 75.0, 1.75, 36.8, '130/85', 78, 'PRIORITARIO', FALSE),
('TR0003', 'PAC003', 'Carla Mendoza', '2026-06-27', 58.2, 1.60, 37.2, '115/75', 80, 'URGENTE', FALSE),
('TR0004', 'PAC004', 'Diego Castro', '2026-06-28', 70.8, 1.70, 36.5, '125/80', 70, 'NORMAL', FALSE),
('TR0005', 'PAC005', 'Rosa Chavez', '2026-06-29', 65.0, 1.65, 36.6, '118/72', 74, 'PRIORITARIO', FALSE),
('TR0006', 'PAC006', 'Miguel Flores', '2026-06-30', 82.3, 1.80, 36.7, '135/88', 82, 'URGENTE', FALSE),
('TR0007', 'PAC007', 'Patricia Lopez', '2026-07-01', 59.5, 1.61, 36.9, '120/80', 75, 'NORMAL', FALSE),
('TR0008', 'PAC008', 'Javier Ortiz', '2026-07-02', 78.4, 1.78, 36.4, '128/82', 76, 'PRIORITARIO', FALSE),
('TR0009', 'PAC009', 'Sofia Navarro', '2026-07-03', 55.0, 1.58, 36.5, '110/70', 68, 'URGENTE', FALSE),
('TR0010', 'PAC010', 'Ricardo Salas', '2026-07-04', 85.1, 1.82, 37.0, '140/90', 84, 'NORMAL', FALSE);

-- ==================== FIN DEL SCRIPT ====================
