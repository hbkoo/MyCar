package com.example.beans;

public class Staff {

	private String id;   //职工号
	private String pwd;  //职工密码
	private String name; //职工姓名
	private String sex;  //职工性别
	private String tel;  //职工电话
	private int age;     //职工年龄
	private String role; //职工角色
	
	public Staff() {}
	
	public Staff(String id, String pwd, String name, String sex,
			String tel, int age, String role) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.sex = sex;
		this.tel = tel;
		this.age = age;
		this.role = role;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Staff [id=" + id + ", pwd=" + pwd + ", name=" + name + ", sex=" + sex + ", tel=" + tel + ", age=" + age
				+ ", role=" + role + "]";
	}
	
	
	
}
