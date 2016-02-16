package demo;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener(value="For Path Init Listener")
public class PathConfiguration implements ServletContextListener {

	// Root path
	public static final String ROOT_PATH = "ROOT_PATH";
	// Location to store upload file
	public static final String UPLOAD_PATH = "UPLOAD_PATH";
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		String rootPath = context.getRealPath("/");
		System.setProperty(ROOT_PATH, rootPath);
		System.setProperty(UPLOAD_PATH, rootPath + File.separator + "uploads");
	}

}
