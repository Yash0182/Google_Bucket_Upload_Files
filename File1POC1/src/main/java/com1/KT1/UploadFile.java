package com1.KT1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadFile extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
			{
			ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
			List<FileItem> files = sfu.parseRequest(request);
			for(FileItem f:files)
			{
			f.write (new File("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName()));
			}
		response.getWriter().print("File Uploaded Successfully");
		}
	
	
	
		
		catch(Exception e)
			{
			System.out.println(e.getMessage());
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

}
