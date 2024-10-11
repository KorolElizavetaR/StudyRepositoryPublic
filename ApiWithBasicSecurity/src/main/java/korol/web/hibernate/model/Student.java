package korol.web.hibernate.model;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors (chain = true)
@Entity
@Table (name = "students")
@EqualsAndHashCode
@NoArgsConstructor
public class Student {
	@Transient
	private static final Student EMPTY = new Student();

	@Id
	@Column (name = "student_id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column (name = "fname")
    private String fname;
	
	@Column (name = "lname")
    private String lname;
	
	@Column (name = "mobile_no")
    private String mobileNo;
	
	@Column (name = "email")
    private String email;
	
	@Column (name = "dob")
	@Temporal(TemporalType.DATE)
    private Date dob;
	
	@Column (name = "doj")
	@Temporal(TemporalType.DATE)
    private Date doj;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn (name = "job_id")
	private Job job;

	public Student(String fname, String lname, Date dob) {
		this.fname = fname;
		this.lname = lname;
		this.dob = dob;
	}
	
	public Student(String fname, String lname, String email, Date dob) {
		this(fname, lname, dob);
		this.email = email;
	}

	public Student(String fname, String lname, String mobileNo, String email, Date dob) {
		this(fname, lname, email, dob);
		this.mobileNo = mobileNo;
	}
	
	public Student(String fname, String lname, String mobileNo, String email, Date dob, Date doj, Job job) {
		this(fname, lname, mobileNo, email, dob);
		this.doj = doj;
		this.job = job;
	}
	
	public boolean isEmpty() {
        return this.equals(EMPTY);
    }
}
