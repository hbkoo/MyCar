package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.beans.Custom;
import com.example.beans.Staff;

public class UserManageDAO {
	
	private static UserManageDAO userManageDAO = null;
	
	private MainDAO mainDAO = null;
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;
	
	private UserManageDAO() {
		mainDAO = MainDAO.getInstance();
	}
	
	public static UserManageDAO getInstance() {
		if (userManageDAO == null) {
			userManageDAO = new UserManageDAO();
		}
		return userManageDAO;
	}
	
	public List<Staff> getAllStaff() throws SQLException {
		List<Staff>staffs = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select * from staff";
		resultSet = statement.executeQuery(sql);
		Staff staff;
		while(resultSet.next()) {
			
			String staff_id = resultSet.getString("staff_id").trim();
			String pwd = resultSet.getString("pwd").trim();
			String name = resultSet.getString("staff_name").trim();
			String sex = resultSet.getString("staff_sex").trim();
			String tel = resultSet.getString("staff_tel").trim();
			int age = resultSet.getInt("age");
			String role = resultSet.getString("role").trim();
			staff = new Staff(staff_id, pwd, name, sex, tel, age, role);
			staffs.add(staff);
		}
		return staffs;
		
	}
	
	//新增新职工
	public String addNewStaff(String name, String sex,String tel,int age,String role) throws SQLException {
		String staff_id = getNewStaffID();		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "insert into staff values(?,?,?,?,?,?,?)";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, staff_id);
		pStatement.setString(2, "123456");
		pStatement.setString(3, name);
		pStatement.setString(4, sex);
		pStatement.setString(5, tel);
		pStatement.setInt(6, age);
		pStatement.setString(7, role);
		int result = pStatement.executeUpdate();
		if (result != 0) {
			return staff_id;
		} else {
			return null;
		}
	}

	//对现有职工修改部门
	public boolean changeStaffRole(String staff_id,String role) throws SQLException {
		boolean isSuccess = false;
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		
		String sql = "update staff set role = '" + role + "' "
				+ " where staff_id = '" + staff_id + "'";
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);		
		return isSuccess;
	}
	
	//对现有职工离职
	public boolean deleteStaff(String staff_id) throws SQLException {
		boolean isSuccess = false;
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		
		String sql = "delete from staff "
				+ " where staff_id = '" + staff_id + "'";
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);		
		return isSuccess;
	}
	
	//获取所有的顾客信息
	public List<Custom> getAllCustom() throws SQLException {
		List<Custom> customs = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select * from custom";
		resultSet = statement.executeQuery(sql);
		Custom custom;
		while (resultSet.next()) {			
			int custom_id = resultSet.getInt("custom_id");
			String name = resultSet.getString("custom_name").trim();
			String sex = resultSet.getString("custom_sex").trim();
			String tel = resultSet.getString("custom_tel").trim();
			String address = resultSet.getString("address").trim();
			custom = new Custom(custom_id, name, sex, tel, address);
			customs.add(custom);
		}
		return customs;
	}
	
	private String getNewStaffID() throws SQLException {	
		String staff_id="";
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select staff_id from staff order by staff_id DESC";
		resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			String id = resultSet.getString("staff_id").trim();
			int num = Integer.valueOf(id);
			staff_id = String.format("%04d", ++num);
		} else {
			staff_id = "0000";
		}
		return staff_id;
	}

}
