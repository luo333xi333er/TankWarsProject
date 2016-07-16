package com.demo;

import java.util.Scanner;

/**
 * 杨辉三角是一个由数字排列的三角形数字表，特点是两侧数值为1，
 * 其余每个数值为其正上方元素值与左上方元素值之和。
 * 可用数组表示：array[i][j]=array[i-1][j-1]+array[i-1][j]
 */
public class DemoTest2 {
	public static void main(String[] args) {
		System.out.println("请输入杨辉三角的行数:");
		Scanner sc = new Scanner(System.in);
		final int Rows = sc.nextInt();
		//声明二维数组
		int array[][] = new int[Rows+1][];
		//循环初始化数组
		for(int i = 0; i < Rows; i++) {
			//设置数组的二维行数
			array[i] = new int[i+1];
		}
		System.out.println("杨辉三角为：");
		Triangle(array, Rows);
	}
	public static void 	Triangle(int array[][], int rows) {
		//行控制
		for(int i = 0; i < rows; i++) {
			//列控制
			for(int j = 0; j < array[i].length; j++) {
				//赋值给二维数组，将两边的元素赋值为1
				if(i == 0 || j == 0 || j == array[i].length - 1) {
					array[i][j] = 1;
				} else {
					array[i][j]=array[i-1][j-1]+array[i-1][j];
				}
			}
		}
		//打印杨辉三角
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}