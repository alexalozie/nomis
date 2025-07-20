package org.nomisng.web;

/**
 * author: Niyi Ogungbemi
 * description: Form resource integration test
 */

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.nomisng.domain.dto.FormDTO;
import org.nomisng.domain.entity.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

@RunWith(SpringRunner.class)
@TestPropertySource(value = {"classpath:application.yml"})
@SpringBootTest(classes = FormResource.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FormResourceTest {
    private static String ENTITY_API_URL = "/api/forms";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private  int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private Form form;

    @Test
    public void contextLoads() {}

    private static FormDTO createEntity() {
        FormDTO form = new FormDTO();
        form.setName("Testform");
        //form.setCode("4043");
        form.setResourceObject("{\"resourceObject\": \"testing form\"}");
        form.setFormType(3);
        form.setResourcePath("/api/v1/path");
        form.setSupportServices("Support service");
        form.setArchived(0);
        form.setVersion("Version 1");
        return  form;
    }

    @Test
    public void testFetchAllForms() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
        ResponseEntity<String> response =
                testRestTemplate.exchange(getRootUrl()+ ENTITY_API_URL,
                        HttpMethod.GET, entity, String.class);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testFetchFormById() {
        Long formId = 17L;
        FormDTO formDTO = testRestTemplate.getForObject(getRootUrl() + ENTITY_API_URL + formId, FormDTO.class);
        System.out.println(formDTO.getName());
        Assert.assertNotNull(formDTO);
    }

    @Test
    public void testCreateForm() {
        FormDTO formDTO = createEntity();
        ResponseEntity<FormDTO> response = testRestTemplate.postForEntity(getRootUrl() + ENTITY_API_URL,
                formDTO, FormDTO.class);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateForm() {
        Long id = 1L;
        FormDTO formDTO = new FormDTO();
        testRestTemplate.put(getRootUrl() + ENTITY_API_URL + id, formDTO);
        FormDTO updatedFormDTO = testRestTemplate.getForObject(getRootUrl() + ENTITY_API_URL + id,
                FormDTO.class);
        Assert.assertNotNull(updatedFormDTO);
    }

    @Test
    public void testDeleteForm() {
        Long id = 2L;
        FormDTO formDTO = testRestTemplate.getForObject(getRootUrl() + ENTITY_API_URL + id,
                FormDTO.class);
        Assert.assertNotNull(formDTO);
        testRestTemplate.delete(getRootUrl() + ENTITY_API_URL + id);
        try {
            formDTO = testRestTemplate.getForObject(getRootUrl() + ENTITY_API_URL + id, FormDTO.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }



//    @org.junit.jupiter.api.Test
//    @WithMockUser(username = "guest@nomisng.org")
//    @Transactional
//    public void testSaveForm() throws Exception {
//        Form form = createEntity();
//        restFormMockMvc.perform(post(ENTITY_API_URL)
//                        .content(TestUtility.convertObjectToJsonBytes(form))
//                        .header("Authorization", JWT_TOKEN)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .andDo(print())
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    public void saveForm() throws Exception {
//        Form form = createEntity();
//        int databaseSizeBeforeCreate = formRepository.findAll().size();
//        // Create the Associate
//        FormDTO formDTO = formMapper.toFormDTO(form);
//        restFormMockMvc
//                .perform(post(ENTITY_API_URL)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(TestUtility.convertObjectToJsonBytes(formDTO)))
//                .andExpect(status().isCreated());
//
//        // Validate the Associate in the database
//        List<Form> formList = formRepository.findAll();
//        assertThat(formList).hasSize(databaseSizeBeforeCreate + 1);
//        Form testForm = formList.get(formList.size() - 1);
//        assertThat(testForm.getName()).isEqualTo(formDTO.getName());
//
//    }
//
//    @Test
//  //  @WithMockUser(username="guest@nomisng.org", password="12345")
//    public void saveData() throws Exception {
//        Form form = createEntity();
//        FormDTO formDTO = formMapper.toFormDTO(form);
//     //   when(this.formService.save(any(FormDTO.class))).thenReturn(form);
//
//        restFormMockMvc.perform(
//                        MockMvcRequestBuilders.post(ENTITY_API_URL)
//                                .content(asJsonString(formDTO))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk());
//
//    }
//
//    private static String asJsonString(final Object obj) {
//        try {
//            return  new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception exception) {
//            throw  new RuntimeException();
//        }
//    }

}
