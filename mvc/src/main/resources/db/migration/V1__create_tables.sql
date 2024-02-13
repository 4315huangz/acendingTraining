CREATE TABLE departments (
                             id          BIGSERIAL NOT NULL,
                             name        VARCHAR(30) not null,
                             description VARCHAR(150),
                             location    VARCHAR(100)
);
ALTER TABLE departments ADD CONSTRAINT Department_pk PRIMARY KEY (id);

CREATE TABLE employees (
                           id              BIGSERIAL NOT NULL,
                           name            VARCHAR(30) not null,
                           first_name      VARCHAR(30),
                           last_name       VARCHAR(30),
                           email           VARCHAR(50),
                           address         VARCHAR(150),
                           hired_date      date default CURRENT_DATE,
                           department_id   BIGINT NOT NULL
);
ALTER TABLE employees ADD CONSTRAINT employee_pk PRIMARY KEY ( id );

CREATE TABLE accounts (
                          id             BIGSERIAL NOT NULL,
                          account_type   VARCHAR(30),
                          balance        NUMERIC(10, 2),
                          create_date    date default CURRENT_DATE,
                          employee_id    BIGINT NOT NULL
);
ALTER TABLE accounts ADD CONSTRAINT account_pk PRIMARY KEY ( id );

ALTER TABLE accounts
    ADD CONSTRAINT account_employee_fk FOREIGN KEY ( employee_id )
        REFERENCES employees ( id );

ALTER TABLE employees
    ADD CONSTRAINT employee_department_fk FOREIGN KEY ( department_id )
        REFERENCES departments ( id );