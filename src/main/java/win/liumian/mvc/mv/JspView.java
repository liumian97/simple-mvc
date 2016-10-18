package win.liumian.mvc.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

/**
 * Created by liumian on 2016/10/18.
 */
public class JspView extends ViewTemplate {


    protected void doRender(Model model, HttpServletRequest request, HttpServletResponse response) {

        Map<String,Object> values = model.getData();

        Set<String> keys = values.keySet();
        for (String key:keys){
            request.setAttribute(key,values.get(key));
        }

    }
}
