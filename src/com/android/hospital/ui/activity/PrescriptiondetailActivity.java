package com.android.hospital.ui.activity;

import java.util.ArrayList;
import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PrescriptionDetailAdapter;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.entity.PrescriptionEntity;
import com.android.hospital.entity.PrescriptiondetailEntity;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
* @ClassName: PrescriptiondetailActivity 
* @Description: TODO(������ϸ��ʾ) 
* @author lll 
* @date 2012-12-19 
*
 */
public class PrescriptiondetailActivity extends Activity{

	private ListView mListView;
	
    private View mProcessView;
	
	private TextView mEmptyView;
	
	private TextView tev1,tev2,tev3,tev4;
	
	private PrescriptionEntity prescriptionEntity;//����ʵ��
	
	private HospitalApp app;
	
	private PatientEntity patientEntity;//����ʵ��
	
	private ArrayList<PrescriptiondetailEntity> arrayList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_prescription_list_detail);
		Intent intent=getIntent();
		prescriptionEntity=(PrescriptionEntity) intent.getSerializableExtra("prescription");
		//��ȡ����ʵ��
		app=(HospitalApp) getApplication();
		this.patientEntity=app.getPatientEntity();
		initView();
		new PrescriptiondetailTask().execute();
	}

	/**
	 * 
	* @Title: initView 
	* @Description: TODO(��ʼ���ؼ�) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void initView() {
		mListView=(ListView) findViewById(R.id.prescription_detail_listview);
		mProcessView=findViewById(R.id.progressContainer);
		mEmptyView=(TextView) findViewById(R.id.inspection_empty);
		tev1=(TextView) findViewById(R.id.patient_information_no1);
		tev2=(TextView) findViewById(R.id.prescription_information_item1);
		tev3=(TextView) findViewById(R.id.prescription_information_item2);
		tev4=(TextView) findViewById(R.id.prescription_information_item3);
		tev1.setText("���ţ�"+patientEntity.bed_no+"   ������"+patientEntity.name+"  �Ա�"+patientEntity.sex
				       +"   ����ID��"+patientEntity.patient_id+"   ʣ��Ԥ����"+patientEntity.prepayments+"   �ѱ�"+patientEntity.charge_type);
		tev2.setText("�����ţ�"+prescriptionEntity.presc_no+"   �������ڣ�"+prescriptionEntity.presc_date+"   ����ҽ����"+prescriptionEntity.prescribed_by);
		tev3.setText("�������ͣ�"+prescriptionEntity.presc_type+"   ������"+prescriptionEntity.repetition+"   �ܻ��ѣ�"+prescriptionEntity.costs);
		tev4.setText("״̬��"+prescriptionEntity.presc_status+"    ��ҩҩ����"+prescriptionEntity.dept_name);
		
		mListView.setVisibility(View.INVISIBLE);
		mProcessView.setVisibility(View.VISIBLE);
		mEmptyView.setVisibility(View.INVISIBLE);
	}
	
	/**
	 * 
	* @ClassName: PrescriptiondetailTask 
	* @Description: TODO(��ȡ��ϸ����) 
	* @author lll 
	* @date 2012-12-19 
	*
	 */
	private class PrescriptiondetailTask extends AsyncTask<Void, Void, Object>{

		@Override
		protected Object doInBackground(Void... params) {
			// TODO Auto-generated method stub
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(getSql());
			arrayList=PrescriptiondetailEntity.init(dataList);
			return arrayList;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			mProcessView.setVisibility(View.GONE);
			if (arrayList.size()!=0) {
				mListView.setVisibility(View.VISIBLE);
				PrescriptionDetailAdapter adapter=new PrescriptionDetailAdapter(PrescriptiondetailActivity.this, arrayList);
				mListView.setAdapter(adapter);
			}else {
				mEmptyView.setVisibility(View.VISIBLE);
			}
		}
	}
	
	/**
	 * 
	* @Title: getSql 
	* @Description: TODO(�õ�������ϸsql���) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private String getSql(){
		String tableName="DOCT_DRUG_PRESC_DETAIL,DOCT_DRUG_PRESC_MASTER";
		String[] paramArray1=new String[]{"DOCT_DRUG_PRESC_DETAIL.PRESC_DATE","DOCT_DRUG_PRESC_DETAIL.PRESC_NO","DOCT_DRUG_PRESC_DETAIL.ITEM_NO",
				                          "DOCT_DRUG_PRESC_DETAIL.DRUG_NAME","DOCT_DRUG_PRESC_DETAIL.PACKAGE_SPEC","DOCT_DRUG_PRESC_DETAIL.FIRM_ID",
				                          "DOCT_DRUG_PRESC_DETAIL.DOSAGE_EACH","DOCT_DRUG_PRESC_DETAIL.DOSAGE_UNITS","DOCT_DRUG_PRESC_DETAIL.ADMINISTRATION",
				                          "DOCT_DRUG_PRESC_DETAIL.FREQUENCY","DOCT_DRUG_PRESC_DETAIL.FREQ_DETAIL","DOCT_DRUG_PRESC_DETAIL.QUANTITY",
				                          "DOCT_DRUG_PRESC_DETAIL.PACKAGE_UNITS","DOCT_DRUG_PRESC_DETAIL.COSTS"};
		String customWhere="WHERE (DOCT_DRUG_PRESC_DETAIL.PRESC_NO = DOCT_DRUG_PRESC_MASTER.PRESC_NO) "
				         + "and (DOCT_DRUG_PRESC_DETAIL.PRESC_DATE =  DOCT_DRUG_PRESC_MASTER.PRESC_DATE)  "
				         + "and ((DOCT_DRUG_PRESC_DETAIL.PRESC_DATE =  "
				         + "to_date('"+prescriptionEntity.presc_date+"', 'yyyy-MM-dd hh24:mi:ss')) AND  "
				         + " (DOCT_DRUG_PRESC_DETAIL.PRESC_NO = '"+prescriptionEntity.presc_no+"'))  "
				         + "order by DOCT_DRUG_PRESC_DETAIL.ITEM_NO  "; 

		String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
		DebugUtil.debug("sql�����ϸ---->"+sql);
		return sql;
	}
}
