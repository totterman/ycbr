INSERT INTO boats (club, cert, name, kind, make, model, sign, sailnr, hullnr, material, year, colour, loa, beam, draft, height, deplacement, sailarea, drive, fuel, water, septi, berths, radio, home, winter, owner, owner2, created_at, created_by, version) VALUES
('NJK', '11234', 'Spray', 'S', 'Swan', '40', 'A12345', 'FIN40', '007', 'G', '1992', 'Vit', 12.27, 3.94, 2.15, 18, 8.8, 77, 'I', 230, 420, 60, 5, 'OH234', 'Helsingfors', null, 'stina', null, CURRENT_TIMESTAMP, 'system', 1),
('NJK', '23424', 'Endeavour', 'M', 'Targa', '33', 'U22211', null, '42', 'G', '1997', 'Vit', 10.5, 3.4, 1.0, 5.4, 7.2, null, 'N', 400, 150, 50, 4, null, 'Sibbo', 'Sibbo', 'stina', null, CURRENT_TIMESTAMP, 'system', 1),
('NJK', '54647', 'Taihoro', 'S', 'Finngulf', '28e', 'P54112', 'L8254', '36', 'G', '2001', 'Blå', 8.61, 2.9, 1.65, 14.5, 3.1, 37.7, 'I', 50, 100, 20, 5, 'OH9876', 'Helsingfors', 'Valkom', 'ronja', null, CURRENT_TIMESTAMP, 'system', 1);

INSERT INTO engines (boats, pos, year, make, model, serial, power) VALUES
((SELECT boat_id FROM boats WHERE name = 'Spray'), 0, '1992', 'Yanmar', '3GM30F', 'YM12345', 27),
((SELECT boat_id FROM boats WHERE name = 'Endeavour'), 0, '1997', 'Cummins', 'C4GF', '653631', 180),
((SELECT boat_id FROM boats WHERE name = 'Endeavour'), 1, '1997', 'Cummins', 'C4GF', '653632', 180),
((SELECT boat_id FROM boats WHERE name = 'Taihoro'), 0, '2001', 'Volvo Penta', 'MD2020', 'VP54321', 11);

INSERT INTO i9events (place, starts, ends, created_at, created_by, version) VALUES
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
