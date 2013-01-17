package com.android.hospital.entity;

import java.util.ArrayList;

import android.util.Log;

import com.android.hospital.util.DebugUtil;
/**
 * 
* @ClassName: PatientEntity 
* @Description: TODO(����ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:38:52 
*
 */
public class PatientEntity {

	public String dept_code;//�������ڿ���
	
	public String bed_no;//����
	
	public String diagnosis;//������ϣ�C��
	
	public String doctor_in_charge;//����ҽ����C��
	public String user_name;//����ҽ���ĵ�½�˺�
	
	public String patient_id;//����ID
	
	public String visit_id;//סԺ����
	
	public String prepayments;//Ԥ�������
	
    public String name;//��������
    
    public String sex;//�Ա�
    
    public String name_phonetic;//����ȫƴ��c��
    
	public String date_of_birth;//�������� (date)
	
	public String birth_place;//�����أ�c��
	
	public String identity;//��ݣ�c��
	
	public String mailing_address;//��ַ ��c��
	
	public String zip_code;//�������루c��
	
	public String phone_number_home;//��ͥ�绰���루c��
	
	public String charge_type;//�ѱ� (c)
    
	public PatientEntity(){
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: TODO(��ʼ��ʵ��) 
	* @param @param arrayList
	* @param @return    �趨�ļ� 
	* @return ArrayList<PatientEntity>    ʵ�弯��
	* @throws
	 */
	public static ArrayList<PatientEntity> init(ArrayList<DataEntity> arrayList){
		ArrayList<PatientEntity> list=new ArrayList<PatientEntity>();
		for (int i = 0; i < arrayList.size(); i++) {
			PatientEntity entity=new PatientEntity();
			entity.dept_code=arrayList.get(i).get("dept_code").trim();
			entity.bed_no=arrayList.get(i).get("bed_no").trim();
			entity.diagnosis=arrayList.get(i).get("diagnosis").trim();
			entity.doctor_in_charge=arrayList.get(i).get("doctor_in_charge").trim();
			entity.user_name=arrayList.get(i).get("user_name").trim();
			entity.patient_id=arrayList.get(i).get("patient_id").trim();
			entity.visit_id=arrayList.get(i).get("visit_id").trim();
			entity.prepayments=arrayList.get(i).get("prepayments").trim();
			entity.name=arrayList.get(i).get("name").trim();
			entity.sex=arrayList.get(i).get("sex").trim();
			entity.name_phonetic=arrayList.get(i).get("name_phonetic").trim();
			entity.date_of_birth=arrayList.get(i).get("date_of_birth").trim();
			entity.identity=arrayList.get(i).get("identity").trim();
			entity.mailing_address=arrayList.get(i).get("mailing_address").trim();
			entity.zip_code=arrayList.get(i).get("zip_code").trim();
			entity.phone_number_home=arrayList.get(i).get("phone_number_home").trim();
			entity.charge_type=arrayList.get(i).get("charge_type").trim();
			entity.birth_place=arrayList.get(i).get("birth_place").trim();
			list.add(entity);
		}
		return list;
	}
}
