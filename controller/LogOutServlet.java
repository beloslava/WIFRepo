package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.db.DBManager;

@WebServlet("/LogOutServlet")
public class LogOutServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if(request.getSession().getAttribute("USER")!=null){
		request.getSession().setAttribute("USER", null);
		request.getSession().invalidate();
		}
		RequestDispatcher view = request.getRequestDispatcher("index.html");
		view.forward(request, response);
	}

	@Override
	public void destroy() {
		try {
			DBManager.getInstance().getConnection().close();
		} catch (SQLException e) {
			System.out.println("Cannot close the connection right now");
			e.printStackTrace();
		}
	}
}
