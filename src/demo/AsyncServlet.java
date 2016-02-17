package demo;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AsyncServlet", urlPatterns = { "/AsyncServlet" }, asyncSupported = true)
public class AsyncServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("进入Servlet的时间: " + new Date() + "<br/>");
        out.flush();
        
        AsyncContext asyncContext = req.startAsync();
        new Thread(new Executor(asyncContext)).start();
        
        out.println("结束Servlet的时间: " + new Date() + "<br/>");
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    class Executor implements Runnable {
        private AsyncContext mAsyncContext;
        public Executor(AsyncContext asyncContext) {
            mAsyncContext = asyncContext;
        }
        
        public void run() {
            try {
                PrintWriter out = mAsyncContext.getResponse().getWriter();
                out.println("业务开始处理时间: " + new Date() + "<br/>");
                out.flush();
                
                Thread.sleep(5000);
                
                out.println("业务处理完毕时间: " + new Date() + "<br/>");
                out.flush();
                mAsyncContext.complete();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
