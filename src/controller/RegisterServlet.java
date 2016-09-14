package controller;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.pojo.UsersManager;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		String gender=request.getParameter("gender");
		String description=request.getParameter("description");
		
		Pattern pattern = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
		Matcher mattcher = pattern.matcher(email);
		boolean validation=mattcher.matches();
		String html="";
		if((mattcher.matches())&& (!email.isEmpty()) && (!password.isEmpty()) && (password.equals(password2)) &&(!name.isEmpty())&&(age>0)&&(gender.equals("female")||gender.equals("male"))){
			UsersManager.getInstance().regUser( email, password, name,age, gender, description);
			html="index.html";
		}else{
			html="RegisterFailed.html";
		}
			RequestDispatcher view = request.getRequestDispatcher(html);
			view.forward(request, response);
		
	
	}

}
