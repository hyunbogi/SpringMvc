package learning;

import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import org.junit.Test;

import javax.servlet.ServletException;
import java.io.IOException;

public class CustomHandlerAdapterTest extends AbstractDispatcherServletTest {
    @Test
    public void helloCustomController() throws ServletException, IOException {
        setClasses(CustomHandlerAdapter.class, HelloCustomController.class);
        initRequest("/hello").addParameter("name", "Spring").runService();

        assertViewName("/WEB-INF/page/hello.jsp");
        assertModel("message", "Hello Spring");
    }
}
