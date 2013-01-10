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
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
/**
 * 
* @ClassName: SearchFragment 
* @Description: TODO(��������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:41:57 
*
 */
public class SearchAddDcAdviceFragment extends Fragment implements SearchView.OnQueryTextListener,OnItemClickListener{

	private ListView mListView;
	
	private SearchView mSearchView;
	
	private HospitalApp app;
	
	private DrugAdapter drugAdapter;
	
	private NonDrugAdapter nonDrugAdapter;
	
	private boolean isDrugOrNonDrug=true;//��ʾ��ΪҩƷ���Ƿ�ҩƷ���
	
	private AddDcAdviceFragment addDcAdviceFm;//��ߵ�fragment
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
		drugAdapter=new DrugAdapter(getActivity(), R.layout.fragment_search_list_item, app.getDrugList());
		nonDrugAdapter=new NonDrugAdapter(getActivity(), R.layout.fragment_search_list_item, app.getNondrugList());
		addDcAdviceFm=(AddDcAdviceFragment) getActivity().getFragmentManager().findFragmentByTag("addfm");
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
        mSearchView.setQueryHint("������");
        showDrug();
        mListView.setOnItemClickListener(this);
		return view;
	}
	
	/**
	 * 
	* @Title: showDrug 
	* @Description: TODO(��ʾҩƷ) 
	* @param @param adapter    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void showDrug(){
		mListView.setAdapter(drugAdapter);
		isDrugOrNonDrug=true;
	}
	
	/**
	 * 
	* @Title: showNonDrug 
	* @Description: TODO(��ʾ��ҩƷ) 
	* @param @param adapter    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void showNonDrug(){
		mListView.setAdapter(nonDrugAdapter);
		isDrugOrNonDrug=false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		return false;
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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
        //DebugUtil.debug("hasfocus--->"+mSearchView.hasFocus());
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //���������
        imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);

		mListView.requestFocus();
		mListView.setFocusable(true);
		if (isDrugOrNonDrug) {
			DrugEntity drugEntity=(DrugEntity) mListView.getAdapter().getItem(position);
			addDcAdviceFm.initDrug(drugEntity);
		}else {
			NonDrugEntity nonDrugEntity=(NonDrugEntity) mListView.getAdapter().getItem(position);
			addDcAdviceFm.initNonDrug(nonDrugEntity);
		}
	}
}
