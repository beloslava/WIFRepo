package com.mywif.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Post;
import com.mywif.model.pojo.UsersManager;

import com.mywif.model.db.AlbumDAO;
import com.mywif.model.db.CommentDAO;

@Controller
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
	"uncategorizedPosts", "familyPosts" })
public class AlbumController {

	@RequestMapping(value = "/createalbum", method = RequestMethod.POST)
	public String createAlbum(@RequestParam(value = "name") String name, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			String email = session.getAttribute("USER").toString();
			try {
				AlbumDAO.getInstance().addAlbum(name, email, Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>());
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "myAlbums";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/createpost", method = RequestMethod.POST)
	public String createPost(@RequestParam(value = "category") String category,
			@RequestParam(value = "nameOfPost") String nameOfPost, @RequestParam(value = "keyWords") String keyWords,
			@RequestParam(value = "email") String email, @RequestParam("fileField") MultipartFile picture,
			@RequestParam("albumId") int albumId, Model model, HttpServletRequest request) throws IOException {
		if (UserController.isUserInSession(request)) {
			category = category.toLowerCase();
			InputStream pictureStream = picture.getInputStream();
			File dir = new File(
					"D:\\MyWifPictures\\userPostPics" + UsersManager.getInstance().getUser(email).getName());
			if (!dir.exists()) {
				dir.mkdir();
			}
			File pictureFile = new File(dir,
					UsersManager.getInstance().getUser(email).getName()
							+ LocalDateTime.now().toString().replaceAll(":", "") + "-post-pic."
							+ picture.getContentType().split("/")[1]);
			Files.copy(pictureStream, pictureFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			try {
				PostDAO.getInstance().addPost(email, albumId, category, pictureFile.getName(), nameOfPost, keyWords,
						Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>(), new HashSet<>(), new HashSet<>());
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			model.addAttribute(category + "Posts", PostDAO.getInstance().getAllPostsByCategory(category).size());
			model.addAttribute("albumId",albumId);
			return "myPhotos";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/postlike", method = RequestMethod.POST)
	public String likePost(@RequestParam(value = "postId") String postId, Model model, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			System.out.println(postId);
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			String email = session.getAttribute("USER").toString();
			try {
				PostDAO.getInstance().likePost(Integer.parseInt(postId), email);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "detailsPost";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/postdislike", method = RequestMethod.POST)
	public String dislikePost(@RequestParam(value = "postId") String postId, Model model, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			String email = session.getAttribute("USER").toString();
			try {
				PostDAO.getInstance().dislikePost(Integer.parseInt(postId), email);
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "detailsPost";
		} else {
			return "index";
		}

	}
	
	@RequestMapping(value = "/postunlike", method = RequestMethod.POST)
	public String unlikePost(@RequestParam(value = "postId") String postId, Model model, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			System.out.println(postId);
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			String email = session.getAttribute("USER").toString();
			try {
				PostDAO.getInstance().unlikePost(Integer.parseInt(postId), email);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "detailsPost";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/postundislike", method = RequestMethod.POST)
	public String undislikePost(@RequestParam(value = "postId") String postId, Model model, HttpSession session,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			Post post = PostDAO.getInstance().getPost(Integer.parseInt(postId));
			model.addAttribute("postId", postId);
			model.addAttribute("post", post);
			model.addAttribute("postUser", UsersManager.getInstance().getUser(post.getUserEmail()));
			model.addAttribute("comments", CommentDAO.getInstance().takeAllCommentsByPost(post.getId()));
			String email = session.getAttribute("USER").toString();
			try {
				PostDAO.getInstance().undislikePost(Integer.parseInt(postId), email);
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "detailsPost";
		} else {
			return "index";
		}

	}

}
