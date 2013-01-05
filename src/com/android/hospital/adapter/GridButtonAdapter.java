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
* @Description: TODO(体温或者生命体征中一组button) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-30 上午10:51:25 
*
 */
public class GridButtonAdapter extends BaseAdapter{

	private Context mContext;
	
	private LayoutInflater mInflater;
	
	private String[] textArr = { "体温", "血压", "呼吸", "脉搏", "大便次数", "体重",
			"血糖", "心率", "尿量", "降温", "痰量", "入院", "转入", "入量", "引流量",
			"死亡", "分娩", "呼吸", "请假", "摄入液量","总量","腋下体温","呕吐量",
			"出院","手术","口入量"};
	
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
	* @Description: TODO(弹出详细信息dialog) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void showDetailInfo(){
		
	}
	
	/**
	 * 
	* @Title: getDetailView 
	* @Description: TODO(得到需显示的view) 
	* @param @return    设定文件 
	* @return View    返回类型 
	* @throws
	 */
	private View getDetailView(){
		return null;
	}
	
	/**
	 * 
	* @ClassName: SignDetailTask 
	* @Description: TODO(获取生命体征详信息任务) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-5 下午8:23:08 
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
