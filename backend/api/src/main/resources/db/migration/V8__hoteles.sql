CREATE TABLE IF NOT EXISTS hoteles (
  id         BIGSERIAL PRIMARY KEY,
  nombre     TEXT NOT NULL,
  capacidad  INTEGER NOT NULL CHECK (capacidad >= 0),
  categoria  INTEGER CHECK (categoria BETWEEN 1 AND 5),
  direccion  TEXT,
  ciudad     TEXT,
  pais_id    BIGINT REFERENCES pais(id)
);

CREATE INDEX IF NOT EXISTS idx_hoteles_ciudad   ON hoteles(ciudad);
CREATE INDEX IF NOT EXISTS idx_hoteles_categoria ON hoteles(categoria);
CREATE INDEX IF NOT EXISTS idx_hoteles_pais     ON hoteles(pais_id);
