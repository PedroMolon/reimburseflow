INSERT INTO tb_roles (name) VALUES ('ROLE_EMPLOYEE') ON CONFLICT (name) DO NOTHING;

INSERT INTO tb_users (id, name, email, password)
VALUES (
           1,
           'Test Employee',
           'employee@reimburseflow.com',
        '$2a$06$BP4Jh5buSWKLzMTkbYWfeON39ZQS//VraQcXHexBrIiTypbl.Dd5u'
       )
ON CONFLICT (email) DO NOTHING;

INSERT INTO tb_users_roles (user_id, role_id)
SELECT 1, id FROM tb_roles WHERE name = 'ROLE_EMPLOYEE'
ON CONFLICT DO NOTHING;