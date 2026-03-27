-- Таблица пользователей (нужна для JPA Entity)
CREATE TABLE IF NOT EXISTS mock_users (id SERIAL PRIMARY KEY, name VARCHAR(255));
-- Таблица компаний (для статистики)
CREATE TABLE IF NOT EXISTS mock_companies (id SERIAL PRIMARY KEY, status VARCHAR(50), amount DOUBLE PRECISION);

INSERT INTO mock_users (name) VALUES ('User 1'), ('User 2');
INSERT INTO mock_companies (status, amount) VALUES ('COMPLETED', 100.50), ('COMPLETED', 200.00), ('PENDING', 50.00);