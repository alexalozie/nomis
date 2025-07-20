package org.nomisng.web;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.range.DoubleRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import io.github.benas.randombeans.randomizers.text.StringRandomizer;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.nomisng.domain.dto.SchoolDTO;
import org.nomisng.domain.entity.School;
import org.nomisng.domain.mapper.SchoolMapper;
import org.nomisng.repository.SchoolRepository;
import org.nomisng.service.impl.SchoolServiceImpl;
import org.nomisng.utility.TestUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static io.github.benas.randombeans.EnhancedRandomBuilder.aNewEnhancedRandom;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SchoolResourceTest {
    private static final String ENTITY_API_URL = "/api/schools";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SchoolMapper schoolMapper;

    @MockBean
    private SchoolServiceImpl schoolService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private MockMvc schoolRestMockMvc;
    @Random(excludes = "id")
    private School school;

    public static School createEntity(EntityManager entityManager) {
        School school = aNewEnhancedRandom().nextObject(School.class);
        return  school;
    }

    public static School updateEntity(EntityManager entityManager) {
        School school = aNewEnhancedRandom().nextObject(School.class);
        return  school;
    }

    @BeforeEach
    public void initTest() {
        school = createEntity(entityManager);
    }

    @Test
    @Transactional
    public  void createSchool() throws Exception {
        School school = aNewEnhancedRandom().nextObject(School.class);
        int databaseSizeBeforeCreate = schoolRepository.findAll().size();
        SchoolDTO schoolDTO = schoolMapper.toSchoolDTO(school);
        schoolRestMockMvc
                .perform(post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtility.convertObjectToJsonBytes(schoolDTO)))
                .andExpect(status().isCreated());

        // Validate the school in the database
        List<School> schoolList = schoolRepository.findAll();
        Assertions.assertThat(schoolList).hasSize(databaseSizeBeforeCreate + 1);
        School testSchool = schoolList.get(schoolList.size() - 1);
        Assertions.assertThat(testSchool.getName()).isEqualTo(schoolDTO.getName());

    }

    static EnhancedRandom enhancedRandom = EnhancedRandomBuilder
            .aNewEnhancedRandomBuilder()
            .stringLengthRange(10, 20)
            .randomize(Integer.class, IntegerRangeRandomizer.aNewIntegerRangeRandomizer(0, 10))
            .randomize(String.class, StringRandomizer.aNewStringRandomizer(8))
            .randomize(Double.class, DoubleRangeRandomizer.aNewDoubleRangeRandomizer(0.0, 10.0))
            .collectionSizeRange(2, 3)
            .objectPoolSize(30)
            .build();


    @RegisterExtension
    static RandomBeansExtension randomBeansExtension = new RandomBeansExtension(enhancedRandom);
}
