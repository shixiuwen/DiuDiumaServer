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
 * ���ڽ����ϴ���ͼƬ�����浽����Tomcat��ӦwebApp/uploadĿ¼
 * 
 * �õ�Appach�����ϴ��ļ��ı�����⣺commons-fileupload �� commons-io
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
		//���������������ȡ����
				req.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/json;charset=utf-8");

				DiskFileItemFactory factory = new DiskFileItemFactory();
		        // �õ������ļ���·��������("D://tomcat6//webapps//test//upload")
		        String path = req.getSession().getServletContext().getRealPath("/upload");
		        System.out.println("����·��"+path);
		        // ��ʱ�ļ���·��
		        String repositoryPath = req.getSession().getServletContext().getRealPath("/upload/temp");
		        System.out.println("���·��"+repositoryPath);
		        // �趨��ʱ�ļ���ΪrepositoryPath
		        factory.setRepository(new File(repositoryPath));
		        // �趨�ϴ��ļ���ֵ������ϴ��ļ�����1M���Ϳ�����repository
		        // ��������ļ����в�����ʱ�ļ�������ֱ�����ڴ��н��д���
		        factory.setSizeThreshold(1024 * 1024);
		        
		        // ����һ��ServletFileUpload����
		        ServletFileUpload uploader = new ServletFileUpload(factory);
		        try {
		            /**
		             * ����uploader�е�parseRequest���������Ի�������е�������ݣ� ��һ��FileItem���͵�ArrayList��
		             * FileItem��ָorg.apache.commons.fileupload�ж���ģ������Դ���һ���ļ���
		             * Ҳ���Դ���һ����ͨ��formfield
		             */
		            ArrayList<FileItem> list = (ArrayList<FileItem>) uploader
		                    .parseRequest(req);
		            System.out.println(list.size());
		            for (FileItem fileItem : list) {
		                if (fileItem.isFormField()) {// �������ͨ��formfield
		                    String name = fileItem.getFieldName();
		                    String value = fileItem.getString();
		                    System.out.println(name + ":" + value);
		                } else {// ������ļ�
		                    String value = fileItem.getName();
		                    // value.lastIndexOf("\\")���ء�\\�������ֵ�λ���±�
		                    int start = value.lastIndexOf("\\");
		                    // substring ��ȡ�ַ�����ʽ֮һ
		                    String fileName = value.substring(start + 1);
		                    // �����а���������д��path()�£���uploadĿ¼�£���ΪfileName���ļ���
		                    fileItem.write(new File(path, fileName));
		                }
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        // ��ͻ��˷��ؽ��
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
