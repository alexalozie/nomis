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
-- Data for Name: organisation_unit_level; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (1, 'Country', 'National level', 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (3, 'Province/LGA', 'Local government level', 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (6, 'Laboratory', 'Laboratory level', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (7, 'PCR Laboratory', 'PCR Laboratory level', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (2, 'State', 'State level', 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (4, 'Ward', 'Ward level', 0, 1, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (5, 'Facility', 'Facility level', 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO public.organisation_unit_level (id, name, description, archived, status, date_created, created_by, date_modified, modified_by, parent_organisation_unit_level_id) VALUES (8, 'Community', 'Community in an Province/LGA', 0, 1, NULL, NULL, NULL, NULL, NULL);


--
-- PostgreSQL database dump complete
--

