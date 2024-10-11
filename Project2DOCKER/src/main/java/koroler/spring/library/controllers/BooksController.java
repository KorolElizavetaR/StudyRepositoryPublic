package koroler.spring.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import koroler.spring.library.models.Book;
import koroler.spring.library.models.Person;
import koroler.spring.library.service.BookService;
import koroler.spring.library.service.PeopleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping ("/books")
@RequiredArgsConstructor
public class BooksController {
	@Autowired
	private final PeopleService peopleService;
	@Autowired
	private final BookService bookService;
	
	// Получить список всех книг
	@GetMapping ()
	public String GetBooks(Model model, @RequestParam (value = "page", defaultValue = "0", required = true) String page, @RequestParam (value = "like", required = false) String like)
	{
		if (like != null && !(like.isBlank())) 
		{
			model.addAttribute("books", bookService.findBookByLikeStr(like));
			model.addAttribute("like", like);
		}
		else
		{
			model.addAttribute("books", bookService.findBookSortedTrue(Integer.valueOf(page)));
		}
		return "books/list";
	}
	
	// Получить книгу по айди
	@GetMapping ("/{id}")
	public String getBook(Model model, @PathVariable ("id") Integer id, @ModelAttribute ("person") Person person)
	{
		Book book = bookService.findBook(id);
		model.addAttribute("book", book);
		model.addAttribute("owner", book.getOwner());
		model.addAttribute("people", peopleService.getPeopleList());
			System.out.println(book.isExpired()); // служебная строчка, потом удалить
		return "books/book";
	}
	
	// из /books/{id} - добавить читателя книге
	@PatchMapping ("/{id}/add")
	public String addBookOwner(Person person, @PathVariable("id") Integer id)
	{
		bookService.addOwner(person, id);
		return "redirect:/books/{id}";
	}
	
	// из /books/{id} - удалить читателя книги
	@PatchMapping ("/{id}/remove")
	public String removeBookOwner(@PathVariable("id") Integer id)
	{
		bookService.removeOwner(id);
		return "redirect:/books/{id}";
	}
	
	// добавить книгу - из /books
	@GetMapping ("/add")
	public String addBook(@ModelAttribute Book book)
	{
		return "books/addbook";
	}
	
	@PostMapping ("/add") 
	public String submitAddBook(@Valid @ModelAttribute("book") Book book, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return "books/addbook";
		}
		bookService.saveBook(book);
		return "redirect:/books";
	}
	
	// Редачить книгу
	@GetMapping ("/{id}/edit")
	public String editBook(Model model, @PathVariable ("id") Integer id)
	{
		model.addAttribute("book", bookService.findBook(id));
		model.addAttribute("book_ID", id);
		return "books/editbook";
	}
	
	@PatchMapping ("/{book_ID}")
	public String commitEditBook(@Valid @ModelAttribute ("book") Book book, BindingResult bindingResult, @PathVariable ("book_ID") Integer id)
	{
		if (bindingResult.hasErrors())
		{
			return "books/editbook";
		}
		bookService.updateBook(id, book);
		return "redirect:/books/{book_ID}";
	}
}
