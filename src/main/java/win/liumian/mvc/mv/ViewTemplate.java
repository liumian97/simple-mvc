package win.liumian.mvc.mv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liumian on 2016/10/18.
 */
public abstract class ViewTemplate implements View {

    protected Model model;

    public final void render(HttpServletRequest request, HttpServletResponse response) {
        doRender(model, request, response);
    }


    public void setModel(Model model) {
        this.model = model;
    }

    protected abstract void doRender(Model model,
                                     HttpServletRequest request,
                                     HttpServletResponse response);


}
