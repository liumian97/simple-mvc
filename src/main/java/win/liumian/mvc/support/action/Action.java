package win.liumian.mvc.support.action;

import win.liumian.mvc.annotation.RequestMethod;
import win.liumian.util.exception.ActionException;
import win.liumian.util.exception.BuilderException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 开发人员设置的每一个uri都是一个Action
 * <p>
 * Created by liumian on 2016/10/9.
 */
public class Action {

    //请求地址
    private String uri;

    //请求方式，GET、POST
    private RequestMethod requestMethod;

    //方法形参名和类型
    private List<Param> params;

    //目标方法
    private Method method;

    //方法的实例
    private Object instance;

    //私有化构造方法
    private Action() {
    }

    public static class ActionBuilder {

        private Action action;

        public ActionBuilder() {
            action = new Action();
        }

        public void setUri(String uri) {
            action.setUri(uri);
        }

        public void setRequestMethod(RequestMethod method) {
            action.setRequestMethod(method);
        }

        public void setParams(List<Param> params) {
            action.setParams(params);
        }

        public void setInstance(Object instance) {
            action.setInstance(instance);
        }

        public void setMethod(Method method) {
            action.setMethod(method);
        }

        public Action build() throws BuilderException {

            check();

            return action;
        }

        private void check() throws BuilderException {
            if (action.uri == null ||
                    action.params == null ||
                    action.requestMethod == null ||
                    action.instance == null ||
                    action.method == null)
                throw new BuilderException("Action all field must be initialize");
        }

    }

    public Method getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    private void setUri(String uri) {
        this.uri = uri;
    }
    

    private void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
    

    private void setParams(List<Param> params) {
        this.params = params;
    }
    

    private void setMethod(Method method) {
        this.method = method;
    }
    

    private void setInstance(Object instance) {
        this.instance = instance;
    }


    /**
     * 执行 Request的目标方法
     * @param request
     * @param response
     * @return 返回值可能是 View的实现类也可能是 String
     * @throws ActionException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object doAction(HttpServletRequest request, HttpServletResponse response) throws ActionException, InvocationTargetException, IllegalAccessException {


        if (params.size() == 0) {
            return method.invoke(instance);
        } else {

            Map<String, Param> requestParams = getRequestParams(request);
            if (requestParams.size() != params.size()) {
                throw new ActionException("params not match");
            }

            Object[] values = new Object[requestParams.size()];
            //按照顺序封装 实参
            for (int i = 0; i < params.size(); i++) {
                Object value = requestParams.get(params.get(i).getName());
                if (value != null) {
                    values[i] = value;
                } else {
                    throw new ActionException("request parameter don't have :" 
                            + params.get(i).getName());
                }
            }
            return method.invoke(instance,values);
        }
    }


    /**
     * 获取请求参数
     *
     * @param request
     * @return 返回的List的长度可能为0
     */
    private Map<String, Param> getRequestParams(HttpServletRequest request) {

        Map<String, Param> params = new HashMap<String, Param>();

        Enumeration<String> requestParams = request.getParameterNames();
        String paramName = null;
        while (requestParams.hasMoreElements()) {
            paramName = requestParams.nextElement();
            String value = request.getParameter(paramName);
            params.put(paramName, new Param(paramName, value));
        }

        return params;
    }

}
