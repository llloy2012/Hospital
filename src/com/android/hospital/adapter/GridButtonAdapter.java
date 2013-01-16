package com.android.hospital.adapter;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.SignsLifeEntity;
import com.android.hospital.temperature.AverageTemperatureChart;
import com.android.hospital.temperature.IDemoChart;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;
import com.android.hospital.widgets.MyProssDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
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
	
	private ListView detailListView;
	
	private View detaillayout;
	
	
	private String[] textArr = { "����ͼ", "Ҹ������", "Ѫѹ", "����", "����", "������", "����",
			"Ѫ��", "����", "����", "����", "̵��", "��Ժ", "ת��", "����", "������",
			"����", "����", "����", "���", "����Һ��","����","Ż����",
			"��Ժ","����","������"};
	
	
	public GridButtonAdapter(Context context){
		this.mContext=context;
		this.mInflater=LayoutInflater.from(mContext);
		
		
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
		final String item=textArr[position];
		button.setText(item);	
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (item.equals("����ͼ")) {
					new TemperatureTask().execute(item);
				}else {
					new SignDetailTask(item).execute();
				}
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
	private void showDetailInfo(String item){
		detaillayout=mInflater.inflate(R.layout.fragment_signslife_listview, null);
		detailListView=(ListView) detaillayout.findViewById(R.id.signslife_list_view);		
		AlertDialog.Builder builder=new AlertDialog.Builder(mContext);	
		builder.setView(detaillayout);
		builder.setIconAttribute(android.R.attr.alertDialogIcon);
		builder.setTitle(item+"��ϸ��Ϣ")
           .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int whichButton) {

                   /* User clicked cancel so do some stuff */
               }
           }).create().show();
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

		private String itemName;
		
		private ArrayList<SignsLifeEntity> signsList;
		
		public SignDetailTask(String item){
			this.itemName=item;
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			showDetailInfo(itemName);
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			HospitalApp app=(HospitalApp) mContext.getApplicationContext();
			String sql = "select recording_date,time_point,vital_signs,vital_signs_cvalues,units,nurse from vital_signs_rec where patient_id='"
					+ app.getPatientEntity().patient_id
					+ "' and visit_id='"
					+ app.getPatientEntity().visit_id
					+ "' and vital_signs='"
					+ itemName + "'";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			signsList=SignsLifeEntity.init(dataList);
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (detailListView!=null&&signsList.size()!=0) {
				SignsLifeDetailAdapter adapter=new SignsLifeDetailAdapter(mContext, signsList);
				detailListView.setAdapter(adapter);
			}else {
				Toast.makeText(mContext, "û������", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	/**
	 * 
	* @ClassName: TemperatureTask 
	* @Description: TODO(��ȡ����ͼ�������Ϣ) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-15 ����11:06:31 
	*
	 */
	private class TemperatureTask extends AsyncTask<String, Void, String>{

		private HospitalApp app;
		
		private String[] temperatureTimeArr=null;//����ʱ��
		
		private String[] temperatureArr=null;//�¶�
		
		private MyProssDialog mDialog;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
	        mDialog=new MyProssDialog(mContext, "�ύ", "�����ύ�������Ժ�...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			app=(HospitalApp)mContext.getApplicationContext();
			AppConstant.patientEntity=app.getPatientEntity();
			String sql="select TO_CHAR(time_point,'yyyy-MM-dd hh24:mi') as time_point, vital_signs_cvalues, units" +
					" from vital_signs_rec" +
					" where patient_id = '"+app.getPatientEntity().patient_id+"'" +
					" and visit_id = '"+app.getPatientEntity().visit_id+"'" +
					" and vital_signs = 'Ҹ������'";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			temperatureArr=new String[dataList.size()];
			temperatureTimeArr=new String[dataList.size()];
			for (int i = 0; i < dataList.size(); i++) {
				temperatureArr[i]=dataList.get(i).get("vital_signs_cvalues");
				temperatureTimeArr[i]=dataList.get(i).get("time_point");
				DebugUtil.debug("�¶�--->"+temperatureArr[i]);
				DebugUtil.debug("ʱ��--->"+temperatureTimeArr[i]);
			}
			
			AppConstant.temperatureArr=temperatureArr;
			AppConstant.temperatureTimeArr=temperatureTimeArr;
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mDialog.cancel();
			IDemoChart mChart=new AverageTemperatureChart();
			Intent intent=mChart.execute(mContext);
			mContext.startActivity(intent);
		}
	}
}
