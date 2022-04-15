import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AreaCheckServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String requestTime = (new SimpleDateFormat("HH:mm:ss")).format((Date) context.getAttribute("requestTime"));
        if (context.getAttribute("resultList") == null){
            context.setAttribute("resultList", new ArrayList<String>());
        }

            ArrayList<String> resultList = null;
            Object obj = context.getAttribute("resultList");
            if (obj.getClass().equals(ArrayList.class)){
                resultList = (ArrayList<String>) obj;
            }



        Body body = (Body) context.getAttribute("body");
        String result = "";
        float x, y, r;
        try{
            x = Float.parseFloat(body.xValue);
            y = Float.parseFloat(body.yValue);
            r = Float.parseFloat(body.rValue);
            if (isPointInsideTheArea(x, y, r)){
                result = "попадание";
            } else {
                result = "мимо";
            }
        }catch (NumberFormatException| NullPointerException e){
            e.printStackTrace();
            result = "wrong data format";
        } finally {
            resultList.add(generateRow(body.xValue, body.yValue, body.rValue, requestTime, result));
            context.setAttribute("resultList", resultList);
            req.getRequestDispatcher("/resultTable.jsp").forward(req, resp);
        }
    }

    private boolean isPointInsideTheArea(float x, float y, float r){
        if (x >= 0 && y>0){
            return (Math.sqrt(x*x + y*y) < r/2);
        } else if (x<0 && y >0){
            return (-x < r && y < r);
        } else if (x < 0 && y <= 0){
            return (r + y > -x);
        } else {
            return false;
        }
    }

    private String generateRow(String x, String y, String r, String requestTime, String result){
        return "<tr>\n" +
                "            <td class=\"result-table-td\">" + x + "</td>\n" +
                "            <td class=\"result-table-td\">" + y + "</td>\n" +
                "            <td class=\"result-table-td\">" + r + "</td>\n" +
                "            <td class=\"result-table-td\">\n"+ requestTime + "</td>\n" +
                "            <td class=\"result-table-td last-element-of-row\">" + result + "</td>\n" +
                "        </tr>";
    }

}
