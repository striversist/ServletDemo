package demo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet {

    private static final String TAG = HelloServlet.class.getSimpleName();
	private static final long serialVersionUID = 1L;
	private static final String GREETINGS = "hello there!";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logD("doGet");
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.println("<h1>" + GREETINGS + "</h1>");
		
		// ��ӡ����headers
		out.println("<ul>");
		Enumeration<String> paramHeaders = req.getHeaderNames();
		while (paramHeaders.hasMoreElements()) {
		    String name = paramHeaders.nextElement();
		    String value = req.getHeader(name);
		    out.println("<li><b>" + name + "</b>: " + value + "</li>");
		}
		out.println("</ul>");
		
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		out.println("<h2>Get first name: " + firstName + "</h2>\n"
				+ "<h2>last name: " + lastName + "</h2>");
		
		// ��ӡ����
		out.println("<ul>");
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
		    String name = paramNames.nextElement();
		    String value = req.getParameter(name);
		    out.println("<li><b>" + name + "</b>: " + value + "</li>");
		}
		out.println("</ul>");
	}
	
	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    doGet(req, resp);
    }

    @Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		super.service(arg0, arg1);
		logD("service");
	}

	@Override
	public void destroy() {
		super.destroy();
		logD("destroy");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		logD("init");
	}

	private static void logD(String msg) {
		System.out.println(TAG + ": " + msg);
	}
}