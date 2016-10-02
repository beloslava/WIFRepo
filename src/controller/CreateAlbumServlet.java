package controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.db.AlbumDAO;

/**
 * Servlet implementation class CreateAlbumServlet
 */
@WebServlet("/CreateAlbumServlet")
public class CreateAlbumServlet extends HttpServlet {
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name=request.getParameter("name");
		String email=request.getSession().getAttribute("USER").toString();
		System.out.println(name+email);
		AlbumDAO.getInstance().addAlbum(name, email, Timestamp.valueOf(LocalDateTime.now()), new ArrayList<>());
		request.getRequestDispatcher("myAlbums.jsp").forward(request, response);
	}

}
