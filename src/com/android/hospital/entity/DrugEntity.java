package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: DrugEntity 
* @Description: TODO(ҩƷʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����11:30:46 
*
 */
public class DrugEntity {

	public String drug_name;//����
	
	public String package_units;//��װ��λ
	
	public String drug_spec;//���
	
	public String storage;//���
	
	public String storage_name;//�ⷿ
	
	public String dose_per_unit;//���μ���
	
	public String dose_units;//��λ 
	
	public String input_code;//�����룬������
	
	public String drug_code;//ҩƷ����
	
	public String is_basic;//�Ƿ��ҩ
	
	public String drug_indicator;//ҽ�����
	
	public String purchase_price;//�����۸�
	
	
	//������������Ķ�������
	public String dosage_each;
	
	public String total_dose_per;//����
	
	public String freq_detail;//
	
	public String quantity;//
	
	public String administration;
	
	public String frequency;
	
	public String single_price;//�����۸�
	
	public String total_price;//�ܵļ۸�
	
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
