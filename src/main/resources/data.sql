-- Вмъкване на нов потребител с роля на директор --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '9876543210', 'Ivan Ivanov', 'CD9876543', 'ivan.ivanov@example.com', 'ivan',
       'a9bd92ae5617c8c79012e6d957bf7155e9ca1fd89c79c747f393eee8255d44bd68a30fe5a7d9852e',
       'MALE', '0887755226', 'CN987654321'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'ivan');

-- Свързване на потребителя Ivan Ivanov с ролята DIRECTOR
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'ivan'),
    (SELECT id FROM roles WHERE role = 'DIRECTOR')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'ivan')
      AND role_id = (SELECT id FROM roles WHERE role = 'DIRECTOR')
);

-- Свързване на потребителя Ivan Ivanov с ролята CLIENT
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'ivan'),
    (SELECT id FROM roles WHERE role = 'CLIENT')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'ivan')
      AND role_id = (SELECT id FROM roles WHERE role = 'CLIENT')
);

-- Вмъкване на разплащателна сметка за Ivan Ivanov
INSERT INTO accounts (type, balance, client_number, user_id)
SELECT 'CHECKING', 20000.00, 'CN987654321', (SELECT id FROM users WHERE username = 'ivan')
WHERE NOT EXISTS (
    SELECT 1 FROM accounts
    WHERE client_number = 'CN987654321' AND type = 'CHECKING'
);

-- Вмъкване на специфична информация за разплащателната сметка в таблицата checking_accounts
INSERT INTO checking_accounts (debit_card_number, debit_card_pin, id)
SELECT '123456781234', '1234', (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'CHECKING')
WHERE NOT EXISTS (
    SELECT 1 FROM checking_accounts
    WHERE id = (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'CHECKING')
);

-- Актуализиране на полето checking_account_id в таблицата users
UPDATE users
SET checking_account_id = (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'CHECKING')
WHERE username = 'ivan';

-- Вмъкване на спестовна сметка за Ivan Ivanov
INSERT INTO accounts (type, balance, client_number, user_id)
SELECT 'SAVINGS', 20000.00, 'CN987654321', (SELECT id FROM users WHERE username = 'ivan')
WHERE NOT EXISTS (
    SELECT 1 FROM accounts
    WHERE client_number = 'CN987654321' AND type = 'SAVINGS'
);

-- Вмъкване на специфична информация за спестовната сметка в таблицата savings_accounts
INSERT INTO savings_accounts (safety_deposit_box, safety_deposit_key, id)
SELECT '987', '5678', (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'SAVINGS')
WHERE NOT EXISTS (
    SELECT 1 FROM savings_accounts
    WHERE id = (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'SAVINGS')
);

-- Актуализиране на полето savings_account_id в таблицата users
UPDATE users
SET savings_account_id = (SELECT id FROM accounts WHERE client_number = 'CN987654321' AND type = 'SAVINGS')
WHERE username = 'ivan';

-- Вмъкване на нов служител за потребителя Ivan Ivanov с роля DIRECTOR
INSERT INTO employees (business_email, password, user_id, role)
SELECT 'ivan_wave@financial.com', 'topsicret', (SELECT id FROM users WHERE username = 'ivan'), 'DIRECTOR'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE business_email = 'ivan_wave@financial.com');

-- Актуализиране на полето employee_id в таблицата users
UPDATE users
SET employee_id = (SELECT id FROM employees WHERE business_email = 'ivan_wave@financial.com')
WHERE username = 'ivan';
