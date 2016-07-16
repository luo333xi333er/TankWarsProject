package com.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

//播放声音的类
class AePlayWave extends Thread {

	private String filename;
	public AePlayWave(String filename) {
		this.filename = filename;
	}

	public void run() {
		File soundFile = new File(filename);
		AudioInputStream audioInputStream = null;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}

		AudioFormat format = audioInputStream.getFormat();
		SourceDataLine auline = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		try {
			auline = (SourceDataLine) AudioSystem.getLine(info);
			auline.open(format);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		auline.start();
		int nBytesRead = 0;
		//这是缓冲
		byte[] abData = new byte[1024];
		try {
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(abData, 0, abData.length);
				if (nBytesRead >= 0)
					auline.write(abData, 0, nBytesRead);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			auline.drain();
			auline.close();
		}
	}
}


//继续上一局--恢复坦克坐标，将坦克看做是一个点
class Node {
	int x;
	int y;
	int direct;
	public Node(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
}

//记录类，同时也可以保存玩家的
class Recorder {
	//记录每关有多少敌人
	private static int enNum = 20;
	//设置我有多少可以用的人
	private static int myLife = 3;
	//记录总共消灭了多少敌人
	private static int AllEnNum = 0;
	//从文件中恢复记录点
	private static Vector<Node> nodes = new Vector<Node>();
	//使用字符流
	private static FileWriter fw = null;
	private static FileReader fr = null;
	//缓冲流
	private static BufferedWriter bw = null;
	private static BufferedReader br = null;
	private static Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	
	//继续上一局--完成读取任务
	public static Vector<Node> getNodeAndEnemyTank() {
		try {
			fr = new FileReader("e:\\myRecording.txt");
			br = new BufferedReader(fr);
			String n = "";
			//先读第一行
			n=br.readLine();
			//恢复敌人坦克数量
			AllEnNum = Integer.parseInt(n);
			//接着从第二行开始读坦克的坐标与方向
			while((n=br.readLine()) != null) {
				//以空格将每行数据进行分割，得到一个字符串数组
				String[] xyz = n.split(" ");
				int x = Integer.parseInt(xyz[0]);
				int y = Integer.parseInt(xyz[1]);
				int direct = Integer.parseInt(xyz[2]);
				Node node = new Node(x, y, direct);
				//将node放入到nodes集合中
				nodes.add(node);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return nodes;
	}
	
	//保存击毁敌人坦克的数量和坐标以及方向
	public static void keepRecAndEnemyTank() {
		//创建文件流
		try {
			fw = new FileWriter("e:\\myRecording.txt");
			bw = new BufferedWriter(fw);
			//记录击败敌人的坦克数量
			bw.write(AllEnNum + "\r\n");
			//保存当前活的敌人坦克的坐标和方向
			for(int i = 0; i < ets.size(); i++) {
				//取出每一辆坦克
				EnemyTanke et = ets.get(i);
				if(et.isLive) {
					//保存
					String str = et.x + " " + et.y + " " + et.direct;
					//写入到文件
					bw.write(str + "\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//后开的先关
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//记录玩家击毁敌人坦克数量并保存到文件中
	public static void keepRecording() {
		//创建文件流
		try {
			fw = new FileWriter("e:\\myRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(AllEnNum + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//后开的先关
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//从文件中读取记录
	public static void getRecoding() {
		try {
			fr = new FileReader("e:\\myRecording.txt");
			br = new BufferedReader(fr);
			String n = br.readLine();
			AllEnNum = Integer.parseInt(n);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	
	//减少敌人数量
	public static void reduceEnNum() {
		enNum--;
	}
	//减少自己数量
	public static void reduceMyLife() {
		myLife--;
	}
	//消灭敌人
	public static void addEnNumRec() {
		AllEnNum++;
	}
	public static int getEnNum() {
		return enNum;
	}
	public static void setEnNum(int enNum) {
		Recorder.enNum = enNum;
	}
	public static int getMyLife() {
		return myLife;
	}
	public static void setMyLife(int myLife) {
		Recorder.myLife = myLife;
	}
	public static int getAllEnNum() {
		return AllEnNum;
	}
	public static void setAllEnNum(int allEnNum) {
		AllEnNum = allEnNum;
	}
	public static Vector<EnemyTanke> getEts() {
		return ets;
	}
	public static void setEts(Vector<EnemyTanke> ets) {
		Recorder.ets = ets;
	}
	public static Vector<Node> getNodes() {
		return nodes;
	}
	public static void setNodes(Vector<Node> nodes) {
		Recorder.nodes = nodes;
	}
}

//定义一个炸弹类
class Bomb {
	//定义炸弹的坐标
	int x;
	int y;
	//定义炸弹生命--每爆炸一次炸弹生命减少一次
	int life = 9;
	//定义炸弹是否活着
	boolean isLive = true;
	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}
	//减少生命值
	public void lifeDown() {
		if(life > 0) {
			life--;
		} else {
			this.isLive = false;
		}
	}
}

//定义一个子弹类
class Bullet implements Runnable{
	//子弹坐标
	int x;
	int y;
	//子弹方向
	int direct;
	//子弹速度
	int speed = 2;
	//子弹是否活着
	boolean isLive = true;
	public Bullet(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	@Override
	public void run() {
		while(true) {
			//让子弹每发生变动休息50毫秒
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			switch(direct) {
			case 0:
				//子弹向上
				y -= speed;
				break;
			case 1:
				//子弹向右
				x += speed;
				break;
			case 2:
				//子弹向下
				y += speed;
				break;
			case 3:
				//子弹向左
				x -= speed;
				break;
			}
			/**
			 * 子弹什么时候死亡？？？
			 * 1.当子弹碰到敌人坦克时死亡
			 * 2.当子弹碰到边框时坦克死亡
			 */
			if(x < 0 || x > 800 || y < 0 || y > 600) {
				this.isLive = false;
				break;
			}
		}
	}
}

//定义一个坦克类
class Tanke {
	//表示坦克的横坐标
	int x = 0;
	//表示坦克的纵坐标
	int y = 0;
	//坦克方向 0--上 ； 1--右； 2--下； 3--左
	int direct = 0;
	//坦克的速度
	int speed = 3;
	//坦克颜色--区分自己和敌人
	int color;
	//坦克是否活着
	boolean isLive = true;
	public Tanke(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getColor() {
		return color;
	}
	public void setColor(int color) {
		this.color = color;
	}
}

//定义一个我的坦克类
class Hero extends Tanke {
	//创建多颗子弹
	Vector<Bullet> heroBullet = new Vector<Bullet>();
	Bullet bullet = null;
	public Hero(int x, int y) {
		super(x, y);
	}
	
	//开火
	public void bulletEnemy() {
		switch (this.direct) {
		//向上
		case 0:
			//创建一颗子弹
			bullet = new Bullet(x + 10, y, 0);
			//把子弹加入到集合中
			heroBullet.add(bullet);
			break;
		//向右
		case 1:
			bullet = new Bullet(x + 30, y + 10, 1);
			heroBullet.add(bullet);
			break;
		//向下
		case 2:
			bullet = new Bullet(x + 10, y + 30, 2);
			heroBullet.add(bullet);
			break;
		//向左
		case 3:
			bullet = new Bullet(x, y + 10, 3);
			heroBullet.add(bullet);
			break;
		}
		//启动子弹线程
		Thread thread = new Thread(bullet);
		thread.start();
	}
	
	//判断是否碰撞到了敌人坦克--------得不到面板上的敌人的坦克数量
	public boolean isTouchEnemy() {
		boolean b = false;
		//定义一个向量，可以访问到MyPanel上所有同类其它坦克
		Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
		switch(this.direct) {
		//自己坦克向上
		case 0:
			//取出所有敌人坦克
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//判断敌人坦克的方向是向上或者向下
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
				}
				//判断敌人坦克的方向是向右或者向左
				if(et.direct == 3 || et.direct == 1) {
					if(this.x >= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
						return true;
				}
			}
		}
		break;
		//自己坦克向右
		case 1:
			for(int i = 0; i < ets.size(); i++) {
				//取出敌人坦克
				EnemyTanke et = ets.get(i);
				//判断敌人坦克的方向是向上或者向下
				if(et.direct == 0 || et.direct == 2) {
					if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
						return true;
					}
				}
				//判断敌人坦克的方向是向右或者向左
				if(et.direct == 3 || et.direct == 1) {
					if(this.x + 30 	>= et.x && this.x + 30 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
						return true;
					}
					if(this.x + 30 >= et.x && this.x + 30 <= et.x + 30 && this.y + 20 >= et.y && this.y + 20 <= et.y + 20) {
						return true;
					}
				}
			}
			break;
		//自己坦克向下
		case 2:
			//取出所有敌人坦克
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//判断敌人坦克的方向是向上或者向下
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
						return true;
					}
				}
				//判断敌人坦克的方向是向右或者向左
				if(et.direct == 3 || et.direct == 1) {
					if(this.x 	>= et.x && this.x <= et.x + 30 && this.y + 30 >= et.y && this.y + 30 <= et.y + 20) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y + 30 >= et.y && this.y + 30 <= et.y + 20) {
						return true;
					}
				}
			}
			break;
		//自己坦克向左
		case 3:
			//取出敌人坦克
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//判断敌人坦克的方向是向上或者向下
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x >= et.x && this.x <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
						return true;
					}
				}
				//判断敌人坦克的方向是向右或者向左
				if(et.direct == 3 || et.direct == 1) {
					if(this.x 	>= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
						return true;
					}
					if(this.x >= et.x && this.x <= et.x + 30 && this.y + 20 >= et.y && this.y + 20 <= et.y + 20) {
						return true;
					}
				}
			}
			break;
		}
		return b;
	}
	
	//坦克向上移动
	public void moveUp() {
		//判断我方坦克是否越界
		if(y > 0 && !this.isTouchEnemy()) {
			y -= speed;
		}
	}
	//坦克向右移动
	public void moveRight() {
		if(x < 800 - 48 && !this.isTouchEnemy()) {
			x += speed;
		}
	}
	//坦克向下移动
	public void moveDown() {
		if(y < 600 - 70 && !this.isTouchEnemy()) {
			y += speed;
		}
	}
	//坦克向左移动
	public void moveLeft() {
		if(x > 0 && !this.isTouchEnemy()) {
			x -= speed;
		}
	}
}

//定义一个敌人坦克类,把敌人坦克做成一个线程类
class EnemyTanke extends Tanke implements Runnable{
	//计时器
	int times = 0;
	//定义一个向量用来存放敌人子弹
	Vector<Bullet> bullets = new Vector<Bullet>();
	//敌人添加子弹--在敌人坦克创建或者敌人子弹消亡的时候添加
	Bullet bullet = null;
	//定义一个向量，可以访问到MyPanel上所有同类其它坦克
	Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	
	public EnemyTanke(int x, int y) {
		super(x, y);
	}

	//得到MyPanel上敌人坦克向量
	public void setEts(Vector<EnemyTanke> vv) {
		this.ets = vv;
	}
	//判断是否碰撞到了同类其他坦克
	public boolean isTouchOther() {
		boolean b = false;
		switch(this.direct) {
		//某个敌人坦克向上
		case 0:
			//取出所有的同类其它坦克
			for(int i = 0; i < ets.size(); i++) {
				//取出第一个坦克
				EnemyTanke et = ets.get(i);
				//判断第一个坦克是否是自己
				if(et != this) {
					//判断同类其它坦克的方向是向上或者向下
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
					}
					//判断同类其它坦克的方向是向右或者向左
					if(et.direct == 3 || et.direct == 1) {
						if(this.x >= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		//某个敌人坦克向右
		case 1:
			//取出所有的同类其它坦克
			for(int i = 0; i < ets.size(); i++) {
				//取出第一个坦克
				EnemyTanke et = ets.get(i);
				//判断第一个坦克是否是自己
				if(et != this) {
					//判断同类其它坦克的方向是向上或者向下
					if(et.direct == 0 || et.direct == 2) {
						if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					//判断同类其它坦克的方向是向右或者向左
					if(et.direct == 3 || et.direct == 1) {
						if(this.x + 30 	>= et.x && this.x + 30 <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if(this.x + 30 >= et.x && this.x + 30 <= et.x + 30 && this.y + 20 >= et.y && this.y + 20 <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		//某个敌人坦克向下
		case 2:
			//取出所有的同类其它坦克
			for(int i = 0; i < ets.size(); i++) {
				//取出第一个坦克
				EnemyTanke et = ets.get(i);
				//判断第一个坦克是否是自己
				if(et != this) {
					//判断同类其它坦克的方向是向上或者向下
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
							return true;
						}
					}
					//判断同类其它坦克的方向是向右或者向左
					if(et.direct == 3 || et.direct == 1) {
						if(this.x 	>= et.x && this.x <= et.x + 30 && this.y + 30 >= et.y && this.y + 30 <= et.y + 20) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 30 && this.y + 30 >= et.y && this.y + 30 <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		//某个敌人坦克向左
		case 3:
			//取出所有的同类其它坦克
			for(int i = 0; i < ets.size(); i++) {
				//取出第一个坦克
				EnemyTanke et = ets.get(i);
				//判断第一个坦克是否是自己
				if(et != this) {
					//判断同类其它坦克的方向是向上或者向下
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x >= et.x && this.x <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					//判断同类其它坦克的方向是向右或者向左
					if(et.direct == 3 || et.direct == 1) {
						if(this.x 	>= et.x && this.x <= et.x + 30 && this.y >= et.y && this.y <= et.y + 20) {
							return true;
						}
						if(this.x >= et.x && this.x <= et.x + 30 && this.y + 20 >= et.y && this.y + 20 <= et.y + 20) {
							return true;
						}
					}
				}
			}
			break;
		}
		return b;
	}

	@Override
	public void run() {
		while(true) {
			//敌人坦克继承父类的方向属性
			switch (this.direct) {
			//坦克正在向上移动
			case 0:
				for(int i = 0; i < 30; i++) {
					//判断坦克是否出边界
					if(y > 0 && !this.isTouchOther()) {
						y-=speed;
					}
					//让坦克休眠50毫秒
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//坦克正在向右移动
			case 1:
				for(int i = 0; i < 30; i++) {
					//判断坦克是否出边界
					if(x < 800 - 48 && !this.isTouchOther()) {
						x+=speed;
					}
					//让坦克休眠50毫秒
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//坦克正在向下移动
			case 2:
				for(int i = 0; i < 30; i++) {
					//判断坦克是否出边界
					if(y < 600 - 70 && !this.isTouchOther()) {
						y+=speed;
					}
					//让坦克休眠50毫秒
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//坦克正在向左移动
			case 3:
				for(int i = 0; i < 30; i++) {
					//判断坦克是否出边界
					if(x > 0 && !this.isTouchOther()) {
						x-=speed;
					}
					//让坦克休眠50毫秒
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			//30*50=1500=1.5秒过后times自加
			this.times++;
			//时间每过三秒，敌人坦克产生一颗子弹
			if(times % 2 == 0) {
				if(this.isLive) {
					//判断敌人坦克是否够5颗子弹，不够就创建
					if(bullets.size() < 5) {
						Bullet bullet = null;
						switch (this.direct) {
						//向上
						case 0:
							//创建一颗子弹
							bullet = new Bullet(x + 10, y, 0);
							//把子弹加入到集合中
							bullets.add(bullet);
							break;
						//向右
						case 1:
							bullet = new Bullet(x + 30, y + 10, 1);
							bullets.add(bullet);
							break;
						//向下
						case 2:
							bullet = new Bullet(x + 10, y + 30, 2);
							bullets.add(bullet);
							break;
						//向左
						case 3:
							bullet = new Bullet(x, y + 10, 3);
							bullets.add(bullet);
							break;
						}
						//启动子弹线程
						Thread thread = new Thread(bullet);
						thread.start();
					}
				}
			}
			
			//让坦克随机产生一个新的方向
			this.direct = (int)(Math.random() * 4);
			//判断敌人坦克是否死亡
			if(this.isLive == false) {
				//让死亡后的坦克退出线程
				break;
			}
		}
	}
}
