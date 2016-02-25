package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Texture {
	private static final int TILE_WIDTH = PlayState.TILE_WIDTH;
	private BufferedImage piecesImage;
	public BufferedImage[][] pieces = new BufferedImage[2][6];

	public Texture() {

		BufferedImageLoader loader = new BufferedImageLoader();
		piecesImage = loader.loadImage("/pieces.png");

		int width = piecesImage.getWidth() / 6;
		int height = width;

		for (int i = 0; i < pieces.length; i++) {
			for (int j = 0; j < pieces[i].length; j++) {
				pieces[i][j] = piecesImage.getSubimage(j * width, i * height, width, height);
				pieces[i][j] = resize(pieces[i][j], TILE_WIDTH, TILE_WIDTH);
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
