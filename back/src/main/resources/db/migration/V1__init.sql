CREATE TABLE IF NOT EXISTS `raffles`
(
    id              bigint          NOT NULL    PRIMARY KEY,
    ca              varchar(66)     NOT NULL,
    token_id        int             NOT NULL,
    seller          varchar(66)     NOT NULL,
    winner          varchar(66),
    end_time        dateTime        NOT NULL,
    is_end          boolean         NOT NULL    DEFAULT false,
    is_paid         boolean         NOT NULL    DEFAULT false,
    settlement      bigint          NULL,
    created_at      dateTime        NOT NULL    DEFAULT now(),
    updated_at      dateTime        NOT NULL    DEFAULT now()
);

CREATE TABLE IF NOT EXISTS `tickets`
(
    id              bigint          NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    raffle_id       bigint          NOT NULL,
    price           double          NOT NULL,
    owner           varchar(66)     NOT NULL,
    token_uri       varchar(255)    NOT NULL,
    created_at      dateTime        NOT NULL    DEFAULT now(),
    updated_at      dateTime        NOT NULL    DEFAULT now(),

    CONSTRAINT tickets_raffles_id_fk
    FOREIGN KEY (raffle_id)
    REFERENCES raffles (id) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS `collections`
(
    id                  bigint          NOT NULL    AUTO_INCREMENT  PRIMARY KEY,
    contract_address    varchar(66)     NOT NULL,
    contract_name       varchar(100)    NOT NULL,
    contract_owner      varchar(66)     NOT NULL,
    opensea_slug        varchar(255)    NOT NULL,
    creator_fee         double          NOT NULL,
    type                varchar(255)    NOT NULL,
    description         varchar(255)    NOT NULL,
    link_twitter        varchar(255)    NOT NULL,
    link_discord        varchar(255)    NOT NULL,
    link_website        varchar(255)    NOT NULL,
    link_klayscope      varchar(255)    NOT NULL,
    created_at          dateTime        NOT NULL    DEFAULT now(),
    updated_at          dateTime        NOT NULL    DEFAULT now()
);