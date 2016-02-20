package objects;

import java.awt.Color;
import java.awt.Graphics2D;

import main.PlayState;
import main.Texture;

public class Card extends GameObject {

	private boolean visible = false;
	private int color;
	private int value;
	private Texture texture = PlayState.getTextureInstance();
	private double speed = 0.0;
	private double deltaX, deltaY;
	private double angle;
	private float len;
	private int t;

	public Card(int x, int y, int color, int value) {
		super(x, y);
		this.color = color;
		this.value = value;
	}

	@Override
	public void draw(Graphics2D g) {
		if (visible)
			g.drawImage(texture.cards[color][value], (int) x, (int) y, null);
		else {
			g.setColor(Color.orange);
			g.fill(getBounds());
			g.setColor(Color.black);
			g.draw(getBounds());
		}

	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public void moveTo(double tx, double ty) {
		dx = tx;
		dy = ty;

		deltaX = dx - x;
		deltaY = dy - y;

		len = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		speed = 40.0;
		t = 0;
		angle = Math.atan2(deltaY, deltaX);

	}

	public void moveTo(Point pos) {
		dx = pos.x;
		dy = pos.y;

		deltaX = dx - x;
		deltaY = dy - y;

		len = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
		speed = 40.0;
		t = 0;
		angle = Math.atan2(deltaY, deltaX);
	}

	@Override
	public void update() {
		x += Math.cos(angle) * speed;
		y += Math.sin(angle) * speed;

		t += speed;
		if (t >= len) {
			speed = 0;
			x = dx;
			y = dy;

		}

		// float xSpeed = ((float)dx - (float)x) / travelTime;
		// float ySpeed = ((float)dy - (float)y) / travelTime;
		// float factor = 1;
		//
		// x += xSpeed*factor;
		// y += ySpeed*factor;

		// if (Math.abs(dx - x) > 10) {
		// x += xSpeed*factor;
		// }
		// else{
		// x = dx;
		// }
		// if (Math.abs(dy - y) > 10) {
		// y += ySpeed*factor;
		// }
		// else{
		// y = dy;
		// }

		// if(x < dx && dx-x > speed){
		// x += speed;
		// }
		// else if(x > dx && x-dx > speed){
		// x -= speed;
		// }
		// else{
		// x = dx;
		// }
		//
		// if(y < dy && dy-y > speed){
		// y += (float)speed*ratio;
		// }
		// else if(y > dy && y-dy > speed){
		// y -= (float)speed*ratio;
		// }
		// else{
		// y = dy;
		// }
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
