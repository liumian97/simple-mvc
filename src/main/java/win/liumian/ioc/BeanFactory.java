package win.liumian.ioc;

import win.liumian.util.exception.InitializeException;
import win.liumian.util.exception.NoSuchBeanException;

import java.util.List;

/**
 * 通过反射获取子类示例
 * 所以子类不允许拥有带参构造方法
 *
 * Created by liumian on 2016/10/9.
 */
public interface BeanFactory {

    Object getBean(String beanName) throws NoSuchBeanException;

    List<Object> getAllControllers() throws InitializeException;


}
