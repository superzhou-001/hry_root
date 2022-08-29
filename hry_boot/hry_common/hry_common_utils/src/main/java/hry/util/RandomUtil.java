/**
 * Copyright:   北京互融时代软件有限公司
 * @author:      Yuan Zhicheng
 * @version:      V1.0 
 * @Date:        2016年2月22日 上午11:57:25
 */
package hry.util;

import java.util.HashSet;
import java.util.Random;

/**
 * <p> TODO</p>
 * @author:         Yuan Zhicheng 
 * @Date :          2016年2月22日 上午11:57:25 
 */
public class RandomUtil {

	/**
	 * @Title: main
	 * @Description: TODO
	 * @param: @param args
	 * @return: void
	 * @throws
	 */
	public static void main(String[] args) {
		int init=5000000;
		/*HashSet<Integer> set=new HashSet<Integer>();
		
		
		randomSet(1,init,10,set);
		StringBuffer bf=new StringBuffer();
        for(int j:set){
        	
        	bf.append(j);
        	bf.append(",");
        }
       System.out.println(bf.toString());*/
		
		StringBuffer bf=new StringBuffer();
		int[] reult2 = randomArray(1,init,init);  
		for (int i : reult2) {  
			  //  System.out.println(i);  
			  bf.append(i);
	        	bf.append(",");
		}
		
		//System.out.println(bf.toString());
		System.out.println("====");
		strLen(bf.toString());
		
	}
	
	private static void strLen(String s){
	    String[] str=s.split(",");
	    System.out.println("str.length="+str.length);
	}
  
	/** 
	  * 随机指定范围内N个不重复的数  
	  * 在初始化的无重复待选数组中随机产生一个数放入结果中，  
	   * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换  
	  * 然后从len-2里随机产生下一个随机数，如此类推 
	  * @param max  指定范围最大值  
	  * @param min  指定范围最小值 
	  * @param n  随机数个数 
	  * @return int[] 随机数结果集  
	  */

	public static  void randomSet(int min, int max, int n, HashSet<Integer> set) {
		if (n > (max - min + 1) || max < min) {
			return;
		}
		for (int i = 0; i < n; i++) {
			// 调用Math.random()方法
			int num = (int) (Math.random() * (max - min)) + min;
			set.add(num);// 将不同的数存入HashSet中
		}

		int setSize = set.size(); // 如果存入的数小于指定生成的个数，则调用递归再生成剩余个数的随机数，如此循环，
		System.out.println("setSize=="+setSize);
		
		System.out.println("n=="+n);
		// 直到达到指定大小
		if (setSize < n) {
			
			randomSet(min, max, n - setSize, set);
			// 递归
		}
	}
	
	
	/**  
	  * 随机指定范围内N个不重复的数  
	  * 在初始化的无重复待选数组中随机产生一个数放入结果中，  
	  * 将待选数组被随机到的数，用待选数组(len-1)下标对应的数替换  
	  * 然后从len-2里随机产生下一个随机数，如此类推 
	  * @param max  指定范围最大值 
	  * @param min  指定范围最小值  
	  * @param n  随机数个数  
	  * @return int[] 随机数结果集  
	  */  
	public static int[] randomArray(int min,int max,int n){
		int len = max-min+1; 
		if(max < min || n > len){ 
			   return null;  
		}
		int[] source=new int[len];
		
		//初始化给定范围的待选数组
		
		for(int i=min;i<min+len;i++){
			source[i-min]=i;
		}
		
		int[] result = new int[n];

		Random rd = new Random();
		int index = 0; 
		for (int i = 0; i < result.length; i++) {
			//待选数组0到(len-2)随机一个下标 
			   index = Math.abs(rd.nextInt() % len--);  
			 //将随机到的数放入结果集 
			   result[i] = source[index];  
			 //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换 
			   source[index] = source[len];  
		}
		 return result; 

	}
	
}
