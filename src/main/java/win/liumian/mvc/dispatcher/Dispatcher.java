package win.liumian.mvc.dispatcher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import win.liumian.ioc.BeanFactory;
import win.liumian.ioc.FactoryBean;
import win.liumian.mvc.annotation.RequestMapping;
import win.liumian.mvc.config.DispatcherStrategy;
import win.liumian.mvc.support.action.Action;
import win.liumian.util.exception.InitializeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 真正分发请求的地方
 * <p>
 * Created by liumian on 2016/10/9.
 */
public class Dispatcher {

    private Log log = LogFactory.getLog(Dispatcher.class);

    private BeanFactory beanFactory;

    private DispatcherStrategy strategy;

    private Map<String, Action> actionMap;

    public Dispatcher(DispatcherStrategy strategy) {
        this.strategy = strategy;
    }

    public void init() {
        try {
            initBeanFactory();
            initActions();
        } catch (InitializeException e) {
            log.error("initializing error " + e.getMessage());
            e.printStackTrace();
        }

    }

    /**
     * 初始化BeanFactory（IOC容器）
     */
    private void initBeanFactory() {
        String clazzPath = strategy.getInitParam("beanFactory");
        if (clazzPath == null) {
            log.error("parameter 'beanFactory' can't be null");
        }
        try {
            beanFactory = new FactoryBean(clazzPath).getBeanFactory();
        } catch (ClassNotFoundException e) {
            log.error("can't find class by path: " + clazzPath);
        } catch (IllegalAccessException e) {
            log.error("can't create instance for : " + beanFactory.getClass().getName());
        } catch (InstantiationException e) {
            log.error("can't create instance for : " + beanFactory.getClass().getName());
        }
    }


    /**
     * 初始化所有的Action，即开发者定义的访问路径
     *
     * @throws InitializeException
     */
    private void initActions() throws InitializeException {
        actionMap = new HashMap<String, Action>();

        List<Object> objects = beanFactory.getAllControllers();
        for (Object obj : objects) {

            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    Action.ActionBuilder builder = new Action.ActionBuilder();
                    RequestMapping mapping = method.getAnnotation(RequestMapping.class);
                    builder.setUri(mapping.uri());
                    builder.setMethod(mapping.method());
                    builder.setArgs(method.getParameterTypes());
                    actionMap.put(mapping.uri(), builder.build());

                }

            }

        }


    }


    public boolean doDispatch(HttpServletRequest request, HttpServletResponse response) {

        String uri = request.getServletPath();
        if(actionMap.containsKey(uri)){

            return true;
        }else {
            return false;
        }

    }

}
