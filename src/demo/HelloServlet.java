package demo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HelloServlet extends HttpServlet {

    private static final String TAG = HelloServlet.class.getSimpleName();
	private static final long serialVersionUID = 1L;
	private static final String GREETINGS = "hello there!";
	private static final Logger logger = Logger.getLogger(HelloServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logD("doGet");
		
		String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        
        // 设置Cookies
        if (firstName != null && lastName != null) {
            Cookie firstNameCookie = new Cookie("first_name", firstName);
            Cookie lastNameCookie = new Cookie("last_name", lastName);
            firstNameCookie.setMaxAge(3600 * 24);
            lastNameCookie.setMaxAge(3600 * 24);
            resp.addCookie(firstNameCookie);
            resp.addCookie(lastNameCookie);
        }
		
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println("<h1>" + GREETINGS + "</h1>");
		
		// 打印请求headers
		out.println("<ul>");
		Enumeration<String> paramHeaders = req.getHeaderNames();
		while (paramHeaders.hasMoreElements()) {
		    String name = paramHeaders.nextElement();
		    String value = req.getHeader(name);
		    out.println("<li><b>" + name + "</b>: " + value + "</li>");
		}
		out.println("</ul>");
		
		out.println("<h2>Get first name: " + firstName + "</h2>\n"
				+ "<h2>last name: " + lastName + "</h2>");
		
		// 打印表单
		out.println("<ul>");
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
		    String name = paramNames.nextElement();
		    String value = req.getParameter(name);
		    out.println("<li><b>" + name + "</b>: " + value + "</li>");
		}
		out.println("</ul>");
		
		logger.debug("log4j debug log");
		logger.info("log4j info log");
		logger.warn("log4j info log");
		logger.error("log4j error log");
		logger.fatal("log4j fatal log");
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
