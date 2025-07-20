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
-- Data for Name: donor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.donor (id, name, description, code, created_by, date_created, modified_by, date_modified, archived) OVERRIDING SYSTEM VALUE VALUES (1, 'USAID', 'International Donor', 'USD', 'guest@nomisng.org', '2021-11-08 13:38:57.671', 'guest@nomisng.org', '2021-11-08 13:38:57.671', 0);
INSERT INTO public.donor (id, name, description, code, created_by, date_created, modified_by, date_modified, archived) OVERRIDING SYSTEM VALUE VALUES (2, 'CDC', 'International Donor', 'CDC', 'guest@nomisng.org', '2021-11-08 13:39:49.126', 'guest@nomisng.org', '2021-11-08 13:39:49.126', 0);
