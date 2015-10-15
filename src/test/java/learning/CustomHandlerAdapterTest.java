package learning;

import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import learning.annotation.RequiredParams;
import learning.annotation.ViewName;
import org.junit.Test;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class CustomHandlerAdapterTest extends AbstractDispatcherServletTest {
    @Test
    public void helloCustomController() throws ServletException, IOException {
        setClasses(CustomHandlerAdapter.class, HelloCustomController.class);
        initRequest("/hello").addParameter("name", "Spring").runService();

        assertViewName("/WEB-INF/page/hello.jsp");
        assertModel("message", "Hello Spring");
    }

    @Component("/hello")
    private static class HelloCustomController implements CustomController {
        @Override
        @ViewName("/WEB-INF/page/hello.jsp")
        @RequiredParams({"name"})
        public void control(Map<String, String> params, Map<String, Object> model) {
            model.put("message", "Hello " + params.get("name"));
        }
    }

    private interface CustomController {
        void control(Map<String, String> params, Map<String, Object> model);
    }

    private static class CustomHandlerAdapter implements HandlerAdapter {
        @Override
        public boolean supports(Object handler) {
            return (handler instanceof CustomController);
        }

        @Override
        public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            Method m = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);
            ViewName viewName = AnnotationUtils.getAnnotation(m, ViewName.class);
            RequiredParams requiredParams = AnnotationUtils.getAnnotation(m, RequiredParams.class);

            Map<String, String> params = new HashMap<>();
            for (String param : requiredParams.value()) {
                String value = request.getParameter(param);
                if (value == null) {
                    throw new IllegalStateException();
                }
                params.put(param, value);
            }

            Map<String, Object> model = new HashMap<>();

            ((CustomController) handler).control(params, model);
            return new ModelAndView(viewName.value(), model);
        }

        @Override
        public long getLastModified(HttpServletRequest request, Object handler) {
            return -1;
        }
    }
}
