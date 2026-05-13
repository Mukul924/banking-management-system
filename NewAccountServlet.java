
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewAccountServlet extends HttpServlet
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

        /* GET FORM DATA */

        String uname = req.getParameter("fullname");

        String aadhar = req.getParameter("aadhar");

        String mobile = req.getParameter("mobile");

        String email = req.getParameter("email");

        String father = req.getParameter("fathername");

        String acctype = req.getParameter("accounttype");

        String balance = req.getParameter("balance");

        String gender = req.getParameter("gender");


        /* RANDOM ACCOUNT NUMBER */

        Random random = new Random();

        String numbers = "1234567890";

        char otp[] = new char[11];

        for(int i=0; i<otp.length; i++)
        {

            otp[i] =
            numbers.charAt
            (
                random.nextInt(numbers.length())
            );

        }

        String accno = "";

        for(String s : String.valueOf(otp).split(""))
        {

            accno = accno + s;

        }


        try
        {

            Connection con = DBConnection.getConnection();


            /* CHECK AADHAR ALREADY EXISTS */

            PreparedStatement ps1 =
            con.prepareStatement
            (
                "select * from accountdata where adhar=?"
            );

            ps1.setString(1, aadhar);

            ResultSet rs = ps1.executeQuery();


            if(rs.next()){
		out.println(
        	"<script>"
        	+
      	  	"alert('Account Already Exists With This Aadhar Number');"
       		+
       		"window.location='createaccount.html';"
        	+
        	"</script>"
    		);

		}

            else
            {

                /* INSERT DATA */

		PreparedStatement ps = con.prepareStatement
		(
		"insert into accountdata" +
		"(name,accountno,adhar,mobileno,email,fathername,accounttype,balance,gender)" +
		"values(?,?,?,?,?,?,?,?,?)"
		);

                
                ps.setString(1, uname);

                ps.setString(2, accno);

                ps.setString(3, aadhar);

                ps.setString(4, mobile);

                ps.setString(5, email);

                ps.setString(6, father);

                ps.setString(7, acctype);

                ps.setString(8, balance);

                ps.setString(9, gender);


                int i = ps.executeUpdate();


if(i > 0)
{

    out.println
    (
        "<script>"
        +
        "alert("
        +
        "'Account Created Successfully\\n\\n'+"
        +

        "'Account Number : " + accno + "\\n'+"
        +

        "'Name : " + uname + "\\n'+"
        +

        "'Aadhar : " + aadhar + "\\n'+"
        +

        "'Mobile : " + mobile + "\\n'+"
        +

        "'Email : " + email + "\\n'+"
        +

        "'Father Name : " + father + "\\n'+"
        +

        "'Account Type : " + acctype + "\\n'+"
        +

        "'Balance : " + balance + "\\n'+"
        +

        "'Gender : " + gender + "'"
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
        "alert('Account Creation Failed');"
        +
        "window.location='createaccount.html';"
        +
        "</script>"
    );

}
            }

        }

        catch(Exception e)
        {

            out.println(e);

        }

    }

}