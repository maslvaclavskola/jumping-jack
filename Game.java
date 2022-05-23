import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class Game extends JPanel
{
    Thread repainter;
    private static Random rd = new Random();
    private Plosina[] p = new Plosina[15];
    private int ukazovatko = 0;
    private int s = 0;
    private int variabilita = 150;
    private boolean vlevo,vpravo,nahoru,dolu,mezera;
    private Multicast m = new Multicast();
    private static final int STEP=5;
    private static final int aSTEP=2;
    private long zacatek = System.currentTimeMillis();
    private Mrak m1;
    private Hrac h1;
    private Token t1;

    public Game(Component c){
        setOpaque(false);
        setBackground( new Color(255, 0, 0, 20) );
        repainter = new Thread(new Runnable(){
                public void run(){
                    while(true){
                        repaint();
                        System.out.println("Thread.run");
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
        });
    }

    private void pohybPlosin(){}
    public void start() {
        repainter.start();
        
        
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
        System.out.println("Game started");
    }
    
    public void paint(Graphics g){
        System.out.println("Game.paint");
        Mrak m1 = new Mrak("Mrak1",0,0,100);
        Hrac h1 = new Hrac("Hrac1",200,200);
        Token t1= new Token("T1", rd.nextInt(770),rd.nextInt(400));
        long ted = System.currentTimeMillis();
        int snimek = (int)(ted-zacatek)/5;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.GREEN);
        m1.setPosition(50+snimek,80);
        m1.paint(g2d);
        t1.paint(g2d);
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
            int f2;
            do{f2 = p[(ukazovatko+p.length-1)%p.length].getY()+rd.nextInt(variabilita)-variabilita/2;}while(f2<75||f2>550);
            p[ukazovatko] = new Plosina("",p[ukazovatko].getX()+p[ukazovatko].getWidth()*p.length,f2,60,20);
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

            g2d.drawString("Konec hry, konečné skóre: "+s,350,300);
            g2d.drawString("Pro reset zmáčkni mezerník",600,500);
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
        g2d.drawString("Skóre: "+s,50,50);
        if(h1.collide(t1)){
            s+=250;
            t1 = new Token("T1", rd.nextInt(770),rd.nextInt(400));
        }

        if(t1.getX()<0){
            t1 = new Token("T1", rd.nextInt(770),rd.nextInt(400));
        }else t1.moveLeft(aSTEP);
        //m1.setPosition(500,500);
    }
}