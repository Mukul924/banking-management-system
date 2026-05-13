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

public class CheckBalanceServlet extends HttpServlet
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


        /* GET ACCOUNT NUMBER */

        String accno =
        req.getParameter("accountno");


        try
        {

            Connection con = DBConnection.getConnection();


            /* FIND ACCOUNT */

            PreparedStatement ps =
            con.prepareStatement
            (
                "select * from accountdata where accountno=?"
            );

            ps.setString(1, accno);

            ResultSet rs =
            ps.executeQuery();


            if(rs.next())
            {

                String name =
                rs.getString(1);

                int balance =
                rs.getInt(8);


                /* SUCCESS ALERT */

                out.println
                (
                    "<script>"

                    +

                    "alert("

                    +

                    "'Balance Details\\n\\n'"

                    +

                    "+'Account Number : "
                    + accno +
                    "\\n'"

                    +

                    "+'Account Holder : "
                    + name +
                    "\\n'"

                    +

                    "+'Available Balance : Rs. "
                    + balance +
                    "'"

                    +

                    ");"

                    +

                    "window.location='userDashboard.html';"

                    +

                    "</script>"
                );

            }

            else
            {

                /* ACCOUNT NOT FOUND */

                out.println
                (
                    "<script>"

                    +

                    "alert('Account Number Not Found');"

                    +

                    "window.location='checkBalance.html';"

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