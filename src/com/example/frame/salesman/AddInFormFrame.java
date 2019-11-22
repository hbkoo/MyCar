package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.beans.Staff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class AddInFormFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JComboBox<String> model_box;
	private JTextField count_tf;
	private InFormsDAO inFormsDAO = null;
	
//	private JTextField count_tf;

	/**
	 * Create the frame.
	 */
	public AddInFormFrame(Staff staff,JFrame parent) {
		
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel model_lb = new JLabel("轿车型号：");
		model_lb.setFont(new Font("楷体", Font.PLAIN, 20));
		model_lb.setBounds(208, 120, 102, 41);
		contentPane.add(model_lb);
		
		JLabel count_lb = new JLabel("进车数量：");
		count_lb.setFont(new Font("楷体", Font.PLAIN, 20));
		count_lb.setBounds(208, 187, 102, 41);
		contentPane.add(count_lb);
		
		model_box = new JComboBox<String>();
		model_box.setFont(new Font("楷体", Font.PLAIN, 19));
		model_box.setBounds(324, 120, 192, 41);
		contentPane.add(model_box);
		
		count_tf = new JTextField();
		count_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		count_tf.setBounds(324, 187, 192, 41);
		contentPane.add(count_tf);
		count_tf.setColumns(10);
		
		JButton add_btn = new JButton("添加");
		add_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		add_btn.setBounds(233, 273, 121, 41);
		contentPane.add(add_btn);
		
		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(368, 273, 121, 41);
		contentPane.add(cancel_btn);
		
		JLabel label = new JLabel("新增进车订单");
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(294, 14, 186, 70);
		contentPane.add(label);
		
		add_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
	}
	
	
	public JPanel getContentPanel() {
		
//		JPanel panel = new JPanel();
//		
//		Icon icon = new ImageIcon("D:\\test\\1.png");
//		JLabel bg_label = new JLabel(icon);
//		bg_label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
//		panel.add(bg_label);
		
		InitDate();
		contentPane.setBounds(0, 0, 786, 528);
		contentPane.setOpaque(false);
		
		
//		bg_label.add(contentPane);
		
		return contentPane;
	}

	//为轿车型号加载初始值
	private void InitDate() {
		model_box.removeAllItems();
		count_tf.setText("");
		try {
			if (inFormsDAO == null) {
				inFormsDAO = InFormsDAO.getInstance();
			}
			List<String> models = inFormsDAO.getAllModels();
			for (String model : models) {
				model_box.addItem(model);
			}
			model_box.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getActionCommand() == "添加") {
			
			DoAddInform();
			
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		} 
		
	}
	
	private void DoAddInform() {
		
		
		try {
			String model_id = (String) model_box.getSelectedItem();
			int count = Integer.valueOf(count_tf.getText());
			
			if (inFormsDAO == null) {
				inFormsDAO = InFormsDAO.getInstance();
			}
			boolean result = inFormsDAO.addInForm(staff.getId(), staff.getName(), model_id, count);
			
			if (result) {
				JOptionPane.showMessageDialog(parent, "订单新增成功", "消息", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent, "订单新增失败\n请稍后重试", "错误", JOptionPane.ERROR_MESSAGE);
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
}
