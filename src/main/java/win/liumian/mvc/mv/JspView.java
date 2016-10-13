package win.liumian.mvc.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * spring 是使用Template设计模式处理的，我们暂时先直接继承，以后会改进
 * <p>
 * Created by liumian on 2016/10/11.
 */
public class JspView extends AbstractView {


    public JspView(String path) {
        super(path);
    }

    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        for (Map.Entry<String,?> entry:model.entrySet()){
            request.setAttribute(entry.getKey(),entry.getValue());
        }

    }
}
