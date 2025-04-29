package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entities.Auth;
import com.example.demo.repository.AuthRepo;

import jakarta.servlet.http.HttpSession;

@Controller
public class WebController {
	@Autowired
	private AuthRepo authRepo;

	@GetMapping("/")
	public String index() {
		return "index"; // This will look for src/main/resources/templates/index.html
	}

	@GetMapping("/home")
	public String home(Model model) {
		List<MyData> dataList = new ArrayList<>();
		dataList.add(new MyData(1, "John Doe", "Software Engineer", "john.doe@example.com"));
		dataList.add(new MyData(2, "Jane Smith", "UI/UX Designer", "jane.smith@example.com"));
		dataList.add(new MyData(3, "Michael Brown", "Project Manager", "michael.brown@example.com"));
		dataList.add(new MyData(4, "Emily Davis", "QA Analyst", "emily.davis@example.com"));
		dataList.add(new MyData(5, "William Johnson", "DevOps Engineer", "william.johnson@example.com"));
		dataList.add(new MyData(6, "Sophia Wilson", "Product Owner", "sophia.wilson@example.com"));
		dataList.add(new MyData(7, "Daniel Martinez", "Backend Developer", "daniel.martinez@example.com"));
		dataList.add(new MyData(8, "Olivia Anderson", "Frontend Developer", "olivia.anderson@example.com"));
		dataList.add(new MyData(9, "James Lee", "Business Analyst", "james.lee@example.com"));
		dataList.add(new MyData(10, "Isabella Clark", "Tech Lead", "isabella.clark@example.com"));

		model.addAttribute("dataList", dataList);

		return "home";
	}

	@GetMapping("/login")
	public String login() throws NoSuchAlgorithmException {

		Auth user = new Auth();
		user.setId(1);
		user.setUsername("Admin");
		user.setPassword(Password.encrypt("Admin@123"));
		user.setRole("admin");

		Auth dbstatus = this.authRepo.save(user);

//		System.out.println(dbstatus);

		return "login";
	}

	@GetMapping("/response")
	public String handleSubmit(@RequestParam String username, @RequestParam String password) {

		return "Submitted: " + username + " | " + password;
	}


	@PostMapping("/authenticateLogin")
	public String authenticateLogin(@RequestParam String username, @RequestParam String password, HttpSession session, RedirectAttributes redirectAttribute) throws NoSuchAlgorithmException {

		// Fetch data using username and password
		Auth user = authRepo.authenticateUser(username, Password.encrypt(password));

//        returns data into json format return type is(ResponseEntity<Auth>)
//        if (user != null) {
//            return ResponseEntity.ok(user);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }

		if (user != null) {
			session.setAttribute("userid", user.getId());
			session.setAttribute("username", user.getUsername());
			session.setAttribute("role", user.getRole());
			session.setAttribute("activeStatus", true);
			
//			increase lead count after login
		    Lead.increase();
		    
			redirectAttribute.addFlashAttribute("success", "Login successfull!");
			return "redirect:/dashboard";
//        	return user.toString();
		} else {
			redirectAttribute.addFlashAttribute("error", "Invalid username or password!");
			return "redirect:/login";
		}

	}
	
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		if(session.getAttribute("activeStatus") == null || !(boolean)session.getAttribute("activeStatus")) {
			return "redirect:/login";
		}else {
		    session.invalidate();
			return "redirect:/login";
		}
	}

}
