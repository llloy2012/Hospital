package com.android.hospital.ui.fragment;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PrescriptionAdapter;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.entity.PrescriptionEntity;
import com.android.hospital.ui.activity.AddCheckActivity;
import com.android.hospital.ui.activity.AddPrescriptionActivity;
import com.android.hospital.ui.activity.MainActivity;
import com.android.hospital.ui.activity.PrescriptiondetailActivity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
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
		getListView().setFastScrollEnabled(true);
		getActivity().findViewById(R.id.listview_common_titlebar).setVisibility(View.GONE);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		menu.addSubMenu(1, 41, 1, "按时间查询");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (item.getItemId()) {
		case 41:
			Toast.makeText(getActivity(), "功能尚未添加!", Toast.LENGTH_SHORT).show(); 		
			break;
		case Menu.FIRST+1:
			
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==14) {
			MainActivity mainActivity=(MainActivity) getActivity();
			HospitalApp app=(HospitalApp) mainActivity.getApplication();
			mainActivity.putPrescriptionTask(app.getPatientEntity());
		}
	}
}
