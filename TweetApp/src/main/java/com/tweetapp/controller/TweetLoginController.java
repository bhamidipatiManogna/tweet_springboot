package com.tweetapp.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.model.Users;
import com.tweetapp.service.TweetService;
//import com.tweetapp.kafka.Producer;
@RestController
@CrossOrigin
@RequestMapping({"/tweets"})
public class TweetLoginController {



	TweetService tweetService=new TweetService();
	
	@GetMapping(path = {"/login"})
	public Users isUserExist(@RequestHeader String userName, @RequestHeader("password")String password)
	{
			Users user=this.tweetService.loginUser(userName, password);
			return user;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public boolean registerUser(@RequestBody Users newUser)
	{
		int res=this.tweetService.registerUser(newUser);
		if(res==1)
		{
			return true;
		}
		return false;
	}
	
	@GetMapping("/{userName}/forgot")
	public boolean updateUserPassword(@PathVariable("userName")String userName, @RequestHeader("password")String newPassword,  @RequestHeader("email")String email)
	{
		if(this.tweetService.updatePassword(userName, newPassword, email)==1)
		{
			return true;
		}
		return false;
	}
	
	@GetMapping("/users/all")
	public List<Users> getAllUsers()
	{
		return this.tweetService.getAllUsers();
	}
//	@Autowired
//	
//    TweetLoginController(Producer producer) {
//    this.producer= producer;
//    }
//
//@GetMapping("/publish")
//public String sendMessage(@PathVariable String message) {
//	this.producer.sendMessage(message);
//	return "connection established";
//}
	
//	@GetMapping(path= {"/{loginId}/forgot"})
//	public String resetStatus(@RequestHeader("password")String password, @PathVariable("loginId")String email) {
//		return this.tweetService.resetPasswordStatus(password, email);
//	}
}
