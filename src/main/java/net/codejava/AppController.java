package net.codejava;


import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.codejava.service.UserService;
import net.sf.jasperreports.engine.JRException;

@Controller
public class AppController {

	@Autowired
	private UserRepository repo;
	
	/* for jesper report */
	@Autowired
	private UserService userService ;
	
	
	@GetMapping("")
	public String viewHomePage() { 
		return "index";
	}
	
	@GetMapping("/register")
	public String showSignForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}

	 @PostMapping("/process_register")
	 public String processRegistration(User user) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encodedPassword  = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		 repo.save(user);
	 
	 return "register_success";
	 
	 }
	 
	 
	 @GetMapping("/list_users")
	 public String viewUsersList(Model model) {
		 List<User> listUsers = repo.findAll();
		 model.addAttribute("listUsers", listUsers);
		 return "users";
		 
	 }
	 
	 /* for jespar report */
	 @GetMapping("/list_users/{format}")
	 public String generateReport(@PathVariable String format) throws FileNotFoundException, JRException {
		 return userService.exportReport(format);
	 }
	 
	 
		/* for searching */
	 @RequestMapping(path = {"/search"})
	 public String home(User users, Model model, String searchUsers) {
	  if(searchUsers!=null) {
	   List<User> listUsers = userService.getBySearchUsers(searchUsers);
	   model.addAttribute("listUsers", listUsers);
	  }else {
	  List<User> listUsers = userService.getAllUser();
	  model.addAttribute("listUsers", listUsers);}
	  return "users";
	 }
	 
	
	
	 
}
