package learning;

import org.junit.Test;

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
}
