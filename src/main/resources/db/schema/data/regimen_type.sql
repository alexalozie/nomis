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
-- Data for Name: regimen_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.regimen_type (id, description) VALUES (1, 'ART First Line Adult');
INSERT INTO public.regimen_type (id, description) VALUES (2, 'ART Second Line Adult');
INSERT INTO public.regimen_type (id, description) VALUES (3, 'ART First Line Children');
INSERT INTO public.regimen_type (id, description) VALUES (4, 'ART Second Line Children');
INSERT INTO public.regimen_type (id, description) VALUES (5, 'ARV Prophylaxis for Pregnant Women');
INSERT INTO public.regimen_type (id, description) VALUES (6, 'ARV Prophylaxis for Infants');
INSERT INTO public.regimen_type (id, description) VALUES (7, 'Post Exposure Prophylaxis (PEP)');
INSERT INTO public.regimen_type (id, description) VALUES (8, 'Cotrimoxazole (CTX) Prophylaxis');
INSERT INTO public.regimen_type (id, description) VALUES (9, 'OI Treatment');
INSERT INTO public.regimen_type (id, description) VALUES (10, 'TB Treatment Adult');
INSERT INTO public.regimen_type (id, description) VALUES (11, 'TB Treatment Children');
INSERT INTO public.regimen_type (id, description) VALUES (12, 'Other anti-infectives (including STI Medicine)');
INSERT INTO public.regimen_type (id, description) VALUES (13, 'Other Medicines');
INSERT INTO public.regimen_type (id, description) VALUES (14, 'Third Line');
INSERT INTO public.regimen_type (id, description) VALUES (15, 'Isoniazid Preventive Therapy (IPT)');
INSERT INTO public.regimen_type (id, description) VALUES (30, 'Pre-Exposure Prophylaxis (PrEP)');


