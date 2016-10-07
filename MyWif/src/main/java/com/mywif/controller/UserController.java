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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.pojo.Album;
import com.mywif.model.pojo.UsersManager;

@Controller
@MultipartConfig
public class UserController {
	
	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String logIn(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
		try {
			if (!UsersManager.getInstance().validLogin(email, password)) {
				return "indecc";
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	model.addAttribute("user", email);
		System.out.println("hi");
		return "main";
	}
	
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {

		return "index";
	}


	@Scope("session")
	@RequestMapping(value = "/logOut", method = RequestMethod.POST)
	protected String logOut(@RequestParam("email") String email, HttpSession session) {
		if (session.getAttribute("USER") != null) {
			session.setAttribute("USER", null);
			session.invalidate();
		}
		return "index";
	}

	@RequestMapping(value = "/changeSettings", method = RequestMethod.POST)
	protected String changeSettings(@RequestParam("newName") String newName, @RequestParam("oldPass") String oldPass,
			@RequestParam("newPass") String newPass, @RequestParam("newPass2") String newPass2, @RequestParam("gender") String gender,
			@RequestParam("newDescription") String newDescription, HttpServletRequest request) {

		String email=request.getSession().getAttribute("USER").toString();
		 
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
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	protected String follow(@RequestParam("emaiToFollow") String emaiToFollow, @RequestParam("USER") String myEmail)  {
		
		return "details/profile?email="+emaiToFollow;
	}

	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	protected String register(@RequestParam("name") String name, @RequestParam("password") String password, @RequestParam("password2") String password2,
			@RequestParam("email") String email, @RequestParam("fileField") MultipartFile avatar) {
		
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
		
		if ( ((!UsersManager.getInstance().isUserExists(email)) && mattcher.matches()) && (!email.isEmpty()) && (!password.isEmpty()) && (password.equals(password2))
				&& (!name.isEmpty())) {
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File avatarFile=new File(dir, name + "-profile-pic." + avatar.getContentType().split("/")[1]);
			System.out.println(avatarFile.getAbsolutePath());
			try {
				Files.copy(avatarStream, avatarFile.toPath(),StandardCopyOption.REPLACE_EXISTING );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Try to save file with name: " + avatarFile.getName());
			System.out.println("abs. path = " + avatarFile.getAbsolutePath());
			UsersManager.getInstance().regUser(email, password2, name, avatarFile.getName(),
					new HashSet<>(),new HashSet<>(), 
					new TreeMap<Integer, Album>());
			jsp="index";
		}
		else {
			jsp="registerFailed";
		}
		
		return jsp;
	}


}
