package com.demo;
/**
 * ʵ���ַ�����ѹ��,���ַ��� aaabcdda ���ʵ�ֽ���ת��Ϊ 3a1b1c2d1a
 * @author HP
 *
 */
public class DemoTest3 {
public static String test(String str) {
	StringBuffer sb = new StringBuffer();
	char c1 = str.charAt(0);//ȡ��һ���ַ���
	int sum = 1;
	for (int i = 1; i < str.length(); i++) {
		char c2 = str.charAt(i);//ѭ��ȡ�ַ�
		if(c1 == c2) {
			sum++;//��ͬ���ַ�������1
			continue;
		}
		sb.append(sum).append(c1);//ƴ���ַ�
		c1 = c2;//��ǰ�ַ����ǰһ���ַ�
		sum = 1;//��������
	}
	sb.append(sum).append(c1);//�������һ���ַ�������
	return sb.toString();
}
	public static void main(String[] args) {
		System.out.println(test("aabbcccddddaaa"));
	}
}
