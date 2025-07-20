--
-- PostgreSQL database dump
--

-- Dumped from database version 14.3
-- Dumped by pg_dump version 14.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: domain; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.domain (id, name, code, created_by, date_created, date_modified, modified_by, archived, uid) VALUES (1, 'HEALTHY', 'd883bdd9-f5b0-4a9d-af31-64c476bd7c4b', 'guest@nomis-ng.org', '2021-06-15 16:05:59.625', '2021-06-15 16:05:59.625', 'guest@nomis-ng.org', 0, '44a5e47a-8fab-41bf-8f30-32e18729aced');
INSERT INTO public.domain (id, name, code, created_by, date_created, date_modified, modified_by, archived, uid) VALUES (3, 'SCHOOLED', '0da517b0-ff47-411d-aa98-a8bded2cf5a0', 'guest@nomisng.org', '2021-06-22 14:14:31.617', '2021-06-22 14:14:31.617', 'guest@nomisng.org', 0, 'e042036d-1085-4be3-90f8-17db715a807b');
INSERT INTO public.domain (id, name, code, created_by, date_created, date_modified, modified_by, archived, uid) VALUES (2, 'SAFE', 'f8f2a6bd-3ab6-4b74-95ee-65324b261d4a', 'guest@nomisng.org', '2021-06-22 14:14:00.974', '2021-06-22 14:14:00.974', 'guest@nomisng.org', 0, 'a3386fd5-e0a9-4c62-93c1-b93c093d6b55');
INSERT INTO public.domain (id, name, code, created_by, date_created, date_modified, modified_by, archived, uid) VALUES (4, 'STABLE', '7dec108c-3b23-4066-9468-507e0182cc02', 'guest@nomisng.org', '2021-06-22 14:14:31.617', '2021-06-22 14:14:31.617', 'guest@nomisng.org', 0, 'c12a36fc-7324-414a-8869-a8b8e257a8bd');


--
-- PostgreSQL database dump complete
--

