package demo;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatabaseAccess extends HttpServlet {

	private static final String TAG = DatabaseAccess.class.getSimpleName();
	private static final long serialVersionUID = 1L;
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/test";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Class.forName(JDBC_DRIVER);
			logD("doGet: jdbc driver load success!");;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;
		
		try {
			connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			statement = connection.createStatement();
			String sql = "select id, age, first, last from Employees";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt("id");
				int age = rs.getInt("age");
				String first = rs.getString("first");
				String last = rs.getString("last");
				
				out.println("ID: " + id + ", age: " + age + ", first: " + first + ", last: " + last + "<br/>");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
	private static void logD(String msg) {
		System.out.println(TAG + ": " + msg);
	}
	
	private static void closeSilently(Closeable closeable) {
		if (closeable == null)
			return;
		try {
			closeable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
