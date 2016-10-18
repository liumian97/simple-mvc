package win.liumian.mvc.dispatcher;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import win.liumian.ioc.BeanFactory;
import win.liumian.ioc.FactoryBean;
import win.liumian.mvc.annotation.RequestMapping;
import win.liumian.mvc.config.DispatcherStrategy;
import win.liumian.mvc.mv.View;
import win.liumian.mvc.support.action.Action;
import win.liumian.mvc.support.action.Param;
import win.liumian.util.exception.ActionException;
import win.liumian.util.exception.BuilderException;
import win.liumian.util.exception.InitializeException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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
                    try {
                        Action action = initAction(method, obj.getClass());
                        actionMap.put(action.getUri(), action);
                    } catch (BuilderException e) {
                        log.error("Action '"
                                + method.getName()
                                + "' initial fail:"
                                + e.getMessage());
                    }

                }

            }

        }


    }

    private Action initAction(Method method, Object instance) throws BuilderException {
        Action.ActionBuilder builder = new Action.ActionBuilder();
        RequestMapping mapping = method.getAnnotation(RequestMapping.class);
        builder.setUri(mapping.uri());
        builder.setRequestMethod(mapping.method());
        builder.setMethod(method);
        builder.setInstance(instance);

        // 确定访问顺序
        List<Param> params = new ArrayList<Param>();
        Class clazz = instance.getClass();

        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass cc = pool.get(clazz.getName());
            CtMethod cm = cc.getDeclaredMethod(method.getName());

            MethodInfo methodInfo = cm.getMethodInfo();
            CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
            LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
            if (attr == null) {
                log.debug(method.getName() + ": param is null");
            }
            CtClass[] types = cm.getParameterTypes();
            int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
            for (int i = 0; i < types.length; i++) {
                params.add(new Param(attr.variableName(i + pos), types[i].getClass()));
            }

            builder.setParams(params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return builder.build();
    }


    public boolean doService(HttpServletRequest request, HttpServletResponse response){
        Object result = doDispatch(request,response);
        if(result instanceof View){
            View view = (View) result;
            view.render(request,response);
            return true;
        }else {
            return false;
        }

    }


    private Object doDispatch(HttpServletRequest request, HttpServletResponse response) {

        String uri = request.getServletPath();
        if (actionMap.containsKey(uri)) {
            Action action = actionMap.get(uri);
            Object result = null;
            try {
                result = action.doAction(request, response);
            } catch (ActionException e) {
                log.error("invoke method :"
                        + action.getMethod().getName()
                        + " occurs error");
                // TODO: 2016/10/14 发送状态码 400
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            return result;
        } else {
            // TODO: 2016/10/14 发送状态码 404
            return null;
        }

    }


    private Action matchAction(String requestUri) {

        return null;
    }


}
