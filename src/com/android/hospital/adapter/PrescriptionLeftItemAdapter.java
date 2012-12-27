package com.android.hospital.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PrescriptionLeftItemAdapter extends BaseAdapter{

	private Context mContext;
	private ArrayList<DrugEntity> mList;
	private LayoutInflater mInflater;
	private HospitalApp app;
	private List<Map<String, String>> mFreqList;//频次list
	private List<Map<String, String>> mWayList;//途径list
	private ArrayAdapter<String> freqAdapter;
	private ArrayAdapter<String> wayAdapter;
	
	public PrescriptionLeftItemAdapter(Context context,ArrayList<DrugEntity> list){
		this.mContext=context;
		this.mList=list;
		this.mInflater=LayoutInflater.from(context);
		this.app=(HospitalApp) mContext.getApplicationContext();
		mFreqList=app.getFreqList();
		mWayList=app.getWayList();
		setSpinner();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList==null?0:mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public void clear(){
		mList.clear();
		notifyDataSetChanged();
	}
	
	public void addItem(DrugEntity entity,boolean defaultFlag){
		if (mList==null) {
			mList=new ArrayList<DrugEntity>();			
		}
		int size=mList.size();
		for (int i = 0; i < size; i++) {
			if (entity.drug_name.equals(mList.get(i).drug_name)) {
				Toast.makeText(mContext, "该项已被添加!", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		mList.add(entity);
		notifyDataSetChanged();
	}
	
	public void setSpinner(){
		List<String> freqSpList=new ArrayList<String>();
		for (int i = 0; i < mFreqList.size(); i++) {
			String item=mFreqList.get(i).get("freq_desc");
			freqSpList.add(item);
		}
		ArrayAdapter<String> freqAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, freqSpList);
		freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		List<String> waySpList=new ArrayList<String>();
		for (int i = 0; i < mWayList.size(); i++) {
			String item=mWayList.get(i).get("administration_name");
			waySpList.add(item);
		}
		ArrayAdapter<String> wayAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, waySpList);
		wayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_add_prescription_left_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_3);
			viewHolder.edit4=(EditText) convertView.findViewById(R.id.add_prescription_left_item_edit_4);
			viewHolder.tev5=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_5);
			viewHolder.spinner6=(Spinner) convertView.findViewById(R.id.add_prescription_left_item_spinner_6);
			viewHolder.spinner7=(Spinner) convertView.findViewById(R.id.add_prescription_left_item_spinner_7);
			viewHolder.edit8=(EditText) convertView.findViewById(R.id.add_prescription_left_item_edit_8);
			viewHolder.tev9=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_9);
			viewHolder.tev10=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_10);
			viewHolder.tev11=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_11);			
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		DrugEntity item=mList.get(position);
		int id=position+1;
		viewHolder.tev1.setText(String.valueOf(id));
		viewHolder.tev2.setText(item.drug_name);//药名
		viewHolder.tev3.setText(item.drug_spec);//规格
		viewHolder.tev5.setText(item.dose_units);//单位
		viewHolder.tev9.setText(item.package_units);
		viewHolder.tev10.setText(item.purchase_price);
		viewHolder.tev11.setText(item.purchase_price);
		viewHolder.spinner6.setAdapter(wayAdapter);
		viewHolder.spinner7.setAdapter(freqAdapter);
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tev1,tev2,tev3,tev5,tev9,tev10,tev11;
		public EditText edit4,edit8;
		public Spinner spinner6,spinner7;
	}
}
