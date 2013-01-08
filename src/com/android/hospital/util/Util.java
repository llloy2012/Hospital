package com.android.hospital.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @ClassName: Util 
* @Description: TODO(������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-22 ����12:54:09 
*
 */
public class Util {

	/**
	 * 
	* @Title: toSimpleDate 
	* @Description: TODO(��ʽ��ʱ��) 
	* @param @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public static String toSimpleDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
		String str=format.format(date);
		return str;
	}
	
	/**
	 * 
	* @Title: toOtherDate 
	* @Description: TODO(��ʽ�������) 
	* @param @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public static String toTest_No(String dateStr){
		switch (dateStr.length()) {
		case 1:
			dateStr="000"+dateStr;
			break;
		case 2:
			dateStr="00"+dateStr;
			break;
		case 3:
			dateStr="0"+dateStr;
			break;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		Date date=new Date(System.currentTimeMillis());//��ȡ��ǰʱ��
		String str=format.format(date);
		return str+dateStr;
	}
	
	
	/**
	 * 
	* @Title: toDateOfBirth 
	* @Description: TODO(��ʽ�����˳�������) 
	* @param @param dateStr
	* @param @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public static String toDateOfBirth(String dateStr){
		String[] date_of_birthArr=dateStr.split("/");
		String[] date_of_birthArr2=date_of_birthArr[2].split(" ");
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(date_of_birthArr2[0]).append("-").append(date_of_birthArr[0]).append("-").append(date_of_birthArr[1]);
		return sBuffer.toString();
	}
	
	public static String toQueryTime(int c){
		if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
	}
}
