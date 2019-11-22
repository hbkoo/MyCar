package com.example.frame.salesman;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.example.DAO.InFormsDAO;
import com.example.DAO.MainDAO;
import com.example.DAO.SellFormDAO;
import com.example.DAO.UserManageDAO;
import com.example.beans.Car;
import com.example.beans.Custom;
import com.example.beans.SellForm;
import com.example.beans.Staff;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Insets;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.border.TitledBorder;

public class AddSellFormFrame extends JFrame implements ActionListener, ItemListener {

	private JLabel title_label;
	private JButton submit_btn;

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JTextField name_tf;
	private JTextField tel_tf;
	private JTextField sex_tf;
	private JTextArea address_ta;
	private JTextField count_tf;
	private JComboBox<String> custom_box;
	private JComboBox<String> model_box;

	private List<Custom> customs; // 获取全部的顾客信息
	private int selectIndex = -1;

	private boolean isAddTag = true;
	private String sell_id;   //查看售车订单
	private Dialog changeDialog = null;

	/**
	 * Create the frame.
	 */
	public AddSellFormFrame(Staff staff, JFrame parent) {

		this.staff = staff;
		this.parent = parent;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 528);
		setFont(new Font("楷体", Font.PLAIN, 19));

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		title_label = new JLabel("新增售车订单", SwingConstants.CENTER);
		title_label.setFont(new Font("楷体", Font.BOLD, 28));
		title_label.setBounds(288, 0, 200, 70);
		contentPane.add(title_label);

		JLabel label_1 = new JLabel("顾客名：");
		label_1.setFont(new Font("楷体", Font.PLAIN, 20));
		label_1.setBounds(61, 269, 102, 41);
		contentPane.add(label_1);

		JLabel label_2 = new JLabel("轿车型号：");
		label_2.setFont(new Font("楷体", Font.PLAIN, 20));
		label_2.setBounds(299, 269, 102, 41);
		contentPane.add(label_2);

		JLabel label_3 = new JLabel("轿车数量：");
		label_3.setFont(new Font("楷体", Font.PLAIN, 20));
		label_3.setBounds(529, 269, 102, 41);
		contentPane.add(label_3);

		JLabel label_4 = new JLabel("姓名：", SwingConstants.CENTER);
		label_4.setFont(new Font("楷体", Font.PLAIN, 20));
		label_4.setBounds(145, 101, 60, 24);
		contentPane.add(label_4);

		JLabel label_5 = new JLabel("性别：", SwingConstants.CENTER);
		label_5.setFont(new Font("楷体", Font.PLAIN, 20));
		label_5.setBounds(405, 101, 60, 24);
		contentPane.add(label_5);

		JLabel label_6 = new JLabel("电话：", SwingConstants.CENTER);
		label_6.setFont(new Font("楷体", Font.PLAIN, 20));
		label_6.setBounds(145, 155, 60, 24);
		contentPane.add(label_6);

		JLabel label_7 = new JLabel("住址：", SwingConstants.CENTER);
		label_7.setFont(new Font("楷体", Font.PLAIN, 20));
		label_7.setBounds(405, 155, 60, 24);
		contentPane.add(label_7);

		name_tf = new JTextField();
		name_tf.setEditable(false);
		name_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		name_tf.setColumns(10);
		name_tf.setBounds(205, 93, 137, 41);
		contentPane.add(name_tf);

		tel_tf = new JTextField();
		tel_tf.setEditable(false);
		tel_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		tel_tf.setColumns(10);
		tel_tf.setBounds(205, 147, 137, 41);
		contentPane.add(tel_tf);

		sex_tf = new JTextField();
		sex_tf.setEditable(false);
		sex_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		sex_tf.setColumns(10);
		sex_tf.setBounds(471, 93, 137, 41);
		contentPane.add(sex_tf);

		address_ta = new JTextArea();
		address_ta.setEditable(false);
		address_ta.setMargin(new Insets(8, 8, 8, 8));
		address_ta.setLineWrap(true);
		address_ta.setFont(new Font("楷体", Font.PLAIN, 18));
		// address_ta.setBounds(471, 158, 137, 75);
		JScrollPane scrollPane = new JScrollPane(address_ta);
		scrollPane.setBounds(471, 158, 160, 75);
		contentPane.add(scrollPane);

		custom_box = new JComboBox<String>();
		custom_box.setFont(new Font("楷体", Font.PLAIN, 19));
		custom_box.setAlignmentY(0.5f);
		custom_box.setBounds(157, 269, 128, 41);
		contentPane.add(custom_box);

		model_box = new JComboBox<String>();
		model_box.setFont(new Font("楷体", Font.PLAIN, 19));
		model_box.setAlignmentY(0.5f);
		model_box.setAlignmentY(CENTER_ALIGNMENT);
		model_box.setBounds(392, 269, 123, 41);
		contentPane.add(model_box);

		count_tf = new JTextField();
		count_tf.setFont(new Font("楷体", Font.PLAIN, 19));
		count_tf.setColumns(10);
		count_tf.setBounds(622, 269, 82, 41);
		contentPane.add(count_tf);

		submit_btn = new JButton("添加");
		submit_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		submit_btn.setBounds(205, 368, 121, 41);
		contentPane.add(submit_btn);

		JButton cancel_btn = new JButton("取消");
		cancel_btn.setFont(new Font("楷体", Font.PLAIN, 20));
		cancel_btn.setBounds(409, 368, 121, 41);
		contentPane.add(cancel_btn);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new LineBorder(new Color(1, 133, 116), 2, true), "顾客信息", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(1, 133, 116)));
		panel.setBounds(61, 54, 643, 202);
		contentPane.add(panel);

		submit_btn.addActionListener(this);
		cancel_btn.addActionListener(this);

		custom_box.addItemListener(this);

	}

	private void InitDate() {
		
		if (!isAddTag) {
			DoInitFrame();//查看传入的订单号的详细数据
		} else {
			title_label.setText("新增售车订单");
			submit_btn.setVisible(true);
			if (customs != null) {
				customs.clear();
			}
			try {
				custom_box.removeAllItems();
				customs = UserManageDAO.getInstance().getAllCustom();
				custom_box.addItem("--请选择--");
				for (Custom custom : customs) {
					custom_box.addItem(custom.getName());
				}

				model_box.removeAllItems();
				model_box.addItem("--请选择--");
				List<String> models = InFormsDAO.getInstance().getAllModels();
				for (String model : models) {
					model_box.addItem(model);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
			}
			
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

		if (e.getActionCommand() == "添加") {
			DoAdd();
		} else if (e.getActionCommand() == "取消") {
			if (isAddTag) {

				contentPane.setVisible(false);
			} else {
				changeDialog.dispose();
				this.dispose();				
			}
		}

	}

	private void DoAdd() {

		SellFormDAO sellFormDAO = SellFormDAO.getInstance();

		if (selectIndex < 0) {
			JOptionPane.showMessageDialog(parent, "请选择顾客信息!", "错误", JOptionPane.ERROR_MESSAGE);
		} else {

			int modelIndex = model_box.getSelectedIndex();
			if (modelIndex == 0) {
				JOptionPane.showMessageDialog(parent, "请选择要购买的轿车型号!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					int count = Integer.valueOf(count_tf.getText());
					boolean result = sellFormDAO.addSellForm(customs.get(selectIndex), staff.getId(), staff.getName(),
							model_box.getSelectedItem().toString(), count);
					if (result) {
						JOptionPane.showMessageDialog(parent, "添加成功!", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(parent, "添加失败!", "错误", JOptionPane.ERROR_MESSAGE);
					}
				} catch (NumberFormatException e) {
					// TODO: handle exception
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "购买车辆请输入整数！", "错误", JOptionPane.ERROR_MESSAGE);
				} catch (SQLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
				}

			}

		}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getStateChange() == ItemEvent.SELECTED && isAddTag) {
			selectIndex = custom_box.getSelectedIndex() - 1;
			if (selectIndex == -1) {
				name_tf.setText("顾客");
				sex_tf.setText("秘密");
				tel_tf.setText("88888888888");
				address_ta.setText("地球村中国境内");
			} else {
				Custom custom = customs.get(selectIndex);
				name_tf.setText(custom.getName());
				sex_tf.setText(custom.getSex());
				tel_tf.setText(custom.getTel());
				address_ta.setText(custom.getAddress());
			}
		}

	}

	public void setIsAddTag(boolean isAddTag) {
		this.isAddTag = isAddTag;
	}
	
	public void setSellId(String sell_id) {
		this.sell_id = sell_id;
	}
	
	private void DoInitFrame() {
		
		title_label.setText("订单信息");
		submit_btn.setVisible(false);
		custom_box.removeAllItems();
		model_box.removeAllItems();
		
		try {
			SellForm sellForm = SellFormDAO.getInstance().getSellFormBySellID(sell_id);
			if (sellForm == null) {
				JOptionPane.showMessageDialog(parent, "记录查看失败！\n请稍后重试!", "错误", JOptionPane.ERROR_MESSAGE);
			} else {
				
				name_tf.setText(sellForm.getCustomName());
				Custom custom = SellFormDAO.getInstance().getCustomById(sellForm.getCustomId());
				if (custom != null) {
					sex_tf.setText(custom.getSex());
					tel_tf.setText(custom.getTel());
					address_ta.setText(custom.getAddress());
				} else {
					JOptionPane.showMessageDialog(parent, "顾客详细信息获取失败！\n请稍后重试!", "错误", JOptionPane.ERROR_MESSAGE);
				}
				custom_box.addItem(custom.getName());
				model_box.addItem(sellForm.getModelId());
				count_tf.setText(String.valueOf(sellForm.getCount()));
				
				name_tf.setEditable(false);
				sex_tf.setEditable(false);
				tel_tf.setEditable(false);
				address_ta.setEditable(false);
				custom_box.setEditable(false);
				model_box.setEditable(false);
				count_tf.setEditable(false);		
//				model_box.setFocusable(false);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(parent, "数据库连接超时", "错误", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public void setDialogFrame(Dialog dialog) {
		changeDialog = dialog;
	}

}
