package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: NonDrugEntity 
* @Description: TODO(��ҩƷʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����11:34:00 
*
 */
public class NonDrugEntity {

	public String item_name;//����
	
	public String input_code;//�����룬������
	
	public String item_code;//ҩƷ����
	
    public String item_class;//���
    
    public NonDrugEntity(){}
    
    
    public static ArrayList<NonDrugEntity> init(ArrayList<DataEntity> dataList){
    	ArrayList<NonDrugEntity> list=new ArrayList<NonDrugEntity>();
    	int size=dataList.size();
    	for (int i = 0; i < size; i++) {
    		NonDrugEntity entity=new NonDrugEntity();
			entity.item_name=dataList.get(i).get("item_name");
			entity.input_code=dataList.get(i).get("input_code");
			entity.item_code=dataList.get(i).get("item_code");
			entity.item_class=dataList.get(i).get("item_class");
			list.add(entity);
		}
		return list;
    }
}
