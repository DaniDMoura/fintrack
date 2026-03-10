ALTER TABLE tb_user
ADD COLUMN income_id INTEGER REFERENCES tb_income(id)