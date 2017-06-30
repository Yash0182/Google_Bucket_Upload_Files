package com1.KT1;

import java.io.File;
import java.io.IOException;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Acl.Role;
import com.google.cloud.storage.Acl.User;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;



public class BucketGoogle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static Storage storage = null;

	  
	  static {
	    storage = StorageOptions.getDefaultInstance().getService();
	  }
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//doGet(request, response);
		Storage storage = StorageOptions.getDefaultInstance().getService();
		String bucketName = "vendor-bucket2";
		Bucket bucket = storage.create(BucketInfo.of(bucketName));
		Object obj = getImageUrl(request,response,bucketName);
		response.getWriter().println("Bucket Created:  " +bucket.getName());
		
		
	}
	public String uploadFile(Part filePart, final String bucketName) throws IOException {
	//public String uploadFile(List<FileItem> filePart, final String bucketName) throws IOException {
	    DateTimeFormatter dtf = DateTimeFormat.forPattern("-YYYY-MM-dd-HHmmssSSS");
	    DateTime dt = DateTime.now(DateTimeZone.UTC);
	    String dtString = dt.toString(dtf);
	    final String fileName = filePart.getSubmittedFileName() + dtString;

	    // the inputstream is closed by default, so we don't need to close it here
	    BlobInfo blobInfo =
	        storage.create(
	            BlobInfo
	                //.newBuilder(bucketName, fileName)
	            .newBuilder(bucketName, fileName)
	                // Modify access list to allow all users with link to read file
	                .setAcl(new ArrayList(Arrays.asList(Acl.of(User.ofAllUsers(), Role.READER))))
	                .build(),
	            filePart.getInputStream());
	    // return the public download link
	    return blobInfo.getMediaLink();
	  }

  public String getImageUrl(HttpServletRequest req, HttpServletResponse resp,
                            final String bucket) throws IOException, ServletException 
  
  {
	String imageUrl="";  
	 
    Part filePart = req.getPart("file");

    final String fileName = filePart.getSubmittedFileName();
    System.out.println(fileName);
    imageUrl = req.getParameter("imageUrl");
    // Check extension of file
    
    
    
	
	
	
    if (fileName != null && !fileName.isEmpty() && fileName.contains(".")) {
      final String extension = fileName.substring(fileName.lastIndexOf('.') + 1);
      String[] allowedExt = { "jpg", "jpeg", "png", "gif" };
      for (String s : allowedExt) {
        if (extension.equals(s)) {
         return this.uploadFile(filePart, bucket);
	 //return this.uploadFile(files, bucket);
        }
      }
      throw new ServletException("file must be an image");
    }
    return imageUrl;
  
  }
}


