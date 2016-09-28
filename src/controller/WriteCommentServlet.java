package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.CommentDAO;

@WebServlet("/WriteCommentServlet")
public class WriteCommentServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String emailUser=request.getParameter("email");
		String comment=request.getParameter("comment");
		Integer parentCommentId=null;
		if(!request.getParameter("parentCommentId").equals("parent")){
			parentCommentId=Integer.parseInt(request.getParameter("parentCommentId"));
		}
		int postId=Integer.parseInt(request.getParameter("postId"));
		String html="";
		if(comment!=null){
			CommentDAO.getInstance().addComment(postId, emailUser, parentCommentId, comment, Timestamp.valueOf(LocalDateTime.now()),new ArrayList<>());
			html="DetailsServlet?postId="+postId;
		}
		RequestDispatcher view=request.getRequestDispatcher(html);
		view.forward(request, response);
	}

}
