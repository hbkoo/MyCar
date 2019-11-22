package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.beans.Car;
import com.example.beans.RepHistory;

public class CarsDAO {

	private static CarsDAO carsDAO = null;
	private MainDAO mainDAO = null;

	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;

	private CarsDAO() {
		if (mainDAO == null) {
			mainDAO = MainDAO.getInstance();
		}
	}

	public static CarsDAO getInstance() {
		if (carsDAO == null) {
			carsDAO = new CarsDAO();
		}
		return carsDAO;
	}

	// 确认轿车入库
	public boolean checkInRep(String in_id, String rep_id, String rep_name) throws SQLException {
		boolean isSuccess = false;
		String model=null;
		int count = 0;
		int result1=0,result2=0;
		
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		
		String sql = "select * from in_form where in_id = '" + in_id + "'";
		resultSet = statement.executeQuery(sql);
		//首先获取进车订单的轿车型号和轿车数量
		if (resultSet.next()) {
			model = resultSet.getString("model_id").trim();
			count = resultSet.getInt("in_count");
			//更新轿车库存的该型号的轿车数量
			sql = "update cars set count = count + " + count + 
					" where model_id='" + model + "'";
			result1 = statement.executeUpdate(sql);
			if (result1 != 0) {
				//更新轿车库存数量成功后然后再更新售车订单，设置为该订单已经处理
				sql = "update in_form set rep_id='" + rep_id + "',rep_name='"+rep_name+"',in_tag='是' " 
						+ "where in_id='" + in_id + "'";
				result2 = statement.executeUpdate(sql);
			}	
		}	
		if (result1 != 0 && result2 == 0) {
			//如果仓库轿车数量更新成功但是进车订单没有修改成功，需要重新将仓库轿车数量修改回去
			sql = "update cars set count = count - " + count +
					" where model_id='" + model + "'";
			statement.executeUpdate(sql);
		}

		isSuccess = (result1 != 0) && (result2 != 0);
		return isSuccess;
	}

	// 确认轿车出库
	public boolean checkOutRep(String sell_id, String rep_id, String rep_name) throws SQLException {
		boolean isSuccess = false;
		String model=null;
		int count = 0;
		int result1=0,result2=0;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		if (statement == null) {
			statement = connection.createStatement();
		}
		String sql = "select * from sell_form where sell_id = '" + sell_id + "'";
		resultSet = statement.executeQuery(sql);
		//首先获取售车订单的轿车型号和轿车数量
		if (resultSet.next()) {
			count = resultSet.getInt("out_count");
			model = resultSet.getString("model_id");	
			//更新轿车库存的该型号的轿车数量
			sql = "update cars set count = count - " + count + 
					" where model_id='" + model + "'";
			result1 = statement.executeUpdate(sql);
			if (result1 != 0) {
				//更新轿车库存数量成功后然后再更新售车订单，设置为该订单已经处理
				sql = "update sell_form set rep_id='" + rep_id + "',rep_name='"+ rep_name + "',out_tag='是' " 
						+ "where sell_id='" + sell_id + "'";
				result2 = statement.executeUpdate(sql);
			}			
		}

		if (result1 != 0 && result2 == 0) {
			//如果仓库轿车数量更新成功但是售车订单没有修改成功，需要重新将仓库轿车数量修改回去
			sql = "update cars set count = count + " + count +
					" where model_id='" + model + "'";
			statement.executeUpdate(sql);
		}

		isSuccess = (result1 != 0) && (result2 != 0);
		return isSuccess;
	}

	// 获取所有的进出历史记录
	public List<RepHistory> getRepHistory() throws SQLException {
		return getRepHistory("%", "%", "1999-1-1", mainDAO.getCurrentDate(), "%");
	}

	// 根据不同条件查看所有轿车进出历史记录
	public List<RepHistory> getRepHistory(String rep_id, String model_id, String start_time, String end_time,
			String tag) throws SQLException {
		List<RepHistory> repHistories = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		switch (tag) {
		case "进库":
			repHistories = getInRepHistory(rep_id, model_id, start_time, end_time);
			break;
		case "出库":
			repHistories = getOutRepHistory(rep_id, model_id, start_time, end_time);
			break;
		case "%":
		default:
			repHistories.addAll(getInRepHistory(rep_id, model_id, start_time, end_time));
			repHistories.addAll(getOutRepHistory(rep_id, model_id, start_time, end_time));
			break;
		}
		return repHistories;
	}

	// 获取所有的进车记录
	public List<RepHistory> getInRepHistory(String rep_id, String model_id, String start_time, String end_time)
			throws SQLException {
		List<RepHistory> repHistories = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		RepHistory repHistory;

		String sql = "select * from in_form where rep_id like ? and model_id like ?"
				+ " and in_time >= ? and in_time <= ?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, rep_id + "%");
		pStatement.setString(2, model_id + "%");
		pStatement.setString(3, start_time);
		pStatement.setString(4, end_time);
		resultSet = pStatement.executeQuery();
		while (resultSet.next()) {
			String id = resultSet.getString("in_id").trim();
			String salesmanId = resultSet.getString("salesman_id").trim();
			String repositoryId = resultSet.getString("rep_id").trim();
			String modelId = resultSet.getString("model_id").trim();
			String time = resultSet.getString("in_time").trim();
			int count = resultSet.getInt("in_count");
			String salesmanName = resultSet.getString("salesman_name").trim();
			String repositoryName = resultSet.getString("rep_name").trim();
			repHistory = new RepHistory(id, salesmanId, repositoryId, modelId, count, time, "进", salesmanName,
					repositoryName);
			repHistory.setTag("进库");
			repHistories.add(repHistory);
		}
		return repHistories;
	}

	// 获取所有的出车记录
	public List<RepHistory> getOutRepHistory(String rep_id, String model_id, String start_time, String end_time)
			throws SQLException {
		List<RepHistory> repHistories = new ArrayList<>();
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		RepHistory repHistory;

		String sql = "select * from sell_form where rep_id like ? and model_id like ?"
				+ " and out_time >= ? and out_time <= ?";
		pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, rep_id + "%");
		pStatement.setString(2, model_id + "%");
		pStatement.setString(3, start_time);
		pStatement.setString(4, end_time);
		resultSet = pStatement.executeQuery();
		while (resultSet.next()) {
			String id = resultSet.getString("sell_id").trim();
			String salesmanId = resultSet.getString("salesman_id").trim();
			String repositoryId = resultSet.getString("rep_id").trim();
			String modelId = resultSet.getString("model_id").trim();
			String time = resultSet.getString("out_time").trim();
			int count = resultSet.getInt("out_count");
			String salesmanName = resultSet.getString("salesman_name").trim();
			String repositoryName = resultSet.getString("rep_name").trim();
			repHistory = new RepHistory(id, salesmanId, repositoryId, modelId, count, time, "出", salesmanName,
					repositoryName);
			repHistory.setTag("出库");
			repHistories.add(repHistory);
		}
		return repHistories;
	}

	// 查看所有的库存信息
	public List<Car> getRepCars(String model_id) throws SQLException{
		List<Car> cars = new ArrayList<>();
		Car car;
		if (connection == null) {
			connection = mainDAO.connectToDB();
		}
		String sql = "select * from cars where model_id like '" + model_id + "%'";
		if (statement == null) {
			statement = connection.createStatement();
		}
		resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			String modelId = resultSet.getString("model_id").trim();
			int count = resultSet.getInt("count");
			double price = resultSet.getDouble("price");
			car = new Car(modelId, count, price);
			cars.add(car);
		}		
		return cars;
	}

}
