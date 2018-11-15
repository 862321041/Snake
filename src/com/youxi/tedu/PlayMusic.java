package com.youxi.tedu;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;
import java.applet.Applet;
import java.net.URI;
import javax.swing.JFrame;
/**
 * 音乐播放器
 * @author 贺旺
 *
 */
public class PlayMusic extends JFrame {
	File f;
	URI uri;
	URL url;

	PlayMusic() {
		try {
			f = new File("music/beij.wav");
			uri = f.toURI();
			url = uri.toURL(); // 解析地址
			AudioClip aau;
			aau = Applet.newAudioClip(url);
			aau.loop(); // 循环播放
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
