package com.shixia;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TestServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
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
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * {"status":"OK","info":"操作成功","data":{}}
		 * 
		 * */
		
		//请求参数
		request.setCharacterEncoding("utf-8");	//这一句解决请求参数的乱码
		Enumeration<String> paraNames = request.getParameterNames();
		while(paraNames.hasMoreElements()){
			String paraKey = paraNames.nextElement();
			if(request.getParameter(paraKey) != null){
				System.out.println("key:" + paraKey + " " + "value:" + request.getParameter(paraKey));
			}else {
				System.out.println("key:" + paraKey + " " + "value:" + "null");
			}
		}
		
		try {
			Thread.sleep(3000);
			//请求结果返回Json字符串
			String s = "{\"status\":1,\"info\":\"操作成功\",\"data\":\"{}\"}";
//			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json;charset=utf-8");
			
			PrintWriter out = response.getWriter();
			out.write("{\"status\":1,\"info\":\"操作成功\",\"data\":{}}");
			out.flush();
			out.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
