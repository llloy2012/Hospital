package com.android.hospital.ui.activity;

import java.util.ArrayList;

import com.android.hospital.adapter.GroupDcAdviceAdapter;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.GroupOrderEntity;
import com.android.hospital.webservice.WebServiceHelper;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

/**
 * 
* @ClassName: GroupDcAdviceActivity 
* @Description: TODO(套餐医嘱明细) 
* @author wanghailong 81813780@qq.com 
* @date 2013-1-1 下午4:58:41 
*
 */
public class GroupDcAdviceActivity extends Activity{

	private ListView mListView;
	private String group_order_id;//套餐医嘱id
	private ArrayList<DcAdviceEntity> groupAdviceList;//套餐医嘱明细集合
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_dcadvice);
		Intent intent=getIntent();
		group_order_id=intent.getStringExtra("id");
		mListView=(ListView) findViewById(R.id.group_dcadvice_listview);
		new GroupDcAcviceTask().execute();
	}
	
	/**
	 * 
	* @ClassName: GroupDcAcviceTask 
	* @Description: TODO(这里用一句话描述这个类的作用) 
	* @author wanghailong 81813780@qq.com 
	* @date 2013-1-1 下午5:15:18 
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
			GroupDcAdviceAdapter adapter=new GroupDcAdviceAdapter(GroupDcAdviceActivity.this, groupAdviceList);
			mListView.setAdapter(adapter);
		}
		
	}
}
