package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.UserDAO;

/**
 * Servlet implementation class FollowServlet
 */
@WebServlet("/FollowServlet")
public class FollowServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userEmail = request.getParameter("userEmail");
		String followerEmail = request.getParameter("followerEmail");
		request.setAttribute("userEmail", userEmail);
		request.setAttribute("followerEmail", followerEmail);
		
		UserDAO.getInstance().followUser(userEmail, followerEmail);
		
		RequestDispatcher view=request.getRequestDispatcher("profile.jsp");
		view.forward(request, response);
	}

}
