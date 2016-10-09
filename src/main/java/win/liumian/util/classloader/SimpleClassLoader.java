package win.liumian.util.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by liumian on 2016/10/9.
 */
public class SimpleClassLoader extends URLClassLoader {

    public SimpleClassLoader(String classPath) throws MalformedURLException {
        this(new URL[]{new URL(classPath)});
    }

    private SimpleClassLoader(URL[] urls) {
        super(urls);

    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {

        Class clazz = findLoadedClass(name);
        if (clazz == null){
            return super.loadClass(name);
        }else {
            return clazz;
        }
    }
}
