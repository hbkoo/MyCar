package com.example.beans;

public class Custom {

	private int id;      //顾客号
	private String name;    //顾客姓名
	private String sex;     //顾客性别
	private String tel;     //顾客电话
	private String address; //顾客住址
	
	public Custom() {}
	
	public Custom(int id, String name, String sex, 
			String tel, String address) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.tel = tel;
		this.address = address;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Custom [id=" + id + ", name=" + name + ", sex=" + sex + ", tel=" + tel + ", address=" + address + "]";
	}
	
	
	
}
