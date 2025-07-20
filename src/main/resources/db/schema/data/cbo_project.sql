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
-- Data for Name: cbo_project; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cbo_project (id, cbo_id, donor_id, implementer_id, archived, description) OVERRIDING SYSTEM VALUE VALUES (27, 3, 1, 1, 0, 'Child Care International-ICHSSA Project');
INSERT INTO public.cbo_project (id, cbo_id, donor_id, implementer_id, archived, description) OVERRIDING SYSTEM VALUE VALUES (29, 1, 1, 3, 0, 'CHAMAGNE ICHSSA 2 PROJECT');
INSERT INTO public.cbo_project (id, cbo_id, donor_id, implementer_id, archived, description) OVERRIDING SYSTEM VALUE VALUES (31, 2, 2, 4, 0, '4GATES PROJECT');

