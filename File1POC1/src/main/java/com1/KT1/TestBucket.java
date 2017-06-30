package com1.KT1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;


public class TestBucket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Instantiates a client
		String targetFileStr ="";
		List<FileItem> fileName = null;
	    Storage storage = StorageOptions.getDefaultInstance().getService();

	    // The name for the new bucket
	    String bucketName = "vendor-bucket13";  // "my-new-bucket";

	    // Creates the new bucket
	    Bucket bucket = storage.create(BucketInfo.of(bucketName));
	    

	    //Object requestedFile = request.getParameter("filename");
	    
	    
	    ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		try {
			 fileName = sfu.parseRequest(request);
			 for(FileItem f:fileName)
				{
				try {
					f.write (new File("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//targetFileStr = readFile("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName(),Charset.defaultCharset());
				targetFileStr = new String(Files.readAllBytes(Paths.get("/Users/tkmajdt/Documents/workspace/File1POC1/" + f.getName())));
				}
		} 
		
	//response.getWriter().print("File Uploaded Successfully");
	

//String content = readFile("test.txt", Charset.defaultCharset());
		
		catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*if(requestedFile==null)
		{
			response.getWriter().print("File Not Found");
		}*/
		/*else
		{
			//String fileName = (String)requestedFile;
			FileInputStream fisTargetFile = new FileInputStream(fileName);

			targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
		}*/
		
		
	    
	    BlobId blobId = BlobId.of(bucketName, "my_blob_name");
	    //Blob blob = bucket.create("my_blob_name", "a simple blob".getBytes("UTF-8"), "text/plain");
	    Blob blob = bucket.create("my_blob_name", targetFileStr.getBytes("UTF-8"), "text/plain");
	    
	    //storage.delete("vendor-bucket3");
	}

	

	
}
