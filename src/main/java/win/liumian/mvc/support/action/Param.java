package win.liumian.mvc.support.action;

/**
 * Created by liumian on 2016/10/14.
 */
public class Param {

    private String name;

    private Class<?> type;

    private Object value;

    public Param(String name,Class<?> type){
        this(name,type,null);
    }

    public Param(String name,Object value){
        this(name,null,value);
    }


    public Param(String name, Class<?> type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
