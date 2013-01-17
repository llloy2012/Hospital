package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.android.hospital.entity.PatientEntity;
import com.android.hospital.ui.activity.R;

import android.app.ListFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
* @ClassName: EvaluationFragment 
* @Description: TODO(住院评估界面) 
* @author lll
* @date 2013-01-17 
*
 */
public class EvaluationFragment extends ListFragment{

	
	public interface Callbacks {

        public void onItemSelected(String id);
    }
	
	public EvaluationFragment(){

	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//PatientAdapter adapter=new PatientAdapter(new PatientEntity("test").list);
		//setListAdapter(adapter);
		
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setFastScrollEnabled(true);
		super.onStart();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		//PatientAdapter adapter=(PatientAdapter) l.getAdapter();
		//adapter.setSelectItem(position);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
//		if (getListAdapter()==null) {
//			setEmptyText("未获取到病人信息");
//		}
	}
}
