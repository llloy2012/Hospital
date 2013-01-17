package com.android.hospital.ui.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.CheckAdapter;
import com.android.hospital.adapter.DcAdviceAdapter;
import com.android.hospital.adapter.InspectionAdapter;
import com.android.hospital.adapter.PrescriptionAdapter;
import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.asyntask.CheckTask;
import com.android.hospital.asyntask.DcAdviceTask;
import com.android.hospital.asyntask.DepartmentTask;
import com.android.hospital.asyntask.BaseAsyncTask.AsyncTaskCallback;
import com.android.hospital.asyntask.add.DrugOrNonDrugTask;
import com.android.hospital.asyntask.InspectionTask;
import com.android.hospital.asyntask.PrescriptionTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.ui.fragment.CheckFragment;
import com.android.hospital.ui.fragment.DoctorAdviceFragment;
import com.android.hospital.ui.fragment.InspectionFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.PrescriptionFragment;
import com.android.hospital.ui.fragment.SignsLifeFragment;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.util.Util;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.ActionBar.OnNavigationListener;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
* @ClassName: MainActivity 
* @Description: TODO(����������) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:36:57 
*
 */
public class MainActivity extends Activity implements AsyncTaskCallback<PatientEntity>{	
	private LeftListFragment leftFm;
	private Spinner mSpinner;
	private TextView titleTev;
	private ActionBar actionBar;
	private List<AsyncTask> asyncTasks=null;
	private PatientEntity patientEntity;
	private HospitalApp app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app=(HospitalApp) getApplication();
		actionBar=getActionBar();
		actionBar.setHomeButtonEnabled(true);//���ÿɵ��
		actionBar.setLogo(R.drawable.logo_new);
		actionBar.setDisplayShowTitleEnabled(false);
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new LeftListFragment();		
			ft.add(R.id.main_left_panel, leftFm ,"leftfm");
		}
		ft.commit();	
		asyncTasks=new ArrayList<AsyncTask>();
		mSpinner=(Spinner) findViewById(R.id.main_left_department_spinner);
		titleTev=(TextView) findViewById(R.id.main_patient_info_tev);
		setSpinner();
		//setTitleTev();
		addTabFragment();
	}
	public void setSpinner(){
		String sql=ServerDao.getQuery("staff_group_dict", 
				                       new String[]{"group_name","group_code"}, 
				                       new String[]{"group_class"}, 
				                       new String[]{"����ҽ��"});
		new DepartmentTask(this, sql).execute();
	}
	public void setTitleTev(PatientEntity patientEntity){
		titleTev.setText("���ţ�"+patientEntity.bed_no+" ������"+patientEntity.name+" �Ա�"+patientEntity.sex
				       +" ���䣺"+String.valueOf(Util.userBirthdayGetAge(patientEntity.date_of_birth))
				       +" ����ID��"+patientEntity.patient_id+" ʣ��Ԥ����"+patientEntity.prepayments+" �ѱ�"+patientEntity.charge_type);
	}	
	public void addTabFragment(){
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.addTab(actionBar.newTab()
				     .setText("ҽ��")
				     .setTabListener(new TabListener<DoctorAdviceFragment>(
		                        this, "dcadvice", DoctorAdviceFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("���")
			     .setTabListener(new TabListener<CheckFragment>(
	                        this, "check", CheckFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("����")
			     .setTabListener(new TabListener<InspectionFragment>(
	                        this, "inspection", InspectionFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("����")
			     .setTabListener(new TabListener<PrescriptionFragment>(
	                        this, "prescription", PrescriptionFragment.class)));
		actionBar.addTab(actionBar.newTab()
			     .setText("��������")
			     .setTabListener(new TabListener<SignsLifeFragment>(
	                        this, "signslife", SignsLifeFragment.class)));
	}
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {
        private final Activity mActivity;
        private final String mTag;
        private final Class<T> mClass;
        private final Bundle mArgs;
        private Fragment mFragment;
        public TabListener(Activity activity, String tag, Class<T> clz) {
            this(activity, tag, clz, null);
        }
        public TabListener(Activity activity, String tag, Class<T> clz, Bundle args) {
            mActivity = activity;
            mTag = tag;
            mClass = clz;
            mArgs = args;
        
            mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
            mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
        	FragmentTransaction ft=mActivity.getFragmentManager().beginTransaction();
        	ft.add(R.id.main_right_frame, mFragment, mTag);
            if (mFragment != null && !mFragment.isDetached()) {
                ft.detach(mFragment);
                ft.commit();
                DebugUtil.debug("TabListener--if--������");
            }
            DebugUtil.debug("TabListener������");
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            if (mFragment == null) {
                mFragment = Fragment.instantiate(mActivity, mClass.getName(), mArgs);
                ft.add(R.id.main_right_frame, mFragment, mTag);
            } else {
                ft.attach(mFragment);
            }
            DebugUtil.debug("onTabSelected������");
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            if (mFragment != null) {
                ft.detach(mFragment);
            }
            DebugUtil.debug("onTabUnselected������");
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(mActivity, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }


	@Override
	public void getSingle(PatientEntity value) {
		// TODO Auto-generated method stub
		DebugUtil.debug("����getSingle"+value.name);
		this.patientEntity=value;
		setTitleTev(value);
		cancleAsyncTask();
		putDcAdviceTask(value,"");
		putCheckTask(value,"");
		putInspectionTask(value,"");
		putPrescriptionTask(value,"");
	}

	@Override
	public void getList(ArrayList<PatientEntity> values) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * 
	* @Title: putDcAdviceTask 
	* @Description: TODO(ҽ������) 
	* @param @param value    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void putDcAdviceTask(PatientEntity value,String query){
		DebugUtil.debug("����put");
		DoctorAdviceFragment fragment=(DoctorAdviceFragment) getFragmentManager().findFragmentByTag("dcadvice");
		if (fragment!=null) {
			DcAdviceAdapter adapter=(DcAdviceAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
				if (fragment.isAdded()) {
					fragment.setListShown(false);
					fragment.setEmptyText("");					
				}				
			}				
			String tableName="orders";
			String[] paramArray1=new String[]{"repeat_indicator","TO_CHAR(start_date_time,'yyyy-MM-dd hh24:mi:ss') as start_date_time",
					                          "order_text","dosage","dosage_units","administration","frequency","perform_schedule",
					                          "TO_CHAR(stop_date_time,'yyyy-MM-dd hh24:mi:ss') as stop_date_time","freq_detail","doctor",
					                          "order_no","freq_counter","freq_interval","freq_interval_unit","order_status","ordering_dept",
					                          "order_sub_no","order_code","order_class","drug_billing_attr"};
			String[] whereArr=new String[]{"patient_id","visit_id"};
			String[] paramArray2=new String[]{value.patient_id,value.visit_id};
			String sql=ServerDao.getQuery(tableName, paramArray1, whereArr, paramArray2);
			sql=sql+query;
			putAsyncTask(new DcAdviceTask(fragment, sql).execute());
		}
		
	}
	/**
	 * 
	* @Title: putCheckTask 
	* @Description: TODO(���) 
	* @param @param value    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void putCheckTask(PatientEntity value,String query){
		CheckFragment fragment=(CheckFragment) getFragmentManager().findFragmentByTag("check");
		if (fragment!=null) {
            fragment.clearAdapter();
			String tableName="exam_master a,exam_report b,exam_items c";
			String[] paramArray1=new String[]{"a.exam_no","a.patient_id","a.visit_id","a.name","a.sex",
					                          "a.date_of_birth","c.exam_item","a.exam_class","a.exam_sub_class",
					                          "TO_CHAR(a.req_date_time,'yyyy-MM-dd hh24:mi:ss') as req_date_time",
					                          "a.req_physician","TO_CHAR(a.report_date_time,'yyyy-MM-dd hh24:mi:ss') as report_date_time",
					                          "a.reporter","b.description","b.impression"};
			String customWhere="where a.exam_no = b.exam_no and a.exam_no = c.exam_no and a.patient_id = '"+value.patient_id+"' "
					           +"and a.visit_id = '"+value.visit_id+"'";
			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			sql=sql+query;
			putAsyncTask(new CheckTask(fragment, sql).execute());
		}
		
	}
	/**
	 * 
	* @Title: putInspectionTask 
	* @Description: TODO(��������) 
	* @param @param value    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void putInspectionTask(PatientEntity value,String query){
		InspectionFragment fragment=(InspectionFragment) getFragmentManager().findFragmentByTag("inspection");
		if(fragment!=null){
			InspectionAdapter adapter=(InspectionAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
				if (fragment.isAdded()) {
					fragment.setListShown(false);
				}				
			}			
			String tableName="LAB_TEST_ITEMS ,LAB_TEST_MASTER ,DEPT_DICT,STAFF_DICT";
			String[] paramArray1=new String[]{"LAB_TEST_ITEMS.ITEM_NO","LAB_TEST_ITEMS.ITEM_NAME","LAB_TEST_MASTER.SPECIMEN","LAB_TEST_ITEMS.ITEM_CODE","LAB_TEST_ITEMS.TEST_NO",
					                          "DEPT_DICT.DEPT_NAME","LAB_TEST_MASTER.RESULT_STATUS","TO_CHAR(LAB_TEST_MASTER.REQUESTED_DATE_TIME,'yyyy-MM-dd hh24:mi:ss') as REQUESTED_DATE_TIME",
					                          "LAB_TEST_MASTER.BILLING_INDICATOR","LAB_TEST_MASTER.PRIORITY_INDICATOR","LAB_TEST_MASTER.CHARGE_TYPE","LAB_TEST_MASTER.NOTES_FOR_SPCM",
					                          "LAB_TEST_MASTER.PERFORMED_BY","LAB_TEST_MASTER.RELEVANT_CLINIC_DIAG","LAB_TEST_MASTER.NAME","LAB_TEST_MASTER.SEX","LAB_TEST_MASTER.AGE",
					                          "LAB_TEST_MASTER.ORDERING_DEPT","LAB_TEST_MASTER.PATIENT_ID","STAFF_DICT.NAME as ORDERING_PROVIDER  "};
			String customWhere="WHERE (LAB_TEST_MASTER.TEST_NO= LAB_TEST_ITEMS.TEST_NO ) and "
					+"( LAB_TEST_MASTER.PERFORMED_BY = DEPT_DICT.DEPT_CODE ) and "
					+"( LAB_TEST_MASTER.PATIENT_ID = '"+value.patient_id +"' ) and "
					+"(LAB_TEST_MASTER.VISIT_ID = '"+value.visit_id+"' ) and  "
					+" STAFF_DICT.USER_NAME = LAB_TEST_MASTER.ORDERING_PROVIDER  ";
					//+"order by LAB_TEST_ITEMS.TEST_NO,LAB_TEST_ITEMS.ITEM_NO  ";
			String orderby = "order by LAB_TEST_ITEMS.TEST_NO,LAB_TEST_ITEMS.ITEM_NO  ";
			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			sql=sql+query+orderby;
			Log.e("�������------->", sql);
			putAsyncTask(new InspectionTask(fragment, sql).execute());
		}	
	}
	/**
	 * 
	* @Title: putPrescriptionTask 
	* @Description: TODO(����������) 
	* @param @param value    �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void putPrescriptionTask(PatientEntity value,String query){
		PrescriptionFragment fragment=(PrescriptionFragment) getFragmentManager().findFragmentByTag("prescription");
		if (fragment!=null) {
			PrescriptionAdapter adapter=(PrescriptionAdapter) fragment.getListAdapter();
			if (null!=adapter&&adapter.getCount()!=0) {
				adapter.clearAdapter();
				if (fragment.isAdded()) {
					fragment.setListShown(false);
				}				
			}			
			String tableName="DOCT_DRUG_PRESC_MASTER, dept_dict";
			String[] paramArray1=new String[]{"DOCT_DRUG_PRESC_MASTER.PRESC_NO","TO_CHAR(DOCT_DRUG_PRESC_MASTER.PRESC_DATE,'yyyy-MM-dd hh24:mi:ss') as PRESC_DATE",
					                          "DOCT_DRUG_PRESC_MASTER.PRESCRIBED_BY","DEPT_DICT.DEPT_NAME","DOCT_DRUG_PRESC_MASTER.PRESC_TYPE","DOCT_DRUG_PRESC_MASTER.REPETITION",
					                          "DOCT_DRUG_PRESC_MASTER.COSTS","DOCT_DRUG_PRESC_MASTER.PRESC_STATUS"};
			String customWhere="WHERE DOCT_DRUG_PRESC_MASTER.Dispensary = dept_dict.dept_code  and (PRESC_STATUS not in (2, 3))  AND (DOCT_DRUG_PRESC_MASTER.PATIENT_ID = '"+value.patient_id+"') "
					           +"AND (DOCT_DRUG_PRESC_MASTER.COSTS >= 0) ";
			String orderby= "order by PRESC_DATE";
			String sql=ServerDao.getQueryCustom(tableName, paramArray1, customWhere);
			sql=sql+query+orderby;
			putAsyncTask(new PrescriptionTask(fragment,sql).execute());
		}
	}
	/**
	 * 
	* @Title: putAsyncTask 
	* @Description: TODO(�첽���񼯺�) 
	* @param @param paramAsyncTask
	* @param @return    �趨�ļ� 
	* @return AsyncTask    �������� 
	* @throws
	 */
	protected AsyncTask putAsyncTask(AsyncTask paramAsyncTask){
		asyncTasks.add(paramAsyncTask);
		return paramAsyncTask;
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		cancleAsyncTask();
	}
	/**
	 * 
	* @Title: cancleAsyncTask 
	* @Description: TODO(ȡ���첽����) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void cancleAsyncTask(){
		for (int i = 0; i < asyncTasks.size(); i++) {
			AsyncTask localAsyncTask=(AsyncTask) asyncTasks.get(i);
			if (!(localAsyncTask == null) || (localAsyncTask.isCancelled())) {
				localAsyncTask.cancel(true);
				DebugUtil.debug("ȡ��ִ�м���-->"+i);
			}
		}
		asyncTasks.clear();
		//����һ�ַ�ʽ
		/*Iterator localIterator=this.asyncTasks.iterator();
		if (!localIterator.hasNext()) {
			return;
		}
		AsyncTask localAsyncTask = (AsyncTask)localIterator.next();
		if ((localAsyncTask == null) || (localAsyncTask.isCancelled()))
	        continue;
	    localAsyncTask.cancel(true);*/
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			logout();
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}
	
	// ע��ϵͳ
	private void logout() {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// .setIcon(R.);
			builder.setMessage("���Ҫ�˳�ϵͳ��").setCancelable(false)
					.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							finish();
						}
					})
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert = builder.create();
			alert.show();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.addSubMenu(1, Menu.FIRST, 1, "����ҽ��").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+4, 1, "�ײ�ҽ��").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+1, 1, "�������").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+2, 1, "��������").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+3, 1, "��������").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+5, 1, "������ѯ").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, Menu.FIRST+6, 1, "����ԤԼ").setIcon(android.R.drawable.ic_menu_add);
		menu.addSubMenu(1, 14, 1, "��ʱ���ѯ");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		Intent intent;
		DebugUtil.debug("idΪ"+item.getItemId());
		switch (item.getItemId()) {
		case Menu.FIRST:
				intent=new Intent();
				DoctorAdviceFragment fragment=(DoctorAdviceFragment) this.getFragmentManager().findFragmentByTag("dcadvice");
				DcAdviceAdapter adapter=(DcAdviceAdapter) fragment.getListAdapter();		
				DcAdviceEntity entity=(DcAdviceEntity) adapter.getItem(adapter.getCount()-1);
				intent.putExtra("subentity", entity);
				intent.setClass(this, AddDcAdviceActivity.class);
				startActivityForResult(intent, 11);	
			break;
		case Menu.FIRST+1:

				intent=new Intent();
				intent.setClass(this, AddCheckActivity.class);
				startActivityForResult(intent, 12);
			break;
		case Menu.FIRST+2:
			
				intent=new Intent();
				intent.setClass(this, AddInspectionActivity.class);
				startActivityForResult(intent, 13);
				
			break;
		case Menu.FIRST+3:
				intent=new Intent();
				intent.setClass(this, AddPrescriptionActivity.class);
				startActivityForResult(intent, 14);
			break;
		case Menu.FIRST+4:
            showGroupDc();
			break;
		case Menu.FIRST+5:
			    intent=new Intent();
				intent.setClass(this, OperationActivity.class);
				startActivity(intent);
			break;
		case Menu.FIRST+6:
			Toast.makeText(MainActivity.this, "������δ���!", Toast.LENGTH_SHORT).show();
			break;
		case 11:
			DoctorAdviceFragment fragmentall=(DoctorAdviceFragment) this.getFragmentManager().findFragmentByTag("dcadvice");
			DcAdviceAdapter adapterall=(DcAdviceAdapter) fragmentall.getListAdapter();
			adapterall.setAllList();
			break;
		case 12:
			DoctorAdviceFragment fragmentlong=(DoctorAdviceFragment) this.getFragmentManager().findFragmentByTag("dcadvice");
			DcAdviceAdapter adapterlong=(DcAdviceAdapter) fragmentlong.getListAdapter();
			adapterlong.setLongList();
			break;
		case 13:
			DoctorAdviceFragment fragmentshort=(DoctorAdviceFragment) this.getFragmentManager().findFragmentByTag("dcadvice");
			DcAdviceAdapter adaptershort=(DcAdviceAdapter) fragmentshort.getListAdapter();
			adaptershort.setShortList();
			break;
		case 14:
			showDatePickerDialog();		
			break;
		default:
			break;
		}
		return true;
	}
    
	
	private int whichButtonChoose=0;
	
	/**
	 * 
	* @Title: showGroupDc 
	* @Description: TODO(�����ײ�ҽ��ѡ���) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void showGroupDc(){
		new AlertDialog.Builder(this)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("ҽ��ģ��")
        .setSingleChoiceItems(getArrayItem(), 0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked on a radio button do some stuff */
            	whichButtonChoose=whichButton;
            }
        })
        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked Yes so do some stuff */
            	DebugUtil.debug("positon--->"+whichButtonChoose);
            	
            		String id=app.getGroupOrderList().get(whichButtonChoose).group_order_id;
                	Intent intent=new Intent();
                	intent.putExtra("id", id);
                	intent.setClass(MainActivity.this, GroupDcAdviceActivity.class);
                	startActivityForResult(intent, 11);
                	whichButtonChoose=0;//���������Ϊ��
            	
            }
        })
        .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked No so do some stuff */
            }
        })
       .create().show();
	}
	
	/**
	 * 
	* @Title: getArrayItem 
	* @Description: TODO(�õ�ģ������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public String[] getArrayItem(){
		int size=app.getGroupOrderList().size();
		String[] arr=new String[size];
		for (int i = 0; i < size; i++) {
			arr[i]=app.getGroupOrderList().get(i).title;
		}
		return arr;
	}
	/*private void addCustomView() {
	// TODO Auto-generated method stub
	View mCustomView = getLayoutInflater().inflate(
			R.layout.app_logo, null);
	actionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(
			LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	actionBar.setDisplayShowCustomEnabled(true);
	actionBar.setDisplayShowTitleEnabled(false);
}*/
	
	/*public void addBarSpinner(){
	actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	SpinnerAdapter adapter=ArrayAdapter.createFromResource(this, R.array.array1, android.R.layout.simple_spinner_dropdown_item);
	actionBar.setListNavigationCallbacks(adapter, new OnNavigationListener() {
		
		@Override
		public boolean onNavigationItemSelected(int itemPosition, long itemId) {
			// TODO Auto-generated method stub
			return false;
		}
	});
}*/
	
    // date and time
    private Button startBut;
    private Button endBut;
	/**
	 * 
	* @Title: showDatePickerDialog 
	* @Description: TODO(��ʾʱ���ѯ�Ի���) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	public void showDatePickerDialog(){
		final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
		LayoutInflater factory = LayoutInflater.from(this);
	    View textEntryView = factory.inflate(R.layout.common_query_timedialog, null);
	    startBut=(Button) textEntryView.findViewById(R.id.start_time_but);
	    endBut=(Button) textEntryView.findViewById(R.id.end_time_but);
	    startBut.setText(new StringBuilder().append(mYear).append("-")
                // Month is 0 based so add 1
                .append(Util.toQueryTime(mMonth + 1)).append("-")
                .append(Util.toQueryTime(mDay)));
	    endBut.setText(new StringBuilder().append(mYear).append("-")
                // Month is 0 based so add 1
	    		.append(Util.toQueryTime(mMonth + 1)).append("-")
                .append(Util.toQueryTime(mDay)));
	    startBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(MainActivity.this, mStartDateSetListener, mYear, mMonth, mDay).show();
			}
		});
	    endBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new DatePickerDialog(MainActivity.this, mEndDateSetListener, mYear, mMonth, mDay).show();
			}
		});
	    new AlertDialog.Builder(this)
        .setIconAttribute(android.R.attr.alertDialogIcon)
        .setTitle("��ʱ���ѯ")
        .setView(textEntryView)
        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked OK so do some stuff */
            	timeQueryTask();
            }
        })
        .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                /* User clicked cancel so do some stuff */
            }
        }).create().show();
	}
	
	private DatePickerDialog.OnDateSetListener mStartDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear,
                        int dayOfMonth) {
                	startBut.setText(
                            new StringBuilder().append(year).append("-")
                                    // Month is 0 based so add 1
                                    .append(Util.toQueryTime(monthOfYear + 1)).append("-")
                                    .append(Util.toQueryTime(dayOfMonth))
                                    );
                }
            };
    private DatePickerDialog.OnDateSetListener mEndDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {

                        public void onDateSet(DatePicker view, int year, int monthOfYear,
                                int dayOfMonth) {
                        	endBut.setText(
                                    new StringBuilder().append(year).append("-")
                                            // Month is 0 based so add 1
                                    .append(Util.toQueryTime(monthOfYear + 1)).append("-")
                                    .append(Util.toQueryTime(dayOfMonth))
                                    );
                        }
                    };
	/**
	 * 
	* @Title: timeQueryTask 
	* @Description: TODO(����ʱ���ѯ) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
    private void timeQueryTask(){
    	
    	this.patientEntity=app.getPatientEntity();
    	
    	//ҽ��----��ʼִ������
		String dcAdviceSql = " and TO_CHAR(start_date_time,'yyyy-MM-dd')>='"
				+ startBut.getText().toString()
				+ "' and TO_CHAR(start_date_time,'yyyy-MM-dd')<='"
				+ endBut.getText().toString() + "' ";
		//��� ----��Ŀ��������
		String checkSql=  " and TO_CHAR(req_date_time,'yyyy-MM-dd')>='"
				+ startBut.getText().toString()
				+ "' and TO_CHAR(req_date_time,'yyyy-MM-dd')<='"
				+ endBut.getText().toString() + "' ";
		//����----��������
		String inspectionSql = " and TO_CHAR(requested_date_time,'yyyy-MM-dd')>='"
				+ startBut.getText().toString()
				+ "' and TO_CHAR(requested_date_time,'yyyy-MM-dd')<='"
				+ endBut.getText().toString() + "' ";
		//����----��������
		String prescriptionSql = " and TO_CHAR(presc_date,'yyyy-MM-dd')>='"
				+ startBut.getText().toString()
				+ "' and TO_CHAR(presc_date,'yyyy-MM-dd')<='"
				+ endBut.getText().toString() + "' ";
		
								
		switch (getActionBar().getSelectedNavigationIndex()) {
		case 0:
			putDcAdviceTask(patientEntity, dcAdviceSql);
			DebugUtil.debug("actionbar--->id00000");
			break;
		case 1:
			putCheckTask(patientEntity, checkSql);
			DebugUtil.debug("actionbar--->id111111");
			break;
		case 2:
			putInspectionTask(patientEntity, inspectionSql);
			DebugUtil.debug("actionbar--->id222222");
			break;
		case 3:
			putPrescriptionTask(patientEntity, prescriptionSql);
			DebugUtil.debug("actionbar--->id333333");
			break;
		default:
			break;
		}
    }           
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	// TODO Auto-generated method stub
    	switch (resultCode) {
		case 11:
			putDcAdviceTask(app.getPatientEntity(),"");
			break;
		case 12:
			putCheckTask(app.getPatientEntity(),"");
			putDcAdviceTask(app.getPatientEntity(),"");
			break;
		case 13:
			putInspectionTask(app.getPatientEntity(),"");
			putDcAdviceTask(app.getPatientEntity(),"");
			break;
		case 14:
			putPrescriptionTask(app.getPatientEntity(),"");
			putDcAdviceTask(app.getPatientEntity(),"");
			break;
		case 16:
			//putDcAdviceTask(app.getPatientEntity(),"");
			break;
		default:
			break;
		}   	
    	super.onActivityResult(requestCode, resultCode, data);
    }
}
