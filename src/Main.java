import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import javax.swing.*;

public class Main{

    public static void main(String[] args){
        /*UN-COMMENT ONLY AFTER TESTING IS FINISHED*******/
        //ClearAllData cad = new ClearAllData();
        /*DO NOT FORGET TO UN-COMMENT AFTER RUNNING ONCE*/
        try{
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
        }
        catch(Exception e){
            System.out.println("Failed to initialize theme");
        }
        PasswordViewer pv = new PasswordViewer();
    }
}