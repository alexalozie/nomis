package org.nomisng;

/**
 * author: Niyi Ogungbemi
 * description: Web integration test
 */

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.nomisng.domain.dto.DrugDTO;
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
@SpringBootTest(classes = NomisApplication.class,
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NomisApplicationTests {
	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private  int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {}

	@Test
	public void testGetAllDrugs() {
		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);
		ResponseEntity<String> response =
				testRestTemplate.exchange(getRootUrl()+ "/api/drugs",
						HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetDrugById() {
		DrugDTO drugDTO = testRestTemplate.getForObject(getRootUrl() + "/api/drugs/1", DrugDTO.class);
		System.out.println(drugDTO.getName());
		Assert.assertNotNull(drugDTO);
	}

	@Test
	public void testCreateDrug() {
		DrugDTO drugDTO = new DrugDTO();
		ResponseEntity<DrugDTO> response = testRestTemplate.postForEntity(getRootUrl() + "/api/drugs",
				drugDTO, DrugDTO.class);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testUpdateDrug() {
		Long id = 1L;
		DrugDTO drugDTO = new DrugDTO();
		testRestTemplate.put(getRootUrl() + "/api/drugs/" + id, drugDTO);
		DrugDTO updatedDrug = testRestTemplate.getForObject(getRootUrl() + "/api/drugs/" + id,
				DrugDTO.class);
		Assert.assertNotNull(updatedDrug);
	}

	@Test
	public void testDeleteDrug() {
		Long id = 2L;
		DrugDTO drugDTO = testRestTemplate.getForObject(getRootUrl() + "/api/drugs/" + id,
				DrugDTO.class);
		Assert.assertNotNull(drugDTO);
		testRestTemplate.delete(getRootUrl() + "/api/drugs/" + id);
		try {
			drugDTO = testRestTemplate.getForObject(getRootUrl() + "/api/drugs/" + id, DrugDTO.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}

}
