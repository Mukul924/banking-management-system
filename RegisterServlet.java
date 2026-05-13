
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

	res.setContentType("text/html");
        PrintWriter pw = res.getWriter();

        String name = req.getParameter("name");
        String pass = req.getParameter("pass");
	String Cpass = req.getParameter("Cpass");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");

	if(!Cpass.equals(pass)){
	 pw.println("<h3 style='color:red'>Password and Confirm Password are not same</h3>");		
	}else{

        try {

			Connection con = DBConnection.getConnection();

            // Query
            PreparedStatement ps = con.prepareStatement(
                "insert into employee values(?,?,?,?)");

            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, email);
            ps.setString(4, contact);

            int i = ps.executeUpdate();

            // Redirect / include
           RequestDispatcher rd = req.getRequestDispatcher("login.html");

           if(i > 0)
		{

    		pw.println
    		(
        	"<script>"
        	+
        	"alert('Registered Successfully');"
        	+
        	"window.location='login.html';"
        	+
        	"</script>"
    		);

		}

		else
		{

   		 pw.println
    		(
     		"<script>"
        	+
        	"alert('Registration Failed');"
        	+
        	"window.location='register.html';"
        	+
        	"</script>"
    		);

		}

        } catch(Exception e) {
            pw.println(e);
	    pw.println("<h3 style='color:red'>Registration Failed</h3>");

        }
}
        pw.close();
    }
}