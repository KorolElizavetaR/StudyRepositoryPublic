package korol.web.hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.NoResultException;
import korol.web.hibernate.model.Job;
import korol.web.hibernate.model.Student;
import korol.web.hibernate.service.JobService;
import korol.web.hibernate.service.StudentService;
import korol.web.hibernate.serviceimpl.EmptyJSONEx;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/jobs")
public class JobController {
	@Autowired
	private final JobService jobService;
	
	@GetMapping ("/{id}")
	public ResponseEntity<Job> getJob(@PathVariable ("id") Integer id)
	{
		try
		{
			return ResponseEntity.ok(jobService.fetchJobById(id));
		}
		catch(NoResultException ex)
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping ("/all")
	public ResponseEntity<List<Job>> getAllJobs(@RequestParam (value = "name", required = false) String job)
	{
		try
		{	if (job != null && !(job.isBlank()))
			{
				return ResponseEntity.ok(jobService.fetchByJobLike(job));
			}
			return ResponseEntity.ok(jobService.getAllJobs());
		}
		catch(EmptyJSONEx ex)
		{
			return ResponseEntity.notFound().build();
		}
	}
	
	// код добавления работы
	
	// код изменения работы
	
	// код удаления работы
}
