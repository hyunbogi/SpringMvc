package learning;

import java.util.Map;

public interface CustomController {
    void control(Map<String, String> params, Map<String, Object> model);
}
