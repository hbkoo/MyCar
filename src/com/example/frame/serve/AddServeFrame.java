package com.example.frame.serve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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

import com.eltima.components.ui.DatePicker;
import com.example.DAO.InFormsDAO;
import com.example.DAO.SellFormDAO;
import com.example.DAO.ServeFormDAO;
import com.example.beans.SellForm;
import com.example.beans.Staff;

public class AddServeFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private ServeFormDAO serveFormDAO = null;

	private JComboBox<String> satisfy_box;

	private int select_index = -1;

	/**
	 * Create the frame.
	 */
	public AddServeFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("新增售后单", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		nameVector.add("单号");
		nameVector.add("业务操作员");
		nameVector.add("仓库操作员");
		nameVector.add("顾客姓名");
		nameVector.add("轿车型号");
		nameVector.add("服务时间");
		nameVector.add("购买数量");
		nameVector.add("处理标记");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);

		JButton query_btn = new JButton("添加");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(260, 407, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(411, 407, 113, 42);
		contentPane.add(cancel_btn);
		
		JLabel label_1 = new JLabel("满意度：", SwingConstants.CENTER);
		label_1.setFont(new Font("楷体", Font.PLAIN, 20));
		label_1.setBounds(225, 353, 110, 30);
		contentPane.add(label_1);
		
		satisfy_box = new JComboBox<String>();
		satisfy_box.setFont(new Font("楷体", Font.PLAIN, 18));
		satisfy_box.setAlignmentX(0.5f);
		satisfy_box.setBounds(333, 345, 188, 38);
		contentPane.add(satisfy_box);

		satisfy_box.addItem("极好");
		satisfy_box.addItem("好");
		satisfy_box.addItem("中等");
		satisfy_box.addItem("差");
		satisfy_box.addItem("极差");

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				select_index = table.getSelectedRow();
			}
		});

	}

	// 初始化界面数据
	private void InitDate() {
		select_index = -1;
		table.clearSelection();
		satisfy_box.setSelectedIndex(0);
		updateTable();
	}
	
	private void updateTable() {
		dataVector.clear();
		try {
			List<SellForm> sellForms = SellFormDAO.getInstance().getSellFormsNoServedByTag("是");
			System.out.println("sellforms's size is:" + sellForms.size());
			Vector<Object> vector;
			for (SellForm sellForm : sellForms) {
				vector = new Vector<>();
				vector.add(sellForm.getId());
				vector.add(sellForm.getSalesmanName());
				vector.add(sellForm.getRepositoryName());
				vector.add(sellForm.getCustomName());
				vector.add(sellForm.getModelId());
				vector.add(sellForm.getOutTime());
				vector.add(sellForm.getCount());
				vector.add(sellForm.getTag());
				dataVector.add(vector);
			}
			table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
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

	// 添加售后单
	private void DoAdd() {
		if (select_index == -1) {
			JOptionPane.showMessageDialog(parent, "请选择一条目!", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sell_id = (String) table.getValueAt(select_index, 0);
		if (serveFormDAO == null) {
			serveFormDAO = ServeFormDAO.getInstance();
		}
		try {
			boolean result = serveFormDAO.addServeForm(staff.getId(), staff.getName(), sell_id, satisfy_box.getSelectedItem().toString());
			if (result) {
				JOptionPane.showMessageDialog(parent, "新增成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
				updateTable();
			} else {
				JOptionPane.showMessageDialog(parent, "新增失败!\n请稍后重试...", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
