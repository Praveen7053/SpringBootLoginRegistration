package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
	
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test 
	public void testCreateUser()
	{
		 User user = new User();
		 user.setName("Praveen");
		 user.setState("Delhi");
		 user.setNumber("7053624942");
		 user.setGender("M");
		 user.setAddress("Okhla");
		 user.setCity("DElhi");
		 user.setLoginid("praveen");
		 user.setPassword("123456");
		 
		User savedUser = repo.save(user);
		
		 
		User existUser =  entityManager.find(User.class, savedUser.getId());
		
		
		assertThat(existUser.getLoginid()).isEqualTo(user.getLoginid());  
	}	
	
	@Test
	public void testFindUserByLoginid(){
		String loginid = "praveen";
		
		User user = repo.findByLoginid(loginid);
		
		assertThat(user).isNotNull();
	}
	
}
