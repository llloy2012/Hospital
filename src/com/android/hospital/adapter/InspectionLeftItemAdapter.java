package com.android.hospital.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.ui.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class InspectionLeftItemAdapter extends BaseAdapter{

	private Context mContext;
	
	private ArrayList<InspectionItemEntity> mList;
	
	private LayoutInflater mInflater;
	
	public InspectionLeftItemAdapter(Context context,ArrayList<InspectionItemEntity> list){
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
		if (mList!=null) {
			mList.clear();
			notifyDataSetChanged();
		}		
	}
	
	public void addItem(InspectionItemEntity entity){
		if (mList==null) {
			mList=new ArrayList<InspectionItemEntity>();			
		}
		int size=mList.size();
		for (int i = 0; i < size; i++) {
			if (entity.item_name.equals(mList.get(i).item_name)) {
				Toast.makeText(mContext, "该项已被添加!", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		mList.add(entity);
		notifyDataSetChanged();
	}
		
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fragment_add_check_left_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.search_item_tev_1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.search_item_tev_2);
			viewHolder.but1=(Button) convertView.findViewById(R.id.search_item_but_delete);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		final InspectionItemEntity item=mList.get(position);
		int id=position+1;
		viewHolder.tev1.setText(String.valueOf(id));
		viewHolder.tev2.setText(item.item_name);
		viewHolder.but1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mList.remove(item);
				notifyDataSetChanged();
			}
		});
		return convertView;
	}
	
	private class ViewHolder{
		public TextView tev1,tev2;
		public Button but1;
	}
}
