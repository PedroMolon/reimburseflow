CREATE TABLE tb_roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE tb_users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_users_roles (
    user_id BIGSERIAL NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_users(id) ON DELETE CASCADE,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES tb_roles(id) ON DELETE CASCADE
);

INSERT INTO tb_roles (name) VALUES ('ROLE_EMPLOYEE') ON CONFLICT DO NOTHING;
INSERT INTO tb_roles (name) VALUES ('ROLE_MANAGER') ON CONFLICT DO NOTHING;
INSERT INTO tb_roles (name) VALUES ('ROLE_FINANCIAL') ON CONFLICT DO NOTHING;

CREATE TABLE tb_reimbursements (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    description TEXT NOT NULL,
    amount NUMERIC(10, 2) NOT NULL,
    category VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES tb_users(id)
);

CREATE TABLE tb_reimbursement_history (
    id BIGSERIAL PRIMARY KEY,
    reimbursement_id BIGINT NOT NULL,
    changed_by_user_id BIGINT NOT NULL,
    previous_status VARCHAR(50),
    new_status VARCHAR(50) NOT NULL,
    comment TEXT,
    changed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reimbursement FOREIGN KEY (reimbursement_id) REFERENCES tb_reimbursements(id),
    CONSTRAINT fk_user FOREIGN KEY (changed_by_user_id) REFERENCES tb_users(id)
);