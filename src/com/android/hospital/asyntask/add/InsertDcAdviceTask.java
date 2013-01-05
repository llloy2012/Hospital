package com.android.hospital.asyntask.add;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.ui.activity.LoginActivity;
import com.android.hospital.webservice.WebServiceHelper;
import com.android.hospital.widgets.MyProssDialog;

public class InsertDcAdviceTask extends BaseAsyncTask{

	private Activity mActivity;
	
	private String sql;
	
	private MyProssDialog mDialog;
	
	
	public InsertDcAdviceTask(Activity activity,String sql){
		this.mActivity=activity;
		this.sql=sql;
	}
	
	
	
	@Override
	protected void onPreExecute() {
        mDialog=new MyProssDialog(mActivity, "�ύ", "�����ύ�������Ժ�...");
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return WebServiceHelper.insertWebServiceData(sql);
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		mDialog.cancel();
		boolean flag=(Boolean) result;
		if (flag) {
			Intent intent=new Intent();
			mActivity.setResult(11, intent);
			mActivity.finish();
		}else {
			Toast.makeText(mActivity, "�ύʧ��,��������!", Toast.LENGTH_SHORT).show();
		}
	}
}
