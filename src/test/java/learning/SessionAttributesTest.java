package learning;

import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class SessionAttributesTest extends AbstractDispatcherServletTest {
    @Test
    public void sessionAttr() throws ServletException, IOException {
        setClasses(UserController.class);

        // GET
        initRequest("/user/edit").addParameter("id", "1");
        runService();
        HttpSession session = request.getSession();
        assertThat(session.getAttribute("user"), is(getModelAndView().getModel().get("user")));

        // POST
        initRequest("/user/edit", "POST").addParameter("id", "1").addParameter("name", "Spring2");
        request.setSession(session);
        runService();
        assertThat(((User) getModelAndView().getModel().get("user")).getEmail(), is("mail@spring.com"));
        assertThat(session.getAttribute("user"), is(nullValue()));
    }

    @Controller
    @SessionAttributes("user")
    private static class UserController {
        @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
        public User form(@RequestParam int id) {
            return new User(1, "Spring", "mail@spring.com");
        }

        @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
        public void submit(@ModelAttribute User user, SessionStatus sessionStatus) {
            sessionStatus.setComplete();
        }
    }

    private static class User {
        private int id;
        private String name;
        private String email;

        public User() {
        }

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
