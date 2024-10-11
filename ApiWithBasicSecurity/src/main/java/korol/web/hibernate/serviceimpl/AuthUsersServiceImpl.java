package korol.web.hibernate.serviceimpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import korol.web.hibernate.model.AuthUser;
import korol.web.hibernate.repository.AuthUserRepos;
import korol.web.hibernate.security.AuthUserDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthUsersServiceImpl implements UserDetailsService {
	@Autowired
	private final AuthUserRepos authUserRepos;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional <AuthUser> user = authUserRepos.findByUsername(username);
		if (user.isEmpty())
			throw new UsernameNotFoundException("User " + username + " is not found");
		return new AuthUserDetails(user.get());
	}

}
