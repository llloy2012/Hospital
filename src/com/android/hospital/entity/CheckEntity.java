package com.android.hospital.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 
* @ClassName: CheckEntity 
* @Description: TODO(检查实体) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-18 下午7:37:51 
*
 */
public class CheckEntity implements Serializable{

	/** 
	* @user - @date 
	*/
	private static final long serialVersionUID = -7091365283485985530L;
	public String exam_no;//申请序号
    public String exam_item;//检查项目
    public String exam_class;//检查类别（C
    public String exam_sub_class;//检查子类（C）
    public String req_date_time;//申请日期及时间 （Da
    public String req_physician;//申请医生（C）
    public String report_date_time;//报告日期及时间（Date） 
    public String reporter;//报告者（C）
    public String description;//检查所见（C）
    public String impression;//印象（C）
    
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
