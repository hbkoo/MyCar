package com.example.frame.admin;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.example.DAO.ServeFormDAO;
import com.example.DAO.UserManageDAO;
import com.example.beans.ServeForm;
import com.example.beans.Staff;
import com.example.frame.salesman.AddSellFormFrame;

public class ChangeStaffRoleFrame extends JFrame implements ActionListener {
	
	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();


	private JComboBox<String> role_box;

	private int select_index = -1;

	private UserManageDAO userManageDAO = null;

	
	/**
	 * Create the frame.
	 */
	public ChangeStaffRoleFrame(Staff staff,JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("修改职工", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		nameVector.add("工号");
		nameVector.add("姓名");
		nameVector.add("性别");
		nameVector.add("电话");
		nameVector.add("角色");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);

		JLabel label_2 = new JLabel("职工角色：", SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.PLAIN, 19));
		label_2.setBounds(225, 346, 113, 27);
		contentPane.add(label_2);

		role_box = new JComboBox<String>();
		role_box.setFont(new Font("楷体", Font.PLAIN, 17));
		role_box.setAlignmentX(0.5f);
		role_box.setBounds(352, 342, 136, 35);
		contentPane.add(role_box);
		role_box.addItem("admin");
		role_box.addItem("salesman");
		role_box.addItem("server");
		role_box.addItem("repository");

		JButton query_btn = new JButton("修改");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(225, 400, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(362, 400, 113, 42);
		contentPane.add(cancel_btn);

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				select_index = table.getSelectedRow();
				role_box.setSelectedItem(table.getValueAt(select_index, 4).toString().trim());
			}
		});

	}

	// 初始化界面数据
	private void InitDate() {
		select_index = -1;
		table.clearSelection();
		role_box.setSelectedIndex(0);
		UpdateTable();
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

	// 添加售后单
	private void DoChange() {
		if (userManageDAO == null) {
			userManageDAO = UserManageDAO.getInstance();
		}
		try {
			if (select_index == -1) {
				JOptionPane.showMessageDialog(parent, "请选择一条记录后再修改！", "错误", JOptionPane.ERROR_MESSAGE);

			} else {
				boolean result = userManageDAO.changeStaffRole((String)table.getValueAt(select_index, 0),
						(String)role_box.getSelectedItem());
				if (result) {
					JOptionPane.showMessageDialog(parent, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(parent, "修改失败！\n请稍后重试！", "错误", JOptionPane.ERROR_MESSAGE);
				}
				UpdateTable();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}
	
	private void UpdateTable() {
		dataVector.clear();
		if (userManageDAO == null) {
			userManageDAO = UserManageDAO.getInstance();
		}
		try {
			List<Staff> staffs = userManageDAO.getAllStaff();
			Vector<Object> vector;
			for (Staff staff : staffs) {
				vector = new Vector<>();
				vector.add(staff.getId());
				vector.add(staff.getName());
				vector.add(staff.getSex());
				vector.add(staff.getTel());
				vector.add(staff.getRole());
				dataVector.add(vector);
			}
			table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}


}
