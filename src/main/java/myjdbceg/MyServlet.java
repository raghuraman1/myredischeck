package myjdbceg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
@WebServlet("/Check")
public class MyServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body><form method=\"POST\">");
		out.println("<table>");
		out.println("<tr>");
		/*
		out.println("<td>");
		out.println("user");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"user\" />");
		out.println("</td>");
		out.println("</tr>");*/
		
		out.println("<tr>");
		out.println("<td>");
		out.println("password");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"password\" type=\"password\"/>");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("host");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"host\" />");
		out.println("</td>");
		out.println("</tr>");
		
		out.println("<tr>");
		out.println("<td>");
		out.println("port");
		out.println("</td>");
		out.println("<td>");
		out.println("<input name=\"port\" />");
		out.println("</td>");
		
		out.println("</tr>");
		out.println("</table>");

		out.println("<input type=\"submit\" value=\"submit\"/>");
		out.println("</form></body></html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		out.println("<html><body>");
		out.println("<head>");
		out.println("<style>");
		out.println("table {");
		out.println("    border-collapse: collapse;");
		out.println("}");
		out.println("");
		out.println("table, td, th {");
		out.println("    border: 1px solid black;");
		out.println("}");
		out.println("</style>");
		out.println("</head>");
		try {
			process(out, req);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace(out);
			
		}
		finally
		{
			out.println("</body></html>");
		}
	}

	private void process(PrintWriter out, HttpServletRequest req) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		
		out.println("processing...........<br/>");
		
		String host = req.getParameter("host");
		String port = req.getParameter("port");
		int portInt=0;
		if(port!=null)
		{
			portInt=Integer.parseInt(port);
		}
		//String url="jdbc:mysql://"+host+":"+port;
		String u=req.getParameter("user");
		String p=req.getParameter("password");
		
		Jedis jedis = new Jedis(host, portInt);
		String auth = jedis.auth(p);
		out.println("got auth..........."+auth+"<br/>");
		 out.println("Server is running: "+jedis.ping());
		 String key="mykey";
		 if(jedis.exists(key))
		 {
			 out.println(key+" exists");
			 Long del = jedis.del(key);
			 out.println(del+" deltetd");
			 if(jedis.exists(key))
			 {
				 out.println(key+" still exists");
			 }
		 }
		jedis.set(key, "value1");
		
	}

}
