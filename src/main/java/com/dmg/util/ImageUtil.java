package com.dmg.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

//	private static final int SIZE1_WIDTH = 600;
//	private static final int SIZE2_WIDTH = 395;
//	private static final int SIZE3_WIDTH = 119;
//
//	private static final int SIZE1_HEIGHT = 338;
//	private static final int SIZE2_HEIGHT = 296;
//	private static final int SIZE3_HEIGHT = 157;

	public static String getImageType(String fileName) {
		return fileName.substring(fileName.indexOf(".") + 1, fileName.length()).toUpperCase();
	}

	public static void saveImage(BufferedImage image, String imageType, String filePath) throws IOException {

		File file = new File(filePath);
		ImageIO.write(image, imageType, file);

	}

	private static BufferedImage getImage(File file) throws IOException {
		
		BufferedImage originalImage = ImageIO.read(file);
		return originalImage;
	}
	
	public static BufferedImage getImage(String filePath) throws IOException{
		
		File image = new File(filePath);
		if(!image.exists()){
	
			Logger.error(ImageUtil.class, "Image does not exist in the path location:"+filePath);
			return null;
//			throw new UtilException("Image does not exist in the path location:"+filePath);
		}
		return getImage(image);
	}
	
	

	public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {

		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;

	}
	
	public static BufferedImage cropImage(BufferedImage originalImage, int x1, int y1,int desWidth, int desHeight){
		
		int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
		BufferedImage cropedImage = new BufferedImage(desWidth, desHeight, type);
		Graphics2D g = cropedImage.createGraphics();
		g.drawImage(originalImage, 0,0,desWidth, desHeight,x1, y1, x1+desWidth, y1+desHeight, null);
		g.dispose();
		return cropedImage;
		
	}
	
	
	public static BufferedImage overlayImage(BufferedImage layout, BufferedImage image, int x1, int y1,int desWidth, int desHeight  ){
		
		if(layout==null ){
			Logger.error(ImageUtil.class, " Layout is null " );
		}
		
		if(image==null){
			Logger.error(ImageUtil.class, " Image is null " );
		}
		
		BufferedImage newImage = layout;
		Graphics2D g = newImage.createGraphics();
		g.drawImage(image,x1, y1, x1+desWidth, y1+desHeight, 0,0,image.getWidth(), image.getHeight(), null);
		g.dispose();
		return newImage;
	}
	
	
//	public static void main(String[] args) throws IOException {
//		
//		
//		BufferedImage image = ImageUtil.getImage("C:/Users/mabdelhadi.WS-DXB-TO-9342/Desktop/img/1344_5474.jpg");
//		BufferedImage portrait = ImageUtil.cropImage(image, 103, 8, 119, 159);
////		BufferedImage name = ImageUtil.cropImage(image, 0, 176, 330, 44);
//		BufferedImage _3to4 = 	ImageUtil.cropImage(image, 18, 0, 293, 220);
//
//		
//		ImageUtil.saveImage(portrait, "jpg", "C:/Users/mabdelhadi.WS-DXB-TO-9342/Desktop/img/portrait.jpg");
//		ImageUtil.saveImage(_3to4, "jpg", "C:/Users/mabdelhadi.WS-DXB-TO-9342/Desktop/img/_4to3.jpg");
//		
//		BufferedImage layout = ImageUtil.getImage("C:/Users/mabdelhadi.WS-DXB-TO-9342/Desktop/img/r2.jpg");
//		
//		BufferedImage overlayImage = ImageUtil.overlayImage(layout, portrait, 136, 8, 119, 159);
//		
////		overlayImage= ImageUtil.overlayImage(overlayImage, name, 0, 176, 391, 44);
//		
//		ImageUtil.saveImage(overlayImage, "jpg", "C:/Users/mabdelhadi.WS-DXB-TO-9342/Desktop/img/_16to9.jpg");
//		
//	}



}
