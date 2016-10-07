package com.mywif.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywif.model.pojo.UsersManager;

@Controller
public class proba {

	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String welcome(@RequestParam("email") String email,@RequestParam("password") String password){
		try {
			if (!UsersManager.getInstance().validLogin(email, password)) {
			return "indecc";
}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hi");
		return "main";
	}
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String welcome2(){
		
		return "index";
	}
}
