package win.liumian.mvc.support.action;

import org.springframework.util.Assert;
import win.liumian.mvc.annotation.RequestMethod;
import win.liumian.util.exception.BuilderException;

import java.util.Map;

/**
 * 开发人员设置的每一个uri都是一个Action
 * <p>
 * Created by liumian on 2016/10/9.
 */
public class Action {

    //请求地址
    private String uri;

    //请求方式，GET、POST
    private RequestMethod method;

    //方法的形参和类型
    private Map<String, Class<?>> args;

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

        public void setMethod(RequestMethod method) {
            action.setMethod(method);
        }

        public void setArgs(Map<String, Class<?>> args) {
            action.setArgs(args);
        }

        public Action build() throws BuilderException {

            check();

            return action;
        }

        private void check() throws BuilderException {
            if (action.uri == null ||
                    action.args == null ||
                    action.method == null)
                throw new BuilderException("Action all field must be initialize");
        }

    }

    private void setUri(String uri) {
        this.uri = uri;
    }


    public String getUri() {
        return uri;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public void setMethod(RequestMethod method) {
        this.method = method;
    }

    public Map<String, Class<?>> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Class<?>> args) {
        this.args = args;
    }
}
