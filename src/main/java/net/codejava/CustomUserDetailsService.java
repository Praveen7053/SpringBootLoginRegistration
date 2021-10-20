package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String loginid) throws UsernameNotFoundException {
		User user = repo.findByLoginid(loginid);  
		if(user == null)
		{
			throw new UsernameNotFoundException("User Not Found");
		}
		
		
		return new CustomUserDetails(user);
	}

}
