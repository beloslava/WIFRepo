package com.mywif.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.pojo.Searchable;
import com.mywif.model.pojo.UsersManager;

import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Post;
import com.mywif.model.pojo.User;

@Controller
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
	"uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts" })
public class PictureController {
	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	@RequestMapping(value = "/pictureprofile", method = RequestMethod.GET)
	public void profile(@RequestParam(value = "email") String email, HttpSession session, HttpServletResponse response)
			throws IOException {
		if (email != null) {
			User user = UsersManager.getInstance().getUser(email);
			returnProfilePic(user, response);
		}
		String logged = (String) session.getAttribute("USER");
		if (logged == null) {// session is new or expired
			System.out.println("This should not happen right now. Might happen later on other pages");
		} else {
			User user = UsersManager.getInstance().getUser(logged);
			returnProfilePic(user, response);
		}
	}

	public static void returnProfilePic(User u, HttpServletResponse response) throws IOException {
		File profilePicFile = new File("D:\\MyWifPictures\\userProfilePics", u.getAvatarPath());
		response.setContentLength((int) profilePicFile.length());
		String contentType = "image/"
				+ profilePicFile.getName().split("[.]")[profilePicFile.getName().split("[.]").length - 1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(profilePicFile.toPath(), out);
	}

	@RequestMapping(value = "/picturepost", method = RequestMethod.GET)
	public void post(@RequestParam(value = "postId") String postId, HttpServletResponse response) throws IOException {
		if (PostDAO.getInstance().getAllPosts().containsKey(Integer.parseInt(postId))) {
			Post post = PostDAO.getInstance().getAllPosts().get(Integer.parseInt(postId));
			returnPic(post, response);
		}
	}

	public static void returnPic(Post post, HttpServletResponse response) throws IOException {
		String email = post.getUserEmail();
		User user = UsersManager.getInstance().getUser(email);
		File picFile = new File("D:\\MyWifPictures\\userPostPics" + user.getName(), post.getPicture());
		response.setContentLength((int) picFile.length());
		String contentType = "image/" + picFile.getName().split("[.]")[picFile.getName().split("[.]").length - 1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(picFile.toPath(), out);
	}

	@RequestMapping(value = "/picturechange", method = RequestMethod.POST)
	public String change(HttpSession session, @RequestParam(value = "fileField") MultipartFile picture,
			HttpServletRequest request) throws IOException {
		if (UserController.isUserInSession(request)) {
			String email = session.getAttribute("USER").toString();
			String name = UsersManager.getInstance().getUser(email).getName();
			InputStream pictureStream = null;
			try {
				pictureStream = picture.getInputStream();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			System.out.println(picture.getContentType());
			File pictureFile = new File(dir, name + "-profile-pic." + picture.getContentType().split("/")[1]);
			System.out.println(pictureFile.getAbsolutePath());
			Files.copy(pictureStream, pictureFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			try {
				UsersManager.getInstance().changeAvatar(pictureFile.getName(), email);
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "myProfile";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(@RequestParam("input") String input, @RequestParam("type") String type, Model model,
			HttpServletRequest request) {
		if (UserController.isUserInSession(request)) {
			ArrayList<Searchable> searchResult = (ArrayList<Searchable>) UsersManager.getInstance().search(input, type);
			model.addAttribute("search", searchResult);
			model.addAttribute("input", input);
			model.addAttribute("count", searchResult.size());
			model.addAttribute("type", type);
			System.out.println(searchResult.size());
			return "search";
		} else {
			return "index";
		}
	}
}
