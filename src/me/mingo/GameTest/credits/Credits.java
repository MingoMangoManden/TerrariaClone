package me.mingo.GameTest.credits;

public enum Credits {
	
	PRODUCER("VicDev", "Producer"),
	DEV("VicDev", "Game Developer"),
	PROGRAMMER("VicDev", "Game Programmer"),
	MUSIC_PRODUCER("Unknown", "Music Producer"),
	ART("Unknown", "Art Producer");
	
	public String person;
	public String title;
	
	Credits(String person, String title) {
		this.person = person;
		this.title = title;
	}

}
