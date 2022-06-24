package me.mingo.GameTest;

public enum Credits {
	
	ART("Unknown", "Art Producer"),
	MUSIC_PRODUCER("Unknown", "Music Producer"),
	PROGRAMMER("VicDev", "Game Programmer"),
	DEV("VicDev", "Game Developer"),
	PRODUCER("VicDev", "Producer");
	
	public String person;
	public String title;
	
	Credits(String person, String title) {
		this.person = person;
		this.title = title;
	}

}
