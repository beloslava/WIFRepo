package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId=Integer.parseInt(request.getParameter("postId"));
		request.setAttribute("postId", postId);
		RequestDispatcher view=request.getRequestDispatcher("detailsPost.jsp");
		view.forward(request, response);
	}

}
