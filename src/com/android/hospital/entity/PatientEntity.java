package com.android.hospital.entity;

import java.util.ArrayList;

import android.util.Log;

import com.android.hospital.util.DebugUtil;
/**
 * 
* @ClassName: PatientEntity 
* @Description: TODO(病人实体) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 上午11:38:52 
*
 */
public class PatientEntity {

	public String dept_code;//病人所在科室
	
	public String bed_no;//床号
	
	public String diagnosis;//病人诊断（C）
	
	public String doctor_in_charge;//主治医生（C）
	
	public String patient_id;//病人ID
	
	public String visit_id;//住院次数
	
	public String prepayments;//预交金余额
	
    public String name;//病人名称
    
    public String sex;//性别
    
    public String name_phonetic;//姓名全拼（c）
    
	public String date_of_birth;//出生年月 (date)
	
	public String birth_place;//出生地（c）
	
	public String identity;//身份（c）
	
	public String mailing_address;//地址 （c）
	
	public String zip_code;//邮政编码（c）
	
	public String phone_number_home;//家庭电话号码（c）
	
	public String charge_type;//费别 (c)
    
	public PatientEntity(){
		
	}
	
	/**
	 * 
	* @Title: init 
	* @Description: TODO(初始化实体) 
	* @param @param arrayList
	* @param @return    设定文件 
	* @return ArrayList<PatientEntity>    实体集合
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
