package win.liumian.mvc.dispatcher;

import javax.servlet.*;
import java.io.IOException;

/**
 * 模拟Spring MVC中的DispatcherServlet
 * Created by liumian on 2016/10/9.
 */
public class DispatcherServlet extends GenericServlet {


    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {

    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
