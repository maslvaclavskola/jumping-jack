import java.awt.*;
import java.awt.geom.*;

public class Hrac extends Sprite
{
    double v = 0;
    public Hrac(String jmeno ,int x,int y){
        super(jmeno,x,y);
    }
    public void paint(Graphics2D g){
        //g.fillOval(x+9,y+31,20,20);
        g.translate(x,y);
        g.setColor(Color.BLACK);
        g.drawOval(10, 0, 20, 20);
        g.drawLine(20, 20, 20, 50);
        g.drawLine(20, 30, 0, 45);
        g.drawLine(20, 30, 40, 45);
        g.drawLine(20, 50, 5, 75);
        g.drawLine(20, 50, 35, 75);
        v--;
        g.translate(-x,-y);
        moveUp((int)v);
    }
    public boolean collide(Plosina p){
        return new Rectangle2D.Double(x,y,40,getHeight()).intersects(p.getBounds());
    }
    public boolean collideHead(Plosina p){
        return new Rectangle2D.Double(x,y,40,getHeight()/2).intersects(p.getBounds());
    }
    public void setVelocity(double v){
        this.v=v;
    }
    public void skok(){
        v+=20;
    }
    public int getHeight(){
        return 75;
    }
    public int getWidth(){
        return 40;
    }
}