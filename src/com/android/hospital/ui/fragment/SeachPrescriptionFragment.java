package com.android.hospital.ui.fragment;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PreMiddleDrugAdapter;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.ui.activity.R;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

public class SeachPrescriptionFragment extends SearchFragment implements OnItemClickListener{

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		show(null);//��ҩ
		getListView().setOnItemClickListener(this);
	}
	
	/**
	 * Ĭ����ʾ��ҩ����
	 */
	@Override
	public void show(BaseAdapter adapter) {
		// TODO Auto-generated method stub
	    ArrayList<DrugEntity> arrayList=getApp().getMiddleDrugList();
		PreMiddleDrugAdapter preAdapter = new PreMiddleDrugAdapter(
				getActivity(), R.layout.fragment_add_prescription_list_item,
				arrayList);
		getListView().setAdapter(preAdapter);
	}
	/**
	 * 
	* @Title: showWestDrug 
	* @Description: TODO(��ʾ��ҩ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void showWestDrug(ArrayList<DrugEntity> arrayList){
		PreMiddleDrugAdapter preAdapter = new PreMiddleDrugAdapter(
				getActivity(), R.layout.fragment_add_prescription_list_item,
				arrayList);
		getListView().setAdapter(preAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //���������
        imm.hideSoftInputFromWindow(getSearchView().getWindowToken(), 0);
		getListView().requestFocus();
		getListView().setFocusable(true);
		DrugEntity drugEntity=(DrugEntity) parent.getAdapter().getItem(position);
		AddPrescriptionFragment fm=(AddPrescriptionFragment) getActivity().getFragmentManager().findFragmentByTag("addfm");
		fm.setListView(drugEntity);
	}
}
