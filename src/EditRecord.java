import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Objects;

public class EditRecord {
    private JPanel mainPanel;
    private JLabel title;
    private JLabel prompt;
    private JComboBox<String> comboBox1;
    private JLabel currValue;
    private JLabel prompt1;
    private JPanel tfContainer;
    private JTextField textField;
    private JPasswordField passwordField;
    private JPanel pfContainer;
    private JButton showEdit;
    private JButton cancel;
    private JButton updateButton;
    Image showImg;
    Image hideImg;
    int status = 0;
    String selected = "Platform";

    EditRecord(String platform, String user, String password, String date){
        JFrame dialog = new JFrame("MyGen: Record Editor");
        try{
            showImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/show.png"));
            hideImg = ImageIO.read(GeneratedPassword.class.getResource("/icons/hide.png"));

        }
        catch(Exception e){
            System.out.println("Failed to load resources");
        }
        pfContainer.setVisible(false);
        currValue.setText("Current Value: "+platform);
        showEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(status == 0){
                    showEdit.setIcon(new ImageIcon(hideImg));
                    passwordField.setEchoChar((char) 0);
                    status = 1;
                }
                else{
                    showEdit.setIcon(new ImageIcon(showImg));
                    passwordField.setEchoChar('•');
                    status = 0;
                }
            }
        });
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    selected = (String) e.getItem();
                    if(Objects.equals(selected, "Platform")){
                        pfContainer.setVisible(false);
                        tfContainer.setVisible(true);
                        currValue.setText("Current Value: "+platform);
                        dialog.pack();
                    } else if (Objects.equals(selected, "Username/Email")) {
                        pfContainer.setVisible(false);
                        tfContainer.setVisible(true);
                        currValue.setText("Current Value: "+user);
                        dialog.pack();
                    }
                    else{
                        pfContainer.setVisible(true);
                        tfContainer.setVisible(false);
                        currValue.setText("Current Value: "+password);
                        dialog.pack();
                    }
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(Objects.equals(selected, "Platform")){
                    CustomUpdate cu = new CustomUpdate("UPDATE USER_DATA SET PLATFORM = '"+textField.getText()+"' WHERE DATE_TIME = '"+date+"'");
                    System.out.println("updated");
                    dialog.dispose();
                } else if (Objects.equals(selected, "Username/Email")) {
                    CustomUpdate cu = new CustomUpdate("UPDATE USER_DATA SET USERNAME = '"+textField.getText()+"' WHERE DATE_TIME = '"+date+"'");
                    System.out.println("updated");
                    dialog.dispose();
                }
                else{
                    CustomUpdate cu = new CustomUpdate("UPDATE USER_DATA SET PASSWORD = '"+new String(passwordField.getPassword())+"' WHERE DATE_TIME = '"+date+"'");
                    System.out.println("updated");
                    dialog.dispose();
                }
            }
        });
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);

    }
}