package koroler.spring.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import koroler.spring.library.models.Book;
import koroler.spring.library.models.Person;
import koroler.spring.library.repos.BooksRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional (readOnly = true)
@RequiredArgsConstructor
public class BookService {
	@Autowired
	private final BooksRepository bookRepos;
	
	public List<Book> getBookList()
	{
		return bookRepos.findAll();
	}
	
	public Book findBook(Integer id)
	{
		Book book = bookRepos.findById(id).orElse(null);
		if (book != null)
		{
			book.setExpired();
		}
		return book;
	}
	
	public List <Book> findBookByLikeStr(String str)
	{
		return bookRepos.findByBookNameContainingIgnoreCase(str);
	}
	
	
	@Transactional
	public void saveBook(Book book)
	{
		bookRepos.save(book);
	}
	
	@Transactional
	public void updateBook(Integer id, Book newBook)
	{
		newBook.setId(id);
		bookRepos.save(newBook);
	}
	
	@Transactional
	public void deleteBook(Integer id)
	{
		bookRepos.deleteById(id);
	}

	@Transactional
	public void addOwner(Person person, Integer bookId)
	{
		Book book = bookRepos.findById(bookId).orElse(null);
		if (book != null)
		{
			book.setOwner(person);
			book.setTimestamp();
		}
	}
	
	@Transactional
	public void removeOwner(Integer bookId)
	{
		Book book = bookRepos.findById(bookId).orElse(null);
		if (book != null)
		{
			book.setOwner(null);
			book.setTimestamp(null);
		}
	}
	
	public List <Book> findBookSortedTrue(Integer page)
	{
		return bookRepos.findAll(PageRequest.of(page, 5, Sort.by("bookName"))).getContent();
	}

}
