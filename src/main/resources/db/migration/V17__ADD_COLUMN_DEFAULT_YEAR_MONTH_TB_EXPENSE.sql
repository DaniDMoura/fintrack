ALTER TABLE tb_expense
ALTER COLUMN year_month
SET DEFAULT date_trunc('month', CURRENT_DATE)::date;
