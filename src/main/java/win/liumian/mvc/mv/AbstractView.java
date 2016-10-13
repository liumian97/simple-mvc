package win.liumian.mvc.mv;

/**
 * Created by liumian on 2016/10/13.
 */
public abstract class AbstractView implements View{

    protected String path;

    public AbstractView(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
