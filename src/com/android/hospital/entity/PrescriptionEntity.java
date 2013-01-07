package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
* @ClassName: PrescriptionEntity 
* @Description: TODO(����ʵ��) 
* @author lll
* @date 2012-12-19  
*
 */
public class PrescriptionEntity implements Serializable{
	
	public String presc_no;//������
	
	public String presc_date;//��������
	
	public String prescribed_by;//����ҽ��
	
	public String presc_type;//�������� 0 ��ҩ 1��ҩ
	
	public String repetition;//����
	
	public String costs;//�ܻ���
	
	public String presc_status;//����״̬ 1��ҩ 0δ��ҩ
	
	public String dept_name;//��ҩҩ��
	
	public PrescriptionEntity(){
		
	}
	public static ArrayList<PrescriptionEntity> init(ArrayList<DataEntity> arrayList){
		ArrayList<PrescriptionEntity> list=new ArrayList<PrescriptionEntity>();
		for (int i = 0; i < arrayList.size(); i++) {
			PrescriptionEntity entity=new PrescriptionEntity();
			entity.presc_no=arrayList.get(i).get("presc_no");
			entity.presc_date=arrayList.get(i).get("presc_date");
			entity.prescribed_by=arrayList.get(i).get("prescribed_by");
			entity.presc_type=arrayList.get(i).get("presc_type");
			entity.repetition=arrayList.get(i).get("repetition");
			entity.costs=arrayList.get(i).get("costs");
			entity.presc_status=arrayList.get(i).get("presc_status");
			entity.dept_name=arrayList.get(i).get("dept_name");
			list.add(entity);	
		}
		return list;
			
	}

}
