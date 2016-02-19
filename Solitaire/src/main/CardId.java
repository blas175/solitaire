package main;

public class CardId {
	private int color;
	private int value;
	public CardId(int color, int value) {
		super();
		this.color = color;
		this.value = value;
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
	@Override
	public String toString() {
		return this.color+" : "+this.value;
	}

}
