package PhotoAlbum_Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyImage extends JPanel {
    BufferedImage img_exmp;

    BufferedImage []album = new BufferedImage[img_num];

    float percent = 0f;

    final static  int img_num = 4;
    final static int width = 1080;
    final static int height = 720;

    public static void main(String[] args) {
        //创建基本窗口
        JFrame frame = new JFrame();
        //大小设置
        frame.setSize(width,height);
        //设置标题
        frame.setTitle("PhotoAlbum");
        //居中显示
        frame.setLocationRelativeTo(null);
        //关闭窗口时JVM停止运行
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //窗口显示
        frame.setVisible(true);

        MyImage image = new MyImage();
        frame.add(image);

        //获取图片元素
        image.initAlbum();
        //比例设置后，图片重绘
        image.setPercent();
    }

    public void setPercent(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                int num = 0;
                while(true){
                    img_exmp = album[num];
                    if(num++ == (img_num - 1))
                        num = 0;
                    while(true){
                        if(percent < 100f){
                            percent += 2f;
                            repaint();
                        }else{
                            while(true){
                                if(percent > 0f) {
                                    percent -= 2f;
                                    repaint();
                                }else break;
                                try{
                                    Thread.sleep(40);
                                }catch(InterruptedException e){
                                    e.printStackTrace();
                                }
                            }
                            percent = 0f;
                            break;
                        }
                        //留足显示的时间
                        try{
                            Thread.sleep(25);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();
    }
    @Override
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        if(null != img_exmp) {
            //淡入效果设置，利用percent变量设置比例
            g2d.setComposite(AlphaComposite.SrcOver.derive(percent / 100f));

            g2d.drawImage(img_exmp, 0, 0, 1080,720, null);
        }
    }

    public void initAlbum(){
        try{
            for (int i = 1; i <= img_num; i++) {
                BufferedImage image = ImageIO.read(MyImage.class.getResource("/PhotoAlbum_Project/Photos/p" + i + ".jpg"));
                album[i - 1] = image;
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
