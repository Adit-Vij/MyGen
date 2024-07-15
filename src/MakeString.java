import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MakeString {

    String platform;
    String usrname;
    int length;
    boolean[] include = new boolean[4];
    void getFormInfo(String platform, String usrname, int length, boolean up, boolean low, boolean num, boolean special){
        this.platform = platform;
        this.usrname = usrname;
        this.length = length;
        this.include[0] = up;
        this.include[1] = low;
        this.include[2] = num;
        this.include[3] = special;
    }
    StringBuilder pword= new StringBuilder();
    void StringMaker(){
        System.out.println(platform+"\n"+length);
        for(boolean print: include){
            System.out.println(print);
        }
        int j=0;
        Random ran = new Random();
        char x;

        for(int i=0; i<length;i++){
                if(include[j]) {
                    switch (j) {
                        case (0):
                            x = (char) (ran.nextInt(26) + 'A');
                            pword.append(x);
                            break;
                        case (1):
                            x = (char) (ran.nextInt(26) + 'a');
                            pword.append(x);
                            break;
                        case (2):
                            String y = "" + ran.nextInt(9);
                            pword.append(y);
                            break;
                        case (3):
                            String specialChars = "!@#$%^&*()_+-=[]{}|;:'\",.<>?"; // Your special characters
                            int randomIndex = ran.nextInt(specialChars.length());
                            char rsc = specialChars.charAt(randomIndex);
                            pword.append(rsc);
                            break;
                    }
                    if (j == 3) {
                        j = 0;
                    } else {
                        j++;
                    }
                }
                else{
                    if (j == 3) {
                        j = 0;
                        length++;
                    } else {
                        j++;
                        length++;
                    }
                }
        }
    }
    void pwordDialog(){
        GeneratedPassword gp = new GeneratedPassword();
        gp.getInfo(platform, usrname, String.valueOf(pword));
        gp.showDialog();
        gp.regen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pword.delete(0,length);
                StringMaker();
                String newPword = String.valueOf(pword);
                gp.password.setText(newPword);
            }
        });
    }
}
