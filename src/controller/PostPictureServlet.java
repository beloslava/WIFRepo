package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.PostDAO;
import model.pojo.Post;
import model.pojo.User;
import model.pojo.UsersManager;

@WebServlet("/PostPictureServlet")
public class PostPictureServlet extends HttpServlet {
	public static void returnPic(Post post,  HttpServletResponse response) throws IOException{
		String email=post.getUserEmail();
		User user=UsersManager.getInstance().getUser(email);
		File picFile = new File("D:\\MyWifPictures\\userPostPics"+user.getName(), post.getPicture());
		response.setContentLength((int)picFile.length());
		String contentType = "image/"+picFile.getName().split("[.]")[picFile.getName().split("[.]").length-1];
		response.setContentType(contentType);
		OutputStream out = response.getOutputStream();
		Files.copy(picFile.toPath(), out);
	}
	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId=Integer.parseInt(request.getParameter("postId"));
		if(PostDAO.getInstance().getAllPosts().containsKey(postId)){
			Post post=PostDAO.getInstance().getAllPosts().get(postId);
			returnPic(post, response);
		}
		
		String logged = (String) request.getSession().getAttribute("USER");
		if(logged == null){//session is new or expired
			System.out.println("This should not happen right now. Might happen later on other pages");
		}
		else{
			Post post=PostDAO.getInstance().getPost(postId);
			returnPic(post, response);
			
		}
	}
			
			
	}

