package com.mywif.controller;

import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywif.model.pojo.Searchable;
import com.mywif.model.pojo.UsersManager;

public class PictureController {

	@RequestMapping(value="/picture/profile", method=RequestMethod.GET)
	public String profile(){
		return null;
	}
	
	@RequestMapping(value="/picture/post", method=RequestMethod.GET)
	public String post(){
		return null;
	}
	
	@RequestMapping(value="/picture/change", method=RequestMethod.GET)
	public String change(){
		return null;
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@RequestParam("input") String input,@RequestParam("type") String type,HttpServletRequest request){
		ArrayList<Searchable> search = (ArrayList<Searchable>) UsersManager.getInstance().search(input, type);
		request.setAttribute("search", search);
		request.setAttribute("input", input);
		request.setAttribute("count", search.size());
		request.setAttribute("type", type);
		return "search";
	}
}
