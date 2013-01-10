package com.android.hospital.ui.activity;

import com.android.hospital.HospitalApp;
import com.android.hospital.adapter.DrugAdapter;
import com.android.hospital.asyntask.add.DrugOrNonDrugTask;
import com.android.hospital.asyntask.add.FreqAndWayTask;
import com.android.hospital.asyntask.add.MaxNumberTask;
import com.android.hospital.constant.AppConstant;
import com.android.hospital.ui.fragment.AddDcAdviceFragment;
import com.android.hospital.ui.fragment.LeftListFragment;
import com.android.hospital.ui.fragment.SearchAddDcAdviceFragment;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
/**
 * 
* @ClassName: AddDcAdviceActivity 
* @Description: TODO(添加医嘱显示界面) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:37:25 
*
 */
public class AddDcAdviceActivity extends Activity implements OnClickListener{

	private AddDcAdviceFragment leftFm;
	
	private SearchAddDcAdviceFragment searchFm;
	
	private Button mCancleBut,mOkBut;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_dcadvice);
		
		initView();
		FragmentTransaction ft=getFragmentManager().beginTransaction();
		if (leftFm==null) {
			leftFm=new AddDcAdviceFragment();		
			ft.add(R.id.common_left_fragment, leftFm,"addfm");
		}
		if (searchFm==null) {
			searchFm=new SearchAddDcAdviceFragment();
			ft.add(R.id.common_right_fragment, searchFm,"searchfm");
		}
		ft.commit();
		new MaxNumberTask(this).execute("");//获取最大序号任务
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
            if (leftFm.validate()) {
            	new AlertDialog.Builder(AddDcAdviceActivity.this)
                .setIconAttribute(android.R.attr.alertDialogIcon)
                .setTitle("是否确认提交？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked OK so do some stuff */
                    	leftFm.getAddData();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        /* User clicked Cancel so do some stuff */
                    }
                })
                .create().show();
			}else {
				Toast.makeText(AddDcAdviceActivity.this, "医嘱或频次，途径不能为空!", Toast.LENGTH_SHORT).show();
			}
			
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
