package com.bluemix.nonsql;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GenEnv
 */
@WebServlet("/GetEnv")
public class GetEnv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetEnv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String greeting = "Hello World! ";
		Map<String, String> sysenv = System.getenv();
		String vcap_services = System.getenv("VCAP_SERVICES");
		String result = "<!DOCTYPE html><meta charset=\"UTF-8\"><html><body><h1>" + greeting + "</h1><h3>System.getenv(\"VCAP_SERVICES\"): " + vcap_services + "</h3><h3>System.getenv(): " + sysenv + "</h3></body></html>";
		response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
