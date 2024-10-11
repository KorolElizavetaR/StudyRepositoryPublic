package com.mockito.programmer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.mockito.programmer.exception.ProgrammerIsNotFoundException;
import com.mockito.programmer.model.Programmer;
import com.mockito.programmer.repository.ProgrammerRepository;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProgrammerServiceTest {
	@MockBean
	private ProgrammerRepository repos;
	 @Autowired
	private ProgrammerService service;
	
	@Test
	@Order (20)
	void testAddProgrammer() {
		//Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Programmer prog = Programmer.builder().
				id(1).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		when(repos.save(prog)).thenReturn(prog);
		
		service.addProgrammer(prog);
		
		verify(repos).save(prog);
	}
	
	@Test
	@Order (21)
	void testAddProgrammer_NotValid() {
		Programmer prog = Programmer.builder().
				name("adwd").
				build();
		when(repos.save(prog)).thenReturn(prog);
		
		assertThrows(ConstraintViolationException.class, () -> service.addProgrammer(prog));
	}

	@Test
	@Order (30)
	void testUpdateProgrammer() {
		Programmer prog = Programmer.builder().
				id(1).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		
		Programmer newProg = Programmer.builder().
				name("Алексей Дмитрий Дмитриевич").
				qualification("Python backend разработчик").
				build();
		
		given(repos.findById(prog.getId())).willReturn(Optional.of(newProg));
		service.updateProgrammer(newProg, prog.getId());
		verify(repos).save(Mockito.any(Programmer.class));
		verify(repos).findById(anyInt());
	}

	@Test
	@Order (15)
	void testFetchAllProgrammers() {
		List<Programmer> progs = new ArrayList<>(Collections.singleton(new Programmer()));
		given(repos.findAll()).willReturn(progs);
		
		List<Programmer> expected = service.fetchAllProgrammers();
		
		assertEquals(expected, progs);
		verify(repos).findAll();
	}

	@Test
	@Order (10)
	void testFetchProgrammerById() {
		Programmer prog = new Programmer().builder().
				id(1).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		when(repos.findById(prog.getId())).thenReturn(Optional.of(prog));
		service.fetchProgrammerById(prog.getId());
		verify(repos).findById(prog.getId());
	}
	
	@Test
	@Order (11)
	void testFetchProgrammerById_DoesNotExists() {
		Programmer prog = Programmer.builder().
				id(1).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		
		when(repos.findById(anyInt())).thenReturn(Optional.empty());

		assertThrows(ProgrammerIsNotFoundException.class, () -> service.fetchProgrammerById(prog.getId()));
	}

	@Test
	@Order (40)
	void testDeleteProgrammer() {
		Programmer prog = Programmer.builder().
				id(1).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		when(repos.findById(prog.getId())).thenReturn(Optional.of(prog));
		
		service.deleteProgrammer(prog.getId());
		
		verify(repos).delete(prog);
	}

	@Test
	@Order (41)
	void testDeleteProgrammerThatDoesNotExist()
	{
		Programmer prog = Programmer.builder().
				id(3).
				name("Алексей Дмитрий Дмитриевич").
				qualification("Инженер-программист").
				build();
		given(repos.findById(anyInt())).willReturn(Optional.empty());

		assertThrows(ProgrammerIsNotFoundException.class, () -> service.deleteProgrammer(prog.getId()));
	}
}
