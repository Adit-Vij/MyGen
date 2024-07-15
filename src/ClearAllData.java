import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ClearAllData {
    ClearAllData(){
        String url = "jdbc:h2:~/testdata";
        String usr = "sa";
        String pass = "";
        String driver = "org.h2.Driver";
        Connection conn;
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,usr,pass);
            if(conn != null){
                var meta = conn.getMetaData();
            }
            System.out.println("H2 Connected!");
            String sql = "DELETE FROM USER_DATA";
            Statement delete = conn.createStatement();
            delete.execute(sql);
            System.out.println("ALL RECORDS DELETED");
            delete.close();
            conn.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
