-- Database: mentoring_program

-- DROP DATABASE mentoring_program;

CREATE DATABASE mentoring_program
  WITH OWNER = postgres
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'English_United States.1252'
       LC_CTYPE = 'English_United States.1252'
       CONNECTION LIMIT = -1;

-- Sequence: public.nh_news_id_seq

-- DROP SEQUENCE public.nh_news_id_seq;

CREATE SEQUENCE public.nh_news_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 17
  CACHE 1;
ALTER TABLE public.nh_news_id_seq
  OWNER TO postgres;

-- Sequence: public.nh_authors_id_seq

-- DROP SEQUENCE public.nh_authors_id_seq;

CREATE SEQUENCE public.nh_authors_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.nh_authors_id_seq
  OWNER TO postgres;

-- Sequence: public.nh_comments_id_seq

-- DROP SEQUENCE public.nh_comments_id_seq;

CREATE SEQUENCE public.nh_comments_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.nh_comments_id_seq
  OWNER TO postgres;

-- Sequence: public.nh_tags_id_seq

-- DROP SEQUENCE public.nh_tags_id_seq;

CREATE SEQUENCE public.nh_tags_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.nh_tags_id_seq
  OWNER TO postgres;

-- Table: public.nh_news

-- DROP TABLE public.nh_news;

CREATE TABLE public.nh_news
(
  id integer NOT NULL DEFAULT nextval('nh_news_id_seq'::regclass),
  title character varying(100) NOT NULL,
  brief_content text NOT NULL,
  full_content text NOT NULL,
  creation_time timestamp without time zone NOT NULL DEFAULT now(),
  last_modification_time timestamp without time zone,
  CONSTRAINT nh_news_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_news
  OWNER TO postgres;

-- Index: public.nh_news_idx1

-- DROP INDEX public.nh_news_idx1;

CREATE INDEX nh_news_idx1
  ON public.nh_news
  USING btree
  (creation_time DESC);

-- Table: public.nh_authors

-- DROP TABLE public.nh_authors;

CREATE TABLE public.nh_authors
(
  id integer NOT NULL DEFAULT nextval('nh_authors_id_seq'::regclass),
  name character varying(50) NOT NULL,
  CONSTRAINT nh_authors_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_authors
  OWNER TO postgres;

-- Table: public.nh_comments

-- DROP TABLE public.nh_comments;

CREATE TABLE public.nh_comments
(
  id integer NOT NULL DEFAULT nextval('nh_comments_id_seq'::regclass),
  content text NOT NULL,
  creation_time timestamp(0) without time zone NOT NULL DEFAULT now(),
  news_id integer NOT NULL,
  CONSTRAINT nh_comments_pk PRIMARY KEY (id),
  CONSTRAINT nh_comments_fk1 FOREIGN KEY (news_id)
      REFERENCES public.nh_news (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_comments
  OWNER TO postgres;

-- Table: public.nh_tags

-- DROP TABLE public.nh_tags;

CREATE TABLE public.nh_tags
(
  id integer NOT NULL DEFAULT nextval('nh_tags_id_seq'::regclass),
  name character varying(30) NOT NULL,
  CONSTRAINT nh_tags_pk PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_tags
  OWNER TO postgres;

-- Table: public.nh_news_authors

-- DROP TABLE public.nh_news_authors;

CREATE TABLE public.nh_news_authors
(
  news_id integer NOT NULL,
  author_id integer NOT NULL,
  CONSTRAINT nh_news_authors_pk PRIMARY KEY (news_id, author_id),
  CONSTRAINT nh_news_authors_fk1 FOREIGN KEY (news_id)
      REFERENCES public.nh_news (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT nh_news_authors_fk2 FOREIGN KEY (author_id)
      REFERENCES public.nh_authors (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_news_authors
  OWNER TO postgres;

-- Table: public.nh_news_tags

-- DROP TABLE public.nh_news_tags;

CREATE TABLE public.nh_news_tags
(
  news_id integer NOT NULL,
  tag_id integer NOT NULL,
  CONSTRAINT nh_news_tags_pk PRIMARY KEY (news_id, tag_id),
  CONSTRAINT nh_news_tags_fk1 FOREIGN KEY (news_id)
      REFERENCES public.nh_news (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT nh_news_tags_fk2 FOREIGN KEY (tag_id)
      REFERENCES public.nh_tags (id) MATCH SIMPLE
      ON UPDATE CASCADE ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.nh_news_tags
  OWNER TO postgres;

