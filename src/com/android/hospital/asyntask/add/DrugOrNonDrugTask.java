package com.android.hospital.asyntask.add;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.DrugAdapter;
import com.android.hospital.adapter.NonDrugAdapter;
import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.ui.fragment.AddDcAdviceFragment;
import com.android.hospital.ui.fragment.SearchFragment;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 
* @ClassName: DrugTask 
* @Description: TODO(��ȡҩƷ�ͷ�ҩƷ����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-19 ����12:01:48 
*
 */
public class DrugOrNonDrugTask extends BaseAsyncTask{

	private Context mContext;
	
	private String drugSql;
	
	private String nondrugSql;
	
	private ArrayList<DrugEntity> drugList;
	
	private ArrayList<NonDrugEntity> nondrugList;
	
	public DrugOrNonDrugTask(Context context,String drugSql,String nondrugSql){
		this.mContext=context;
		this.drugSql=drugSql;
		this.nondrugSql=nondrugSql;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(drugSql);
		drugList=DrugEntity.init(dataList);
		ArrayList<DataEntity> dataList2=WebServiceHelper.getWebServiceData(nondrugSql);
		nondrugList=NonDrugEntity.init(dataList2);
		return drugList;
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
        dataLoaded();
		
	}
	
	/**
	 * 
	* @Title: dataLoaded 
	* @Description: TODO(����ȡ�����ݱ��浽app��) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void dataLoaded(){
		if (drugList.size()==0||nondrugList.size()==0) {
			Toast.makeText(mContext, "��ȡ����ʧ��!", Toast.LENGTH_SHORT).show();
		}else {	
			HospitalApp app=(HospitalApp) mContext.getApplicationContext();
			app.setDrugList(drugList);
			app.setNondrugList(nondrugList);
			setPrescriptionMiddleDrug();
		}
	}
    /**
     * 
    * @Title: setPrescriptionMiddleDrug 
    * @Description: TODO(���ô�����ҩ��ҩƷ����) 
    * @param     �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
	private void setPrescriptionMiddleDrug() {
		// TODO Auto-generated method stub
		ArrayList<DrugEntity> middLeDrugList=new ArrayList<DrugEntity>();
		int size=drugList.size();
		for (int i = 0; i < size; i++) {
			if ("��ҩ��".equals(drugList.get(i).storage_name)) {
				middLeDrugList.add(drugList.get(i));
			}
		}
		HospitalApp app=(HospitalApp) mContext.getApplicationContext();
		app.setMiddleDrugList(middLeDrugList);
	}
}
