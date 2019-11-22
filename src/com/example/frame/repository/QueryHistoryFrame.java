package com.example.frame.repository;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.example.DAO.CarsDAO;
import com.example.DAO.InFormsDAO;
import com.example.beans.RepHistory;
import com.example.beans.Staff;

public class QueryHistoryFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private DatePicker start_picker, end_picker;

	private JComboBox<String> model_box, tag_box;

	private List<String> ids, names;

	private CarsDAO carsDAO = null;

	/**
	 * Create the frame.
	 */
	public QueryHistoryFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("仓库进出记录", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		nameVector.add("单号");
		nameVector.add("业务操作员");
		nameVector.add("仓库操作员");
		nameVector.add("轿车型号");
		nameVector.add("时间");
		nameVector.add("轿车数量");
		nameVector.add("进出标记");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("选择轿车型号", JLabel.CENTER);
		lblNewLabel.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel.setBounds(32, 332, 113, 18);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("选择开始日期", JLabel.CENTER);
		lblNewLabel_1.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel_1.setBounds(195, 332, 164, 18);
		contentPane.add(lblNewLabel_1);

		JLabel label_1 = new JLabel("选择结束日期", SwingConstants.CENTER);
		label_1.setFont(new Font("黑体", Font.BOLD, 15));
		label_1.setBounds(408, 332, 164, 18);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("选择进出标记", SwingConstants.CENTER);
		label_2.setFont(new Font("黑体", Font.BOLD, 15));
		label_2.setBounds(621, 332, 113, 18);
		contentPane.add(label_2);

		model_box = new JComboBox<String>();
		model_box.setBounds(32, 363, 113, 35); // 32 - 145 //全部spacing = 49.3
		contentPane.add(model_box);

		start_picker = getDatePicker(false);
		start_picker.setBounds(195, 363, 164, 35);// 272+spacing
		contentPane.add(start_picker);

		end_picker = getDatePicker(true);
		end_picker.setBounds(408, 363, 164, 35);
		contentPane.add(end_picker);

		tag_box = new JComboBox<String>();
		tag_box.setBounds(621, 363, 113, 35);
		contentPane.add(tag_box);

		JButton query_btn = new JButton("搜索");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(246, 426, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(437, 426, 113, 42);
		contentPane.add(cancel_btn);

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

	}

	private void InitDate() {
		model_box.removeAllItems();
		dataVector.clear();
		table.updateUI();

		tag_box.removeAllItems();
		tag_box.addItem("---请选择---");
		tag_box.addItem("进库");
		tag_box.addItem("出库");
		tag_box.updateUI();

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand() == "搜索") {
			DoSearch();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}

	private void DoSearch() {
		
		dataVector.clear();

		if (carsDAO == null) {
			carsDAO = CarsDAO.getInstance();
		}

		String tag = "%";
		String model_id = "%";
		String start_time = start_picker.getText();
		String end_time = end_picker.getText();
		if (model_box.getSelectedIndex() != 0) {
			model_id = (String) model_box.getSelectedItem();
		}
		if (tag_box.getSelectedIndex() != 0) {
			tag = (String) tag_box.getSelectedItem();
		}
		try {
			List<RepHistory> repHistories = carsDAO.getRepHistory("%", model_id, start_time, end_time, tag);
			Vector<Object> vector;
			System.out.println("the history of 仓库："  + repHistories.size());
			for(RepHistory repHistory : repHistories) {
				
				System.out.println(repHistory.toString());
				
				vector = new Vector<>();
				vector.add(repHistory.getId());
				vector.add(repHistory.getSalesmanName());
				vector.add(repHistory.getRepositoryName());
				vector.add(repHistory.getModelId());
				vector.add(repHistory.getTime());
				vector.add(repHistory.getCount());
				vector.add(repHistory.getTag());
				dataVector.add(vector);				
			}
			table.updateUI();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}

	private void getInRepHistory() {

	}

	// 获取日历控件
	private DatePicker getDatePicker(boolean isNow) {
		DatePicker datepick;
		String DefaultFormat = "yyyy-MM-dd"; // 格式
		Date date = new Date(); // 当前时间
		if (!isNow) {
			date.setYear(2000 - 1900);
			date.setMonth(0);
			date.setDate(1);
		}
		Font font = new Font("楷体", Font.BOLD, 14); // 字体
		int[] hilightDays = { 1, 3, 5, 7 };
		int[] disabledDays = { 4, 6, 5, 9 };
		// 构造方法（初始时间，时间显示格式，字体，控件大小）
		datepick = new DatePicker(date, DefaultFormat, font, null);
		datepick.fd.setEditable(false);
		datepick.getInnerTextField().setEditable(false);
		// 设置一个月份中需要高亮显示的日子
		datepick.setHightlightdays(hilightDays, Color.red);
		// 设置一个月份中不需要的日子，呈灰色显示
		datepick.setDisableddays(disabledDays);
		// 设置国家
		datepick.setLocale(Locale.CANADA);
		// 设置时钟面板可见
		datepick.setTimePanleVisible(false);
		datepick.setFocusable(false);
		return datepick;
	}

}
