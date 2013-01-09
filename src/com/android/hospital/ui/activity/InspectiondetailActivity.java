package com.android.hospital.ui.activity;

import java.util.ArrayList;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.InspectionDetailAdapter;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.InspectionEntity;
import com.android.hospital.entity.InspectiondetailEntity;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
* @ClassName: InspectionInfoActivity 
* @Description: TODO(检验明细显示) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-19 下午3:27:47 
*
 */
public class InspectiondetailActivity extends Activity{

	private ListView mListView;
	
	private View mProcessView;
	
	private TextView mEmptyView;
	
	private TextView tev1,tev2,tev3;
	
	private InspectionEntity inspectionEntity;//检验实体
	
	private HospitalApp app;
	
	private PatientEntity patientEntity;//病人实体
	
	private ArrayList<InspectiondetailEntity> arrayList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_inspection_list_detail);
		Intent intent=getIntent();
		inspectionEntity=(InspectionEntity) intent.getSerializableExtra("inspection");
		app=(HospitalApp) getApplication();
		this.patientEntity=app.getPatientEntity();
		initView();
		new InspectiondetailTask().execute();
	}

	/**
	 * 
	* @Title: initView 
	* @Description: TODO(初始化控件) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initView() {
		mListView=(ListView) findViewById(R.id.inspection_detail_listview);
		mProcessView=findViewById(R.id.progressContainer);
		mEmptyView=(TextView) findViewById(R.id.inspection_empty);
		tev1=(TextView) findViewById(R.id.patient_information_item1);
		tev2=(TextView) findViewById(R.id.patient_information_item2);
		tev3=(TextView) findViewById(R.id.patient_information_item3);
		
		tev1.setText("床号："+patientEntity.bed_no+"   姓名："+patientEntity.name+"  性别："+patientEntity.sex
				       +"   病人ID："+patientEntity.patient_id+"   剩余预交金："+patientEntity.prepayments+"   费别："+patientEntity.charge_type);
		tev2.setText("临床诊断："+patientEntity.diagnosis+"   检验科室："+inspectionEntity.dept_name+"   标本："+inspectionEntity.specimen);
		tev3.setText(inspectionEntity.item_name);
		
		mListView.setVisibility(View.INVISIBLE);
		mProcessView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 
	* @ClassName: InspectiondetailTask 
	* @Description: TODO(获取明细任务) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-19 下午4:06:59 
	*
	 */
	private class InspectiondetailTask extends AsyncTask<Void, Void, Object>{

		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(getSql());
			arrayList=InspectiondetailEntity.init(dataList);
			return arrayList;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			mProcessView.setVisibility(View.GONE);
			if (arrayList.size()!=0) {
				mListView.setVisibility(View.VISIBLE);
				InspectionDetailAdapter adapter=new InspectionDetailAdapter(InspectiondetailActivity.this, arrayList);
				mListView.setAdapter(adapter);
			}else {
				mEmptyView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * 
	* @Title: getSql 
	* @Description: TODO(得到获取明细sql语句) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private String getSql(){
		String tableName="LAB_RESULT";
		String[] paramArray1=new String[]{"TO_CHAR(LAB_RESULT.RESULT_DATE_TIME,'yyyy-MM-dd hh24:mi:ss') as RESULT_DATE_TIME","LAB_RESULT.REPORT_ITEM_NAME",
				                          "LAB_RESULT.RESULT","LAB_RESULT.UNITS","LAB_RESULT.PRINT_CONTEXT","LAB_RESULT.ABNORMAL_INDICATOR"};
		String[] whereArr=new String[]{"LAB_RESULT.TEST_NO"};
		String[] paramArray2=new String[]{inspectionEntity.test_no};
		String sql=ServerDao.getQuery(tableName, paramArray1, whereArr, paramArray2);
		return sql;
	}
}
