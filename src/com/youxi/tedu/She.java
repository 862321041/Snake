package com.youxi.tedu;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * 蛇
 * 
 * @author 贺旺
 */
public class She implements Runnable {
int score=1;
int s=1;
int see=1;
	public She(Maps maps) {
		this.maps = maps;
		JButton jl = new JButton();
		jl.setBounds(0, 200, 20, 20);//
		this.body.add(jl);
		this.add("images/douzi1.png");//头
		this.add("images/she.png");
		this.add("images/she.png");
		this.add("images/douzi1.png");//尾
	}

	// 身体
	public List<JButton> body = new ArrayList<JButton>();

	// 方向
	private String fangXiang = "右";


	/**
	 * 当前位置
	 */
	private Point touWZ;

	/**
	 * 前一个位置
	 */
	private Point qianWZ;

	public boolean bool = true;

	/**
	 * 前进
	 */
	public void run() {
		while (bool) {
			if (maps.foods == null) {
				return;
			}

			qianWZ = new Point();
			JButton tou = body.get(0);
			// 得到当前位置,当前坐标
			touWZ = tou.getLocation();
			// 存储前一位置
			qianWZ.x = touWZ.x;
			qianWZ.y = touWZ.y;
			// 改变头的位置
			touGB(tou);
			// 身体其他部位根上来
			for (int i = 1; i < body.size(); i++) {
				// 当前位置
				touWZ = body.get(i).getLocation();
				// 放到前一位置
				body.get(i).setLocation(qianWZ);
				// 还原前一位置
				qianWZ.x = touWZ.x;
				qianWZ.y = touWZ.y;
			}
			// 看看有没东西吃
			chi(lookFood());
			// add();
			
			iszq();
			try {
				Thread.sleep(100);//睡眠，为蛇当前线程间隔时间，为蛇添加速度
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private Maps maps;

	/**
	 * 看看有没食物和修改尾部两张图片
	 * 
	 * @param foods
	 * @return
	 */
	public List<JButton> lookFood() {
		JButton my=body.get(body.size()-1);
		//修改尾部图片
		ImageIcon ii1 = new ImageIcon("images/douzi1.png");
		my.setIcon(ii1);
		my.setContentAreaFilled(false);//
		my.setBorderPainted(false);
		//将倒数第二张图片修改，设置按钮的边框和内容的透明
		JButton myj2 = body.get(body.size() - 2);
		ImageIcon ii2 = new ImageIcon("images/she.png");
		myj2.setIcon(ii2);
		myj2.setContentAreaFilled(false);//
		myj2.setBorderPainted(false);
		return maps.getFoods();
	}

	/**
	 * 改变头的位置
	 */
	public void touGB(JButton tou) {
		if (fangXiang.equals("上")) {
			touWZ.y = touWZ.y + 10;
		} else if (fangXiang.equals("下")) {
			touWZ.y = touWZ.y - 10;
		} else if (fangXiang.equals("左")) {
			touWZ.x = touWZ.x - 10;
		} else if (fangXiang.equals("右")) {
			touWZ.x = touWZ.x + 10;
		}
		// 改变头的位置
		tou.setLocation(touWZ);
	}

	/**
	 * 改变头的方向
	 */
	public void gbFangXiang(KeyEvent e) {
		if (e.getKeyCode() == 38 && (!fangXiang.equals("上"))) {
			fangXiang = "下";
		}
		if (e.getKeyCode() == 40 && (!fangXiang.equals("下"))) {
			fangXiang = "上";
		}
		if (e.getKeyCode() == 37 && (!fangXiang.equals("右"))) {
			fangXiang = "左";
		}
		if (e.getKeyCode() == 39 && (!fangXiang.equals("左"))) {
			fangXiang = "右";
		}

	}

	/**
	 * 生长
	 */
	public void add(String img) {
		JButton myjl = body.get(body.size() - 1);
		JButton jl = new JButton();

	//	if(body.size()){}
		// 为我们的蛇添加图片（图片按钮添加背景图片）,去除边框和透明（**）
		ImageIcon ii = new ImageIcon(img);
		jl.setIcon(ii);
		jl.setContentAreaFilled(false);//
		jl.setBorderPainted(false);

		jl.setBounds(myjl.getX(), myjl.getY(), myjl.getWidth(), myjl.getHeight());
		
		body.add(jl);
	
		// 将新增的加入到map
		maps.add(jl);
	}

	/**
	 * 吃东西
	 */
	public void chi(List<JButton> foodList) {
		if (foodList == null) {
			return;
		}
		
		
		for (int i = 0; i < foodList.size(); i++) {
			JButton tou = body.get(0);
			JButton food = foodList.get(i);
			int abs = Math.abs(food.getX()+10 - tou.getX());
			int abs2 = Math.abs(food.getY()+10 - tou.getY());
			if ((abs < 15 && abs2 < 15)) {// 关键点（消失的位置）
				// new PlayMusic().sto
				add("images/she.png");
				/**
				 * 判断吃的豆子
				 */
				//蓝
				if(food.getSize().getWidth()==20){
					System.out.println("蓝"+score);
					score+=2;
				}
				//绿色的豆子
				if(food.getSize().getWidth()==30){
					System.out.println("绿"+s);
					score+=2;
					s++;
				}
				//红色豆子
				if(food.getSize().getWidth()==40){
					System.out.println("红"+see);
					score+=2;
					see++;
				}
				
				foodList.remove(food);
				
				food.setVisible(false);

			}
		}
	}


	/**
	 * 是否撞墙,是否撞上障碍物
	 */
	public void iszq() {
		JButton tou = body.get(0);
		
		if (tou.getX() < 0 || tou.getX() > maps.getWidth() || tou.getY() < 0 || tou.getY() > maps.getHeight()) {

			maps.gameover();
			return;
		}else if(dash()){
			//是否撞上障碍物
			
			return;
		} else {
			for (int i = 1; i < body.size(); i++) {
				int abs1 = Math.abs((tou.getX() - body.get(i).getX()));
				int abs2 = Math.abs((tou.getY() - body.get(i).getY()));
				if (abs1 < 5 && abs2 < 5) {
					maps.gameover();
					return;
				}
			}
		}

	}
	//撞上障碍物
	private boolean dash() {
		JButton tou = body.get(0);
		Iterator<JButton>  it= maps.obstacle.iterator();	
		while (it.hasNext()) {
			JButton z=it.next();
			int x=z.getX();
			int y=z.getY();
			 int a1=Math.abs(tou.getX()-(x+10));
			 int a2=Math.abs(tou.getY()-(y+10));
			 //有一点问题，改变图片大小就可以
			 if (a1 < 20 && a2 < 20) {
					maps.gameover();
					return true;
				}
		}
		return false;
	}

}
