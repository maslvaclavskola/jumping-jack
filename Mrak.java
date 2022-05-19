//import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.geom.Ellipse2D;
public class Mrak extends Sprite
{
    //private static Scanner sc = new Scanner(System.in);
    //private static Graphics2D g2d = new Graphics2D();
    protected int w,h;
    public Mrak(String jmeno, int x,int y,int w){
        super(jmeno,x,y);
        this.w = w;
        this.h = w/3;
    }
    @Override
    public void paint(Graphics2D g){
        g.fillOval(9+x,31+y,w,h);
        //Ellipse2D();
    } 
    /*@Override
    public void paint(Graphics g){
        Graphics g2d = (Graphics2D)g;
        g2d.setColor(Color.WHITE);
        //g2d.fillOval(this.x,this.y,this.w,this.h);
        g2d.fillOval(50,50,50,50);
    }*/
}