package com.mywif.controller;

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
public class DetailController {

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String category(@RequestParam("category") String category, Model model, HttpServletRequest request) {
			model.addAttribute("category", category);
			return "category";
	}

	@RequestMapping(value = "/detailsalbum", method = RequestMethod.GET)
	public String album(@RequestParam("albumId") String albumId, Model model, HttpServletRequest request) {
			model.addAttribute("albumId", Integer.parseInt(albumId));
			return "myPhotos";
	}

	@RequestMapping(value = "/detailspost", method = RequestMethod.GET)
	public String post(@RequestParam("postId") String postId, HttpServletRequest request, Model model) {
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId",postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			return "detailsPost";
	}

	@RequestMapping(value = "/detailsprofile", method = RequestMethod.GET)
	public String profile(@RequestParam("email") String email, Model model, HttpSession session,
			HttpServletRequest request) {
			model.addAttribute("email", email);
			String jsp = "";
			if (session.getAttribute("USER").toString().equals(email)) {
				jsp = "myProfile";
			} else {
				jsp = "profile";
			}
			return jsp;
		}

	@RequestMapping(value = "/topTen", method = RequestMethod.GET)
	public String viewTopTen(HttpServletRequest request) {
			return "topTen";
	}

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String viewMain(Model model, HttpServletRequest request) {
			System.out.println(PostDAO.getInstance().getAllPosts().values().size());
			model.addAttribute("allPosts", PostDAO.getInstance().getAllPosts().values());
			return "main";
	}

	@RequestMapping(value = "/myProfile", method = RequestMethod.GET)
	public String viewMyProfile(HttpServletRequest request) {
			return "myProfile";
	}

	@RequestMapping(value = "/myAlbums", method = RequestMethod.GET)
	public String viewMyAlbums(HttpServletRequest request) {
			return "myAlbums";
	}
	
	@RequestMapping(value = "/myFollowedPosts", method = RequestMethod.GET)
	public String viewFollowedPosts(HttpServletRequest request) {
			return "myFollowedPosts";
	}
}
