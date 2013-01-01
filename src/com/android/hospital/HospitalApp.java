package com.android.hospital;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.hospital.entity.DrugEntity;
import com.android.hospital.entity.GroupOrderEntity;
import com.android.hospital.entity.InspectionItemEntity;
import com.android.hospital.entity.NonDrugEntity;
import com.android.hospital.entity.PatientEntity;

import android.app.Application;
/**
 * 
* @ClassName:  
* @Description: TODO(��̬����) 
* �����ϴ�
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����8:57:34 
*
 */
public class HospitalApp extends Application{

	private PatientEntity patientEntity;//��������ʵ��
	private ArrayList<DrugEntity> drugList;//ҩƷ����
	private ArrayList<DrugEntity> middleDrugList;//������ҩ��ҩƷ����
	private ArrayList<NonDrugEntity> nondrugList;//��ҩƷ����
	private String maxNumber;//�����ţ�ÿ�ε��������ť��ֵ���ı�
	private List<Map<String, String>> freqList;//;������
	private List<Map<String, String>> wayList;//Ƶ�μ���
	private List<Map<String, String>> classList;//�����𼯺�
	private List<Map<String, String>> deptList;//�����Ҽ���
	private List<Map<String, String>> inspecitonClassList;//������𼯺�
	private List<Map<String, String>> inspectionDeptList;//������Ҽ���
	private String loginName="";//��¼�˺�
	private String doctor="";//ҽ����
	private ArrayList<InspectionItemEntity> inspectionItemList;//������Ŀ����
	private String nextval="";//������
	private ArrayList<GroupOrderEntity> groupOrderList;//�ײ�ҽ������
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public PatientEntity getPatientEntity() {
		return patientEntity;
	}

	public void setPatientEntity(PatientEntity patientEntity) {
		this.patientEntity = patientEntity;
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

	public ArrayList<DrugEntity> getDrugList() {
		return drugList;
	}

	public void setDrugList(ArrayList<DrugEntity> drugList) {
		this.drugList = drugList;
	}

	public ArrayList<NonDrugEntity> getNondrugList() {
		return nondrugList;
	}

	public void setNondrugList(ArrayList<NonDrugEntity> nondrugList) {
		this.nondrugList = nondrugList;
	}

	public List<Map<String, String>> getFreqList() {
		return freqList;
	}

	public void setFreqList(List<Map<String, String>> freqList) {
		this.freqList = freqList;
	}

	public List<Map<String, String>> getWayList() {
		return wayList;
	}

	public void setWayList(List<Map<String, String>> wayList) {
		this.wayList = wayList;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public List<Map<String, String>> getClassList() {
		return classList;
	}

	public void setClassList(List<Map<String, String>> classList) {
		this.classList = classList;
	}

	public List<Map<String, String>> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Map<String, String>> deptList) {
		this.deptList = deptList;
	}

	public List<Map<String, String>> getInspecitonClassList() {
		return inspecitonClassList;
	}

	public void setInspecitonClassList(List<Map<String, String>> inspecitonClassList) {
		this.inspecitonClassList = inspecitonClassList;
	}

	public List<Map<String, String>> getInspectionDeptList() {
		return inspectionDeptList;
	}

	public void setInspectionDeptList(List<Map<String, String>> inspectionDeptList) {
		this.inspectionDeptList = inspectionDeptList;
	}

	public ArrayList<InspectionItemEntity> getInspectionItemList() {
		return inspectionItemList;
	}

	public void setInspectionItemList(
			ArrayList<InspectionItemEntity> inspectionItemList) {
		this.inspectionItemList = inspectionItemList;
	}

	public ArrayList<DrugEntity> getMiddleDrugList() {
		return middleDrugList;
	}

	public void setMiddleDrugList(ArrayList<DrugEntity> middleDrugList) {
		this.middleDrugList = middleDrugList;
	}

	public String getNextval() {
		return nextval;
	}

	public void setNextval(String nextval) {
		this.nextval = nextval;
	}

	public ArrayList<GroupOrderEntity> getGroupOrderList() {
		return groupOrderList;
	}

	public void setGroupOrderList(ArrayList<GroupOrderEntity> groupOrderList) {
		this.groupOrderList = groupOrderList;
	}

	

}
