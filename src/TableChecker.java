import java.sql.*;

public class TableChecker {
    Connection conn;
    String platform;
    String username;
    String password;
    String save="INSERT INTO USER_DATA(PLATFORM,USERNAME,PASSWORD,DATE_TIME) VALUES(?,?,?,?)";
    TableChecker() throws SQLException {
        String url = "jdbc:h2:~/testdata";
        String usr = "sa";
        String pass = "";
        String driver = "org.h2.Driver";
        try{
            Class.forName(driver);
            conn = DriverManager.getConnection(url,usr,pass);
            if(conn != null){
                var meta = conn.getMetaData();
            }
            System.out.println("H2 Connected!");
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!checkTable()){
            System.out.println("TABLE NOT FOUND!");
            String create = "CREATE TABLE USER_DATA(PLATFORM VARCHAR(255), USERNAME VARCHAR (255), PASSWORD VARCHAR(100), DATE_TIME DATETIME)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(create);
            System.out.println("TABLE CREATED!");
        }
    }
    private boolean checkTable() throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null,null,"USER_DATA", new String[] {"TABLE"});
        return rs.next();
    }
}
