package win.liumian.mvc.dispatcher;

import win.liumian.mvc.config.DispatcherStrategy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 使用Filter进行分发
 *
 * Created by liumian on 2016/10/9.
 */
public class DispatcherFilter implements Filter {

    private Dispatcher dispatcher;

    public void init(FilterConfig filterConfig) throws ServletException {
        dispatcher = new Dispatcher(new DispatcherStrategy(filterConfig));
        dispatcher.init();
    }

    /**
     * 在这里进行分发请求
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String viewPath = dispatcher.doDispatch(request,response);
        if(viewPath != null){
            // TODO: 2016/10/9  如何处理其它的过滤器
            request.getRequestDispatcher(viewPath).forward(request,response);

        }else {
            filterChain.doFilter(servletRequest,servletResponse);
        }
    }

    public void destroy() {

    }
}
