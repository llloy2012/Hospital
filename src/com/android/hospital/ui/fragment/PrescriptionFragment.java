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
* @Description: TODO(��������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:41:45 
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
			
	/**
	 * @method: �������  
     * @Description: TODO(����ʵ��) 
     * @author lll
     * @date 2012-12-19   
	 */
	@Override
	public void onListItemClick(ListView list, View v, int position, long id) {
		super.onListItemClick(list, v, position, id);
		PrescriptionAdapter adapter=(PrescriptionAdapter) list.getAdapter();
		PrescriptionEntity item=(PrescriptionEntity) adapter.getItem(position);
		DebugUtil.debug("ѡ�е�Ϊ---->"+item.presc_no);
		//��ת���µĽ���
		Intent intent=new Intent();
		intent.putExtra("prescription", item);//������ʵ�崫���µĽ���
		intent.setClass(getActivity(), PrescriptiondetailActivity.class);
		startActivity(intent);
	}
	
}
