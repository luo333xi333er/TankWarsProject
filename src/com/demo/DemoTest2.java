package com.demo;

import java.util.Scanner;

/**
 * ���������һ�����������е����������ֱ��ص���������ֵΪ1��
 * ����ÿ����ֵΪ�����Ϸ�Ԫ��ֵ�����Ϸ�Ԫ��ֵ֮�͡�
 * ���������ʾ��array[i][j]=array[i-1][j-1]+array[i-1][j]
 */
public class DemoTest2 {
	public static void main(String[] args) {
		System.out.println("������������ǵ�����:");
		Scanner sc = new Scanner(System.in);
		final int Rows = sc.nextInt();
		//������ά����
		int array[][] = new int[Rows+1][];
		//ѭ����ʼ������
		for(int i = 0; i < Rows; i++) {
			//��������Ķ�ά����
			array[i] = new int[i+1];
		}
		System.out.println("�������Ϊ��");
		Triangle(array, Rows);
	}
	public static void 	Triangle(int array[][], int rows) {
		//�п���
		for(int i = 0; i < rows; i++) {
			//�п���
			for(int j = 0; j < array[i].length; j++) {
				//��ֵ����ά���飬�����ߵ�Ԫ�ظ�ֵΪ1
				if(i == 0 || j == 0 || j == array[i].length - 1) {
					array[i][j] = 1;
				} else {
					array[i][j]=array[i-1][j-1]+array[i-1][j];
				}
			}
		}
		//��ӡ�������
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < array[i].length; j++) {
				System.out.print(array[i][j] + " ");
			}
			System.out.println();
		}
	}
}