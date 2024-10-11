package korol.web.hibernate.serviceimpl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import korol.web.hibernate.model.Job;
import korol.web.hibernate.model.Student;
import korol.web.hibernate.service.StudentService;
import lombok.RequiredArgsConstructor;

@Service // возможно надо удалить
@Transactional (readOnly = true)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

	@PersistenceContext
	private EntityManager session;

	@Override
	public List <Student> fetchAll() throws EmptyJSONEx {
		List<Student> students = session.createQuery("FROM Student", Student.class).getResultList();
		if (students.isEmpty()) throw new EmptyJSONEx();
		return students;
	}

	@Override
	public List<Student> fetchByNameLike(String name) throws EmptyJSONEx {
		List<Student> students = session.createQuery("FROM Student WHERE fname || ' ' || lname LIKE :name", Student.class).setParameter("name", "%".concat(name).concat("%")).getResultList();
		if (students.isEmpty()) throw new EmptyJSONEx();
		return students;
	}

	@Override
	public Student fetchStudent(Integer id){
		Student student = session.find(Student.class, id);
		return student;
	}

	@Override
	@Transactional (readOnly = false)
	public void addStudent(Student student) {
		session.persist(student);
	}

	@Override
	@Transactional (readOnly = false)
	public void editStudent(Student updateStudent, Integer id) throws IllegalArgumentException, IllegalAccessException, EmptyJSONEx {
		if(updateStudent.isEmpty()) throw new EmptyJSONEx();
		Field[] studentFields = updateStudent.getClass().getDeclaredFields();
		Integer countFields = studentFields.length;
		Student st = session.find(Student.class, id);
		for (int i = 1; i < countFields; i++)
		{
			studentFields[i].setAccessible(true);
			Object value = studentFields[i].get(updateStudent);
			studentFields[i].set(st, value);
			studentFields[i].setAccessible(false);
		}
	}

	@Override
	@Transactional (readOnly = false)
	public void deleteStudent(Integer id) {
		Student student = session.find(Student.class, id);
		session.remove(student);
	}
	
	@Override
	public void addJobToStudent(Integer job_id, Integer student_id) throws EmptyJSONEx {
		Job job = session.find(Job.class, job_id);
		if (job == null) throw new EmptyJSONEx();
		Student st = session.find(Student.class, student_id);
		if (st == null) throw new EmptyJSONEx();
		st.setJob(job);
	}

}
