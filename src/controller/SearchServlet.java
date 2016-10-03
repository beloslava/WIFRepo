package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.Searchable;
import model.pojo.User;
import model.pojo.UsersManager;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = request.getParameter("input");
		String type=request.getParameter("type");
		ArrayList<Searchable> search = (ArrayList<Searchable>) UsersManager.getInstance().search(input, type);
		request.setAttribute("search", search);
		request.setAttribute("input", input);
		request.setAttribute("count", search.size());
		request.setAttribute("type", type);
		RequestDispatcher view = request.getRequestDispatcher("search.jsp");
		view.forward(request, response);
	}


}
