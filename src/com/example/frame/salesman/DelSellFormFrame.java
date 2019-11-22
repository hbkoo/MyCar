package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.DAO.SellFormDAO;
import com.example.beans.SellForm;
import com.example.beans.Staff;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class DelSellFormFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();

	private JTable table;

	private int selectedIndex = -1;

	private SellFormDAO sellFormDAO = null;

	private List<SellForm> sellForms = null;

	/**
	 * Create the frame.
	 */
	public DelSellFormFrame(Staff staff, JFrame parent) {

		this.staff = staff;
		this.parent = parent;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("删除售车订单", SwingConstants.CENTER);
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(288, 0, 200, 70);
		contentPane.add(label);

		// nameVector.add("单号");
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
		scrollPane.setBounds(32, 75, 702, 269);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		contentPane.add(scrollPane);

		JButton del_btn = new JButton("删除订单");
		del_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		del_btn.setBounds(241, 395, 127, 40);
		contentPane.add(del_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(411, 395, 127, 40);
		contentPane.add(cancel_btn);

		del_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				selectedIndex = table.getSelectedRow();
				System.out.println("the selected row is " + selectedIndex);
			}
		});

	}

	private void InitDate() {

		dataVector.clear();

		if (sellFormDAO == null) {
			sellFormDAO = SellFormDAO.getInstance();
		}

		try {
			sellForms = sellFormDAO.getSellForms(staff.getId(), "否");
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
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
		if (e.getActionCommand() == "删除订单") {
			if (selectedIndex == -1) {//当前选中的条目
				JOptionPane.showMessageDialog(parent, "请选择一条记录!", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				Object[] options = { "删除", "取消" };
				int choice = JOptionPane.showOptionDialog(parent, "确定要删除此条订单吗？", "确认", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (choice == 0) {
					DoDel();
				}
			}
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}

	private void DoDel() {

		if (sellFormDAO == null) {
			sellFormDAO = SellFormDAO.getInstance();
		}
		try {
			boolean result = sellFormDAO.deleteSellForm(sellForms.get(selectedIndex).getId());
			if (result) {
				dataVector.remove(selectedIndex);
				table.updateUI();
				selectedIndex = -1;
				table.clearSelection();
				JOptionPane.showMessageDialog(parent, "删除成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(parent, "删除失败!", "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接错误", "错误", JOptionPane.ERROR_MESSAGE);
		}

	}

}
