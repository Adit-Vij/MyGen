import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class GeneratedPassword {
    private JButton cancelButton;
    private JButton saveButton;
    private JLabel title;
    private JLabel prompt;
    JPasswordField password;
    private JButton show;
    JButton regen;
    private JPanel mainPanel;
    private JCheckBox launch;
    String platform;
    String usrname;
    String pass;
    int status = 0;
    Image showImg;
    Image hideImg;


    GeneratedPassword(){
        try{
            showImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/show.png"));
            hideImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/hide.png"));

        }
        catch(Exception e){
            System.out.println("Failed to load resources");
        }
    }


    void getInfo(String platform, String usrname, String pass){
        this.pass = pass;
        this.platform = platform;
        this.usrname = usrname;
    }
    void showDialog(){
        JFrame dialog = new JFrame("Generated Password");
        title.setText("Generated Password for "+platform+" ("+usrname+")");
        password.setText(pass);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataSaver ds = new DataSaver();
                ds.getInfo(platform, usrname, new String(password.getPassword()));
                try {
                    ds.save();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.dispose();

            }
        });
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
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(mainPanel);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        dialog.pack();
    }
}
