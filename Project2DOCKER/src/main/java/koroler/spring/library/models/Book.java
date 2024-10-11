package koroler.spring.library.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table (name = "book")
@EqualsAndHashCode (exclude = {"id", "owner", "timestamp", "isExpired"})
public class Book {
	@Id
	@Column (name = "book_id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank (message = "Название книги не может быть пустым")
	@Column (name = "book_name")
	private String bookName;
	
	@Column (name = "book_author")
	@NotBlank (message = "Автор книги не может быть пустым")
	private String book_author;
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "taken_at")
	private Date timestamp;
	
	@ToString.Exclude
	@ManyToOne
	@JoinColumn (name = "book_owner", referencedColumnName = "person_id")
	private Person owner;

	@Transient
	private final Long EXPIRATION_Miliseconds = (long) (10 * 86400000);
	
	@Transient
	private boolean isExpired;
	
	public Book(String book_name, String book_author)
	{
		setBook_author(book_author);
		setBookName(book_name);
		setExpired();
	}
	
	public void setExpired() {
		if (timestamp == null)
			this.isExpired = false;
		else
			this.isExpired = ((new Date().getTime() - timestamp.getTime()) > EXPIRATION_Miliseconds);
    }
	
	public void setTimestamp(Date timestamp)
	{
		this.timestamp = timestamp;
		setExpired();
	}
	
	public void setTimestamp()
	{
		this.timestamp = new Date();
		setExpired(false);
	}
}
