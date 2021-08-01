package LightningFighter_Project;

import javax.swing.*;
import java.awt.*;

import static LightningFighter_Project.GameFrame.FRAME_HEIGHT;

public class TargetPlane extends Thread {
    GameFrame gf;
    int x, y;
    int width = 50, height = 50;
    int speed = 1;
    Image img = new ImageIcon("E:/Pictures/Enemy.png").getImage();

    public TargetPlane(int x, int y,GameFrame gf) {
        this.gf = gf;
        this.x = x;
        this.y = y;
    }

    public TargetPlane(int x, int y, int width, int height, GameFrame gf) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.gf = gf;
    }
    @Override
    public void run(){
        while (true) {
            if(hit()){
                System.out.println("子弹命中...");
                this.speed = 0;
                this.img = new ImageIcon("E:/Pictures/Boom.jpg").getImage();
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                this.img = null;
                break;
            }
            if(this.y >= FRAME_HEIGHT)
                break;
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public boolean hit(){
        Rectangle target_rect = new Rectangle(this.x,this.y,this.width,this.height);
        Rectangle bullet_rect = null;
        for(int i = 0; i < gf.bullets.size(); i++){
            Bullet b = gf.bullets.get(i);
            System.out.println("测试子弹命中");
            bullet_rect = new Rectangle(b.x,b.y - 1,b.width,b.height);
            if(target_rect.intersects(bullet_rect)){
                b.speed = 0;
                b.img = null;
                gf.bullets.remove(b);
                return true;
            }
        }
        return false;
    }
}