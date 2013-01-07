package com.android.hospital.entity;

import java.io.Serializable;

public class DataObjectEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6342770665544341374L;

	private String key;
	private String value;
	
	public DataObjectEntity(){
		
	}
	
	public DataObjectEntity(String key,String value){
		this.key=key;
		this.value=value;
		
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}
    /**
     * 
    * @Title: setValue 
    * @Description: TODO(����ӷ�������õ�ĳ���ֶ�Ϊ�ջ���==null����ֵΪ���ַ���); 
    * @param @param value    �趨�ļ� 
    * @return void    �������� 
    * @throws
     */
	public void setValue(String value) {
		value=value.trim();
		if (value==null||value.equals("null")) {
			value="";
		}
		this.value = value;
	}
	
	
}
