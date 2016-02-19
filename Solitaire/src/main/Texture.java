package main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Texture {
	private static final int TILE_WIDTH = PlayState.TILE_WIDTH;
	private BufferedImage tileImage;
	private SpriteSheet tileSheet;
	public BufferedImage tile;
	public BufferedImage[] brick = new BufferedImage[3];

	public Texture(){

		BufferedImageLoader loader = new BufferedImageLoader();
		tileImage = loader.loadImage("/tile.png");
		
		tileSheet = new SpriteSheet(tileImage);
		
		tile = tileSheet.grabImage(0, 0, 200, 200);
		tile = resize(tile, TILE_WIDTH, TILE_WIDTH);
		
		for(int i = 0 ; i < 3 ; i++){

			brick[i] = tileSheet.grabImage(i+1, 0, 200, 200);
			brick[i] = resize(brick[i], TILE_WIDTH, TILE_WIDTH);
		}
	}
	
	public static BufferedImage brickButtonImage(Id id, double size) {
	    BufferedImage img = new BufferedImage((int)(TILE_WIDTH*size*4), (int)(TILE_WIDTH*size*4), BufferedImage.TYPE_INT_ARGB);

	    Graphics2D g = img.createGraphics();
		g.scale(size, size);
	    RenderingHints qualityHints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON );
		qualityHints.put(
				RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY );
		g.setRenderingHints( qualityHints );
		
		switch (id) {
		case b4x1:
			for(int i = 0 ; i < 4 ; i++){
				g.setColor(PlayState.red);
				g.fillRoundRect(PlayState.TILE_BORDER+TILE_WIDTH*i, PlayState.TILE_BORDER, TILE_WIDTH-PlayState.TILE_BORDER*2, TILE_WIDTH-PlayState.TILE_BORDER*2, PlayState.TILE_ROUND, PlayState.TILE_ROUND);
			}
			break;
		case b1x4:
			for(int i = 0 ; i < 4 ; i++){
				g.setColor(PlayState.blue);
				g.fillRoundRect(PlayState.TILE_BORDER, PlayState.TILE_BORDER+TILE_WIDTH*i, TILE_WIDTH-PlayState.TILE_BORDER*2, TILE_WIDTH-PlayState.TILE_BORDER*2, PlayState.TILE_ROUND, PlayState.TILE_ROUND);
			}
			break;
		case b2x2:
			for(int i = 0 ; i < 2 ; i++){
				for(int j = 0 ; j < 2 ; j++){
					g.setColor(PlayState.green);
					g.fillRoundRect(PlayState.TILE_BORDER+TILE_WIDTH*i, PlayState.TILE_BORDER+TILE_WIDTH*j, TILE_WIDTH-PlayState.TILE_BORDER*2, TILE_WIDTH-PlayState.TILE_BORDER*2, PlayState.TILE_ROUND, PlayState.TILE_ROUND);
				}
			}
			break;
		default:
			break;
		}
	    g.dispose();

	    return img;
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
