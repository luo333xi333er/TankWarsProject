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
		//获取音频输入流
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(new File(MUSIC_FILE));
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		//获取音频编码对象
		AudioFormat audioFormat = audioInputStream.getFormat();
		//设置数据输入
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
		 * 从输入流中读取数据发送到混音器
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
		//清空数据缓冲，并关闭输入
		sourceDataLine.drain();
		sourceDataLine.close();
	}
}
