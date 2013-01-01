package com.android.hospital.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.R.array;
import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.activity.GroupDcAdviceActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
/**
 * 
* @ClassName: DcAdviceAdapter 
* @Description: TODO(ҽ��Adatper) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:39:14 
*
 */
public class GroupDcAdviceAdapter extends BaseAdapter{
	private ArrayList<DcAdviceEntity> mList;
	private Context mContext;
	private List<Map<String, String>> mFreqList;//Ƶ��list
	private List<Map<String, String>> mWayList;//;��list
	private HospitalApp app;
	
	public GroupDcAdviceAdapter(Context context,ArrayList<DcAdviceEntity> entities){
		this.mList=entities;
		this.mContext=context;
		this.app=(HospitalApp) mContext.getApplicationContext();
		mFreqList=app.getFreqList();
		mWayList=app.getWayList();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder=null;
		if (convertView==null) {
			convertView=LayoutInflater.from(mContext).inflate(R.layout.activity_group_dcadvice_list_item, null);
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
		
		viewHolder.tev5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DebugUtil.debug("���Ե��--->");
				showEditDialog(position);
			}
		});
		viewHolder.tev7.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSingleChoiceDialog(position, 0);
			}
		});
		viewHolder.tev8.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showSingleChoiceDialog(position, 1);
			}
		});
		return convertView;
	}
	
   private class ViewHolder{
	public TextView tev1,tev2,tev3,tev4,tev5,tev6,tev7,tev8,tev9,tev10,tev11,tev12;
	public View tevDivider;
   }
   
   /**
    * 
   * @Title: showEditDialog 
   * @Description: TODO(�����༭�Ի���) 
   * @param     �趨�ļ� 
   * @return void    �������� 
   * @throws
    */
   private void showEditDialog(final int position){
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
            	   DcAdviceEntity item=(DcAdviceEntity) getItem(position);
            	   item.dosage=editText.getText().toString();
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
        	   DcAdviceEntity item=(DcAdviceEntity) getItem(position);
        	   if (which==0) {
        		   item.administration=array[whichChoose];
			   }else {
				   item.frequency=array[whichChoose];
			   }
        	   mList.set(position, item);
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