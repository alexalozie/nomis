--
-- PostgreSQL database dump
--

-- Dumped from database version 12.9
-- Dumped by pg_dump version 12.9

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
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (1, '2020-11-23 12:23:39.169', '2020-11-26 09:32:54.243', 'User', 'guest@nomisng.org', 'guest@nomisng.org', 'e48c613a-439e-11ec-81d3-0242ac130003');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (2, '2020-11-23 12:23:39.169', '2020-11-23 12:23:39.169', 'System Administrator', 'guest@nomisng.org', 'guest@nomisng.org', '3b063f04-439f-11ec-81d3-0242ac130003');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (3, '2020-11-23 12:23:39.169', '2020-11-23 12:23:39.169', 'DEC', 'guest@nomisng.org', 'guest@nomisng.org', '7dc5feb0-29d1-4d89-8039-2f9aeb3883ba');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (4, '2020-11-23 12:23:39.169', '2020-11-23 12:23:39.169', 'Community case work', 'guest@nomisng.org', 'guest@nomisng.org', '65ba2494-4792-11ec-81d3-0242ac130003');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (5, '2021-12-24 08:35:44.693', '2021-12-24 08:35:44.693', 'M&E Officer', 'guest@nomisng.org', 'guest@nomisng.org', 'c55ae769-33dc-4eda-b2a2-0461b6a4550f');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (58, '2022-06-23 07:10:45.373', '2022-06-23 07:10:45.373', 'Reviewer', 'guest@nomisng.org', 'guest@nomisng.org', 'd3c704dc-c352-400b-b30a-35dcc9cfacbd');
INSERT INTO public.role (id, date_created, date_modified, name, created_by, modified_by, code) VALUES (59, '2022-06-23 07:20:18.248', '2022-06-23 07:20:18.248', 'Assessing Officer', 'guest@nomisng.org', 'guest@nomisng.org', 'f2053fa0-7ac5-44b1-a600-0cd978fef594');


--
-- PostgreSQL database dump complete
--

