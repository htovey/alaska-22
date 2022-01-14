--<ScriptOptions statementTerminator=";"/>

ALTER TABLE order DROP PRIMARY KEY;

ALTER TABLE item DROP PRIMARY KEY;

ALTER TABLE person DROP PRIMARY KEY;

ALTER TABLE user DROP PRIMARY KEY;

ALTER TABLE person DROP INDEX user_name_UNIQUE;

ALTER TABLE person DROP INDEX user_name_UNIQUE;

ALTER TABLE user DROP INDEX username_UNIQUE;

ALTER TABLE person DROP INDEX fk_username_idx;

ALTER TABLE item DROP INDEX id_UNIQUE;

ALTER TABLE user DROP INDEX username_UNIQUE;

ALTER TABLE person DROP INDEX fk_username_idx;

DROP TABLE order;

DROP TABLE item;

DROP TABLE user;

DROP TABLE person;

CREATE TABLE order (
	user_id INT NOT NULL,
	item_id VARCHAR(45),
	id VARCHAR(45) NOT NULL,
	PRIMARY KEY (id)
);

CREATE TABLE item (
	id INT NOT NULL,
	category VARCHAR(45) NOT NULL,
	name VARCHAR(45) NOT NULL,
	order_no INT,
	create_dt DATE,
	save_dt DATE,
	PRIMARY KEY (id)
);

CREATE TABLE user (
	password VARCHAR(200) NOT NULL,
	role_id INT NOT NULL,
	user_name VARCHAR(45) NOT NULL,
	create_dt DATETIME,
	save_dt DATETIME,
	id INT NOT NULL,
	admin_id INT,
	biz_id INT NOT NULL,
	group_id INT,
	password VARCHAR(200) NOT NULL,
	user_name VARCHAR(45) NOT NULL,
	create_dt DATETIME,
	save_dt DATETIME,
	id INT NOT NULL,
	Host CHAR(255) NOT NULL,
	User CHAR(32) NOT NULL,
	Select_priv ENUM DEFAULT N NOT NULL,
	Insert_priv ENUM DEFAULT N NOT NULL,
	Update_priv ENUM DEFAULT N NOT NULL,
	Delete_priv ENUM DEFAULT N NOT NULL,
	Create_priv ENUM DEFAULT N NOT NULL,
	Drop_priv ENUM DEFAULT N NOT NULL,
	Reload_priv ENUM DEFAULT N NOT NULL,
	Shutdown_priv ENUM DEFAULT N NOT NULL,
	Process_priv ENUM DEFAULT N NOT NULL,
	File_priv ENUM DEFAULT N NOT NULL,
	Grant_priv ENUM DEFAULT N NOT NULL,
	References_priv ENUM DEFAULT N NOT NULL,
	Index_priv ENUM DEFAULT N NOT NULL,
	Alter_priv ENUM DEFAULT N NOT NULL,
	Show_db_priv ENUM DEFAULT N NOT NULL,
	Super_priv ENUM DEFAULT N NOT NULL,
	Create_tmp_table_priv ENUM DEFAULT N NOT NULL,
	Lock_tables_priv ENUM DEFAULT N NOT NULL,
	Execute_priv ENUM DEFAULT N NOT NULL,
	Repl_slave_priv ENUM DEFAULT N NOT NULL,
	Repl_client_priv ENUM DEFAULT N NOT NULL,
	Create_view_priv ENUM DEFAULT N NOT NULL,
	Show_view_priv ENUM DEFAULT N NOT NULL,
	Create_routine_priv ENUM DEFAULT N NOT NULL,
	Alter_routine_priv ENUM DEFAULT N NOT NULL,
	Create_user_priv ENUM DEFAULT N NOT NULL,
	Event_priv ENUM DEFAULT N NOT NULL,
	Trigger_priv ENUM DEFAULT N NOT NULL,
	Create_tablespace_priv ENUM DEFAULT N NOT NULL,
	ssl_type ENUM NOT NULL,
	ssl_cipher BLOB NOT NULL,
	x509_issuer BLOB NOT NULL,
	x509_subject BLOB NOT NULL,
	max_questions INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	max_updates INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	max_connections INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	max_user_connections INTEGER UNSIGNED DEFAULT 0 NOT NULL,
	plugin CHAR(64) DEFAULT caching_sha2_password NOT NULL,
	authentication_string TEXT,
	password_expired ENUM DEFAULT N NOT NULL,
	password_last_changed TIMESTAMP,
	password_lifetime SMALLINT UNSIGNED,
	account_locked ENUM DEFAULT N NOT NULL,
	Create_role_priv ENUM DEFAULT N NOT NULL,
	Drop_role_priv ENUM DEFAULT N NOT NULL,
	Password_reuse_history SMALLINT UNSIGNED,
	Password_reuse_time SMALLINT UNSIGNED,
	Password_require_current ENUM,
	User_attributes null,
	PRIMARY KEY (Host,User)
);

CREATE TABLE person (
	id INT NOT NULL,
	user_name VARCHAR(45) NOT NULL,
	fname VARCHAR(45) NOT NULL,
	lname VARCHAR(45) NOT NULL,
	save_dt DATETIME NOT NULL,
	id INT NOT NULL,
	user_name VARCHAR(45) NOT NULL,
	fname VARCHAR(45) NOT NULL,
	lname VARCHAR(45) NOT NULL,
	phone VARCHAR(45) NOT NULL,
	address_id VARCHAR(45) NOT NULL,
	note VARCHAR(500) NOT NULL,
	save_dt DATETIME NOT NULL,
	PRIMARY KEY (id)
);

CREATE UNIQUE INDEX user_name_UNIQUE ON person (user_name ASC);

CREATE UNIQUE INDEX user_name_UNIQUE ON person (user_name ASC);

CREATE UNIQUE INDEX username_UNIQUE ON user (user_name ASC);

CREATE INDEX fk_username_idx ON person (user_name ASC);

CREATE UNIQUE INDEX id_UNIQUE ON item (id ASC);

CREATE UNIQUE INDEX username_UNIQUE ON user (user_name ASC);

CREATE INDEX fk_username_idx ON person (user_name ASC);

