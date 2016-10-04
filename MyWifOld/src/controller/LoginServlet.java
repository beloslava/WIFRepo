package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.PostDAO;
import model.pojo.UsersManager;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String html = "";
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		if (UsersManager.getInstance().validLogin(email, password)) {
			request.getSession().setAttribute("USER", email);
			request.getServletContext().setAttribute("animalsPosts", PostDAO.getInstance().getAllPostsByCategory("animals").size());
			request.getServletContext().setAttribute("abstractPosts", PostDAO.getInstance().getAllPostsByCategory("abstract").size());
			request.getServletContext().setAttribute("foodPosts", PostDAO.getInstance().getAllPostsByCategory("food").size());
			request.getServletContext().setAttribute("peoplePosts", PostDAO.getInstance().getAllPostsByCategory("people").size());
			request.getServletContext().setAttribute("naturePosts", PostDAO.getInstance().getAllPostsByCategory("nature").size());
			request.getServletContext().setAttribute("urbanPosts", PostDAO.getInstance().getAllPostsByCategory("urban").size());
			request.getServletContext().setAttribute("uncategorizedPosts", PostDAO.getInstance().getAllPostsByCategory("uncategorized").size());
			request.getServletContext().setAttribute("familyPosts", PostDAO.getInstance().getAllPostsByCategory("family").size());
			request.getServletContext().setAttribute("sportPosts", PostDAO.getInstance().getAllPostsByCategory("sport").size());
			request.getServletContext().setAttribute("travelPosts", PostDAO.getInstance().getAllPostsByCategory("travel").size());

			html="MainServlet";
		} else {
			html="index.html";
		}
		RequestDispatcher view = request.getRequestDispatcher(html);
		view.forward(request, response);
	}
	

}
