CREATE TABLE tb_user(
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE tb_income(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES tb_user(id),
    amount NUMERIC(19,2) NOT NULL CHECK (amount >= 0),
    reference_month DATE NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE tb_expense(
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES tb_user(id),
    amount NUMERIC(19,2) NOT NULL CHECK (amount >= 0),
    category VARCHAR(100) NOT NULL,
    reference_month DATE NOT NULL,
    description VARCHAR(255)
);

CREATE INDEX idx_income_user_month
    ON tb_income(user_id, reference_month);

CREATE INDEX idx_expense_user_month
    ON tb_expense(user_id, reference_month);
