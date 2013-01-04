package com.android.hospital.ui.activity;

import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.fragment.AddCheckFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.SeachCheckFragment;
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
* @Description: TODO(���������ʾ����) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:37:25 
*
 */
public class AddCheckActivity extends Activity implements OnClickListener{

	private AddCheckFragment leftFm;
	private SearchFragment searchFm;
	private Button mCancleBut,mOkBut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_common);
		initView();
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new AddCheckFragment();		
			ft.add(R.id.common_left_fragment, leftFm,"addfm");
		}
		if (searchFm==null) {
			searchFm=new SeachCheckFragment();
			ft.add(R.id.common_right_fragment, searchFm,"searchfm");
		}
		ft.commit();
		new MaxNumberTask(this).execute("exam_no_seq.nextval");//��ȡ����������
	}

	private void initView(){
		mCancleBut=(Button) findViewById(R.id.common_but_cancle);
		mOkBut=(Button) findViewById(R.id.common_but_ok);
		findViewById(R.id.common_but_clear).setVisibility(View.GONE);
		
		mCancleBut.setOnClickListener(this);
		mOkBut.setOnClickListener(this);
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
			new AlertDialog.Builder(AddCheckActivity.this)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle("�Ƿ�ȷ���ύ��")
            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked OK so do some stuff */
                	if (leftFm.validate()) {
                		new InsertCheckTask().execute("insert");
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

		default:
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
	* @ClassName: InsertCheckTask 
	* @Description: TODO(��������������) 
	* @author wanghailong 81813780@qq.com 
	* @date 2012-12-26 ����11:50:33 
	*
	 */
	private class InsertCheckTask extends AsyncTask<String, Void, String>{

		private MyProssDialog mDialog;
		
		@Override
		protected void onPreExecute() {
	        mDialog=new MyProssDialog(AddCheckActivity.this, "�ύ", "�����ύ�������Ժ�...");
		}
		
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			leftFm.insert();//�ɼӸ�booleanֵ�����ж�
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			mDialog.cancel();
			Intent intent=new Intent();
			setResult(12, intent);
			finish();
		}
	}
}
