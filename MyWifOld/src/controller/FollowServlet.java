package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.UserDAO;
import model.pojo.UsersManager;

@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emaiToFollow=request.getParameter("emaiToFollow");
		String myEmail=request.getSession().getAttribute("USER").toString();
		UsersManager.getInstance().follow(emaiToFollow, myEmail);
		request.getRequestDispatcher("ProfileServlet?email="+emaiToFollow).forward(request, response);
	}

}
