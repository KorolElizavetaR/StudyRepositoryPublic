package koroler.spring.library.controllers;


import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import koroler.spring.library.models.Book;
import koroler.spring.library.models.Person;
import koroler.spring.library.service.BookService;
import koroler.spring.library.service.PeopleService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping ("/people")
@RequiredArgsConstructor
public class PeopleController {
	@Autowired
	private final PeopleService peopleService;
	@Autowired
	private final BookService bookService;
	
	// people - список из людей. Есть кнопка добавить человека
	@GetMapping () 
	public String peoplePage(Model model)
	{
		model.addAttribute("people", peopleService.getPeopleList());
		return "people/list";
	}
	
	//  people/{id} - конкретный человек
	@GetMapping ("/{id}") 
	public String personPage(Model model, @PathVariable ("id") Integer id)
	{
		Person person = peopleService.findPerson(id);
		model.addAttribute("person", person);
		model.addAttribute("books", person.getBooks());
		return "people/person";
	}
	
	// people/add - добавить чушбана
	@GetMapping ("/add") 
	public String addPerson(@ModelAttribute Person person) //@ModelAttribute ??
	{
		return "people/addPerson";
	}
	
	@PostMapping ("/add")
	public String submitAddPerson(@Valid Person person, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			return "people/addPerson";
		}
		peopleService.savePerson(person);
		return "redirect:/people";
	}
	
	// delete person
	@DeleteMapping ("/{id}")
	public String deletePerson(@PathVariable("id") Integer id)
	{
		peopleService.deletePerson(id);
		return "redirect:/people";
	}
	
	//Поменять чушбана
	@GetMapping ("/{id}/edit")
	public String editBook(Model model, @PathVariable ("id") Integer id)
	{
		model.addAttribute("person", peopleService.findPerson(id));
		model.addAttribute("person_ID", id);
		return "people/editPerson";
	}
	
	@PatchMapping ("/{person_ID}")
	public String commitEditBook(@Valid @ModelAttribute ("person") Person person, BindingResult bindingResult, @PathVariable ("person_ID") Integer id)
	{
		if (bindingResult.hasErrors())
		{
			return "people/editPerson";
		}
		peopleService.updatePerson(id, person);
		return "redirect:/people/{person_ID}";
	}
}
