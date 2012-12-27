package com.android.hospital.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.hospital.HospitalApp;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.R.integer;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 
* @ClassName: AddInspectionFragment 
* @Description: TODO(新增处方界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-21 下午11:54:54 
*
 */
public class AddPrescriptionFragment extends BaseFragment implements OnClickListener,OnItemSelectedListener{
    private View view;
	private Spinner mClassSp;
	private CheckBox mTaskDrugBox,mIsOrNotBox;//出院带药,是否带煎
	private TextView mDrugHouseTev,mNextvalTev,mTempJudgeTev,mAgentNumTev,mEachNumTev;//药局，处方，诊断,剂数，每剂煎
	private Button mAgentPlusBut,mAgentMinusBut,mEachPlusBut,mEachMinusBut;//加减
	private EditText mHowEdit,mNextvalEdit;//用法，处方名
	private ListView mListView;
	private HospitalApp app;
	private ArrayList<DrugEntity> mWestDrugList;//西药
	private SeachPrescriptionFragment fm;//搜索界面
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
		fm=(SeachPrescriptionFragment) getActivity().getFragmentManager().findFragmentByTag("searchfm");
		new WestDrugTaskStep1().execute();//获取西药任务
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_add_prescription_left, null);
		init();
		return view;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		mClassSp=(Spinner) view.findViewById(R.id.add_prescription_spinner_1);
		mTaskDrugBox=(CheckBox) view.findViewById(R.id.add_prescription_checkbox_taskdrug);
		mIsOrNotBox=(CheckBox) view.findViewById(R.id.add_prescription_checkbox_isornot);
		mDrugHouseTev=(TextView) view.findViewById(R.id.add_prescription_tev_1);
		mNextvalTev=(TextView) view.findViewById(R.id.add_prescription_tev_2);
		mTempJudgeTev=(TextView) view.findViewById(R.id.add_prescription_tev_3);
		mAgentNumTev=(TextView) view.findViewById(R.id.add_prescription_tev_agent);
		mEachNumTev=(TextView) view.findViewById(R.id.add_prescription_tev_each);
		mAgentPlusBut=(Button) view.findViewById(R.id.add_prescription_but_agentplus);
		mAgentMinusBut=(Button) view.findViewById(R.id.add_prescription_but_agentminus);
		mEachPlusBut=(Button) view.findViewById(R.id.add_prescription_but_eachplus);
		mEachMinusBut=(Button) view.findViewById(R.id.add_prescription_but_eachminus);
		mHowEdit=(EditText) view.findViewById(R.id.add_prescription_edit_how);
		mNextvalEdit=(EditText) view.findViewById(R.id.add_prescription_edit_nextval);
		mListView=(ListView) view.findViewById(R.id.add_prescription_listview);
		
		mTempJudgeTev.setText(app.getPatientEntity().diagnosis);
		mAgentPlusBut.setOnClickListener(this);
		mAgentMinusBut.setOnClickListener(this);
		mEachPlusBut.setOnClickListener(this);
		mEachMinusBut.setOnClickListener(this);
		mClassSp.setOnItemSelectedListener(this);
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onOtherFragmentClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_prescription_but_agentplus:
			int num=Integer.parseInt(mAgentNumTev.getText().toString());
			if (num<20) {
				num++;
				mAgentNumTev.setText(Integer.toString(num));
			}
			break;
		case R.id.add_prescription_but_agentminus:
			int numMinus=Integer.parseInt(mAgentNumTev.getText().toString());
			if (numMinus>1) {
				numMinus--;
				mAgentNumTev.setText(Integer.toString(numMinus));
			}
			break;
		case R.id.add_prescription_but_eachplus:
			int num2=0;
			if (!"".equals(mEachNumTev.getText().toString())) {
				num2=Integer.parseInt(mEachNumTev.getText().toString());
			}
			if (num2<20) {
				num2++;
				mEachNumTev.setText(Integer.toString(num2));
			}
			break;
		case R.id.add_prescription_but_eachminus:
			int numMinus2=0;
			if (!"".equals(mEachNumTev.getText().toString())) {
				numMinus2=Integer.parseInt(mEachNumTev.getText().toString());
			}
			if (numMinus2>1) {
				numMinus2--;
				mEachNumTev.setText(Integer.toString(numMinus2));
			}else {
				mEachNumTev.setText("");
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			fm.show(null);
			break;
		case 1:
			DebugUtil.debug("positon-case--1");
			if (mWestDrugList!=null) {
				fm.showWestDrug(mWestDrugList);
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 
	* @ClassName: WestDrugTaskStep1 
	* @Description: TODO(获取西药步骤) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-26 下午5:03:29 
	*
	 */
	private class WestDrugTaskStep1 extends AsyncTask<Void, Void, List<String>>{

		@Override
		protected List<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String sql = "select order_code from orders where patient_id = '"
					+ app.getPatientEntity().patient_id
					+ "'"
					+ " and visit_id = '"
					+ app.getPatientEntity().visit_id
					+ "'"
					+ " and order_class = 'A'"
					+ " and ((start_date_time < sysdate and start_date_time >sysdate-1) or ( repeat_indicator = '1' and administration in( '口服','雾化吸入')))";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			int size=dataList.size();
			List<String> drug_codeList=new ArrayList<String>();
			for (int i = 0; i < size; i++) {
				drug_codeList.add(dataList.get(i).get("order_code"));
			}
			return drug_codeList;
		}
		@Override
		protected void onPostExecute(List<String> result) {
			// TODO Auto-generated method stub
			new WestDrugTaskStep2().execute(result);
		}
	}	
	private class WestDrugTaskStep2 extends AsyncTask<List<String>, Void, ArrayList<DrugEntity>>{

		@Override
		protected ArrayList<DrugEntity> doInBackground(List<String>... params) {
			// TODO Auto-generated method stub
			StringBuffer buffer=new StringBuffer();
			buffer.append("select * from input_drug_lists where storage in ('3103', '3102') and drug_code in(");
			int size=params[0].size();
			for (int i = 0; i < size; i++) {
				if (i==(size-1)) {
					buffer.append("'").append(params[0].get(i)).append("')");
					break;
				}
				buffer.append("'").append(params[0].get(i)).append("',");
			}
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(buffer.toString());
			ArrayList<DrugEntity> westDrugList=DrugEntity.init(dataList);
			return westDrugList;
		}
		@Override
		protected void onPostExecute(ArrayList<DrugEntity> result) {
			// TODO Auto-generated method stub
            mWestDrugList=result;
		}
	}
	
}
