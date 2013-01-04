package com.android.hospital.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class PrescriptionLeftItemAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<DrugEntity> mList;
	private LayoutInflater mInflater;
	private HospitalApp app;
	private List<Map<String, String>> mFreqList;//Ƶ��list
	private List<Map<String, String>> mWayList;//;��list
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
	
	public void addItem(DrugEntity entity,int defaultFlag){
		if (mList==null) {
			mList=new ArrayList<DrugEntity>();			
		}
		int size=mList.size();
		for (int i = 0; i < size; i++) {
			if (entity.drug_name.equals(mList.get(i).drug_name)) {
				Toast.makeText(mContext, "�����ѱ����!", Toast.LENGTH_SHORT).show();
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
		freqAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, freqSpList);
		freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		List<String> waySpList=new ArrayList<String>();
		for (int i = 0; i < mWayList.size(); i++) {
			String item=mWayList.get(i).get("administration_name");
			waySpList.add(item);
		}
		wayAdapter=new ArrayAdapter(mContext, android.R.layout.simple_spinner_item, waySpList);
		wayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	}
		
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub	
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_add_prescription_left_item, null);	
		}	
		convertView.setTag(position);
		TextView tev1=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_1);
		TextView tev2=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_2);
		TextView tev3=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_3);
		TextView edit4=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_4);
		TextView tev5=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_5);
		TextView spinner6=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_6);
		TextView spinner7=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_7);
		TextView edit8=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_8);
		TextView tev9=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_9);
		TextView tev10=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_10);
		TextView tev11=(TextView) convertView.findViewById(R.id.add_prescription_left_item_tev_11);		
		
		DrugEntity item=mList.get(position);
		int id=position+1;
		tev1.setText(String.valueOf(id));
		tev2.setText(item.drug_name);//ҩ��
		tev3.setText(item.drug_spec);//���
		edit4.setText(item.dosage_each);//����
		edit8.setText(item.quantity);//����
		tev5.setText(item.dose_units);//��λ
		spinner6.setText(item.administration);
		spinner7.setText(item.frequency);
		tev9.setText(item.package_units);
		tev10.setText(item.purchase_price);
		tev11.setText(item.purchase_price);
		
		edit4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEditDialog(position, 0);
			}
		});
		edit8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showEditDialog(position, 1);
			}
		});
		spinner6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSingleChoiceDialog(position, 0);
			}
		});
		spinner7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSingleChoiceDialog(position, 1);
			}
		});
		
		return convertView;
	}

	/**
	    * 
	   * @Title: showEditDialog 
	   * @Description: TODO(�����༭�Ի���) 
	   * @param     �趨�ļ� 
	   * @return void    �������� 
	   * @throws
	    */
	   public void showEditDialog(final int position,final int which){
	       LayoutInflater factory = LayoutInflater.from(mContext);
	       final View textEntryView = factory.inflate(R.layout.activity_group_editdialog, null);
	       final EditText editText=(EditText) textEntryView.findViewById(R.id.activity_group_editdialog_edit);
	       new AlertDialog.Builder(mContext)
	           .setIconAttribute(android.R.attr.alertDialogIcon)
	           .setTitle("��༭")
	           .setView(textEntryView)
	           .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int whichButton) {

	                   /* User clicked OK so do some stuff */
	            	   DrugEntity item=(DrugEntity) getItem(position);
	            	   if (which==0) {
	            		   item.dosage_each=editText.getText().toString();//����
					   }else {
						   item.quantity=editText.getText().toString();//����
					   }	            	   
	            	   mList.set(position, item);
	            	   notifyDataSetChanged();
	               }
	           })
	           .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int whichButton) {

	                   /* User clicked cancel so do some stuff */
	               }
	           }).create().show();
	   }
	   
	   private int whichChoose=0;//showSingleChoiceDialogѡ����id
	   /**
	    * 
	   * @Title: showSingleChoiceDialog 
	   * @Description: TODO(;����Ƶ�εĶԻ���) 
	   * @param     �趨�ļ� 
	   * @return void    �������� 
	   * @throws
	    */
	   private void showSingleChoiceDialog(final int position,final int which){
		   final String[] array=getArrayItem(which); 
		   new AlertDialog.Builder(mContext)
	       .setIconAttribute(android.R.attr.alertDialogIcon)
	       .setTitle("��ѡ��")
	       .setSingleChoiceItems(array, 0, new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int whichButton) {

	               /* User clicked on a radio button do some stuff */
	        	   whichChoose=whichButton;
	           }
	       })
	       .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int whichButton) {

	               /* User clicked Yes so do some stuff */
	        	   final DrugEntity item=(DrugEntity) getItem(position);
	        	   if (which==0) {
	        		   item.administration=array[whichChoose];
				   }else {
					   item.frequency=array[whichChoose];
				   }
	        	   mList.set(position, item);
	        	   whichChoose=0;
	        	   notifyDataSetChanged();
	           }
	       })
	       .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int whichButton) {

	               /* User clicked No so do some stuff */
	           }
	       })
	      .create().show();
	   }

	   /**
	    * 
	   * @Title: getArrayItem 
	   * @Description: TODO(��ȡ��Ҫ��ʾ������) 0Ϊ;��������ΪƵ��
	   * @param @return    �趨�ļ� 
	   * @return String[]    �������� 
	   * @throws
	    */
	  private String[] getArrayItem(int which) {
		// TODO Auto-generated method stub
		  String[] arr;
		  if (which==0) {
			  arr=new String[mWayList.size()];
		 	for (int i = 0; i < mWayList.size(); i++) {
		 		arr[i]=mWayList.get(i).get("administration_name");
			}
		  }else {
			  arr=new String[mFreqList.size()];
			  for (int i = 0; i < mFreqList.size(); i++) {
				arr[i]=mFreqList.get(i).get("freq_desc");
			  }
		  }
		  return arr;
	  }
}
