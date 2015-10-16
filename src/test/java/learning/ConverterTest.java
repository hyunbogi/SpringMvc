package learning;

import com.hyunbogi.springmvc.domain.Level;
import com.hyunbogi.springmvc.testutil.AbstractDispatcherServletTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ConverterTest extends AbstractDispatcherServletTest {
    @Test
    public void levelConvert() throws ServletException, IOException {
        setClasses(LevelController.class);
        setLocation("/ConverterTest-context.xml");
        initRequest("/level").addParameter("level", "1");
        runService();

        assertThat(getModelAndView().getModel().get("level"), is(Level.BASIC));
    }

    @Controller
    private static class LevelController {
        @Autowired
        private ConversionService conversionService;

        @RequestMapping("/level")
        public String level(@RequestParam Level level, Model model) {
            model.addAttribute("level", level);
            return "temp";
        }

        @InitBinder
        public void initBinder(WebDataBinder dataBinder) {
            dataBinder.setConversionService(conversionService);
        }
    }
}
