package com.mywif.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;

@Controller
public class DeleteController {

	@RequestMapping(value = "/deletepost", method = RequestMethod.POST)
	public String deletePost(@RequestParam(value = "postId") String postId, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			System.out.println(Integer.parseInt(postId));
			System.out.println(Integer.parseInt(postId) + session.getAttribute("USER").toString());
			try {
				PostDAO.getInstance().deletePost(Integer.parseInt(postId));
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "main";
		} else {
			return "index";
		}
	}
}
