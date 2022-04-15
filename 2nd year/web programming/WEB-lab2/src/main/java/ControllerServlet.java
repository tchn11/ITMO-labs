import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        context.setAttribute("requestTime", new Date());
        Body body = Body.getBodyFromRequest(req);
        context.setAttribute("body", body);

        String requestType = body.requestType;
        if (requestType == null) {
            doGet(req, resp);
            return;
        }
        if (requestType.equals("aim request")) {
            req.getRequestDispatcher("/areaCheckerServlet").forward(req, resp);
        } else if (requestType.equals("clear table")){
            req.getRequestDispatcher("/clearServlet").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}