package korol.web.hibernate.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import korol.web.hibernate.model.AuthUser;

@Repository
public interface AuthUserRepos extends JpaRepository<AuthUser, Integer>{
	Optional<AuthUser> findByUsername(String username);
}
