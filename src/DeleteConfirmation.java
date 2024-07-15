import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteConfirmation {
    private JPanel mainPanel;
    private JButton deleteButton;
    private JButton cancelButton;
    private JLabel recordInfo1;
    private JLabel recordInfo2;
    private JLabel recordInfo3;
    private JLabel recordInfo4;
    String platform;
    String username;
    String date;
    DeleteConfirmation(String platform, String username, String date){
        JFrame dialog = new JFrame("Delete Confirmation");
        recordInfo1.setText("Record for: ");
        recordInfo2.setText("Platform: "+platform);
        recordInfo3.setText("Username/Email: "+ username);
        recordInfo4.setText("Created On: "+ date);
        String sql = "DELETE FROM USER_DATA WHERE PLATFORM = '"+platform+ "' AND USERNAME = '" + username + "' AND DATE_TIME ='"+date+"'";
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CustomUpdate cu = new CustomUpdate(sql);
                dialog.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        dialog.add(mainPanel);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }
}
