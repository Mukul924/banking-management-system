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

public class TransferServlet extends HttpServlet {

    public void service(
            HttpServletRequest req,
            HttpServletResponse res)

            throws IOException, ServletException {

        res.setContentType("text/html");

        PrintWriter out = res.getWriter();

        /* GET DATA */

        String fromacc = req.getParameter("fromaccount");

        String toacc = req.getParameter("toaccount");

        int amount = Integer.parseInt(
                req.getParameter("amount"));

        /* DATE */

        Date d = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String date = sdf.format(d);

        try {

            /* DRIVER */

                Connection con = DBConnection.getConnection();

            /* SAME ACCOUNT CHECK */

            if (fromacc.equals(toacc)) {

                out.println(
                        "<script>"
                                +
                                "alert('Cannot Transfer To Same Account');"
                                +
                                "window.location='transfer.html';"
                                +
                                "</script>");

            }

            else {

                /* SENDER ACCOUNT */

                PreparedStatement ps1 = con.prepareStatement(
                        "select * from accountdata where accountno=?");

                ps1.setString(1, fromacc);

                ResultSet rs1 = ps1.executeQuery();

                /* RECEIVER ACCOUNT */

                PreparedStatement ps2 = con.prepareStatement(
                        "select * from accountdata where accountno=?");

                ps2.setString(1, toacc);

                ResultSet rs2 = ps2.executeQuery();

                if (rs1.next() && rs2.next()) {

                    int senderbalance = rs1.getInt(8);

                    int receiverbalance = rs2.getInt(8);

                    /* INSUFFICIENT BALANCE */

                    if (senderbalance < amount) {

                        out.println(
                                "<script>"
                                        +
                                        "alert('Insufficient Balance');"
                                        +
                                        "window.location='transfer.html';"
                                        +
                                        "</script>");

                    }

                    else {

                        int newsenderbalance = senderbalance - amount;

                        int newreceiverbalance = receiverbalance + amount;

                        /* DEBIT SENDER */

                        PreparedStatement ps3 = con.prepareStatement(
                                "update accountdata set balance=? where accountno=?");

                        ps3.setInt(1, newsenderbalance);

                        ps3.setString(2, fromacc);

                        ps3.executeUpdate();

                        /* CREDIT RECEIVER */

                        PreparedStatement ps4 = con.prepareStatement(
                                "update accountdata set balance=? where accountno=?");

                        ps4.setInt(1, newreceiverbalance);

                        ps4.setString(2, toacc);

                        int i = ps4.executeUpdate();

                        if (i > 0) {

                            /* INSERT INTO TRANSACTION TABLE */

                            PreparedStatement ps5 = con.prepareStatement(
                                    "insert into transaction values(?,?,?,?,?)");

                            String senderName = rs1.getString(1);

                            ps5.setString(
                                    1,
                                    senderName);

                            ps5.setLong(
                                    2,
                                    Long.parseLong(fromacc));

                            ps5.setLong(
                                    3,
                                    Long.parseLong(toacc));

                            ps5.setInt(
                                    4,
                                    amount);

                            ps5.setDate(
                                    5,
                                    new java.sql.Date(
                                            System.currentTimeMillis()));

                            ps5.executeUpdate();

                            out.println(
                                    "<script>"
                                            +
                                            "alert("
                                            +
                                            "'Money Transferred Successfully\\n\\n'"
                                            +
                                            "+'From Account : "
                                            + fromacc +
                                            "\\n'"
                                            +
                                            "+'To Account : "
                                            + toacc +
                                            "\\n'"
                                            +
                                            "+'Transferred Amount : Rs. "
                                            + amount +
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
                                            "</script>");

                        }

                        else {

                            out.println(
                                    "<script>"
                                            +
                                            "alert('Transfer Failed');"
                                            +
                                            "window.location='transfer.html';"
                                            +
                                            "</script>");

                        }

                    }

                }

                else {

                    out.println(
                            "<script>"
                                    +
                                    "alert('Invalid Account Number');"
                                    +
                                    "window.location='transfer.html';"
                                    +
                                    "</script>");

                }

            }

        }

        catch (Exception e) {

            out.println(e);

        }

    }

}