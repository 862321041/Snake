package com.youxi.tedu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.youxi.entity.User;

/**
 * 地图
 * 
 * @author 贺旺
 * 
 */
public class Maps extends JFrame implements Runnable {
	static PlayMusic music = null;
	// int n=0;

	public Maps() {
		this.setLayout(null);// 清空布局管理器
		this.addKeyListener(new KeyEve());// 键盘控制事件
		this.setBounds(0, 0, 700, 450);// 组件的位置，定义控件的大小

		// 背景图片的路径。（相对路径或者绝对路径。本例图片放于"java项目名"的文件下（**）
		String path = "images/beijing.png";
		// 背景图片
		ImageIcon background = new ImageIcon(path);
		// 把背景图片显示在一个标签里面
		JLabel label = new JLabel(background);
		// 把标签的大小位置设置为图片刚好填充整个面板
		label.setBounds(0, 0, this.getWidth(), this.getHeight());
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明
		JPanel imagePanel = (JPanel) this.getContentPane();
		imagePanel.setOpaque(false);
		// 把背景图片添加到分层窗格的最底层作为背景
		this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
		this.setUndecorated(false);// 让Frame窗口出现边框和标题栏
		this.setVisible(true);// 设置可见性

	}

	/**
	 * 食物集合
	 */
	public List<JButton> foods = new ArrayList();
	// 障碍物集合
	List<JButton> obstacle = new ArrayList<>();
	// 用户信息
	User user = new User();

	/**
	 * 生成食物坐标,通过java自带Point进行生成平面坐标
	 */
	public Point newPoint() {
		Point point = new Point();
		Random r = new Random();
		point.x = (r.nextInt(400 * 5) / 5);
		point.y = (r.nextInt(400 * 5) / 5);
		return point;
	}

	/**
	 * 创建食物, 拿按钮作为食物,三种食物
	 */
	public void addFood() {
		int jiangli;
		for (int i = 0; i < 3; i++) {

			jiangli = (int) (Math.random() * 100);

			if (jiangli >= 0 && jiangli <= 50) {
				jiangli = 0;
			} else if (jiangli > 50 && jiangli <= 80) {
				jiangli = 1;
			} else if (jiangli > 80 && jiangli < 100) {
				jiangli = 2;
			}
			switch (jiangli) {
			case 0:
				// 创建集合
				// foods= new ArrayList();
				// 把豆子加入集合
				foods.add(adfood("images/she.png", 20));
				// 把豆子集合加入map集合,将前面的覆盖了
				/*
				 * foodss1.put(n+",douzi_one", "one"); n++;
				 */
				//
				// foodss.get("")
				break;
			case 1:
				// 创建集合
				// foods= new ArrayList();
				// 把豆子加入集合
				foods.add(adfood("images/douzi1.png", 30));
				// 把豆子集合加入map集合
				/*
				 * foodss2.put(n+",douzi_two", "two"); n++;
				 */
				break;
			default:
				// 创建集合
				// foods= new ArrayList();
				// 把豆子加入集合，并设置图片和大小
				foods.add(adfood("images/douzi2.png", 40));
				// 把豆子集合加入map集合
				/*
				 * foodss3.put(n+",douzi_three", "three"); n++;
				 */
				break;
			}

		}
	}

	// 食物
	private JButton adfood(String img, int length) {

		JButton jl = new JButton();
		jl.setSize(length, length);

		jl.setLocation(newPoint());// 设置按钮在平面的位置
		// 为我们的食物添加图片（图片按钮添加背景图片）,去除边框和透明（**）
		ImageIcon ii = new ImageIcon(img);
		jl.setIcon(ii);
		jl.setContentAreaFilled(false);
		jl.setBorderPainted(false);

		// foods.add(jl);// 添加到食物集合
		return jl;
	}

	/**
	 * 显示食物
	 * 
	 * @param args
	 */
	public void showFood() {
		for (int i = 0; i < foods.size(); i++) {
			this.getContentPane().add(foods.get(i));// 初始化一个容器，用来在容器上添加一些控件
		}
	}

	/**
	 * gameover（游戏结束的方法）
	 * 
	 * @param args
	 */
	public void gameover() {
		this.foods = null;
		this.setVisible(false);
		int r = JOptionPane.showConfirmDialog(this, "是否继续?");
		if (r == 0) {
			Maps m = new Maps();
			m.init();
		} else {
			System.exit(0);
		}
	}

	public She she;

	public List<JButton> getFoods() {
		return foods;
	}

	public void setFoods(List<JButton> foods) {
		this.foods = foods;

		// this.obstacle=obstacle;
	}

	// 键盘事件
	class KeyEve implements KeyListener {

		public void keyPressed(KeyEvent e) {
			she.gbFangXiang(e);

		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}

	}

	public void init() {
		this.setFocusable(true);

		// 初始化蛇,启动蛇和豆子的线程

		She s = new She(this);
		this.she = s;
		Thread tm = new Thread(this);
		tm.start();
		Thread t = new Thread(s);
		t.start();
	}

	public static void main(String[] args) {
		// 启动游戏
		Maps m = new Maps();
		m.init();
		if (music == null) {
			// System.out.println("s");
			music = new PlayMusic();// 开启背景音乐
		}
	}

	public void run() {
		while (true) {
			if (this.foods == null) {
				return;
			}
			try {

				addFood();
				showFood();
				// 添加障碍物
				addoBstacle();
				//获取分数,显示在界面上
				gainscore();
				// 线程画对象
				this.update(this.getGraphics());
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	//获取分数,显示在界面上
	public void gainscore() {
	
		JButton jl = new JButton();
		jl.setSize(40, 40);
		Point point = new Point();
		Random r = new Random();
		jl.setForeground(Color.RED);
		point.x = 40;
		point.y = 40;
		jl.setLocation(point);// 设置按钮在平面的位置，和生成位置
		jl.setContentAreaFilled(false);
		String score=she.score+"";
		System.out.println(score);
	    jl.setText(score);
	    //设置边框
		//jl.setBorderPainted(false);
		this.getContentPane().add(jl);
	}

	/**
	 * 添加障碍物
	 */
	public void addoBstacle() {
		JButton jl = new JButton();
		jl.setSize(60, 60);

		jl.setLocation(newPoint());// 设置按钮在平面的位置，和生成位置
		// 为我们的食物添加图片（图片按钮添加背景图片）,去除边框和透明（**）
		ImageIcon ii = new ImageIcon("images/zaw.png");
		jl.setIcon(ii);
		jl.setContentAreaFilled(false);
		jl.setBorderPainted(false);
		obstacle.add(jl);
		for (int i = 0; i < obstacle.size(); i++) {
			this.getContentPane().add(obstacle.get(i));// 初始化一个容器，用来在容器上添加一些控件
		}
		// foods.add(jl);// 添加到食物集合

	}

}
