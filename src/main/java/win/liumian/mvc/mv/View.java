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


    void render(HttpServletRequest request, HttpServletResponse response);

}
