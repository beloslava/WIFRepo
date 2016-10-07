package com.mywif.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailController {

	@RequestMapping(value="/category", method=RequestMethod.GET)
	public String category(@RequestParam("category") String category,HttpServletRequest request){
		request.setAttribute("category", category);
		return "category";
	}
	
	@RequestMapping(value="/details/album", method=RequestMethod.GET)
	public String album(@RequestParam("albumId") String albumId,HttpServletRequest request){
		request.setAttribute("albumId", Integer.parseInt(albumId));
		return "myPhotos";
	}
	
	@RequestMapping(value="/details/post", method=RequestMethod.GET)
	public String post(@RequestParam("postId") String postId,HttpServletRequest request){
		request.setAttribute("postId", Integer.parseInt(postId));
		return "detailsPost";
	}
	
	@RequestMapping(value="/details/profile", method=RequestMethod.GET)
	public String profile(@RequestParam("email") String email,HttpServletRequest request){
		request.setAttribute("email", email);
		String jsp="";
		if(request.getSession().getAttribute("USER").toString().equals(email)){
			jsp="myProfile";
		}
		else{
			jsp="profile";
		}
		return jsp;
	}
}
