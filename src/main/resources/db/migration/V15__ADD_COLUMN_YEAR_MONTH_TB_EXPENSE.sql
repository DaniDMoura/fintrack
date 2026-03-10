ALTER TABLE tb_expense
ADD COLUMN year_month DATE NOT NULL;

CREATE INDEX idx_income_user_month
    ON tb_income(user_id, year_month);

CREATE INDEX idx_expense_user_month
    ON tb_expense(user_id, year_month);

