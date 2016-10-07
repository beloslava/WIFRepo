package com.mywif.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywif.model.db.CommentDAO;

@Controller
public class CommentController {

	@RequestMapping(value = "/likeComment", method = RequestMethod.GET)
	protected String likeComment(@RequestParam String commentId, @RequestParam String postId,
			@RequestParam String email, HttpServletRequest request) {
		request.setAttribute(commentId, Integer.parseInt(commentId));
		request.setAttribute(postId, Integer.parseInt(postId));

		email = request.getSession().getAttribute("USER").toString();
		CommentDAO.getInstance().likeComment(Integer.parseInt(commentId), email);

		return "details?postId=" + postId;

	}
	
	
	@RequestMapping(value = "/writeComment", method = RequestMethod.GET)
	protected String writeComment(@RequestParam String emailUser, @RequestParam String comment,
			 HttpServletRequest request, @RequestParam String parentCommentId,  @RequestParam String postId) {
	
		parentCommentId=null;
		if(!parentCommentId.equals("parent")){
			request.setAttribute("parentCommentId", Integer.parseInt(parentCommentId));
		}
		
		if(comment!=null){
			CommentDAO.getInstance().addComment(Integer.parseInt(postId), emailUser, Integer.parseInt(parentCommentId), comment, Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>(),new HashSet<>());
			
		}
		return "details?postId=" + postId;

	}

}
