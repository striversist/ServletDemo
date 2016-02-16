package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SessionTrack", urlPatterns = { "/SessionTrack" })
public class SessionTrack extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 如果不存在 session 会话，则创建一个 session 对象
		HttpSession session = req.getSession(true);
		Date createTime = new Date(session.getCreationTime());
		Date lastAccessTime = new Date(session.getLastAccessedTime());
		
		String title = "Welcome";
		Integer visitCount = 0;
		String visitCountKey = "visitCount";
		String userIdKey = "userID";
		String userId = "ABCD";
		
		if (session.isNew()) {
			title = "Welcome!";
			session.setAttribute(userIdKey, userId);
		} else {
			title = "Welcome back!";
			visitCount = (Integer) session.getAttribute(visitCountKey);
			visitCount++;
			userId = (String) session.getAttribute(userIdKey);
		}
		session.setAttribute(visitCountKey, visitCount);
		
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		String docType =  "<!doctype html public \"-//w3c//dtd html 4.0 transitional//en\">\n";
		out.println(docType +
	                "<html>\n" +
	                "<head><title>" + title + "</title></head>\n" +
	                "<body bgcolor=\"#f0f0f0\">\n" +
	                "<h1 align=\"center\">" + title + "</h1>\n" +
	                 "<h2 align=\"center\">Session 信息</h2>\n" +
	                "<table border=\"1\" align=\"center\">\n" +
	                "<tr bgcolor=\"#949494\">\n" +
	                "  <th>Session 信息</th><th>值</th></tr>\n" +
	                "<tr>\n" +
	                "  <td>id</td>\n" +
	                "  <td>" + session.getId() + "</td></tr>\n" +
	                "<tr>\n" +
	                "  <td>Creation Time</td>\n" +
	                "  <td>" + createTime + 
	                "  </td></tr>\n" +
	                "<tr>\n" +
	                "  <td>Time of Last Access</td>\n" +
	                "  <td>" + lastAccessTime + 
	                "  </td></tr>\n" +
	                "<tr>\n" +
	                "  <td>User ID</td>\n" +
	                "  <td>" + userId + 
	                "  </td></tr>\n" +
	                "<tr>\n" +
	                "  <td>Number of visits</td>\n" +
	                "  <td>" + visitCount + "</td></tr>\n" +
	                "</table>\n" +
	                "</body></html>");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
