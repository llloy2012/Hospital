package com.android.hospital.asyntask.add;

import java.util.ArrayList;

import android.content.Context;

import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.webservice.WebServiceHelper;
/**
 * 
* @ClassName: PriceTask 
* @Description: TODO(计价任务) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-25 上午9:05:50 
*
 */
public class PriceTask extends BaseAsyncTask{

	private Context mContext;
	private DcAdviceEntity mAdviceEntity;
	private int isDrug=0;//是否为药品，0为是，1为非药品
	
	public PriceTask(Context context,DcAdviceEntity dcAdviceEntity,int isDrug){
		this.mContext=context;
		this.mAdviceEntity=dcAdviceEntity;
		this.isDrug=isDrug;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	private void priceStep1(){
		String sql = "select "
				+ "price_item_name_dict.item_name,"
				+ "clinic_vs_charge.charge_item_class,"
				+ "clinic_vs_charge.charge_item_spec,"
				+ "clinic_vs_charge.clinic_item_code,"
				+ "clinic_vs_charge.charge_item_code,"
				+ "price_item_name_dict.item_code,"
				+ "clinic_vs_charge.units,"
				+ "clinic_vs_charge.amount,"
				+ "clinic_vs_charge.backbill_rule"
				+ " from clinic_vs_charge, clinic_item_name_dict, price_item_name_dict"
				+ " where (clinic_vs_charge.clinic_item_class ="
				+ "clinic_item_name_dict.item_class) "
				+ "and (clinic_vs_charge.clinic_item_code = clinic_item_name_dict.item_code) "
				+ "and (clinic_vs_charge.charge_item_class ="
				+ " price_item_name_dict.item_class) "
				+ "and (clinic_vs_charge.charge_item_code = price_item_name_dict.item_code) "
				+ "and ((clinic_item_name_dict.item_code = '"
				+ mAdviceEntity.order_text
				+ "')) "
				+ "and (price_item_name_dict.std_indicator = 1) "
				+ " and (clinic_item_name_dict.std_indicator = 1) "
				+ "and (clinic_item_name_dict.item_class in ('a', 'b', 'h','i','e','z'))";
		ArrayList<DataEntity> dataList=WebServiceHelper.getWebServiceData(sql);
		for (int i = 0; i < dataList.size(); i++) {
			
		}
	}
}
