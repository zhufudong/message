package com.zfd.message;


import java.util.Random;

/**
 * 唯一的序列
 * 
 * @author zfd
 * @createtime 2018年4月17日
 * @email zhufudong001@gmail.com
 */
public class Unique {
	/**
	 * 得到唯一序列
	 * 
	 * @param rand
	 * @param num
	 * @param max
	 * @return
	 */
	public static int[] getSequence(Random rand, int num) {
		int ret[] = new int[num];
		int max[] = new int[num];
		int maxs = num;
		for (int i = 0; i < num; i++) {
			int r = rand.nextInt(maxs--);
			ret[i] = max[r] == 0 ? r : max[r];
			max[r] = max[maxs] == 0 ? maxs : max[maxs];
		}
		return ret;
	}

	/**
	 * 得到一个序列
	 * 
	 * @param num
	 * @return
	 */
	public static String getSequence(int num,int max) {
		int[] result = getSequence(new Random(), num);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < max; i++) {
			sb.append(result[i]);
		}
		return sb.toString();
	}

}
