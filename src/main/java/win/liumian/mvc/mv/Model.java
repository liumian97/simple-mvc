package win.liumian.mvc.mv;

import java.util.Map;

/**
 * Created by liumian on 2016/10/18.
 */
public class Model {

    private Map<String, Object> data;

    private StringBuffer text;


    public Model(String text) {
        this(null, new StringBuffer(text));
    }

    public Model(Map<String, Object> data) {
        this(data, null);
    }

    private Model(Map<String, Object> data, StringBuffer text) {
        this.data = data;
        this.text = text;
    }

    public void addValue(String key,Object value){
        data.put(key,value);
    }

    public void addValues(Map<String,Object> map){
        data.putAll(map);
    }

    public void appendText(String value){
        text.append(value);
    }

    public Map<String,Object> getData(){
        return data;
    }

    public String getText() {
        return text.toString();
    }
}

