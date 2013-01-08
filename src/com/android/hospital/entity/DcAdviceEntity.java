package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
* @ClassName: DcAdviceEntity 
* @Description: TODO(ҽ��ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-14 ����11:38:27 
*
 */
public class DcAdviceEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7911361690361081062L;

	public String order_no;//ҽ�����룬������ҽ������ҽ��
	
	public String freq_counter;//Ƶ�ʴ���
	
    public String freq_interval;//Ƶ�ʼ��
    
    public String freq_interval_unit;//Ƶ�ʼ����λ
    
    public String order_status;//��ORDER_STATUS_DICT�в鿴���¿�ҽ����ʶΪ1���ɣ�
    
    public String ordering_dept;//��ҽ������ staff_dict.dept_code
    
    public String drug_billing_attr;//ҩƷ�Ƽ����� ��ӳҩƷ�Ƿ�Ƽۣ�0-������1-�Դ�ҩ(һ����0��
    
    public String order_sub_no;//��ҽ�����
    
    public String order_code;//ҩƷ����
    
    public String order_class;//ҽ�����AΪҩƷ
    
    public String repeat_indicator;//����
    
    public String start_date_time;//�´�ʱ��--------       
    
    public String order_text;//ҽ������-
    
    public String dosage;//����
    
    public String dosage_units;//��λ-------
    
    public String administration;//;��
    
    public String frequency;//Ƶ��
    
    public String perform_schedule;//ִ��ʱ��
    
	public String stop_date_time;//����ʱ��
	
	public String freq_detail;//ҽ��˵��-
	
	public String doctor;//ִ��ҽ��
	
	//����ҽ�����������
	public String enter_date_time;//
	
	public String patient_id;
	
	public String visit_id;
	
	public String billing_attr;
	
	public String doctor_user;
	
	public String drug_spec;//ҩƷ���
	
	
	public DcAdviceEntity(){
		
	}
	
	
	public static ArrayList<DcAdviceEntity> init(ArrayList<DataEntity> dataList) {

		ArrayList<DcAdviceEntity> list=new ArrayList<DcAdviceEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			DcAdviceEntity entity=new DcAdviceEntity();
			entity.order_no=dataList.get(i).get("order_no").trim();
			entity.freq_counter=dataList.get(i).get("freq_counter").trim();
			entity.freq_interval=dataList.get(i).get("freq_interval").trim();
			entity.freq_interval_unit=dataList.get(i).get("freq_interval_units").trim();
			entity.order_status=dataList.get(i).get("order_status").trim();
			entity.ordering_dept=dataList.get(i).get("ordering_dept").trim();
			entity.drug_billing_attr=dataList.get(i).get("drug_billing_attr").trim();
			entity.order_sub_no=dataList.get(i).get("order_sub_no").trim();
			entity.order_code=dataList.get(i).get("order_code").trim();
			entity.order_class=dataList.get(i).get("order_class").trim();
			entity.repeat_indicator=dataList.get(i).get("repeat_indicator").trim();
			entity.start_date_time=dataList.get(i).get("start_date_time").trim();
			entity.enter_date_time=dataList.get(i).get("enter_date_time").trim();
			entity.order_text=dataList.get(i).get("order_text").trim();
			entity.dosage=dataList.get(i).get("dosage").trim();
			entity.dosage_units=dataList.get(i).get("dosage_units").trim();
			entity.administration=dataList.get(i).get("administration").trim();
			entity.perform_schedule=dataList.get(i).get("perform_schedule").trim();
			entity.stop_date_time=dataList.get(i).get("stop_date_time").trim();
			entity.freq_detail=dataList.get(i).get("freq_detail").trim();
			entity.doctor=dataList.get(i).get("doctor").trim();
			entity.frequency=dataList.get(i).get("frequency").trim();
			list.add(entity);
		}
		return list;
	}
}
