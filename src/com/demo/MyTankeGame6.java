
package com.demo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * 功能：坦克5.0版本
 * 1.画出坦克
 * 2.给坦克添加事件，让坦克上下左右移动
 * 3.让我的坦克发出子弹
 * 4.让子弹实现连发,并且连发五颗
 * 5.当我方坦克击中敌人坦克时敌人坦克自动消失
 *    1>.计算出敌人坦克坐标和我的子弹的坐标
 *    2>.写一个专门判断子弹是否击中敌人坦克的函数
 *    3>.在什么地方判断子弹是否击中敌人的坦克---在run方法中判断
 *    4>.做出爆炸的效果---本质上是图片的替换
 *      a.准备三张图片,三张图片瞬间进行替换
 *      b.定义一个炸弹类
 *      c.在击中敌人坦克时，将炸弹放入集合中
 *      d.在paint函数中画出坦克
 * 6.让敌人的坦克也能够自由的上下左右移动
 * 7.控制我方的坦克和敌人的坦克均在规定范围内移动
 * 8.让敌人坦克也能发射子弹
 * 9.当敌人坦克发出的子弹打中我的坦克时，我的坦克也爆炸-----类似5
 * 10.控制敌人的坦克之间不重叠运动
 * 		a.决定把判断是否重叠的函数写在enemyTanke类中
 * 11.可以分关
 * 		a.做一个开始的Panel，它是一个空的
 * 		b.字体做成闪烁效果---线程实现
 * 12.可以在玩游戏的时候暂停和继续
 * 		a.考虑通过按空格键实现
 * 		b.当用户点击暂停的时候把子弹和坦克的速度设置为0，并让坦克的方向不要发生变化
 * 13.记录玩家成绩--利用io流实现
 *   a.考虑利用文件流的方式实现
 *   b.单写一个记录类完成对玩家的记录
 *   c.先完成保存共击毁了多少量敌人坦克的功能
 *   d.存盘退出游戏，下次打开游戏时，可以恢复到上次退出的状态接着玩游戏
 * 		1.考虑将消灭的坦克数量保存到磁盘
 * 		2.将退出游戏时的所有坦克的坐标保存到磁盘下次继续游戏
 * 14.利用java操作声音文件
 * 		a.找一个声音文件
 * 		b.
 */
public class MyTankeGame6 extends JFrame implements ActionListener{
	MyPanel mp = null;
	//定义一个开始面板
	StartPanel startPanel = null;
	//做菜单
	JMenuBar jmb = null;
	//菜单选项--游戏
	JMenu jm1 = null;
	//开始游戏
	JMenuItem jmi1 = null;
	//保存退出
	JMenuItem jmi2 = null;
	//存盘退出
	JMenuItem jmi3 = null;
	//继续上局游戏
	JMenuItem jmi4 = null;
	
	public static void main(String[] args) {
		MyTankeGame6 game = new MyTankeGame6();
	}
	
	//构造函数
	public MyTankeGame6() {
		startPanel = new StartPanel();
		this.add(startPanel);
		//启动startPanel线程
		Thread thread = new Thread(startPanel);
		thread.start();
		//创建菜单及菜单选项
		jmb = new JMenuBar();
		jm1 = new JMenu("游戏(G)");
		//设置快捷方式 alt+G
		jm1.setMnemonic('G');
		jmi1 = new JMenuItem("开始游戏(N)");
		jmi1.setMnemonic('N');
		jmi2 = new JMenuItem("保存退出(E)");
		jmi2.setMnemonic('E');
		jmi3 = new JMenuItem("存盘退出(S)");
		jmi3.setMnemonic('S');
		jmi4 = new JMenuItem("继续上局(C)");
		jmi4.setMnemonic('C');
		//给jmi1添加监听
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		//给jmi2添加监听
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exitGame");
		//给jmi3添加监听
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExitGame");
		//给jmi4添加监听
		jmi4.addActionListener(this);
		jmi4.setActionCommand("beforeGame");
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		this.setSize(900, 700);
		//窗体居中
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//对用户不同的点击做出不同的处理
		if(e.getActionCommand().equals("newGame")) {
			//创建战场面板
			this.mp = new MyPanel("newGame");
			//启动mp线程
			Thread thread = new Thread(mp);
			thread.start();
			//先移除掉之前的旧面板
			this.remove(startPanel);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示新面板
			this.setVisible(true);
		} else if(e.getActionCommand().equals("exitGame")) {
			//用户点击了退出游戏的菜单
			//保存击毁敌人坦克数量
			Recorder.keepRecording();
			System.exit(0);
		} else if(e.getActionCommand().equals("saveExitGame")) {
			Recorder.setEts(mp.ets);
			//存盘退出--保存击毁敌人的数量和敌人的坐标
			Recorder.keepRecAndEnemyTank();
			//退出
			System.exit(0);
		} else if(e.getActionCommand().equals("beforeGame")) {
			//继续上一局游戏
			//创建战场面板
			mp = new MyPanel("continue");
			//启动mp线程
			Thread thread = new Thread(mp);
			thread.start();
			//先移除掉之前的旧面板
			this.remove(startPanel);
			this.add(mp);
			//注册监听
			this.addKeyListener(mp);
			//显示新面板
			this.setVisible(true);
		}
	}
}

//做一个开始面板---单纯起着提示的作用，为实现分关做铺垫
class StartPanel extends JPanel implements Runnable{
	//开关器
	int times = 0;
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 900, 800);
		if(times % 2 == 0) {
			//设置颜色
			g.setColor(Color.RED);
			//提示信息--开关信息的字体
			g.setFont(new Font("宋体", Font.BOLD, 100));
			g.drawString("stage: 1", 230, 310);
		}
	}
	//让提示信息显示闪烁效果
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			times++;
			//重画
			this.repaint();
		}
	}
}

//我的面板
class MyPanel extends JPanel implements KeyListener, Runnable{
	//定义一个我的坦克
	Hero hero = null;
	Vector<Node> nodes = null;
	//定义一个敌人坦克组---vector
	Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	//定义敌人的坦克为6辆
	int enemySize = 6;
	//定义炸弹集合
	Vector<Bomb> bombs = new Vector<Bomb>();
	//定义三张图片（实现爆炸）
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	//构造函数
	public MyPanel(String flag) {
		//调用函数---恢复退出游戏后的类
		Recorder.getRecoding();
		hero = new Hero(350, 250);
		//判断
		if(flag.equals("newGame")) {
			//初始化敌人的坦克
			for(int i = 0; i < enemySize; i++) {
				//创建一辆敌人的坦克对象
				EnemyTanke et = new EnemyTanke((i + 1) * 120, 0);
				//设置敌人坦克的方向
				et.setDirect(2);
				//将MyPanel的敌人坦克向量交给该敌人坦克
				et.setEts(ets);
				//启动敌人坦克
				Thread thread = new Thread(et);
				thread.start();
				//创建一颗敌人坦克发射的子弹
				Bullet bullet = new Bullet(et.x + 10, et.y + 30, et.direct);
				//给敌人坦克添加子弹---添加到集合中
				et.bullets.add(bullet);
				//启动子弹线程
				Thread thread2 = new Thread(bullet);
				thread2.start();
				//将初始化的敌人坦克添加在集合中
				ets.add(et);
			}
		} else {
			this.nodes = Recorder.getNodeAndEnemyTank();
			//恢复敌人的坦克
			for(int i = 0; i < nodes.size(); i++) {
				//恢复敌人的坦克对象
				EnemyTanke et = new EnemyTanke(nodes.get(i).x, nodes.get(i).y);
				//恢复敌人坦克的方向
				et.setDirect(nodes.get(i).direct);
				//将MyPanel的敌人坦克向量交给该敌人坦克
				et.setEts(ets);
				//启动敌人坦克
				Thread thread = new Thread(et);
				thread.start();
				//创建一颗敌人坦克发射的子弹
				Bullet bullet = new Bullet(et.x + 10, et.y + 30, et.direct);
				//给敌人坦克添加子弹---添加到集合中
				et.bullets.add(bullet);
				//启动子弹线程
				Thread thread2 = new Thread(bullet);
				thread2.start();
				//将初始化的敌人坦克添加在集合中
				ets.add(et);
			}
		}
		
		//使用IO流初始化三张图片---三张图片的切换组成一颗炸弹
		try {
			image1 = ImageIO.read(new File("bomb1.jpg"));
			image2 = ImageIO.read(new File("bomb2.jpg"));
			image3 = ImageIO.read(new File("bomb3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//添加音乐
		AePlayWave apw = new AePlayWave("e:\\333.wma");
		apw.start();
		
		//使用toolkit初始化三张图片---三张图片的切换组成一颗炸弹
//		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.jpg"));
//		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.jpg"));
//		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.jpg"));
	}
	
	//画出提示信息
	public void showInfo(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		//画出敌人坦克提示信息，该坦克不参与战斗
		this.drawTanke(270, 605, g, 0, 1);
		g.setColor(Color.BLACK);
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.drawString(Recorder.getEnNum()+"", 310, 628);
		//画出我的坦克提示信息，该坦克不参与战斗
		this.drawTanke(460, 605, g, 0, 0);
		g.setColor(Color.BLACK);
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.drawString(Recorder.getMyLife()+"", 500, 628);
		//画出玩家的总成绩
		g.setColor(Color.RED);
		g.setFont(new Font("宋体", Font.BOLD, 25));
		g.drawString("总成绩", 800, 60);
		//画出敌人坦克
		this.drawTanke(810, 100, g, 0, 1);
		//画出击败敌人数量
		g.drawString(Recorder.getAllEnNum()+"", 850, 125);
	}
	
	//重新paint
	public void paint(Graphics g) {
		super.paint(g);
		//调用提示信息
		this.showInfo(g);
		//画出自己的坦克
		if(hero.isLive) {
			//调用drawTanke方法
			this.drawTanke(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
		}
		//画出敌人的坦克
		for(int i = 0; i < ets.size(); i++) {
			//取出敌人坦克
			EnemyTanke et = ets.get(i);
			//判断坦克是否活着
			if(et.isLive) {
				//画出坦克
				this.drawTanke(et.getX(), et.getY(), g, et.getDirect(), 1);
				//从bullets集合中取出每一颗子弹
				for(int j = 0; j < et.bullets.size(); j++) {
					//取出一颗子弹
					Bullet enemyBullet = et.bullets.get(j);
					//画出一颗子弹--先判断
					if(enemyBullet != null && enemyBullet.isLive) {
						g.draw3DRect(enemyBullet.x, enemyBullet.y, 3, 3, false);
					} else {
						//如果敌人的坦克死亡就从子弹向量中删除子弹
						et.bullets.remove(enemyBullet);
					}
				}
			}
		}
		//从bullets集合中取出每一颗子弹并画出
		for(int i = 0; i < hero.heroBullet.size(); i++) {
			//取出一颗子弹
			Bullet myBullet = hero.heroBullet.get(i);
			//画出一颗子弹--先判断
			if(myBullet != null && myBullet.isLive) {
				g.setColor(Color.YELLOW);
				g.draw3DRect(myBullet.x, myBullet.y, 3, 3, false);
			} else{
				//从bullets集合中移除该子弹
				hero.heroBullet.remove(myBullet);
			}
		}
		//画出炸弹
		for(int i = 0; i < bombs.size(); i++) {
			//取出炸弹
			Bomb bomb = bombs.get(i);
			if(bomb.life > 6) {
				g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
			} else if(bomb.life > 3) {
				g.drawImage(image2, bomb.x, bomb.y, 30, 30, this);
			} else {
				g.drawImage(image3, bomb.x, bomb.y, 30, 30, this);
			}
			//让炸弹的生命值减少
			bomb.lifeDown();
			//如果炸弹生命值为0,就从集合中将炸弹移除
			if(bomb.life == 0) {
				bombs.remove(bomb);
			}
		}
	}

	//判断敌方子弹是否击中我方坦克
	public void hitHeroTank() {
		for(int i = 0; i < ets.size(); i++) {
			//取出每一辆敌方坦克
			EnemyTanke et = ets.get(i);
			for(int j = 0; j < et.bullets.size(); j++) {
				//取出每一颗敌方子弹
				Bullet ebt = et.bullets.get(j);
				//判断子弹是否有效
				if(ebt.isLive) {
					//判断我方坦克是否有效
					if(hero.isLive) {
						//调用函数
						if(this.hitTanke(ebt, hero)) {
							//自己的坦克数量减1
							Recorder.reduceMyLife();
						}
					}
				}
			}
		}
	}
	
	//判断我的子弹是否击中敌人坦克
	public void hitEnemyTank() {
		for(int i = 0; i < hero.heroBullet.size(); i++) {
			//取出每一颗子弹
			Bullet myBullet = hero.heroBullet.get(i);
			//判断子弹是否有效
			if(myBullet.isLive) {
				//判断
				for(int j = 0; j < ets.size(); j++) {
					//取出敌人坦克
					EnemyTanke et = ets.get(j);
					//判断坦克是否有效
					if(et.isLive) {
						//调用函数
						if(this.hitTanke(myBullet, et)) {
							//敌人的坦克数量减1
							Recorder.reduceEnNum();
							//增加对方的战击数
							Recorder.addEnNumRec();
						}
					}
				}
			}
		}
	}
	
	/**
	 * 写一个函数用来判断子弹是否击中对方的坦克，传一个子弹对象和对方坦克对象
	 */
	public boolean hitTanke(Bullet bt, Tanke tanke) {
		boolean b = false;
		//判断敌人坦克的方向
		switch(tanke.direct) {
		//敌人坦克的方向是向上或者向下
		case 0:
		case 2:
			if(bt.x > tanke.x && bt.x < tanke.x + 20 && bt.y > tanke.y && bt.y < tanke.y + 30) {
				//子弹击中了坦克--子弹与敌人坦克死亡
				bt.isLive = false;
				tanke.isLive = false;
				b = true;
				//创建一颗炸弹,并将被击中的坦克的坐标赋值给炸弹
				Bomb bomb = new Bomb(tanke.x, tanke.y);
				//将炸弹放在bombs集合中
				bombs.add(bomb);
			}
			break;
		//敌人坦克的方向是向右或者向左
		case 1:
		case 3:
			if(bt.x > tanke.x && bt.x < tanke.x + 30 && bt.y > tanke.y && bt.y < tanke.y + 20) {
				//子弹击中了坦克--子弹与敌人坦克死亡
				bt.isLive = false;
				tanke.isLive = false;
				b = true;
				//创建一颗炸弹,并将被击中的坦克的坐标赋值给炸弹
				Bomb bomb = new Bomb(tanke.x, tanke.y);
				//将炸弹放在bombs集合中
				bombs.add(bomb);
			}
			break;
		}
		return b;
	}
	
	/*
	 * 画出坦克（封装函数并扩展）
	 * 传参：横坐标、纵坐标、画笔、方向、坦克类型
	 */
	public void drawTanke(int x, int y, Graphics g, int direct, int type) {
		//首先判断坦克类型
		switch(type) {
		case 0:
			//0表示自己的坦克，自己的坦克是黄色
			g.setColor(Color.YELLOW);
			break;
		case 1:
			//1表示敌人的坦克，敌人的坦克是蓝
			g.setColor(Color.GREEN);
			break;
		}
		//然后判断坦克方向
		switch(direct) {
		//炮筒向上
		case 0:
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30, false);
			//2.画出右边的矩形
			g.fill3DRect(x + 15, y, 5, 30, false);
			//3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			//4.画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			//5.画出一根线
			g.drawLine(x + 10, y + 15, x + 10, y);
			break;
		//炮筒向右
		case 1:
			//1.画出上边的矩形
			g.fill3DRect(x, y, 30, 5, false);
			//2.画出下边的矩形
			g.fill3DRect(x, y + 15, 30, 5, false);
			//3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			//4.画出圆形
			g.fillOval(x + 10, y + 5, 10, 10);
			//5.画出一根线
			g.drawLine(x + 15, y + 10, x + 30, y + 10);
			break;
		//炮筒向下
		case 2:
			//1.画出左边的矩形
			g.fill3DRect(x, y, 5, 30, false);
			//2.画出右边的矩形
			g.fill3DRect(x + 15, y, 5, 30, false);
			//3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			//4.画出圆形
			g.fillOval(x + 5, y + 10, 10, 10);
			//5.画出一根线
			g.drawLine(x + 10, y + 15, x + 10, y + 30);
			break;
		//炮筒向左
		case 3:
			//1.画出上边的矩形
			g.fill3DRect(x, y, 30, 5, false);
			//2.画出下边的矩形
			g.fill3DRect(x, y + 15, 30, 5, false);
			//3.画出中间矩形
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			//4.画出圆形
			g.fillOval(x + 10, y + 5, 10, 10);
			//5.画出一根线
			g.drawLine(x + 15, y + 10, x, y + 10);
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	//键按下处理 w--上键，d--右键，s--下键，a--左键
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			//设置我的坦克的方向--默认方向向上
			this.hero.setDirect(0);
			//设置我的坦克的向上移动速度
			this.hero.moveUp();
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			//向右
			this.hero.setDirect(1);
			this.hero.moveRight();
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			//向下
			this.hero.setDirect(2);
			this.hero.moveDown();
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			//向左
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
		//判断玩家是否按下J键
		if(e.getKeyCode() == KeyEvent.VK_J) {
			//判断我的坦克最多只能连发5颗子弹
			if(this.hero.heroBullet.size() < 10) {
				if(this.hero.isLive) {
					//开火
					this.hero.bulletEnemy();
				}
			}
		}
		//重新绘制repaint
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void run() {
		//每隔100毫秒刷新一次面板---重绘子弹
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//我方子弹击中敌方坦克
			this.hitEnemyTank();
			//敌方子弹击中我方坦克
			this.hitHeroTank();
			//重绘
			this.repaint();
		}
	}
}