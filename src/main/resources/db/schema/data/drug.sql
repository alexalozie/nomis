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
-- Data for Name: drug; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (27, 'LPV/r', 'Lopinavir/Ritonavir', '200/50mg', 60, 'Capsules', 2, 0, 2, 27);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (28, 'IDV/r', 'Indinavir/Ritonavir', '400/100mg', 60, 'Capsules', 1, 1, 1, 28);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (29, 'ATV/r', 'Atazanavir/Ritonavir', '300/100mg', 60, 'Capsules', 1, 0, 0, 29);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (30, 'FTC', 'Emtricitabine', '200mg', 60, 'Capsules', 1, 0, 0, 30);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (31, 'TDF', 'Tenofovir', '300mg', 60, 'Capsules', 0, 0, 1, 31);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (32, 'TDF/FTC', 'Tenofovir/Emtricitabine', '300/200mg', 60, 'Capsules', 1, 0, 0, 32);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (33, 'd4t', 'Stavudine', '20mg', 60, 'Capsules', 1, 0, 1, 33);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (34, 'd4t', 'Stavudine', '6mg', 60, 'Capsules', 1, 0, 1, 34);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (35, 'd4t', 'Stavudine', '12mg', 60, 'Capsules', 1, 0, 1, 35);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (36, '3TC', 'Lamivudine', '30mg', 60, 'Tablets', 0, 0, 1, 36);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (37, '3TC', 'Lamivudine', '60mg', 60, 'Tablets', 0, 0, 1, 37);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (38, 'NVP', 'Nevirapine', '50mg', 60, 'Tablets', 1, 0, 1, 38);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (39, 'AZT', 'Zidovudine', '60mg', 60, 'Tablets', 1, 0, 1, 39);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (41, 'CTX', 'Cotrimoxazole', '960mg', 336, 'Tablets', 1, 0, 0, 41);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (42, 'CTX', 'Cotrimoxazole', '480mg', 240, 'Tablets', 2, 0, 0, 42);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (43, 'CTX', 'Cotrimoxazole', '240mg/5ml', 100, 'Suspension', 3, 0, 3, 43);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (44, 'ACY', 'Acyclovir', '200mg', 100, 'Tablets', 1, 0, 0, 44);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (45, 'AMOX/C', 'Amoxycillin/Clavulanate', '625mg', 100, 'Tablets', 1, 0, 0, 45);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (46, 'AMOX/C', 'Amoxycillin/Clavulanate', '457mg/5ml', 100, 'Syrup', 3, 0, 3, 46);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (47, 'FLU', 'Fluconazole', '50mg', 100, 'Tablets', 1, 0, 0, 47);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (48, 'FLU', 'Fluconazole', '50mg/5ml', 100, 'Suspension', 3, 0, 3, 48);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (49, 'FLU', 'Fluconazole', '200mg', 100, 'Tablets', 1, 0, 0, 49);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (50, 'NYS', 'Nystatin', '100,000iu/ml', 100, 'Suspension', 3, 0, 3, 50);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (51, 'TDZ', 'Tinidazole', '500mg', 100, 'Tablets', 1, 0, 0, 51);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (52, 'AZI', 'Azithromycin', '250mg', 100, 'Tablets', 1, 0, 0, 52);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (53, 'BPCN', 'Benzathine Penicillin', '', 100, 'Injectables', 1, 0, 0, 53);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (54, 'CFX', 'Ceftriaxone', '1g', 100, 'Injectables', 1, 0, 0, 54);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (55, 'CIPRO', 'Ciprofloxacin', '500mg', 100, 'Tablets', 1, 0, 0, 55);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (1, 'EFV', 'Efavirenz', '600mg', 30, 'Capsules', 1, 0, 1, 1);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (2, 'EFV', 'Efavirenz', '200mg', 90, 'Capsules', 1, 0, 1, 2);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (3, '3TC', 'Lamivudine', '150mg', 60, 'Tablets', 1, 0, 1, 3);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (4, '3TC', 'Lamivudine', '10mg/ml', 60, 'Suspension', 3, 0, 3, 4);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (5, '3TC', 'Lamivudine', '300mg', 60, 'Tablets', 0, 0, 1, 5);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (6, 'AZT/3TC', 'Zidovudine/Lamivudine', '300/150mg', 60, 'Tablets', 1, 0, 1, 6);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (7, 'd4T/3TC/EFV', 'Stavudine/Lamivudine/Efavirenz', '6/30/200mg', 60, 'Tablets', 1, 0, 1, 7);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (8, 'd4T/3TC/EFV', 'Stavudine/Lamivudine/Efavirenz', '12/60/200mg', 60, 'Tablets', 1, 0, 1, 8);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (9, 'd4T/3TC/NVP', 'Stavudine/Lamivudine/Nevirapine', '6/30/50mg', 60, 'Tablets', 1, 0, 1, 9);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (10, 'd4T/3TC/NVP', 'Stavudine/Lamivudine/Nevirapine', '12/60/100mg', 60, 'Tablets', 1, 0, 1, 10);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (11, 'AZT/3TC/NVP', 'Zidovudine/Lamivudine/Nevirapine', '60/30/50mg', 60, 'Tablets', 1, 0, 1, 11);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (12, 'AZT/3TC/EFV', 'Zidovudine/Lamivudine/Efavirenz', '60/30/200mg', 60, 'Tablets', 1, 0, 1, 12);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (13, 'NVP', 'Nevirapine', '10mg/ml', 60, 'Suspension', 3, 0, 3, 13);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (14, 'NVP', 'Nevirapine', '200mg', 60, 'Tablets', 1, 0, 1, 14);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (15, 'd4t', 'Stavudine', '30mg', 60, 'Capsules', 1, 0, 1, 15);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (16, 'd4t', 'Stavudine', '1mg/ml', 60, 'Suspension', 3, 0, 3, 16);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (17, 'AZT', 'Zidovudine', '100mg', 60, 'Capsules', 1, 0, 1, 17);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (18, 'AZT', 'Zidovudine', '10mg/ml', 60, 'Syrup', 3, 0, 3, 18);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (19, 'AZT', 'Zidovudine', '300mg', 60, 'Tablets', 1, 0, 1, 19);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (20, 'AZT', 'Zidovudine', '60mg', 60, 'Tablets', 1, 0, 1, 20);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (21, 'DDI', 'Didanosine', '10mg/ml', 60, 'Suspension', 3, 0, 3, 21);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (22, 'DDI', 'Didanosine', '400mg', 60, 'Tablets', 1, 0, 1, 22);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (23, 'ABC', 'Abacavir', '300mg', 60, 'Tablets', 1, 0, 1, 23);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (24, 'ABC', 'Abacavir', '20mg/ml', 60, 'Oral Solution', 3, 0, 3, 24);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (25, 'ABC', 'Abacavir', '60mg', 60, 'Tablets', 1, 0, 1, 25);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (26, 'LPV/r', 'Lopinavir/Ritonavir', '80/20mg/ml', 60, 'Oral Solution', 3, 0, 3, 26);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (56, 'DOXY', 'Doxycycline', '100mg', 100, 'Capsules', 1, 0, 0, 56);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (57, 'ERY', 'Erythromycin', '500mg', 100, 'Tablets', 1, 0, 0, 57);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (58, 'OFLOX', 'Ofloxacin', '200mg', 100, 'Tablets', 1, 0, 0, 58);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (59, 'SPEC', 'Spectinomycine', '2g', 100, 'Injectables', 1, 0, 0, 59);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (60, 'TCN', 'Tetracycline', '500mg', 100, 'Capsules', 1, 0, 0, 60);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (61, 'ATP', 'Amitryptiline', '25mg', 100, 'Tablets', 1, 0, 0, 61);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (62, 'ART/LUM', 'Arthemeter/Lumefantrine', '20/120mg', 100, 'Tablets', 1, 0, 0, 62);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (63, 'FOLIC', 'Folic Acid', '5mg', 100, 'Tablets', 1, 0, 0, 63);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (64, 'FEROS', 'Ferrous Sulphate', '300mg', 100, 'Tablets', 1, 0, 0, 64);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (65, 'FEROC', 'Ferrous Gluconate', '300mg', 100, 'Tablets', 1, 0, 0, 65);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (66, 'MVT', 'Multivitamin', '', 100, 'Tablets', 1, 0, 0, 66);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (67, 'MVT', 'Multivitamin', '', 100, 'Syrup', 3, 0, 0, 67);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (68, 'IBU', 'Ibuprofen', '200mg', 100, 'Tablets', 1, 0, 0, 68);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (69, 'LOP', 'Loperamide', '2mg', 100, 'Tablets', 1, 0, 0, 69);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (70, 'PROMET', 'Promethezine HCL', '25mg', 100, 'Tablets', 1, 0, 0, 70);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (71, 'TRAM', 'Tramadol HCL', '50mg', 100, 'Capsules', 1, 0, 0, 71);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (72, 'ORS', 'Oral Dehydration Solution', '', 100, 'Oral Solution', 3, 0, 3, 72);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (73, 'FLUX', 'Fluoxetine', '20mg', 100, 'Capsules', 1, 0, 0, 73);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (74, 'LOR', 'Loratidine', '10mg', 100, 'Tablets', 1, 0, 0, 74);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (75, 'METO', 'Metoclopramide', '10mg', 100, 'Tablets', 1, 0, 0, 75);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (76, 'BSA', 'Benzoic Acid/Salicylic Acid Ointment', '6/3%', 100, 'Creams/Ointment', 1, 0, 0, 76);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (77, 'HYC', 'Hydrocortine', '1%', 100, 'Creams/Ointment', 1, 0, 0, 77);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (78, 'CHLO', 'Chlorpheniramine', '4mg', 100, 'Tablets', 1, 0, 0, 78);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (79, 'SUPY', 'Sulphadoxin/Pyrimethamine', '500/25mg', 100, 'Tablets', 1, 0, 0, 79);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (80, 'IBU', 'Ibuprofen', '400mg', 100, 'Tablets', 1, 0, 1, 80);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (81, 'NYS', 'Nystatin', '50000iu', 100, 'Tablets', 0, 0, 1, 81);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (82, 'ZIT', 'Zithromax', '500mg', 100, 'Tablets', 1, 0, 0, 82);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (83, 'MET', 'Metronidazole', '400mg', 100, 'Tablets', 1, 0, 0, 83);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (84, 'R', 'Rifampicin', '150mg', 100, 'Tablets', 1, 0, 0, 84);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (85, 'R', 'Rifampicin', '60mg', 28, 'Tablets', 1, 0, 0, 85);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (86, 'H', 'Isoniazid', '75mg', 28, 'Tablets', 1, 0, 0, 86);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (87, 'H', 'Isoniazid', '100mg', 28, 'Tablets', 1, 0, 0, 87);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (88, 'E', 'Ethambuthol', '275mg', 28, 'Tablets', 1, 0, 0, 88);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (89, 'E', 'Ethambuthol', '400mg', 28, 'Tablets', 1, 0, 0, 89);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (90, 'Z', 'Pyrazinamide', '400mg', 28, 'Tablets', 1, 0, 0, 90);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (91, 'Z', 'Pyrazinamide', '150mg', 28, 'Tablets', 1, 0, 0, 91);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (92, 'S', 'Streptomycin', '1g', 28, 'Injectables', 1, 0, 0, 92);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (93, 'EH', 'Ethambuthol/Isoniazid', '400/150mg', 28, 'Tablets', 1, 0, 0, 93);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (94, 'RH', 'Rifampicin/Isoniazid', '150/75mg', 28, 'Tablets', 1, 0, 0, 94);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (95, 'RH', 'Rifampicin/Isoniazid', '60/30mg', 28, 'Tablets', 1, 0, 0, 95);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (96, 'RHZ', 'Rifampicin/Isoniazid/Pyrazinamide', '60/30/150mg', 28, 'Tablets', 1, 0, 0, 96);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (97, 'RHZE', 'Rifampicin/Isoniazid/Pyrazinamide/Ethambuthol', '150/75/400/275mg', 28, 'Tablets', 1, 0, 0, 97);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (98, 'AZT/3TC/NVP', 'Zidovudine/Lamivudine/Nevirapine', '300/150/200mg', 60, 'Tablets', 1, 0, 1, 98);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (99, 'H', 'Isoniazid', '300mg', 24, 'Tablets', 1, 0, 0, 99);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (100, 'DTG', 'Dolutegravir', '50mg', 24, 'Tablets', 1, 0, 0, 100);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (101, 'LPV/r', 'Lopinavir/Ritonavir', '40/10mg', 24, 'Pellet', 1, 0, 0, 101);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (102, 'ABC', 'Abacavir', '600mg', 60, 'Tablets', 1, 0, 0, 102);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (110, 'LPV/r', 'Abacavir', '120mg', 60, 'Tablets', 1, 0, 1, 110);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (111, 'ABC', 'Lopinavir/Ritonavir', '100/25mg', 60, 'Capsules', 1, 0, 1, 111);
INSERT INTO public.drug (id, abbrev, name, strength, pack_size, doseform, morning, afternoon, evening, item_id) VALUES (112, 'Hian', 'Pyridoxine(25mg)', '25mg', 60, 'Tablets', 1, 0, 0, 112);
