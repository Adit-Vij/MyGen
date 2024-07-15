import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PassForm {
    private JLabel title;
    private JTextField inPlatform;
    private JLabel platform;
    private JLabel length;
    private JSlider inLength;
    private JPanel inclusions;
    private JCheckBox up;
    private JCheckBox low;
    private JCheckBox num;
    private JCheckBox special;
    private JButton submit;
    private JPanel mainPanel;
    private JLabel showNum;
    private JLabel error;
    private JTextField inUsrname;
    private JLabel usrname;

    PassForm(){
        JFrame window = new JFrame("Enter Details");
        inLength.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                showNum.setText(String.valueOf(inLength.getValue()));
            }
        });
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String _platform = inPlatform.getText();
                String _usrname = inUsrname.getText();
                int _len = inLength.getValue();
                boolean[] checkIncl = {up.isSelected(),low.isSelected(),num.isSelected(),special.isSelected()};
                int errCode = -1;

                for(boolean check:checkIncl){
                    if(check){
                        errCode = -1;
                        break;
                    }
                    else{
                        errCode = 1;
                    }
                }
                if(_platform.isEmpty()){
                    errCode = 0;
                }
                if(_usrname.isEmpty()){
                    errCode = 2;
                }
                switch (errCode){
                    case(-1):
                        error.setText("");
                        window.pack();

                        MakeString ms = new MakeString();
                        ms.getFormInfo(_platform,_usrname, _len, up.isSelected(),low.isSelected(),num.isSelected(),special.isSelected());
                        ms.StringMaker();
                        ms.pwordDialog();
                        break;
                    case(0):
                        error.setText("Please Enter Platform Name");
                        window.pack();
                        break;
                    case(1):
                        error.setText("Please Check At Least 1 Inclusion");
                        window.pack();
                        break;
                    case(2):
                        error.setText("Please Enter Username Or Email");
                        window.pack();
                        break;
                }
            }
        });
        window.add(mainPanel);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        window.pack();
    }
}
