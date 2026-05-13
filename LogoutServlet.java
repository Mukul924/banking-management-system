import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet
{

    public void service
    (
        HttpServletRequest req,
        HttpServletResponse res
    )

    throws IOException, ServletException
    {

        res.setContentType("text/html");


        /* GET SESSION */

        HttpSession session =
        req.getSession(false);


        /* DESTROY SESSION */

        if(session != null)
        {

            session.invalidate();

        }


        /* REDIRECT */

        res.sendRedirect("login.html");

    }

}