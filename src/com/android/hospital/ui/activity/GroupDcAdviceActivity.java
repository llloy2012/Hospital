package com.android.hospital.ui.activity;

import java.util.ArrayList;

import com.android.hospital.adapter.GroupDcAdviceAdapter;
import com.android.hospital.asyntask.add.InsertDcAdviceTask;
import com.android.hospital.asyntask.add.PriceTask;
import com.android.hospital.db.ServerDao;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.GroupOrderEntity;
import com.android.hospital.webservice.WebServiceHelper;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 
* @ClassName: GroupDcAdviceActivity 
* @Description: TODO(�ײ�ҽ����ϸ) 
* @author wanghailong 81813780@qq.com 
* @date 2013-1-1 ����4:58:41 
*
 */
public class GroupDcAdviceActivity extends Activity implements OnClickListener{

	private ListView mListView;
	private Button mCancleBut,mOkBut;
	private String group_order_id;//�ײ�ҽ��id
	private ArrayList<DcAdviceEntity> groupAdviceList;//�ײ�ҽ����ϸ����
	private GroupDcAdviceAdapter adapter;
	private LinearLayout prossbarLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_dcadvice);
		Intent intent=getIntent();
		group_order_id=intent.getStringExtra("id");
		mListView=(ListView) findViewById(R.id.group_dcadvice_listview);
		mOkBut=(Button) findViewById(R.id.common_but_ok);
		mCancleBut=(Button) findViewById(R.id.common_but_cancle);
		prossbarLayout=(LinearLayout) findViewById(R.id.group_progressbar);
		
		mOkBut.setOnClickListener(this);
		mCancleBut.setOnClickListener(this);
		new GroupDcAcviceTask().execute();
	}
	
	/**
	 * 
	* @ClassName: GroupDcAcviceTask 
	* @Description: TODO(������һ�仰��������������) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-1 ����5:15:18 
	*
	 */
	private class GroupDcAcviceTask extends AsyncTask<Void, Void, String>{
	
		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			String sql="select group_order_id,item_no,item_sub_no,repeat_indicator,order_class,order_text,order_code,dosage," +
					"dosage_units,administration,frequency,freq_counter,freq_interval,freq_interval_unit,freq_detail,drug_billing_attr " +
					"from group_order_items where group_order_id='"+group_order_id+"'";
			ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
			groupAdviceList=new ArrayList<DcAdviceEntity>();
			for (int i = 0; i < dataList.size(); i++) {
				DcAdviceEntity entity=new DcAdviceEntity();
				entity.order_no=dataList.get(i).get("item_no").trim();
				entity.order_sub_no=dataList.get(i).get("item_sub_no").trim();
				entity.repeat_indicator=dataList.get(i).get("repeat_indicator").trim();
				entity.order_class=dataList.get(i).get("order_class").trim();
				entity.order_text=dataList.get(i).get("order_text").trim();
				entity.order_code=dataList.get(i).get("order_code").trim();
				entity.dosage=dataList.get(i).get("dosage").trim();
				entity.dosage_units=dataList.get(i).get("dosage_units").trim();
				entity.administration=dataList.get(i).get("administration").trim();
				entity.frequency=dataList.get(i).get("frequency").trim();
				entity.freq_counter=dataList.get(i).get("freq_counter").trim();
				entity.freq_interval=dataList.get(i).get("freq_interval").trim();
				entity.freq_interval_unit=dataList.get(i).get("freq_interval_unit").trim();
				entity.freq_detail=dataList.get(i).get("freq_detail").trim();
				entity.drug_billing_attr=dataList.get(i).get("drug_billing_attr").trim();
				groupAdviceList.add(entity);
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			prossbarLayout.setVisibility(View.GONE);
			adapter=new GroupDcAdviceAdapter(GroupDcAdviceActivity.this, groupAdviceList);
			mListView.setAdapter(adapter);
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.common_but_ok:
			if (adapter.getCount()==0) {
				Toast.makeText(getApplicationContext(), "û������!", Toast.LENGTH_SHORT).show();
			}else {
				new AlertDialog.Builder(GroupDcAdviceActivity.this)
	            .setIconAttribute(android.R.attr.alertDialogIcon)
	            .setTitle("�Ƿ�ȷ���ύ��")
	            .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {

	                    /* User clicked OK so do some stuff */
	                	insertGroupDc();
	                }
	            })
	            .setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {

	                    /* User clicked Cancel so do some stuff */
	                }
	            })
	            .create().show();
			}
			break;
		case R.id.common_but_cancle:
			Intent intent=new Intent();
			setResult(1, intent);
			finish();
			break;
		default:
			break;
		}
	}
	/**
	 * 
	* @Title: insertGroupDc 
	* @Description: TODO(��������) 
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws
	 */
	private void insertGroupDc(){
		int size=adapter.getCount();
		for (int i = 0; i < size; i++) {
			DcAdviceEntity entity=(DcAdviceEntity) adapter.getItem(i);
			String sql=ServerDao.getInsertOrders(entity);
			new InsertDcAdviceTask(this, sql).execute();
			int isDrug=0;
			if (!entity.order_class.equals("A")) {
				isDrug=1;
			}
			new PriceTask(this, entity, isDrug).execute();
		}
	}
}
