package com.stong;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 生成二维码，使用RQCode
 */
public class Code {

    /**
     *
     * @param width 二维码的宽度
     * @param height 二维码的宽度
     * @param content 二维码的内容
     * @param type 二维码的类型，图片或者文字等等
     * @param path 生成二维码的路径
     */
    public void getCode(int width,int height,String content,String type,String path){

        //设置二维码的基本信息（纠错等级的设置，损坏程度大小下还可以使用；留白；字符集）
        Map map = new HashMap();
        map.put(EncodeHintType.CHARACTER_SET,"utf-8");
        //留白
        map.put(EncodeHintType.MARGIN,2);
        //设置纠错等级 L（7% 低）， M（15% 中） Q（25%） H（50% 高）
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        //二维码的输出流
        MultiFormatWriter mu = new MultiFormatWriter();
        //使用二维矩阵
        try {
            BitMatrix bit = mu.encode(content, BarcodeFormat.QR_CODE,width,height,map);
            //画图,声明一张图片
            BufferedImage img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            //绘制二维码上的点
            for (int i = 0; i < width ; i++) {
                for (int j = 0; j < height; j++) {
                    //获取某个点上的值，如果bit.get(i,j)则打印一个黑点，反之就是白色
                    double random = Math.random()*10000;
                    int rgb =  bit.get(i, j) ? Color.BLACK.getRGB() : Color.WHITE.getRGB();
                   img.setRGB(i,j,rgb);
                }
            }
            //将图片输出到指定位置，通过File来描述位置对象
            File file = new File(path);
            //将图片写入文件
            Boolean foo = ImageIO.write(img,type,file);
            if(! foo ){
                System.out.println("失败了");
            }else{
                System.out.println(path+"二维码生成完毕");
            }
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static Color randomColor(){
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r,g,b);
    }

    //主程序的入口
    public static void main(String[] args) {
        new Code().getCode(400,400,"wwwww问我午饭是","jpg","/Users/yeon/Desktop/Java/1.jpg");
    }
}
