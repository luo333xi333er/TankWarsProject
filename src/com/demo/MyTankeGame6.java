
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
 * ���ܣ�̹��5.0�汾
 * 1.����̹��
 * 2.��̹������¼�����̹�����������ƶ�
 * 3.���ҵ�̹�˷����ӵ�
 * 4.���ӵ�ʵ������,�����������
 * 5.���ҷ�̹�˻��е���̹��ʱ����̹���Զ���ʧ
 *    1>.���������̹��������ҵ��ӵ�������
 *    2>.дһ��ר���ж��ӵ��Ƿ���е���̹�˵ĺ���
 *    3>.��ʲô�ط��ж��ӵ��Ƿ���е��˵�̹��---��run�������ж�
 *    4>.������ը��Ч��---��������ͼƬ���滻
 *      a.׼������ͼƬ,����ͼƬ˲������滻
 *      b.����һ��ը����
 *      c.�ڻ��е���̹��ʱ����ը�����뼯����
 *      d.��paint�����л���̹��
 * 6.�õ��˵�̹��Ҳ�ܹ����ɵ����������ƶ�
 * 7.�����ҷ���̹�˺͵��˵�̹�˾��ڹ涨��Χ���ƶ�
 * 8.�õ���̹��Ҳ�ܷ����ӵ�
 * 9.������̹�˷������ӵ������ҵ�̹��ʱ���ҵ�̹��Ҳ��ը-----����5
 * 10.���Ƶ��˵�̹��֮�䲻�ص��˶�
 * 		a.�������ж��Ƿ��ص��ĺ���д��enemyTanke����
 * 11.���Էֹ�
 * 		a.��һ����ʼ��Panel������һ���յ�
 * 		b.����������˸Ч��---�߳�ʵ��
 * 12.����������Ϸ��ʱ����ͣ�ͼ���
 * 		a.����ͨ�����ո��ʵ��
 * 		b.���û������ͣ��ʱ����ӵ���̹�˵��ٶ�����Ϊ0������̹�˵ķ���Ҫ�����仯
 * 13.��¼��ҳɼ�--����io��ʵ��
 *   a.���������ļ����ķ�ʽʵ��
 *   b.��дһ����¼����ɶ���ҵļ�¼
 *   c.����ɱ��湲�����˶���������̹�˵Ĺ���
 *   d.�����˳���Ϸ���´δ���Ϸʱ�����Իָ����ϴ��˳���״̬��������Ϸ
 * 		1.���ǽ������̹���������浽����
 * 		2.���˳���Ϸʱ������̹�˵����걣�浽�����´μ�����Ϸ
 * 14.����java���������ļ�
 * 		a.��һ�������ļ�
 * 		b.
 */
public class MyTankeGame6 extends JFrame implements ActionListener{
	MyPanel mp = null;
	//����һ����ʼ���
	StartPanel startPanel = null;
	//���˵�
	JMenuBar jmb = null;
	//�˵�ѡ��--��Ϸ
	JMenu jm1 = null;
	//��ʼ��Ϸ
	JMenuItem jmi1 = null;
	//�����˳�
	JMenuItem jmi2 = null;
	//�����˳�
	JMenuItem jmi3 = null;
	//�����Ͼ���Ϸ
	JMenuItem jmi4 = null;
	
	public static void main(String[] args) {
		MyTankeGame6 game = new MyTankeGame6();
	}
	
	//���캯��
	public MyTankeGame6() {
		startPanel = new StartPanel();
		this.add(startPanel);
		//����startPanel�߳�
		Thread thread = new Thread(startPanel);
		thread.start();
		//�����˵����˵�ѡ��
		jmb = new JMenuBar();
		jm1 = new JMenu("��Ϸ(G)");
		//���ÿ�ݷ�ʽ alt+G
		jm1.setMnemonic('G');
		jmi1 = new JMenuItem("��ʼ��Ϸ(N)");
		jmi1.setMnemonic('N');
		jmi2 = new JMenuItem("�����˳�(E)");
		jmi2.setMnemonic('E');
		jmi3 = new JMenuItem("�����˳�(S)");
		jmi3.setMnemonic('S');
		jmi4 = new JMenuItem("�����Ͼ�(C)");
		jmi4.setMnemonic('C');
		//��jmi1��Ӽ���
		jmi1.addActionListener(this);
		jmi1.setActionCommand("newGame");
		//��jmi2��Ӽ���
		jmi2.addActionListener(this);
		jmi2.setActionCommand("exitGame");
		//��jmi3��Ӽ���
		jmi3.addActionListener(this);
		jmi3.setActionCommand("saveExitGame");
		//��jmi4��Ӽ���
		jmi4.addActionListener(this);
		jmi4.setActionCommand("beforeGame");
		jm1.add(jmi1);
		jm1.add(jmi2);
		jm1.add(jmi3);
		jm1.add(jmi4);
		jmb.add(jm1);
		this.setJMenuBar(jmb);
		this.setSize(900, 700);
		//�������
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//���û���ͬ�ĵ��������ͬ�Ĵ���
		if(e.getActionCommand().equals("newGame")) {
			//����ս�����
			this.mp = new MyPanel("newGame");
			//����mp�߳�
			Thread thread = new Thread(mp);
			thread.start();
			//���Ƴ���֮ǰ�ľ����
			this.remove(startPanel);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ�����
			this.setVisible(true);
		} else if(e.getActionCommand().equals("exitGame")) {
			//�û�������˳���Ϸ�Ĳ˵�
			//������ٵ���̹������
			Recorder.keepRecording();
			System.exit(0);
		} else if(e.getActionCommand().equals("saveExitGame")) {
			Recorder.setEts(mp.ets);
			//�����˳�--������ٵ��˵������͵��˵�����
			Recorder.keepRecAndEnemyTank();
			//�˳�
			System.exit(0);
		} else if(e.getActionCommand().equals("beforeGame")) {
			//������һ����Ϸ
			//����ս�����
			mp = new MyPanel("continue");
			//����mp�߳�
			Thread thread = new Thread(mp);
			thread.start();
			//���Ƴ���֮ǰ�ľ����
			this.remove(startPanel);
			this.add(mp);
			//ע�����
			this.addKeyListener(mp);
			//��ʾ�����
			this.setVisible(true);
		}
	}
}

//��һ����ʼ���---����������ʾ�����ã�Ϊʵ�ַֹ����̵�
class StartPanel extends JPanel implements Runnable{
	//������
	int times = 0;
	public void paint(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 900, 800);
		if(times % 2 == 0) {
			//������ɫ
			g.setColor(Color.RED);
			//��ʾ��Ϣ--������Ϣ������
			g.setFont(new Font("����", Font.BOLD, 100));
			g.drawString("stage: 1", 230, 310);
		}
	}
	//����ʾ��Ϣ��ʾ��˸Ч��
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			times++;
			//�ػ�
			this.repaint();
		}
	}
}

//�ҵ����
class MyPanel extends JPanel implements KeyListener, Runnable{
	//����һ���ҵ�̹��
	Hero hero = null;
	Vector<Node> nodes = null;
	//����һ������̹����---vector
	Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	//������˵�̹��Ϊ6��
	int enemySize = 6;
	//����ը������
	Vector<Bomb> bombs = new Vector<Bomb>();
	//��������ͼƬ��ʵ�ֱ�ը��
	Image image1 = null;
	Image image2 = null;
	Image image3 = null;
	
	//���캯��
	public MyPanel(String flag) {
		//���ú���---�ָ��˳���Ϸ�����
		Recorder.getRecoding();
		hero = new Hero(350, 250);
		//�ж�
		if(flag.equals("newGame")) {
			//��ʼ�����˵�̹��
			for(int i = 0; i < enemySize; i++) {
				//����һ�����˵�̹�˶���
				EnemyTanke et = new EnemyTanke((i + 1) * 120, 0);
				//���õ���̹�˵ķ���
				et.setDirect(2);
				//��MyPanel�ĵ���̹�����������õ���̹��
				et.setEts(ets);
				//��������̹��
				Thread thread = new Thread(et);
				thread.start();
				//����һ�ŵ���̹�˷�����ӵ�
				Bullet bullet = new Bullet(et.x + 10, et.y + 30, et.direct);
				//������̹������ӵ�---��ӵ�������
				et.bullets.add(bullet);
				//�����ӵ��߳�
				Thread thread2 = new Thread(bullet);
				thread2.start();
				//����ʼ���ĵ���̹������ڼ�����
				ets.add(et);
			}
		} else {
			this.nodes = Recorder.getNodeAndEnemyTank();
			//�ָ����˵�̹��
			for(int i = 0; i < nodes.size(); i++) {
				//�ָ����˵�̹�˶���
				EnemyTanke et = new EnemyTanke(nodes.get(i).x, nodes.get(i).y);
				//�ָ�����̹�˵ķ���
				et.setDirect(nodes.get(i).direct);
				//��MyPanel�ĵ���̹�����������õ���̹��
				et.setEts(ets);
				//��������̹��
				Thread thread = new Thread(et);
				thread.start();
				//����һ�ŵ���̹�˷�����ӵ�
				Bullet bullet = new Bullet(et.x + 10, et.y + 30, et.direct);
				//������̹������ӵ�---��ӵ�������
				et.bullets.add(bullet);
				//�����ӵ��߳�
				Thread thread2 = new Thread(bullet);
				thread2.start();
				//����ʼ���ĵ���̹������ڼ�����
				ets.add(et);
			}
		}
		
		//ʹ��IO����ʼ������ͼƬ---����ͼƬ���л����һ��ը��
		try {
			image1 = ImageIO.read(new File("bomb1.jpg"));
			image2 = ImageIO.read(new File("bomb2.jpg"));
			image3 = ImageIO.read(new File("bomb3.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//�������
		AePlayWave apw = new AePlayWave("e:\\333.wma");
		apw.start();
		
		//ʹ��toolkit��ʼ������ͼƬ---����ͼƬ���л����һ��ը��
//		image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.jpg"));
//		image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.jpg"));
//		image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.jpg"));
	}
	
	//������ʾ��Ϣ
	public void showInfo(Graphics g) {
		super.paint(g);
		g.fillRect(0, 0, 800, 600);
		//��������̹����ʾ��Ϣ����̹�˲�����ս��
		this.drawTanke(270, 605, g, 0, 1);
		g.setColor(Color.BLACK);
		g.setFont(new Font("����", Font.BOLD, 25));
		g.drawString(Recorder.getEnNum()+"", 310, 628);
		//�����ҵ�̹����ʾ��Ϣ����̹�˲�����ս��
		this.drawTanke(460, 605, g, 0, 0);
		g.setColor(Color.BLACK);
		g.setFont(new Font("����", Font.BOLD, 25));
		g.drawString(Recorder.getMyLife()+"", 500, 628);
		//������ҵ��ܳɼ�
		g.setColor(Color.RED);
		g.setFont(new Font("����", Font.BOLD, 25));
		g.drawString("�ܳɼ�", 800, 60);
		//��������̹��
		this.drawTanke(810, 100, g, 0, 1);
		//�������ܵ�������
		g.drawString(Recorder.getAllEnNum()+"", 850, 125);
	}
	
	//����paint
	public void paint(Graphics g) {
		super.paint(g);
		//������ʾ��Ϣ
		this.showInfo(g);
		//�����Լ���̹��
		if(hero.isLive) {
			//����drawTanke����
			this.drawTanke(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
		}
		//�������˵�̹��
		for(int i = 0; i < ets.size(); i++) {
			//ȡ������̹��
			EnemyTanke et = ets.get(i);
			//�ж�̹���Ƿ����
			if(et.isLive) {
				//����̹��
				this.drawTanke(et.getX(), et.getY(), g, et.getDirect(), 1);
				//��bullets������ȡ��ÿһ���ӵ�
				for(int j = 0; j < et.bullets.size(); j++) {
					//ȡ��һ���ӵ�
					Bullet enemyBullet = et.bullets.get(j);
					//����һ���ӵ�--���ж�
					if(enemyBullet != null && enemyBullet.isLive) {
						g.draw3DRect(enemyBullet.x, enemyBullet.y, 3, 3, false);
					} else {
						//������˵�̹�������ʹ��ӵ�������ɾ���ӵ�
						et.bullets.remove(enemyBullet);
					}
				}
			}
		}
		//��bullets������ȡ��ÿһ���ӵ�������
		for(int i = 0; i < hero.heroBullet.size(); i++) {
			//ȡ��һ���ӵ�
			Bullet myBullet = hero.heroBullet.get(i);
			//����һ���ӵ�--���ж�
			if(myBullet != null && myBullet.isLive) {
				g.setColor(Color.YELLOW);
				g.draw3DRect(myBullet.x, myBullet.y, 3, 3, false);
			} else{
				//��bullets�������Ƴ����ӵ�
				hero.heroBullet.remove(myBullet);
			}
		}
		//����ը��
		for(int i = 0; i < bombs.size(); i++) {
			//ȡ��ը��
			Bomb bomb = bombs.get(i);
			if(bomb.life > 6) {
				g.drawImage(image1, bomb.x, bomb.y, 30, 30, this);
			} else if(bomb.life > 3) {
				g.drawImage(image2, bomb.x, bomb.y, 30, 30, this);
			} else {
				g.drawImage(image3, bomb.x, bomb.y, 30, 30, this);
			}
			//��ը��������ֵ����
			bomb.lifeDown();
			//���ը������ֵΪ0,�ʹӼ����н�ը���Ƴ�
			if(bomb.life == 0) {
				bombs.remove(bomb);
			}
		}
	}

	//�жϵз��ӵ��Ƿ�����ҷ�̹��
	public void hitHeroTank() {
		for(int i = 0; i < ets.size(); i++) {
			//ȡ��ÿһ���з�̹��
			EnemyTanke et = ets.get(i);
			for(int j = 0; j < et.bullets.size(); j++) {
				//ȡ��ÿһ�ŵз��ӵ�
				Bullet ebt = et.bullets.get(j);
				//�ж��ӵ��Ƿ���Ч
				if(ebt.isLive) {
					//�ж��ҷ�̹���Ƿ���Ч
					if(hero.isLive) {
						//���ú���
						if(this.hitTanke(ebt, hero)) {
							//�Լ���̹��������1
							Recorder.reduceMyLife();
						}
					}
				}
			}
		}
	}
	
	//�ж��ҵ��ӵ��Ƿ���е���̹��
	public void hitEnemyTank() {
		for(int i = 0; i < hero.heroBullet.size(); i++) {
			//ȡ��ÿһ���ӵ�
			Bullet myBullet = hero.heroBullet.get(i);
			//�ж��ӵ��Ƿ���Ч
			if(myBullet.isLive) {
				//�ж�
				for(int j = 0; j < ets.size(); j++) {
					//ȡ������̹��
					EnemyTanke et = ets.get(j);
					//�ж�̹���Ƿ���Ч
					if(et.isLive) {
						//���ú���
						if(this.hitTanke(myBullet, et)) {
							//���˵�̹��������1
							Recorder.reduceEnNum();
							//���ӶԷ���ս����
							Recorder.addEnNumRec();
						}
					}
				}
			}
		}
	}
	
	/**
	 * дһ�����������ж��ӵ��Ƿ���жԷ���̹�ˣ���һ���ӵ�����ͶԷ�̹�˶���
	 */
	public boolean hitTanke(Bullet bt, Tanke tanke) {
		boolean b = false;
		//�жϵ���̹�˵ķ���
		switch(tanke.direct) {
		//����̹�˵ķ��������ϻ�������
		case 0:
		case 2:
			if(bt.x > tanke.x && bt.x < tanke.x + 20 && bt.y > tanke.y && bt.y < tanke.y + 30) {
				//�ӵ�������̹��--�ӵ������̹������
				bt.isLive = false;
				tanke.isLive = false;
				b = true;
				//����һ��ը��,���������е�̹�˵����긳ֵ��ը��
				Bomb bomb = new Bomb(tanke.x, tanke.y);
				//��ը������bombs������
				bombs.add(bomb);
			}
			break;
		//����̹�˵ķ��������һ�������
		case 1:
		case 3:
			if(bt.x > tanke.x && bt.x < tanke.x + 30 && bt.y > tanke.y && bt.y < tanke.y + 20) {
				//�ӵ�������̹��--�ӵ������̹������
				bt.isLive = false;
				tanke.isLive = false;
				b = true;
				//����һ��ը��,���������е�̹�˵����긳ֵ��ը��
				Bomb bomb = new Bomb(tanke.x, tanke.y);
				//��ը������bombs������
				bombs.add(bomb);
			}
			break;
		}
		return b;
	}
	
	/*
	 * ����̹�ˣ���װ��������չ��
	 * ���Σ������ꡢ�����ꡢ���ʡ�����̹������
	 */
	public void drawTanke(int x, int y, Graphics g, int direct, int type) {
		//�����ж�̹������
		switch(type) {
		case 0:
			//0��ʾ�Լ���̹�ˣ��Լ���̹���ǻ�ɫ
			g.setColor(Color.YELLOW);
			break;
		case 1:
			//1��ʾ���˵�̹�ˣ����˵�̹������
			g.setColor(Color.GREEN);
			break;
		}
		//Ȼ���ж�̹�˷���
		switch(direct) {
		//��Ͳ����
		case 0:
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30, false);
			//2.�����ұߵľ���
			g.fill3DRect(x + 15, y, 5, 30, false);
			//3.�����м����
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			//4.����Բ��
			g.fillOval(x + 5, y + 10, 10, 10);
			//5.����һ����
			g.drawLine(x + 10, y + 15, x + 10, y);
			break;
		//��Ͳ����
		case 1:
			//1.�����ϱߵľ���
			g.fill3DRect(x, y, 30, 5, false);
			//2.�����±ߵľ���
			g.fill3DRect(x, y + 15, 30, 5, false);
			//3.�����м����
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			//4.����Բ��
			g.fillOval(x + 10, y + 5, 10, 10);
			//5.����һ����
			g.drawLine(x + 15, y + 10, x + 30, y + 10);
			break;
		//��Ͳ����
		case 2:
			//1.������ߵľ���
			g.fill3DRect(x, y, 5, 30, false);
			//2.�����ұߵľ���
			g.fill3DRect(x + 15, y, 5, 30, false);
			//3.�����м����
			g.fill3DRect(x + 5, y + 5, 10, 20, false);
			//4.����Բ��
			g.fillOval(x + 5, y + 10, 10, 10);
			//5.����һ����
			g.drawLine(x + 10, y + 15, x + 10, y + 30);
			break;
		//��Ͳ����
		case 3:
			//1.�����ϱߵľ���
			g.fill3DRect(x, y, 30, 5, false);
			//2.�����±ߵľ���
			g.fill3DRect(x, y + 15, 30, 5, false);
			//3.�����м����
			g.fill3DRect(x + 5, y + 5, 20, 10, false);
			//4.����Բ��
			g.fillOval(x + 10, y + 5, 10, 10);
			//5.����һ����
			g.drawLine(x + 15, y + 10, x, y + 10);
			break;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	//�����´��� w--�ϼ���d--�Ҽ���s--�¼���a--���
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			//�����ҵ�̹�˵ķ���--Ĭ�Ϸ�������
			this.hero.setDirect(0);
			//�����ҵ�̹�˵������ƶ��ٶ�
			this.hero.moveUp();
		} else if(e.getKeyCode() == KeyEvent.VK_D) {
			//����
			this.hero.setDirect(1);
			this.hero.moveRight();
		} else if(e.getKeyCode() == KeyEvent.VK_S) {
			//����
			this.hero.setDirect(2);
			this.hero.moveDown();
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			//����
			this.hero.setDirect(3);
			this.hero.moveLeft();
		}
		//�ж�����Ƿ���J��
		if(e.getKeyCode() == KeyEvent.VK_J) {
			//�ж��ҵ�̹�����ֻ������5���ӵ�
			if(this.hero.heroBullet.size() < 10) {
				if(this.hero.isLive) {
					//����
					this.hero.bulletEnemy();
				}
			}
		}
		//���»���repaint
		this.repaint();
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	@Override
	public void run() {
		//ÿ��100����ˢ��һ�����---�ػ��ӵ�
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//�ҷ��ӵ����ез�̹��
			this.hitEnemyTank();
			//�з��ӵ������ҷ�̹��
			this.hitHeroTank();
			//�ػ�
			this.repaint();
		}
	}
}