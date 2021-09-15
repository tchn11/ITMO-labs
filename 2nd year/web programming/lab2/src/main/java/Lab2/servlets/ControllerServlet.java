package Lab2.servlets;
import Lab2.beans.RawBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getParameter("x_val") != null && req.getParameter("y_val") != null
                && req.getParameter("r_val") != null) {
            getServletContext().getNamedDispatcher("AreaCheckServlet").forward(req, resp);
        }
        else if (req.getParameter("clear") != null){
            //Получение Beans для очистки
            RawBean beans = (RawBean) req.getSession().getAttribute("table");
            if (beans == null) beans = new RawBean();
            beans.getRaws().clear();
            req.getSession().setAttribute("table", beans);
            getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
        }
        else {
            getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}
