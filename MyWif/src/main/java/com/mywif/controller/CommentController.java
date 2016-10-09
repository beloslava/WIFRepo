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

	@RequestMapping(value = "/commentlike", method = RequestMethod.GET)
	protected String likeComment(@RequestParam("commentId") String commentId, @RequestParam("postId") String postId,
			@RequestParam("email") String email, HttpServletRequest request) {
		request.setAttribute(commentId, Integer.parseInt(commentId));
		request.setAttribute(postId, Integer.parseInt(postId));

		email = request.getSession().getAttribute("USER").toString();
		CommentDAO.getInstance().likeComment(Integer.parseInt(commentId), email);

		return "details?postId=" + postId;

	}
	
	
	@RequestMapping(value = "/commentwrite", method = RequestMethod.GET)
	protected String writeComment(@RequestParam("emailUser") String emailUser, @RequestParam("comment") String comment,
			 HttpServletRequest request, @RequestParam("parentCommentId") String parentCommentId,  @RequestParam("postId") String postId) {
	
//		parentCommentId=null;
//		if(!parentCommentId.equals("parent")){
//			request.setAttribute("parentCommentId", Integer.parseInt(parentCommentId));
//		}
		
		if(comment!=null){
			CommentDAO.getInstance().addComment(Integer.parseInt(postId), emailUser, Integer.parseInt(parentCommentId), comment, Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>(),new HashSet<>());
			
		}
		return "details?postId=" + postId;

	}

}
