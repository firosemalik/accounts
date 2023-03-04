CREATE TABLE ACCOUNT_LIST
(
    ACCOUNT_ID        SERIAL  PRIMARY KEY,
    CUSTOMER_ID       INTEGER              NOT NULL,
    ACCOUNT_NUMBER    VARCHAR(20)          NOT NULL,
    ACCOUNT_NAME      VARCHAR(64)          NOT NULL,
    ACCOUNT_TYPE      VARCHAR(20)          NOT NULL,
    BALANCED_TIME     TIMESTAMPTZ          NOT NULL,
    CURRENCY          VARCHAR(5)           NOT NULL,
    AVAILABLE_BALANCE NUMERIC              NOT NULL
);

CREATE TABLE ACCOUNT_TRANSACTION
(
    TRANSACTION_ID              SERIAL  PRIMARY KEY,
    ACCOUNT_ID                  INTEGER,
    VALUE_DATE                  TIMESTAMPTZ          NOT NULL,
    CURRENCY                    VARCHAR(5)           NOT NULL,
    CREDIT_AMOUNT               NUMERIC,
    DEBIT_AMOUNT                NUMERIC,
    TRANSACTION_TYPE            VARCHAR(10)          NOT NULL,
    TRANSACTION_NARRATIVE       VARCHAR(64),
    CONSTRAINT FK_ACCOUNT_ID FOREIGN KEY (ACCOUNT_ID) REFERENCES ACCOUNT_LIST (ACCOUNT_ID)
);