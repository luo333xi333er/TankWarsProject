package com.demo;

import java.util.ArrayList;

/**
 * ���У������Χ��һȦ���ӵڣ����˿�ʼ�����������ڣ���˳��У�Ȼ��ӳ��е���һ���˿�ʼ������
 * �����ڣ�����ֳ��У�������˷��������е���ȫ������Ϊֹ��
 * �����˵ı�ŷֱ�Ϊ1��2������n����ӡ�����е�˳��Ҫ����javaʵ�֡�
 */

public class DemoTest1 {
	public static void main(String[] args) {
		DemoTest1 test1 = new DemoTest1();
		test1.play(8, 7);
	}
	
	public static void play(int n,int m){
        ArrayList list = new ArrayList();
        for (int i=1; i<=n; i++) {
        	list.add(i);
        }
        int at = 0;
        int pp = m-1;
        while (list.size() > 0) {                       
            at += pp;
            if (at >= list.size()) {
            	at %= list.size();
            }
            System.out.println(list.get(at));
            list.remove(at);                       
        }
	}
}


