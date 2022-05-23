import javax.swing.*;
import java.awt.*;

public class Menu extends JPanel
{
    /*JMenuItem m = new JMenuItem();
    JMenuItem a;
    a.("a");*/
    protected JButton play, close;
    
    public Menu(){
        play = new JButton("Hrát");
        this.add(play);
        close = new JButton("EXIT");
        this.add(close);
        //play.setBounds(100,100,80,30);
        //setLayout(new FlowLayout());
        //play.setVisible(true);
        //setVisible(true);
        //play.addActionListener();
        /*Play.setMnemonic(KeyEvent.VK_D);
        Play.setActionCommand("disable");*/
    }
}
