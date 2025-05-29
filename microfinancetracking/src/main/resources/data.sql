INSERT INTO users (username, password, email) VALUES ('user1', 'pass1', 'user1@example.com') ON CONFLICT (username) DO NOTHING;
INSERT INTO users (username, password, email) VALUES ('user2', 'pass2', 'user2@example.com') ON CONFLICT (username) DO NOTHING;

INSERT INTO categories (name, type) VALUES ('Maaş', 'GELİR') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, type) VALUES ('Serbest Kazanç', 'GELİR') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, type) VALUES ('Kira', 'GİDER') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, type) VALUES ('Market', 'GİDER') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, type) VALUES ('Ulaşım', 'GİDER') ON CONFLICT (name) DO NOTHING;
INSERT INTO categories (name, type) VALUES ('Fatura', 'GİDER') ON CONFLICT (name) DO NOTHING;

-- user1 için örnek işlemler
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Maaş'), 5000.00, 'Aylık maaş', '2025-04-01', 'GELİR') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Market'), 500.00, 'Market alışverişi', '2025-04-05', 'GİDER') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Kira'), 2000.00, 'Nisan ayı kira', '2025-04-10', 'GİDER') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Ulaşım'), 150.00, 'Toplu taşıma', '2025-04-12', 'GİDER') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Market'), 300.00, 'Ekstra market', '2025-05-01', 'GİDER') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Maaş'), 5000.00, 'Mayıs ayı maaş', '2025-05-01', 'GELİR') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (1, (SELECT id FROM categories WHERE name = 'Fatura'), 250.00, 'Elektrik faturası', '2025-05-15', 'GİDER') ON CONFLICT DO NOTHING;


-- user2 için örnek işlemler
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (2, (SELECT id FROM categories WHERE name = 'Serbest Kazanç'), 1500.00, 'Freelance iş', '2025-04-15', 'GELİR') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (2, (SELECT id FROM categories WHERE name = 'Market'), 200.00, 'Hızlı market', '2025-04-18', 'GİDER') ON CONFLICT DO NOTHING;
INSERT INTO transactions (user_id, category_id, amount, description, date, type) VALUES (2, (SELECT id FROM categories WHERE name = 'Ulaşım'), 100.00, 'Yakıt', '2025-05-05', 'GİDER') ON CONFLICT DO NOTHING;