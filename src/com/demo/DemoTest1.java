package com.demo;

import java.util.ArrayList;

/**
 * 设有ｎ个人依围成一圈，从第１个人开始报数，数到第ｍ个人出列，然后从出列的下一个人开始报数，
 * 数到第ｍ个人又出列，…，如此反复到所有的人全部出列为止。
 * 设ｎ个人的编号分别为1，2，…，n，打印出出列的顺序；要求用java实现。
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


