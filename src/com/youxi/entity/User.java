package com.youxi.entity;

/**
 * 实体类
 * 
 * @author 贺旺
 *
 */
public class User {
	private String name;
	private String pwd;
	private int slength;
	private int score;
	public User(String name, String pwd, int slength, int score) {
		super();
		this.name = name;
		this.pwd = pwd;
		this.slength = slength;
		this.score = score;
	}
	public User() {
		super();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getSlength() {
		return slength;
	}
	public void setSlength(int slength) {
		this.slength = slength;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
