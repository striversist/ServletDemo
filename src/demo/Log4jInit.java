package demo;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Log4jInit implements ServletContextListener {

	public static final String ROOT_PATH = "ROOT_PATH";
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String rootPath = context.getRealPath("/");
		System.setProperty(ROOT_PATH, rootPath);
	}

}
