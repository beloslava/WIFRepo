package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.PostDAO;
import model.pojo.Post;

@WebServlet("/LikesServlet")
public class LikesServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId=Integer.parseInt(request.getParameter("postId"));
		request.setAttribute("postId", postId);
		Post post=PostDAO.getInstance().getPost(postId);
		PostDAO.getInstance().likePost(post);
		RequestDispatcher view=request.getRequestDispatcher("DetailsServlet?postId="+postId);
		view.forward(request, response);

	}
}
