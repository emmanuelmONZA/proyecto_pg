

1. Tabla de practicantes
CREATE TABLE IF NOT EXISTS practicantes (
    id                  SERIAL PRIMARY KEY,
    nombre              VARCHAR(120) NOT NULL,
    documento           VARCHAR(20)  NOT NULL UNIQUE,
    universidad         VARCHAR(100),
    semestre            VARCHAR(5),
    programa            VARCHAR(80),
    docente             VARCHAR(120),
    induccion_realizada BOOLEAN DEFAULT FALSE,
    arl_vigente         BOOLEAN DEFAULT FALSE,
    estado              VARCHAR(50)  DEFAULT 'Pendiente docs'
);

 2. Tabla de rotacionesA
CREATE TABLE IF NOT EXISTS rotaciones (
    id           SERIAL PRIMARY KEY,
    area         VARCHAR(100) NOT NULL,
    fecha_inicio DATE         NOT NULL,
    fecha_fin    DATE         NOT NULL,
    hora_inicio  TIME         NOT NULL,
    hora_fin     TIME         NOT NULL,
    cupo_maximo  INT          NOT NULL DEFAULT 10,
    docente      VARCHAR(120)
);

 3. Tabla de registros de acceso
CREATE TABLE IF NOT EXISTS registros_acceso (
    id              SERIAL PRIMARY KEY,
    nombre_practicante VARCHAR(120) NOT NULL,
    fecha           DATE      NOT NULL,
    hora            TIME      NOT NULL,
    area            VARCHAR(100),
    tipo            VARCHAR(10) CHECK (tipo IN ('Entrada', 'Salida'))
);

-- Datos de ejemplo 

INSERT INTO practicantes (nombre, documento, universidad, semestre, programa, docente, induccion_realizada, arl_vigente, estado)
VALUES
    ('Ana Torres',    '1002456781', 'Universidad Santo Tomas', '5', 'Enfermeria',    'Laura Medina', TRUE,  TRUE,  'En practica'),
    ('Mateo Gomez',   '1002456782', 'UPTC',                    '8', 'Medicina',      'Carlos Ruiz',  TRUE,  FALSE, 'Pendiente ARL'),
    ('Sofia Morales', '1002456783', 'Universidad de Boyaca',   '6', 'Bacteriologia', 'Laura Medina', FALSE, TRUE,  'Pendiente induccion')
ON CONFLICT (documento) DO NOTHING;

INSERT INTO rotaciones (area, fecha_inicio, fecha_fin, hora_inicio, hora_fin, cupo_maximo, docente)
VALUES
    ('Urgencias',              '2026-05-01', '2026-05-31', '07:00', '09:00', 15, 'Laura Medina'),
    ('Pediatria',              '2026-05-01', '2026-05-31', '14:00', '16:00',  8, 'Carlos Ruiz'),
    ('Hospitalizacion piso 5', '2026-05-01', '2026-05-31', '08:00', '12:00', 20, 'Laura Medina');

INSERT INTO registros_acceso (nombre_practicante, fecha, hora, area, tipo)
VALUES
    ('Ana Torres',  '2026-05-17', '07:05', 'Urgencias', 'Entrada'),
    ('Mateo Gomez', '2026-05-17', '07:08', 'Urgencias', 'Entrada'),
    ('Ana Torres',  '2026-05-17', '09:12', 'Urgencias', 'Salida');
