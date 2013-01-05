package com.android.hospital.adapter;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.SignsLifeEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
/**
 * 
* @ClassName: GridButtonAdapter 
* @Description: TODO(���»�������������һ��button) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-30 ����10:51:25 
*
 */
public class GridButtonAdapter extends BaseAdapter{

	private Context mContext;
	
	private LayoutInflater mInflater;
	
	private String[] textArr = { "����", "Ѫѹ", "����", "����", "������", "����",
			"Ѫ��", "����", "����", "����", "̵��", "��Ժ", "ת��", "����", "������",
			"����", "����", "����", "���", "����Һ��","����","Ҹ������","Ż����",
			"��Ժ","����","������"};
	
	private HospitalApp app;
	
	public GridButtonAdapter(Context context){
		this.mContext=context;
		this.mInflater=LayoutInflater.from(mContext);
		this.app=(HospitalApp) mContext.getApplicationContext();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return textArr.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		convertView=mInflater.inflate(R.layout.gridview_item_button, null);
		final Button button=(Button) convertView.findViewById(R.id.gridview_item_but);
		button.setText(textArr[position]);	
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});				
		return convertView;
	}

	/**
	 * 
	* @Title: showDetailInfo 
	* @Description: TODO(������ϸ��Ϣdialog) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void showDetailInfo(){
		
	}
	
	/**
	 * 
	* @Title: getDetailView 
	* @Description: TODO(�õ�����ʾ��view) 
	* @param @return    �趨�ļ� 
	* @return View    �������� 
	* @throws
	 */
	private View getDetailView(){
		return null;
	}
	
	/**
	 * 
	* @ClassName: SignDetailTask 
	* @Description: TODO(��ȡ������������Ϣ����) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-5 ����8:23:08 
	*
	 */
	private class SignDetailTask extends AsyncTask<String, Void, String>{

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDetailInfo();
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String sql = "select recording_date,time_point,vital_signs,vital_signs_cvalues,units,nurse from vital_signs_rec where patient_id='"
					+ app.getPatientEntity().patient_id
					+ "' and visit_id='"
					+ app.getPatientEntity().visit_id
					+ "' and vital_signs='"
					+ params[0] + "'";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			ArrayList<SignsLifeEntity> signsList=SignsLifeEntity.init(dataList);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}
	}
}
