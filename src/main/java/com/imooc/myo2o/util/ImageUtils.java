package com.imooc.myo2o.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtils {
	public static String absolutePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("C:/Users/hh/Pictures/manyCoder.jpg")).size(600, 400)
				.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(absolutePath+"/coding.jpg")), 0.35f).outputQuality(0.8)
				.toFile(new File("C:/Users/hh/Pictures/manyCoderWithCoding.jpg"));
		
//		
//		System.out.println(System.getProperty("os.name"));
//		Properties properties = System.getProperties();
//		String[] split = properties.toString().split(",");
//		for (String string : split) {
//			System.out.println(string);
//		}
		
	}
	
	
}
