package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.pojo.UsersManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (UsersManager.getInstance().validLogin(email, password)) {
			HttpSession session = request.getSession();
			session.setAttribute("USER", email);
			response.sendRedirect("MainServlet");
		} else {
			response.sendRedirect("InvalidLogin.html");
		}
	}

}
