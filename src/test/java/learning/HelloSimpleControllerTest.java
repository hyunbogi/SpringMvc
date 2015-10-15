package learning;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HelloSimpleControllerTest {
    @Test
    public void helloSimpleController() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Spring");
        Map<String, Object> model = new HashMap<>();

        new HelloSimpleController().control(params, model);

        assertThat(model.get("message"), is("Hello Spring"));
    }

    private static class HelloSimpleController extends SimpleController {
        public HelloSimpleController() {
            setRequiredParams(new String[]{"name"});
            setViewName("/WEB-INF/page/hello.jsp");
        }

        @Override
        public void control(Map<String, String> params, Map<String, Object> model) {
            model.put("message", "Hello " + params.get("name"));
        }
    }

    private abstract static class SimpleController implements Controller {
        private String[] requiredParams;
        private String viewName;

        public void setRequiredParams(String[] requiredParams) {
            this.requiredParams = requiredParams;
        }

        public void setViewName(String viewName) {
            this.viewName = viewName;
        }

        @Override
        public final ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
                throws Exception {
            if (viewName == null) {
                throw new IllegalStateException();
            }

            Map<String, String> params = new HashMap<>();
            for (String param : requiredParams) {
                String value = request.getParameter(param);
                if (value == null) {
                    throw new IllegalStateException();
                }
                params.put(param, value);

            }

            Map<String, Object> model = new HashMap<>();
            control(params, model);

            return new ModelAndView(viewName, model);
        }

        public abstract void control(Map<String, String> params, Map<String, Object> model);
    }
}
