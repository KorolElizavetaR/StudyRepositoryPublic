package com.mockito.programmer.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mockito.programmer.exception.ProgrammerIsNotFoundException;
import com.mockito.programmer.model.Programmer;
import com.mockito.programmer.service.ProgrammerService;

@SpringBootTest (webEnvironment=WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProgrammerControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	private static Programmer programmer;
	private static Programmer invalidProgrammer;

	@MockBean
	private ProgrammerService service;

	@BeforeAll
	public static void init() {
		programmer = new Programmer("Жабоед Артур Викторович", "Frontend'ер");
		invalidProgrammer = new Programmer("жаба", "Frontend'ер");
	}

	@Test
	@Order (10)
	void testGetProgrammerById() {
		given(service.fetchProgrammerById(anyInt())).willReturn(programmer);
		
		ResponseEntity<Programmer> response = restTemplate.getForEntity("/api/programmer/1", Programmer.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().equals(programmer));
	}
	
	@Test
	@Order (11)
	void testGetProgrammerById_NotFound() {
		given(service.fetchProgrammerById(anyInt())).willThrow(new ProgrammerIsNotFoundException());
		
		ResponseEntity<Programmer> response = restTemplate.getForEntity("/api/programmer/1", Programmer.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
	}

	@Test
	@Order (20)
	void testAddProgrammer_Succesful() {
		given(service.addProgrammer(Mockito.any(Programmer.class))).willReturn(programmer);
		
		ResponseEntity<Programmer> response = restTemplate.postForEntity("/api/programmer/add", programmer, Programmer.class);
		
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().equals(programmer));
	}
	
	@Test
	@Order (22)
	void testAddProgrammer_Invalid() {
		given(service.addProgrammer(Mockito.any(Programmer.class))).willReturn(programmer);
		ResponseEntity<Programmer> response = restTemplate.postForEntity("/api/programmer/add", invalidProgrammer, Programmer.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

}
