package korol.web.hibernate.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Table (name = "jobs")
@Getter
@Setter
@Accessors (chain = true)
@EqualsAndHashCode
@NoArgsConstructor
public class Job {
	@Id
	@Column (name = "id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "designation")
	private String designation;
	
	@Column (name = "salary")
	private BigDecimal salary;

	public Job(String designation, BigDecimal salary) {
		this.designation = designation;
		this.salary = salary;
	}

}
