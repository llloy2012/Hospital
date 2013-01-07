package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: DrugEntity 
* @Description: TODO(药品实体) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 下午11:30:46 
*
 */
public class DrugEntity {

	public String drug_name;//别名
	
	public String package_units;//包装单位
	
	public String drug_spec;//规格
	
	public String storage;//库存
	
	public String storage_name;//库房
	
	public String dose_per_unit;//单次剂量
	
	public String dose_units;//单位 
	
	public String input_code;//输入码，检索用
	
	public String drug_code;//药品代码
	
	public String is_basic;//是否基药
	
	public String drug_indicator;//医嘱类别
	
	public String purchase_price;//卖出价格
	
	
	//处方新增所需的额外属性
	public String dosage_each;
	
	public String total_dose_per;//总量
	
	public String freq_detail;//
	
	public String quantity;//
	
	public String administration;
	
	public String frequency;
	
	public String single_price;//单个价格
	
	public String total_price;//总的价格
	
	public String firm_id;
	
	public String package_spec;
	
	public String amount_per_package;
	
	public String dosage;
	
	public String min_spec;
	
	
	public DrugEntity(){
		
	}
	
	
	public static ArrayList<DrugEntity> init(ArrayList<DataEntity> dataList){
		ArrayList<DrugEntity> list=new ArrayList<DrugEntity>();
		int size=dataList.size();
		for (int i = 0; i < size; i++) {
			DrugEntity entity=new DrugEntity();
			entity.drug_name=dataList.get(i).get("drug_name");
			entity.package_units=dataList.get(i).get("package_units");
			entity.drug_spec=dataList.get(i).get("drug_spec");
			entity.storage=dataList.get(i).get("storage");
			entity.storage_name=dataList.get(i).get("storage_name");
			entity.dose_per_unit=dataList.get(i).get("dose_per_unit");
			entity.dose_units=dataList.get(i).get("dose_units");
			entity.input_code=dataList.get(i).get("input_code");
			entity.drug_code=dataList.get(i).get("drug_code");
			entity.is_basic=dataList.get(i).get("is_basic");
			entity.drug_indicator=dataList.get(i).get("drug_indicator");
			entity.purchase_price=dataList.get(i).get("purchase_price");
			list.add(entity);
		}
		return list;
	}
}
