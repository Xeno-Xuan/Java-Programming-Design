package IdentifyingCode_Project;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ImageCode {
    static String []strs = {"2","3","4","5","6","7","8","9","a","b","c","d","e","f","g"
                            ,"h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z"};
    public static void main(String[] args) {
        final int width = 200;
        final int height = 50;

        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        Graphics pencil = image.getGraphics();
        //图形验证码 矩形填充
        pencil.setColor(Color.blue);
        pencil.fillRect(0,0,width,height);

        //图形验证码 4个字符显示
        //设置新的画笔颜色
        pencil.setColor(Color.green);
        //设置字体、大小
        pencil.setFont(new Font("楷体",Font.PLAIN,25));

        Random random = new Random();

        //设置字符输出坐标x,y
        int code_x = 10;
        int code_y = (int)(height / 2);
        //设置横轴方向增量
        int code_dlt = 40;

        for (int i = 0; i < 4; i++) {
            //设置num 确定随机取得的字符
            int num = random.nextInt(strs.length);
            //循环每一次，获得取出的字符
            String code = strs[num];
            //画笔将字符串输出
            // 每次输出后横轴上位置增加
            pencil.drawString(code,code_x,code_y);

            code_x += code_dlt;
        }

        //画干扰线，干扰线条数
        final int line_num = 2;
        //设置干扰线颜色
        pencil.setColor(Color.orange);
        for (int i = 0; i < line_num; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);

            int x2 = random.nextInt(width) + 50;
            int y2 = random.nextInt(height);

            pencil.drawLine(x1,y1,x2,y2);
        }

        //将image生成至磁盘文件
        try {
            ImageIO.write(image,"jpg",new File("E:\\code.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
