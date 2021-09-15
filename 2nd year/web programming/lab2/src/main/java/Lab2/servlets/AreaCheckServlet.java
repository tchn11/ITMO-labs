package Lab2.servlets;

import Lab2.beans.Raw;
import Lab2.beans.RawBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class AreaCheckServlet extends HttpServlet {

    private double x_range[] = {-4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0};
    private double r_range[] = {1.0, 2.0, 3.0, 4.0, 5.0};

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.nanoTime();

        String x = req.getParameter("x_val");
        String y = req.getParameter("y_val").trim();
        String r = req.getParameter("r_val");

        if (validateX(x) && validateY(y) && validateR(r)){
            double xValue = Double.parseDouble(x);
            double yValue = Double.parseDouble(y);
            double rValue = Double.parseDouble(r);

            boolean isInside =  insideCircle(xValue, yValue, rValue) ||
                                insideTriangle(xValue, yValue, rValue) ||
                                insideRectangle(xValue, yValue, rValue);

            OffsetDateTime currentTimeObject = OffsetDateTime.now(ZoneOffset.UTC);
            String currentTime;
            try {
                currentTimeObject = currentTimeObject.minusMinutes(Long.parseLong(req.getParameter("timezone")));
                currentTime = currentTimeObject.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            } catch (Exception exception) {
                currentTime = "А где я...";
            }

            String executionTime = String.valueOf(System.nanoTime() - startTime);

            RawBean raws = (RawBean) req.getSession().getAttribute("table");
            if (raws == null) raws = new RawBean();
            raws.getRaws().add(new Raw(xValue, yValue, rValue, currentTime, executionTime, isInside));
            req.getSession().setAttribute("table", raws);
        }

        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }

    private boolean validateX(String x){
        try{
            double double_x = Double.parseDouble(x);
            return Arrays.asList(x_range).contains(double_x);
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean validateY(String y) throws ServletException{
        try{
            double double_y = Double.parseDouble(y);
            return -5.0 <= double_y && double_y <= 3.0;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean validateR(String r){
        try{
            double double_r = Double.parseDouble(r);
            return Arrays.asList(r_range).contains(double_r);
        } catch (NumberFormatException e){
            return false;
        }
    }

    private boolean insideCircle(double x, double y, double r){
        return x <= 0 && y >= 0 && x*x + y*y <= (r/2)*(r/2);
    }

    private boolean insideTriangle(double x, double y, double r){
        return x <= 0 && y <= 0 && x>=-y-r;
    }

    private boolean insideRectangle(double x, double y, double r){
        return x >= 0 && y <= 0 && x <= r && y >= -r;
    }
}
