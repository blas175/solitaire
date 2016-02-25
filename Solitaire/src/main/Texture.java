package main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture {
	private static final int TILE_WIDTH = PlayState.TILE_WIDTH;
//	private static final int TILE_HEIGHT = PlayState.TILE_HEIGHT;
	private BufferedImage cardsImage;
	private SpriteSheet cardsSheet;
	public BufferedImage tile;
	public BufferedImage[][] cards = new BufferedImage[4][13];

	public Texture(){

		BufferedImageLoader loader = new BufferedImageLoader();
		cardsImage = loader.loadImage("/cards.png");
		
		cardsSheet = new SpriteSheet(cardsImage);
		
		int width = cardsImage.getWidth()/13;
		int height = cardsImage.getHeight()/4;
		float ratio = (float)height/(float)width;
				
		for(int i = 0 ; i < cards.length ; i++){
			for(int j = 0 ; j < cards[i].length ; j++){
			cards[i][j] = cardsSheet.grabImage(j, i, width, height);
			cards[i][j] = resize(cards[i][j], TILE_WIDTH, (int) (TILE_WIDTH*ratio));
//			File outputfile = new File("card"+i+"-"+j+".png");
//		    try {
//				ImageIO.write(cards[i][j], "png", outputfile);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			}
		}
	}
	
	public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
	    Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
	    BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g2d = dimg.createGraphics();
	    g2d.drawImage(tmp, 0, 0, null);
	    g2d.dispose();

	    return dimg;
	} 
}
