import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Plosina extends Sprite
{
    public int w,h;
    public int vydrz = 200;
    public Plosina(String j, int x, int y,int w,int h){
        super(j,x,y);
        this.w = w;
        this.h = h;
    }
    public void paint(Graphics2D g){
        g.setColor(new Color(0,0,255,vydrz*255/200));
        Rectangle2D.Double p = getBounds();
        g.fill(p);
    }
    public int getWidth(){
        return this.w;
    }
    public int getHeight(){
        return this.h;
    }
    public Rectangle2D.Double getBounds(){
        if(vydrz<=0){
            return new Rectangle2D.Double(-999,-999,0,0);
        }else{
            return new Rectangle2D.Double(x,y,w,h);
        }
    }
    public void snizVydrz(){
        vydrz-=10;
    }
}
