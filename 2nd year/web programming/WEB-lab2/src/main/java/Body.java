import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Body {
    public String requestType;
    public String xValue;
    public String yValue;
    public String rValue;


    public Body(String requestType, String xValue, String yValue, String rValue) {
        this.requestType = requestType;
        this.xValue = xValue;
        this.yValue = yValue;
        this.rValue = rValue;
    }

    public static Body getBodyFromRequest(HttpServletRequest req) throws IOException {
        String jsonString = req.getReader().lines()
                .reduce("", (accumulator, actual) -> accumulator + actual);
        Gson gson = new Gson();
        Body body = gson.fromJson(jsonString, Body.class);
        return body;
    }
}
