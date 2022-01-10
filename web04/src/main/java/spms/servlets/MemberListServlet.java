package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

@SuppressWarnings("serial")
public class MemberListServlet extends GenericServlet{

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		//1. 사용할 JDBC 드라이버를 등록한다.
		Connection con=null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
		//	DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		//	ServletConfig config = this.getServletConfig();
		//	Class.forName(config.getInitParameter("driver"));	
		//	Class.forName(this.getInitParameter("driver"));
			ServletContext ctx=this.getServletContext();
			Class.forName(ctx.getInitParameter("driver"));//2. 드라이버를 사용하여 MySQL 서버와 연결
			con = DriverManager.getConnection(
					ctx.getInitParameter("url"),
					ctx.getInitParameter("username"),
					ctx.getInitParameter("password"));
			//3. 커넥션 객체로부터 SQL을 던질 객체를 준비
			stmt = con.createStatement();
			//4. SQL을 던지는 객체를 사용하여 서버에 질의!
			// ResultSet은 mysql서버에서 가져오는 역할을 하는 객체
			rs = stmt.executeQuery("select MNO,MNAME,EMAIL,CRE_DATE" +
					" from MEMBERS" + " order by MNO ASC");
			//5. 서버에 가져온 데이터를 사용하여 HTML만들어서 웹 브라우저로 출력
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head><body>");
			out.println("<h1>회원 목록</h1>");
			out.println("<p><a href='add'>신규 회원</a></p>");
			while(rs.next()) {
				out.println(
					rs.getInt("MNO") + "," +
					"<a href='update?no=" + rs.getInt("MNO") + "'>" +
					rs.getString("MNAME") + "</a>," +
					rs.getString("EMAIL") + "," + 
					rs.getDate("CRE_DATE") + "<br>"
				);
			}
			 out.println("</body></html>");
			} catch(Exception e) {
				throw new ServletException(e);
			
			} finally {
				try{rs.close();} catch (Exception e) {}
				try{stmt.close();} catch (Exception e) {}
				try{con.close();} catch (Exception e) {}
					
			}
	}
	
}
