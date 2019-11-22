package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.DAO.SellFormDAO;
import com.example.beans.Custom;
import com.example.beans.Staff;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.concurrent.atomic.DoubleAdder;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class AddCustomFrame extends JFrame implements ActionListener {

	private boolean isAdd = true;
	private Dialog changeDialog = null;
	private Custom changeCustom = null;
	private ChangeSellFormFrame changeParent;
	
	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JLabel title_label;
	private JTextField name_tf;
	private JComboBox<String> sex_box;
	private JTextField tel_tf;
	private JTextArea address_ta;
	private JButton confirm_btn;

	/**
	 * Create the frame.
	 */
	public AddCustomFrame(Staff staff,JFrame parent) {
		
		this.staff = staff;
		this.parent = parent;
		
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		title_label = new JLabel("新增顾客信息",JLabel.CENTER);
		title_label.setFont(new Font("楷体", Font.BOLD, 28));
		title_label.setBounds(288, 0, 200, 70);
		contentPane.add(title_label);
		
		JLabel label_1 = new JLabel("姓名：",JLabel.CENTER);
		label_1.setFont(new Font("楷体", Font.PLAIN, 20));
		label_1.setBounds(242, 88, 72, 30);
		contentPane.add(label_1);
		
		name_tf = new JTextField();
		name_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		name_tf.setColumns(10);
		name_tf.setBounds(328, 83, 192, 41);
		contentPane.add(name_tf);
		
		JLabel label_2 = new JLabel("性别：", SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.PLAIN, 20));
		label_2.setBounds(242, 149, 72, 30);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("电话：", SwingConstants.CENTER);
		label_3.setFont(new Font("楷体", Font.PLAIN, 20));
		label_3.setBounds(242, 205, 72, 30);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("住址：", SwingConstants.CENTER);
		label_4.setFont(new Font("楷体", Font.PLAIN, 20));
		label_4.setBounds(242, 256, 72, 30);
		contentPane.add(label_4);
		
		sex_box = new JComboBox<String>();
		sex_box.setFont(new Font("楷体", Font.PLAIN, 19));
		sex_box.setBounds(328, 146, 192, 41);
		sex_box.setAlignmentY(CENTER_ALIGNMENT);
		contentPane.add(sex_box);
		
		tel_tf = new JTextField();
		tel_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		tel_tf.setColumns(10);
		tel_tf.setBounds(328, 200, 192, 41);
		contentPane.add(tel_tf);
		
		address_ta = new JTextArea();
		address_ta.setFont(new Font("楷体", Font.PLAIN, 19));
		address_ta.setLineWrap(true);//设置自动换行
		address_ta.setMargin(new Insets(8, 8, 8, 8));
		JScrollPane scrollPane = new JScrollPane(address_ta);
		scrollPane.setBounds(328, 256, 192, 92);
		contentPane.add(scrollPane);
		
		confirm_btn = new JButton("添加");
		confirm_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		confirm_btn.setBounds(242, 381, 121, 41);
		contentPane.add(confirm_btn);
		
		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(399, 381, 121, 41);
		contentPane.add(cancel_btn);
		
		confirm_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
	}
	
	private void InitDate() {
		sex_box.removeAllItems();
		sex_box.addItem("秘密");
		sex_box.addItem("男");
		sex_box.addItem("女");
		UpdateAllUI();
	}
	
	
	public JPanel getContentPanel() {
		
		contentPane.setBounds(0, 0, 786, 528);
		contentPane.setOpaque(false);
		InitDate();
		return contentPane;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getActionCommand() == "添加") {
			
			System.out.println("添加");
			DoAdd();
			
			
			
		} else if (e.getActionCommand() == "取消") {
			System.out.println("取消");
			if (isAdd) {
				contentPane.setVisible(false);
			} else {
				changeDialog.dispose();
				this.dispose();				
			}
		} else if (e.getActionCommand() == "修改") {
			System.out.println("修改");
			DoChange();
		}
		
	}
	
	// 添加新顾客操作
	private void DoAdd() {
		SellFormDAO sellFormDAO = SellFormDAO.getInstance();
		String name = name_tf.getText().trim();
		String sex = (String) sex_box.getSelectedItem();
		String tel = (String) tel_tf.getText().trim();
		String address = (String) address_ta.getText().trim();
		if ("".equals(name) || "".equals(address)) {
			JOptionPane.showMessageDialog(parent, "姓名或者地址不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			boolean result = sellFormDAO.addCustom(name, sex, tel, address);
			if (result) {
				JOptionPane.showMessageDialog(parent, "添加成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent, "添加失败！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	//修改顾客信息操作
	private void DoChange() {
		SellFormDAO sellFormDAO = SellFormDAO.getInstance();
		String name = name_tf.getText().trim();
		String sex = (String) sex_box.getSelectedItem();
		String tel = (String) tel_tf.getText().trim();
		String address = (String) address_ta.getText().trim();
		if ("".equals(name) || "".equals(address)) {
			JOptionPane.showMessageDialog(parent, "姓名或者地址不能为空！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (name.equals(changeCustom.getName()) && sex.equals(changeCustom.getSex()) 
				&& tel.equals(changeCustom.getTel()) && address.equals(changeCustom.getAddress())) {
			JOptionPane.showMessageDialog(parent, "请修改信息后再点击修改按钮！", "提示", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		try {
			
			Custom custom = new Custom(changeCustom.getId(), name, sex, tel, address);
			boolean result = sellFormDAO.changeCustomer(custom);
			if (result) {
				JOptionPane.showMessageDialog(parent, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				changeParent.UpdateDateOfTable();
			} else {
				JOptionPane.showMessageDialog(parent, "修改失败！", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// 设置是否为查看修改顾客界面
	public void setTag(boolean isAdd) {
		this.isAdd = isAdd;
	}
	
	public void setFrame(Dialog dialog) {
		changeDialog = dialog;
	}
	
	public void setCustom(Custom custom) {
		changeCustom = custom;
	}
	
	public void setParentFrame(ChangeSellFormFrame changeParent) {
		this.changeParent = changeParent;
	}
	
	// 更新界面显示的效果
	public void UpdateAllUI() {
		
		if (changeCustom != null) {
			System.out.println("in AddCustomFrame UpdateAllUI function" + changeCustom.toString());
		}
		
		if (isAdd) {
			System.out.println("in AddCustomFrame UpdateAllUI function  is 增加");
			title_label.setText("新增顾客信息");
			confirm_btn.setText("添加");
			name_tf.setText("");
			tel_tf.setText("");
			address_ta.setText("");
		} else {
			System.out.println("in AddCustomFrame UpdateAllUI function  is 修改");
			name_tf.setText(changeCustom.getName());
			sex_box.setSelectedItem(changeCustom.getSex());
			tel_tf.setText(changeCustom.getTel());
			address_ta.setText(changeCustom.getAddress());
			title_label.setText("修改顾客信息");
			confirm_btn.setText("修改");
		}
		
	}


}
