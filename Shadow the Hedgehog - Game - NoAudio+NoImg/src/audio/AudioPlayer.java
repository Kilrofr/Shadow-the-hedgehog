package audio;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	
	public static int MENU = 0;
	public static int westopolis  = 1;
	public static int level1  = 2;

	
	public static int courirmaxAUDIO = 0;
	public static int ring = 1;
	public static int saut = 2;
	public static int powerup = 3;
	public static int chaosblast = 4;
	public static int iamallofme = 5;
	public static int badnickmort = 6;
	public static int mort = 7;
	public static int panneau = 8;
	public static int pics = 9;	
	public static int poing = 10;
	public static int poing2 = 11;
	public static int poing3 = 12;
	public static int LEVELCOMPLETED  = 13;




	private Clip[] songs, effects;
	private int currentSongId;
	private float volume = 0.6f;
	private Random rand = new Random();
		
	public AudioPlayer() {
		loadSongs();
		loadEffects();
		
		playSong(MENU);
		//playEffect(chaosblast);
	}


	
	private void loadSongs() {
		String[] names = { "menu", "westopolis", "level1" };
		

		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() {
		String[] EffectNames = {"vitessemax","ring","saut","powerup","chaosblast","i am","badnickmort","mort","panneau","pics","poing","poing2","poing3", "lvlcomplete"};
		effects = new Clip[EffectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(EffectNames[i]);
		
		updateEffectsVolume();
		
	}
	
	
	private Clip getClip(String name) {
		System.out.println(name);
		URL url = getClass().getResource("/audios/"+name+".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;

		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
		}

		return null;

	}

	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}

	public void setLevelSong(int lvlIndex) {
		if (lvlIndex % 2 == 0)
			playSong(westopolis);
		else
			playSong(level1);
	}

	public void lvlCompleted() {
		stopSong();
		playEffect(LEVELCOMPLETED);
	}

	public void playAttackSound() {
		int start = 10;
		start += rand.nextInt(3);
		playEffect(start);
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

	public void playSong(int song) {
		stopSong();

		currentSongId = song;
		updateSongVolume();
		songs[currentSongId].setMicrosecondPosition(0);
		songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);
	}

//	public void toggleSongMute() {
//		this.songMute = !songMute;
//		for (Clip c : songs ) {
//			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
//			booleanControl.setValue(songMute);
//		}
//	}	
//	public void toggleEffectMute() {
//		this.effectMute = !effectMute;
//		for (Clip c : effects) {
//			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
//			booleanControl.setValue(effectMute);
//		}
//		if (!effectMute)
//			playEffect(powerup);
//	}

	private void updateSongVolume() {

		FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);

	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}

}
