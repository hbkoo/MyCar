package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.beans.ServeForm;

public class ServeFormDAO {

	private static ServeFormDAO serveFormDAO = null;
	private MainDAO mainDAO = null;

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	public static ServeFormDAO getInstance() {
		if (serveFormDAO == null) {
			serveFormDAO = new ServeFormDAO();
		}
		return serveFormDAO;
	}

	private ServeFormDAO() {
		mainDAO = MainDAO.getInstance();
	}

	//新增一条售后服务单
	public boolean addServeForm(String staff_id,String staff_name,String sell_id,String satisfy) throws SQLException {
		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String serve_id = getNewSeveID();
		String sql = "insert into serve_form values(?,?,?,?,?,?)";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, serve_id);
		pStatement.setString(2, staff_id);
		pStatement.setString(3, sell_id);
		pStatement.setString(4, mainDAO.getCurrentDate());
		pStatement.setString(5, satisfy);
		pStatement.setString(6, staff_name);
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;
	}

	//售后服务单的修改
	public boolean changeServeForm(String serve_id,String satisfy) throws SQLException {
		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}		
		String sql = "update serve_form set satisfy='" + satisfy + "'"
				+ "where serve_id='" + serve_id + "'";
		if (statement == null) {
			statement = connection.createStatement();
		}
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);		
		return isSuccess;		
	}
	
	public List<ServeForm> getServeForms() throws SQLException {
		return getServeForms("%", "%", "2000-1-1", mainDAO.getCurrentDate(), "%");
	}
	
	//查询售后单
	public List<ServeForm> getServeForms(String server_id,String sell_id,String start_time,
			String end_time,String satisfy) throws SQLException {		
		List<ServeForm> serveForms = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}		
		String sql = "select * from serve_form where server_id like ? and sell_id like ? "
				+ "and serve_time >= ? and serve_time <= ? and satisfy like ?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, server_id+"%");
		pStatement.setString(2, sell_id+"%");
		pStatement.setString(3, start_time);
		pStatement.setString(4, end_time);
		pStatement.setString(5, satisfy+"%");
		resultSet = pStatement.executeQuery();
		ServeForm serveForm;
		while (resultSet.next()) {
			String serve_id = resultSet.getString(1).trim();
			String serverId = resultSet.getString(2).trim();
			String sellId = resultSet.getString(3).trim();
			String serveTime = resultSet.getString(4).trim();
			String satisfy1 = resultSet.getString(5).trim();
			String serverName = resultSet.getString("server_name").trim();
			serveForm = new ServeForm(serve_id, serverId, sellId, serveTime, satisfy1,serverName);
			serveForms.add(serveForm);			
		}		
		return serveForms;		
	}
	
	// 获取一个新的售后服务号
	private String getNewSeveID() throws SQLException {

		String date = mainDAO.getCurrentDate();
		String serve_id = date + "10"; // 10是售后单号的标记

		if (connection == null) {
			mainDAO.connectToDB();
		}

		if (statement == null) {
			statement = connection.createStatement();
		}
		

		// 查询以今天日期开头的订单号
		String sql = "select serve_id from serve_form where serve_id like '" + date + "%' order by serve_id DESC";
		resultSet = statement.executeQuery(sql);

		String id = null;
		if (resultSet.next()) {
			id = resultSet.getString("serve_id").trim();
			String last = id.substring(10, 13);// 截取最后的三位
			int num = Integer.valueOf(last); // 将最后三位转为整数
			serve_id += String.format("%03d", ++num); // 最后三位整数加一
		} else {
			// 今天还没有订单
			serve_id += "001";
		}
		// System.out.println(FSid);
		return serve_id;
	}

}
