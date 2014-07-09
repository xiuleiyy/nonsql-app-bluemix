package com.bluemix.nonsql;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/**
 * Servlet implementation class MongoServlet
 */
@WebServlet("/MongoServlet")
public class MongoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MongoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		JSONObject vcap_services_obj = null;
		String greeting = "<Empty>";
			
		System.out.println("Start to get connection with system var........");
		
		
		System.out.println("Start to read system var to connect to MongoDB");
		
		String icap_services_string = System.getenv("VCAP_SERVICES");
		
		System.out.println("VCAP_SERVICES =" + icap_services_string);
		if (null != icap_services_string && icap_services_string.length() > 0) {
			 vcap_services_obj = JSONObject.parse(icap_services_string);
			
			 System.out.println("VCAP_SERVICES_JSON_Obj =" + vcap_services_obj );
		} else {
			System.out.println("VCAP_SERVICES_JSON_Obj IS NULL" );
		}

		if (null != vcap_services_obj) {
			System.out.println("getting vcap_services_obj details, size:"
					+ vcap_services_obj.size());
			
			JSONArray mongo_services = (JSONArray) vcap_services_obj.get("mongolab");
			JSONObject first_myMongo = (JSONObject) mongo_services.get(0);
			JSONObject first_credential = (JSONObject) first_myMongo.get("credentials");
			String uri =(String) first_credential.get("uri");
			String uri_value = uri.substring(10);
			String uid_pwd= uri_value.substring(0,uri_value.indexOf("@"));
			String host_port_dbname = uri_value.substring(uri_value.indexOf("@") + 1);
			 
			String uid=uid_pwd.substring(0, uid_pwd.indexOf(":"));
			String pwd=uid_pwd.substring(uid_pwd.indexOf(":")+1);
			 
			String host_port= host_port_dbname.substring(0, host_port_dbname.indexOf("/"));
			String databaseName = host_port_dbname.substring(host_port_dbname.indexOf("/")+1);
			String host = host_port.substring(0, host_port.indexOf(":"));
			String port = host_port.substring(host_port.indexOf(":")+1);
			

			Mongo m = new Mongo(host, Integer.parseInt(port));
			DB db = m.getDB(databaseName);
			//Authentication is a MUST
			boolean flag = db.authenticate(uid, pwd.toCharArray());
			System.out.println("flag::::: " + flag);
			
			DBCollection cities = db.getCollection("cities");
			cities.drop();			
			
			DBObject city1 = new BasicDBObject();
			city1.put("name", "Beijing");
			city1.put("population", "20000000");
			city1.put("country", "China");
			
			DBObject city2 = new BasicDBObject();
			city2.put("name", "ShangHai");
			city2.put("population", "15000000");
			city2.put("country", "China");
			
			DBObject city3 = new BasicDBObject();
			city3.put("name", "TianJin");
			city3.put("population", "10000000");
			city3.put("country", "China");
			
			cities.insert(city1);
			cities.insert(city2);
			cities.insert(city3);
			
			List<DBObject> resultCities = cities.find(new BasicDBObject("country", "China")).toArray();
			
			StringBuffer sb = new StringBuffer();
		    sb.append("<table style=\"width=500px;\" border=\"1\">");
		    sb.append("<tr><td>City</td><td>Population</td><td>Country</td></tr>");
			
			for(int i = 0; i < resultCities.size(); i++) {
				sb.append(oneRowMongo(resultCities.get(i)));
			}
			sb.append("</table>");
		    greeting = sb.toString();
			
		}
		
		
		
		
		PrintWriter out = response.getWriter();
		out.write("<html> " + "<title>" + greeting + "</title>"
		        + "<body><h1>" + greeting + " ^_^ MongoDB is good!" + "</h1></body>" + "</html> ");
		out.flush();
			
		
	}
    
    private String oneRowMongo(DBObject dbObject) {
    	String template = "<tr><td>{0}</td><td>{1}</td><td>{2}</td></tr>";
		return MessageFormat.format(template, dbObject.get("name"), dbObject.get("population"), dbObject.get("country"));
    }
	
	private String oneRow(ResultSet resultSet){
		String template = "<tr><td>{0}</td><td>{1}</td><td>{2}</td></tr>";
		try {
			return MessageFormat.format(template, resultSet.getString(1),resultSet.getString(2),resultSet.getString(3));
		} catch (SQLException e) {
			return template;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet (request, response);
	}

}