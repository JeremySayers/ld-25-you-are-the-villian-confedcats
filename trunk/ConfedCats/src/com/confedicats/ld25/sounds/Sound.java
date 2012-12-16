package com.confedicats.ld25.sounds;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Sound {
	private AudioInputStream din;
	private Clip clip;
	private boolean music;
	private static boolean mute = false;
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
			e.printStackTrace();
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
	public static void stopAll() {
		for (Sound s:instances)
			s.stop();
	}
	public boolean isMusic() {
		return music;
	}
	public boolean isPlaying() {
		return clip.isRunning();
	}
	private void pause() {
		clip.stop();
	}
	public void play() {
		playSound();
		if (!isMute() && isMusic())
			clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	private void playSound() {
		try {
			clip.open(din);
			if (!isMute())
				clip.start();
		} catch (Exception e) {
		}
	}
	private void resume() {
		play();
	}
	public void stop() {
		clip.stop();
		clip.close();
	}
}
