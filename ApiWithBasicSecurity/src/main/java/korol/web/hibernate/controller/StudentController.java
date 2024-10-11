package korol.web.hibernate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.NoResultException;
import korol.web.hibernate.model.Student;
import korol.web.hibernate.service.StudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping ("students")
public class StudentController {
	@Autowired
	private final StudentService studentService;
	
	@GetMapping("all")
	public ResponseEntity<Object> getStudents(@RequestParam (value = "name", required = false) String name)
	{
		try
		{
		if (name != null && !(name.isBlank()))
		{
			return  ResponseEntity.ok(studentService.fetchByNameLike(name));
		}
		return ResponseEntity.ok(studentService.fetchAll());
		}
		catch (Exception ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@GetMapping ("/{id}")
	public ResponseEntity<Student> getStudent(@PathVariable ("id") Integer id)
	{
		try
		{
			return ResponseEntity.ok(studentService.fetchStudent(id));
		}
		catch(NoResultException ex)
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping ("/add")
	public ResponseEntity<Object> addStudent(@RequestBody Student student)
	{
		try
		{
			studentService.addStudent(student);
			return ResponseEntity.ok(studentService.fetchStudent((int) student.getId()));
		}
		catch (Exception ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@PatchMapping ("/{id}")
	public ResponseEntity<Object> editStudent(@PathVariable ("id") Integer id, @RequestBody Student student)
	{
		try
		{
			studentService.editStudent(student, id);
			return ResponseEntity.ok(studentService.fetchStudent(id));
		}
		catch (Exception ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<Object> deleteStudent(@PathVariable ("id") Integer id)
	{
		try
		{
			studentService.deleteStudent(id);
			return ResponseEntity.ok("Yes, you murdered them, you happy now?");
		}
		catch (Exception ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
	
	@PatchMapping ("/{id}/setjob")
	public ResponseEntity<Object> setJobToStudent(@PathVariable ("id") Integer id, @RequestBody Integer job_id)
	{
		try
		{
			studentService.addJobToStudent(job_id, id);
			return ResponseEntity.ok(studentService.fetchStudent(id));
		}
		catch (Exception ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage());
	    }
	}
}
