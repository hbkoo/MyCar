package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.beans.InForm;

public class InFormsDAO {

	private static InFormsDAO inFormsDAO = null;
	private MainDAO mainDAO = null;

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	public static InFormsDAO getInstance() {
		if (inFormsDAO == null) {
			inFormsDAO = new InFormsDAO();
		}
		return inFormsDAO;
	}

	private InFormsDAO() {
		if (mainDAO == null) {
			mainDAO = MainDAO.getInstance();
		}
	}

	// 新增进车订单
	public boolean addInForm(String staff_id, String staff_name, String model, int count) throws SQLException {

		// System.out.println("add inform is called");
		boolean isSuccess = false;
		String in_id = getNewInID();
		// System.out.println("new_id:" + in_id);
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "insert into in_form values(?,?,?,?,?,?,?,?,?)";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, in_id);
		pStatement.setString(2, staff_id);// 进车职工号,salesman
		pStatement.setString(3, null); // 仓库审核职工号
		pStatement.setString(4, model);
		pStatement.setString(5, mainDAO.getCurrentDate());
		pStatement.setInt(6, count);
		pStatement.setString(7, "否");
		pStatement.setString(8, staff_name);
		pStatement.setString(9, null);
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;
	}

	// 对未审核的订单进行修改
	public boolean changeInForm(String in_id, String model, int count) throws SQLException {

		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "update in_form set model_id=? , in_count=? where in_id = ? and in_tag='否'";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, model);
		pStatement.setInt(2, count);
		pStatement.setString(3, in_id);
		int result = pStatement.executeUpdate();
		isSuccess = (result != 0);
		return isSuccess;
	}

	// 对未审核的订单进行删除
	public boolean deleteInForm(String in_id) throws SQLException {

		boolean isSuccess = false;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "delete from in_form where in_id = '" + in_id + "' and in_tag='否'";
		statement = connection.createStatement();
		int result = statement.executeUpdate(sql);
		isSuccess = (result != 0);
		return isSuccess;

	}

	//获取所有的职工号和姓名
	public Map<String, List<String>> getStaffIdAndNameByRole(String role) throws SQLException {
		Map<String, List<String>> salesmans = new HashMap<>();
		
		List<String> ids = new ArrayList<>();
		List<String> names = new ArrayList<>();
		
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select staff_id,staff_name from staff where role like '" + role + "%'";
		resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			String staff_id = resultSet.getString("staff_id");
			String staff_name = resultSet.getString("staff_name");
			ids.add(staff_id);
			names.add(staff_name);
		}
		salesmans.put("staff_id", ids);
		salesmans.put("staff_name", names);
		return salesmans;
	}

	// 查询所有的进车单
	public List<InForm> getInforms() throws SQLException {
		System.out.println("in getInforms() function");
		return getInForms("%", "%", "%", "1997-1-1", mainDAO.getCurrentDate(), "%");
	}

	// 根据处理标记查找进车单
	public List<InForm> getInformsByTag(String in_tag) throws SQLException {
		return getInForms("%", "%", "%", "1997-1-1", mainDAO.getCurrentDate(), in_tag);
	}

	// 根据时间段查找进车单
	public List<InForm> getInformsByTime(String start_time, String end_time) throws SQLException {
		return getInForms("%", "%", "%", start_time, end_time, "%");
	}

	// 通过业务员查找进车单
	public List<InForm> getInformsBySalesmanID(String salesman_id) throws SQLException {
		return getInForms(salesman_id, "%", "%", "1997-1-1", mainDAO.getCurrentDate(), "%");
	}
	
	public List<InForm> getInformsBySalesmanIDTag(String salesman_id,String tag) throws SQLException {
		return getInForms(salesman_id, "%", "%", "1997-1-1", mainDAO.getCurrentDate(), tag);
	}
	
	public List<InForm> getInForms(String saleaman_id, String start_time,String end_time, String in_tag) throws SQLException {
		return getInForms(saleaman_id, "%", "%", start_time, end_time, in_tag);
	}

	// 根据多个条件查询进车订单
	public List<InForm> getInForms(String saleaman_id, String rep_id, String model_id, String start_time,
			String end_time, String in_tag) throws SQLException {

		List<InForm> inForms = new ArrayList<>();

		if (connection == null) {
			connection = mainDAO.connectToDB();
		}

		String sql = "select * from in_form where salesman_id like ? and (rep_id like ? or rep_id is NULL)"
				+ " and model_id like ? and in_time>=? and in_time<=? and in_tag like ?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, saleaman_id + "%");
		pStatement.setString(2, rep_id + "%");
		pStatement.setString(3, model_id + "%");
		pStatement.setString(4, start_time);
		pStatement.setString(5, end_time);
		pStatement.setString(6, in_tag + "%");
		resultSet = pStatement.executeQuery();
		InForm inForm;
		while (resultSet.next()) {
			String inId = resultSet.getString("in_id").trim();
			String salesmanId = resultSet.getString("salesman_id").trim();
			String repId = resultSet.getString("rep_id");
			if (repId == null) {
				repId = "暂未处理";
			} else {
				repId = repId.trim();
			}
			String modelId = resultSet.getString("model_id").trim();
			String inTime = resultSet.getString("in_time").trim();
			int count = resultSet.getInt("in_count");
			String tag = resultSet.getString("in_tag").trim();
			String salesmanName = resultSet.getString("salesman_name").trim();
			String repositoryName = resultSet.getString("rep_name");
			if (repositoryName == null) {
				repositoryName = "暂未处理";
			} else {
				repositoryName = repositoryName.trim();
			}
			inForm = new InForm(inId, salesmanId, repId, modelId, inTime, count, tag, salesmanName, repositoryName);
			inForms.add(inForm);
		}
		return inForms;
	}

	// 获取所有的轿车型号
	public List<String> getAllModels() throws SQLException {
		List<String> models = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select model_id from cars";
		resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			String model = resultSet.getString("model_id").trim();
			models.add(model);
		}
		return models;
	}

	// 获取一个新的进车订单号
	private String getNewInID() throws SQLException {

		String date = mainDAO.getCurrentDate();
		String in_id = date + "00"; // 00表示进车单号类型标记

		if (connection == null) {
			connection = mainDAO.connectToDB();
		}

		statement = connection.createStatement();

		// 查询以今天日期开头的订单号
		String sql = "select in_id from in_form where in_id like '" + date + "%' order by in_id DESC";
		resultSet = statement.executeQuery(sql);

		String id = null;
		if (resultSet.next()) {
			id = resultSet.getString("in_id").trim();
			String last = id.substring(10, 13);// 截取最后的三位
			int num = Integer.valueOf(last); // 将最后三位转为整数
			in_id = in_id + String.format("%03d", ++num); // 最后三位整数加一
		} else {
			// 今天还没有订单
			in_id = in_id + "001";
		}
		return in_id;
	}

}
