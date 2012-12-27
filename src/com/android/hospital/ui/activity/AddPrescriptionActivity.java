package com.android.hospital.ui.activity;

import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.fragment.AddCheckFragment;
import com.android.hospital.ui.fragment.AddPrescriptionFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.SeachPrescriptionFragment;
import com.android.hospital.ui.fragment.SearchFragment;
import com.android.hospital.util.DebugUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 
* @ClassName: AddDcAdviceActivity 
* @Description: TODO(新增处方显示界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:37:25 
*
 */
public class AddPrescriptionActivity extends Activity implements OnClickListener{

	private AddPrescriptionFragment leftFm;
	private SearchFragment searchFm;
	private Button mCancleBut,mOkBut;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_prescription);
		
		initView();
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new AddPrescriptionFragment();		
			ft.add(R.id.common_left_fragment, leftFm,"addfm");
		}
		if (searchFm==null) {
			searchFm=new SeachPrescriptionFragment();
			ft.add(R.id.common_right_fragment, searchFm,"searchfm");
		}
		ft.commit();
		new MaxNumberTask(this).execute("presc_no_seq.nextval");//获取最大序号任务
	}

	private void initView(){
		mCancleBut=(Button) findViewById(R.id.common_but_cancle);
		mOkBut=(Button) findViewById(R.id.common_but_ok);
		
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
			new AlertDialog.Builder(AddPrescriptionActivity.this)
            .setIconAttribute(android.R.attr.alertDialogIcon)
            .setTitle("是否确认提交？")
            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    /* User clicked OK so do some stuff */
                }
            })
            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
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
	
}
