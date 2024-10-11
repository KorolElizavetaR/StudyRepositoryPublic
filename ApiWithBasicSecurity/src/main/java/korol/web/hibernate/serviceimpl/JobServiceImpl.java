package korol.web.hibernate.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import korol.web.hibernate.model.Job;
import korol.web.hibernate.model.Student;
import korol.web.hibernate.service.JobService;
import lombok.RequiredArgsConstructor;

@Transactional (readOnly = true)
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService{
	@PersistenceContext
	private EntityManager session;
	
	@Override
	public Job fetchJobById(Integer id) {
		return session.find(Job.class, id);
	}

	@Override
	public List<Job> getAllJobs() throws EmptyJSONEx {
		List<Job> jobs = session.createQuery("FROM Job", Job.class).getResultList();
		if (jobs == null) throw new EmptyJSONEx();
		return jobs;
	}

	@Override
	public List<Job> fetchByJobLike(String job) throws EmptyJSONEx{
		List<Job> jobs = session.createQuery("FROM Job WHERE designation LIKE :job", Job.class).setParameter("name", "%".concat(job).concat("%")).getResultList();
		if (jobs == null) throw new EmptyJSONEx();
		return jobs;
	}

}
