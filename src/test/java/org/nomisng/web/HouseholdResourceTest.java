package org.nomisng.web;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.nomisng.domain.dto.HouseholdDTO;
import org.nomisng.domain.entity.Household;
import org.nomisng.repository.HouseholdRepository;
import org.nomisng.service.HouseholdService;
import org.nomisng.util.JsonUtil;
import org.nomisng.web.apierror.RestExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HouseholdResource.class)
@ExtendWith(SpringExtension.class)
public class HouseholdResourceTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    HouseholdService householdService;
    private final String BASE_URL = "/api/households";



    @Mock
    HouseholdRepository householdRepository;

    ObjectMapper mapper = new ObjectMapper();

    @InjectMocks
    HouseholdResource householdController;

    private JSONObject houseHoldDTOJsonObject;
    private JSONObject houseHoldDTODetailsJsonObject;
    private JSONObject houseHoldMemberDTOJsonObject;
    private Map<String, String> houseHoldAddressMap;
    private JSONArray houseHoldAddressJsonArray;
    private Household household;
    private HouseholdDTO householdDTO;
    private Long id = 1L;
    List<Household> households;
    List<HouseholdDTO> householdDTOS;


    @Test
    public void saveHousehold_success() throws Exception {

      //  Mockito.when(householdService.save(householdDTO)).thenReturn(household);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.getJsonFromObject(householdDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.firstName").value(houseHoldDTODetailsJsonObject.get("firstName")))
                .andExpect(jsonPath("$.details.lastName").value(houseHoldDTODetailsJsonObject.get("lastName")))
                .andReturn();

        Household returnedHousehold = JsonUtil.getObjectFromJson(result.getResponse().getContentAsString(), Household.class);
        Assert.assertNotNull(returnedHousehold);
    }

    @Test
    public void getAllHouseholdsById_success() throws Exception {

        Mockito.when(householdService.getHouseholdById(id)).thenReturn(householdDTO);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL+"/"+id)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("utf-8"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.firstName").value(houseHoldDTODetailsJsonObject.get("firstName")))
                .andExpect(jsonPath("$.details.lastName").value(houseHoldDTODetailsJsonObject.get("lastName")))
                .andReturn();

        HouseholdDTO returnedHouseholdDTO = JsonUtil.getObjectFromJson(result.getResponse().getContentAsString(), HouseholdDTO.class);
        Assert.assertNotNull(returnedHouseholdDTO);
    }

    /*@Test
    public void getAllHouseholds_success() throws Exception {
        Mockito.when(householdService.getAllHouseholds()).thenReturn(householdDTOS);
        mockMvc.perform(MockMvcRequestBuilders
                .get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }*/

    @After
    public void resetDb() {
        //householdRepository.deleteAll();
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(householdController)
                .setControllerAdvice(new RestExceptionHandler()).build();

        //Define the structure of the json object
        houseHoldDTOJsonObject = new JSONObject("{\"id\":0}");
        houseHoldDTODetailsJsonObject = new JSONObject("{\"mobilePhoneNumber\":\"09087265265\"}");
        houseHoldMemberDTOJsonObject = new JSONObject("{\"householdMemberType\":0}");
        houseHoldAddressMap = new HashMap<>();
        houseHoldAddressJsonArray = new JSONArray();

        //Populate HouseholdDTO
        houseHoldDTODetailsJsonObject.put("firstName", "Alex");
        houseHoldDTODetailsJsonObject.put("lastName", "Adama");
        houseHoldAddressMap.put("state", "Abuja");
        houseHoldDTOJsonObject.put("details", houseHoldDTODetailsJsonObject);
        houseHoldDTOJsonObject.put("householdMemberDTO", houseHoldMemberDTOJsonObject);
        houseHoldDTOJsonObject.put("householdAddressDTOS", houseHoldAddressJsonArray);
        houseHoldDTOJsonObject.put("uniqueId", "FCT9-623521");
        householdDTO = mapper.readValue(houseHoldDTOJsonObject.toString(), HouseholdDTO.class);

        householdDTO.setId(1L);
        HouseholdDTO householdDTO2 = householdDTO;
        householdDTO2.setId(2L);

        HouseholdDTO householdDTO3 = householdDTO;
        householdDTO3.setId(3L);

        householdDTOS = new ArrayList<>(Arrays.asList(householdDTO, householdDTO2, householdDTO3));



        //Populate Household
        household = new Household();
        household.setDetails(householdDTO.getDetails());
        household.setId(id);
        household.setUniqueId("FCT9-623521");

        Household household2 = new Household();
        household2.setDetails(householdDTO.getDetails());
        household2.setId(2L);
        household2.setUniqueId("FCT9-6235210");

        Household household3 = new Household();
        household3.setDetails(householdDTO.getDetails());
        household3.setId(3L);
        household3.setUniqueId("FCT9-6235214");

        households = new ArrayList<>(Arrays.asList(household, household2, household3));

    }
}
