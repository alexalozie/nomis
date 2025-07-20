CREATE OR REPLACE FUNCTION public.get_regimen(
	inputid bigint,
	regimen_line boolean,
	baseline boolean)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare record text;
BEGIN
select description into record from (
                                        select case when regimen_line = true then
                                                                f.data->'currentRegimen'->'regimenType'->>'description'
                                                    else f.data->'currentRegimen'->>'description' end description,
                                               case when baseline = true then row_number() over(order by e.date_created asc)
                                                    else row_number() over(order by e.date_created desc) end row_index
                                        from encounter e join form_data f on e.id = f.encounter_id
                                        where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723' and e.household_member_id = inputId
                                    ) tbl where row_index = 1;

RETURN record;
END;
$BODY$;
ALTER FUNCTION public.get_regimen(bigint, boolean, boolean)
    OWNER TO postgres;


CREATE OR REPLACE FUNCTION public.get3rd95(
	)
    RETURNS TABLE(cgeli bigint, vceli bigint, cgresult bigint, vcresult bigint, cgsuppressed bigint, vcsuppressed bigint, cgunsuppressed bigint, vcunsuppressed bigint)
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
    ROWS 1000

AS $BODY$
BEGIN
RETURN QUERY
    with cg_eligible as (
		SELECT count(*) as cg_eli from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and date_part('MONTH',current_date) - date_part('MONTH',to_date(fd.data->>'encounterDate', 'YYYY-m-d'))
		 >=2 and hm.household_member_type= 1
	),
	vc_eligible as (
		SELECT count(*) as vc_eli  from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and date_part('MONTH',current_date) - date_part('MONTH',to_date(fd.data->>'encounterDate', 'YYYY-m-d'))
		 >=2 and hm.household_member_type= 2
	),
	cg_res as (
		SELECT count(*) as cg_result  from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and hm.household_member_type= 1
	),
	vc_res as (
		SELECT count(*) as vc_result from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and hm.household_member_type= 2
	),
	cg_suppress as (
		SELECT count(*) as cg_suppressed  from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and nullif(fd.data->>'whatWasTheResult','')::int < 1000 and hm.household_member_type= 1
	),
	vc_suppress as (
		SELECT count(*) as vc_suppressed from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and nullif(fd.data->>'whatWasTheResult','')::int < 1000 and hm.household_member_type= 2
	),
	cg_unsuppress as (
		SELECT count(*) as cg_unsuppressed  from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and nullif(fd.data->>'whatWasTheResult','')::int >= 1000 and hm.household_member_type= 1
	),
	vc_unsuppress as (
		SELECT count(*) as vc_unsuppressed from encounter e
		join form_data fd on e.id = fd.encounter_id
		join household_member hm on hm.id = e.household_member_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723'
		and fd.data->'currentlyOnART'->>'code'='51dc4de7-8c88-4963-b255-331bf930b7c0'
		and nullif(fd.data->>'whatWasTheResult','')::int >= 1000 and hm.household_member_type= 2
	)
select cg_eli, vc_eli, cg_result, vc_result,cg_suppressed, vc_suppressed,
       cg_unsuppressed, vc_unsuppressed
from cg_eligible, vc_eligible, cg_res, vc_res, cg_suppress, vc_suppress,
     cg_unsuppress, vc_unsuppress;

END;
$BODY$;
ALTER FUNCTION public.get3rd95()
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.get_followup_status(
	inputid bigint,
	status_type text,
	is_date boolean)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare status text;
BEGIN
select u_status into status from (
                                     select
                                         CASE WHEN status_type = 'withdraw' then
                                                      case when f.data->'beneficiary_status'->>'code'='a695ea42-1bab-4a79-95bf-5ed05c576d99'
                                                      AND is_date = false then f.data->'beneficiary_status'->>'display'
		  		when f.data->'beneficiary_status'->>'code'='a695ea42-1bab-4a79-95bf-5ed05c576d99'
		  		AND is_date = true then f.data->>'date_status_update'
		  	else null end
		  WHEN status_type = 'death' then
		  	case when f.data->'beneficiary_status'->>'code'='8a7d1d0b-5b7a-4ce4-87ba-6a6562490f09'
		  		AND is_date = false then f.data->'beneficiary_status'->>'display'
		  		when f.data->'beneficiary_status'->>'code'='8a7d1d0b-5b7a-4ce4-87ba-6a6562490f09'
		  		AND is_date = true then f.data->>'date_status_update'
		  	else null end
		  WHEN status_type = 'migrated' then
		  	case when f.data->'beneficiary_status'->>'code'='a81739d6-0455-4ccb-a386-4addf7256228'
		  		AND is_date = false then f.data->'beneficiary_status'->>'display'
		  		when f.data->'beneficiary_status'->>'code'='a81739d6-0455-4ccb-a386-4addf7256228'
		  		AND is_date = true then f.data->>'date_status_update'
		  	else null end
		  WHEN status_type = 'lftu' then
		  	case when f.data->'beneficiary_status'->>'code'='d5c2ebd9-0391-420d-845a-2a7ec63c6151'
				AND is_date = false then f.data->'beneficiary_status'->>'display'
				when f.data->'beneficiary_status'->>'code'='d5c2ebd9-0391-420d-845a-2a7ec63c6151'
				AND is_date = true then f.data->>'date_status_update'
		  	else null end
		  WHEN status_type = 'graduate' then
		  	case when f.data->'beneficiary_status'->>'code'='9b39b8bd-8cd8-41cd-8bc7-1bb4491ab102'
				AND is_date = false then f.data->'beneficiary_status'->>'display'
				when f.data->'beneficiary_status'->>'code'='9b39b8bd-8cd8-41cd-8bc7-1bb4491ab102'
				AND is_date = true then f.data->>'date_status_update'
		  	else null end
		  ELSE null END u_status,
		  row_number() over(order by e.date_created desc) row_index
                                     from encounter e join form_data f on e.id = f.encounter_id
                                     where e.form_code = '6fb50a2e-e2d3-4534-a641-1abdb75c6fda' and e.household_member_id = inputId
                                 ) tbl where row_index = 1;

RETURN status;
END;
$BODY$;
ALTER FUNCTION public.get_followup_status(bigint, text, boolean)
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.isnumeric(
	text)
    RETURNS boolean
    LANGUAGE 'plpgsql'
    COST 100
    IMMUTABLE STRICT PARALLEL UNSAFE
AS $BODY$
   DECLARE x NUMERIC;   BEGIN       x = $1::NUMERIC;       RETURN TRUE;       EXCEPTION WHEN others THEN           RETURN FALSE;   END;
$BODY$;
ALTER FUNCTION public.isnumeric(text)
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.get_current_art_status(
	inputid bigint,
	end_date date)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare current_status text;
BEGIN
select art_status into current_status from (
                                               select CASE WHEN (((f.data->>'drugPickupDate')::date + (f.data->>'refillDuration')::int + INTERVAL '28 DAYS') < end_date) then
			'IIT' WHEN (((f.data->>'drugPickupDate')::date + (f.data->>'refillDuration')::int + INTERVAL '28 DAYS') > end_date) THEN
			'Active' ELSE null END art_status, row_number() over(order by e.date_created desc) row_index
		from encounter e join form_data f on e.id = f.encounter_id
		where e.form_code = '3c126779-e6ef-42d4-881e-04b381df9723' and e.household_member_id = inputId
	) tbl where row_index = 1;

RETURN current_status;
END;
$BODY$;
ALTER FUNCTION public.get_current_art_status(bigint, date)
    OWNER TO postgres;

CREATE OR REPLACE FUNCTION public.enrolment_stream(
	indx integer)
    RETURNS text
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE PARALLEL UNSAFE
AS $BODY$
declare rec text[];
BEGIN
rec := ARRAY['Child living with HIV (CLHIV)','HIV Exposed Infants (HEI)','Child living with an HIV Positive Adult',
 'Child at risk or have experienced sexual violence or any other form of violence', 'Teenage mother',
 'Children in need of alternative family care','Children living on the street (exploited almajiri, nomadic, militants)',
 'Children in conflict with law','Children of KP','Child orphaned by AIDS',
 'Child living in a child Headed Household','Child living in a child Headed Household',
 'Child especially adolescent females at risk of transactional sex','Socially excluded children',
 'Children with disabilities','Children trafficked/in exploitative labour',
 'Children affected by armed conflict','Siblings of CLHIV'];
RETURN rec[indx];
END;
$BODY$;
ALTER FUNCTION public.enrolment_stream(integer)
    OWNER TO postgres;

