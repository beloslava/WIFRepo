package com.mywif.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.db.PostDAO;
import com.mywif.model.db.UserDAO;
import com.mywif.model.pojo.Album;
import com.mywif.model.pojo.UsersManager;

@Controller
@MultipartConfig
@SessionAttributes({"animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts", "uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts"})
public class UserController {

	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		try {
			if (!UsersManager.getInstance().validLogin(email, password)) {
				return "indecc";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("allPosts", PostDAO.getInstance().getAllPosts().values());
		session.setAttribute("USER", email);
		model.addAttribute("USER", email);
		System.out.println("hi");
		
		model.addAttribute("animalsPosts", PostDAO.getInstance().getAllPostsByCategory("animals").size());
		model.addAttribute("abstractPosts", PostDAO.getInstance().getAllPostsByCategory("abstract").size());
		model.addAttribute("foodPosts", PostDAO.getInstance().getAllPostsByCategory("food").size());
		model.addAttribute("peoplePosts", PostDAO.getInstance().getAllPostsByCategory("people").size());
		model.addAttribute("naturePosts", PostDAO.getInstance().getAllPostsByCategory("nature").size());
		model.addAttribute("urbanPosts", PostDAO.getInstance().getAllPostsByCategory("urban").size());
		model.addAttribute("uncategorizedPosts", PostDAO.getInstance().getAllPostsByCategory("uncategorized").size());
		model.addAttribute("familyPosts", PostDAO.getInstance().getAllPostsByCategory("family").size());
		model.addAttribute("sportPosts", PostDAO.getInstance().getAllPostsByCategory("sport").size());
		model.addAttribute("travelPosts", PostDAO.getInstance().getAllPostsByCategory("travel").size());
		
		return "main";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "index";
	}

	@Scope("session")
	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	protected String logOut(@RequestParam("USER") String email, HttpSession session, Model model) {
		if (session.getAttribute("USER") != null) {
			session.setAttribute("USER", null);
			session.invalidate();

		}
		// if(model.containsAttribute("USER")){
		// model.addAttribute("USER", null);
		//
		// }
		return "index";
	}

	@RequestMapping(value = "/settingschange", method = RequestMethod.POST)
	protected String changeSettings(@RequestParam("newName") String newName, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, @RequestParam("newPass2") String newPass2,
			@RequestParam("gender") String gender, @RequestParam("newDescription") String newDescription,
			HttpServletRequest request) {

		String email = request.getSession().getAttribute("USER").toString();

		System.out.println(newName);
		System.out.println(oldPass);
		System.out.println(newPass);
		System.out.println(newPass2);
		System.out.println(gender);
		System.out.println(newDescription);
		System.out.println(email);
		if (newName != null && oldPass != null && newPass != null && newPass2 != null && gender != null
				&& newDescription != null) {
			try {
				UsersManager.getInstance().changeSettings(newName, newPass, gender, newDescription, email);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return "myProfile";
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	protected String unfollow(@RequestParam("email") String emaiToFollow, HttpSession session, Model model) {
		model.addAttribute("email", emaiToFollow);
		model.addAttribute("myEmail", session.getAttribute("USER").toString());
		UsersManager.getInstance().unfollow(emaiToFollow, session.getAttribute("USER").toString());
		model.addAttribute("isFollowed", UsersManager.getInstance().isUserFollowedByUser(emaiToFollow, session.getAttribute("USER").toString()));
		return "profile";
	}

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	protected String follow(@RequestParam("email") String emaiToFollow, HttpSession session, Model model) {
		model.addAttribute("email", emaiToFollow);
		model.addAttribute("myEmail", session.getAttribute("USER").toString());
		UsersManager.getInstance().follow(emaiToFollow, session.getAttribute("USER").toString());
		model.addAttribute("isFollowed", UsersManager.getInstance().isUserFollowedByUser(emaiToFollow, session.getAttribute("USER").toString()));
		return "profile";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	protected String register(@RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("password2") String password2, @RequestParam("email") String email,
			@RequestParam("fileField") MultipartFile avatar) throws IOException {

		InputStream avatarStream = null;
		try {
			avatarStream = avatar.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		Matcher mattcher = pattern.matcher(email);
		String jsp = "";

		if (((!UsersManager.getInstance().isUserExists(email)) && mattcher.matches()) && (!email.isEmpty())
				&& (!password.isEmpty()) && (password.equals(password2)) && (!name.isEmpty())) {
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File avatarFile = new File(dir, name + "-profile-pic." + avatar.getContentType().split("/")[1]);
			System.out.println(avatarFile.getAbsolutePath());
			Files.copy(avatarStream, avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			System.out.println("Try to save file with name: " + avatarFile.getName());
			System.out.println("abs. path = " + avatarFile.getAbsolutePath());
			UsersManager.getInstance().regUser(email, password2, name, avatarFile.getName(), new HashSet<>(),
					new HashSet<>(), new TreeMap<Integer, Album>());
			jsp = "index";
		} else {
			jsp = "registerFailed";
		}

		return jsp;
	}

}
