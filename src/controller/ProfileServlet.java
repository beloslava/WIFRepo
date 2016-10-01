package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/ProfileServlet")
public class ProfileServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String email=request.getParameter("email");
	request.setAttribute("email", email);
	String jsp="";
	if(request.getSession().getAttribute("USER").toString().equals(email)){
		jsp="myProfile.jsp";
	}
	else{
		jsp="profile.jsp";
	}
	
	RequestDispatcher view=request.getRequestDispatcher(jsp);
	view.forward(request, response);
}
}
