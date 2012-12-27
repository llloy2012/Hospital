package com.android.hospital.ui.fragment;

import com.android.hospital.adapter.PrescriptionAdapter;
import com.android.hospital.entity.PrescriptionEntity;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddPrescriptionActivity;
import com.android.hospital.ui.activity.PrescriptiondetailActivity;
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
* @ClassName: PrescriptionFragment 
* @Description: TODO(处方界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:41:45 
*
 */
public class PrescriptionFragment extends ListFragment{
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.add(Menu.NONE, Menu.FIRST, 0, "新增处方")
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case Menu.FIRST:
			intent=new Intent();
			intent.setClass(getActivity(), AddPrescriptionActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**
	 * @method: 处方点击  
     * @Description: TODO(处方实体) 
     * @author lll
     * @date 2012-12-19   
	 */
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		super.onListItemClick(list, v, position, id);
		PrescriptionAdapter adapter=(PrescriptionAdapter) list.getAdapter();
		PrescriptionEntity item=(PrescriptionEntity) adapter.getItem(position);
		DebugUtil.debug("选中的为---->"+item.presc_no);
		//跳转到新的界面
		Intent intent=new Intent();
		intent.putExtra("prescription", item);//将处方实体传到新的界面
		intent.setClass(getActivity(), PrescriptiondetailActivity.class);
		startActivity(intent);
	}
}
