package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.db.IPostDAO;
import model.db.PostDAO;
import model.pojo.Post;
import model.pojo.UsersManager;

/**
 * Servlet implementation class UploadPost
 */
@WebServlet("/UploadPost")
public class UploadPostServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email=(String) request.getSession().getAttribute("USER");
		String tag=request.getParameter("tag");
		int like=0;
		int dislike=0;
		Part picture = request.getPart("file");
		InputStream pictureStream = picture.getInputStream();
		if(tag.equalsIgnoreCase("people")||tag.equalsIgnoreCase("fun")||tag.equalsIgnoreCase("pets")||tag.equalsIgnoreCase("nature")||tag.equalsIgnoreCase("food and drinks")){
			File dir = new File(UsersManager.getInstance().getUser(email).getName()+"userPostPics");
			if (!dir.exists()) {
				dir.mkdir();
			}
			File pictureFile=new File(dir, UsersManager.getInstance().getUser(email).getName()+UsersManager.getInstance().getUser(email).getPosts().size() + "-post-pic." + picture.getContentType().split("/")[1]);
			if(!pictureFile.exists()){
				pictureFile.createNewFile();
			}			
			Files.copy(pictureStream, pictureFile.toPath());
			PostDAO.getInstance().addPost(email, tag, pictureFile.getName(), like, dislike, Timestamp.valueOf(LocalDateTime.now()));
		}
	}

}
