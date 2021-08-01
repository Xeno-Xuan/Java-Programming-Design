package LightningFighter_Project;

import java.awt.event.*;

public class Player extends KeyAdapter {
    GameFrame gf;

    public Player(GameFrame gf) {
        this.gf = gf;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        //37 38 39 40  ->左  上  右  下
        switch(keyCode){
            case 37:
                gf.sp.left = true;
                break;
            case 38:
                gf.sp.up = true;
                break;
            case 39:
                gf.sp.right = true;
                break;
            case 40:
                gf.sp.down = true;
                break;
            case 32:
                addBullet();
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        System.out.println(keyCode);
        //37 38 39 40  ->左  上  右  下
        switch(keyCode){
            case 37:
                gf.sp.left = false;
                break;
            case 38:
                gf.sp.up = false;
                break;
            case 39:
                gf.sp.right = false;
                break;
            case 40:
                gf.sp.down = false;
                break;
        }
    }
    public void addBullet(){
        gf.bullets.add(new Bullet(gf.sp.x, gf.sp.y + 25));
    }
}
