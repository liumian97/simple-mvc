package win.liumian.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作用参照Spring MVC 中的RequestMapping🎍注解
 *
 * Created by liumian on 2016/10/9.
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {

    String uri();

    String method() default "GET";

}
