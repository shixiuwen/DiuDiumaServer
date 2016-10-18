package com.shixia;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Destruction of the servlet. <br>
 * 
 * 用于接收上传的图片并保存到本地Tomcat对应webApp/upload目录
 * 
 * 用到Appach两个上传文件的保存类库：commons-fileupload 和 commons-io
 */
public class UploadServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public UploadServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		//定义输入输出流读取类型
				req.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/json;charset=utf-8");

				DiskFileItemFactory factory = new DiskFileItemFactory();
		        // 得到绝对文件夹路径，比如("D://tomcat6//webapps//test//upload")
		        String path = req.getSession().getServletContext().getRealPath("/upload");
		        System.out.println("绝对路径"+path);
		        // 临时文件夹路径
		        String repositoryPath = req.getSession().getServletContext().getRealPath("/upload/temp");
		        System.out.println("相对路径"+repositoryPath);
		        // 设定临时文件夹为repositoryPath
		        factory.setRepository(new File(repositoryPath));
		        // 设定上传文件的值，如果上传文件大于1M，就可能在repository
		        // 所代表的文件夹中产生临时文件，否则直接在内存中进行处理
		        factory.setSizeThreshold(1024 * 1024);
		        
		        // 创建一个ServletFileUpload对象
		        ServletFileUpload uploader = new ServletFileUpload(factory);
		        try {
		            /**
		             * 调用uploader中的parseRequest方法，可以获得请求中的相关内容， 即一个FileItem类型的ArrayList。
		             * FileItem是指org.apache.commons.fileupload中定义的，他可以代表一个文件，
		             * 也可以代表一个普通的formfield
		             */
		            ArrayList<FileItem> list = (ArrayList<FileItem>) uploader
		                    .parseRequest(req);
		            System.out.println(list.size());
		            for (FileItem fileItem : list) {
		                if (fileItem.isFormField()) {// 如果是普通的formfield
		                    String name = fileItem.getFieldName();
		                    String value = fileItem.getString();
		                    System.out.println(name + ":" + value);
		                } else {// 如果是文件
		                    String value = fileItem.getName();
		                    // value.lastIndexOf("\\")返回“\\”最后出现的位置下标
		                    int start = value.lastIndexOf("\\");
		                    // substring 截取字符串方式之一
		                    String fileName = value.substring(start + 1);
		                    // 将其中包含的内容写到path()下，即upload目录下，名为fileName的文件中
		                    fileItem.write(new File(path, fileName));
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        // 向客户端返回结果
		        PrintWriter out = response.getWriter();
		        out.println("ok");
		        out.flush();
		        out.close();
		
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
