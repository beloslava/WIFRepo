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

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
//import org.apache.commons.lang.StringEscapeUtils;

import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Album;
import com.mywif.model.pojo.User;
import com.mywif.model.pojo.UsersManager;

@Controller
@MultipartConfig
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
		"uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts" })
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
			System.out.println("Something went wrong with crypting pass!");
			e.printStackTrace();
		}
		model.addAttribute("allPosts", PostDAO.getInstance().getAllPosts().values());
		session.setAttribute("USER", email);

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

	@RequestMapping(value = "/logOut", method = RequestMethod.GET)
	protected String logOut(HttpSession session, Model model) {
		if (session.getAttribute("USER") != null) {
			session.setAttribute("USER", null);
			session.invalidate();
		}
		return "index";
	}

	@RequestMapping(value = "/settingschange", method = RequestMethod.POST)
	protected String changeSettings(@RequestParam("newName") String newName, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, @RequestParam("newPass2") String newPass2,
			@RequestParam("gender") String gender, @RequestParam("newDescription") String newDescription,
			HttpServletRequest request) {
		String email = request.getSession().getAttribute("USER").toString();
		String description = StringEscapeUtils.escapeHtml4(newDescription);
		if (isUserInSession(request)) {
			if (newName != null && (!newName.isEmpty()) && oldPass != null && (!oldPass.isEmpty()) && newPass != null
					&& (!newPass.isEmpty()) && newPass2 != null && (!newPass2.isEmpty()) && newPass.equals(newPass2)
					&& gender != null && description != null) {
				try {
					UsersManager.getInstance().changeSettings(newName, newPass, gender, description, email);
				} catch (UnsupportedEncodingException | DBException e) {
					e.printStackTrace();
				}

			}
			return "myProfile";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.POST)
	protected String unfollow(@RequestParam("email") String emaiToFollow, HttpSession session, Model model,
			HttpServletRequest request) {
		if (isUserInSession(request)) {
			model.addAttribute("email", emaiToFollow);
			model.addAttribute("myEmail", session.getAttribute("USER").toString());
			try {
				UsersManager.getInstance().unfollow(emaiToFollow, session.getAttribute("USER").toString());
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			return "profile";
		} else {
			return "index";
		}
	}

	@RequestMapping(value = "/follow", method = RequestMethod.POST)
	protected String follow(@RequestParam("email") String emaiToFollow, HttpSession session, Model model,
			HttpServletRequest request) {
		if (isUserInSession(request)) {
			model.addAttribute("email", emaiToFollow);
			model.addAttribute("myEmail", session.getAttribute("USER").toString());
			try {
				UsersManager.getInstance().follow(emaiToFollow, session.getAttribute("USER").toString());
				SendMail sendMail = new SendMail();
				sendMail.setFollowedUserEmail(emaiToFollow);
				sendMail.setFollowerUserEmail(session.getAttribute("USER").toString());
				sendMail.start();
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}

			return "profile";
		} else {
			return "index";
		}
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
				&& (!password.isEmpty()) && (password.equals(password2) && User.isPaswordStrong(password))
				&& (!name.isEmpty()) && (name.trim().length() >= 3)) {
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File avatarFile = new File(dir, name + "-profile-pic." + avatar.getContentType().split("/")[1]);
			Files.copy(avatarStream, avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			try {
				UsersManager.getInstance().regUser(email, password2, name, avatarFile.getName(), new HashSet<>(),
						new HashSet<>(), new TreeMap<Integer, Album>());
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			jsp = "index";
		} else {
			jsp = "index";
		}

		return jsp;
	}

	public static boolean isUserInSession(HttpServletRequest request) {
		return request.getSession().getAttribute("USER") != null;
	}

}
