package com.android.hospital.ui.fragment;

import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddDcAdviceActivity;
import com.android.hospital.ui.activity.R;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
/**
 * 
* @ClassName: CheckFragment 
* @Description: TODO(检查界面显示) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:40:23 
*
 */
public class CheckFragment extends ListFragment {

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增检查")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			intent=new Intent();
			intent.setClass(getActivity(), AddCheckActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/**
	 * 
	* @Title: clearAdapter 
	* @Description: TODO(清空adapter) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void clearAdapter(){
	    CheckAdapter adapter=(CheckAdapter) getListAdapter();
		if (null!=adapter&&adapter.getCount()!=0) {
			adapter.clearAdapter();
		}
	}
}
