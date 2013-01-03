package com.android.hospital.asyntask.add;

import java.util.ArrayList;
import android.content.Context;
import android.util.Log;
import com.android.hospital.HospitalApp;
import com.android.hospital.asyntask.BaseAsyncTask;
import com.android.hospital.entity.DataEntity;
import com.android.hospital.entity.DcAdviceEntity;
import com.android.hospital.entity.PatientEntity;
import com.android.hospital.webservice.WebServiceHelper;

/**
 * 
* @ClassName: PriceTask 
* @Description: TODO(�Ƽ�����) 
* @author lll
* @date 2012-12-25 
*
 */
public class PriceTask extends BaseAsyncTask{

	private Context mContext;
	private DcAdviceEntity mAdviceEntity;
	private int isDrug=0;//�Ƿ�ΪҩƷ��0Ϊ�ǣ�1Ϊ��ҩƷ
	private String item_name ;//�Ƽ���Ŀ���ơ�
	private String amount;//������
	private String units;//�Ƽ۵�λ��
	private String item_code;//�Ƽ���Ŀ���롣
	private String item_class;//�Ƽ���Ŀ���
	private String item_no="1";//�Ƽ���Ŀ��š�
	private String costs;//����Ŀ�ۼƷ��� 
	private String item_spec;//�Ƽ���Ŀ��� 
	private String backbill_rule;//�Ƽ۹���
	private String price; //����
	private String dose_per_unit;//��λ����
	private String charge_item_class;//;���е���Ŀ���
	private String charge_item_code;//��ҩƷ�е���Ŀ����
	private String charge_item_spec;//��ҩƷ�е���Ŀ���
	private String charge_item_amount;//;���е���Ŀ����
	private String charge_item_name;//;���е���Ŀ����
	private String charge_units;//;���е���Ŀ��λ
	private HospitalApp app;
	private PatientEntity patientEntity;//��������ʵ��
	
	public PriceTask(Context context,DcAdviceEntity dcAdviceEntity,int isDrug){
		this.mContext=context;
		this.mAdviceEntity=dcAdviceEntity; //ҽ��ʵ��
		this.isDrug=isDrug;
		this.app=(HospitalApp) mContext.getApplicationContext();
		patientEntity=app.getPatientEntity();
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		first_getData();	
		return null;
	}
	/**
	 * 
	* �Ƽ۹���������
	* 1��������first_getData()  ��order_code ��ü۱��е���Ŀ���룬�Լ���Ŀ�Ĺ�񣨸���ҩƷ/��ҩƷ����ȡ��ͬ��item_code�� 
	* 2��������second_getData(String item_code_temp)   ����Ŀ���룬�Լ���Ŀ�Ĺ��  �����Ŀ��Ӧ�۱����Ϣ  
	* 3��������getCountValue()   �����ҩƷ����Ҫ����ҩƷ����
	* 4��������insertData()  ���뵽orders_costs����
	* 5��������administration_costs() �����;��������;����üƼ���Ŀ ��ִ��4��
	* @author lll
	* @date 2012-12-25 
	*
	 */
	//1����ü۱��е���Ŀ���룬�Լ���Ŀ�Ĺ��
	public void first_getData(){
		String sqlString="SELECT "+
    					"PRICE_ITEM_NAME_DICT.ITEM_NAME,"+ //�۱���Ŀ����
    					"CLINIC_VS_CHARGE.CHARGE_ITEM_CLASS,"+ //�Ƽ���Ŀ���
    					"CLINIC_VS_CHARGE.CHARGE_ITEM_SPEC,"+ //�Ƽ���Ŀ���
    					"CLINIC_VS_CHARGE.CLINIC_ITEM_CODE,"+ //������Ŀ����
    					"CLINIC_VS_CHARGE.CHARGE_ITEM_CODE,"+ //�Ƽ���Ŀ����
    					"PRICE_ITEM_NAME_DICT.ITEM_CODE,"+ // �۱���Ŀ����
    					"CLINIC_VS_CHARGE.UNITS,"+ //��λ
    					"CLINIC_VS_CHARGE.AMOUNT,"+ //����
    					"CLINIC_VS_CHARGE.BACKBILL_RULE"+ //��̨�Ʒѹ���
    			 " FROM CLINIC_VS_CHARGE, CLINIC_ITEM_NAME_DICT, PRICE_ITEM_NAME_DICT"+
    			 " WHERE (CLINIC_VS_CHARGE.CLINIC_ITEM_CLASS ="+
    			        "CLINIC_ITEM_NAME_DICT.ITEM_CLASS) "+
    			   "and (CLINIC_VS_CHARGE.CLINIC_ITEM_CODE = CLINIC_ITEM_NAME_DICT.ITEM_CODE) "+
    			   "and (CLINIC_VS_CHARGE.CHARGE_ITEM_CLASS = PRICE_ITEM_NAME_DICT.ITEM_CLASS) "+
    			   "and (CLINIC_VS_CHARGE.CHARGE_ITEM_CODE = PRICE_ITEM_NAME_DICT.ITEM_CODE) "+
    			   "and ((CLINIC_ITEM_NAME_DICT.ITEM_CODE = '"+mAdviceEntity.order_code+"')) "+
    			   "and (PRICE_ITEM_NAME_DICT.STD_INDICATOR = 1) "+
    			   "and (CLINIC_ITEM_NAME_DICT.STD_INDICATOR = 1) "+
    			   "and (CLINIC_ITEM_NAME_DICT.ITEM_CLASS IN ('A', 'B', 'H','I','E','Z'))";
		ArrayList<DataEntity> dcbeacs=WebServiceHelper.getWebServiceData(sqlString);
		//ѭ���õ�clinic_item_code ��ֵ���Ա㸳��second_getData����
        String clinic_item_code=""; //������Ŀ����
	    for (int i = 0; i < dcbeacs.size(); i++) {
			clinic_item_code=dcbeacs.get(i).get("clinic_item_code").trim();
			item_name = dcbeacs.get(i).get("item_name").trim();
			backbill_rule = dcbeacs.get(i).get("backbill_rule").trim();
			item_class = dcbeacs.get(i).get("charge_item_class").trim();
			charge_item_code = dcbeacs.get(i).get("charge_item_code").trim();
			charge_item_spec= dcbeacs.get(i).get("charge_item_spec").trim();
			amount= dcbeacs.get(i).get("amount").trim();	
		}
		if (isDrug==0) {
			second_getData(clinic_item_code);//ҩƷ
			getCountValue();//����ҩƷ��������
			insertData();
			int sub_no=Integer.parseInt(mAdviceEntity.order_sub_no);
    	    if(sub_no<=1){
    	    	administration_costs();
    	    }
		}else {
			second_getData(charge_item_code);//��ҩƷ
			insertData();
		}
	}
	//2)�����Ŀ��Ӧ�۱����Ϣ
	public void second_getData(String item_code_temp){
		if(isDrug==0){
			item_spec = mAdviceEntity.drug_spec;//ҩƷ�Ĺ�񣬹�����ʵ�崫ֵ��mAdviceEntity-----------------------
		}else{
			item_spec = charge_item_spec;//��ҩƷ��õĹ����1���е�sql���õ�	
		}	
		String sqlString="SELECT PRICE_LIST.ITEM_CODE,"+
								"PRICE_LIST.ITEM_SPEC,"+
								"PRICE_LIST.ITEM_NAME,"+
								"PRICE_LIST.UNITS,"+
								"PRICE_LIST.PRICE"+
						" FROM PRICE_LIST"+
						" WHERE PRICE_LIST.ITEM_CLASS IN ('A', 'B', 'H','K','I','J','L','Z','E')"+
						" and PRICE_LIST.ITEM_CODE ='"+item_code_temp+"'"+
						" and sysdate >= START_DATE"+
						" and (PRICE_LIST.STOP_DATE IS NULL or SYSDATE <= STOP_DATE)"+
						" and item_spec ='"+item_spec+"'";
		ArrayList<DataEntity> dcbeacs=WebServiceHelper.getWebServiceData(sqlString);
		//ѭ���õ�clinic_item_code ��ֵ���Ա㸳��getCountValue����
		for (int i = 0; i < dcbeacs.size(); i++) {
			units =dcbeacs.get(i).get("units");	
			item_spec = dcbeacs.get(i).get("item_spec");
			price = dcbeacs.get(i).get("price");
			item_code = dcbeacs.get(i).get("item_code");
			if(price != null){
    			costs = price;
    		}else{
    			costs = "";
    		}
		}
	}
	//3�������ҩƷ����Ҫ����ҩƷ����
	public void getCountValue(){
    	String string = "select drug_dict.dose_per_unit, drug_dict.units, drug_dict.dose_units"+
    				    " from drug_dict, drug_price_list "+
    			        " where drug_price_list.drug_code = drug_dict.drug_code "+
    			          " and drug_price_list.drug_spec = drug_dict.drug_spec "+
    			          " and drug_price_list.stop_date is null  "+
    			          " and drug_price_list.drug_code = '"+mAdviceEntity.order_code+"'  "+
    			          " and  drug_price_list.drug_spec||drug_price_list.firm_id = '"+mAdviceEntity.drug_spec+"'";//------��ʵ�崫��-----
    	ArrayList<DataEntity> aList=WebServiceHelper.getWebServiceData(string);
		for (int i = 0; i < aList.size(); i++) {
			dose_per_unit=aList.get(i).get("dose_per_unit");//��λ����
		}
		if(dose_per_unit!= null && !dose_per_unit.equals("") ){
			double double1  =  Double.parseDouble(dose_per_unit);//��λ����
			double double2;//ҩƷʵ��ĵ��μ���
			if(mAdviceEntity.dosage.equals("")){
				double2 = 0.0;
			}else{
				double2 = Double.parseDouble(mAdviceEntity.dosage);
			}
			double  count = Math.ceil(double2/double1); // ������=���μ���/��λ����
			amount = String.valueOf(count);
			int int_count = (int)count; //������ת����int��
			//�����ۼƻ���
			if(price != null){
				float fcosts = Float.parseFloat(price);
				costs = String.valueOf(fcosts*int_count);
			}
		}
		amount= amount.trim();
//		insertData();		
    }
	//4�����뵽orders_costs����
	public void insertData(){	    	
	    	if(units == null||units == " "){
	    		units = "";
	    	}
	    	if(amount == null||amount == " "){
	    		amount = "";
	    	}
	    	if(item_spec == null||item_spec == " "){
	    		item_spec = "";
	    	}
	    	if(item_code == null||item_code == " "){
	    		item_code = "";
	    	}
	    	int sub_no=Integer.parseInt(mAdviceEntity.order_sub_no);
	    	if(sub_no>1){
	    		String sqlQuery="select max(item_no) from orders_costs  "+
	    	                    "where patient_id ='"+patientEntity.patient_id+"' "+
	    				        " and order_no='"+mAdviceEntity.order_no+"'";
	    		ArrayList<DataEntity> cList=WebServiceHelper.getWebServiceData(sqlQuery);	    		
	    		for (int i = 0; i < cList.size(); i++) {
	    			String numberStr=cList.get(i).get("max(item_no)").trim();
	    			if (!numberStr.equals("")) {
	    				int number=Integer.parseInt(numberStr)+1;
	    				item_no=String.valueOf(number);
					}				
				}
	    	}
	    	String sql="insert into orders_costs ("+ 
	    			"item_name,"+  //�Ƽ���Ŀ����
	    			"amount,"+      //����
	    			"units,"+       //�Ƽ۵�λ
	    			"item_code,"+   //�Ƽ���Ŀ����
	    			"item_class,"+  //�Ƽ���Ŀ���
	    			"item_no,"+     //�Ƽ���Ŀ���
	    			"order_sub_no,"+//ҽ������� 
	    			"order_no,"+    //ҽ����� 
	    			"visit_id,"+    //���˱���סԺ��ʶ 
	    			"patient_id,"+ //���˱�ʶ�� 
	    			"costs,"+      //����Ŀ�ۼƷ��� 
	    			"item_spec,"+    //�Ƽ���Ŀ��� 
	    			"backbill_rule )"+ //�Ƽ۹���
	    	" values ( '"+item_name+"', '"+amount+"', '"+units+"', '"+item_code+"','"+item_class+"',"+
	    			 "'"+item_no+"', '"+mAdviceEntity.order_sub_no+"', '"+mAdviceEntity.order_no+"'," +
	    			 "'"+patientEntity.visit_id+"', '"+patientEntity.patient_id+"','"+costs+"',"+
	    			 "'"+item_spec+"', '"+backbill_rule+"')";
	    	WebServiceHelper.insertWebServiceData(sql);
	}
	//5������;����õļƼ���Ŀ
	public void  administration_costs(){
		//���;����Ϊ�գ� ��ͨ���������ȡ��  ��Ŀ�����Ŀ���롢��Ŀ��� 
		String sql="SELECT PRICE_ITEM_NAME_DICT.ITEM_NAME, "+ //�۱���Ŀ����
						"CLINIC_VS_CHARGE.CHARGE_ITEM_CLASS, "+  //�Ƽ���Ŀ���
						"CLINIC_VS_CHARGE.CHARGE_ITEM_SPEC, "+ //�Ƽ���Ŀ���
						"CLINIC_VS_CHARGE.CLINIC_ITEM_CODE, "+  //������Ŀ����
						"CLINIC_VS_CHARGE.CHARGE_ITEM_CODE,  "+ //�Ƽ���Ŀ����
						"PRICE_ITEM_NAME_DICT.ITEM_CODE,  "+ //�۱����
						"CLINIC_VS_CHARGE.UNITS,  "+ //���
						"CLINIC_VS_CHARGE.AMOUNT, "+ //����
						"CLINIC_VS_CHARGE.BACKBILL_RULE "+  // ��̨�Ʒѹ���
				  " FROM CLINIC_VS_CHARGE, CLINIC_ITEM_NAME_DICT, PRICE_ITEM_NAME_DICT "+ 
				  "WHERE (CLINIC_VS_CHARGE.CLINIC_ITEM_CLASS = CLINIC_ITEM_NAME_DICT.ITEM_CLASS) "+ 
				    "and (CLINIC_VS_CHARGE.CLINIC_ITEM_CODE = CLINIC_ITEM_NAME_DICT.ITEM_CODE) "+ 
				    "and (CLINIC_VS_CHARGE.CHARGE_ITEM_CLASS = PRICE_ITEM_NAME_DICT.ITEM_CLASS) "+ 
				    "and (CLINIC_VS_CHARGE.CHARGE_ITEM_CODE = PRICE_ITEM_NAME_DICT.ITEM_CODE) "+ 
				    "and ((CLINIC_ITEM_NAME_DICT.ITEM_NAME = '"+mAdviceEntity.administration+"')) "+ 
				    "and (PRICE_ITEM_NAME_DICT.STD_INDICATOR = 1) "+ 
				    "and (CLINIC_ITEM_NAME_DICT.ITEM_CLASS = 'E') ";
	    ArrayList<DataEntity> blist=WebServiceHelper.getWebServiceData(sql);
	    for (int i = 0; i < blist.size(); i++) {
	    	charge_item_class=blist.get(i).get("charge_item_class");
	    	charge_item_code=blist.get(i).get("charge_item_code");
	    	charge_item_spec = blist.get(i).get("charge_item_spec");
	    	backbill_rule=blist.get(i).get("backbill_rule");
	    	amount = blist.get(i).get("amount").trim();
	    	item_name = blist.get(i).get("item_name");
	    	//ͨ�� ��Ŀ�����Ŀ���롢��Ŀ��� ���������ȡ���۸�
	    	String sqlprice = "select price_list.item_code,"+
	     		                    " price_list.item_spec,"+
	     		                    " price_list.item_name,"+
	     		                    " price_list.units,"+
	     		                    " price_list.price,"+
	     		                    " price_list.item_class"+
	     		              "  from price_list"+
	     		              " where price_list.item_class = '"+charge_item_class+"'"+
	     		              "   and price_list.item_code = '"+charge_item_code+"'"+
	     		              "   and sysdate >= start_date"+
	     		              "   and (price_list.stop_date is null or sysdate <= stop_date)"+
	     		              "   and item_spec = '"+charge_item_spec+"'";
	     	ArrayList<DataEntity> cList=WebServiceHelper.getWebServiceData(sqlprice);    	
	    	item_no=String.valueOf(((i+1)+1));
	    	for (int j = 0; j < cList.size(); j++) {
	    		item_spec = cList.get(j).get("item_spec");
		    	item_name = cList.get(j).get("item_name");
		    	item_class = cList.get(j).get("item_class");
		    	item_code = cList.get(j).get("item_code");
		    	costs = cList.get(j).get("price");
		    	units =  cList.get(j).get("units");
		    	insertData();//���뵽orders_costs
			}
	    	
	    }
	}
}
