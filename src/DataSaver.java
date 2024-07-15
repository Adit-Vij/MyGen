import java.sql.*;
import java.time.LocalDateTime;

class DataSaver{
    Connection conn;
    String platform;
    String username;
    String password;
    String save="INSERT INTO USER_DATA(PLATFORM,USERNAME,PASSWORD,DATE_TIME) VALUES(?,?,?,?)";
    DataSaver(){
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
    }
    public void getInfo(String platform,String username,String password) {
        this.platform = platform;
        this.username = username;
        this.password = password;
        System.out.println(platform+": "+password);
    }
    private boolean checkTable() throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet rs = meta.getTables(null,null,"USER_DATA", new String[] {"TABLE"});
        return rs.next();
    }
    void save() throws SQLException {
        if(!checkTable()){
            System.out.println("TABLE NOT FOUND!");
            String create = "CREATE TABLE USER_DATA(PLATFORM VARCHAR(255), USERNAME VARCHAR (255), PASSWORD VARCHAR(100), DATE_TIME DATETIME)";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(create);
            System.out.println("TABLE CREATED!");
        }
        PreparedStatement ps = conn.prepareStatement(save);
        LocalDateTime dateTime = LocalDateTime.now();
        Timestamp timestamp = Timestamp.valueOf(dateTime);
        ps.setString(1,platform);
        ps.setString(2,username);
        ps.setString(3,password);
        ps.setTimestamp(4,timestamp);
        ps.execute();
        System.out.println("SAVED!");

    }
}
