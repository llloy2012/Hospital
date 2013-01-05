package com.android.hospital.adapter;

import java.util.List;
import java.util.Map;

import com.android.hospital.entity.DrugEntity;
import com.android.hospital.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CheckItemAdapter extends BaseAdapter{

	private Context mContext;
	
	private List<Map<String, String>> mList;
	
	private LayoutInflater mInflater;
	
	public CheckItemAdapter(Context context,List<Map<String, String>> list){
		this.mContext=context;
		this.mList=list;
		this.mInflater=LayoutInflater.from(context);
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
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_add_checkitem_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.search_item_tev_1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.search_item_tev_2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.search_item_tev_3);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		Map<String, String> map=mList.get(position);
		int id=position+1;
		viewHolder.tev1.setText(String.valueOf(id));
		viewHolder.tev2.setText(map.get("description"));
		viewHolder.tev3.setText(map.get("input_code"));
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tev1,tev2,tev3;
	}
}
