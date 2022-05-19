import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.*;
import java.util.Random;
public class Okno extends JFrame
{
    private static Random rd = new Random();
    private Mrak m1;
    private Hrac h1;
    private Plosina[] p = new Plosina[15];
    private BufferedImage[] img = new BufferedImage[1];
    private long zacatek = System.currentTimeMillis();
    private static final int STEP=5;
    private static final int aSTEP=2;
    private boolean vlevo,vpravo,nahoru,dolu,mezera;
    private int ukazovatko = 0;
    private int s = 0;
    private int variabilita = 150;
    private Multicast m = new Multicast();
    private boolean menu = true;
    public static void main(String[] args)
    {
        new Okno().setVisible(true);
    }

    public Okno(){
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        /*if(menu==true){
            
        }
        else{*/
            this.m1 = new Mrak("Mrak1",0,0,100);
            this.h1 = new Hrac("Hrac1",200,200);
            int f;
            for(int i = 0;i<p.length;i++)
                if(i==0){
                    this.p[i] = new Plosina("P"+i,i*60,rd.nextInt(280-31)+300,60,20);
                }else{
                    do{f = p[i-1].getY()+rd.nextInt(variabilita)-variabilita/2;}while(f<75||f>550);
                    this.p[i] = new Plosina("P"+i,i*60,f,60,20);
                }
            addKeyListener(new KeyAdapter(){
                    @Override
                    public void keyPressed(KeyEvent e){
                        switch(e.getKeyCode()){
                            case 37: vlevo=true; break;
                            case 38: nahoru=true; break;
                            case 39: vpravo=true; break;
                            case 40: dolu=true; break;
                            case 32: mezera=true;break;
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e){
                        switch(e.getKeyCode()){
                            case 37: vlevo=false; break;
                            case 38: nahoru=false; break;
                            case 39: vpravo=false; break;
                            case 40: dolu=false; break;
                            case 32: mezera=false;break;
                        }
                    }
                });
            m.addGameCommandListener(new GameCommandListener(){
                    @Override
                    public void onGameCommand(GameCommand c){
                        switch(c.toString()){
                            case "v1": vlevo=true; break;
                            case "n1": nahoru=true; break;
                            case "r1": vpravo=true; break;
                            case "d1": dolu=true; break;
                            case "m1": mezera=true;break;
                            case "v2": vlevo=false; break;
                            case "n2": nahoru=false; break;
                            case "r2": vpravo=false; break;
                            case "d2": dolu=false; break;
                            case "m2": mezera=false;break;
                        }
                    }
                });
            /*try
            {
            img[0] = ImageIO.read(new File("img.jpeg"));
            }
            catch (IOException ioe)
            {
            ioe.printStackTrace();
            }*/
            new Thread(new Runnable(){
                    public void run(){
                        while(true){
                            repaint();
                            try
                            {
                                Thread.sleep(50);
                            }
                            catch (InterruptedException ie)
                            {
                                ie.printStackTrace();
                            }
                        }
                    }
                }).start();
        /*}*/
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        long ted = System.currentTimeMillis();
        int snimek = (int)(ted-zacatek)/5;
        Graphics2D g2d = (Graphics2D)g;
        pozadi(g2d,snimek%800);
        g2d.setColor(Color.GREEN);
        m1.setPosition(50+snimek,80);
        m1.paint(g2d);
        g2d.setColor(Color.WHITE);
        if(vlevo){
            h1.moveLeft(STEP);
        }
        if(vpravo){
            h1.moveRight(STEP);
        }

        if(nahoru){
            for(Plosina pl:p){
                if(h1.collide(pl))
                    h1.skok();
            }}
        if(dolu){
            h1.moveDown(STEP);
        }
        h1.moveLeft(aSTEP);
        h1.paint(g2d);
        for(Plosina pl:p){
            pl.moveLeft(aSTEP);
            pl.paint(g2d);
        }
        if (p[ukazovatko].getX()+p[ukazovatko].getWidth()<0){
            int f;
            do{f = p[(ukazovatko+p.length-1)%p.length].getY()+rd.nextInt(variabilita)-variabilita/2;}while(f<75||f>550);
            p[ukazovatko] = new Plosina("",p[ukazovatko].getX()+p[ukazovatko].getWidth()*p.length,f,60,20);
            ukazovatko=(ukazovatko+1)%p.length;
        }
        for(Plosina pl:p){
            if(h1.collide(pl)){
                h1.setVelocity(0);
                if (h1.collideHead(pl)){
                    h1.setY(pl.getY()+(pl.getHeight()+3));
                }
                else{h1.setY(pl.getY()-(h1.getHeight()-3));}
            }
        }
        if(h1.getX()>800||h1.getX()<-h1.getWidth()||h1.getY()>600){

            g.drawString("Konec hry, konečné skóre: "+s,350,300);
            g.drawString("Pro reset zmáčkni mezerník",600,500);
            if(mezera){
                h1.setX(200);
                h1.setY(0);
                s=0;
                h1.setVelocity(0);
            }
        }else s++;
        for(Plosina pl:p){
            if(h1.collide(pl)){
                pl.snizVydrz();
                if (h1.collideHead(pl)){
                    h1.setY(pl.getY()+(pl.getHeight()+3));
                }
                else{h1.setY(pl.getY()-(h1.getHeight()-3));}
            }
        }
        if(variabilita < 400)variabilita++;
        g.drawString("Skóre: "+s,50,50);
        //m1.setPosition(500,500);
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
