package com.dinner.controller.captcha;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtils {

	private static Random random = new Random(); 
	private static final int IMG_WORD_TYPE_INT = 1;
	private static final int IMG_WORD_TYPE_CHAR = 2;
	private static final int IMG_WORD_TYPE_MIX = 3;
	
	private static int CAPTCHA_WIDTH = 140;
	private static int CAPTCHA_HEIGHT = 80;
	
	public static Object[] generateImage() {
		String word = generateImageWord(IMG_WORD_TYPE_MIX, 4);
		Color backGroundColor = Color.WHITE;
		Color fontColor = new Color(25, 60, 170);
		BufferedImage bufImage = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics g = bufImage.getGraphics();
		g.setColor(backGroundColor);
		g.fillRect(0, 0, bufImage.getWidth(), bufImage.getHeight());

		CaptchaBestFitTextRenderer renderer = new CaptchaBestFitTextRenderer();
		renderer.setLeftMargin(20);
		renderer.setRightMargin(20);
		renderer.draw(word, bufImage, fontColor);
		bufImage = renderer.applyFilters(bufImage);
		return new Object[]{word, bufImage};
	}
	
	
	private static String generateImageWord(int type, int length) {
	    String keyWord;
        switch (type)
        {
            case IMG_WORD_TYPE_INT:   //汉字
            	keyWord = getRandomInt(length);
                break;
            case IMG_WORD_TYPE_CHAR:   //数字
            	keyWord = getRandomChar(length);
                break;
            case IMG_WORD_TYPE_MIX:   //混合
            	keyWord = getRandomMix(length);
                break;
            default:   //默认
            	keyWord = getRandomMix(length);
                break;
        }
        return keyWord;
    }

    private static String getRandomInt(int codeCount)
    {
        String ret = "";
        for (int i = 0; i < codeCount; i++)
        {
        	ret += random.nextInt(10);
        }
        return ret;
    }
    
	private static String getRandomChar(int codeCount) {
		String allChar = "ABCDEFGHIJKLMNOPQRSTUWXYZ";
		String ret = "";
		for (int i = 0; i < codeCount; i++){
			ret += allChar.charAt(random.nextInt(allChar.length()));
		}
		return ret;
	}
	private static String getRandomMix(int codeCount) {
		//字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
		String allChar = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
		String ret = "";
		for (int i = 0; i < codeCount; i++){
			ret += allChar.charAt(random.nextInt(allChar.length()));
		}
		return ret;
	}

}
