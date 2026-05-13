import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.SimpleDateFormat;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreditServlet extends HttpServlet
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

        String accno = req.getParameter("accountno");

        int amount =
        Integer.parseInt
        (
            req.getParameter("amount")
        );


        /* DATE */

        Date d = new Date();

        SimpleDateFormat sdf =
        new SimpleDateFormat("dd-MM-yyyy");

        String date = sdf.format(d);


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

            ResultSet rs = ps.executeQuery();


            if(rs.next())
            {

                String name = rs.getString(1);

                int balance = rs.getInt(8);


                /* NEW BALANCE */

                int newbalance =
                balance + amount;


                /* UPDATE BALANCE */

                PreparedStatement ps1 =
                con.prepareStatement
                (
                    "update accountdata set balance=? where accountno=?"
                );

                ps1.setInt(1, newbalance);

                ps1.setString(2, accno);

                int i = ps1.executeUpdate();


                /* CREDIT HISTORY */
		
		PreparedStatement ps2 =
		con.prepareStatement
		(
    		"insert into credit values(?,?,to_date(?,'dd-mm-yyyy'))"
		);

		ps2.setString(1, name);

		ps2.setInt(2, amount);

		ps2.setString(3, date);

                ps2.executeUpdate();


                if(i > 0)
                {

                    out.println
                    (
                        "<script>"
                        +

                        "alert("
                        +

                        "'Amount Credited Successfully\\n\\n'"

                        +

                        "+'Account Number : "
                        + accno +
                        "\\n'"

                        +

                        "+'Credited Amount : Rs. "
                        + amount +
                        "\\n'"

                        +

                        "+'Updated Balance : Rs. "
                        + newbalance +
                        "\\n'"

                        +

                        "+'Date : "
                        + date +
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

                    out.println
                    (
                        "<script>"
                        +
                        "alert('Credit Failed');"
                        +
                        "window.location='credit.html';"
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
                    "alert('Account Not Found');"
                    +
                    "window.location='credit.html';"
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