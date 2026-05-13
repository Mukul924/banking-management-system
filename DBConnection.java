import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection
{
    public static Connection getConnection()
    {
        Connection con = null;

        try
        {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            con = DriverManager.getConnection
            (
                "jdbc:oracle:thin:@localhost:1521:xe",
                "YOUR_DB_USERNAME",
                "YOUR_DB_PASSWORD"
            );
        }

        catch(Exception e)
        {
            System.out.println(e);
        }

        return con;
    }
}