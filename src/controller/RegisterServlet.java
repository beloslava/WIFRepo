package controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.HashSet;
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

import model.pojo.UsersManager;

@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {

	private static final String USERS_PROFILE_PICS_DIR = "D:\\MyWifPictures\\userProfilePics";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender = request.getParameter("gender");
		String email = request.getParameter("email");
		String about = request.getParameter("description");
		Part avatar = request.getPart("file");
		InputStream avatarStream = avatar.getInputStream();
		Pattern pattern = Pattern.compile(
				"^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		Matcher mattcher = pattern.matcher(email);
		String html = "";
		if ((mattcher.matches()) && (!email.isEmpty()) && (!password.isEmpty()) && (password.equals(password2))
				&& (!name.isEmpty()) && (age > 0)
				&& (gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("male"))) {
			File dir = new File(USERS_PROFILE_PICS_DIR);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			System.out.println(avatar.getContentType());
			File avatarFile=new File(dir, name + "-profile-pic." + avatar.getContentType().split("/")[1]);
			System.out.println(avatarFile.getAbsolutePath());
			Files.copy(avatarStream, avatarFile.toPath(),StandardCopyOption.REPLACE_EXISTING );
			System.out.println("Try to save file with name: " + avatarFile.getName());
			System.out.println("abs. path = " + avatarFile.getAbsolutePath());
			UsersManager.getInstance().regUser(email, password, name, age, gender, about, avatarFile.getName(),
					new ArrayList<>());
			html="index.html";
		}
		else{
			html="RegisterFailed.html";
		}

		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}

}
