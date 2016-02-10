package demo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final int MAX_UPLOAD_SIZE = 500 * 1024;
	private static final int MAX_MEM_SIZE = 4 * 1024;
	private static String mUploadPath;
	
	@Override
	public void init() throws ServletException {
		mUploadPath = getServletContext().getInitParameter("upload-path");
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
		
		String uploadTmpDir = mUploadPath + File.separator + "tmp";
		File tmpDir = new File(uploadTmpDir);
		if (!tmpDir.exists()) {
			tmpDir.mkdirs();
		}
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(MAX_MEM_SIZE);
		factory.setRepository(tmpDir);
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_UPLOAD_SIZE);
		
		try {
			List<FileItem> fileItems = upload.parseRequest(new ServletRequestContext(req));
			for (FileItem item : fileItems) {
				String fieldName = item.getFieldName();
				String fileName = item.getName();
				String contentType = item.getContentType();
				boolean isInMemory = item.isInMemory();
				long sizeInBytes = item.getSize();
				out.println("fieldName=" + fieldName + ", fileName=" + fileName
						+ ", contentType=" + contentType + ", isInMemory=" + isInMemory
						+ ", sizeInBytes=" + sizeInBytes + "<br/>");
				
				if (item.isFormField()) {
					continue;
				}
				
				File file = new File(mUploadPath + File.separator + fileName);
				item.write(file);
				out.println("Uploaded fileName:" + fileName + "<br/>");
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
