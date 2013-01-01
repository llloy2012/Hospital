package com.android.hospital.webservice;

import java.io.IOException;
import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.android.hospital.entity.DataEntity;
import com.android.hospital.util.DebugUtil;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
/**
 * 
* @ClassName: WebServiceHelper 
* @Description: TODO(数据库交互类) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:42:12 
*
 */
public class WebServiceHelper {

	private static final String METHOD_NAME = "getObjByDefaultDBSql";
	private static final String NAMESPACE = "http://service.com";
	private static final String SERVERURL="http://192.168.0.40:8888/WebServiceServer/services/GeneralOpSQL";
	private static WakeLock wl;
	/**
     * 检测网络是否可用
     * @param context
     * @return
     */
    public static boolean checkNetWork( Context context )
    {
    	boolean newWorkOK = false;  
        ConnectivityManager connectManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	if( connectManager.getActiveNetworkInfo() != null )
    	{
    		newWorkOK = true;
    	}
        return newWorkOK;
    }
    
   
    /**
     * 
    * @Title: getUserName 
    * @Description: TODO(登录校验) 
    * @param @param userName
    * @param @param password
     */
    public static String getUserName(String userName,String password){
    	String result = null;
		SoapObject request = new SoapObject(NAMESPACE, "isUserRight");
		request.addProperty("in0", userName);
		request.addProperty("in1", password);
		DebugUtil.debug("username-->"+userName);
		DebugUtil.debug("pwd-->"+password);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(SERVERURL);
		ht.debug = true;
		try {
			ht.call("http://service.com/isUserRight", envelope);
			DebugUtil.debug("result--->"+envelope.getResponse().toString());
			if (envelope.getResponse() != null) {
				result = envelope.getResponse().toString();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		
		
		return result;
    }
    /**
     * 
    * @Title: getWebServiceData 
    * @Description: TODO(获取数据库数据) 
    * @param @param sql                查询语句
    * @return ArrayList<DataEntity>    封装的Map集合 
    * @throws
     */
    public static ArrayList<DataEntity> getWebServiceData(String sql){
    	ArrayList<DataEntity> dataList=new ArrayList<DataEntity>();
    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("in0", sql);
		DebugUtil.debug("测试"+sql);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(SERVERURL);
		DebugUtil.debug("测试url"+SERVERURL);
		ht.debug = true;
		try {
			ht.call("http://service.com/getObjByDefaultDBSql", envelope);
			if (envelope.getResponse() != null) {
				DebugUtil.debug("get-result->"+envelope.getResponse().toString());
				SoapObject object = (SoapObject) envelope.getResponse();
				int count = object.getPropertyCount();
				String[] sTitle = {};
				sTitle = object.getProperty(0).toString().split("<;>");
				for (int i = 0; i < sTitle.length; i++) {
					DebugUtil.debug("sTitle--->"+sTitle[i]);
				}
				DataEntity bean = null;
				if (sTitle.length > 0) {
					for (int i = 1; i < count; i++) {
						String[] forumObject = object.getProperty(i).toString().split("<;>");
						bean = new DataEntity(sTitle.length);
						for (int j = 0; j<sTitle.length; j++){
							bean.add(j, sTitle[j], forumObject[j]);
						}
						dataList.add(bean);
					}
				}
				dataList.trimToSize();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
    	return dataList;
    } 
    /**
     * 
    * @Title: insertWebServiceData 
    * @Description: TODO(插入数据) 
    * @param @param sql  插入语句
    * @return boolean    true为插入成功
     */
    public static boolean insertWebServiceData(String sql){
    	SoapObject request = new SoapObject(NAMESPACE, "executeByDefaultDBSql");
    	request.addProperty("in0", sql);
    	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
    	envelope.setOutputSoapObject(request);
    	DebugUtil.debug("插入的sql语句--->"+sql);
    	HttpTransportSE ht = new HttpTransportSE(SERVERURL);
    	ht.debug = true;
    	try {
			ht.call("http://service.com/executeByDefaultDBSql", envelope);
			if (envelope.getResponse()!=null) {
				//SoapObject object = (SoapObject) envelope.getResponse();
				String result=envelope.getResponse().toString();
				DebugUtil.debug("插入的返回结果->"+result);
				if (result.equals("1")) {
					return true;
				}
			}else {
				return false;
			}
		} catch (Exception e) {
		}
    	return false;
    }
    /**
    * @Title: updateWebServiceData 
    * @Description: TODO(更新数据) 
    * @param @param sql  更新语句
    * @return boolean    true为更新成功
     */

    public static boolean updateWebServiceData(String sql){
       	SoapObject request = new SoapObject(NAMESPACE, "executeByDefaultDBSql");
       	request.addProperty("in0", sql);
       	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
   				SoapEnvelope.VER11);
       	envelope.setOutputSoapObject(request);
       	DebugUtil.debug("更新的sql语句--->"+sql);
       	HttpTransportSE ht = new HttpTransportSE(SERVERURL);
       	ht.debug = true;
       	try {
   			ht.call("http://service.com/executeByDefaultDBSql", envelope);
   			if (envelope.getResponse()!=null) {
   				//SoapObject object = (SoapObject) envelope.getResponse();
   				String result=envelope.getResponse().toString();
   				DebugUtil.debug("更新的返回结果->"+result);
   				if (result.equals("1")) {
   					return true;
   				}
   			}else {
   				return false;
   			}
   		} catch (Exception e) {
   		}
       	return false;
       }
    
    /** 
     * 保持屏幕唤醒状态（即背景灯不熄灭） 
     * @param on 是否唤醒 
     */  
    public static void keepScreenOn(Context context, boolean on) {  
        if (on) {  
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);  
            wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE, "==KeepScreenOn==");  
            wl.acquire();  
        }else {  
            wl.release();  
            wl = null;  
        }  
    }
    
    /**
	 * 判断给定字符串是否空白串。<br>
	 * 空白串是指由空格、制表符、回车符、换行符组成的字符串<br>
	 * 若输入字符串为null或空字符串，返回true
	 * @param input
	 * @return boolean
	 */
	public static boolean isBlank( String input ) 
	{
		if ( input == null || "".equals( input ) )
			return true;
		
		for ( int i = 0; i < input.length(); i++ ) 
		{
			char c = input.charAt( i );
			if ( c != ' ' && c != '\t' && c != '\r' && c != '\n' )
			{
				return false;
			}
		}
		return true;
	}	
}
