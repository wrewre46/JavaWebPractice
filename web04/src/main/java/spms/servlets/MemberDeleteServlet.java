package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/member/delete")
@SuppressWarnings("serial")
public class MemberDeleteServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
	Connection conn=null;
	Statement stmt = null;
	
	try {
		ServletContext ctx=this.getServletContext();
		Class.forName(ctx.getInitParameter("driver"));//2. 드라이버를 사용하여 MySQL 서버와 연결
		conn = DriverManager.getConnection(
				ctx.getInitParameter("url"),
				ctx.getInitParameter("username"),
				ctx.getInitParameter("password"));
		stmt = conn.createStatement();
		stmt.executeUpdate(
				"DELETE FROM MEMBERS WHERE MNO="+
						request.getParameter("no"));
		response.sendRedirect("list");
		
		} catch(Exception e) {
				throw new ServletException(e);
			
		} finally {
			
			try{stmt.close();} catch (Exception e) {}
			try{conn.close();} catch (Exception e) {}
					
		}
	}
}