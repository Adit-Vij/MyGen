import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomUpdate {
    String url = "jdbc:h2:~/testdata";
    String usr = "sa";
    String pass = "";
    String driver = "org.h2.Driver";
    Connection conn;
    String platform;
    String username;
    String password;
    String timeStamp;
    Object[] record = new Object[4];
    CustomUpdate(String query) {

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usr, pass);
            if (conn != null) {
                var meta = conn.getMetaData();
            }
            System.out.println("H2 Connected!");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
