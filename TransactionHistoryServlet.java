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

public class TransactionHistoryServlet extends HttpServlet
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

        String accno =
        req.getParameter("accountno");

        try
        {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
            con.prepareStatement
            (
                "select * from accountdata where accountno=?"
            );

            ps.setLong
            (
                1,
                Long.parseLong(accno)
            );

            ResultSet rs =
            ps.executeQuery();

            if(rs.next())
            {

                String name =
                rs.getString(1);

                int balance =
                rs.getInt(8);

                out.println
                (
                    "<html>"

                    +

                    "<head>"

                    +

                    "<title>Transaction History</title>"

                    +

                    "<style>"

                    +

                    "body{background:#03122e;color:white;font-family:Arial;padding:30px;}"

                    +

                    ".box{background:#1b2a47;padding:30px;border-radius:20px;width:95%;max-width:1100px;margin:auto;}"

                    +

                    "h1{text-align:center;margin-bottom:30px;}"

                    +

                    "h2{color:#29cf5d;margin-top:40px;}"

                    +

                    "table{width:100%;border-collapse:collapse;margin-top:15px;}"

                    +

                    "th,td{border:1px solid white;padding:12px;text-align:center;}"

                    +

                    "th{background:#29cf5d;}"

                    +

                    ".btn{display:block;width:250px;margin:30px auto;padding:15px;background:#29cf5d;color:white;text-decoration:none;text-align:center;border-radius:12px;font-size:20px;font-weight:bold;}"

                    +

                    "</style>"

                    +

                    "</head>"

                    +

                    "<body>"

                    +

                    "<div class='box'>"

                    +

                    "<h1>Transaction History</h1>"
                );



                /* ACCOUNT DETAILS */

                out.println
                (
                    "<table>"

                    +

                    "<tr>"

                    +

                    "<th>Account Number</th>"

                    +

                    "<th>Account Holder</th>"

                    +

                    "<th>Available Balance</th>"

                    +

                    "</tr>"

                    +

                    "<tr>"

                    +

                    "<td>"+accno+"</td>"

                    +

                    "<td>"+name+"</td>"

                    +

                    "<td>Rs. "+balance+"</td>"

                    +

                    "</tr>"

                    +

                    "</table>"
                );



                /* CREDIT HISTORY */

                out.println
                (
                    "<h2>Credit History</h2>"

                    +

                    "<table>"

                    +

                    "<tr>"

                    +

                    "<th>Name</th>"

                    +

                    "<th>Amount</th>"

                    +

                    "<th>Date</th>"

                    +

                    "</tr>"
                );

                PreparedStatement ps1 =
                con.prepareStatement
                (
                    "select * from credit where name=?"
                );

                ps1.setString(1, name);

                ResultSet rs1 =
                ps1.executeQuery();

                boolean credit =
                false;

                while(rs1.next())
                {

                    credit = true;

                    out.println
                    (
                        "<tr>"

                        +

                        "<td>"+rs1.getString(1)+"</td>"

                        +

                        "<td>Rs. "+rs1.getInt(2)+"</td>"

                        +

                        "<td>"+rs1.getDate(3)+"</td>"

                        +

                        "</tr>"
                    );

                }

                if(!credit)
                {

                    out.println
                    (
                        "<tr>"

                        +

                        "<td colspan='3'>No Credit History</td>"

                        +

                        "</tr>"
                    );

                }

                out.println("</table>");



                /* DEBIT HISTORY */

                out.println
                (
                    "<h2>Debit History</h2>"

                    +

                    "<table>"

                    +

                    "<tr>"

                    +

                    "<th>Name</th>"

                    +

                    "<th>Amount</th>"

                    +

                    "<th>Date</th>"

                    +

                    "</tr>"
                );

                PreparedStatement ps2 =
                con.prepareStatement
                (
                    "select * from debit where name=?"
                );

                ps2.setString(1, name);

                ResultSet rs2 =
                ps2.executeQuery();

                boolean debit =
                false;

                while(rs2.next())
                {

                    debit = true;

                    out.println
                    (
                        "<tr>"

                        +

                        "<td>"+rs2.getString(1)+"</td>"

                        +

                        "<td>Rs. "+rs2.getInt(2)+"</td>"

                        +

                        "<td>"+rs2.getDate(3)+"</td>"

                        +

                        "</tr>"
                    );

                }

                if(!debit)
                {

                    out.println
                    (
                        "<tr>"

                        +

                        "<td colspan='3'>No Debit History</td>"

                        +

                        "</tr>"
                    );

                }

                out.println("</table>");



                /* TRANSFER HISTORY */

                out.println
                (
                    "<h2>Transfer History</h2>"

                    +

                    "<table>"

                    +

                    "<tr>"

                    +

                    "<th>Sender</th>"

                    +

                    "<th>Receiver</th>"

                    +

                    "<th>Amount</th>"

                    +

                    "<th>Date</th>"

                    +

                    "<th>Status</th>"

                    +

                    "</tr>"
                );

                PreparedStatement ps3 =
                con.prepareStatement
                (
                    "select * from transaction where sender=? or reciever=?"
                );

                long account =
                Long.parseLong(accno);

                ps3.setLong(1, account);

                ps3.setLong(2, account);

                ResultSet rs3 =
                ps3.executeQuery();

                boolean transfer =
                false;

                while(rs3.next())
                {

                    transfer = true;

                    long sender =
                    rs3.getLong(2);

                    long receiver =
                    rs3.getLong(3);

                    int amount =
                    rs3.getInt(4);

                    java.sql.Date date =
                    rs3.getDate(5);

                    String status =
                    "";

                    if(sender == account)
                    {

                        status = "Sent";

                    }

                    else
                    {

                        status = "Received";

                    }

                    out.println
                    (
                        "<tr>"

                        +

                        "<td>"+sender+"</td>"

                        +

                        "<td>"+receiver+"</td>"

                        +

                        "<td>Rs. "+amount+"</td>"

                        +

                        "<td>"+date+"</td>"

                        +

                        "<td>"+status+"</td>"

                        +

                        "</tr>"
                    );

                }

                if(!transfer)
                {

                    out.println
                    (
                        "<tr>"

                        +

                        "<td colspan='5'>No Transfer History</td>"

                        +

                        "</tr>"
                    );

                }

                out.println("</table>");



                out.println
                (
                    "<a href='userDashboard.html' class='btn'>Back To Dashboard</a>"

                    +

                    "</div>"

                    +

                    "</body>"

                    +

                    "</html>"
                );

            }

            else
            {

                out.println
                (
                    "<script>"

                    +

                    "alert('Account Number Not Found');"

                    +

                    "window.location='transactionHistory.html';"

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