package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.db.PostDAO;
import model.pojo.UsersManager;

/**
 * Servlet implementation class ChangeProfilePictureServlet
 */
@WebServlet("/ChangeProfilePictureServlet")
@MultipartConfig
public class ChangeProfilePictureServlet extends HttpServlet {
	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getSession().getAttribute("USER").toString();
		String name=UsersManager.getInstance().getUser(email).getName();
		Part picture = request.getPart("fileField");
		InputStream pictureStream = picture.getInputStream();
		
		File dir = new File(USERS_PROFILE_PICS_DIR);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		System.out.println(picture.getContentType());
		File pictureFile=new File(dir, name + "-profile-pic." + picture.getContentType().split("/")[1]);
		System.out.println(pictureFile.getAbsolutePath());
		Files.copy(pictureStream, pictureFile.toPath(),StandardCopyOption.REPLACE_EXISTING );
			UsersManager.getInstance().changeAvatar(pictureFile.getName(), email);
			request.getRequestDispatcher("myProfile.jsp").forward(request, response);
	}

}
