package com.example.frame.salesman;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.DAO.SellFormDAO;
import com.example.beans.Custom;
import com.example.beans.SellForm;
import com.example.beans.Staff;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class ChangeSellFormFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTable table;
	private JFrame parent;
	private Staff staff;

	private Vector<String> nameVector = new Vector<String>();
	private Vector<Vector<Object>> dataVector = new Vector<Vector<Object>>();
	private JTextField count_tf;
	private JComboBox<String> model_box;
	
	private int selectIndex = -1;
	private SellFormDAO sellFormDAO = null;
	private List<SellForm> sellForms = null;
	
	private AddCustomFrame addCustomFrame = null;

	/**
	 * Create the frame.
	 */
	public ChangeSellFormFrame(Staff staff,JFrame parent) {
		
		this.staff = staff;
		this.parent = parent;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("修改售车订单", SwingConstants.CENTER);
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
		scrollPane.setBounds(32, 75, 702, 239);
		table.setFont(new Font("楷体", Font.PLAIN, 17));
		contentPane.add(scrollPane);

		model_box = new JComboBox<String>();
		model_box.setAlignmentX(CENTER_ALIGNMENT);
		model_box.setFont(new Font("楷体", Font.PLAIN, 18));
		model_box.setBounds(198, 343, 147, 38);
		contentPane.add(model_box);

		count_tf = new JTextField();
		count_tf.setAlignmentX(CENTER_ALIGNMENT);
		count_tf.setFont(new Font("楷体", Font.PLAIN, 18));
		count_tf.setBounds(532, 344, 117, 38);
		contentPane.add(count_tf);
		count_tf.setColumns(10);

		JLabel label_3 = new JLabel("轿车型号：", SwingConstants.CENTER);
		label_3.setFont(new Font("仿宋", Font.BOLD, 20));
		label_3.setBounds(86, 347, 110, 30);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("购车数量：", SwingConstants.CENTER);
		label_4.setFont(new Font("仿宋", Font.BOLD, 20));
		label_4.setBounds(401, 347, 117, 30);
		contentPane.add(label_4);

		JButton change_btn = new JButton("修改订单");
		change_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		change_btn.setBounds(320, 412, 135, 44);
		contentPane.add(change_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(524, 412, 135, 44);
		contentPane.add(cancel_btn);
		
		JButton custom_btn = new JButton("顾客信息");
		custom_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		custom_btn.setBounds(125, 412, 135, 44);
		contentPane.add(custom_btn);

		custom_btn.addActionListener(this);
		change_btn.addActionListener(this);
		cancel_btn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				selectIndex = table.getSelectedRow();
				int selectCol = table.getSelectedColumn();
				if (selectCol == 2) {
					showCustom(); //显示顾客的详细信息
				}
				model_box.setSelectedItem(table.getValueAt(selectIndex, 3));
				count_tf.setText(table.getValueAt(selectIndex, 5).toString());
			}		
		});
		
	}

	// 初始化数据
	private void InitDate() {
		table.clearSelection();
		selectIndex = -1;
		count_tf.setText("");
		if (sellFormDAO == null) {
			sellFormDAO = SellFormDAO.getInstance();
		}
		try {	
			UpdateDateOfTable();
			model_box.removeAllItems();
			List<String> models = InFormsDAO.getInstance().getAllModels();
			model_box.addItem("---请选择---");
			for (String model : models) {
				model_box.addItem(model);
			}
			model_box.updateUI();
			
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
		
		if (e.getActionCommand() == "顾客信息") {
			if (selectIndex == -1) {
				JOptionPane.showMessageDialog(parent, "请选择一条目!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				showCustom();
			}
		} else if (e.getActionCommand() == "修改订单") {
			DoChangeForm();
		} else if (e.getActionCommand() == "取消") {
			contentPane.setVisible(false);
		}
	}
	
	// 修改订单操作
	private void DoChangeForm() {
		if (sellFormDAO == null) {
			sellFormDAO = SellFormDAO.getInstance();
		}
		if (selectIndex == -1) {
			JOptionPane.showMessageDialog(parent, "请选择进车单条目!", "错误", JOptionPane.ERROR_MESSAGE);
		} else {
			if (model_box.getSelectedIndex() == 0) {
				JOptionPane.showMessageDialog(parent, "请选择轿车型号!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					boolean result = sellFormDAO.changeSellForm(sellForms.get(selectIndex).getId(),
							model_box.getSelectedItem().toString().trim(), Integer.valueOf(count_tf.getText()));
					if (result) {
						UpdateDateOfTable();
						JOptionPane.showMessageDialog(parent, "修改成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(parent, "修改失败!", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "购买车辆请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}
	
	// 获取数据库中的数据并更新table中的条目
	public void UpdateDateOfTable() throws SQLException {
		
		dataVector.clear();
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
		
	}
	
	// 查看用户信息并更新
	private void showCustom() {
		System.out.println("顾客信息");
		
		SellForm sellForm = sellForms.get(selectIndex);
		try {
			
			Custom custom = sellFormDAO.getCustomById(sellForm.getCustomId());
			
			System.out.println("in changeSellFormFrame showCustom function:" + custom.toString());
			
			if (addCustomFrame == null) {
				addCustomFrame = new AddCustomFrame(staff, parent);
				addCustomFrame.setTag(false);   //设置为修改界面
			}
			addCustomFrame.setCustom(custom);
			addCustomFrame.UpdateAllUI();
			addCustomFrame.setParentFrame(this);  //方便修改顾客后更新table中的数据
			JPanel panel = addCustomFrame.getContentPanel(); //获取界面布局
			panel.setBounds(0, 0, 400, 300);
			
			Dialog dialog = new Dialog(parent,"custom",true);
			dialog.setBounds(400, 400, 786, 528);
			dialog.add(panel);
			addCustomFrame.setFrame(dialog);	  //方便释放该dialog窗口		
			dialog.setVisible(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
		
		
	}
	
	
}
