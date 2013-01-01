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
* @Description: TODO(���ݿ⽻����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:42:12 
*
 */
public class WebServiceHelper {

	private static final String METHOD_NAME = "getObjByDefaultDBSql";
	private static final String NAMESPACE = "http://service.com";
	private static final String SERVERURL="http://192.168.0.40:8888/WebServiceServer/services/GeneralOpSQL";
	private static WakeLock wl;
	/**
     * ��������Ƿ����
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
    * @Description: TODO(��¼У��) 
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
    * @Description: TODO(��ȡ���ݿ�����) 
    * @param @param sql                ��ѯ���
    * @return ArrayList<DataEntity>    ��װ��Map���� 
    * @throws
     */
    public static ArrayList<DataEntity> getWebServiceData(String sql){
    	ArrayList<DataEntity> dataList=new ArrayList<DataEntity>();
    	SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("in0", sql);
		DebugUtil.debug("����"+sql);
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		HttpTransportSE ht = new HttpTransportSE(SERVERURL);
		DebugUtil.debug("����url"+SERVERURL);
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
    * @Description: TODO(��������) 
    * @param @param sql  �������
    * @return boolean    trueΪ����ɹ�
     */
    public static boolean insertWebServiceData(String sql){
    	SoapObject request = new SoapObject(NAMESPACE, "executeByDefaultDBSql");
    	request.addProperty("in0", sql);
    	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
    	envelope.setOutputSoapObject(request);
    	DebugUtil.debug("�����sql���--->"+sql);
    	HttpTransportSE ht = new HttpTransportSE(SERVERURL);
    	ht.debug = true;
    	try {
			ht.call("http://service.com/executeByDefaultDBSql", envelope);
			if (envelope.getResponse()!=null) {
				//SoapObject object = (SoapObject) envelope.getResponse();
				String result=envelope.getResponse().toString();
				DebugUtil.debug("����ķ��ؽ��->"+result);
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
    * @Description: TODO(��������) 
    * @param @param sql  �������
    * @return boolean    trueΪ���³ɹ�
     */

    public static boolean updateWebServiceData(String sql){
       	SoapObject request = new SoapObject(NAMESPACE, "executeByDefaultDBSql");
       	request.addProperty("in0", sql);
       	SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
   				SoapEnvelope.VER11);
       	envelope.setOutputSoapObject(request);
       	DebugUtil.debug("���µ�sql���--->"+sql);
       	HttpTransportSE ht = new HttpTransportSE(SERVERURL);
       	ht.debug = true;
       	try {
   			ht.call("http://service.com/executeByDefaultDBSql", envelope);
   			if (envelope.getResponse()!=null) {
   				//SoapObject object = (SoapObject) envelope.getResponse();
   				String result=envelope.getResponse().toString();
   				DebugUtil.debug("���µķ��ؽ��->"+result);
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
     * ������Ļ����״̬���������Ʋ�Ϩ�� 
     * @param on �Ƿ��� 
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
	 * �жϸ����ַ����Ƿ�հ״���<br>
	 * �հ״���ָ�ɿո��Ʊ�����س��������з���ɵ��ַ���<br>
	 * �������ַ���Ϊnull����ַ���������true
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
