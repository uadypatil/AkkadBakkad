package com.example.demo.controllers;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Lead;
import com.example.demo.MyData;
import com.example.demo.Password;
import com.example.demo.QuoteFetcher;
import com.example.demo.entities.Auth;
import com.example.demo.entities.Message;
import com.example.demo.entities.Quote;
import com.example.demo.repository.AuthRepo;
import com.example.demo.repository.MessageRepository;
import com.example.demo.repository.QuoteRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class Dashboard {
	

	@Autowired
	private AuthRepo authRepo;
	
	@Autowired 
	private MessageRepository messageRepository;

	@Autowired
	private QuoteRepository quoteRepository;
	
	@GetMapping("/dashboard")
	public String dashboard(Model model, HttpSession session) {
//		validating session
		if (!this.validateSession(session)) {
			return "redirect:/login";
		}

		String sessionUsername = (String)session.getAttribute("username"); 
		model.addAttribute("sessionUsername", sessionUsername);

//		setting data on page
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

		List<Auth> users = authRepo.findAll();
		model.addAttribute("users", users);
		
	    model.addAttribute("title_name", "Dashboard");
		
//	    lead count 
//	    int leadCount = Lead.count();
	    int jokeCount = (int)this.quoteRepository.count();
	    model.addAttribute("leadCount", jokeCount);
	    
//	    users count
	    int userCount = (int)this.authRepo.count();
	    model.addAttribute("userCount", userCount);
	    
//	    message count
	    int messageCount = (int)this.messageRepository.count();
	    model.addAttribute("messageCount", messageCount);
	    
//	    joke generator
	    Quote joke = QuoteFetcher.getQuote();
	    Quote dbResponse = this.quoteRepository.save(joke);
	    
	    if(dbResponse != null) {
		    model.addAttribute("joke", joke);	
	    }else {
		    model.addAttribute("joke", null);
	    }

//	    load all jokes
	    List<Quote> jokes = this.quoteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
	    		
	    if(jokes != null) {
		    model.addAttribute("jokes", jokes);	
	    }else {
		    model.addAttribute("jokes", null);
	    }
	    
	    
		return "home";
	}

	@GetMapping("/profile")
	public String profile(Model model, HttpSession session) {
//		validating session
		if (!this.validateSession(session)) {
			return "redirect:/login";
		}

		String sessionUsername = (String)session.getAttribute("username"); 
		model.addAttribute("sessionUsername", sessionUsername);
		
		List<Auth> users = authRepo.findAll();
		model.addAttribute("usersList", users);

		model.addAttribute("users", users);
		

	    model.addAttribute("title_name", "Dashboard");
	    
		return "profile";
	}

	@PostMapping("/addNewUser")
	public String addNewUser(@RequestParam String username, @RequestParam String password, @RequestParam String role,
			HttpSession session, RedirectAttributes redirectAttribute) throws NoSuchAlgorithmException {
//		validating session
		if (!this.validateSession(session)) {
			return "redirect:/login";
		}

		System.out.println(username);
		Auth user = new Auth();
		user.setUsername(username);
		user.setPassword(Password.encrypt(password));
		user.setRole(role);

		Auth dbstatus = this.authRepo.save(user);
		if (dbstatus != null) {
			redirectAttribute.addFlashAttribute("success", "User added successfully!");
			return "redirect:/profile";
		} else {
			redirectAttribute.addFlashAttribute("error", "Failed to add user!");
			return "redirect:/profile";
		}
		// return "redirect:/profile";
//		return user.toString();
	}

	@PostMapping("/updateUserPost")
	public String updateUserPost(@RequestParam int id, @RequestParam String username, @RequestParam String password,
			@RequestParam String role, HttpSession session, RedirectAttributes redirectAttribute) throws NoSuchAlgorithmException {
		Auth user = new Auth();
		user.setId(id);
		user.setUsername(username);
		user.setPassword(Password.encrypt(password));
		user.setRole(role);

		System.out.println(user);

		Auth dbstatus = this.authRepo.save(user);

		System.out.println(dbstatus);

		if (dbstatus != null) {
			redirectAttribute.addFlashAttribute("success", "User updated successfully!");
			return "redirect:/profile";
		} else {
			redirectAttribute.addFlashAttribute("error", "Failed to update user!");
			return "redirect:/profile";
		}

	}
	
	@GetMapping("deleteUserPost")
	public String deleteUserPost(@RequestParam int id, HttpSession session, RedirectAttributes redirectAttribute) {
		
		this.authRepo.deleteById(Integer.valueOf(id));
		
		boolean existStatus = this.authRepo.existsById(Integer.valueOf(id));
		if(!existStatus) {
			redirectAttribute.addFlashAttribute("success", "User deleted successfully!");
			return "redirect:/profile";
		}else {
			redirectAttribute.addFlashAttribute("error", "Failed to delete user!");
			return "redirect:/profile";
		}
	}

	private boolean validateSession(HttpSession session) {
		if (session.getAttribute("activeStatus") == null || !(boolean) session.getAttribute("activeStatus")) {
			return false;
		}
		return true;
	}

//chat with user
	@GetMapping("/chat")
	public String chat(@RequestParam("user") int receiverUser, Model model, HttpSession session) {

		if (!this.validateSession(session)) {
			return "redirect:/login";
		}

		String sessionUsername = (String)session.getAttribute("username"); 
		model.addAttribute("sessionUsername", sessionUsername);
		
		Optional<Auth> chatUserOption = this.authRepo.findById(Integer.valueOf(receiverUser));
		if (chatUserOption.isPresent()) {
		    model.addAttribute("title_name", chatUserOption.get().getUsername());
		    
		} else {
		    // Handle case if user not found (optional)
		    model.addAttribute("title_name", "Dashboard");
		}

		int thisUserId = (int) session.getAttribute("userid");
		

		ArrayList<Message> messages = (ArrayList<Message>) this.messageRepository.getMessagesBySenderReceiver(thisUserId, receiverUser);

	    model.addAttribute("senderUserId", thisUserId);
	    model.addAttribute("receiverUserId", receiverUser);
	    
	    
	    
	    System.out.println(messages);

	    model.addAttribute("messages", messages);

		List<Auth> users = authRepo.findAll();
		model.addAttribute("users", users);
		
		return "chat";
	}	// function ends
	
	
//	function to post message
	@PostMapping("messagePost")
	public String messagePost(@RequestParam("message") String messageText, @RequestParam int senderUserId, @RequestParam int receiverUserId, HttpSession session, RedirectAttributes redirectAttribute) {
		
		Message message = new Message();
		message.setMessage(messageText);
		message.setsenderUser(this.authRepo.findById(senderUserId).get());
		message.setreceiverUser(this.authRepo.findById(receiverUserId).get());
		message.setRecord_timestamp(new java.sql.Timestamp(System.currentTimeMillis()));

		Message dbStatus = this.messageRepository.save(message);
		
		if(dbStatus != null) {
			redirectAttribute.addFlashAttribute("success", "Message Sent");
			return "redirect:/chat?user=" + receiverUserId;
		}else {
			redirectAttribute.addFlashAttribute("error", "Failed to send message");
			return "redirect:/chat?user=" + receiverUserId;
		}
	}
}
