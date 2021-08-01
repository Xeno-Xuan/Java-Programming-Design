package LightningFighter_Project;

import javax.swing.*;
import java.awt.*;

public class SourcePlane extends Thread {
    int x = 210,y = 600;
    int width = 50,height = 50;
    int speed = 2;
    Image img = new ImageIcon("E:/Pictures/Fighter.png").getImage();
    boolean up,down,left,right;
    public SourcePlane(){

    };
    public SourcePlane(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void run() {
        while(true){
            if(up) y -= speed;
            if(down) y += speed;
            if(left) x -= speed;
            if(right) x += speed;
            try{
                Thread.sleep(2);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
