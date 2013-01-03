package com.android.hospital.ui.fragment;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.entity.CheckEntity;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.CheckdetailActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: CheckFragment 
* @Description: TODO(��������ʾ) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:40:23 
*
 */
public class CheckFragment extends ListFragment {
		
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
		getListView().setFastScrollEnabled(true);
		getActivity().findViewById(R.id.listview_common_titlebar).setVisibility(View.GONE);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "�������")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		menu.addSubMenu(1, Menu.FIRST+1, 1, "��ʱ���ѯ");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			if (AppConstant.isPatientChoose) {
				intent=new Intent();
				intent.setClass(getActivity(), AddCheckActivity.class);
				startActivityForResult(intent, 12);
			}else {
				Toast.makeText(getActivity(), "����ѡ����!", Toast.LENGTH_SHORT).show();//�ɸ�����߲���listview�Ƿ��б�ѡ���ж�
			}	
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	* @Title: clearAdapter 
	* @Description: TODO(���adapter) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void clearAdapter(){
	    CheckAdapter adapter=(CheckAdapter) getListAdapter();
		if (null!=adapter&&adapter.getCount()!=0) {
			adapter.clearAdapter();
			if (isAdded()) {
				setListShown(false);
			}			
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		DebugUtil.debug("resultCode---"+resultCode);
		DebugUtil.debug("requestCode---"+requestCode);
        if (resultCode==12) {
			MainActivity mainActivity=(MainActivity) getActivity();
			HospitalApp app=(HospitalApp) mainActivity.getApplication();
			mainActivity.putCheckTask(app.getPatientEntity());
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		CheckAdapter adapter=(CheckAdapter) getListAdapter();
		CheckEntity entity=(CheckEntity) adapter.getItem(position);
		Intent intent=new Intent();
		intent.putExtra("check", entity);
		intent.setClass(getActivity(), CheckdetailActivity.class);
		startActivity(intent);
	}
	

}
