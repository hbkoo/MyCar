package com.example.frame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InfoChangeDAO;
import com.example.beans.Staff;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.LineBorder;

public class MyInfoFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JTextField staffid_tf;
	private JTextField name_tf;
	private JPasswordField pwd_tf;
	private JTextField tel_tf;
	private JTextField age_tf;
	private JTextField role_tf;
	private JTextField sex_tf;
	private JPasswordField confirmPwd_tf;
	
	private JButton change_btn,cancel_btn;
	
	/**
	 * Create the frame.
	 */
	public MyInfoFrame(Staff staff,JFrame parent) {
		
		this.staff = staff;
		this.parent = parent;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		contentPane = new JPanel();
		contentPane.setForeground(SystemColor.desktop);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("个人信息", SwingConstants.CENTER);
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
		label_1.setBounds(380, 78, 72, 24);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("密码：", SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.PLAIN, 20));
		label_2.setBounds(88, 259, 72, 24);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("性别：", SwingConstants.CENTER);
		label_3.setFont(new Font("楷体", Font.PLAIN, 20));
		label_3.setBounds(380, 135, 72, 24);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("电话：", SwingConstants.CENTER);
		label_4.setFont(new Font("楷体", Font.PLAIN, 20));
		label_4.setBounds(380, 203, 72, 24);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("年龄：", SwingConstants.CENTER);
		label_5.setFont(new Font("楷体", Font.PLAIN, 20));
		label_5.setBounds(88, 203, 72, 24);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("角色：", SwingConstants.CENTER);
		label_6.setFont(new Font("楷体", Font.PLAIN, 20));
		label_6.setBounds(88, 135, 72, 24);
		contentPane.add(label_6);
		
		name_tf = new JTextField();
		name_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		name_tf.setEditable(false);
		name_tf.setColumns(10);
		name_tf.setBounds(454, 70, 137, 41);
		contentPane.add(name_tf);
		
		pwd_tf = new JPasswordField();
		pwd_tf.setColumns(10);
		pwd_tf.setBounds(174, 251, 137, 41);
		contentPane.add(pwd_tf);
		
		tel_tf = new JTextField();
		tel_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		tel_tf.setColumns(10);
		tel_tf.setBounds(454, 195, 137, 41);
		contentPane.add(tel_tf);
		
		age_tf = new JTextField();
		age_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		age_tf.setColumns(10);
		age_tf.setBounds(174, 195, 137, 41);
		contentPane.add(age_tf);
		
		role_tf = new JTextField();
		role_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		role_tf.setEditable(false);
		role_tf.setColumns(10);
		role_tf.setBounds(174, 127, 137, 41);
		contentPane.add(role_tf);
		
		sex_tf = new JTextField();
		sex_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		sex_tf.setEditable(false);
		sex_tf.setColumns(10);
		sex_tf.setBounds(454, 127, 137, 41);
		contentPane.add(sex_tf);
		
		JLabel label_7 = new JLabel("确认密码：", SwingConstants.CENTER);
		label_7.setFont(new Font("楷体", Font.PLAIN, 20));
		label_7.setBounds(352, 259, 100, 24);
		contentPane.add(label_7);
		
		confirmPwd_tf = new JPasswordField();
		confirmPwd_tf.setColumns(10);
		confirmPwd_tf.setBounds(454, 251, 137, 41);
		contentPane.add(confirmPwd_tf);
		
		change_btn = new JButton("修改");
		change_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		change_btn.setBounds(207, 355, 121, 41);
		contentPane.add(change_btn);
		
		cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(392, 355, 121, 41);
		contentPane.add(cancel_btn);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(255, 0, 0), 1, true), "不可修改", TitledBorder.LEADING, TitledBorder.TOP, null, Color.RED));
		panel.setBounds(80, 50, 542, 133);
		contentPane.add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setForeground(SystemColor.desktop);
		panel_1.setBorder(new TitledBorder(new LineBorder(new Color(1, 133, 116), 1, true), "可修改", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(1, 133, 116)));
		panel_1.setBounds(80, 181, 542, 147);
		contentPane.add(panel_1);
		
		change_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
	}

	
	private void InitDate() {
		staffid_tf.setText(staff.getId());
		name_tf.setText(staff.getName());
		sex_tf.setText(staff.getSex());
		role_tf.setText(staff.getRole());
		age_tf.setText(String.valueOf(staff.getAge()));
		pwd_tf.setText(staff.getPwd());
		tel_tf.setText(staff.getTel());
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
		if (e.getActionCommand() == "修改") {
			DoChange();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}
	
	private void DoChange() {
		
		InfoChangeDAO infoChangeDAO = InfoChangeDAO.getInstance();
		
		String pwd = pwd_tf.getText().trim();
		
		//密码和原密码不一样，说明密码被修改了
		if (!pwd.equals(staff.getPwd())) {
			
			String confirm_pwd = confirmPwd_tf.getText().trim();
			
			//判断两次输入的密码是否一样
			if (!pwd.equals(confirm_pwd)) {
				JOptionPane.showMessageDialog(parent, "两次输入的密码不一样!\n请重新输入!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				int age = Integer.valueOf(age_tf.getText());
				String tel = tel_tf.getText().trim();
				try {
					boolean result = infoChangeDAO.changeStaff(staff.getId(), age, tel, staff.getPwd(), pwd);
					if (result) {
						JOptionPane.showMessageDialog(parent, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
						changeSuccess();
					} else {
						JOptionPane.showMessageDialog(parent, "修改失败!", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(parent, "年龄请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			//密码栏没有被修改，则相当于修改年龄或手机号
			
			try {
				String tel = tel_tf.getText().trim();
				int age = Integer.valueOf(age_tf.getText());
				boolean result = infoChangeDAO.changeStaffTelAge(staff.getId(), tel, age);
				if (result) {
					JOptionPane.showMessageDialog(parent, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
					changeSuccess();
				} else {
					JOptionPane.showMessageDialog(parent, "修改失败!", "错误", JOptionPane.ERROR_MESSAGE);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
			} catch (NumberFormatException e) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(parent, "年龄请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
			
		}
		
	}
	
	// 修改数据库成功后修改本地的成员变量数据信息
	private void changeSuccess() {
		staff.setAge(Integer.valueOf(age_tf.getText()));
		staff.setPwd(pwd_tf.getText().trim());
		staff.setTel(tel_tf.getText().trim());
	}
	
}
