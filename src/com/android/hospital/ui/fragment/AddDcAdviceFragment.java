package com.android.hospital.ui.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.DrugAdapter;
import com.android.hospital.adapter.FreqSpinnerAdapter;
import com.android.hospital.asyntask.add.InsertDcAdviceTask;
import com.android.hospital.asyntask.add.PriceTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.util.Util;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: AddDcAdviceFragment 
* @Description: TODO(������һ�仰��������������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:34:25 
*
 */
public class AddDcAdviceFragment extends Fragment implements OnClickListener,OnItemSelectedListener{

	private View view;
	private RadioButton mDrugBut,mNonDrugBut,mLongTimeBut,mShortTimeBut;
	private TextView mDcAdViceTev,mDosageunitsTev,mTimeTev;
	private EditText mDosageEdit,mInstrutionEdit;
	private Spinner mWaySpinner,mFreqSpinner,mZiSpinner;
	private LinearLayout mHideLayout1,mHideLayout2;//�����صĲ���
	private CheckBox mSubCheckBox;
	private HospitalApp app;
	private List<Map<String, String>> mFreqList;
	private List<Map<String, String>> mWayList;
	private String order_class="";
	private String order_code="";
	private String drug_spec="";//ҩƷ���
	private DcAdviceEntity subEntity;//��ҽ��
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
		this.mFreqList=app.getFreqList();
		this.mWayList=app.getWayList();
		this.subEntity=(DcAdviceEntity) getActivity().getIntent().getSerializableExtra("subentity");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_add_dcadvice_left, null);
		initView();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mNonDrugBut.setOnClickListener(this);
		mDrugBut.setOnClickListener(this);
		mLongTimeBut.setOnClickListener(this);
		mShortTimeBut.setOnClickListener(this);
		mSubCheckBox.setOnClickListener(this);
		mWaySpinner.setOnItemSelectedListener(this);
		mFreqSpinner.setOnItemSelectedListener(this);
		setSpinner();
		if (subEntity.order_class.equals("A")) {
			mSubCheckBox.setVisibility(View.VISIBLE);
		}else {
			mSubCheckBox.setVisibility(View.GONE);
		}
	}

	/**
	 * 
	* @Title: initView 
	* @Description: TODO(��ʼ���ؼ�) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void initView(){
		mDrugBut=(RadioButton) view.findViewById(R.id.add_dcadvice_but_drug);
		mNonDrugBut=(RadioButton) view.findViewById(R.id.add_dcadvice_but_nondrug);
		mLongTimeBut=(RadioButton) view.findViewById(R.id.add_dcadvice_but_longtime);
		mShortTimeBut=(RadioButton) view.findViewById(R.id.add_dcadvice_but_shorttime);
		mHideLayout1=(LinearLayout) view.findViewById(R.id.hidelayout1);
		mHideLayout2=(LinearLayout) view.findViewById(R.id.hidelayout2);
		mDcAdViceTev=(TextView) view.findViewById(R.id.add_dcadvice_tev_info);
		mDosageunitsTev=(TextView) view.findViewById(R.id.add_dcadvice_tev_dosage_units);
		mTimeTev=(TextView) view.findViewById(R.id.add_dcadvice_tev_time);
		mDosageEdit=(EditText) view.findViewById(R.id.add_dcadvice_edit_dosage);
		mWaySpinner=(Spinner) view.findViewById(R.id.add_dcadvice_spinner_administration);
		mZiSpinner=(Spinner) view.findViewById(R.id.add_dcadvice_spinner_zi);
		mFreqSpinner=(Spinner) view.findViewById(R.id.add_dcadvice_spinner_frequency);
		mInstrutionEdit=(EditText) view.findViewById(R.id.add_dcadvice_edit_instrution);
		mSubCheckBox=(CheckBox) view.findViewById(R.id.add_dcadvice_checkbox_sub);
	}
	
	/**
	 * 
	* @Title: setSpinner 
	* @Description: TODO(����spinner��ʾ��Ϣ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void setSpinner(){
		List<String> freqSpList=new ArrayList<String>();
		for (int i = 0; i < mFreqList.size(); i++) {
			String item=mFreqList.get(i).get("freq_desc");
			freqSpList.add(item);
		}
		ArrayAdapter<String> freqAdapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, freqSpList);
		freqAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mFreqSpinner.setAdapter(freqAdapter);
		
		List<String> waySpList=new ArrayList<String>();
		for (int i = 0; i < mWayList.size(); i++) {
			String item=mWayList.get(i).get("administration_name");
			waySpList.add(item);
		}
		ArrayAdapter<String> wayAdapter=new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, waySpList);
		wayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mWaySpinner.setAdapter(wayAdapter);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SearchAddDcAdviceFragment searchFm=(SearchAddDcAdviceFragment) getActivity().getFragmentManager().findFragmentByTag("searchfm");
		switch (v.getId()) {
		case R.id.add_dcadvice_but_drug:
			if (mDrugBut.isChecked()) {
				searchFm.showDrug();
			}
			if (mLongTimeBut.isChecked()) {
				mHideLayout1.setVisibility(View.VISIBLE);
				mHideLayout2.setVisibility(View.VISIBLE);
			}else {
				mHideLayout1.setVisibility(View.VISIBLE);
				mHideLayout2.setVisibility(View.GONE);
			}
			break;
		case R.id.add_dcadvice_but_nondrug:
			if (mNonDrugBut.isChecked()) {
				searchFm.showNonDrug();
			}
			if (mLongTimeBut.isChecked()) {
				mHideLayout1.setVisibility(View.GONE);
				mHideLayout2.setVisibility(View.VISIBLE);
			}else {
				mHideLayout1.setVisibility(View.GONE);
				mHideLayout2.setVisibility(View.GONE);
			}
			break;
		case R.id.add_dcadvice_but_longtime:
			if (mNonDrugBut.isChecked()) {
				searchFm.showNonDrug();
			}
			if (mDrugBut.isChecked()) {
				mHideLayout1.setVisibility(View.VISIBLE);
				mHideLayout2.setVisibility(View.VISIBLE);
			}else {
				mHideLayout1.setVisibility(View.GONE);
				mHideLayout2.setVisibility(View.VISIBLE);		
			}
			break;
		case R.id.add_dcadvice_but_shorttime:
			if (mNonDrugBut.isChecked()) {
				searchFm.showNonDrug();
			}
			if (mDrugBut.isChecked()) {
				mHideLayout1.setVisibility(View.VISIBLE);
				mHideLayout2.setVisibility(View.GONE);
			}else {
				mHideLayout1.setVisibility(View.GONE);
				mHideLayout2.setVisibility(View.GONE);
			}
			break;
		case R.id.add_dcadvice_checkbox_sub:
			if (mSubCheckBox.isChecked()) {
				view.findViewById(R.id.radiolayout).setVisibility(View.GONE);
				view.findViewById(R.id.dosage_units_layout).setVisibility(View.GONE);
				view.findViewById(R.id.administration_layout).setVisibility(View.GONE);
				mHideLayout2.setVisibility(View.GONE);
			}else {
				view.findViewById(R.id.radiolayout).setVisibility(View.VISIBLE);
				view.findViewById(R.id.dosage_units_layout).setVisibility(View.VISIBLE);
				view.findViewById(R.id.administration_layout).setVisibility(View.VISIBLE);
				mHideLayout2.setVisibility(View.VISIBLE);
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 
	* @Title: initDrug 
	* @Description: TODO(��ʾҩƷ����) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void initDrug(DrugEntity drugEntity){
		mDcAdViceTev.setText(drugEntity.drug_name);
		mDosageunitsTev.setText(drugEntity.dose_units);
		mDosageEdit.setText(drugEntity.dose_per_unit);
		order_class=drugEntity.drug_indicator;
		order_code=drugEntity.drug_code;
		drug_spec=drugEntity.drug_spec;
	}
	/**
	 * 
	* @Title: initNonDrug 
	* @Description: TODO(��ʾ��ҩƷ����) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void initNonDrug(NonDrugEntity nonDrugEntity){
		mDcAdViceTev.setText(nonDrugEntity.item_name);
		order_class=nonDrugEntity.item_class;
		order_code=nonDrugEntity.item_code;
	}
	
	/**
	 * 
	* @Title: getAddData 
	* @Description: TODO(�õ�) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void getAddData(){
		DcAdviceEntity entity=new DcAdviceEntity();
		entity.start_date_time=Util.toSimpleDate();// ��ȡ��ǰʱ��
		entity.order_text=mDcAdViceTev.getText().toString();// ҽ������
		entity.dosage=mDosageEdit.getText().toString();// ����
		entity.dosage_units=mDosageunitsTev.getText().toString();// ������λ
		entity.freq_detail=mInstrutionEdit.getText().toString();// ҽ��˵��
		entity.administration=mWaySpinner.getItemAtPosition(
				mWaySpinner.getSelectedItemPosition()).toString();// ;��
		Map<String, String> freqMap = mFreqList.get(mFreqSpinner
				.getSelectedItemPosition());
		entity.frequency=freqMap.get("freq_desc");// Ƶ��
		entity.freq_counter=freqMap.get("freq_counter");// Ƶ�ʴ���
		entity.freq_interval=freqMap.get("freq_interval");/// Ƶ�ʼ��
		entity.freq_interval_units=freqMap.get("freq_interval_unit");// Ƶ�ʼ����λ
		if (null!=entity.freq_interval_units&&entity.freq_interval_units.equals("")) {
			entity.freq_interval_units="��";
		}
		entity.doctor=app.getDoctor();// ҽ��
		entity.order_class=this.order_class;// ҽ����� ҩƷ��
		                                    // input_drug_lists.drug_indicator
		                                    // ��ҩƷ:v_clinic_name_dict.item_class,ͨ��drug��nondrug���
		entity.order_code=this.order_code;//ҽ������,ҽ������  ��ҩƷ:item_code  ҩƷ:drug_code
		entity.repeat_indicator="1";// ������ʱ��0Ϊ �ڣ�1Ϊ��ʱ;
		entity.stop_date_time="";// ֹͣʱ��,���Ϊ���ڣ���Ϊ�գ�������ڵ�ǰʱ��
		if (mShortTimeBut.isChecked()) {
			entity.repeat_indicator = "0";
			entity.stop_date_time = entity.start_date_time;
		}
        entity.order_status="6";// ��ORDER_STATUS_DICT�в鿴
		entity.enter_date_time=entity.start_date_time;// ��ҽ��¼�����ڼ�ʱ�䣬��ͬSTART_DATE_TIMEʱ�䣬������ʱϵͳʱ��)
        entity.patient_id=app.getPatientEntity().patient_id;//����ID
        entity.visit_id=app.getPatientEntity().visit_id;//סԺ����
        entity.order_no=app.getMaxNumber();// ҽ�����
        entity.order_sub_no="1";// ��ҽ���� �Զ�����ҽ����Ϊ1���ڳ���ҽ���ڲ�����1��ʼ˳������
        entity.perform_schedule = "";// ҽ��ִ��ʱ��
		                             // ����ҽ������ִ��ʱ�䣬��ʱҽ��������Ϊ�ա�����ʿת��ʱ�����ֶ���д��
		if (mLongTimeBut.isChecked()) {
			entity.perform_schedule = mTimeTev.getText().toString();
		}
		entity.billing_attr = String.valueOf(mZiSpinner
				.getSelectedItemPosition());// �Ƽ����� ��ӳ����ҽ���Ƽ۷������Ϣ��0-�����Ƽ� 1-�Դ�ҩ
											// 2-���ֹ��Ƽ�
											// 3-���Ƽۡ��ɻ�ʿ¼��ҽ��ʱ������ҽ������ȷ����(һ����0��
		entity.ordering_dept = app.getPatientEntity().dept_code;// ��ҽ������ staff_dict.dept_code
		entity.drug_billing_attr = "0";// ҩƷ�Ƽ����� ��ӳҩƷ�Ƿ�Ƽۣ�0-������1-�Դ�ҩ(һ����0��
		if (order_class.equals("A")) {// ���order_class=A,BILLING_ATTR=1,��c=1,BILLING_ATTR=4,��DRUG_BILLING_ATTR=4
			if (entity.billing_attr.equals("1")) {
				entity.drug_billing_attr = "1";
			}
			if (entity.billing_attr.equals("4")) {
				entity.drug_billing_attr = "4";
			}
		}
		entity.doctor_user = app.getLoginName();// ҽ����½�˺�    	
        entity.drug_spec=drug_spec;
		
		if (mShortTimeBut.isChecked() && mDrugBut.isChecked()) {// ���Ϊ��ҩ�����Ƶ��ִ��ʱ��
			butChoose(0, entity);
		}
		if (mShortTimeBut.isChecked() && mNonDrugBut.isChecked()) {// ���Ϊ�ٷ�ҩ����յ�λ��������;����Ƶ�Σ�ִ��ʱ��
			butChoose(1, entity);
		}
		if (mLongTimeBut.isChecked() && mNonDrugBut.isChecked()) {// ���Ϊ����ҩ����յ�λ��������;��
			butChoose(2, entity);
		}
		
		if (mSubCheckBox.isChecked()) {//������ҽ��
			setSubOrders(entity);
		}
		String sql=ServerDao.getInsertOrders(entity);
		new InsertDcAdviceTask(getActivity(), sql).execute();
		
		int isDrug=0; 
		if (!mDrugBut.isChecked()) {
			isDrug=1;
		}
		new PriceTask(getActivity(), entity, isDrug).execute();
	}
	
	/**
	 * 
	* @Title: butChoose 
	* @Description: TODO(����ѡ�еĲ�ͬ���ͣ���������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void butChoose(int id,DcAdviceEntity entity){
		switch (id) {
		case 0:
			entity.frequency = "";
			entity.freq_counter = "1";
			entity.freq_interval = "1";
			entity.freq_interval_units = "��";
			entity.perform_schedule = "";
			break;
		case 1:
			entity.dosage = "";
			entity.dosage_units = "";
			entity.administration = "";
			entity.frequency = "";
			entity.freq_counter = "1";
			entity.freq_interval = "1";
			entity.freq_interval_units = "��";
			entity.perform_schedule = "";
			break;
		case 2:
			entity.dosage = "";
			entity.dosage_units = "";
			entity.administration = "";
			break;
		default:
			break;
		}
	}
	
	
	/**
	 * 
	* @Title: setSubOrders 
	* @Description: TODO(���Ϊ��ҽ����������������Ϣ) 
	* @param @param entity    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void setSubOrders(DcAdviceEntity entity){
		entity.start_date_time=subEntity.start_date_time;
		entity.administration=subEntity.administration;
		entity.frequency=subEntity.frequency;
		entity.freq_counter=subEntity.freq_counter;
		entity.freq_interval=subEntity.freq_interval;
		entity.freq_interval_units=subEntity.freq_interval_units;
		entity.repeat_indicator=subEntity.repeat_indicator;
		entity.order_no=subEntity.order_no;
		int order_sub_no=Integer.parseInt(subEntity.order_sub_no)+1;
		entity.order_sub_no=String.valueOf(order_sub_no);
		entity.perform_schedule=subEntity.perform_schedule;
		entity.ordering_dept=subEntity.ordering_dept;
		entity.drug_billing_attr=entity.drug_billing_attr;
		entity.enter_date_time=subEntity.enter_date_time;
	}
	/**
	 * 
	* @Title: validate 
	* @Description: TODO(�Ƿ��б�����Ϣ) 
	* @param @return    �趨�ļ� 
	* @return boolean    �������� 
	* @throws
	 */
	public boolean validate(){
		if (mDcAdViceTev.getText().toString().equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		String administration="";//;��
		String frequency="";//Ƶ��
		// TODO Auto-generated method stub
		switch (parent.getId()) {
		case R.id.add_dcadvice_spinner_administration:
			administration=parent.getItemAtPosition(position).toString();
			frequency=mFreqSpinner.getItemAtPosition(mFreqSpinner.getSelectedItemPosition()).toString();
			new TimeTask(mTimeTev,null).execute(administration,frequency);
			break;
		case R.id.add_dcadvice_spinner_frequency:
			frequency=parent.getItemAtPosition(position).toString();
			administration=mWaySpinner.getItemAtPosition(mWaySpinner.getSelectedItemPosition()).toString();
			new TimeTask(mTimeTev,null).execute(administration,frequency);
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
	* @ClassName: TimeTask 
	* @Description: TODO(ͨ��;����Ƶ�λ�ȡִ��ʱ������) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-25 ����8:37:55 
	*
	 */
	public static class TimeTask extends AsyncTask<String, Void, String>{
		private TextView mTextView;
		private TimeTaskCallback mTaskCallback;
		
		public TimeTask(TextView textView,TimeTaskCallback taskCallback){
			this.mTextView=textView;
			this.mTaskCallback=taskCallback;
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			if (params[0].equals("")) {
				params[0]=" is NULL";
			}
			String sqlStr = "select default_schedule from perform_default_schedule where freq_desc='"
					+ params[1] + "' and administration='" + params[0] + "'";
			String time="";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sqlStr);
			for (int i = 0; i < dataList.size(); i++) {
				time=dataList.get(i).get("default_schedule").trim();
			}
			return time;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if (mTextView!=null) {
				mTextView.setText(result);
			}else {
				mTaskCallback.callback(result);
			}
		}
	}
	/**
	 * 
	* @ClassName: TimeTaskCallback 
	* @Description: TODO(��ȡִ��ʱ��ص�����) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-2 ����10:40:14 
	*
	 */
	public interface TimeTaskCallback{
		void callback(String result);
	}
}
