package korol.web.hibernate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import korol.web.hibernate.serviceimpl.AuthUsersServiceImpl;
import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
	private final AuthUsersServiceImpl authUsersServiceImpl;

	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{	
		auth.userDetailsService(authUsersServiceImpl).passwordEncoder(getPasswordEncoder());
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
	{ 
		http.
			csrf(csrf -> csrf.disable()).
			sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
			authorizeHttpRequests((requests) ->  
				requests.
				requestMatchers("/check/guest", "/jobs/all", "/jobs/{id}").permitAll().
				requestMatchers("/students/all", "/students/{id}", "/students/{id}").hasRole("USER").
				requestMatchers("/check/admin", "/jobs/**", "/students/**").hasRole("ADMIN").
				anyRequest().authenticated()).
			logout((logout) -> logout.permitAll()).
			httpBasic(Customizer.withDefaults());
		return http.build();
	}
}
