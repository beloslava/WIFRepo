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
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		InputStream pictureStream = picture.getInputStream();
		System.out.println(email);
		if (category!=null&&nameOfPost!=null&&keyWords!=null) {
			File dir = new File("D:\\MyWifPictures\\userPostPics"+UsersManager.getInstance().getUser(email).getName());
			if (!dir.exists()) {
				dir.mkdir();
			}
			File pictureFile = new File(dir,
					UsersManager.getInstance().getUser(email).getName()
							+ LocalDateTime.now() + "-post-pic."
							+ picture.getContentType().split("/")[1]);
			Files.copy(pictureStream, pictureFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			PostDAO.getInstance().addPost(email, 0, category, pictureFile.getName(), nameOfPost, keyWords, Timestamp.valueOf(LocalDateTime.now()), new LinkedList<>());
			html = "main.jsp";
		} else {
			html = "index.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}
}
