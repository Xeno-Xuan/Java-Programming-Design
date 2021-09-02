package com.demo.DailyTest.AudioTest;

import javax.sound.sampled.*;
import java.io.File;

/**
 * @Author ：Xuan
 * @Date ：Created in 2021/8/19 23:38
 * @Description：a little program of audio
 * @Modified By：
 * @Version: 1.0$
 */
public class AudioTest {

    //SourceDataLine接收音频用于播放
    public static SourceDataLine sdl;

    public boolean playable = true;

    public AudioTest() {
    }

    public static void play(String fileurl) {
        try {
            //音频文件流
            //获取文件输入流
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileurl));

            //音频数据格式
            AudioFormat aif = ais.getFormat();
            System.out.println("正在播放..." + fileurl);

            //获取与线路数据流相关联的音频格式
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);

            //获取对应线路
            sdl = (SourceDataLine) AudioSystem.getLine(info);

            //SourceDataLine写入前进行打开
            sdl.open(aif);
            sdl.start();

/*            FloatControl fc=(FloatControl)sdl.getControl(FloatControl.Type.MASTER_GAIN);
//value可以用来设置音量，从0-2.0
            double value=2;
            float dB = (float)
                    (Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
            fc.setValue(dB);*/

            //向线路写入数据
            int writeByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (writeByte != -1) {
                writeByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, writeByte);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        sdl.drain();
        sdl.stop();
    }

    public static void main(String[] args) {
        play("E:/Audios/東京フィルハーモニー交響楽団-英雄の証.wav");
    }
}
