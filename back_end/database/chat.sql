-- SQL dump generated using DBML (dbml.dbdiagram.io)
-- Database: PostgreSQL
-- Generated at: 2025-09-02T04:58:05.229Z

CREATE TABLE "users" (
  "id" uuid PRIMARY KEY,
  "username" varchar(50) UNIQUE NOT NULL,
  "password_hash" varchar(255) NOT NULL,
  "last_login" timestamp,
  "created_at" timestamp NOT NULL DEFAULT (now())
);

CREATE TABLE "conversations" (
  "id" uuid PRIMARY KEY,
  "user1_id" uuid NOT NULL,
  "user2_id" uuid NOT NULL,
  "status" varchar(20) NOT NULL DEFAULT 'pending',
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "accepted_at" timestamp
);

CREATE TABLE "messages" (
  "id" uuid PRIMARY KEY,
  "conversation_id" uuid NOT NULL,
  "sender_id" uuid NOT NULL,
  "receiver_id" uuid NOT NULL,
  "content" text NOT NULL,
  "created_at" timestamp NOT NULL DEFAULT (now()),
  "is_read" boolean NOT NULL DEFAULT false
);

CREATE TABLE "conversation_requests" (
  "id" uuid PRIMARY KEY,
  "requester_id" uuid NOT NULL,
  "target_id" uuid NOT NULL,
  "status" varchar(20) NOT NULL DEFAULT 'pending',
  "created_at" timestamp NOT NULL DEFAULT (now())
);

COMMENT ON TABLE "users" IS 'Tabla que almacena la información principal de los usuarios.';

COMMENT ON COLUMN "users"."id" IS 'UUID único del usuario';

COMMENT ON COLUMN "users"."username" IS 'Nombre de usuario único';

COMMENT ON COLUMN "users"."password_hash" IS 'Contraseña encriptada con hash seguro';

COMMENT ON COLUMN "users"."last_login" IS 'Indica la fecha del ultimo ingreso';

COMMENT ON COLUMN "users"."created_at" IS 'Fecha y hora de creación del usuario';

COMMENT ON TABLE "conversations" IS 'Tabla que representa las conversaciones 1 a 1 entre usuarios.';

COMMENT ON COLUMN "conversations"."id" IS 'UUID único de la conversación';

COMMENT ON COLUMN "conversations"."user1_id" IS 'ID del primer usuario en la conversación';

COMMENT ON COLUMN "conversations"."user2_id" IS 'ID del segundo usuario en la conversación';

COMMENT ON COLUMN "conversations"."status" IS 'Estado de la conversación: active o blocked';

COMMENT ON COLUMN "conversations"."created_at" IS 'Fecha y hora de creación de la conversación';

COMMENT ON COLUMN "conversations"."accepted_at" IS 'Fecha y hora en que la conversación fue aceptada';

COMMENT ON TABLE "messages" IS 'Tabla que almacena los mensajes enviados entre usuarios.';

COMMENT ON COLUMN "messages"."id" IS 'UUID único del mensaje';

COMMENT ON COLUMN "messages"."conversation_id" IS 'ID de la conversación a la que pertenece el mensaje';

COMMENT ON COLUMN "messages"."sender_id" IS 'Usuario que envía el mensaje';

COMMENT ON COLUMN "messages"."receiver_id" IS 'Usuario que recibe el mensaje';

COMMENT ON COLUMN "messages"."content" IS 'Contenido del mensaje (encriptado)';

COMMENT ON COLUMN "messages"."created_at" IS 'Fecha y hora en que se envió el mensaje';

COMMENT ON COLUMN "messages"."is_read" IS 'Indica si el receptor ya leyó el mensaje';

COMMENT ON TABLE "conversation_requests" IS 'Tabla que registra las solicitudes de conversación entre usuarios.';

COMMENT ON COLUMN "conversation_requests"."id" IS 'UUID único de la solicitud';

COMMENT ON COLUMN "conversation_requests"."requester_id" IS 'Usuario que envía la solicitud de conversación';

COMMENT ON COLUMN "conversation_requests"."target_id" IS 'Usuario que recibe la solicitud';

COMMENT ON COLUMN "conversation_requests"."status" IS 'Estado de la solicitud: pending, received, accepted o rejected';

COMMENT ON COLUMN "conversation_requests"."created_at" IS 'Fecha y hora en que se creó la solicitud';

ALTER TABLE "conversations" ADD FOREIGN KEY ("user1_id") REFERENCES "users" ("id");

ALTER TABLE "conversations" ADD FOREIGN KEY ("user2_id") REFERENCES "users" ("id");

ALTER TABLE "messages" ADD FOREIGN KEY ("conversation_id") REFERENCES "conversations" ("id");

ALTER TABLE "messages" ADD FOREIGN KEY ("sender_id") REFERENCES "users" ("id");

ALTER TABLE "messages" ADD FOREIGN KEY ("receiver_id") REFERENCES "users" ("id");

ALTER TABLE "conversation_requests" ADD FOREIGN KEY ("requester_id") REFERENCES "users" ("id");

ALTER TABLE "conversation_requests" ADD FOREIGN KEY ("target_id") REFERENCES "users" ("id");
