ALTER TABLE tb_income
ALTER COLUMN year_month
SET DEFAULT date_trunc('month', CURRENT_DATE)::date;
