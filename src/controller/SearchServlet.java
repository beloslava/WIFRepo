package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.User;
import model.pojo.UsersManager;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userName = request.getParameter("userName");
		ArrayList<User> users = (ArrayList<User>) UsersManager.getInstance().searchUsersByName(userName);
		request.setAttribute("users", users);
		request.setAttribute("userName", userName);
		request.setAttribute("count", users.size());
		
		RequestDispatcher view = request.getRequestDispatcher("search.jsp");
		view.forward(request, response);
	}


}
