package com.example.frame.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.example.DAO.InfoChangeDAO;
import com.example.DAO.UserManageDAO;
import com.example.beans.Staff;
import javax.swing.JComboBox;

public class AddStaffFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JTextField staffid_tf;
	private JTextField name_tf;
	private JTextField pwd_tf;
	private JTextField tel_tf;
	private JTextField age_tf;
	private JComboBox<String> role_box,sex_box;
	
	private JButton add_btn,cancel_btn;
	
	private UserManageDAO userManageDAO = null;
	

	/**
	 * Create the frame.
	 */
	public AddStaffFrame(Staff staff,JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("添加新职工", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);
		JLabel lblNewLabel = new JLabel("工号：",JLabel.CENTER);
		lblNewLabel.setFont(new Font("楷体", Font.PLAIN, 20));
		lblNewLabel.setBounds(88, 78, 72, 24);
		contentPane.add(lblNewLabel);
		
		staffid_tf = new JTextField();
		staffid_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		staffid_tf.setEditable(false);
		staffid_tf.setColumns(10);
		staffid_tf.setBounds(174, 70, 137, 41);
		contentPane.add(staffid_tf);
		
		JLabel label_1 = new JLabel("姓名：", SwingConstants.CENTER);
		label_1.setFont(new Font("楷体", Font.PLAIN, 20));
		label_1.setBounds(88, 135, 72, 24);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("密码：", SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.PLAIN, 20));
		label_2.setBounds(368, 83, 72, 24);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("性别：", SwingConstants.CENTER);
		label_3.setFont(new Font("楷体", Font.PLAIN, 20));
		label_3.setBounds(380, 135, 72, 24);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("电话：", SwingConstants.CENTER);
		label_4.setFont(new Font("楷体", Font.PLAIN, 20));
		label_4.setBounds(380, 189, 72, 24);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("年龄：", SwingConstants.CENTER);
		label_5.setFont(new Font("楷体", Font.PLAIN, 20));
		label_5.setBounds(88, 189, 72, 24);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("角色：", SwingConstants.CENTER);
		label_6.setFont(new Font("楷体", Font.PLAIN, 20));
		label_6.setBounds(88, 248, 72, 24);
		contentPane.add(label_6);
		
		name_tf = new JTextField();
		name_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		name_tf.setColumns(10);
		name_tf.setBounds(174, 127, 137, 41);
		contentPane.add(name_tf);
		
		pwd_tf = new JTextField();
		pwd_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		pwd_tf.setEditable(false);
		pwd_tf.setColumns(10);
		pwd_tf.setBounds(454, 72, 137, 41);
		pwd_tf.setText("123456");
		contentPane.add(pwd_tf);
		
		tel_tf = new JTextField();
		tel_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		tel_tf.setColumns(10);
		tel_tf.setBounds(454, 181, 137, 41);
		contentPane.add(tel_tf);
		
		age_tf = new JTextField();
		age_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		age_tf.setColumns(10);
		age_tf.setBounds(174, 181, 137, 41);
		contentPane.add(age_tf);
		
		add_btn = new JButton("添加");
		add_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		add_btn.setBounds(207, 355, 121, 41);
		contentPane.add(add_btn);
		
		cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(392, 355, 121, 41);
		contentPane.add(cancel_btn);
		
		sex_box = new JComboBox<String>();
		sex_box.setFont(new Font("楷体", Font.PLAIN, 17));
		sex_box.setBounds(454, 127, 137, 41);
		contentPane.add(sex_box);
		sex_box.addItem("男");
		sex_box.addItem("女");
		
		role_box = new JComboBox<String>();
		role_box.setFont(new Font("楷体", Font.PLAIN, 17));
		role_box.setBounds(174, 239, 137, 44);
		contentPane.add(role_box);
		role_box.addItem("admin");
		role_box.addItem("salesman");
		role_box.addItem("server");
		role_box.addItem("repository");
		
		add_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
	}

	
	private void InitDate() {
		sex_box.setSelectedIndex(0);
		role_box.setSelectedIndex(0);
	}
	
	
	public JPanel getContentPanel() {
		InitDate();
		contentPane.setBounds(0, 0, 786, 528);
		contentPane.setOpaque(false);
		return contentPane;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "添加") {
			DoAdd();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}
	
	private void DoAdd() {
		
		if (userManageDAO == null) {
			userManageDAO = UserManageDAO.getInstance();
		}
		
		try {
			String name = name_tf.getText();
			String sex = (String) sex_box.getSelectedItem();
			String tel = tel_tf.getText();
			String role = (String) role_box.getSelectedItem();
			System.out.println(age_tf.getText());
			int age = Integer.valueOf(age_tf.getText().trim());
			String staff_id = userManageDAO.addNewStaff(name, sex, tel, age, role);
			if (staff_id == null) {
				JOptionPane.showMessageDialog(parent, "新增失败!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent, "新增成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
				staffid_tf.setText(staff_id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "年龄请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	}
	
	// 修改数据库成功后修改本地的成员变量数据信息
	private void changeSuccess() {
		staff.setAge(Integer.valueOf(age_tf.getText()));
		staff.setPwd(pwd_tf.getText().trim());
		staff.setTel(tel_tf.getText().trim());
	}
}
