package demo;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class LogFilter implements Filter {
    
    private static final String TAG = LogFilter.class.getSimpleName();

    @Override
    public void init(FilterConfig config) throws ServletException {
        String testParam = config.getInitParameter("test-param");
        logD("init: testParam=" + testParam);
    }
    
    @Override
    public void destroy() {
        logD("destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String ip = request.getRemoteAddr();
        logD("doFilter: ip=" + ip + ", time=" + new Date().toString());
        chain.doFilter(request, response);
    }

    private static void logD(String msg) {
        System.out.println(TAG + ": " + msg);
    }
}
