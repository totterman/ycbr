INSERT INTO boats (owner, name, sign, kind, make, model, loa, draft, beam, deplacement, engines, year, created_at, created_by, version) VALUES
('ronja', 'Spray', 'A12345', 'S', 'Swan', '40', 12.01, 2.10, 4.20, 9000, 'Yanmar', '1992', CURRENT_TIMESTAMP, 'system', 1),
('stina', 'Endeavour', 'U22211', 'M', 'Targa', '27', 8.40, 1.00, 3.20, 5000, 'VP 280', '1997', CURRENT_TIMESTAMP, 'system', 1),
('stina', 'Taihoro', 'P54112', 'S', 'Finngulf', '28e', 8.72, 1.65, 2.90, 3000, 'VP MD2010', '2001', CURRENT_TIMESTAMP, 'system', 1);

INSERT INTO public.i9events (place, starts, ends, created_at, created_by, version) VALUES
('Björkholmen', '2025-05-06T17:00:00.000+02:00', '2025-05-06T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Blekholmen', '2025-05-11T17:00:00.000+02:00', '2025-05-11T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2025-05-26T17:00:00.000+02:00', '2025-05-26T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2026-05-07T17:00:00.000+02:00', '2026-05-07T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Blekholmen', '2026-05-12T17:00:00.000+02:00', '2026-05-12T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2026-05-27T17:00:00.000+02:00', '2026-05-27T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2027-05-08T17:00:00.000+02:00', '2027-05-08T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Blekholmen', '2027-05-13T17:00:00.000+02:00', '2027-05-13T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2027-05-28T17:00:00.000+02:00', '2027-05-28T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1),
('Björkholmen', '2028-05-09T17:00:00.000+02:00', '2028-05-09T20:00:00.000+02:00', CURRENT_TIMESTAMP, 'system', 1);
