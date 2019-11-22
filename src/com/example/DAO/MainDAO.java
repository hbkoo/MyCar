package com.example.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.crypto.Data;

import com.example.beans.Staff;

public class MainDAO {

	private static MainDAO carsDAO = null;

	private String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver"; // 数据库驱动
	private String dbURL = "jdbc:sqlserver://localhost:1433;DatabaseName="; // 数据库地址
	private String userName = "myself"; // 用户名
	private String password = "123456"; // 密码

	private String user_dbname, user_name, user_pwd;

	private Connection connection = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	public static MainDAO getInstance() {
		if (carsDAO == null) {
			carsDAO = new MainDAO();
		}
		return carsDAO;
	}

	private MainDAO() {
		user_dbname = "car";
		user_name = userName;
		user_pwd = password;
	}

	public Connection connectToDB() throws SQLException {
		if (connection == null) {
			connection = connectToDB("car", userName, password);
		}
		return connection;
	}

	public Connection connectToDB(String userName, String password) throws SQLException {
		if (connection == null) {
			connection = connectToDB("car", userName, password);
		}
		return connection;
	}

	public Connection connectToDB(String databaseName, String userName, String password) throws SQLException {
		if (connection != null) {
			return connection;
		}
		user_dbname = databaseName;
		user_name = userName;
		user_pwd = password;
		try {
			Class.forName(driverName);
			dbURL = dbURL + databaseName;
			connection = DriverManager.getConnection(dbURL, userName, password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	// 登录界面查找用户
	public Staff searchStaff(String name, String password) throws SQLException {

		Staff staff = null;

		if (connection == null) {
			connection = connectToDB(user_dbname, user_name, user_pwd);
		}

		String sql = "select * from staff where staff_id = ? and pwd = ? ";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, name);
		pStatement.setString(2, password);
		resultSet = pStatement.executeQuery();

		if (resultSet.next()) {
			String staff_id = resultSet.getString("staff_id").trim();
			String pwd = resultSet.getString("pwd").trim();
			String staff_name = resultSet.getString("staff_name").trim();
			String staff_sex = resultSet.getString("staff_sex").trim();
			String staff_tel = resultSet.getString("staff_tel").trim();
			int age = resultSet.getInt("age");
			String role = resultSet.getString("role").trim();
			staff = new Staff(staff_id, pwd, staff_name, staff_sex, staff_tel, age, role);
		}
		return staff;
	}


	// 获取今天的日期
	public String getCurrentDate() {
		String date;
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String time = timestamp.toString();
		String s[] = time.split(" ");
		String d[] = s[0].split("-");
		date = d[0] + d[1] + d[2];// 获取今天的日期
		return date;
	}

}
