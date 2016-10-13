package win.liumian.mvc.dispatcher;

import win.liumian.mvc.config.DispatcherStrategy;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 模拟Spring MVC中的DispatcherServlet
 *
 * Created by liumian on 2016/10/9.
 */
public class DispatcherServlet extends GenericServlet {

    private Dispatcher dispatcher;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dispatcher = new Dispatcher(new DispatcherStrategy(config));
        dispatcher.init();
    }

    /**
     * 在这里进行分发请求
     * @param servletRequest
     * @param servletResponse
     * @throws ServletException
     * @throws IOException
     */
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String viewPath = dispatcher.doDispatch(request,response);
        if(viewPath != null){
            request.getRequestDispatcher(viewPath).forward(request,response);
        }else {
            // TODO: 2016/10/9 跳转到 404页面 
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


    /**
     * 向浏览器输出 404或者400 状态码
     * @param code
     */
    private void outputStateCode(int code){

    }

}
