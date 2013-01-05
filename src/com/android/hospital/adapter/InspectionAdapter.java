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
* @Description: TODO(检验Adatper) 
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
	* @Description: TODO(每次获取，清空之前) 
	* @param     设定文件 
	* @return void    返回类型 
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
		viewHolder.tev1.setText("临床诊断："+item.relevant_clinic_diag+"  检验科室："+item.dept_name
				+"  标本："+item.specimen+"  标本说明："+item.notes_for_spcm);
		viewHolder.tev2.setText("项目名称:"+item.item_name);
		viewHolder.tev3.setText("送检医师："+item.prdering_provider+"  申请日期："+item.requested_date_time);
		return convertView;
	}
	private class ViewHolder{
		public TextView tev1,tev2,tev3;
	   }

}
