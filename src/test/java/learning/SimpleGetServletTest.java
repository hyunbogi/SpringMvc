package learning;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SimpleGetServletTest {
    @Test
    public void simpleServlet() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");

        MockHttpServletResponse response = new MockHttpServletResponse();

        SimpleGetServlet servlet = new SimpleGetServlet();
        servlet.service(request, response);

        //assertThat(response.getContentAsString(), is("<HTML><BODY>Hello Spring</BODY></HTML>"));
        assertThat(response.getContentAsString().contains("Hello Spring"), is(true));
    }
}
