package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.asyntask.DcAdviceTask;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.GroupDcAdviceActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.util.Util;
import com.android.hospital.webservice.WebServiceHelper;

import android.R.menu;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: DoctorAdviceFragment 
* @Description: TODO(ҽ������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:40:47 
*
 */
public class DoctorAdviceFragment extends ListFragment {

	private HospitalApp app;

	private int position=0;//���ģ��id

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
		//ArrayList<DcAdviceEntity> arrayList=new DcAdviceEntity("").list;
//		DcAdviceAdapter adapter=new DcAdviceAdapter(getActivity(),arrayList);
//		setListAdapter(adapter);
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
//		getListView().setDivider(getResources().getDrawable(R.drawable.main_list_divder));
//		getListView().setDividerHeight(2);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		getListView().setFastScrollEnabled(true);
		getListView().setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
			
			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("��ѡ��");       
				menu.add(0, 0, 0, "ֹͣ����ҽ��");  
				menu.add(0, 1, 0, "���Ƹ���ҽ��");  
				menu.add(0, 2, 0, "ȡ��");  
			}
		});
		getActivity().findViewById(R.id.listview_common_titlebar).setVisibility(View.VISIBLE);
		notChoosePatient();
	}
		
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.addSubMenu(1, 11, 1, "ȫ��");
		menu.addSubMenu(1, 12, 1, "����");
		menu.addSubMenu(1, 13, 1, "��ʱ");
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		ContextMenuInfo menuInfo = (ContextMenuInfo) item.getMenuInfo();
		AdapterContextMenuInfo id=(AdapterContextMenuInfo) menuInfo; 
		int position=id.position;//�ڼ���
		DebugUtil.debug("id-->"+id.position);
		switch (item.getItemId()) {
		case 0:
			 DcAdviceAdapter adapter=(DcAdviceAdapter) getListAdapter();
			 DcAdviceEntity entity=(DcAdviceEntity) adapter.getItem(position);
			 String req_date_time=Util.toSimpleDate();//���ϵͳʱ��
			 //��ʱҽ�����߻�û�д����ҽ��������ͣ
			 if("0".equals(entity.repeat_indicator)||"6".equals(entity.order_status)){
				 Toast.makeText(getActivity(), "��ʱҽ����ʿδ�����ҽ������ͣ!", Toast.LENGTH_SHORT).show(); 
				 return false;
			 }else{
				 if(!"1".equals(entity.order_sub_no)){ //���������ҽ��
					 Toast.makeText(getActivity(), "��Ϊ��ҽ������Ҫͣ��ҽ��!", Toast.LENGTH_SHORT).show(); 
					 return false;
				 }else{
					 ArrayList<DcAdviceEntity> list = adapter.getList();//������е�ҽ��
					 ArrayList<DcAdviceEntity> list_update = new ArrayList<DcAdviceEntity>();//�����Ҫֹͣ��ҽ��
					 for (int i = 0; i < list.size(); i++) {
						if(entity.order_no.equals(list.get(i).order_no)){
							list_update.add(list.get(i));
							//DebugUtil.debug("��Ҫͣ��ҽ�������-->", list_update.get(i).order_no);
						}
					}//for
					for (int i = 0; i < list_update.size(); i++) {
						String sql="UPDATE ORDERS "+
		                         " SET STOP_DOCTOR= '"+app.getDoctor()+"',"+
								 " STOP_DATE_TIME= TO_DATE('"+req_date_time+"','yyyy-MM-dd hh24:mi:ss'),"+
	        		             " ORDER_STATUS='6',BILLING_ATTR='0',DRUG_BILLING_ATTR='0' "+
	                         " WHERE PATIENT_ID  = '"+app.getPatientEntity().patient_id+"'"+
	                         " AND VISIT_ID = '"+app.getPatientEntity().visit_id+"'"+
	                         " AND ORDER_NO = '"+list_update.get(i).order_no+"'"+
	                         " AND ORDER_SUB_NO = '"+list_update.get(i).order_sub_no+"'";
						Log.e("�������-->", sql);
						WebServiceHelper.insertWebServiceData(sql);
					} 
				 }
			 }//else1
			return true;
		default:
			break;
		}
		return false;
	}		

	/**
	 * 
	* @Title: getArrayItem 
	* @Description: TODO(�õ�ģ������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public String[] getArrayItem(){
		int size=app.getGroupOrderList().size();
		String[] arr=new String[size];
		for (int i = 0; i < size; i++) {
			arr[i]=app.getGroupOrderList().get(i).title;
		}
		return arr;
	}
	
	/**
	 * 
	* @Title: notChoosePatient 
	* @Description: TODO(��һ�ν��룬δѡ�в���ʱ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void notChoosePatient(){
		if (getListAdapter()==null) {
			setListShown(true);
			setEmptyText("��ѡ����");
		}
	}
}

