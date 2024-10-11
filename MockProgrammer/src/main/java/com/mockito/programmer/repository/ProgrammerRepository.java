package com.mockito.programmer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgrammerRepository extends JpaRepository<com.mockito.programmer.model.Programmer, Integer>{

}
