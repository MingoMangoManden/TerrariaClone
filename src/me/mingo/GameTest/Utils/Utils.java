package me.mingo.GameTest.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.World.World;

public class Utils {
	
	public static void outputError(String text) {
		String fileName = "game_data/debug_log.txt";
		
		try {
			PrintWriter out = new PrintWriter(fileName);
			
			LocalDate todaysDate = LocalDate.now();
			out.println(todaysDate);
			out.println(text);
			out.close();
			Game.quit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void initializeGameData() {
		File gameDataFolder = new File("game_data");
		gameDataFolder.mkdir();
	}
	
	public static void saveWorldData(World world) {
		try {
	    	FileOutputStream fileStream = new FileOutputStream("game_data/data.ser");
	    	ObjectOutputStream os = new ObjectOutputStream(fileStream);
	    	
	    	os.writeObject(world);
	    	os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
