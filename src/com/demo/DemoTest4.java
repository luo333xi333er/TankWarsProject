package com.demo;

/**
 * ȡ���ַ����ڵ����ֲ����
 */
public class DemoTest4 {
public static int test(String str) {
	int sum = 0;//��ʼ����ֵ
	for (int i = 0; i < str.length(); i++) {
		if(Character.isDigit(str.charAt(i))) {//�ж��ַ����Ƿ�Ϊ����
			sum += Integer.parseInt(str.charAt(i) + "");
		}
	}
	return sum;
}
	public static void main(String[] args) {
		System.out.println(test("ggg5ggg3ggg2"));
	}	
}
