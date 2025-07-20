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
-- Data for Name: application_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.application_user (id, user_name, password, first_name, last_name, other_names, email, phone_number, current_cbo_project_id, date_created, activation_key, date_reset, reset_key, time_uploaded, archived, created_by, date_modified, modified_by, gender) VALUES (1, 'guest@nomisng.org', '$2a$10$lwV6hLxSrZXm5gGioFKsjuvxIXvJ9vQrfzBhK.fD92qev0uo8lphK', 'guest', 'nomis', NULL, 'guest@nomisng.org', '08097654321', 27, '2021-06-07 23:38:26.575', NULL, NULL, NULL, NULL, 0, 'guest@nomis-ng.org', '2021-06-07 23:38:26.575', 'guest@nomis-ng.org', NULL);
INSERT INTO public.application_user (id, user_name, password, first_name, last_name, other_names, email, phone_number, current_cbo_project_id, date_created, activation_key, date_reset, reset_key, time_uploaded, archived, created_by, date_modified, modified_by, gender) VALUES (58, 'ademola', '$2a$10$84w1Eh6K3OmJEUZxdawizO7UCwiMj8b54K3zj0OWv/F0E25yVwYvC', 'Ademola', 'Samson', NULL, 'ademola@sample.com', NULL, 27, '2022-06-21 22:16:27.924374', NULL, NULL, NULL, NULL, 0, 'guest@nomisng.org', '2022-06-21 22:16:27.924374', 'guest@nomisng.org', '');
INSERT INTO public.application_user (id, user_name, password, first_name, last_name, other_names, email, phone_number, current_cbo_project_id, date_created, activation_key, date_reset, reset_key, time_uploaded, archived, created_by, date_modified, modified_by, gender) VALUES (59, 'awal', '$2a$10$KLk0xydltgfqaRt18E1jQuRovUVlxz9OlvDzsueBJy7G68HfVnZd6', 'Awal', 'Muhammad', NULL, 'awal@example.com', NULL, 27, '2022-06-23 07:21:18.065', NULL, NULL, NULL, NULL, 0, 'guest@nomisng.org', '2022-06-23 07:21:18.065', 'guest@nomisng.org', '');


--
-- PostgreSQL database dump complete
--

