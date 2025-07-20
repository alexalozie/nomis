package org.nomisng.web;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.nomisng.IntegrationTest;
import org.nomisng.domain.dto.DrugDTO;
import org.nomisng.domain.entity.Drug;
import org.nomisng.domain.entity.Item;
import org.nomisng.domain.entity.RegimenDrug;
import org.nomisng.domain.mapper.DrugMapper;
import org.nomisng.repository.DrugRepository;
import org.nomisng.utility.TestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
public class DrugResourceTest {
    private static Long ID = 120L;
    private static String ABBREV = "EFV2";
    private static String NAME = "Efavirenz";
    private static String STRENGTH="600mg";
    private static Integer PACK_SIZE=30;
    private static String DOSE_FORM="Capsules";
    private static Integer MORNING = 1;
    private static Integer AFTERNOON = 0;
    private static Integer EVENING = 1;
    private static Item ITEM = null;
    private static List<RegimenDrug> REGIMEN_DRUG_LIST = new ArrayList<>();
    private static final String ENTITY_API_URL = "/api/drugs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DrugRepository drugRepository;
    @Autowired
    private DrugMapper drugMapper;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc restDrugMockMvc;

    private Drug drug;

    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug();
        drug.setName(NAME);
        drug.setAbbrev(ABBREV);
        drug.setDoseForm(DOSE_FORM);
        drug.setPackSize(PACK_SIZE);
        drug.setAfternoon(AFTERNOON);
        drug.setEvening(EVENING);
        drug.setMorning(MORNING);
        drug.setItem(ITEM);
        drug.setStrength(STRENGTH);
        drug.setRegimenDrugs(REGIMEN_DRUG_LIST);

        return drug;
    }

    @BeforeEach
    public void initTest() {
        drug = createEntity(entityManager);
    }

    @Test
    @Transactional
    public void testSaveDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();
        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);
        restDrugMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtility.convertObjectToJsonBytes(drugDTO)))
                .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getAbbrev()).isEqualTo(ABBREV);
    }

    @org.junit.jupiter.api.Test
    @Transactional
    void createDrugWithExistingId() throws Exception {
        drug.setId(ID);
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);

        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        restDrugMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtility.convertObjectToJsonBytes(drugDTO)))
                .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setName(null);

        // Create the Drug, which fails.
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);

        restDrugMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtility.convertObjectToJsonBytes(drugDTO)))
                .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }
    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        drugRepository.saveAndFlush(drug);

        restDrugMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(NAME)))
                .andExpect(jsonPath("$.[*].abbrev").value(hasItem(ABBREV)))
                .andExpect(jsonPath("$.[*].doseForm").value(hasItem(DOSE_FORM)))
                .andExpect(jsonPath("$.[*].strength").value(hasItem(STRENGTH)))
                .andExpect(jsonPath("$.[*].packSize").value(hasItem(PACK_SIZE)))
                .andExpect(jsonPath("$.[*].morning").value(hasItem(MORNING)))
                .andExpect(jsonPath("$.[*].afternoon").value(hasItem(AFTERNOON)))
                .andExpect(jsonPath("$.[*].evening").value(hasItem(EVENING)));
    }

    @Test
    public void getDrugById() throws Exception {
        drugRepository.saveAndFlush(drug);

        restDrugMockMvc
                .perform(get(ENTITY_API_URL_ID, drug.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(NAME)))
                .andExpect(jsonPath("$.[*].abbrev").value(hasItem(ABBREV)))
                .andExpect(jsonPath("$.[*].doseForm").value(hasItem(DOSE_FORM)))
                .andExpect(jsonPath("$.[*].strength").value(hasItem(STRENGTH)))
                .andExpect(jsonPath("$.[*].packSize").value(hasItem(PACK_SIZE)))
                .andExpect(jsonPath("$.[*].morning").value(hasItem(MORNING)))
                .andExpect(jsonPath("$.[*].afternoon").value(hasItem(AFTERNOON)))
                .andExpect(jsonPath("$.[*].evening").value(hasItem(EVENING)));
    }

    @Test
    @Transactional
    public void patchNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();
        drug.setId(count.incrementAndGet());

        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, drugDTO.getId())
                                .contentType("application/merge-patch+json")
                                .content(TestUtility.convertObjectToJsonBytes(drugDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void patchWithIdMismatchDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();
        drug.setId(count.incrementAndGet());

        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrugMockMvc
                .perform(
                        patch(ENTITY_API_URL_ID, count.incrementAndGet())
                                .contentType("application/merge-patch+json")
                                .content(TestUtility.convertObjectToJsonBytes(drugDTO))
                )
                .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void patchWithMissingIdPathParamDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();
        drug.setId(count.incrementAndGet());

        // Create the Drug
        DrugDTO drugDTO = drugMapper.toDrugDTO(drug);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrugMockMvc
                .perform(
                        patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtility.convertObjectToJsonBytes(drugDTO))
                )
                .andExpect(status().isMethodNotAllowed());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Delete the drug
        restDrugMockMvc
                .perform(delete(ENTITY_API_URL_ID, drug.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }

}
