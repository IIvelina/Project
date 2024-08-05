-- Вмъкване на нов потребител с роля на директор --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '9876543210', 'Ivan Ivanov', 'CD9876543', 'ivan.ivanov@example.com', 'ivan',
       'e51daae315c9e141f654be26af17094b8d5e3ac920c20041592c893544b4c156406a7f7e120414f5d04cbee278a9099a',
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




-- нов потребител Pesho
-- Вмъкване на нов потребител с роля на клиент --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '8765432109', 'Pesho Petrov', 'CD8765432', 'pesho.petrov@example.com', 'pesho',
       'e51daae315c9e141f654be26af17094b8d5e3ac920c20041592c893544b4c156406a7f7e120414f5d04cbee278a9099a',
       'MALE', '0887766335', 'CN876543210'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'pesho');

-- Свързване на потребителя Pesho Petrov с ролята CLIENT
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'pesho'),
    (SELECT id FROM roles WHERE role = 'CLIENT')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'pesho')
      AND role_id = (SELECT id FROM roles WHERE role = 'CLIENT')
);


-- нов потребител Dragan
-- Вмъкване на нов потребител с роля на клиент --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '7654321098', 'Dragan Draganov', 'CD7654321', 'dragan.draganov@example.com', 'dragan',
       'e51daae315c9e141f654be26af17094b8d5e3ac920c20041592c893544b4c156406a7f7e120414f5d04cbee278a9099a',
       'MALE', '0887777444', 'CN765432109'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'dragan');

-- Свързване на потребителя Dragan Draganov с ролята CLIENT
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'dragan'),
    (SELECT id FROM roles WHERE role = 'CLIENT')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'dragan')
      AND role_id = (SELECT id FROM roles WHERE role = 'CLIENT')
);






-- нов потребител Georgi
-- Вмъкване на нов потребител с роля на клиент --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '5432109876', 'Georgi Georgiev', 'CD5432109', 'georgi.georgiev@example.com', 'georgi',
       'e51daae315c9e141f654be26af17094b8d5e3ac920c20041592c893544b4c156406a7f7e120414f5d04cbee278a9099a',
       'MALE', '0887799666', 'CN543210987'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'georgi');

-- Свързване на потребителя Georgi Georgiev с ролята CLIENT
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'georgi'),
    (SELECT id FROM roles WHERE role = 'CLIENT')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'georgi')
      AND role_id = (SELECT id FROM roles WHERE role = 'CLIENT')
);

-- нов потребител Hristo
-- Вмъкване на нов потребител с роля на клиент --pass Ivan741@
INSERT INTO users (ssn, full_name, id_card_number, email, username, password, gender, phone_number, client_number)
SELECT '4321098765', 'Hristo Hristov', 'CD4321098', 'hristo.hristov@example.com', 'hristo',
       'e51daae315c9e141f654be26af17094b8d5e3ac920c20041592c893544b4c156406a7f7e120414f5d04cbee278a9099a',
       'MALE', '0887700111', 'CN432109876'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'hristo');

-- Свързване на потребителя Hristo Hristov с ролята CLIENT
INSERT INTO users_roles (user_id, role_id)
SELECT
    (SELECT id FROM users WHERE username = 'hristo'),
    (SELECT id FROM roles WHERE role = 'CLIENT')
WHERE NOT EXISTS (
    SELECT 1 FROM users_roles
    WHERE user_id = (SELECT id FROM users WHERE username = 'hristo')
      AND role_id = (SELECT id FROM roles WHERE role = 'CLIENT')
);

