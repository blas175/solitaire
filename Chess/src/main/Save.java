package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Save {

	public static void saveGame(PlayState playState) {
		try {
			SaveFile saveFile = new SaveFile(playState);
			FileOutputStream fos = new FileOutputStream("res/save/levels");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(saveFile);
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void loadGame(PlayState playState) {
		try {
			FileInputStream fos = new FileInputStream("res/save/levels");
			ObjectInputStream oos = new ObjectInputStream(fos);
			SaveFile saveFile = (SaveFile) oos.readObject();
			oos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
