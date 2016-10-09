package com.mywif.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mywif.model.pojo.Searchable;
import com.mywif.model.pojo.UsersManager;

import com.mywif.model.db.PostDAO;
import com.mywif.model.pojo.Post;
import com.mywif.model.pojo.User;
@Controller
public class PictureController {
	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	@RequestMapping(value="/picture/profile", method=RequestMethod.GET)
	public void profile(@RequestParam (value="email") String email, HttpSession session,HttpServletResponse response){
		if(email != null){
			User user = UsersManager.getInstance().getUser(email);
			try {
				returnProfilePic(user, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String logged = (String) session.getAttribute("USER");
		if(logged == null){//session is new or expired
			System.out.println("This should not happen right now. Might happen later on other pages");
		}
		else{
			User user = UsersManager.getInstance().getUser(logged);
			try {
				returnProfilePic(user, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public static void returnProfilePic(User u,  HttpServletResponse response) throws IOException{
		File profilePicFile = new File("D:\\MyWifPictures\\userProfilePics", u.getAvatarPath());
		response.setContentLength((int)profilePicFile.length());
		String contentType = "image/"+profilePicFile.getName().split("[.]")[profilePicFile.getName().split("[.]").length-1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(profilePicFile.toPath(), out);
	}
	@RequestMapping(value="/picture/post", method=RequestMethod.GET)
	public String post(@RequestParam (value="postId") String postId,HttpServletResponse response){
		if (PostDAO.getInstance().getAllPosts().containsKey(Integer.parseInt(postId))) {
			Post post = PostDAO.getInstance().getAllPosts().get(Integer.parseInt(postId));
			try {
				returnPic(post, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static void returnPic(Post post, HttpServletResponse response) throws IOException {
		String email = post.getUserEmail();
		User user = UsersManager.getInstance().getUser(email);
		File picFile = new File("D:\\MyWifPictures\\userPostPics" + user.getName(), post.getPicture());
		response.setContentLength((int) picFile.length());
		String contentType = "image/" + picFile.getName().split("[.]")[picFile.getName().split("[.]").length-1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(picFile.toPath(), out);
	}


	
	@RequestMapping(value="/picture/change", method=RequestMethod.POST)
	public String change(HttpSession session,@RequestParam(value="fileField") MultipartFile picture){
		String email = session.getAttribute("USER").toString();
		String name=UsersManager.getInstance().getUser(email).getName();
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
		File pictureFile=new File(dir, name + "-profile-pic." + picture.getContentType().split("/")[1]);
		System.out.println(pictureFile.getAbsolutePath());
		try {
			Files.copy(pictureStream, pictureFile.toPath(),StandardCopyOption.REPLACE_EXISTING );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			try {
				UsersManager.getInstance().changeAvatar(pictureFile.getName(), email);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return "myProfile";
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(@RequestParam("input") String input,@RequestParam("type") String type,HttpServletRequest request){
		ArrayList<Searchable> searchResult = (ArrayList<Searchable>) UsersManager.getInstance().search(input, type);
		request.setAttribute("search", searchResult);
		request.setAttribute("input", input);
		request.setAttribute("count", searchResult.size());
		request.setAttribute("type", type);
		System.out.println(searchResult.size());
		return "search";
	}
}
