package com1.KT1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	final String fileLocation = "/Users/tkmajdt/Documents/workspace/File1POC1/";
	final String[][] contentTypes = {{"xml","text/xml"},{"pdf","application/pdf"},{"doc","text/doc"}};
    public Download() {
        super();
        
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		
		Object requestedFile = request.getParameter("filename");
		if(requestedFile==null)
		{
			response.getWriter().print("File Not Found");
		}
		else
		{
			String fileName = (String)requestedFile;
			String contentType = getContentType(fileName.split("\\.")[1]);
			File file = new File(fileLocation + fileName);
			response.addHeader("Content-Disposition", "attachment; filename=" +fileName);
			response.setContentLength((int)file.length());
			ServletOutputStream sOS = response.getOutputStream();
			BufferedInputStream bIS = new BufferedInputStream(new FileInputStream(file));
			int bytesRead = bIS.read();
			while(bytesRead!=-1)
			{
				sOS.write(bytesRead);
				bytesRead = bIS.read();
			}
			if(sOS!=null)
			{
				sOS.close();
			}
			if(bIS!=null)
			{
				bIS.close();
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ServletContext context = getServletContext();
		String role=(String)context.getAttribute("roles");
		boolean flag = roleChecker(role);
		if(flag)
		{
		Object requestedFile = request.getParameter("filename");
		if(requestedFile==null)
		{
			response.getWriter().print("OK");
		}
		else
		{
			String fileName = (String)requestedFile;
			String contentType = getContentType(fileName.split("\\.")[1]);
			File file = new File(fileLocation + fileName);
			response.addHeader("Content-Disposition", "attachment; filename=" +fileName);
			response.setContentLength((int)file.length());
			ServletOutputStream sOS = response.getOutputStream();
			BufferedInputStream bIS = new BufferedInputStream(new FileInputStream(file));
			int bytesRead = bIS.read();
			while(bytesRead!=-1)
			{
				sOS.write(bytesRead);
				bytesRead = bIS.read();
			}
			if(sOS!=null)
			{
				sOS.close();
			}
			if(bIS!=null)
			{
				bIS.close();
			}
		}
		}
		else
		{
			response.getWriter().print("You are not authorized for this action");
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<html>");
		out.println("<body>");
		out.println("<br/>");
		out.println("<a href='index.jsp'>Home</a>");
		out.println("</body>");
		out.println("</html>");
	}
		
	private String getContentType(String fileType)
		{
			String returnType = null;
			for(int i=0;i<contentTypes.length;i++)
			{
				if(fileType.equals(contentTypes[i][0]))
				{
					returnType = contentTypes[i][1];
				}
			}
			return returnType;
		}
	private boolean roleChecker(String name)
	{
		String role="admin";
		
		if (role.equals(name))
		{
			return true;
		}
		else
		{
		return false;
		}
	}
		
		
	

}
