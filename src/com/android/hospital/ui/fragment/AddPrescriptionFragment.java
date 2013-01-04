package com.android.hospital.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.PrescriptionLeftItemAdapter;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DrugEntity;
import com.android.hospital.ui.activity.R;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.util.Util;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
* @ClassName: AddInspectionFragment 
* @Description: TODO(������������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-21 ����11:54:54 
*
 */
public class AddPrescriptionFragment extends BaseFragment implements OnClickListener,OnItemSelectedListener{
    private View view;
	private Spinner mClassSp;
	private CheckBox mTaskDrugBox,mIsOrNotBox;//��Ժ��ҩ,�Ƿ����
	private TextView mDrugHouseTev,mNextvalTev,mTempJudgeTev,mAgentNumTev,mEachNumTev;//ҩ�֣����������,������ÿ����
	private Button mAgentPlusBut,mAgentMinusBut,mEachPlusBut,mEachMinusBut;//�Ӽ�
	private EditText mHowEdit,mNextvalEdit;//�÷���������
	private ListView mListView;
	private LinearLayout mHideLayout;
	private HospitalApp app;
	private ArrayList<DrugEntity> mWestDrugList;//��ҩ
	private SeachPrescriptionFragment fm;//��������
	private PrescriptionLeftItemAdapter mAdapter;
	private int drugFlag=0;//0Ϊ��ҩ��1Ϊ��ҩ
	private ArrayList<DrugEntity> insertList;//��Ҫ����ļ��� 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		app=(HospitalApp) getActivity().getApplication();
		fm=(SeachPrescriptionFragment) getActivity().getFragmentManager().findFragmentByTag("searchfm");
		new WestDrugTaskStep1().execute();//��ȡ��ҩ����
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view=inflater.inflate(R.layout.fragment_add_prescription_left, null);
		init();
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mAdapter=new PrescriptionLeftItemAdapter(getActivity(), null);
		mListView.setAdapter(mAdapter);
		
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
		mHideLayout=(LinearLayout) view.findViewById(R.id.prescription_hidelayout);
		
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
		if (mClassSp.getSelectedItemPosition()==0) {
			if (mNextvalEdit.getText().toString().equals("")) {
				Toast.makeText(getActivity(), "����������Ϊ�գ�", Toast.LENGTH_SHORT).show();
				return false;
			}
			if (numNull()) {
				return true;
			}else {
				Toast.makeText(getActivity(), "��������������Ϊ�գ�", Toast.LENGTH_SHORT).show();
				return false;
			}
		}else {
			return true;
		}
	}

	@Override
	public void onOtherFragmentClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		prescriptionInsertSql();
	}

	public void setListView(DrugEntity entity){
		if (mAdapter.getCount()==1) {
			drugFlag=mClassSp.getSelectedItemPosition();//
		}
		if (mAdapter.getCount()>1) {
			if (drugFlag!=mClassSp.getSelectedItemId()) {
				Toast.makeText(getActivity(), "��ҩ����ҩ���ܿ���һ�ŵ�����!!", Toast.LENGTH_SHORT).show();
				return;
			}			
		}
		if (!mDrugHouseTev.getText().equals("")) {
			if (!entity.storage_name.equals(mDrugHouseTev.getText().toString())) {
				Toast.makeText(getActivity(), "ҩ����ͬ���ܿ���һ�ŵ�����!!", Toast.LENGTH_SHORT).show();
				return;
			}
		}
		mDrugHouseTev.setText(entity.storage_name);
		mAdapter.addItem(entity, mClassSp.getSelectedItemPosition());
		
	}
	
	/**
	 * 
	* @Title: clear 
	* @Description: TODO(��հ�ť) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void clear(){
		mAdapter.clear();
		mClassSp.setSelection(0);
		mDrugHouseTev.setText("");
		drugFlag=0;		
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
			mHideLayout.setVisibility(View.VISIBLE);
			break;
		case 1:
			DebugUtil.debug("positon-case--1");
			if (mWestDrugList!=null) {
				fm.showWestDrug(mWestDrugList);
			}
			mHideLayout.setVisibility(View.GONE);
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
	* @Description: TODO(��ȡ��ҩ����) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-26 ����5:03:29 
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
					+ " and ((start_date_time < sysdate and start_date_time >sysdate-1) or ( repeat_indicator = '1' and administration in( '�ڷ�','������')))";
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
			mNextvalTev.setText(app.getNextval());//������
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
	
	/**
	 * 
	* @Title: prescriptionInsertSql 
	* @Description: TODO(�������뷽��) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void prescriptionInsertSql(){
		String start_date_time=Util.toSimpleDate();
		String presc_type="0";
		String dispensary="3103";
		String decoction="";
		String presc_no=mNextvalTev.getText().toString();
		String repetition=mAgentNumTev.getText().toString();
		String count_per_repetition=mEachNumTev.getText().toString();
		String usage=mHowEdit.getText().toString();
		String binding_presc_title=mNextvalEdit.getText().toString();
		String discharge_taking_indicator="";
		if (mTaskDrugBox.isChecked()) {
			discharge_taking_indicator="1";
		}
		if (mClassSp.getSelectedItemPosition()==0) {//���Ϊ��ҩ
			if (binding_presc_title.equals("")) {
				return;
			}
			dispensary="3102";
			presc_type="1";
			if (mIsOrNotBox.isChecked()) {
				decoction="1";
			}
		}else {
			repetition="1";
			count_per_repetition="";
			usage="";
			binding_presc_title="";
		}		
		String costs=account();//����۸񷽷�
		
		StringBuffer prescriptionBuffer=new StringBuffer();
		prescriptionBuffer.append("insert into  doct_drug_presc_master"+
				"(patient_id,"+ //  ����id
				"visit_id,"+  //   סԺ����
				"name,"+       //  ����
				"name_phonetic,"+//����ƴ���� 
				"presc_date,"+  // �������ڣ�ϵͳʱ�䣩
				"identity,"+    // ��ݣ�ũ�񣬹��˵ȣ�
				"charge_type,"+ // �ѱ�
				"ordered_by,"+  // �������ң��������ڵĿ��Ҵ��룩
				"prescribed_by,"+ //ִ���˴���  ������ҽ������¼ҽ�������֣�-------
				"presc_attr, "+ // �������ԣ���գ�--------------
				"dispensary,"+  // ��ҩҩ�֣�ҩ�ִ��룩
				"presc_source,"+ //������Դ�����סԺ��סԺΪ1-------
				"unit_in_contract,"+ //��ͬ��λ����գ�
				"presc_no,"+    //������
				"presc_type,"+  //�������� 0��ҩ 1��ҩ 
				"repetition,"+  // ���� ��������ҩ���ԣ������仯��һ��Ϊ1����ҩΪ1
				"costs, "+      // �Ƽ۽����������㣨�ܻ��ѣ�
				"payments, "+   // Ӧ�ս����������㣨ͬ�ϣ�
				"entered_by,"+ // ȷ���˴��루����ҽ������¼ҽ�������֣�-------
				"presc_status,"+// ����״̬ ��Ϊ0������ҩ��Ϊ1���˴�Ϊ0��
				"dispensing_provider,"+ // ȷ�������� ���գ�--------
				"count_per_repetition,"+// ÿ����������ҩ��������Ϊ�գ�
				"usage,"+       //�÷�����ҩ��
				"binding_presc_title,"+ //����������ҩ��
				"discharge_taking_indicator,"+ //��Ժ��ҩ��־��1Ϊ��Ժ��ҩ��û��Ϊ�գ�
				"doctor_user,"+ //ҽ������ ���ò��˵�����ҽ�����룩
				"decoction,"+   //�Ƿ���壨��ҩ����û��Ϊ�գ�����Ϊ1��
				"newly_print,"+ //�Ƿ��ش�û��Ϊ�գ�
				"diagnosis_name)"+ //�������"
			"values(");
		prescriptionBuffer.append("'"+app.getPatientEntity().patient_id).append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().visit_id).append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().name).append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().name_phonetic).append("',");
		prescriptionBuffer.append("TO_DATE('"+start_date_time).append("','yyyy-MM-dd hh24:mi:ss'),");
		prescriptionBuffer.append("'"+app.getPatientEntity().identity).append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().charge_type).append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().dept_code).append("',");
		prescriptionBuffer.append("'"+app.getDoctor()).append("',");
		prescriptionBuffer.append("'"+"").append("',");
		prescriptionBuffer.append("'"+dispensary).append("',");
		prescriptionBuffer.append("'"+"1").append("',");
		prescriptionBuffer.append("'"+"").append("',");
		prescriptionBuffer.append("'"+presc_no).append("',");
		prescriptionBuffer.append("'"+presc_type).append("',");
		prescriptionBuffer.append("'"+repetition).append("',");
		prescriptionBuffer.append("'"+costs).append("',");
		prescriptionBuffer.append("'"+costs).append("',");
		prescriptionBuffer.append("'"+app.getDoctor()).append("',");
		prescriptionBuffer.append("'"+"0").append("',");
		prescriptionBuffer.append("'"+"").append("',");
		prescriptionBuffer.append("'"+count_per_repetition).append("',");
		prescriptionBuffer.append("'"+usage).append("',");
		prescriptionBuffer.append("'"+binding_presc_title).append("',");
		prescriptionBuffer.append("'"+discharge_taking_indicator).append("',");
		prescriptionBuffer.append("'"+"doctor_user").append("',");//����ҽ������
		prescriptionBuffer.append("'"+decoction).append("',");
		prescriptionBuffer.append("'"+"").append("',");
		prescriptionBuffer.append("'"+app.getPatientEntity().diagnosis).append("')");
		WebServiceHelper.insertWebServiceData(prescriptionBuffer.toString());
		
		for (int i = 0; i < insertList.size(); i++) {
			int item_no=i+1;
			DrugEntity itemEntity=insertList.get(i);
			StringBuffer buffer=new StringBuffer();
			buffer.append("insert into doct_drug_presc_detail" + " (presc_date,"
					+ // �������ڣ�ϵͳʱ�䣩
					"presc_no," + // ������
					"item_no," + // ��Ŀ��ţ��Զ���ţ���1��ʼ��
					"order_no," + // ҽ����� ���գ������ҩʱ������
					"order_sub_no," + // ҽ������ţ��գ�
					"drug_code," + // ҩƷ����
					"drug_spec," + // ҩƷ���
					"drug_name," + // ҩƷ����
					"firm_id," + // ��������
					"package_spec," + // ��װ���
					"package_units," + // ��װ��λ�������ĵ�λ��
					"quantity," + // ��������������
					"costs," + // ���ã��۸񣨵�����Ŀ�ķ��ã�
					"payments," + // �Ѹ����ã�������Ŀ�ķ��ã�ͬ�ϣ�
					"administration," + // ;��
					"dosage," + // ������������*��װ���
					"dosage_units," + // ������λ�����μ����ĵ�λ��
					"amount_per_package," + // ÿ��װ�������գ�
					"frequency," + // ִ��Ƶ��
					"dosage_each," + // ���μ���
					"freq_detail," + // ҽ��˵��
					"is_basic) " + // �Ƿ�Ϊ��ҩ������Ϊ�գ���ҩ��1��
					"values(");
			buffer.append("TO_DATE('"+start_date_time).append("','yyyy-MM-dd hh24:mi:ss'),");
			buffer.append("'"+presc_no).append("',");
			buffer.append("'"+item_no).append("',");
			buffer.append("'"+"").append("',");
			buffer.append("'"+"").append("',");
			buffer.append("'"+itemEntity.drug_code).append("',");
			buffer.append("'"+itemEntity.drug_spec).append("',");
			buffer.append("'"+itemEntity.drug_name).append("',");
			buffer.append("'"+itemEntity.firm_id).append("',");
			buffer.append("'"+itemEntity.package_spec).append("',");
			buffer.append("'"+itemEntity.package_units).append("',");
			buffer.append("'"+itemEntity.quantity).append("',");
			buffer.append("'"+itemEntity.single_price).append("',");
			buffer.append("'"+itemEntity.single_price).append("',");
			buffer.append("'"+itemEntity.administration).append("',");
			buffer.append("'"+itemEntity.dosage).append("',");
			buffer.append("'"+itemEntity.dose_units).append("',");
			buffer.append("'"+"").append("',");
			buffer.append("'"+itemEntity.frequency).append("',");
			buffer.append("'"+itemEntity.dosage_each).append("',");
			buffer.append("'"+itemEntity.freq_detail).append("',");
			buffer.append("'"+itemEntity.is_basic.trim()).append("')");
			WebServiceHelper.insertWebServiceData(buffer.toString());
		}
	}
	
	/**
	 * 
	* @Title: account 
	* @Description: TODO(����۸�) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public String account(){
		insertList=new ArrayList<DrugEntity>();
		double total_price=0;//�ܼ۸�
		int size=mAdapter.getCount();
		if (size!=0) {
			for (int i = 0; i < size; i++) {
				DrugEntity itemEntity=(DrugEntity) mAdapter.getItem(i);
				StringBuffer buffer=new StringBuffer();
				buffer.append(itemEntity.dosage_each).append("/").append(itemEntity.package_units);//����/��λ
				itemEntity.freq_detail=buffer.toString();
				if (itemEntity.quantity==null) {
					itemEntity.quantity="";
				}
				if (itemEntity.quantity.equals("")||itemEntity.quantity.equals("0")) {
					itemEntity.quantity="1";
				}
				double num=Double.parseDouble(itemEntity.quantity);//����������*�۸�
				String purchase_price=itemEntity.purchase_price;
				if (null==purchase_price||purchase_price.equals("")) {
					purchase_price="1";
				}
				double num1=Double.parseDouble(purchase_price);
				double num2=num*num1;
				itemEntity.single_price=String.valueOf(num2);
				total_price+=num2;
				itemEntity.total_price=String.valueOf(total_price);
				DebugUtil.debug("����numֵ-->"+num+","+num1+","+num2+","+total_price);
				accout2(itemEntity);//��������
			}
			return String.valueOf(total_price);
		}
		return "";
	}

	/**
	 * 
	* @Title: accout2 
	* @Description: TODO(��ȡһЩ���õ�������) 
	* @param @param itemEntity    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void accout2(DrugEntity itemEntity) {
		// TODO Auto-generated method stub
		String ssString2 = "select DRUG_CODE,DRUG_SPEC,FIRM_ID,UNITS,AMOUNT_PER_PACKAGE,MIN_SPEC"
				+ " from drug_price_list "
				+ "where drug_code= '"
				+ itemEntity.drug_code
				+ "'"
				+ " and  DRUG_SPEC||FIRM_ID='"
				+ itemEntity.drug_spec + "'" + " and stop_date is null";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(ssString2);
		for (int i = 0; i < dataList.size(); i++) {
			itemEntity.firm_id=dataList.get(i).get("firm_id");
			itemEntity.package_spec=dataList.get(i).get("drug_spec");
			itemEntity.amount_per_package=dataList.get(i).get("amount_per_package");
			itemEntity.min_spec=dataList.get(i).get("min_spec");
			itemEntity.dosage=accout3(itemEntity);
		}
		insertList.add(itemEntity);
	}
	/**
	 * 
	* @Title: accout3 
	* @Description: TODO(��������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private String accout3(DrugEntity itemEntity){
		boolean arrFlag=false;
		StringBuffer numBuffer=new StringBuffer();
		StringBuffer numBuffer2=new StringBuffer();
		//DebugUtil.debug("param1-->"+param1);
		String[] numArr=itemEntity.min_spec.split("\\.");
		DebugUtil.debug("���鳤��--->"+numArr.length);
		if (numArr.length>1) {
			numBuffer.append(numArr[0]).append(".").append(getNum(numArr[1]));
		}else {
			numBuffer.append(getNum(itemEntity.min_spec));
		}  
		DebugUtil.debug("�ָ���ֵ"+numBuffer.toString());
		double i=Double.parseDouble(numBuffer.toString());
		double j=Double.parseDouble(itemEntity.amount_per_package);
		if (itemEntity.quantity.equals("")) {
			itemEntity.quantity="1";
		}
		double k=Double.parseDouble(itemEntity.quantity);
		double m=i*j*k;
		DebugUtil.debug("m��ֵ-->"+m);
		return String.valueOf(m);
	}
	/**'
	 * 
	* @Title: getNum 
	* @Description: TODO() 
	* @param @param s
	* @param @return    �趨�ļ� 
	* @return String    �������� 
	* @throws
	 */
	public String getNum(String s){
		ArrayList<String> nums = new ArrayList<String>();
		String num = "";
		boolean lastIsNum = false;
		for(int i = 0;i<s.length();i++){
			 if(s.charAt(i)<='9' && s.charAt(i)>='0'){
				   lastIsNum = true;
				   num += s.substring(i,i+1);
			 }else {
				 if (lastIsNum)
					 nums.add(num);
				 num="";
				 lastIsNum=false;
			}
		}
		int[] rs = new int[nums.size()];
		StringBuffer buffer=new StringBuffer();
		for(int i = 0;i<rs.length;i++){
			 rs[i] = Integer.parseInt((nums.get(i)));
			 buffer.append(rs[i]);
			 
			 DebugUtil.debug("�õ�������-->"+rs[i]);
		}
		DebugUtil.debug("�õ���buffer-->"+buffer.toString());
		return buffer.toString();
	}
	
	/**
	 * 
	* @Title: numNull 
	* @Description: TODO(�������������Ƿ�Ϊ��) 
	* @param @return    �趨�ļ� 
	* @return boolean    �������� 
	* @throws
	 */
	private boolean numNull(){
		int size=mAdapter.getCount();
		if (size!=0) {
			for (int i = 0; i < size; i++) {
				DrugEntity itemEntity=(DrugEntity) mAdapter.getItem(i);
				if (itemEntity.dosage_each==null||itemEntity.dosage_each.equals("")) {
					return false;
				}
				if (itemEntity.quantity==null||itemEntity.quantity.equals("")) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
