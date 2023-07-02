DROP TABLE IF EXISTS user_balance;

CREATE TABLE  user_balance(
          user_id BIGINT PRIMARY KEY,
          balance DOUBLE NOT NULL
);
