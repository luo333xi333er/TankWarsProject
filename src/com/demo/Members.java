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

//������������
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
		//���ǻ���
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


//������һ��--�ָ�̹�����꣬��̹�˿�����һ����
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

//��¼�࣬ͬʱҲ���Ա�����ҵ�
class Recorder {
	//��¼ÿ���ж��ٵ���
	private static int enNum = 20;
	//�������ж��ٿ����õ���
	private static int myLife = 3;
	//��¼�ܹ������˶��ٵ���
	private static int AllEnNum = 0;
	//���ļ��лָ���¼��
	private static Vector<Node> nodes = new Vector<Node>();
	//ʹ���ַ���
	private static FileWriter fw = null;
	private static FileReader fr = null;
	//������
	private static BufferedWriter bw = null;
	private static BufferedReader br = null;
	private static Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	
	//������һ��--��ɶ�ȡ����
	public static Vector<Node> getNodeAndEnemyTank() {
		try {
			fr = new FileReader("e:\\myRecording.txt");
			br = new BufferedReader(fr);
			String n = "";
			//�ȶ���һ��
			n=br.readLine();
			//�ָ�����̹������
			AllEnNum = Integer.parseInt(n);
			//���Ŵӵڶ��п�ʼ��̹�˵������뷽��
			while((n=br.readLine()) != null) {
				//�Կո�ÿ�����ݽ��зָ�õ�һ���ַ�������
				String[] xyz = n.split(" ");
				int x = Integer.parseInt(xyz[0]);
				int y = Integer.parseInt(xyz[1]);
				int direct = Integer.parseInt(xyz[2]);
				Node node = new Node(x, y, direct);
				//��node���뵽nodes������
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
	
	//������ٵ���̹�˵������������Լ�����
	public static void keepRecAndEnemyTank() {
		//�����ļ���
		try {
			fw = new FileWriter("e:\\myRecording.txt");
			bw = new BufferedWriter(fw);
			//��¼���ܵ��˵�̹������
			bw.write(AllEnNum + "\r\n");
			//���浱ǰ��ĵ���̹�˵�����ͷ���
			for(int i = 0; i < ets.size(); i++) {
				//ȡ��ÿһ��̹��
				EnemyTanke et = ets.get(i);
				if(et.isLive) {
					//����
					String str = et.x + " " + et.y + " " + et.direct;
					//д�뵽�ļ�
					bw.write(str + "\r\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�󿪵��ȹ�
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	//��¼��һ��ٵ���̹�����������浽�ļ���
	public static void keepRecording() {
		//�����ļ���
		try {
			fw = new FileWriter("e:\\myRecording.txt");
			bw = new BufferedWriter(fw);
			bw.write(AllEnNum + "\r\n");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//�󿪵��ȹ�
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//���ļ��ж�ȡ��¼
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
		
	
	//���ٵ�������
	public static void reduceEnNum() {
		enNum--;
	}
	//�����Լ�����
	public static void reduceMyLife() {
		myLife--;
	}
	//�������
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

//����һ��ը����
class Bomb {
	//����ը��������
	int x;
	int y;
	//����ը������--ÿ��ըһ��ը����������һ��
	int life = 9;
	//����ը���Ƿ����
	boolean isLive = true;
	public Bomb(int x, int y) {
		this.x = x;
		this.y = y;
	}
	//��������ֵ
	public void lifeDown() {
		if(life > 0) {
			life--;
		} else {
			this.isLive = false;
		}
	}
}

//����һ���ӵ���
class Bullet implements Runnable{
	//�ӵ�����
	int x;
	int y;
	//�ӵ�����
	int direct;
	//�ӵ��ٶ�
	int speed = 2;
	//�ӵ��Ƿ����
	boolean isLive = true;
	public Bullet(int x, int y, int direct) {
		this.x = x;
		this.y = y;
		this.direct = direct;
	}
	@Override
	public void run() {
		while(true) {
			//���ӵ�ÿ�����䶯��Ϣ50����
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			switch(direct) {
			case 0:
				//�ӵ�����
				y -= speed;
				break;
			case 1:
				//�ӵ�����
				x += speed;
				break;
			case 2:
				//�ӵ�����
				y += speed;
				break;
			case 3:
				//�ӵ�����
				x -= speed;
				break;
			}
			/**
			 * �ӵ�ʲôʱ������������
			 * 1.���ӵ���������̹��ʱ����
			 * 2.���ӵ������߿�ʱ̹������
			 */
			if(x < 0 || x > 800 || y < 0 || y > 600) {
				this.isLive = false;
				break;
			}
		}
	}
}

//����һ��̹����
class Tanke {
	//��ʾ̹�˵ĺ�����
	int x = 0;
	//��ʾ̹�˵�������
	int y = 0;
	//̹�˷��� 0--�� �� 1--�ң� 2--�£� 3--��
	int direct = 0;
	//̹�˵��ٶ�
	int speed = 3;
	//̹����ɫ--�����Լ��͵���
	int color;
	//̹���Ƿ����
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

//����һ���ҵ�̹����
class Hero extends Tanke {
	//��������ӵ�
	Vector<Bullet> heroBullet = new Vector<Bullet>();
	Bullet bullet = null;
	public Hero(int x, int y) {
		super(x, y);
	}
	
	//����
	public void bulletEnemy() {
		switch (this.direct) {
		//����
		case 0:
			//����һ���ӵ�
			bullet = new Bullet(x + 10, y, 0);
			//���ӵ����뵽������
			heroBullet.add(bullet);
			break;
		//����
		case 1:
			bullet = new Bullet(x + 30, y + 10, 1);
			heroBullet.add(bullet);
			break;
		//����
		case 2:
			bullet = new Bullet(x + 10, y + 30, 2);
			heroBullet.add(bullet);
			break;
		//����
		case 3:
			bullet = new Bullet(x, y + 10, 3);
			heroBullet.add(bullet);
			break;
		}
		//�����ӵ��߳�
		Thread thread = new Thread(bullet);
		thread.start();
	}
	
	//�ж��Ƿ���ײ���˵���̹��--------�ò�������ϵĵ��˵�̹������
	public boolean isTouchEnemy() {
		boolean b = false;
		//����һ�����������Է��ʵ�MyPanel������ͬ������̹��
		Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
		switch(this.direct) {
		//�Լ�̹������
		case 0:
			//ȡ�����е���̹��
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//�жϵ���̹�˵ķ��������ϻ�������
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
				}
				//�жϵ���̹�˵ķ��������һ�������
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
		//�Լ�̹������
		case 1:
			for(int i = 0; i < ets.size(); i++) {
				//ȡ������̹��
				EnemyTanke et = ets.get(i);
				//�жϵ���̹�˵ķ��������ϻ�������
				if(et.direct == 0 || et.direct == 2) {
					if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
						return true;
					}
				}
				//�жϵ���̹�˵ķ��������һ�������
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
		//�Լ�̹������
		case 2:
			//ȡ�����е���̹��
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//�жϵ���̹�˵ķ��������ϻ�������
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
						return true;
					}
					if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
						return true;
					}
				}
				//�жϵ���̹�˵ķ��������һ�������
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
		//�Լ�̹������
		case 3:
			//ȡ������̹��
			for(int i = 0; i < ets.size(); i++) {
				EnemyTanke et = ets.get(i);
				//�жϵ���̹�˵ķ��������ϻ�������
				if(et.direct == 0 || et.direct == 2) {
					if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
						return true;
					}
					if(this.x >= et.x && this.x <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
						return true;
					}
				}
				//�жϵ���̹�˵ķ��������һ�������
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
	
	//̹�������ƶ�
	public void moveUp() {
		//�ж��ҷ�̹���Ƿ�Խ��
		if(y > 0 && !this.isTouchEnemy()) {
			y -= speed;
		}
	}
	//̹�������ƶ�
	public void moveRight() {
		if(x < 800 - 48 && !this.isTouchEnemy()) {
			x += speed;
		}
	}
	//̹�������ƶ�
	public void moveDown() {
		if(y < 600 - 70 && !this.isTouchEnemy()) {
			y += speed;
		}
	}
	//̹�������ƶ�
	public void moveLeft() {
		if(x > 0 && !this.isTouchEnemy()) {
			x -= speed;
		}
	}
}

//����һ������̹����,�ѵ���̹������һ���߳���
class EnemyTanke extends Tanke implements Runnable{
	//��ʱ��
	int times = 0;
	//����һ������������ŵ����ӵ�
	Vector<Bullet> bullets = new Vector<Bullet>();
	//��������ӵ�--�ڵ���̹�˴������ߵ����ӵ�������ʱ�����
	Bullet bullet = null;
	//����һ�����������Է��ʵ�MyPanel������ͬ������̹��
	Vector<EnemyTanke> ets = new Vector<EnemyTanke>();
	
	public EnemyTanke(int x, int y) {
		super(x, y);
	}

	//�õ�MyPanel�ϵ���̹������
	public void setEts(Vector<EnemyTanke> vv) {
		this.ets = vv;
	}
	//�ж��Ƿ���ײ����ͬ������̹��
	public boolean isTouchOther() {
		boolean b = false;
		switch(this.direct) {
		//ĳ������̹������
		case 0:
			//ȡ�����е�ͬ������̹��
			for(int i = 0; i < ets.size(); i++) {
				//ȡ����һ��̹��
				EnemyTanke et = ets.get(i);
				//�жϵ�һ��̹���Ƿ����Լ�
				if(et != this) {
					//�ж�ͬ������̹�˵ķ��������ϻ�������
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
					}
					//�ж�ͬ������̹�˵ķ��������һ�������
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
		//ĳ������̹������
		case 1:
			//ȡ�����е�ͬ������̹��
			for(int i = 0; i < ets.size(); i++) {
				//ȡ����һ��̹��
				EnemyTanke et = ets.get(i);
				//�жϵ�һ��̹���Ƿ����Լ�
				if(et != this) {
					//�ж�ͬ������̹�˵ķ��������ϻ�������
					if(et.direct == 0 || et.direct == 2) {
						if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x + 30 >= et.x && this.x + 30 <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					//�ж�ͬ������̹�˵ķ��������һ�������
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
		//ĳ������̹������
		case 2:
			//ȡ�����е�ͬ������̹��
			for(int i = 0; i < ets.size(); i++) {
				//ȡ����һ��̹��
				EnemyTanke et = ets.get(i);
				//�жϵ�һ��̹���Ƿ����Լ�
				if(et != this) {
					//�ж�ͬ������̹�˵ķ��������ϻ�������
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
							return true;
						}
						if(this.x + 20 >= et.x && this.x + 20 <= et.x + 20 && this.y + 30 >= et.y && this.y + 30 <= et.y + 30) {
							return true;
						}
					}
					//�ж�ͬ������̹�˵ķ��������һ�������
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
		//ĳ������̹������
		case 3:
			//ȡ�����е�ͬ������̹��
			for(int i = 0; i < ets.size(); i++) {
				//ȡ����һ��̹��
				EnemyTanke et = ets.get(i);
				//�жϵ�һ��̹���Ƿ����Լ�
				if(et != this) {
					//�ж�ͬ������̹�˵ķ��������ϻ�������
					if(et.direct == 0 || et.direct == 2) {
						if(this.x >= et.x && this.x <= et.x + 20 && this.y >= et.y && this.y <= et.y + 30) {
							return true;
						}
						if(this.x >= et.x && this.x <= et.x + 20 && this.y + 20 >= et.y && this.y + 20 <= et.y + 30) {
							return true;
						}
					}
					//�ж�ͬ������̹�˵ķ��������һ�������
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
			//����̹�˼̳и���ķ�������
			switch (this.direct) {
			//̹�����������ƶ�
			case 0:
				for(int i = 0; i < 30; i++) {
					//�ж�̹���Ƿ���߽�
					if(y > 0 && !this.isTouchOther()) {
						y-=speed;
					}
					//��̹������50����
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//̹�����������ƶ�
			case 1:
				for(int i = 0; i < 30; i++) {
					//�ж�̹���Ƿ���߽�
					if(x < 800 - 48 && !this.isTouchOther()) {
						x+=speed;
					}
					//��̹������50����
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//̹�����������ƶ�
			case 2:
				for(int i = 0; i < 30; i++) {
					//�ж�̹���Ƿ���߽�
					if(y < 600 - 70 && !this.isTouchOther()) {
						y+=speed;
					}
					//��̹������50����
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			//̹�����������ƶ�
			case 3:
				for(int i = 0; i < 30; i++) {
					//�ж�̹���Ƿ���߽�
					if(x > 0 && !this.isTouchOther()) {
						x-=speed;
					}
					//��̹������50����
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				break;
			}
			//30*50=1500=1.5�����times�Լ�
			this.times++;
			//ʱ��ÿ�����룬����̹�˲���һ���ӵ�
			if(times % 2 == 0) {
				if(this.isLive) {
					//�жϵ���̹���Ƿ�5���ӵ��������ʹ���
					if(bullets.size() < 5) {
						Bullet bullet = null;
						switch (this.direct) {
						//����
						case 0:
							//����һ���ӵ�
							bullet = new Bullet(x + 10, y, 0);
							//���ӵ����뵽������
							bullets.add(bullet);
							break;
						//����
						case 1:
							bullet = new Bullet(x + 30, y + 10, 1);
							bullets.add(bullet);
							break;
						//����
						case 2:
							bullet = new Bullet(x + 10, y + 30, 2);
							bullets.add(bullet);
							break;
						//����
						case 3:
							bullet = new Bullet(x, y + 10, 3);
							bullets.add(bullet);
							break;
						}
						//�����ӵ��߳�
						Thread thread = new Thread(bullet);
						thread.start();
					}
				}
			}
			
			//��̹���������һ���µķ���
			this.direct = (int)(Math.random() * 4);
			//�жϵ���̹���Ƿ�����
			if(this.isLive == false) {
				//���������̹���˳��߳�
				break;
			}
		}
	}
}
