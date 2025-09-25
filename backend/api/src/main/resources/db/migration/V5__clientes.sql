CREATE TABLE IF NOT EXISTS clientes (
  id BIGSERIAL PRIMARY KEY,
  nombre TEXT NOT NULL,
  apellidos TEXT NOT NULL,
  ci TEXT UNIQUE,
  sexo CHAR(1) CHECK (sexo IN ('M','F')),
  tipo_cliente TEXT NOT NULL CHECK (tipo IN ('TRADICIONAL','VIP')),
  cargo_rango TEXT,
  pais_id_origen BIGINT REFERENCES pais(id)
);

ALTER TABLE clientes
  ADD CONSTRAINT clientes_vip_cargo_chk
  CHECK (tipo_cliente <> 'VIP' OR cargo_rango IS NOT NULL);

CREATE INDEX IF NOT EXISTS idx_clientes_tipo ON clientes(tipo_cliente);
