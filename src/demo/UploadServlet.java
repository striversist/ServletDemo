package demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(name = "UploadServlet", urlPatterns = { "/UploadServlet" })
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static String mUploadPath;
	
	@Override
	public void init() throws ServletException {
		mUploadPath = System.getProperty(PathConfiguration.UPLOAD_PATH);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean isMulti = ServletFileUpload.isMultipartContent(req);
		resp.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		if (!isMulti) {
			out.println("no file uploaded");
			return;
		}
		
        Collection<Part> parts = req.getParts();
        for (Part part : parts) {
            String fileName = part.getSubmittedFileName();
            String destPath = mUploadPath + File.separator + fileName;
            out.println("part.name=" + fileName + ", destPath=" + destPath + "<br/>");
            part.write(destPath);
        }
	}
}
