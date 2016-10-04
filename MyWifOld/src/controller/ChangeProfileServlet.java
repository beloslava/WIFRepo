package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.User;
import model.pojo.UsersManager;

/**
 * Servlet implementation class ChangeProfileServlet
 */
@WebServlet("/ChangeProfileServlet")
public class ChangeProfileServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String newName=request.getParameter("newName");
		String oldPass=request.getParameter("oldPass");
		String newPass=request.getParameter("newPass");
		String newPass2=request.getParameter("newPass2");
		String gender=request.getParameter("gender");
		String newDescription=request.getParameter("newDescription");
		String email=request.getSession().getAttribute("USER").toString();
		System.out.println(newName);
		System.out.println(oldPass);
		System.out.println(newPass);
		System.out.println(newPass2);
		System.out.println(gender);
		System.out.println(newDescription);
		System.out.println(email);
		if(newName!=null&&oldPass!=null&&newPass!=null&&newPass2!=null&&gender!=null&&newDescription!=null){
			UsersManager.getInstance().changeSettings(newName, newPass, gender, newDescription, email);
			request.getRequestDispatcher("myProfile.jsp").forward(request, response);
		}
		
	}

}
