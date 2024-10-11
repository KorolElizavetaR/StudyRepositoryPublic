package koroler.spring.library.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Table (name = "person")
@Entity
public class Person {
	@Column (name = "person_id")
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column (name = "full_name")
	@Pattern (regexp = "[А-ЯЁ][-А-яЁё]+ [А-ЯЁ][-А-яЁё]+ [А-ЯЁ][-А-яЁё]+", message = "Некорректный ввод ФИО")
	@NotEmpty (message = "Имя не может быть пустым")
	private String full_name;
	
	@Past (message = "Время не может быть в будущем")
	@NotNull (message = "Дата рождения не может быть пустой")
	@Column (name = "birth_date")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "y-MM-dd")
	private Date birth_date;
	
	@ToString.Exclude
	@OneToMany (mappedBy = "owner")
	private List<Book> books;
	
	public Person(String full_name, Date birth_date, List <Book> books)
	{
		this(full_name, birth_date);
		this.books = books;
	}
	
	public Person(String full_name, Date birth_date)
	{
		setBirth_date(birth_date);
		setFull_name(full_name);
	}
	
	public void addBook(Book book)
	{
		books.add(book);
	}
	
	public void removeBook(Book book)
	{
		books.remove(book);
	}
	
	public List<Book> getBooks()
	{
		return books.isEmpty() ? null : this.books;
	}
}
