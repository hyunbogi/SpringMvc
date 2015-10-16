package learning;

import com.hyunbogi.springmvc.domain.Level;
import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import com.hyunbogi.springmvc.web.converter.LevelPropertyEditor;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ProperyEditorTest extends AbstractDispatcherServletTest {
    @Test
    public void charsetEditor() {
        CharsetEditor charsetEditor = new CharsetEditor();
        charsetEditor.setAsText("UTF-8");
        assertThat(charsetEditor.getValue(), is(instanceOf(Charset.class)));
        assertThat(charsetEditor.getValue(), is(Charset.forName("UTF-8")));
    }

    @Test
    public void levelPropertyEditor() {
        LevelPropertyEditor levelEditor = new LevelPropertyEditor();

        levelEditor.setAsText("3");
        assertThat(levelEditor.getValue(), is(Level.GOLD));

        levelEditor.setValue(Level.BASIC);
        assertThat(levelEditor.getAsText(), is("1"));
    }

    @Test
    public void webDataBinder() {
        WebDataBinder dataBinder = new WebDataBinder(null);
        dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        assertThat(dataBinder.convertIfNecessary("1", Level.class), is(Level.BASIC));
    }

    @Test
    public void memberController() throws ServletException, IOException {
        setClasses(MemberController.class);
        initRequest("/member/add").addParameter("id", "1000").addParameter("age", "1000");
        runService();

        Member member = (Member) getModelAndView().getModel().get("member");
        assertThat(member.id, is(1000));
        assertThat(member.age, is(200));
    }

    @Test
    public void charsetInParameter() throws ServletException, IOException {
        setClasses(MemberController.class);
        initRequest("/member/charsettest").addParameter("charset", "UTF-8");
        runService();

        Charset charset = (Charset) getModelAndView().getModel().get("charset");
        assertThat(charset.name(), is("UTF-8"));
    }

    @Test
    public void levelInParameter() throws ServletException, IOException {
        setClasses(MemberController.class);
        initRequest("/member/leveltest").addParameter("level", "1");
        runService();

        assertThat(getModelAndView().getModel().get("level"), is(Level.BASIC));
    }

    @Controller
    @RequestMapping("/member")
    private static class MemberController {
        @RequestMapping("/add")
        public String add(@ModelAttribute Member member) {
            return "temp";
        }

        @RequestMapping("/charsettest")
        public String charset(@RequestParam Charset charset, Model model) {
            model.addAttribute("charset", charset);
            return "temp";
        }

        @RequestMapping("/leveltest")
        public String level(@RequestParam Level level, Model model) {
            model.addAttribute("level", level);
            return "temp";
        }

        @InitBinder
        public void initBinder(WebDataBinder dataBinder) {
            dataBinder.registerCustomEditor(int.class, "age", new MinMaxPropertyEditor(0, 200));
            dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        }
    }

    private static class MinMaxPropertyEditor extends PropertyEditorSupport {
        private int min;
        private int max;

        public MinMaxPropertyEditor(int min, int max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public String getAsText() {
            return String.valueOf(getValue());
        }

        @Override
        public void setAsText(String text) throws IllegalArgumentException {
            Integer val = Integer.parseInt(text);
            if (val < min) {
                val = min;
            } else if (val > max) {
                val = max;
            }
            setValue(val);
        }
    }

    private static class Member {
        private int id;
        private int age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
