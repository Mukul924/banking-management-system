import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangePasswordServlet extends HttpServlet
{

    public void service
    (
        HttpServletRequest req,
        HttpServletResponse res
    )

    throws IOException, ServletException
    {

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();


        /* GET DATA */

        String username =
        req.getParameter("username");

        String oldpassword =
        req.getParameter("oldpassword");

        String newpassword =
        req.getParameter("newpassword");


        try
        {

            Connection con = DBConnection.getConnection();


            /* CHECK USER */

            PreparedStatement ps =
            con.prepareStatement
            (
                "select * from employee where email=? and password=?"
            );

            ps.setString(1, username);

            ps.setString(2, oldpassword);

            ResultSet rs =
            ps.executeQuery();


            if(rs.next())
            {

                /* UPDATE PASSWORD */

                PreparedStatement ps1 =
                con.prepareStatement
                (
                    "update employee set password=? where email=?"
                );

                ps1.setString(1, newpassword);

                ps1.setString(2, username);

                int i =
                ps1.executeUpdate();


                if(i > 0)
                {

                    out.println
                    (
                        "<script>"

                        +

                        "alert('Password Changed Successfully');"

                        +

                        "window.location='login.html';"

                        +

                        "</script>"
                    );

                }

                else
                {

                    out.println
                    (
                        "<script>"

                        +

                        "alert('Password Change Failed');"

                        +

                        "window.location='changePassword.html';"

                        +

                        "</script>"
                    );

                }

            }

            else
            {

                out.println
                (
                    "<script>"

                    +

                    "alert('Invalid Username Or Old Password');"

                    +

                    "window.location='changePassword.html';"

                    +

                    "</script>"
                );

            }

        }

        catch(Exception e)
        {

            out.println(e);

        }

    }

}