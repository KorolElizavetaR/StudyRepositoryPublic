package korol.web.hibernate.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import korol.web.hibernate.security.AuthUserDetails;

@RestController
@RequestMapping ("/check")
public class CheckAuth {
	
	@GetMapping ("/guest")
	public String homepage()
	{
		return "accessible by everyone!";
	}
	
	@GetMapping("/user")
	private String secured(@AuthenticationPrincipal AuthUserDetails principal)
	{
		return "User page. You are: " + principal.getUsername();
	}
	
	@GetMapping("/admin")
	private String admin(@AuthenticationPrincipal AuthUserDetails principal)
	{
		return "Admin page. You are: " + principal.getUsername();
	}
	
	
}
