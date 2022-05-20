import java.awt.*;
import java.awt.geom.Rectangle2D;
public class Token extends Sprite{
    public int w,h;
    public Token(String j, int x, int y){
        super(j,x,y);
        this.w = 30;
        this.h = 30;
    }
    public void paint(Graphics2D g){
        g.setColor(Color.YELLOW);
        g.fillOval(9+x,31+y,w,h);
    }
    public Rectangle2D.Double getBounds(){
        return new Rectangle2D.Double(x,y,w,h);
    }
    public int getX(){
        return this.x;
    }
}