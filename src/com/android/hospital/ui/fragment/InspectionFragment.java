package com.android.hospital.ui.fragment;

import com.android.hospital.adapter.InspectionAdapter;
import com.android.hospital.entity.InspectionEntity;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddInspectionActivity;
import com.android.hospital.ui.activity.InspectiondetailActivity;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
/**
 * 
* @ClassName: InspectionFragment 
* @Description: TODO(检验界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:41:04 
*
 */
public class InspectionFragment extends ListFragment{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增检验")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			intent=new Intent();
			intent.setClass(getActivity(), AddInspectionActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		InspectionAdapter adapter=(InspectionAdapter) l.getAdapter();
		InspectionEntity item=(InspectionEntity) adapter.getItem(position);
		Intent intent=new Intent();
		intent.putExtra("inspection", item);
		intent.setClass(getActivity(), InspectiondetailActivity.class);
		startActivity(intent);
	}
}
