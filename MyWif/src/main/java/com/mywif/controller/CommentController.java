package com.mywif.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mywif.model.db.CommentDAO;
import com.mywif.model.db.PostDAO;
import com.mywif.model.pojo.Post;
import com.mywif.model.pojo.UsersManager;

@Controller
public class CommentController {

	@RequestMapping(value = "/commentlike", method = RequestMethod.GET)
	protected String likeComment(@RequestParam("commentId") String commentId, @RequestParam("postId") String postId,
			@RequestParam("email") String email, HttpServletRequest request,Model model,HttpSession session) {
		model.addAttribute("commentId", Integer.parseInt(commentId));
		model.addAttribute("postId", Integer.parseInt(postId));
		email = session.getAttribute("USER").toString();
		CommentDAO.getInstance().likeComment(Integer.parseInt(commentId), email);
		model.addAttribute("postId", postId);
		return "detailsPost";
	}
	
	
	@RequestMapping(value = "/commentwrite", method = RequestMethod.POST)
	protected String writeComment(@RequestParam("email") String email, @RequestParam("comment") String comment,
			  @RequestParam("parentCommentId") String parentCommentId,  @RequestParam("postId") String postId,Model model) {
		Integer parentId=null;
		if(!parentCommentId.equals("parent")){
			parentId=Integer.parseInt(parentCommentId);
		}
		if(comment!=null){
			CommentDAO.getInstance().addComment(Integer.parseInt(postId), email, parentId, comment, Timestamp.valueOf(LocalDateTime.now()),new ArrayList<>(),new HashSet<>());
		}
		Post post=PostDAO.getInstance().getPost(Integer.parseInt(postId));
		model.addAttribute("postId", postId);
		model.addAttribute("post",post);
		model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
		model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
		return "detailsPost";
	}

}
