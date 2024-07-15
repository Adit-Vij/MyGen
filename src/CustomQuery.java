import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class CustomQuery {
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
    CustomQuery(String query) {

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usr, pass);
            if (conn != null) {
                var meta = conn.getMetaData();
            }
            System.out.println("H2 Connected!");
            String sql = query;
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                 platform = rs.getString(1);
                 username = rs.getString(2);
                 password = rs.getString(3);
                 timeStamp = String.valueOf(rs.getTimestamp(4));
            }
            System.out.println("Query: "+ sql +" Executed Successfully!");
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
