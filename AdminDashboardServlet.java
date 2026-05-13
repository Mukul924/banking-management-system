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

public class AdminDashboardServlet extends HttpServlet
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


        try
        {

            Connection con = DBConnection.getConnection();


            /* FETCH ACCOUNT DATA */

            PreparedStatement ps =
            con.prepareStatement
            (
                "select * from accountdata"
            );

            ResultSet rs =
            ps.executeQuery();


            /* HTML START */

            out.println
            (
                "<html>"

                +

                "<head>"

                +

                "<title>Admin Dashboard</title>"

                +

                "<style>"

                +

                "*{margin:0;padding:0;box-sizing:border-box;font-family:Arial;}"

                +

                "body{background:#03122e;color:white;padding:40px;}"

                +

                "h1{font-size:45px;margin-bottom:35px;color:#ffd633;text-align:center;}"

                +

                ".table-box{background:#1b2a47;padding:30px;border-radius:20px;box-shadow:0 8px 25px rgba(0,0,0,0.5);}"

                +

                "table{width:100%;border-collapse:collapse;overflow:hidden;border-radius:15px;}"

                +

                "th{background:#233b92;padding:18px;font-size:20px;}"

                +

                "td{padding:16px;text-align:center;border-bottom:1px solid rgba(255,255,255,0.1);font-size:18px;}"

                +

                "tr:hover{background:#24385f;}"

                +

                ".btn{display:block;width:250px;margin:35px auto 0;background:#29cf5d;color:white;text-decoration:none;text-align:center;padding:16px;border-radius:14px;font-size:22px;font-weight:bold;}"

                +

                ".btn:hover{background:#1fb34d;}"

                +

                "</style>"

                +

                "</head>"

                +

                "<body>"

                +

                "<h1>All User Accounts</h1>"

                +

                "<div class='table-box'>"


                +

                "<table>"

                +

                "<tr>"

                +

                "<th>Name</th>"

                +

                "<th>Account No</th>"

                +

                "<th>Aadhar</th>"

                +

                "<th>Mobile</th>"

                +

                "<th>Email</th>"

                +

                "<th>Account Type</th>"

                +

                "<th>Balance</th>"

                +

                "<th>Gender</th>"

                +

                "</tr>"
            );


            /* FETCH ROWS */

            while(rs.next())
            {

                out.println
                (
                    "<tr>"

                    +

                    "<td>"+rs.getString(1)+"</td>"

                    +

                    "<td>"+rs.getString(2)+"</td>"

                    +

                    "<td>"+rs.getString(3)+"</td>"

                    +

                    "<td>"+rs.getString(4)+"</td>"

                    +

                    "<td>"+rs.getString(5)+"</td>"

                    +

                    "<td>"+rs.getString(7)+"</td>"

                    +

                    "<td>Rs. "+rs.getString(8)+"</td>"

                    +

                    "<td>"+rs.getString(9)+"</td>"

                    +

                    "</tr>"
                );

            }


            /* HTML END */

            out.println
            (
                "</table>"

                +

                "<a href='adminDashboard.html' class='btn'>Back To Dashboard</a>"

                +

                "</div>"

                +

                "</body>"

                +

                "</html>"
            );

        }

        catch(Exception e)
        {

            out.println(e);

        }

    }

}