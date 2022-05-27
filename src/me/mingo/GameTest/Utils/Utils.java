package me.mingo.GameTest.Utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;

import me.mingo.GameTest.Game;
import me.mingo.GameTest.Window;

public class Utils {
	
	public static void outputError(String text) {
		String fileName = "debug_log.txt";
		
		try {
			PrintWriter out = new PrintWriter(fileName);
			
			LocalDate todaysDate = LocalDate.now();
			out.println(todaysDate);
			out.println(text);
			out.close();
			Game.Quit();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
