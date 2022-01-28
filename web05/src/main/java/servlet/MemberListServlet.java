package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.MemberDao;
import vo.Member;
@WebServlet("/member/list")
@SuppressWarnings("serial")
public class MemberListServlet extends HttpServlet{
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			ServletContext sc = this.getServletContext();
			conn=(Connection) sc.getAttribute("conn");
			MemberDao memberDao = new MemberDao();
			memberDao.setConnection(conn);
			request.setAttribute("members", memberDao.selectList());
			response.setContentType("text/html; charset=UTF-8");
			
			
			RequestDispatcher rd=request.getRequestDispatcher("/member/MemberList.jsp");
			rd.include(request, response);
		}catch(Exception E) {
			//throw new ServletException(E);
			E.printStackTrace();
			request.setAttribute("error",E);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}finally {
			try {if (rs!=null) rs.close();} catch(Exception e) {}
			try {if (stmt!=null) stmt.close();} catch(Exception e) {}
			
		}
	}
		
	
}
