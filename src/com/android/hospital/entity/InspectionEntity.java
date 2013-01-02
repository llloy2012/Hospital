package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;

import android.util.Log;



/**
 * 
* @ClassName: InspectionEntity 
* @Description: TODO(����ʵ��) 
* @author lll
* @date 2012-12-18  
*
 */
public class InspectionEntity implements Serializable{

	/** 
	* @user - @date 
	*/
	private static final long serialVersionUID = -611386808382331665L;
	public String item_no;//��Ŀ���
	public String item_name;//��Ŀ����
	public String specimen;//�걾
	public String item_code;//��Ŀ����
	public String test_no;//�������
	public String dept_name;//��������
	public String result_status;//���״̬
	public String requested_date_time;//�������ڼ�ʱ��
	public String billing_indicator;//�Ƽ۱�־
	public String priority_indicator;//���ȱ�־
	public String charge_type;//�ѱ�
	public String notes_for_spcm;//�걾˵��
	public String performed_by;//ִ�п���
	public String relevant_clinic_diag;//�ٴ����
	public String name;//����
	public String sex;//�Ա�
	public String age;//����
	public String ordering_dept;//�������
	public String patient_id;//���˱�ʶ��
	public String prdering_provider;//����ҽ��
	
	public InspectionEntity() {
		
	}
	/**
	 * 
	* @Title: init 
	* @Description: TODO(��ʼ��ʵ��) 
	* @param @param arrayList
	* @param @return    �趨�ļ� 
	* @return ArrayList<InspectionEntity>    ʵ�弯��
	* @throws
	 */
	public static ArrayList<InspectionEntity> init(ArrayList<DataEntity> arrayList){
		ArrayList<InspectionEntity> list=new ArrayList<InspectionEntity>();
		for (int i = 0; i < arrayList.size(); i++) {
			InspectionEntity entity=new InspectionEntity();
			entity.item_no=arrayList.get(i).get("item_no");
			entity.item_name=arrayList.get(i).get("item_name");
			entity.specimen=arrayList.get(i).get("specimen");
			entity.item_code=arrayList.get(i).get("item_code");
			entity.test_no=arrayList.get(i).get("test_no");
			entity.dept_name=arrayList.get(i).get("dept_name");
			entity.result_status=arrayList.get(i).get("result_status");
			entity.requested_date_time=arrayList.get(i).get("requested_date_time");
			entity.billing_indicator=arrayList.get(i).get("billing_indicator");
			entity.priority_indicator=arrayList.get(i).get("priority_indicator");
			entity.charge_type=arrayList.get(i).get("charge_type");
			entity.notes_for_spcm=arrayList.get(i).get("notes_for_spcm");
			entity.performed_by=arrayList.get(i).get("performed_by");
			entity.relevant_clinic_diag=arrayList.get(i).get("relevant_clinic_diag");
			entity.name=arrayList.get(i).get("name");
			entity.sex=arrayList.get(i).get("sex");
			entity.age=arrayList.get(i).get("age");
			entity.ordering_dept=arrayList.get(i).get("ordering_dept");
			entity.patient_id=arrayList.get(i).get("patient_id");
			entity.prdering_provider=arrayList.get(i).get("prdering_provider");
			list.add(entity);	
		}
		//�����������ͬ����Ŀ���ƺϲ���һ��
		ArrayList<InspectionEntity> list_end=new ArrayList<InspectionEntity>();
		int k=0;
		for (int j = 0; j < list.size(); j++) {
			if (k!=0){
					String  test_n1=list.get(j).test_no;  
					String  test_n0=list.get(j-1).test_no; 
					if (test_n0.equals(test_n1)){					
						list.get(k-1).item_name=list.get(k-1).item_name + '('+list.get(j).item_no+')'+list.get(j).item_name;
						list.set(k-1, list.get(k-1));
					}else{
							list_end.add(list.get(k-1));
							Log.e("����ʱ��-->", list.get(k-1).requested_date_time.toString());
							k=j;
							list.get(k).item_name ='('+list.get(j).item_no+')'+list.get(j).item_name;
							list.set(k, list.get(k));
							k++;
					}
			}else{
					list.get(j).item_name ='('+list.get(j).item_no+')'+list.get(j).item_name;
					list.set(j, list.get(j));
					k++;
			}
		}
		list_end.add(list.get(k-1));	
		return list_end;
	}
}
