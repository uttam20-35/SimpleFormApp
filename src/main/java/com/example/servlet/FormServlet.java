package com.example.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/submitForm")  //1)URL Mapping 
public class FormServlet extends HttpServlet {  //2) Extends to Servlet 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;  // not manually, generated automatically
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException { // 3) Create post method
		
		//4) get form data
		
		String firstName=request.getParameter("firstName");
		String lastName= request.getParameter("lastName");
		int age=Integer.parseInt(request.getParameter("age"));
		
		// 5) Load Driver and Connect to DB
			try {
			Class.forName("org.postgresql.Driver");
			Connection connection=	DriverManager.getConnection(
					"jdbc:postgresql://localhost:5432/simpleformapp","postgres","root");
			
		// 6)Prepare and execute SQL statement
			String sqlQuery= "insert into users (first_name,last_name,age) values (?,?,?)";
			
			PreparedStatement pStatement = connection.prepareStatement(sqlQuery);
			pStatement.setString(1, firstName);
			pStatement.setString(2, lastName);
			pStatement.setInt(3, age);
			
			pStatement.executeUpdate();
			
			// 7) Redirect to result page 
			response.sendRedirect("result.jsp");
			
			// 8) Close Resource
			pStatement.close();
			connection.close();
			
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
	
	}
}
