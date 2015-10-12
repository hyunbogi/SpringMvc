package learning;

import learning.annotation.RequiredParams;
import learning.annotation.ViewName;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("/hello")
public class HelloCustomController implements CustomController {
    @Override
    @ViewName("/WEB-INF/page/hello.jsp")
    @RequiredParams({"name"})
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
