package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
* @ClassName: CheckEntity 
* @Description: TODO(���ʵ��) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 ����7:37:51 
*
 */
public class CheckEntity implements Serializable{

	/** 
	* @user - @date 
	*/
	private static final long serialVersionUID = -7091365283485985530L;
	public String exam_no;//�������
    public String exam_item;//�����Ŀ
    public String exam_class;//������C
    public String exam_sub_class;//������ࣨC��
    public String req_date_time;//�������ڼ�ʱ�� ��Da
    public String req_physician;//����ҽ����C��
    public String report_date_time;//�������ڼ�ʱ�䣨Date�� 
    public String reporter;//�����ߣ�C��
    public String description;//���������C��
    public String impression;//ӡ��C��
    
    public CheckEntity(){
    	
    }
    
    public static ArrayList<CheckEntity> init(ArrayList<DataEntity> dataList){
    	ArrayList<CheckEntity> list=new ArrayList<CheckEntity>();
		for (int i = 0; i < dataList.size(); i++) {
			CheckEntity entity=new CheckEntity();
			entity.exam_no=dataList.get(i).get("exam_no");
			entity.exam_item=dataList.get(i).get("exam_item");
			entity.exam_class=dataList.get(i).get("exam_class");
			entity.exam_sub_class=dataList.get(i).get("exam_sub_class");
			entity.req_date_time=dataList.get(i).get("req_date_time");
			entity.req_physician=dataList.get(i).get("req_physician");
			entity.report_date_time=dataList.get(i).get("report_date_time");
			entity.reporter=dataList.get(i).get("reporter");
			entity.description=dataList.get(i).get("description");
			entity.impression=dataList.get(i).get("impression");
			list.add(entity);
		}
		return list;
    }
}
