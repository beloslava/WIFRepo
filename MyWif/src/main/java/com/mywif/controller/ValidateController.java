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
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.db.PostDAO;
import com.mywif.model.exception.DBException;
import com.mywif.model.pojo.Album;
import com.mywif.model.pojo.User;
import com.mywif.model.pojo.UsersManager;

@RestController
@MultipartConfig
@SessionAttributes({ "animalsPosts", "abstractPosts", "foodPosts", "peoplePosts", "naturePosts", "urbanPosts",
		"uncategorizedPosts", "familyPosts", "sportPosts", "travelPosts" })
public class ValidateController {
	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	@RequestMapping(value = "/loginValidate", method = RequestMethod.POST)
	public String loginValidate(@RequestParam("email") String email, @RequestParam("password") String password,
			HttpSession session, Model model) {

		String message = "";
		try {
			if (UsersManager.getInstance().validLogin(email, password)) {
				session.setAttribute("USER", email);
				model.addAttribute("allPosts", PostDAO.getInstance().getAllPosts().values());
				model.addAttribute("animalsPosts", PostDAO.getInstance().getAllPostsByCategory("animals").size());
				model.addAttribute("abstractPosts", PostDAO.getInstance().getAllPostsByCategory("abstract").size());
				model.addAttribute("foodPosts", PostDAO.getInstance().getAllPostsByCategory("food").size());
				model.addAttribute("peoplePosts", PostDAO.getInstance().getAllPostsByCategory("people").size());
				model.addAttribute("naturePosts", PostDAO.getInstance().getAllPostsByCategory("nature").size());
				model.addAttribute("urbanPosts", PostDAO.getInstance().getAllPostsByCategory("urban").size());
				model.addAttribute("uncategorizedPosts",
						PostDAO.getInstance().getAllPostsByCategory("uncategorized").size());
				model.addAttribute("familyPosts", PostDAO.getInstance().getAllPostsByCategory("family").size());
				model.addAttribute("sportPosts", PostDAO.getInstance().getAllPostsByCategory("sport").size());
				model.addAttribute("travelPosts", PostDAO.getInstance().getAllPostsByCategory("travel").size());
				message = "success";
			} else {
				message = "LoginFailed";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return message;

	}

	@RequestMapping(value = "/registerValidate", method = RequestMethod.POST)
	protected String register(@RequestParam("name") String name, @RequestParam("password") String password,
			@RequestParam("password2") String password2, @RequestParam("email") String email,
			@RequestParam("fileField") MultipartFile avatar) throws IOException {
		String message = "";
		InputStream avatarStream = null;
		try {
			avatarStream = avatar.getInputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile( "[a-z0-9!#$%&'*+/=?^_`{|}~.-]+@[a-z0-9-]+(\\.[a-z0-9-]+)*");
		Matcher mattcher = pattern.matcher(email);
		if (((!UsersManager.getInstance().isUserExists(email)) && mattcher.matches()) && (!email.isEmpty())
				&& (!password.isEmpty()) && (password.equals(password2) && User.isPaswordStrong(password))
				&& (!name.isEmpty()) && (name.trim().length() >= 3)) {
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File avatarFile = new File(dir, email + "-profile-pic." + avatar.getContentType().split("/")[1]);
			Files.copy(avatarStream, avatarFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			try {
				UsersManager.getInstance().regUser(email, password2, name, avatarFile.getName(), new HashSet<>(),
						new HashSet<>(), new TreeMap<Integer, Album>());
			} catch (DBException e) {
				System.out.println(DBException.ERROR_MESSAGE);
				e.printStackTrace();
			}
			message = "success";
		} else {
			message = "RegisterFailed";
		}

		return message;
	}
}
