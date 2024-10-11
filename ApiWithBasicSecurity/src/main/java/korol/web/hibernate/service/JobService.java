package korol.web.hibernate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import korol.web.hibernate.model.Job;
import korol.web.hibernate.model.Student;
import korol.web.hibernate.serviceimpl.EmptyJSONEx;

@Service
public interface JobService {
	Job fetchJobById(Integer job_id);
	List <Job> getAllJobs() throws EmptyJSONEx;
	List <Job> fetchByJobLike(String job) throws EmptyJSONEx;
}
