package com.example.frame.repository;

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

import org.omg.CORBA.INV_IDENT;

import com.eltima.components.ui.DatePicker;
import com.example.DAO.CarsDAO;
import com.example.DAO.InFormsDAO;
import com.example.beans.InForm;
import com.example.beans.Staff;

public class InFormCheckFrame extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();
	
	private List<String> ids,names;
	
	private CarsDAO carsDAO = null;
	

	/**
	 * Create the frame.
	 */
	public InFormCheckFrame(Staff staff,JFrame parent) {
		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("进车订单审核", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
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
				
		
		JButton check_btn = new JButton("审核处理");
		check_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		check_btn.setBounds(185, 370, 170, 42);
		contentPane.add(check_btn);
		
		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(397, 370, 170, 42);
		contentPane.add(cancel_btn);
		
	
		
		
		check_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		
	}
	
	
	private void InitDate() {
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
		if(e.getActionCommand() == "审核处理") {
			DoCheck();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);			
		}
	}
	
	private void DoCheck() {
		if (carsDAO == null) {
			carsDAO = CarsDAO.getInstance();
		}
		
		if(table.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(parent, "请选择一条记录后再审核！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String in_id = (String) table.getValueAt(table.getSelectedRow(), 0);
		
		try {
			boolean result = carsDAO.checkInRep(in_id, staff.getId(),staff.getName());
			if (result) {
				JOptionPane.showMessageDialog(parent, "审核入库成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				UpdateTable();
			} else {
				JOptionPane.showMessageDialog(parent, "审核入库失败！\n请稍后重试...", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void UpdateTable() {
		
		try {
			List<InForm> inForms = InFormsDAO.getInstance().getInformsByTag("否");
			dataVector.clear();
			Vector<Object> vector;			
			for(InForm inForm : inForms) {
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

	
	
	
	

}
