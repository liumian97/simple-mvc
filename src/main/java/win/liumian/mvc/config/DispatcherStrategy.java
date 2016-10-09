package win.liumian.mvc.config;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * 根据开发者的配置使用不同的策略：filter分发或者servlet分发
 * <p>
 * Created by liumian on 2016/10/9.
 */
public class DispatcherStrategy {

    private FilterConfig filterConfig;

    private ServletConfig servletConfig;

    public DispatcherStrategy(FilterConfig filterConfig) {
        this(filterConfig, null);
    }

    public DispatcherStrategy(ServletConfig servletConfig) {
        this(null, servletConfig);
    }

    private DispatcherStrategy(FilterConfig filterConfig, ServletConfig servletConfig) {
        this.filterConfig = filterConfig;
        this.servletConfig = servletConfig;
    }

    public String getInitParam(String name) {

        return servletConfig == null ?
                filterConfig.getInitParameter(name) :
                servletConfig.getInitParameter(name);
    }

    public ServletContext getServletConfig() {
        return servletConfig == null ?
                filterConfig.getServletContext() :
                servletConfig.getServletContext();
    }


}
