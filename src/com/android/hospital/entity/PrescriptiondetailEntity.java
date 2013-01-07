package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: PrescriptiondetailEntity 
* @Description: TODO(������ϸʵ��) 
* @author lll 
* @date 2012-12-20 
*
 */
public class PrescriptiondetailEntity {

	public String PRESC_DATE;//��������
	
	public String PRESC_NO;//������
	
	public String ITEM_NO;//���
	
	public String DRUG_NAME;//ҩƷ����
	
	public String PACKAGE_SPEC;//���
	
	public String FIRM_ID;//����
	
	public String DOSAGE_EACH;//���μ���
	
	public String DOSAGE_UNITS;//��λ
	
	public String ADMINISTRATION;//;��
	
	public String FREQUENCY;//Ƶ��
	
	public String FREQ_DETAIL;//ҽ��˵��
	
	public String QUANTITY;//����
	
	public String PACKAGE_UNITS;//��λ
	
	public String COSTS;//�Ƽ�
	
	
	public PrescriptiondetailEntity(){
		
	}
	/**
	 * 
	* @Title: init 
	* @Description: TODO(��ȡ������ϸ����) 
	* @param @param dataList
	* @param @return    �趨�ļ� 
	* @return ArrayList<PrescriptiondetailEntity>    �������� 
	* @throws
	 */
	public static ArrayList<PrescriptiondetailEntity> init(ArrayList<DataEntity> dataList ){
		ArrayList<PrescriptiondetailEntity> arrayList=new ArrayList<PrescriptiondetailEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			PrescriptiondetailEntity entity=new PrescriptiondetailEntity();
			entity.PRESC_DATE=dataList.get(i).get("presc_date");
			entity.PRESC_NO=dataList.get(i).get("presc_no");
			entity.ITEM_NO=dataList.get(i).get("item_no");
			entity.DRUG_NAME=dataList.get(i).get("drug_name");
			entity.PACKAGE_SPEC=dataList.get(i).get("package_spec");
			entity.FIRM_ID=dataList.get(i).get("firm_id");
			entity.DOSAGE_EACH=dataList.get(i).get("dosage_each");
			entity.DOSAGE_UNITS=dataList.get(i).get("dosage_units");
			entity.ADMINISTRATION=dataList.get(i).get("administration");
			entity.FREQUENCY=dataList.get(i).get("frequency");
			entity.FREQ_DETAIL=dataList.get(i).get("freq_detail");
			entity.QUANTITY=dataList.get(i).get("quantity");
			entity.PACKAGE_UNITS=dataList.get(i).get("package_units");
			entity.COSTS=dataList.get(i).get("costs");
			arrayList.add(entity);
		}
		return arrayList;
	}
	
	

}
