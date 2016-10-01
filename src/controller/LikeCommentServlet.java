package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.CommentDAO;
import model.db.PostDAO;
import model.pojo.Post;
@WebServlet("/LikeCommentServlet")
public class LikeCommentServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int commentId=Integer.parseInt(request.getParameter("commentId"));
		request.setAttribute("commentId", commentId);
		int postId=Integer.parseInt(request.getParameter("postId").toString());
		String email=request.getSession().getAttribute("USER").toString();
		Post post=PostDAO.getInstance().getPost(commentId);
		CommentDAO.getInstance().likeComment(commentId, email);
		RequestDispatcher view=request.getRequestDispatcher("DetailsServlet?postId="+postId);
		view.forward(request, response);
		 

	}
}
