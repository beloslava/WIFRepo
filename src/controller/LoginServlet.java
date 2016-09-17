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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html = "";
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (UsersManager.getInstance().validLogin(email, password)) {
			request.getSession().setAttribute("USER", email);
			html="MainServlet";
		} else {
			html="loginFailed.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}
}
