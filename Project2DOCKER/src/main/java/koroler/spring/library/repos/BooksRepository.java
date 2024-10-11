package koroler.spring.library.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import koroler.spring.library.models.Book;
import koroler.spring.library.models.Person;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer>{
	//List <Book> findByOwner(Person owner);
	List <Book> findByBookNameContainingIgnoreCase(String str);
}
