package com.android.hospital.entity;

import java.util.ArrayList;

/**
 * 
* @ClassName: SignsLifeEntity 
* @Description: TODO(��������ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2013-1-5 ����8:29:04 
*
 */
public class SignsLifeEntity {

	public String recording_date;//��¼����(D) ���磺2008-11-16
	
	public String time_point;//ʱ���(D) ���磺2008-11-23 6:44:45
	
	public String  vital_signs;//��Ŀ����(C)
	
	public String  vital_signs_cvalues;//��Ŀֵ(N)
	
	public String units;//��λ(C)
	
	public String nurse;//��¼��(C)
	
	public SignsLifeEntity(){}
	
	
	public static ArrayList<SignsLifeEntity> init(ArrayList<DataEntity> dataList){
    	ArrayList<SignsLifeEntity> list=new ArrayList<SignsLifeEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			SignsLifeEntity entity=new SignsLifeEntity();
			entity.recording_date=dataList.get(i).get("recording_date");
			entity.time_point=dataList.get(i).get("time_point");
			entity.vital_signs=dataList.get(i).get("vital_signs");
			entity.vital_signs_cvalues=dataList.get(i).get("vital_signs_cvalues");
			entity.units=dataList.get(i).get("units");
			entity.nurse=dataList.get(i).get("nurse");
			list.add(entity);
		}
		return list;
    }
}
