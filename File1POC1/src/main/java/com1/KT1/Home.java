package com1.KT1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String name = request.getParameter("name");
		response.getWriter().println("Welcome  " +name);
		
		String role = request.getParameter("role");
		ServletContext context = getServletContext();
		context.setAttribute("roles", role);
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html>");
		out.println("<body>");
		
		out.println("<h3>Select the action you wanted to perform</h3>");
		out.println("<br/>");
		
		out.println("<a href='up.html'>Upload</a>");
		
		out.println("<a href='down.html'>Download</a>");
		out.println("<a href='up1.html'>TestUpload</a>");
		out.println("</body>");
		out.println("</html>");
		
	}

}
