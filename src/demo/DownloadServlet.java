package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import demo.utils.Utils;

@WebServlet(name="DownloadServlet", urlPatterns={"/download"})
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String FILE_PATH = "D:\\Projects\\J2EE\\ServletDemo\\downloads\\light.jpg";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			System.err.println("file not exist");
			return;
		}
		
		String contentType = getServletContext().getMimeType(file.getAbsolutePath());
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		resp.setContentType(contentType);
		resp.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\";");
		resp.setContentLengthLong(file.length());
		
		FileInputStream fis = new FileInputStream(file);
		OutputStream os = resp.getOutputStream();
		
		try {
			int byteRead = 0;
			byte[] buffer = new byte[4 * 1024];
			while ((byteRead = fis.read(buffer)) != -1) {
				os.write(buffer, 0, byteRead);
			}
			os.flush();
		} finally {
			Utils.closeSilently(fis);
			Utils.closeSilently(os);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
