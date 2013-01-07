package com.android.hospital.adapter;

import java.util.ArrayList;

import com.android.hospital.entity.CheckEntity;
import com.android.hospital.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * 
* @ClassName: CheckAdapter 
* @Description: TODO(���adapter) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����7:51:19 
*
 */
public class CheckAdapter extends BaseAdapter{
	
	private Context mContext;
	
	private ArrayList<CheckEntity> mList;

	public CheckAdapter(Context context,ArrayList<CheckEntity> arrayList){
		this.mContext=context;
		this.mList=arrayList;
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
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment_check_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.check_item_tev1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.check_item_tev2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.check_item_tev3);
			viewHolder.tev4=(TextView) convertView.findViewById(R.id.check_item_tev4);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		CheckEntity item=mList.get(position);
		viewHolder.tev1.setText("������"+item.exam_class+"  ������ࣺ"+item.exam_sub_class+"  �����Ŀ��"+item.exam_item);
		viewHolder.tev2.setText("����ʱ�䣺"+item.req_date_time+"  ����ҽ����"+item.req_physician+"  ����ʱ�䣺"+item.report_date_time+"  �����ߣ�"+item.reporter);
		viewHolder.tev3.setText("���������"+item.description);
		viewHolder.tev4.setText("ӡ��"+item.impression);
		
		return convertView;
	}

	
	private class ViewHolder{
		public TextView tev1,tev2,tev3,tev4;
	  
	}
}
