package win.liumian.mvc.support.interceptor;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liumian on 2016/10/11.
 */
public interface Interceptor {

    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception;


    void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception;


}
