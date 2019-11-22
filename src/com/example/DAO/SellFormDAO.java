package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.beans.Custom;
import com.example.beans.SellForm;

public class SellFormDAO {

	private static SellFormDAO sellFormDAO = null;
	private MainDAO mainDAO = null;

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	public static SellFormDAO getInstance() {
		if (sellFormDAO == null) {
			sellFormDAO = new SellFormDAO();
		}
		return sellFormDAO;
	}

	private SellFormDAO() {
		mainDAO = MainDAO.getInstance();
	}

	// 新增售车订单
	public boolean addSellForm(Custom custom, String staff_id, String staff_name, String model, int count) throws SQLException {
		boolean isSuccess = false;

		if (connection == null) {
			connection = mainDAO.connectToDB();
		}

		String sell_id = getNewSellID();
		String sql = "insert into sell_form values(?,?,?,?,?,?,?,?,?,?,?)";
		pStatement = connection.prepareStatement(sql);

		pStatement.setString(1, sell_id);
		pStatement.setString(2, staff_id);
		pStatement.setString(3, null);
		pStatement.setString(4, model);
		pStatement.setString(5, mainDAO.getCurrentDate());
		pStatement.setInt(6, count);
		pStatement.setString(7, "否");
		pStatement.setInt(8, custom.getId());
		pStatement.setString(9, staff_name);
		pStatement.setString(10, null);
		pStatement.setString(11, custom.getName());

		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);

		return isSuccess;
	}

	// 对未审核的订单进行修改
	public boolean changeSellForm(String sell_id, String model, int count) throws SQLException {
		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "update sell_form set model_id=? , out_count=? " + "where sell_id=? and out_tag='否' ";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, model);
		pStatement.setInt(2, count);
		pStatement.setString(3, sell_id);
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;
	}

	//对未审核的订单进行删除
	public boolean deleteSellForm(String sell_id) throws SQLException {
		
		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}		
		String sql = "delete from sell_form where sell_id = '" + sell_id + "'";
		if (statement == null) {
			statement = connection.createStatement();
		}
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);		
		return isSuccess;
		
	}
	
	private List<String> getAllServedSellId() throws SQLException {
		List<String> sellids = new ArrayList<>();
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}

		String sql = "select sell_id from serve_form";
		resultSet = statement.executeQuery(sql);
		String sellid;
		while(resultSet.next()) {
			sellid = resultSet.getString("sell_id").trim();
			sellids.add(sellid);
		}
		return sellids;
	}
	
	public List<SellForm> getSellFormsNoServedByTag(String tag) throws SQLException {
		List<String> sellIds = getAllServedSellId();
		List<SellForm> sellForms = getSellForms("%","%", "%", "%", "%", "2000-01-01", mainDAO.getCurrentDate(), tag);
		List<SellForm> finalSellForms = new ArrayList<>();
		for(SellForm sellForm : sellForms) {
			if (!sellIds.contains(sellForm.getId())) {
				finalSellForms.add(sellForm);
			}
		}
		return finalSellForms;
	}
	
	public List<SellForm> getSellFormsByTag(String tag) throws SQLException {
		return getSellForms("%","%", "%", "%", "%", "2000-01-01", mainDAO.getCurrentDate(), tag);
	}
	
	public List<SellForm> getSellForms(String salesman_id,String tag) throws SQLException {
		return getSellForms("%",salesman_id, "%", "%", "%", "2000-1-1", mainDAO.getCurrentDate(), tag);
	}
	
	public SellForm getSellFormBySellID(String sell_id) throws SQLException {
		List<SellForm> sellForms = getSellForms(sell_id,"%", "%", "%", "%", "2000-01-01", mainDAO.getCurrentDate(), "%");
		if (sellForms.size() == 0) {
			return null;
		}
		return sellForms.get(0);
	}
	
	public List<SellForm> getSellForms(String salesman_id,String rep_id,String model_id,
			String start_time,String end_time,String out_tag) throws SQLException {
		return getSellForms("%",salesman_id,rep_id, model_id, "%", start_time, end_time , out_tag);
	}
	
	//查询销售订单
	public List<SellForm> getSellForms(String sell_id,String salesman_id,String rep_id,String model_id,
			String custom_id,String start_time,String end_time,String out_tag) throws SQLException{
		List<SellForm> sellForms = new ArrayList<>();
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}		
		String sql = "select * from sell_form where salesman_id like ? and (rep_id like ? or rep_id is NULL) "
				+ "and model_id like ? and custom_id like ? and out_time >= ? "
				+ "and out_time <= ? and out_tag like ? and sell_id like ?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, salesman_id + "%");
		pStatement.setString(2, rep_id + "%");
		pStatement.setString(3, model_id + "%");
		pStatement.setString(4, custom_id + "%");
		pStatement.setString(5, start_time);
		pStatement.setString(6, end_time);
		pStatement.setString(7, out_tag + "%");
		pStatement.setString(8, sell_id + "%");
		resultSet = pStatement.executeQuery();
		SellForm sellForm;
		while(resultSet.next()) {
			String sellId = resultSet.getString("sell_id").trim();
			String salesmanId = resultSet.getString("salesman_id").trim();
			String repId = resultSet.getString("rep_id");
			if (repId == null) {
				repId = "暂未处理";
			} else {
				repId = repId.trim();
			}
			String modelId = resultSet.getString("model_id").trim();
			String time = resultSet.getString("out_time").trim();
			int count = resultSet.getInt("out_count");
			String tag = resultSet.getString("out_tag").trim();
			int customId = resultSet.getInt("custom_id");
			String salesmanName = resultSet.getString("salesman_name").trim();
			String repositoryName = resultSet.getString("rep_name");
			if (repositoryName == null) {
				repositoryName = "暂未处理";
			} else {
				repositoryName = repositoryName.trim();
			}
			String customName = resultSet.getString("custom_name").trim();
			sellForm = new SellForm(sellId, salesmanId, repId, modelId, customId, count, time, tag, salesmanName, repositoryName, customName);
			sellForms.add(sellForm);
		}	
		return sellForms;
		
	}
	
	// 新增客户信息
	public boolean addCustom(String name, String sex, String tel, String address) throws SQLException {

		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "insert into custom values(?,?,?,?)";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, name);
		pStatement.setString(2, sex);
		pStatement.setString(3, tel);
		pStatement.setString(4, address);
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;

	}

	// 获取顾客信息
	public Custom getCustomById(int id) throws SQLException {
		
		Custom custom = null;
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		
		String sql = "select * from custom where custom_id='" + id + "'";
		resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			String name = resultSet.getString("custom_name").trim();
			String sex = resultSet.getString("custom_sex").trim();
			String tel = resultSet.getString("custom_tel").trim();
			String address = resultSet.getString("address").trim();
			custom = new Custom(id, name, sex, tel, address);
		}
		return custom;
	}
	
	// 修改顾客信息
	public boolean changeCustomer(Custom custom) throws SQLException {

		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "update custom set custom_name=?,custom_sex=?,custom_tel=?,address=? " + " where custom_id=?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, custom.getName());
		pStatement.setString(2, custom.getSex());
		pStatement.setString(3, custom.getTel());
		pStatement.setString(4, custom.getAddress());
		pStatement.setInt(5, custom.getId());
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;

	}

	// 获取一个新的售车编号
	public String getNewSellID() throws SQLException {

		String date = mainDAO.getCurrentDate();
		String sell_id = date + "01"; // 01是售车单号的标记
		if (connection == null) {
			mainDAO.connectToDB();
		}
		statement = connection.createStatement();
		// 查询以今天日期开头的订单号
		String sql = "select sell_id from sell_form where sell_id like '" + date + "%' order by sell_id DESC";
		resultSet = statement.executeQuery(sql);
		String id = null;
		if (resultSet.next()) {
			id = resultSet.getString("sell_id").trim();
			String last = id.substring(10, 13);// 截取最后的三位
			int num = Integer.valueOf(last); // 将最后三位转为整数
			sell_id += String.format("%03d", ++num); // 最后三位整数加一
		} else {
			// 今天还没有订单
			sell_id += "001";
		}
		return sell_id;
	}

}
