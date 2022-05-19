import java.awt.Graphics2D;
public abstract class Sprite
{
    protected int x,y;
    protected String jmeno;
    public Sprite(String jmeno ,int x,int y){
        this.x = x;
        this.y = y;
        this.jmeno = jmeno;
    }
    public abstract void paint(Graphics2D g);
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    public void moveLeft(int step){
        x-=step;
    }
    public void moveRight(int step){
        x+=step;
    }
    public void moveUp(int step){
        y-=step;
    }
    public void moveDown(int step){
        y+=step;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}