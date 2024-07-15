import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class PasswordViewer {
    private JPanel mainPanel;
    private JTable data;
    private JPanel tablePanel;
    private JPasswordField password;
    private JButton show;
    private JButton copy;
    private JButton edit;
    private JButton delete;
    private JLabel platform;
    private JLabel username;
    private JButton refreshButton;
    private JButton newPass;
    Image showImg;
    Image hideImg;
    int status = 0;
    String url = "jdbc:h2:~/testdata";
    String usr = "sa";
    String pass = "";
    String driver = "org.h2.Driver";
    String sql = "SELECT * FROM USER_DATA";
    Object[] record = new Object[3];

    void makeTable(){
        try{
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(url,usr,pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            DefaultTableModel model = (DefaultTableModel) data.getModel();
            while(model.getRowCount()>0){
                model.removeRow(0);
            }

            int  cols = rsmd.getColumnCount();
            String[] colName = new String[3];
            for(int i=0; i<3; i++){
                if(i!=2) {
                    colName[i] = rsmd.getColumnName(i + 1);
                }
                else{
                    colName[i] = "DATE&TIME";
                }
            }
            model.setColumnIdentifiers(colName);
            String platform,username,time;
            while(rs.next()){
                platform = rs.getString(1);
                username = rs.getString(2);
                time = rs.getString(4);
                Object[] row= {platform,username,time};
                System.out.println(Arrays.toString(row));
                model.addRow(row);
            }
            stmt.close();
            rs.close();
            conn.close();
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    PasswordViewer(){
        makeTable();
        try{
            showImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/show.png"));
            hideImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/hide.png"));

        }
        catch(Exception e){
            System.out.println("Failed to load resources");
        }
        data.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = data.getSelectedRow();
                    if (selectedRow != -1) {
                        record[0] = data.getModel().getValueAt(selectedRow, 0);
                        record[1] = data.getModel().getValueAt(selectedRow, 1);
                        record[2] = data.getModel().getValueAt(selectedRow, 2);
                        CustomQuery cq = new CustomQuery("SELECT * FROM USER_DATA WHERE PLATFORM = '" + record[0] + "' AND USERNAME ='" + record[1] + "'");
                        platform.setText("Platform: " + record[0]);
                        username.setText("Username/Email: " + record[1]);
                        password.setText(cq.password);

                    }

                }
            }
        });
        newPass.setBorderPainted(false);
        refreshButton.setBorderPainted(false);
        copy.setBorderPainted(false);
        edit.setBorderPainted(false);
        delete.setBorderPainted(false);
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(status == 0){
                    show.setIcon(new ImageIcon(hideImg));
                    password.setEchoChar((char) 0);
                    status = 1;
                }
                else{
                    show.setIcon(new ImageIcon(showImg));
                    password.setEchoChar('•');
                    status = 0;
                }
            }
        });
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String activePassword =  new String(password.getPassword());
                copyToClipboard(activePassword);
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteConfirmation dc = new DeleteConfirmation((String) record[0], (String) record[1], (String) record[2]);
            }
        });
        edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditRecord er = new EditRecord((String) record[0], (String) record[1],new String(password.getPassword()),(String) record[2]);
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                makeTable();
            }
        });
        newPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PassForm pf = new PassForm();
            }
        });
        JFrame window = new JFrame("MyGen: Password viewer");
        window.add(mainPanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public static void copyToClipboard(String string){
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(string);
        clipboard.setContents(selection,null);
    }


}