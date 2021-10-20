package net.codejava;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("SELECT u FROM User u WHERE u.loginid = ?1")
	User findByLoginid(String loginid); 
	
	
	/* search users */
	@Modifying  
	@Query(value = "select * from users u where u.name like %:searchUsers% or u.loginid like %:searchUsers% or u.number like %:searchUsers%", nativeQuery = true)
	 List<User> findBySearchUsers(@Param("searchUsers") String searchUsers);
}
