package com.example.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class FormSatisticDAO {
	private FormSatisticDAO formSatisticDAO = null;

	private MainDAO mainDAO = null;
	private Connection connection = null;
	private Statement statement = null;
	private PreparedStatement pStatement = null;
	private ResultSet resultSet = null;
	
	private InFormsDAO inFormsDAO = null;
	private SellFormDAO sellFormDAO = null;
	private ServeFormDAO serveFormDAO = null;
	private CarsDAO carsDAO = null;
	
	private FormSatisticDAO() {
		if (mainDAO == null) {
			mainDAO = MainDAO.getInstance();
		}
	}
	
	public FormSatisticDAO getInstance() {
		if (formSatisticDAO == null) {
			formSatisticDAO = new FormSatisticDAO();
		}
		return formSatisticDAO;
	}
	
	//进车订单查看
	
	
	
}
