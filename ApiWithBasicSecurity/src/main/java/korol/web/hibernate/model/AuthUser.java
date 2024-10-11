package korol.web.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name = "person")
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AuthUser {
	@Column (name = "id")
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column (name = "username")
	@NotEmpty
	@Size (min = 4, max = 50)
	private String username;
	
	@Column (name = "password")
	@NotEmpty
	private String password;
	
	@Column (name = "role")
	private String role;
	
	public AuthUser (String username, String password)
	{
		this.password = password;
		this.username = username;
	}
}
