package com.demo;
/**
 * 实现字符串的压缩,将字符串 aaabcdda 编程实现将其转换为 3a1b1c2d1a
 * @author HP
 *
 */
public class DemoTest3 {
public static String test(String str) {
	StringBuffer sb = new StringBuffer();
	char c1 = str.charAt(0);//取第一个字符串
	int sum = 1;
	for (int i = 1; i < str.length(); i++) {
		char c2 = str.charAt(i);//循环取字符
		if(c1 == c2) {
			sum++;//相同的字符个数加1
			continue;
		}
		sb.append(sum).append(c1);//拼接字符
		c1 = c2;//当前字符变成前一个字符
		sum = 1;//个数清零
	}
	sb.append(sum).append(c1);//加上最后一个字符及个数
	return sb.toString();
}
	public static void main(String[] args) {
		System.out.println(test("aabbcccddddaaa"));
	}
}
