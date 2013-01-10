package com.android.hospital.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	
    // ͬʱҪע������ϵͳȡ����ʲôʱ�䡣���������ݿ�ʱ�䣬������ʱ�䣬jdkʱ��,���ǿͻ���ʱ�䡣������ȡ���ǵ�ǰϵͳʱ��
	 
    // ͬʱ�������ǰʱ����2012-2-29 ��ô��today.getMonth()��ֵ��һ��
 
    public static int userBirthdayGetAge(String birth) {
 
       try {
    	   String birthday=toDateOfBirth(birth);
    	   DebugUtil.debug("��ǰ������--->"+birthday);
    	   
    	   Calendar cal = Calendar.getInstance();
    	   
           SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");

           String mDateTime = formatter.format(cal.getTime());// ��ǰʱ��
     
           java.util.Date today = formatter.parse(mDateTime);
           
           DebugUtil.debug("��ǰ������--->"+today);
     
           java.text.SimpleDateFormat sdf =new java.text.SimpleDateFormat(
     
                  "yyyy-MM-dd");
     
           java.util.Date birday = sdf.parse(birthday);// ��ǰ�Ե�ǰ�����
     
           int age = today.getYear() - birday.getYear();
     
           if (today.getMonth() == birday.getMonth()
     
                  &&today.getDate() == birday.getDate()
     
                  &&birday.getYear() % 4 != 0 && today.getYear() != 0
     
                  &&birday.getMonth() != 1 && today.getMonth() != 1) {// �·ݺ����ڶ��뵱ǰʱ����ͬ(��ȥ���պ͵�ǰ�������꣬�����Ƕ��µ����)
     
              return age;
     
           } else if (today.getMonth() < birday.getMonth()) {// ���յ��·ݴ��ڵ�ǰʱ����·�
     
              return age - 1;
     
           } else if (birday.getMonth() == 1 && birday.getDate() == 29// ���������꣬��ǰ�겻һ��������
     
                  &&today.getMonth() == 1) {// ��������������,���ұ����Ƕ���
     
              if (today.getYear() % 4 == 0) {// ��ǰ�������� 2���ж�ʮ��
     
                 if (today.getDate() < birday.getDate()) {//
     
                     return age - 1;
     
                  }else {
     
                     return age;
     
                  }
     
               }else {// ��ǰ��������Ƕ�ʮ����
     
                 if (today.getDate() < birday.getDate() - 1) {
     
                     return age - 1;
     
                  }else {
     
                     return age;
     
                  }
     
               }
     
           } else if (today.getMonth() == 1 && today.getDate() == 29
     
                  &&birday.getMonth() == 1) { // ��ǰ�������꣬�����겻һ��������
     
              if (birday.getYear() % 4 == 0) {// ������������ �����ж�ʮ����
     
                 if (today.getDate() < birday.getDate()) {//
     
                     return age - 1;
     
                  }else {
     
                     return age;
     
                  }
     
               }else {// ����������Ƕ�ʮ����
     
                 if (today.getDate() + 1 < birday.getDate()) {
     
                     return age - 1;
     
                  }else {
     
                     return age;
     
                  }
     
               }
     
     
     
           } else if (today.getMonth() > birday.getMonth()) {// ����ͬһ�꣬���յ��·ݲ����ڵ�ǰ���·ݵ����
     
              return age;
     
     
     
           } else if (today.getDate() < birday.getDate()) {// ����������(������) ��ǰ С�� ����
     
              return age - 1;
     
           } else if (today.getDate() == birday.getDate()) {// ��ǰ ���� ����
     
              return age;
     
           } else {
     
              return age; // ��ǰ ���� ����
     
           }
	   } catch (Exception e) {
		// TODO: handle exception
	   }
       
       return 0;
 
    }
}
