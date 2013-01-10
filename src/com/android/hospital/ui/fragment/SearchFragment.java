package com.android.hospital.ui.fragment;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.DrugAdapter;
import com.android.hospital.adapter.NonDrugAdapter;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
/**
 * 
* @ClassName: SearchFragment 
* @Description: TODO(À—À˜ΩÁ√Ê) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 …œŒÁ11:41:57 
*
 */
public abstract class SearchFragment extends Fragment implements SearchView.OnQueryTextListener,OnItemClickListener{

	private ListView mListView;
	
	private SearchView mSearchView;
	
	private HospitalApp app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_search, null);
		mListView=(ListView) view.findViewById(R.id.search_list_view);
		mSearchView=(SearchView) view.findViewById(R.id.search_search_view);
		mListView.setTextFilterEnabled(true);
		mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint("«Î ‰»Î");
        mListView.setFastScrollEnabled(true);
        return view;
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
        	mListView.clearTextFilter();
        } else {
        	mListView.setFilterText(newText.toString());
        }
        return true;
	}
	
	public ListView getListView(){
		return mListView;
	}
	
	public HospitalApp getApp(){
		return app;
	}
	
	public SearchView getSearchView(){
		return mSearchView;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //“˛≤ÿ»Ìº¸≈Ã
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
		mListView.requestFocus();
		mListView.setFocusable(true);
	}
	
	public abstract void show(BaseAdapter adapter);
}
