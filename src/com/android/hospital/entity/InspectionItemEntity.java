package com.android.hospital.entity;

import java.util.ArrayList;

public class InspectionItemEntity {

	public String item_class;//��Ŀ���
	
	public String item_name;  //��Ŀ����
	
	public String item_code; //��Ŀ����
	
	public String std_indicator;  // 0��1
	
	public String input_code;//������
	
	public String input_code_wb;  //
	
	public String performed_by;  //������Ҵ��룿
	
	public String expand1; //�걾
	
	public String expand2; //���
	
	public String expand3;  //���Ҵ���
	
	
	public InspectionItemEntity(){
		
	}
	
	public static ArrayList<InspectionItemEntity> init(ArrayList<DataEntity> dataList){
		ArrayList<InspectionItemEntity> list=new ArrayList<InspectionItemEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			InspectionItemEntity entity=new InspectionItemEntity();
			entity.item_class=dataList.get(i).get("item_class").trim();
			entity.item_name=dataList.get(i).get("item_name").trim();
			entity.item_code=dataList.get(i).get("item_code").trim();
			entity.std_indicator=dataList.get(i).get("std_indicator").trim();
			entity.input_code=dataList.get(i).get("input_code").trim();
			entity.input_code_wb=dataList.get(i).get("input_code_wb").trim();
			entity.performed_by=dataList.get(i).get("performed_by").trim();
			entity.expand1=dataList.get(i).get("expand1").trim();
			entity.expand2=dataList.get(i).get("expand2").trim();
			entity.expand3=dataList.get(i).get("expand3").trim();
			list.add(entity);
		}
		return list;
	}
}
