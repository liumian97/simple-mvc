package win.liumian.ioc;

/**
 * 根据web.xml配置的BeanFactory路径创建实例
 *
 * Created by liumian on 2016/10/9.
 */
public class FactoryBean {

    private String beanFactoryPath;

    public FactoryBean(String beanFactoryPath) {
        this.beanFactoryPath = beanFactoryPath;
    }

    public BeanFactory getBeanFactory() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class clazz = Class.forName(beanFactoryPath);
        return (BeanFactory)clazz.newInstance();
    }

}
