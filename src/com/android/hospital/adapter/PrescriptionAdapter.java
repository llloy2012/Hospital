package com.android.hospital.adapter;

import java.util.ArrayList;

import com.android.hospital.entity.PrescriptionEntity;
import com.android.hospital.ui.activity.R;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 
* @ClassName: PrescriptionAdapter 
* @Description: TODO(����Adatper) 
* @author lll 
* @date 2012-12-19  
*
 */
public class PrescriptionAdapter extends BaseAdapter{
	
	private ArrayList<PrescriptionEntity> mList;
	
	private Context mContext;
	
	public PrescriptionAdapter(Context context,ArrayList<PrescriptionEntity> entities){
		this.mContext=context;
		this.mList=entities;
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
	
	/**
	 * 
	* @Title: clearAdapter 
	* @Description: TODO(ÿ�λ�ȡ�����֮ǰ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void clearAdapter(){
		if (mList!=null) {
			mList.clear();
			notifyDataSetChanged();
		}	
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment_prescription_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.prescription_list_item1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.prescription_list_item2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.prescription_list_item3);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		PrescriptionEntity item=mList.get(position);
		viewHolder.tev1.setText("�����ţ�"+item.presc_no+"  �������ڣ�"+item.presc_date
				                +"  ����ҽ����"+item.prescribed_by);
		viewHolder.tev2.setText("��������:"+item.presc_type+"  ����:"+item.repetition+"  �ܻ���:"+item.costs);
		viewHolder.tev3.setText("״̬��"+item.presc_status+"  ��ҩҩ����"+item.dept_name);
		return convertView;
	}
	private class ViewHolder{
		public TextView tev1,tev2,tev3;
	   }

}
