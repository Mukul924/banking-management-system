
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

	res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String role = req.getParameter("role");
        String username = req.getParameter("email");
        String password = req.getParameter("password");

        try {
            if(role.equals("admin")) {
                if(username.equals("MUKUL") && password.equals("JAVA")) {
                    RequestDispatcher rd = req.getRequestDispatcher("adminDashboard.html");
                    rd.forward(req, res);
                } else {
                    pw.println("<h3 style='color:red'>Invalid Admin Credentials</h3>");
                    RequestDispatcher rd = req.getRequestDispatcher("login.html");
                    rd.forward(req, res);
                }
            } else {
                
                Connection con = DBConnection.getConnection();

                PreparedStatement ps = con.prepareStatement(
                    "select * from employee where email=? and password=?");
                ps.setString(1, username);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if(rs.next()) {
                    HttpSession session = req.getSession();
                    session.setAttribute("user", username);
                    RequestDispatcher rd = req.getRequestDispatcher("userDashboard.html");
                    rd.forward(req, res);
                } else {
                    pw.println("<script>alert('Invalid User Credentials')</script>");
                    RequestDispatcher rd = req.getRequestDispatcher("login.html");
                    rd.include(req, res);
                }
            }
        } catch(Exception e) {
            pw.println(e);
        }
    }
}