package com.android.hospital.adapter;

import java.util.ArrayList;

import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.InspectionEntity;
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
* @ClassName: InspectionAdapter 
* @Description: TODO(����Adatper) 
* @author lll 
* @date 2012-12-18  
*
 */
public class InspectionAdapter extends BaseAdapter{
	
	private ArrayList<InspectionEntity> mList;
	
	private Context mContext;
	
	public InspectionAdapter(Context context,ArrayList<InspectionEntity> entities){
		this.mList=entities;
		this.mContext=context;
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
		mList.clear();
	    notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment_inspection_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.inspection_list_item1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.inspection_list_item2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.inspection_list_item3);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		InspectionEntity item=mList.get(position);
		Log.e("", item.test_no);
		viewHolder.tev1.setText("�ٴ���ϣ�"+item.relevant_clinic_diag+"  ������ң�"+item.dept_name
				+"  �걾��"+item.specimen+"  �걾˵����"+item.notes_for_spcm);
		viewHolder.tev2.setText("��Ŀ����:"+item.item_name);
		viewHolder.tev3.setText("�ͼ�ҽʦ��"+item.prdering_provider+"  �������ڣ�"+item.requested_date_time);
		return convertView;
	}
	private class ViewHolder{
		public TextView tev1,tev2,tev3;
	   }

}
