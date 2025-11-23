-- Esquema base según tus entidades actuales (IDs String, price INT)

CREATE TABLE IF NOT EXISTS users (
  id          VARCHAR(36)  PRIMARY KEY,
  name        VARCHAR(120) NOT NULL,
  email       VARCHAR(160) NOT NULL UNIQUE,
  password    VARCHAR(255) NOT NULL,
  role        ENUM('ROLE_ADMIN','ROLE_USER') NOT NULL,
  created_at  TIMESTAMP NOT NULL,
  updated_at  TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
  id                    VARCHAR(32)  PRIMARY KEY,
  title                 VARCHAR(255) NOT NULL,
  author                VARCHAR(255) NOT NULL,
  category              VARCHAR(120) NOT NULL,
  price                 INT NOT NULL,
  description           TEXT,
  extended_description  TEXT,
  image                 VARCHAR(1024),
  created_at            TIMESTAMP NOT NULL,
  updated_at            TIMESTAMP NOT NULL
);
