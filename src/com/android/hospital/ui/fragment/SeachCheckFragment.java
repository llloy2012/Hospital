package com.android.hospital.ui.fragment;

import java.util.Map;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckItemAdapter;
import com.android.hospital.ui.activity.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;

public class SeachCheckFragment extends SearchFragment implements OnItemClickListener{

	private AddCheckFragment fm;
	
	@Override
	public void show(BaseAdapter adapter) {
		// TODO Auto-generated method stub
		CheckItemAdapter itemAdapter=(CheckItemAdapter) adapter;
		getListView().setAdapter(itemAdapter);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getListView().setOnItemClickListener(this);
		fm=(AddCheckFragment) getActivity().getFragmentManager().findFragmentByTag("addfm");
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Map<String, String> map=(Map<String, String>) parent.getAdapter().getItem(position);
		fm.setListView(map);
	}
	
	
}
