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
import com.example.DAO.SellFormDAO;
import com.example.DAO.ServeFormDAO;
import com.example.beans.Custom;
import com.example.beans.SellForm;
import com.example.beans.ServeForm;
import com.example.beans.Staff;
import com.example.frame.salesman.AddCustomFrame;
import com.example.frame.salesman.AddSellFormFrame;

public class QueryServeFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private ServeFormDAO serveFormDAO = null;

	private JComboBox<String> satisfy_box, server_box;

	private DatePicker start_picker, end_picker;

	private int select_index = -1;

	private List<String> server_ids, server_names;

	private AddSellFormFrame addSellFormFrame = null;

	/**
	 * Create the frame.
	 */
	public QueryServeFrame(Staff staff, JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("售后单查询", SwingConstants.CENTER);
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

		JLabel lblNewLabel = new JLabel("售后服务员", JLabel.CENTER);
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

		JLabel label_2 = new JLabel("选择满意程度", SwingConstants.CENTER);
		label_2.setFont(new Font("黑体", Font.BOLD, 15));
		label_2.setBounds(603, 332, 113, 18);
		contentPane.add(label_2);

		server_box = new JComboBox<String>();
		server_box.setFont(new Font("楷体", Font.PLAIN, 17));
		server_box.setBounds(32, 363, 136, 35); // 32 - 145 //全部spacing = 49.3
		contentPane.add(server_box);

		start_picker = getDatePicker(false);
		start_picker.setBounds(195, 363, 164, 35);// 272+spacing
		contentPane.add(start_picker);

		end_picker = getDatePicker(true);
		end_picker.setBounds(408, 363, 164, 35);
		contentPane.add(end_picker);

		satisfy_box = new JComboBox<String>();
		satisfy_box.setFont(new Font("楷体", Font.PLAIN, 17));
		satisfy_box.setAlignmentX(0.5f);
		satisfy_box.setBounds(598, 363, 136, 35);
		contentPane.add(satisfy_box);

		satisfy_box.addItem("--请选择--");
		satisfy_box.addItem("极好");
		satisfy_box.addItem("好");
		satisfy_box.addItem("中等");
		satisfy_box.addItem("差");
		satisfy_box.addItem("极差");

		JButton query_btn = new JButton("查询");
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
				if (table.getSelectedColumn() == 1) {
					showSellForm();
				}
			}
		});

	}

	// 初始化界面数据
	private void InitDate() {
		dataVector.clear();
		select_index = -1;
		table.updateUI();
		satisfy_box.setSelectedIndex(0);
		try {
			Map<String, List<String>> server_id_name = InFormsDAO.getInstance().getStaffIdAndNameByRole("server");
			server_names = server_id_name.get("staff_name");
			server_ids = server_id_name.get("staff_id");
			server_box.addItem("--请选择--");
			for (String name : server_names) {
				server_box.addItem(name);
			}
			server_box.updateUI();
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
		if (e.getActionCommand() == "查询") {
			DoSearch();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}

	// 添加售后单
	private void DoSearch() {
		dataVector.clear();
		if (serveFormDAO == null) {
			serveFormDAO = ServeFormDAO.getInstance();
		}
		try {
			String server_id = "%";
			String sell_id = "%";
			String start_time = start_picker.getText();
			String end_time = end_picker.getText();
			String satisfy = "%";
			if (server_box.getSelectedIndex() != 0) {
				server_id = server_ids.get(server_box.getSelectedIndex() - 1);
			}
			if (satisfy_box.getSelectedIndex() != 0) {
				satisfy = satisfy_box.getSelectedItem().toString().trim();
			}
			List<ServeForm> serveForms = serveFormDAO.getServeForms(server_id, "%", start_time, end_time, satisfy);
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

}
