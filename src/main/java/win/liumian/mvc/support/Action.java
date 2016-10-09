package win.liumian.mvc.support;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * 开发人员设置的每一个uri都是一个Action
 *
 * Created by liumian on 2016/10/9.
 */
public class Action {

    //请求地址
    private String uri;

    //请求方式，GET、POST
    private String method;

    //请求参数类型
    private Class<?>[] args;

    //私有化构造方法
    private Action() {
    }

    public static class ActionBuilder{

        private Action action;

        public ActionBuilder() {
            action = new Action();
        }

        public void setUri(String uri){
            action.setUri(uri);
        }

        public void setMethod(String method){
            action.setMethod(method);
        }

        public void setArgs(Class<?>[] args){
            action.setArgs(args);
        }

        public Action build(){

            Assert.isNull(action.args,"args must be initialized");
            Assert.isNull(action.method,"method must be initialized");
            Assert.isNull(action.uri,"uri must be initialized");

            return action;
        }

    }

    private void setUri(String uri) {
        this.uri = uri;
    }

    private void setMethod(String method) {
        this.method = method;
    }

    private void setArgs(Class<?>[] args) {
        this.args = args;
    }

    public String getUri() {
        return uri;
    }

    public String getMethod() {
        return method;
    }

    public Class<?>[] getArgs() {
        return args;
    }
}
