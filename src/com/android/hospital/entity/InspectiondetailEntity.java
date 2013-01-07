package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: InspectiondetailEntity 
* @Description: TODO(����ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-19 ����4:16:35 
*
 */
public class InspectiondetailEntity {

	public String result_date_time;//��������
	
	public String report_item_name;//������Ŀ
	
	public String result;//������ֵ
	
	public String units;//��λ
	
	public String print_context;//�ο���Χ
	
	public String abnormal_indicator;//������
	
	
	public InspectiondetailEntity(){
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: TODO(��ȡ������ϸ����) 
	* @param @param dataList
	* @param @return    �趨�ļ� 
	* @return ArrayList<InspectiondetailEntity>    �������� 
	* @throws
	 */
	public static ArrayList<InspectiondetailEntity> init(ArrayList<DataEntity> dataList){
		ArrayList<InspectiondetailEntity> list=new ArrayList<InspectiondetailEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			InspectiondetailEntity entity=new InspectiondetailEntity();
			entity.result_date_time=dataList.get(i).get("result_date_time");
			entity.report_item_name=dataList.get(i).get("report_item_name");
			entity.result=dataList.get(i).get("result");
			entity.units=dataList.get(i).get("units");
			entity.print_context=dataList.get(i).get("print_context");
			entity.abnormal_indicator=dataList.get(i).get("abnormal_indicator");
			list.add(entity);
		}
		return list;
	}
}
