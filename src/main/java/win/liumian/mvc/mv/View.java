package win.liumian.mvc.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 *
 * Created by liumian on 2016/10/9.
 */
public interface View {


    /**
     * 执行渲染
     * @param model 参数
     * @param request
     * @param response
     * @throws Exception
     */
    void render(Map<String,?> model,HttpServletRequest request, HttpServletResponse response) throws Exception;

}
