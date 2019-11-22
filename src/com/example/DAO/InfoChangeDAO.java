package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InfoChangeDAO {

	private static InfoChangeDAO infoChangeDAO = null;

	private MainDAO mainDAO = null;
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	private InfoChangeDAO() {
		if (mainDAO == null) {
			mainDAO = MainDAO.getInstance();
		}
	}

	public static InfoChangeDAO getInstance() {
		if (infoChangeDAO == null) {
			infoChangeDAO = new InfoChangeDAO();
		}
		return infoChangeDAO;
	}
	
	

	// 修改密码
	public boolean changeStaffPWD(String staff_id, String oldpwd, String newpwd) throws Exception {
		boolean isSuccess = false;

		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select pwd from staff where staff_id = '" + staff_id + "'";
		resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			String pwd = resultSet.getString("pwd");
			if (oldpwd.equals(pwd)) {
				sql = "update staff set pwd = '" + newpwd + "' " + "where staff_id='" + staff_id + "'";
				int result = statement.executeUpdate(sql);
				isSuccess = (result != 0);
			} else {
				throw new Exception("原密码输入错误!");
			}
		} else {
			throw new Exception("没有找到该用户!");
		}
		return isSuccess;
	}

	// 修改手机号
	public boolean changeStaffTelAge(String staff_id, String newtel,int age) throws SQLException {
		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "update staff set staff_tel = '" + newtel + "',age = '" + age + "' "
				+ " where staff_id='" + staff_id + "'";
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);
		return isSuccess;
	}
	
	// 修改密码
		public boolean changeStaff(String staff_id, int age,String tel, String oldpwd, String newpwd) throws Exception {
			boolean isSuccess = false;

			if (connection == null) {
				connection = mainDAO.connectToDB();
			}
			if (statement == null) {
				statement = connection.createStatement();
			}
			String sql = "select pwd from staff where staff_id = '" + staff_id + "'";
			resultSet = statement.executeQuery(sql);
			if (resultSet.next()) {
				String pwd = resultSet.getString("pwd").trim();
				if (oldpwd.equals(pwd)) {
					sql = "update staff set pwd = '" + newpwd + "' " + ""
							+ ",age= '"+ age + "', staff_tel='" + tel + "' "
							+ "where staff_id='" + staff_id + "'";
					int result = statement.executeUpdate(sql);
					isSuccess = (result != 0);
				} else {
					throw new Exception("原密码输入错误!");
				}
			} else {
				throw new Exception("没有找到该用户!");
			}
			return isSuccess;
		}
	

}
