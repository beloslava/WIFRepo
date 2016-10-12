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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mywif.model.db.CommentDAO;
import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Post;
import com.mywif.model.pojo.UsersManager;

@Controller
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
	"uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts","allPosts","USER" })
public class CommentController {

	@RequestMapping(value = "/commentlike", method = RequestMethod.POST)
	protected String likeComment(@RequestParam("commentId") String commentId, @RequestParam("postId") String postId,
			Model model, HttpSession session, HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			String email = session.getAttribute("USER").toString();
			try {
				CommentDAO.getInstance().likeComment(Integer.parseInt(commentId), email);
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "detailsPost";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/commentwrite", method = RequestMethod.POST)
	protected String writeComment(@RequestParam("email") String email, @RequestParam("comment") String comment,
			@RequestParam("parentCommentId") String parentCommentId, @RequestParam("postId") String postId, Model model,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			Integer parentId = null;
			if (!parentCommentId.equals("parent")) {
				parentId = Integer.parseInt(parentCommentId);
			}
			if (comment != null) {
				try {
					CommentDAO.getInstance().addComment(Integer.parseInt(postId), email, parentId, comment,
							Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>(), new HashSet<>());
				} catch (DBException e) {
					System.out.println(DBException.ERROR_MESSAGE);
					e.printStackTrace();
				}
			}
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			return "detailsPost";
		} else {
			return "index";
		}
	}

}
