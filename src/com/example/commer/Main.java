package com.example.commer;

import java.awt.EventQueue;
import java.sql.SQLException;

import com.example.DAO.MainDAO;
import com.example.frame.LoginFrame;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame("用户登录");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		MainDAO carsDAO = MainDAO.getInstance();
		try {
			carsDAO.connectToDB("car", "sa", "123");	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
