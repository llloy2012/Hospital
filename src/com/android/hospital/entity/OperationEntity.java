package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.http.impl.cookie.DateUtils;
/**
 * 
* @ClassName: OperationEntity 
* @Description: TODO(����ʵ��) 
* @author lll
* @date 2013-1-15  
*
 */
public class OperationEntity implements Serializable{


	public String scheduled_date_time;//��������
	public String operating_room; //������
	public String operating_room_no; //������
	public String sequence ;//̨��
	public String name; //��������
	public String sex; //�Ա�
	public String bed_no;//����
	public String diag_before_operation;//��Ҫ���
	public String operation;//��������
	public String surgeon;//������
	public String first_assistant,second_assistant,third_assistant,fourth_assistant;//�����ĸ�
	public String anesthesia_method;//������
	public String anesthesia_doctor;//����ҽ��
	public String blood_tran_doctor;//��Ѫ��
	public String notes_on_operation;//����׼������
	public String ack_indicator ;//������ȷ�ϱ�־  0��գ�δ��������ȷ�ϣ�1��ȷ��
	
	public OperationEntity(){
		
	}
	
	
	public static ArrayList<OperationEntity> init(ArrayList<DataEntity> dataList) {

		ArrayList<OperationEntity> list=new ArrayList<OperationEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			OperationEntity entity=new OperationEntity();
			entity.scheduled_date_time=dataList.get(i).get("scheduled_date_time").trim();
			entity.operating_room=dataList.get(i).get("operating_room").trim();
			entity.operating_room_no=dataList.get(i).get("operating_room_no").trim();
			entity.sequence=dataList.get(i).get("sequence").trim();
			entity.name=dataList.get(i).get("name").trim();
			entity.sex=dataList.get(i).get("sex").trim();
			entity.bed_no=dataList.get(i).get("bed_no").trim();
			entity.diag_before_operation=dataList.get(i).get("diag_before_operation").trim();
			entity.operation=dataList.get(i).get("operation").trim();
			entity.surgeon=dataList.get(i).get("surgeon").trim();
			entity.first_assistant=dataList.get(i).get("first_assistant").trim();
			entity.second_assistant=dataList.get(i).get("second_assistant").trim();
			entity.third_assistant=dataList.get(i).get("third_assistant").trim();
			entity.fourth_assistant=dataList.get(i).get("fourth_assistant").trim();
			entity.anesthesia_method=dataList.get(i).get("anesthesia_method").trim();
			entity.anesthesia_doctor=dataList.get(i).get("anesthesia_doctor").trim();
			entity.blood_tran_doctor=dataList.get(i).get("blood_tran_doctor").trim();
			entity.notes_on_operation=dataList.get(i).get("notes_on_operation").trim();
			entity.ack_indicator=dataList.get(i).get("ack_indicator").trim();
			list.add(entity);
		}
		return list;
	}
}
