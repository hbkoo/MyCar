package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.eltima.components.ui.DatePicker;
import com.example.DAO.InFormsDAO;
import com.example.beans.InForm;
import com.example.beans.Staff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;

public class QueryInformFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();
	
	private DatePicker start_picker,end_picker;
	
	private JComboBox<String> salesman_box,tag_box;
	
	private List<String> ids,names;

	/**
	 * Create the frame.
	 */
	public QueryInformFrame(Staff staff, JFrame parent) {

		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("查询进车订单",JLabel.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(290, 0, 186, 70);
		contentPane.add(label);
		
		nameVector.add("单号");
		nameVector.add("业务操作员");
		nameVector.add("仓库操作员");
		nameVector.add("轿车型号");
		nameVector.add("进车时间");
		nameVector.add("进车量");
		nameVector.add("处理标记");
		table = new JTable(dataVector, nameVector);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		scrollPane.setBounds(32, 75, 702, 239);
		contentPane.add(scrollPane);
		
		JLabel lblNewLabel = new JLabel("选择业务员",JLabel.CENTER);
		lblNewLabel.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel.setBounds(32, 332, 113, 18);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("选择开始日期",JLabel.CENTER);
		lblNewLabel_1.setFont(new Font("黑体", Font.BOLD, 15));
		lblNewLabel_1.setBounds(195, 332, 164, 18);
		contentPane.add(lblNewLabel_1);
		
		JLabel label_1 = new JLabel("选择结束日期", SwingConstants.CENTER);
		label_1.setFont(new Font("黑体", Font.BOLD, 15));
		label_1.setBounds(408, 332, 164, 18);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("选择处理标记", SwingConstants.CENTER);
		label_2.setFont(new Font("黑体", Font.BOLD, 15));
		label_2.setBounds(621, 332, 113, 18);
		contentPane.add(label_2);
		
		salesman_box = new JComboBox<String>();
		salesman_box.setBounds(32, 363, 113, 35); // 32 - 145   //全部spacing = 49.3
		contentPane.add(salesman_box);

		start_picker = getDatePicker(false);
		start_picker.setBounds(195, 363, 164, 35);//272+spacing
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

	
	private void InitData() {	
		
		salesman_box.removeAllItems();
		dataVector.clear();
		table.updateUI();
		
		tag_box.removeAllItems();
		tag_box.addItem("---请选择---");
		tag_box.addItem("是");
		tag_box.addItem("否");
		tag_box.updateUI();
		
		try {
			InFormsDAO inFormsDAO = InFormsDAO.getInstance();
			Map<String, List<String>> salesmans_id_name = inFormsDAO.getStaffIdAndNameByRole("salesman");
			names = salesmans_id_name.get("staff_name");
			ids = salesmans_id_name.get("staff_id");
			salesman_box.addItem("---请选择---");
			for (String name : names) {		
				salesman_box.addItem(name);							
			}
			salesman_box.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
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
	
	//开始搜索
	private void DoSearch() {
		dataVector.clear();
		int salesmanIndex = salesman_box.getSelectedIndex();  // 第一个为0
		String salesman_id = "%";
		if (salesmanIndex != 0) {
			salesman_id = ids.get(salesmanIndex-1);
		}
		String start_time = start_picker.getText();
		String end_time = end_picker.getText();
		int tagIndex = tag_box.getSelectedIndex();
		String tag = "%";
		if (tagIndex != 0) {
			tag = (String) tag_box.getSelectedItem();
		}
		
		try {
			InFormsDAO inFormsDAO = InFormsDAO.getInstance();
			List<InForm> inForms = inFormsDAO.getInForms(salesman_id, start_time, end_time, tag);
			System.out.println("informs's size is:" + inForms.size());
			Vector<Object> vector;
			for (InForm inForm : inForms) {
				vector = new Vector<>();
				vector.add(inForm.getId());
				vector.add(inForm.getSalesmanName());
				vector.add(inForm.getRepositoryName());
				vector.add(inForm.getModelId());
				vector.add(inForm.getInTime());
				vector.add(inForm.getCount());
				vector.add(inForm.getTag());
				dataVector.add(vector);
			}
			table.updateUI();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	//获取界面面板
	public JPanel getContentPanel() {
		InitData();
		contentPane.setBounds(0, 0, 786, 528);
		contentPane.setOpaque(false);
		return contentPane;
	}

	
	// 获取日历控件
	private DatePicker getDatePicker(boolean isNow) {
        DatePicker datepick;
        String DefaultFormat = "yyyy-MM-dd";               // 格式
        Date date = new Date();                            // 当前时间
        if (!isNow) {
			date.setYear(2000-1900);
			date.setMonth(0);
			date.setDate(1);
		}
        Font font = new Font("楷体", Font.BOLD, 14);        // 字体
        int[] hilightDays = { 1, 3, 5, 7 };
        int[] disabledDays = { 4, 6, 5, 9 };
        //构造方法（初始时间，时间显示格式，字体，控件大小）
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
