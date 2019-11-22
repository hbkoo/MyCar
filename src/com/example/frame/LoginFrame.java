package com.example.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.example.DAO.MainDAO;
import com.example.beans.Staff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Component;
import javax.swing.Box;

public class LoginFrame extends JFrame implements ActionListener {
	private JTextField username_tf;
	private JPasswordField pwd_tf;
	private JButton login_btn, reset_btn;

	/**
	 * Create the frame.
	 */
	public LoginFrame(String name) {
		this.setFont(new Font("楷体", Font.PLAIN, 20));
		setResizable(false);
		setTitle(name);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);

		JLabel username_lb = new JLabel("用户名：");
		username_lb.setFont(new Font("楷体", Font.PLAIN, 20));
		username_lb.setBounds(81, 61, 83, 25);
		getContentPane().add(username_lb);

		JLabel pwd_lb = new JLabel("密  码：");
		pwd_lb.setFont(new Font("楷体", Font.PLAIN, 20));
		pwd_lb.setBounds(81, 108, 83, 25);
		getContentPane().add(pwd_lb);

		username_tf = new JTextField();
		username_tf.setBounds(178, 61, 162, 30);
		getContentPane().add(username_tf);
		username_tf.setColumns(10);

		pwd_tf = new JPasswordField();
		pwd_tf.setBounds(178, 105, 162, 30);
		getContentPane().add(pwd_tf);

		login_btn = new JButton("登录");
		login_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		login_btn.setBounds(92, 180, 104, 40);
		getContentPane().add(login_btn);

		reset_btn = new JButton("重置");
		reset_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		// reset_btn.setForeground(Color.RED);
		// reset_btn.setBackground(Color.RED);
		reset_btn.setBounds(236, 180, 104, 40);
		getContentPane().add(reset_btn);

		login_btn.addActionListener(this);
		reset_btn.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource() == login_btn) {

			Login();

		} else if (e.getSource() == reset_btn) {
			username_tf.setText("");
			pwd_tf.setText("");
		}
	}

	private void Login() {

		String username = username_tf.getText();
		String password = pwd_tf.getText();

		System.out.println(username + "\n" + password);

		try {
			Staff staff = MainDAO.getInstance().searchStaff(username, password);

			if (staff == null) {
				JOptionPane.showMessageDialog(this, "用户名或密码错误!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {

				switch (staff.getRole()) {
				case "salesman":
					new SalesmanFrame(staff,this).setVisible(true);
					this.setVisible(false);
					break;
				case "repository":
					new RepositoryFrame(staff,this).setVisible(true);
					this.setVisible(false);
					break;
				case "server":
					new ServerFrame(staff,this).setVisible(true);
					this.setVisible(false);
					break;
				case "admin":
					new AdminFrame(staff,this).setVisible(true);
					this.setVisible(false);
					break;
				}
				System.out.println(staff.toString());
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
