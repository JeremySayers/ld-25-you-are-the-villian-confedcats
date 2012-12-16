package com.confedicats.ld25.sounds;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	private AudioInputStream din;
	private Clip clip;
	private boolean music;
	private static boolean mute = false;
	private static boolean muteBGM = false;
	private static boolean muteSFX = false;
	private static final String PREFIX = "/com/confedicats/ld25/sounds/";
	private static ArrayList<Sound> instances = new ArrayList<Sound>();
	private Sound(AudioInputStream in, boolean music) {
		try {
			din = in;
		    try {
				clip = AudioSystem.getClip();
			} catch (LineUnavailableException e) {
			}
		    this.music = music;
		    instances.add(this);
		} catch (Exception e) {
		}
	}
	public static void clearAll() {
		stopAll();
		instances.clear();
	}
	public static Sound create(String fname, boolean music) {
		return new Sound(getAudioStream(fname), music);
	}
	private static AudioInputStream getAudioStream(String fname) {
		try {
			return AudioSystem.getAudioInputStream(new BufferedInputStream(Sound.class.getResource(PREFIX+fname).openStream()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	} 
	public static boolean isMute() {
		return mute;
	}
	public static boolean isMuteBGM() {
		return muteBGM;
	}
	public static boolean isMuteSFX() {
		return muteSFX;
	}
	private static void pauseAll() {
		for (Sound s:instances)
			s.pause();
	}
	private static void resumeAll() {
		for (Sound s:instances)
			if (s.isMusic())
				s.resume();
	}
	public static void setMute(boolean mute) {
		Sound.mute = mute;
		if (mute)
			pauseAll();
		else
			resumeAll();
	}
	public static void setMuteBGM(boolean muteBGM) {
		Sound.muteBGM = muteBGM;
		if (muteBGM) {
			for (Sound s:instances)
				if (s.isMusic())
					s.pause();
		} else {
			for (Sound s:instances)
				if (s.isMusic())
					s.resume();
		}
	}
	public static void setMuteSFX(boolean muteSFX) {
		Sound.muteSFX = muteSFX;
		if (muteSFX) {
			for (Sound s:instances)
				if (!s.isMusic())
					s.stop();
		}
	}
	public static void stopAll() {
		for (Sound s:instances)
			s.stop();
	}
	public boolean isMusic() {
		return music;
	}
	public boolean isPlaying() {
		try {
			return clip.isRunning();
		} catch (Exception e){
		}
		return false;
	}
	private void pause() {
		try {
			clip.stop();
		} catch (Exception e){
		}
	}
	public void play() {
		playSound();
		try {
			if (!isMute() && isMusic() && !isMuteBGM())
				clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e){
		}
	}
	private void playSound() {
		try {
			clip.open(din);
			if (!isMute() && (isMusic()&&!isMuteBGM() || !isMusic()&&!isMuteSFX()))
				clip.start();
		} catch (Exception e) {
		}
	}
	private void resume() {
		play();
	}
	public void stop() {
		try {
			clip.stop();
			clip.close();
		} catch (Exception e){
		}
	}
}
