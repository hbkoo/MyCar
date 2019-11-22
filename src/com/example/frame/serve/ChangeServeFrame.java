package com.example.frame.serve;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
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
import com.example.DAO.ServeFormDAO;
import com.example.beans.ServeForm;
import com.example.beans.Staff;
import com.example.frame.salesman.AddSellFormFrame;

public class ChangeServeFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private ServeFormDAO serveFormDAO = null;

	private JComboBox<String> satisfy_box;

	private int select_index = -1;

	private AddSellFormFrame addSellFormFrame = null;

	/**
	 * Create the frame.
	 */
	public ChangeServeFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("售后单修改", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		nameVector.add("单号");
		nameVector.add("售车单号");
		nameVector.add("售后员");
		nameVector.add("服务时间");
		nameVector.add("满意度");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);

		JLabel label_2 = new JLabel("选择满意度：", SwingConstants.CENTER);
		label_2.setFont(new Font("楷体", Font.BOLD, 17));
		label_2.setBounds(225, 346, 113, 27);
		contentPane.add(label_2);

		satisfy_box = new JComboBox<String>();
		satisfy_box.setFont(new Font("楷体", Font.PLAIN, 17));
		satisfy_box.setAlignmentX(0.5f);
		satisfy_box.setBounds(352, 342, 136, 35);
		contentPane.add(satisfy_box);

		satisfy_box.addItem("极好");
		satisfy_box.addItem("好");
		satisfy_box.addItem("中等");
		satisfy_box.addItem("差");
		satisfy_box.addItem("极差");

		JButton query_btn = new JButton("修改");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(246, 426, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(437, 426, 113, 42);
		contentPane.add(cancel_btn);

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				select_index = table.getSelectedRow();

				System.out.println(table.getValueAt(select_index, 4));


				satisfy_box.setSelectedItem(table.getValueAt(select_index, 4).toString().trim());
				if (table.getSelectedColumn() == 1) {
					showSellForm();
				}
			}
		});

	}

	// 初始化界面数据
	private void InitDate() {
		select_index = -1;
		table.clearSelection();
		satisfy_box.setSelectedIndex(0);
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
		if (serveFormDAO == null) {
			serveFormDAO = ServeFormDAO.getInstance();
		}
		try {
			if (select_index == -1) {
				JOptionPane.showMessageDialog(parent, "请选择一条记录后再修改！", "错误", JOptionPane.ERROR_MESSAGE);

			} else {
				System.out.println("select_index:" + select_index);
				System.out.println("value:" + table.getValueAt(select_index, 0));
				boolean result = serveFormDAO.changeServeForm((String) table.getValueAt(select_index, 0),
						(String) satisfy_box.getSelectedItem());
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

	private void showSellForm() {
		System.out.println("售车订单信息");

		String sell_id = (String) table.getValueAt(select_index, 1);

		if (addSellFormFrame == null) {
			addSellFormFrame = new AddSellFormFrame(staff, parent);
			addSellFormFrame.setIsAddTag(false); // 设置为修改界面
		}
		addSellFormFrame.setSellId(sell_id);
		JPanel panel = addSellFormFrame.getContentPanel(); // 获取界面布局
		panel.setBounds(0, 0, 400, 300);

		Dialog dialog = new Dialog(parent, "custom", true);
		dialog.setBounds(400, 400, 786, 528);
		dialog.add(panel);
		addSellFormFrame.setDialogFrame(dialog); // 方便释放该dialog窗口
		dialog.setVisible(true);

	}

	private void UpdateTable() {
		dataVector.clear();
		if (serveFormDAO == null) {
			serveFormDAO = ServeFormDAO.getInstance();
		}
		try {
			List<ServeForm> serveForms = serveFormDAO.getServeForms();
			Vector<Object> vector;
			for (ServeForm serveForm : serveForms) {
				vector = new Vector<>();
				vector.add(serveForm.getId());
				vector.add(serveForm.getSellId());
				vector.add(serveForm.getServerName());
				vector.add(serveForm.getServeTime());
				vector.add(serveForm.getSatisfy());
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
