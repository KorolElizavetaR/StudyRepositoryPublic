package korol.web.hibernate.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import korol.web.hibernate.model.Student;
import korol.web.hibernate.serviceimpl.EmptyJSONEx;

@Service
public interface StudentService {
	List <Student> fetchAll() throws EmptyJSONEx;
	
	List <Student> fetchByNameLike(String name) throws EmptyJSONEx;
	
	Student fetchStudent(Integer id);
	
	void addStudent(Student student);
	
	void editStudent(Student student, Integer id)  throws IllegalArgumentException, IllegalAccessException, EmptyJSONEx;
	
	void deleteStudent(Integer id);
	
	void addJobToStudent(Integer job_id, Integer student_id) throws EmptyJSONEx;
}
