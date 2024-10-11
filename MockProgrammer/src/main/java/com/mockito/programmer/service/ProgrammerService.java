package com.mockito.programmer.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockito.programmer.exception.ProgrammerIsNotFoundException;
import com.mockito.programmer.model.Programmer;
import com.mockito.programmer.repository.ProgrammerRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Service
@Transactional (readOnly = true)
public class ProgrammerService {
	private final Validator validator;
	private final ProgrammerRepository programmerRepository;

	@Transactional (readOnly = false)
	public Programmer addProgrammer(Programmer programmer)
	{
		Set<ConstraintViolation<Programmer>> violations = validator.validate(programmer);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
        
		return programmerRepository.save(programmer);
	}
	
	@Transactional (readOnly = false)
	public Programmer updateProgrammer(Programmer newProgrammer, Integer id)
	{
		Set<ConstraintViolation<Programmer>> violations = validator.validate(newProgrammer);
        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
 
		Programmer oldProgrammer = programmerRepository.findById(id).orElseThrow(()->new ProgrammerIsNotFoundException());
		newProgrammer.setId(oldProgrammer.getId());
		return programmerRepository.save(newProgrammer);
	}
	
	public List<Programmer> fetchAllProgrammers()
	{
		return programmerRepository.findAll();
	}
	
	public Programmer fetchProgrammerById(Integer id)
	{
		return programmerRepository.findById(id).orElseThrow(()->new ProgrammerIsNotFoundException());
	}
	
	@Transactional (readOnly = false)
	public void deleteProgrammer(Integer id)
	{
		Programmer programmer = programmerRepository.findById(id).orElseThrow(()->new ProgrammerIsNotFoundException());
		programmerRepository.delete(programmer);
	}
}
