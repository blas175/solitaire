package main;

import java.io.Serializable;
import java.util.ArrayList;

public class SaveFile implements Serializable{
	private static final long serialVersionUID = -4980806421696146332L;
	public ArrayList<CardId> cardsIds;

	public SaveFile(ArrayList<CardId> cardsIds) {
		this.cardsIds = cardsIds;
	}
}