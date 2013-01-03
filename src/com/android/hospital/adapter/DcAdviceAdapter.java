package com.android.hospital.adapter;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.activity.R;
/**
 * 
* @ClassName: DcAdviceAdapter 
* @Description: TODO(ҽ��Adatper) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:39:14 
*
 */
public class DcAdviceAdapter extends BaseAdapter{
	private ArrayList<DcAdviceEntity> mList;
	private Context mContext;
	
	public DcAdviceAdapter(Context context,ArrayList<DcAdviceEntity> entities){
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
	/**
	 * 
	* @Title: getList 
	* @Description: TODO(�õ����е�ҽ������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public ArrayList<DcAdviceEntity> getList(){
		return mList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.fragment_dcadvice_list_item, null);
			viewHolder=new ViewHolder();
			viewHolder.tev1=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_1);
			viewHolder.tev2=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_2);
			viewHolder.tev3=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_3);
			viewHolder.tev4=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_4);
			viewHolder.tev5=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_5);
			viewHolder.tev6=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_6);
			viewHolder.tev7=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_7);
			viewHolder.tev8=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_8);
			viewHolder.tev9=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_9);
			viewHolder.tev10=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_10);
			viewHolder.tev11=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_11);
			viewHolder.tev12=(TextView) convertView.findViewById(R.id.dcadvice_item_tev_12);
			viewHolder.tevDivider=(View)convertView.findViewById(R.id.listview_divider);
			convertView.setTag(viewHolder);
		}else {
			viewHolder=(ViewHolder) convertView.getTag();
		}
		DcAdviceEntity item=mList.get(position);
		viewHolder.tev1.setText(item.order_no);
		String repeat_indicator = "����";
		if (item.repeat_indicator.equals("0")) {
			repeat_indicator="��ʱ";
		}
		viewHolder.tev2.setText(repeat_indicator);
		viewHolder.tev3.setText(item.start_date_time);
		viewHolder.tev4.setText(item.order_text);
		viewHolder.tev5.setText(item.dosage);
		viewHolder.tev6.setText(item.dosage_units);
		viewHolder.tev7.setText(item.administration);
		viewHolder.tev8.setText(item.frequency);
		viewHolder.tev9.setText(item.perform_schedule);
		viewHolder.tev10.setText(item.stop_date_time);
		viewHolder.tev11.setText(item.freq_detail);
		viewHolder.tev12.setText(item.doctor);
		if(position>1){
			DcAdviceEntity last_item=mList.get(position-1);//�����һ��ҽ����Ϣ
			if(null!=last_item){
				if(last_item.order_no.equals(item.order_no)){//��������Ϊ��һ������ҽ��ʱ
					//��ҽ��������Ϣ����ʾ���ÿ�
					viewHolder.tev1.setText(""); //order_no
					viewHolder.tev2.setText("");
					viewHolder.tev3.setText("");
					viewHolder.tev7.setText("");
					viewHolder.tev8.setText("");
					viewHolder.tev9.setText("");
					viewHolder.tev10.setText("");
					viewHolder.tev12.setText("");
				}
			}
		}
		//����ִ��״̬�����ò�ͬ��ɫ
		if("3".equals(item.order_status)){ //ֹͣҽ��
			setTextColor(viewHolder, mContext.getResources().getColor(R.color.lightpink));
			
		}else{	
			if("8".equals(item.order_status)||"4".equals(item.order_status)){ //����ҽ��
				setTextColor(viewHolder, Color.RED);
				
			}else{
				setTextColor(viewHolder, mContext.getResources().getColor(R.color.cornflowerblue));
			}
		}
		return convertView;
	}
	
//���ݲ�ͬ��״̬���ı�ҽ������ʾ��ɫ
   public void setTextColor(ViewHolder viewHolder,int color){
	   viewHolder.tev1.setTextColor(color);
   	   viewHolder.tev2.setTextColor(color);
   	   viewHolder.tev3.setTextColor(color);
   	   viewHolder.tev4.setTextColor(color);
   	   viewHolder.tev5.setTextColor(color);
   	   viewHolder.tev6.setTextColor(color);
   	   viewHolder.tev7.setTextColor(color);
   	   viewHolder.tev8.setTextColor(color);
   	   viewHolder.tev9.setTextColor(color);
   	   viewHolder.tev10.setTextColor(color);
   	   viewHolder.tev11.setTextColor(color);
   	   viewHolder.tev12.setTextColor(color);
	
   }
	
   private class ViewHolder{
	public TextView tev1,tev2,tev3,tev4,tev5,tev6,tev7,tev8,tev9,tev10,tev11,tev12;
	public View tevDivider;
   }
}
