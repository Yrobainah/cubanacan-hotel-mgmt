INSERT INTO pais (nombre, iso2) VALUES
  ('Cuba',   'CU'),
  ('España', 'ES'),
  ('Italia', 'IT')
ON CONFLICT (nombre) DO NOTHING;
