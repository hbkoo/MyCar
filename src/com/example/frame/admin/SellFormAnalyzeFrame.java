package com.example.frame.admin;

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
import com.example.DAO.InFormsDAO;
import com.example.DAO.SellFormDAO;
import com.example.beans.SellForm;
import com.example.beans.Staff;

public class SellFormAnalyzeFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;
	
	private JLabel result_label;  //统计结果的计数统计

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private DatePicker start_picker, end_picker;

	private JComboBox<String> salesman_box, tag_box, rep_box, model_box;

	private List<String> salesman_ids, salesman_names;
	private List<String> rep_ids, rep_names;

	private SellFormDAO sellFormDAO = null;

	/**
	 * Create the frame.
	 */
	public SellFormAnalyzeFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("售车单统计", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);
		nameVector.add("业务操作员");
		nameVector.add("仓库操作员");
		nameVector.add("顾客姓名");
		nameVector.add("轿车型号");
		nameVector.add("订单时间");
		nameVector.add("购买数量");
		nameVector.add("处理标记");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 154);
		contentPane.add(scrollPane);

		JLabel lblNewLabel = new JLabel("选择业务员", JLabel.CENTER);
		lblNewLabel.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel.setBounds(71, 242, 142, 18);
		contentPane.add(lblNewLabel);

		JLabel label_3 = new JLabel("选择仓库管理员", SwingConstants.CENTER);
		label_3.setFont(new Font("黑体", Font.BOLD, 15));
		label_3.setBounds(300, 242, 142, 18);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("轿车型号", SwingConstants.CENTER);
		label_4.setFont(new Font("黑体", Font.BOLD, 15));
		label_4.setBounds(518, 242, 142, 18);
		contentPane.add(label_4);

		JLabel lblNewLabel_1 = new JLabel("选择开始日期", JLabel.CENTER);
		lblNewLabel_1.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel_1.setBounds(71, 332, 142, 18);
		contentPane.add(lblNewLabel_1);

		JLabel label_1 = new JLabel("选择结束日期", SwingConstants.CENTER);
		label_1.setFont(new Font("黑体", Font.BOLD, 15));
		label_1.setBounds(300, 332, 142, 18);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("选择处理标记", SwingConstants.CENTER);
		label_2.setFont(new Font("黑体", Font.BOLD, 15));
		label_2.setBounds(518, 332, 142, 18);
		contentPane.add(label_2);

		salesman_box = new JComboBox<String>();
		salesman_box.setBounds(71, 275, 142, 35); // 32 - 145 //全部spacing = 49.3
		contentPane.add(salesman_box);

		rep_box = new JComboBox<String>();
		rep_box.setBounds(300, 275, 142, 35);
		contentPane.add(rep_box);

		model_box = new JComboBox<String>();
		model_box.setBounds(518, 275, 142, 35);
		contentPane.add(model_box);

		start_picker = getDatePicker(false);
		start_picker.setBounds(71, 363, 142, 35);// 272+spacing
		contentPane.add(start_picker);

		end_picker = getDatePicker(true);
		end_picker.setBounds(300, 363, 142, 35);
		contentPane.add(end_picker);

		tag_box = new JComboBox<String>();
		tag_box.setBounds(518, 363, 142, 35);
		contentPane.add(tag_box);

		JButton query_btn = new JButton("统计");
		query_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		query_btn.setBounds(246, 426, 113, 42);
		contentPane.add(query_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(437, 426, 113, 42);
		contentPane.add(cancel_btn);
		
		JLabel lblNewLabel_2 = new JLabel("统计数量：",JLabel.CENTER);
		lblNewLabel_2.setFont(new Font("楷体", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(579, 31, 81, 18);
		contentPane.add(lblNewLabel_2);
		
		result_label = new JLabel("0",JLabel.CENTER);
		result_label.setForeground(Color.RED);
		result_label.setFont(new Font("楷体", Font.PLAIN, 15));
		result_label.setBounds(662, 31, 61, 18);
		contentPane.add(result_label);

		query_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
	}

	// 初始化界面数据
	private void InitDate() {

		tag_box.removeAllItems();
		tag_box.addItem("---请选择---");
		tag_box.addItem("是");
		tag_box.addItem("否");
		tag_box.updateUI();

		salesman_box.removeAllItems();
		dataVector.clear();
		table.updateUI();
		result_label.setText("0");
		updateSearchBox();

	}
	

	private void updateSearchBox() {

		rep_box.removeAllItems();
		try {
			InFormsDAO inFormsDAO = InFormsDAO.getInstance();
			
			Map<String, List<String>> salesmans_id_name = inFormsDAO.getStaffIdAndNameByRole("salesman");
			salesman_names = salesmans_id_name.get("staff_name");
			salesman_ids = salesmans_id_name.get("staff_id");
			salesman_box.addItem("---请选择---");
			for (String name : salesman_names) {
				salesman_box.addItem(name);
			}
			
			Map<String, List<String>> rep_id_name = inFormsDAO.getStaffIdAndNameByRole("repository");
			rep_names = rep_id_name.get("staff_name");
			rep_ids = rep_id_name.get("staff_id");
			rep_box.addItem("---请选择---");
			for (String name : rep_names) {
				rep_box.addItem(name);
			}
			
			model_box.removeAllItems();
			model_box.addItem("--请选择--");
			List<String> models = inFormsDAO.getAllModels();
			for (String model : models) {
				model_box.addItem(model);
			}
			
		} catch (SQLException e) {
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
		if (e.getActionCommand() == "统计") {
			DoSearch();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}

	// 开始搜索
	private void DoSearch() {
		dataVector.clear();
		String salesman_id = "%";
		String rep_id = "%";
		String model = "%";
		if (salesman_box.getSelectedIndex() != 0) {
			salesman_id = salesman_ids.get(salesman_box.getSelectedIndex() - 1);
		}
		if (rep_box.getSelectedIndex() != 0) {
			rep_id = rep_ids.get(rep_box.getSelectedIndex()-1);
		}
		if (model_box.getSelectedIndex() != 0) {
			model = (String) model_box.getSelectedItem();
		}
		String start_time = start_picker.getText();
		String end_time = end_picker.getText();
		int tagIndex = tag_box.getSelectedIndex();
		String tag = "%";
		if (tagIndex != 0) {
			tag = (String) tag_box.getSelectedItem();
		}


		try {
			if (sellFormDAO == null) {
				sellFormDAO = SellFormDAO.getInstance();
			}
			List<SellForm> sellForms = sellFormDAO.getSellForms(salesman_id, rep_id, model, 
					start_time, end_time, tag);
			System.out.println("sellforms's size is:" + sellForms.size());
			Vector<Object> vector;
			for (SellForm sellForm : sellForms) {
				vector = new Vector<>();
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
			result_label.setText(String.valueOf(sellForms.size()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

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
