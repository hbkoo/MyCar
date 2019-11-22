package com.example.frame.admin;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import com.example.DAO.CarsDAO;
import com.example.DAO.InFormsDAO;
import com.example.beans.Car;
import com.example.beans.Staff;

public class QueryCarsFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();
	private JComboBox<String> model_box;

	/**
	 * Create the frame.
	 */
	public QueryCarsFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("库存信息", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		nameVector.add("型号");
		nameVector.add("数量");
		nameVector.add("价格");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);

		model_box = new JComboBox<String>();
		model_box.setFont(new Font("楷体", Font.PLAIN, 19));
		model_box.setBounds(363, 327, 200, 40);
		contentPane.add(model_box);

		JButton query_btn = new JButton("查看");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(257, 392, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(428, 392, 113, 42);
		contentPane.add(cancel_btn);

		JLabel label_1 = new JLabel("轿车型号：");
		label_1.setFont(new Font("楷体", Font.PLAIN, 20));
		label_1.setBounds(247, 327, 102, 41);
		contentPane.add(label_1);

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

	}

	private void InitDate() {
		table.removeAll();
		model_box.removeAllItems();
		try {
			List<String> models = InFormsDAO.getInstance().getAllModels();
			model_box.addItem("---请选择---");
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

	public JPanel getContentPanel() {
		InitDate();
		contentPane.setBounds(0, 0, 786, 528);
		contentPane.setOpaque(false);
		return contentPane;
	}

	private void DoSearch() {
		List<Car> cars;

		try {
			if (model_box.getSelectedIndex() == 0) {
				cars = CarsDAO.getInstance().getRepCars("%");
			} else {
				cars = CarsDAO.getInstance().getRepCars(model_box.getSelectedItem().toString().trim());
			}			
			dataVector.removeAllElements();			
			Vector<Object> vector;
			System.out.println("the size od the cars is " + cars.size());
			for(Car car : cars) {
				vector = new Vector<>();
				vector.addElement(car.getId());
				vector.addElement(car.getCount());
				vector.addElement(car.getPrice());
				dataVector.add(vector);
			}
			table.updateUI();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "查看") {

			System.out.println("查看所有轿车库存");
			
			DoSearch();

		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}
}
