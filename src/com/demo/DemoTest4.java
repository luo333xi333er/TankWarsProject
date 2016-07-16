package com.demo;

/**
 * 取出字符串内的数字并求和
 */
public class DemoTest4 {
public static int test(String str) {
	int sum = 0;//初始化和值
	for (int i = 0; i < str.length(); i++) {
		if(Character.isDigit(str.charAt(i))) {//判断字符串是否为数字
			sum += Integer.parseInt(str.charAt(i) + "");
		}
	}
	return sum;
}
	public static void main(String[] args) {
		System.out.println(test("ggg5ggg3ggg2"));
	}	
}
