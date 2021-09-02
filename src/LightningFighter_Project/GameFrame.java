package LightningFighter_Project;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Vector;

public class GameFrame extends JFrame {
    public final static int FRAME_HEIGHT = 760;
    public final static int FRAME_WIDTH = 500;
    SourcePlane sp;
    GameFrame frame;
    Vector<Bullet> bullets = new Vector<>();
    Vector<TargetPlane> targets = new Vector<>();
    public GameFrame(){
        frame = this;
        //窗口基本框架
        this.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        this.setTitle("雷霆战机");
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //窗口可见
        this.setVisible(true);
        sp = new SourcePlane();
        sp.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                  repaint();
                  try{
                      Thread.sleep(1);
                  }catch(InterruptedException e){
                      e.printStackTrace();
                  }
                }
            }
        }).start();
        //产生敌机
        new Thread(new Runnable(){
            @Override
            public void run(){
                Random r = new Random();
                while(true){
                    TargetPlane t = new TargetPlane(r.nextInt(FRAME_WIDTH - 50),0,frame);
                    targets.add(t);
                    t.start();
                    try{
                        Thread.sleep(400);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    public void paint(Graphics g){
        System.out.println("画板绘制...");
        BufferedImage image = (BufferedImage) this.createImage(this.getSize().width,this.getSize().height);
        Graphics pencil = image.getGraphics();
        //游戏背景图
        pencil.drawImage(new ImageIcon("E:/Pictures/ZeldaSkywardHD.jpg").getImage(),0,0,null);
        //玩家飞机
        if(sp.x < 0){
            sp.x = 0;
            if(sp.y < 0) {
                sp.y = 0;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else if(sp.y > (FRAME_HEIGHT - 50)) {
                sp.y = FRAME_HEIGHT - 50;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else
                pencil.drawImage(sp.img,sp.x,sp.y,sp.width,sp.height,null);
        }
        else if(sp.x > (FRAME_WIDTH - 50)){
            sp.x = FRAME_WIDTH - 50;
            if(sp.y < 0) {
                sp.y = 0;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else if(sp.y > (FRAME_HEIGHT - 50)) {
                sp.y = FRAME_HEIGHT - 50;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else
                pencil.drawImage(sp.img,sp.x,sp.y,sp.width,sp.height,null);
        }
        else{
            if(sp.y < 0) {
                sp.y = 0;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else if(sp.y > (FRAME_HEIGHT - 50)) {
                sp.y = FRAME_HEIGHT - 50;
                pencil.drawImage(sp.img, sp.x, sp.y, sp.width, sp.height, null);
            }
            else
                pencil.drawImage(sp.img,sp.x,sp.y,sp.width,sp.height,null);
        }

        //发射子弹
        for(int i = 0; i < bullets.size(); i++){
            System.out.println("子弹发射");
            Bullet b = bullets.get(i);
            if(b.y > 0)
            pencil.drawImage(b.img,b.x,b.y -= b.speed,b.width,b.height,null);
            else
                bullets.remove(b);
        }
        for(int i = 0; i < targets.size(); i++){
            System.out.println("敌机出现");
            TargetPlane t = targets.get(i);
            if(t.y < FRAME_HEIGHT)
                pencil.drawImage(t.img,t.x,t.y += t.speed,t.width,t.height,null);
            else
                targets.remove(t);
        }
        //调用draw令其生效
        g.drawImage(image,0,0,null);
    }

    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        Player player = new Player(gf);
        gf.addKeyListener(player);
    }
}
