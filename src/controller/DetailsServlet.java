package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DetailsServlet
 */
@WebServlet("/DetailsServlet")
@MultipartConfig
public class DetailsServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view;
		if(!request.getSession().getAttribute("USER").toString().isEmpty()){
			String userEmail=request.getSession().getAttribute("USER").toString();
			int postId=Integer.parseInt(request.getParameter("postpic"));
			request.setAttribute("postId", postId);
			view=request.getRequestDispatcher("detailsPost.jsp");
		}else{
			view=request.getRequestDispatcher("index.html");
		}
		view.forward(request, response);
	}

}
