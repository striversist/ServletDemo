package demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import demo.utils.Log;

@WebFilter(filterName = "AuthenFilter", urlPatterns = { "/*" }, asyncSupported = true)
public class AuthenFilter implements Filter {

    private static final String TAG = AuthenFilter.class.getSimpleName();
    
    @Override
    public void destroy() {
        logD("destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logD("doFilter");
        chain.doFilter(request, response);
//        throw new IOException("aaa test aaa");  // For ErrorHandler test
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        logD("init");
    }

    private static void logD(String msg) {
        Log.d(TAG, msg);
    }
}
