package controller;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.db.IPostDAO;
import model.db.PostDAO;
import model.pojo.Comment;
import model.pojo.Post;
import model.pojo.UsersManager;

@WebServlet("/UploadPostServlet")
@MultipartConfig
public class UploadPostServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String html = "";
		String email = request.getSession().getAttribute("USER").toString();
		String category = request.getParameter("category");
		category=category.toLowerCase();
		String nameOfPost=request.getParameter("nameOfPost");
		String keyWords=request.getParameter("keyWords");
		Part picture = request.getPart("fileField");
		int albumId=Integer.parseInt(request.getParameter("albumId"));
		InputStream pictureStream = picture.getInputStream();
		System.out.println(email);
		System.out.println(category);
		System.out.println(nameOfPost);
		System.out.println(keyWords);
		System.out.println(albumId);
		if (category!=null&&nameOfPost!=null&&keyWords!=null) {
			File dir = new File("D:\\MyWifPictures\\userPostPics"+UsersManager.getInstance().getUser(email).getName());
			if (!dir.exists()) {
				dir.mkdir();
			}
			File pictureFile = new File(dir,
					UsersManager.getInstance().getUser(email).getName()
							+ LocalDateTime.now().toString().replaceAll(":", "") + "-post-pic."
							+ picture.getContentType().split("/")[1]);
			Files.copy(pictureStream, pictureFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			PostDAO.getInstance().addPost(email, albumId, category, pictureFile.getName(), nameOfPost, keyWords, Timestamp.valueOf(LocalDateTime.now()),new ArrayList<>(), new HashSet(), new HashSet<>());
			html = "main.jsp";
			
			request.getServletContext().setAttribute(category+"Posts", PostDAO.getInstance().getAllPostsByCategory(category).size());
			
		} else {
			html = "index.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}
}
