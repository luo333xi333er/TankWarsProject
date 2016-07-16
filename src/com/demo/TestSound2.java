package com.demo;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

public class TestSound2 {
	public static final String MUSIC_FILE = "e:\\333.wma";
	public static void main(String[] args) {
		//��ȡ��Ƶ������
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(MUSIC_FILE));
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//��ȡ��Ƶ�������
		AudioFormat audioFormat = audioInputStream.getFormat();
		//������������
		DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, audioFormat, AudioSystem.NOT_SPECIFIED);
		SourceDataLine sourceDataLine = null;;
		try {
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
			sourceDataLine.open(audioFormat);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		sourceDataLine.start();
		/**
		 * ���������ж�ȡ���ݷ��͵�������
		 */
		int count = 0;
		byte tempBuffer[] = new byte[1024];
		try {
			while((count = audioInputStream.read(tempBuffer, 0, tempBuffer.length)) != -1) {
				if(count > 0) {
					sourceDataLine.write(tempBuffer, 0, count);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		//������ݻ��壬���ر�����
		sourceDataLine.drain();
		sourceDataLine.close();
	}
}
