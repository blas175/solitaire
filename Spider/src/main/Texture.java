package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Texture {
	private static final int TILE_WIDTH = PlayState.TILE_WIDTH;
	private BufferedImage cardsImage;
	public BufferedImage[][] cards = new BufferedImage[4][13];

	public Texture() {

		BufferedImageLoader loader = new BufferedImageLoader();
		cardsImage = loader.loadImage("/cards.png");

		int width = cardsImage.getWidth() / 13;
		int height = cardsImage.getHeight() / 4;
		float ratio = (float) height / (float) width;

		for (int i = 0; i < cards.length; i++) {
			for (int j = 0; j < cards[i].length; j++) {
				cards[i][j] = cardsImage.getSubimage(j * width, i * height, width, height);
				cards[i][j] = resize(cards[i][j], TILE_WIDTH, (int) (TILE_WIDTH * ratio));
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
