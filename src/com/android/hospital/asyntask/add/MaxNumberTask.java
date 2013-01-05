package com.android.hospital.asyntask.add;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;
/**
 * 
* @ClassName: MaxNumberTask 
* @Description: TODO(������,������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-24 ����5:44:11 
*
 */
public class MaxNumberTask extends AsyncTask<String, Void, String>{

	private Context mContext;
	
	private HospitalApp app;
	
	private String nextval="";//������
	
	public MaxNumberTask(Context context){
		this.mContext=context;
		this.app=(HospitalApp) mContext.getApplicationContext();
		if (app==null) {
			DebugUtil.debug("����appΪ��");
		}
	}
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		String sql = "select max (order_no) from orders where patient_id ='"
				+ app.getPatientEntity().patient_id + "' and visit_Id ='"
				+ app.getPatientEntity().visit_id + "'";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		String max="0";
		for (int i = 0; i < dataList.size(); i++) {
			String result=dataList.get(i).get("max(order_no)").trim();
			if (!result.equals("")) {
				max=result;
			}
		}
		if (!params[0].equals("")) {
			String nextvalStr=(String) params[0];
			nextvalTask(nextvalStr);//��ȡ������
		}
		return max;
	}
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		try {
			String temp=(String) result;
			int order_no=Integer.parseInt(temp);
			int max_no=order_no+1;
			String maxNumber=String.valueOf(max_no);
			app.setMaxNumber(maxNumber);
			app.setNextval(nextval);
		} catch (Exception e) {
			Toast.makeText(mContext, "��ȡ���ֵʧ��!", Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	* @Title: nextvalTask 
	* @Description: TODO(��ȡ��������) 
	* @param @param param    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void nextvalTask(String param){
		String sql="select "+param+" from dual";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		for (int i = 0; i < dataList.size(); i++) {
			nextval=dataList.get(i).get("nextval");
		}
	}
}
