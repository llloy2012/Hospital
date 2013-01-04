package com.android.hospital.ui.activity;

import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.fragment.AddCheckFragment;
import com.android.hospital.ui.fragment.AddInspectionFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.SeachInspectionFragment;
import com.android.hospital.ui.fragment.SearchFragment;
import com.android.hospital.util.DebugUtil;
import com.android.hospital.widgets.MyProssDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 
* @ClassName: AddDcAdviceActivity 
* @Description: TODO(����������ʾ����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:37:25 
*
 */
public class AddInspectionActivity extends Activity implements OnClickListener{

	private AddInspectionFragment leftFm;
	private SearchFragment searchFm;
	private Button mCancleBut,mOkBut,mClearBut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_common);
		
		initView();
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new AddInspectionFragment();		
			ft.add(R.id.common_left_fragment, leftFm,"addfm");
		}
		if (searchFm==null) {
			searchFm=new SeachInspectionFragment();
			ft.add(R.id.common_right_fragment, searchFm,"searchfm");
		}
		ft.commit();
		new MaxNumberTask(this).execute("test_no.nextval");//��ȡ����������
	}

	private void initView(){
		mCancleBut=(Button) findViewById(R.id.common_but_cancle);
		mOkBut=(Button) findViewById(R.id.common_but_ok);
		mClearBut=(Button) findViewById(R.id.common_but_clear);
		
		mCancleBut.setOnClickListener(this);
		mOkBut.setOnClickListener(this);
		mClearBut.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.common_but_cancle:
			intent=new Intent();
			setResult(1, intent);
			finish();
			break;
		case R.id.common_but_ok:
			new AlertDialog.Builder(AddInspectionActivity.this)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle("�Ƿ�ȷ���ύ��")
            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked OK so do some stuff */
                	if (leftFm.validate()) {
                		new InsertInspectionTask().execute();
					}   
                }
            })
            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked Cancel so do some stuff */
                }
            })
            .create().show();
			break;

		case R.id.common_but_clear:
			leftFm.clear();
			break;
		}
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	/**
	 * 
	* @ClassName: InsertInspectionTask 
	* @Description: TODO(�����������) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-27 ����12:35:11 
	*
	 */
	private class InsertInspectionTask extends AsyncTask<Void, Void, String>{

        private MyProssDialog mDialog;
		
		@Override
		protected void onPreExecute() {
	        mDialog=new MyProssDialog(AddInspectionActivity.this, "�ύ", "�����ύ�������Ժ�...");
		}
		
		
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			leftFm.insert();//�ɼӸ�booleanֵ�����ж�
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mDialog.cancel();
			Intent intent=new Intent();
			setResult(13, intent);
			finish();
		}
	}
}
