import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
import java.awt.Graphics2D;

public class Okno extends JFrame
{
    private static Random rd = new Random();
    private BufferedImage[] img = new BufferedImage[1];
    private long zacatek = System.currentTimeMillis();
    private boolean showMenu = true;
    private Menu menu;
    private Game game;
    
    public static void main(String[] args)
    {
        Okno okno = new Okno();
        okno.setVisible(true);
        //okno.setLayout(new FlowLayout());
        okno.setSize(800,600);
        okno.setLayout(new GridLayout(0, 1));
        //okno.setLayout(new FlowLayout());
    }

    public Okno(){
        menu = new Menu();
        game = new Game(this);
        menu.setVisible(false);
        game.setVisible(true);
        
        add(menu);
        add(game);
        game.start();
        //System.out.println("F");
        //if (true)return;
    }
    

    @Override
    public void paint(Graphics g){
        super.paint(g);
        long ted = System.currentTimeMillis();
        int snimek = (int)(ted-zacatek)/5;
        Graphics2D g2d = (Graphics2D)g;
        pozadi(g2d,snimek%800);
    }
    private void pozadi(Graphics2D g2d,int pozadi){    
        g2d.setColor(Color.CYAN);
        Rectangle2D.Double nebe = new Rectangle2D.Double(-pozadi,0,1600,600);
        g2d.fill(nebe);
        pozadi2(g2d,pozadi);
        pozadi2(g2d,pozadi-800);
    }

    private void pozadi2(Graphics2D g2d,int pozadi){
        //g.drawLine(10,10,100,100);
        //g.drawString("Ahoj",10,100);
        g2d.setColor(Color.GREEN);
        //Rectangle2D.Double hora1 = new Rectangle2D.Double(-200,-150,400,300);
        g2d.fillPolygon(new int[]{300-pozadi,450-pozadi,600-pozadi}, new int[]{600,450,600},3);
        g2d.fillPolygon(new int[]{-20-pozadi,180-pozadi,380-pozadi}, new int[]{600,400,600},3);
        g2d.fillPolygon(new int[]{500-pozadi,700-pozadi,900-pozadi}, new int[]{600,420,600},3);
        g2d.setColor(Color.WHITE);
        g2d.fillPolygon(new int[]{420-pozadi,450-pozadi,480-pozadi}, new int[]{480,450,480},3);
        //s animacíg2d.fillPolygon(new int[]{150+snimek,200,250}, new int[]{450+snimek,400,450},3);
        g2d.fillPolygon(new int[]{130-pozadi,180-pozadi,230-pozadi}, new int[]{450,400,450},3);
        g2d.fillPolygon(new int[]{644-pozadi,700-pozadi,756-pozadi}, new int[]{470,420,470},3);

        g2d.setColor(Color.YELLOW);
        g2d.fillOval(-50,-10,150,100);
        //Ellipse2D e = new Ellipse2D(10,12,12,12);
        /*g2d.translate(200,450);
        g2d.rotate(Math.PI/4);
        g2d.fill(hora1);
        g2d.rotate(-Math.PI/4);
        g2d.translate(-200,-450);*/
        //g2d.drawImage(img[0],50,50,null);
        //Mrak m1 = new Mrak(50,50,50,50);
        /*public Mrak a = new Mrak(50,50,50,50);
        a.nakresli();*/
    }
}
