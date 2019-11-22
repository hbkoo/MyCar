package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.beans.InForm;
import com.example.beans.Staff;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

public class ChangeInformFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();
	private JLabel inId_lb;
	private JTextField count_tf;
	private JComboBox<String> model_box;
	
	private InFormsDAO inFormsDAO = null; // 数据库操作
	private int selectIndex = -1;

	/**
	 * Create the frame.
	 */
	public ChangeInformFrame(Staff staff, JFrame parent) {

		this.staff = staff;
		this.parent = parent;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label = new JLabel("修改进车订单");
		label.setFont(new Font("楷体", Font.BOLD, 28));
		label.setBounds(294, 14, 186, 70);
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
		scrollPane.setBounds(32, 75, 702, 239);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		contentPane.add(scrollPane);

		model_box = new JComboBox<String>();
		model_box.setAlignmentX(CENTER_ALIGNMENT);
		model_box.setFont(new Font("楷体", Font.PLAIN, 18));
		model_box.setBounds(343, 365, 110, 38);
		contentPane.add(model_box);

		count_tf = new JTextField();
		count_tf.setAlignmentX(CENTER_ALIGNMENT);
		count_tf.setFont(new Font("楷体", Font.PLAIN, 18));
		count_tf.setBounds(545, 366, 117, 38);
		contentPane.add(count_tf);
		count_tf.setColumns(10);

		JLabel label_1 = new JLabel("订单号：", JLabel.CENTER);
		label_1.setFont(new Font("仿宋", Font.BOLD, 20));
		label_1.setBounds(52, 327, 110, 30);
		contentPane.add(label_1);

		inId_lb = new JLabel("", JLabel.CENTER);
		inId_lb.setFont(new Font("楷体", Font.PLAIN, 18));
		inId_lb.setBounds(32, 365, 154, 38);
		contentPane.add(inId_lb);

		JLabel label_3 = new JLabel("轿车型号：", SwingConstants.CENTER);
		label_3.setFont(new Font("仿宋", Font.BOLD, 20));
		label_3.setBounds(343, 327, 110, 30);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("进车数量：", SwingConstants.CENTER);
		label_4.setFont(new Font("仿宋", Font.BOLD, 20));
		label_4.setBounds(545, 327, 117, 30);
		contentPane.add(label_4);

		JButton change_btn = new JButton("修改");
		change_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		change_btn.setBounds(214, 424, 113, 44);
		contentPane.add(change_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(431, 424, 113, 44);
		contentPane.add(cancel_btn);

		change_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectIndex = table.getSelectedRow();
				inId_lb.setText((String) table.getValueAt(selectIndex, 0));
				model_box.setSelectedItem(table.getValueAt(selectIndex, 3));
				count_tf.setText(table.getValueAt(selectIndex, 5).toString());
			}		
		});

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
			DoChangeForm();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}

	}
	
	// 修改订单操作
	private void DoChangeForm() {
		
		if (inFormsDAO == null) {
			inFormsDAO = InFormsDAO.getInstance();
		}
		if (selectIndex == -1) {
			JOptionPane.showMessageDialog(parent, "请选择进车单条目!", "错误", JOptionPane.ERROR_MESSAGE);
		} else {
			if (model_box.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(parent, "请选择轿车型号!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					boolean result = inFormsDAO.changeInForm(inId_lb.getText(), (String) model_box.getSelectedItem(), 
							Integer.valueOf(count_tf.getText()));
					if (result) {
						UpdateDateOfTable();
						JOptionPane.showMessageDialog(parent, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(parent, "修改失败!", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();JOptionPane.showMessageDialog(parent, "购买车辆请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		
	}

	//初始化数据
	private void InitDate() {
		inId_lb.setText("");
		count_tf.setText("");
		table.clearSelection();
		selectIndex = -1;
		if (inFormsDAO == null) {
			inFormsDAO = InFormsDAO.getInstance();
		}
		try {
			UpdateDateOfTable();
			model_box.removeAllItems();
			model_box.addItem("---请选择---");
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
	
	// 更新table中的条目
	private void UpdateDateOfTable() throws SQLException {
		
		dataVector.clear();
		List<InForm> inForms = inFormsDAO.getInformsBySalesmanIDTag(staff.getId(),"否");
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
	}

	

}
