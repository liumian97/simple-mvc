package win.liumian.ioc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import win.liumian.util.exception.InitializeException;
import win.liumian.util.exception.NoSuchBeanException;

import javax.management.AttributeList;
import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by liumian on 2016/10/9.
 */
public class SpringBeanFactory implements BeanFactory {

    private Log log = LogFactory.getLog(SpringBeanFactory.class);

    private ApplicationContext applicationContext;

    private List<Object> controllers;

    public void init(ServletContext context) throws NoSuchBeanException {
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
        controllers = new ArrayList();
        log.info("init Spring Bean Factory");
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String name : beanNames) {
            log.info("get bean '" + name + "'");
            try {
                controllers.add(applicationContext.getBean(name));
            } catch (Exception e) {
                log.error("No Such Bean '" + name + "' Exist");
                throw new NoSuchBeanException("No Such Bean '" + name + "' Exist");
            }
        }
    }

    public List<Object> getAllControllers() throws InitializeException {

        String[] names = applicationContext.getBeanDefinitionNames();
        if (names == null || names.length == 0) {
            log.error("controller size at least one");
            throw new InitializeException("controller size at least one");
        }
        controllers = new AttributeList(names.length);

        for (String name : names) {
            controllers.add(applicationContext.getBean(name));
        }
        return controllers;
    }

    public Object getBean(String beanName) throws NoSuchBeanException {
        return null;
    }
}
