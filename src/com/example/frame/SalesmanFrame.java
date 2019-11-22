package com.example.frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.example.beans.Staff;
import com.example.frame.salesman.AddCustomFrame;
import com.example.frame.salesman.AddInFormFrame;
import com.example.frame.salesman.AddSellFormFrame;
import com.example.frame.salesman.ChangeInformFrame;
import com.example.frame.salesman.ChangeSellFormFrame;
import com.example.frame.salesman.DelInformFrame;
import com.example.frame.salesman.DelSellFormFrame;
import com.example.frame.salesman.QueryInformFrame;
import com.example.frame.salesman.QuerySellFormFrame;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

public class SalesmanFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JMenuItem addInform, queryInform, changeInform, deleteInform;
	private JMenuItem addCustom, addSellform, querySellform, changeSellform, deleteSellform;
	private JMenuItem myInfo;
	// JLabel bg_label;
	// JPanel panel;
	private JButton exit;

	private AddInFormFrame addInFormFrame = null;
	private QueryInformFrame queryInformFrame = null;
	private ChangeInformFrame changeInformFrame = null;
	private DelInformFrame delInformFrame = null;

	private AddCustomFrame addCustomFrame = null;
	private AddSellFormFrame addSellFormFrame = null;
	private QuerySellFormFrame querySellFormFrame = null;
	private ChangeSellFormFrame changeSellFormFrame = null;
	private DelSellFormFrame delSellFormFrame = null;

	private MyInfoFrame myInfoFrame = null;

	/**
	 * Create the frame.
	 */
	public SalesmanFrame(Staff staff, JFrame parent) {

		this.staff = staff;
		this.parent = parent;

		Font font_kai = new Font("楷体", Font.PLAIN, 17);
		Font font_yahei = new Font("微软雅黑", Font.PLAIN, 17);

		// setResizable(false);
		// setTitle("业务员---" + staff.getName());
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setBounds(100, 100, 450, 300);
		// getContentPane().setLayout(null);
		// setLocationRelativeTo(null);

		setFont(new Font("楷体", Font.PLAIN, 20));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 786, 563);
		setTitle("业务员---" + staff.getName());
		setVisible(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu inFormMenu = new JMenu("进车管理");
		inFormMenu.setFont(font_yahei);
		menuBar.add(inFormMenu);

		addInform = new JMenuItem("新增进车订单");
		addInform.setFont(font_kai);
		inFormMenu.add(addInform);

		inFormMenu.addSeparator();
		queryInform = new JMenuItem("查询进车订单");
		queryInform.setFont(font_kai);
		inFormMenu.add(queryInform);

		inFormMenu.addSeparator();
		changeInform = new JMenuItem("修改进车订单");
		changeInform.setFont(font_kai);
		inFormMenu.add(changeInform);

		inFormMenu.addSeparator();
		deleteInform = new JMenuItem("删除进车订单");
		deleteInform.setFont(font_kai);
		inFormMenu.add(deleteInform);

		JMenu sellFormMenu = new JMenu("售车管理");
		sellFormMenu.setFont(font_yahei);
		menuBar.add(sellFormMenu);

		addCustom = new JMenuItem("新增顾客");
		addCustom.setFont(font_kai);
		sellFormMenu.add(addCustom);

		sellFormMenu.addSeparator();
		addSellform = new JMenuItem("新增售车订单");
		addSellform.setFont(font_kai);
		sellFormMenu.add(addSellform);

		sellFormMenu.addSeparator();
		querySellform = new JMenuItem("查询售车订单");
		querySellform.setFont(font_kai);
		sellFormMenu.add(querySellform);

		sellFormMenu.addSeparator();
		changeSellform = new JMenuItem("修改售车订单");
		changeSellform.setFont(font_kai);
		sellFormMenu.add(changeSellform);

		sellFormMenu.addSeparator();
		deleteSellform = new JMenuItem("删除售车订单");
		deleteSellform.setFont(font_kai);
		sellFormMenu.add(deleteSellform);

		JMenu myInfoMenu = new JMenu("个人信息");
		myInfoMenu.setFont(font_yahei);
		menuBar.add(myInfoMenu);

		myInfo = new JMenuItem("个人信息");
		myInfo.setFont(font_kai);
		myInfoMenu.add(myInfo);

		Component horizontalGlue = Box.createHorizontalGlue();
		menuBar.add(horizontalGlue);

		exit = new JButton("退出登录");
		exit.setFont(new Font("仿宋", Font.PLAIN, 17));
		exit.setBorder(null);
		menuBar.add(exit);

		addInform.addActionListener(this);
		queryInform.addActionListener(this);
		changeInform.addActionListener(this);
		deleteInform.addActionListener(this);
		addCustom.addActionListener(this);
		addSellform.addActionListener(this);
		querySellform.addActionListener(this);
		changeSellform.addActionListener(this);
		deleteSellform.addActionListener(this);
		myInfo.addActionListener(this);
		exit.addActionListener(this);

		// Icon icon = new ImageIcon("D:\\test\\1.png");
		// bg_label = new JLabel(icon);
		// bg_label.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
		// contentPane.add(bg_label);
		//
		// InFormFrame inFormFrame = new InFormFrame();
		// panel = inFormFrame.getContentPanel();
		// // panel.setVisible(true);
		// panel.setBounds(0, 0, 786, 528);
		// panel.setOpaque(false);
		// bg_label.add(panel);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		System.out.println(e.getActionCommand());
		JPanel panel;
		switch (e.getActionCommand()) {
		case "新增进车订单":
			contentPane.removeAll();
			if (addInFormFrame == null) {
				addInFormFrame = new AddInFormFrame(staff, this);
			}
			panel = addInFormFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "查询进车订单":
			contentPane.removeAll();
			if (queryInformFrame == null) {
				queryInformFrame = new QueryInformFrame(staff, this);
			}
			panel = queryInformFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "修改进车订单":
			contentPane.removeAll();
			if (changeInformFrame == null) {
				changeInformFrame = new ChangeInformFrame(staff, this);
			}
			panel = changeInformFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "删除进车订单":
			contentPane.removeAll();
			if (delInformFrame == null) {
				delInformFrame = new DelInformFrame(staff, this);
			}
			panel = delInformFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "新增顾客":
			contentPane.removeAll();
			if (addCustomFrame == null) {
				addCustomFrame = new AddCustomFrame(staff, parent);
			}
			addCustomFrame.setTag(true);
			panel = addCustomFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "新增售车订单":
			contentPane.removeAll();
			if (addSellFormFrame == null) {
				addSellFormFrame = new AddSellFormFrame(staff, parent);
			}
			addSellFormFrame.setIsAddTag(true);
			panel = addSellFormFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "查询售车订单":
			contentPane.removeAll();
			if (querySellFormFrame == null) {
				querySellFormFrame = new QuerySellFormFrame(staff, parent);
			}
			panel = querySellFormFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "修改售车订单":
			contentPane.removeAll();
			if (changeSellFormFrame == null) {
				changeSellFormFrame = new ChangeSellFormFrame(staff, parent);
			}
			panel = changeSellFormFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "删除售车订单":
			contentPane.removeAll();
			if (delSellFormFrame == null) {
				delSellFormFrame = new DelSellFormFrame(staff, parent);
			}
			panel = delSellFormFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;

		case "个人信息":
			contentPane.removeAll();
			if (myInfoFrame == null) {
				myInfoFrame = new MyInfoFrame(staff, parent);
			}
			panel = myInfoFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "退出登录":
			exit();
			break;
		}

	}
	
	// 退出登录，释放资源
	private void exit() {
		if (addInFormFrame != null) {
			addInFormFrame.dispose();
		}
		if (queryInformFrame != null) {
			queryInformFrame.dispose();
		}
		if (changeInformFrame != null) {
			changeInformFrame.dispose();
		}
		if (delInformFrame != null) {
			delInformFrame.dispose();
		}
		if (addCustomFrame != null) {
			addCustomFrame.dispose();
		}
		if (querySellFormFrame != null) {
			querySellFormFrame.dispose();
		}
		if (addSellFormFrame != null) {
			addSellFormFrame.dispose();
		}
		if (changeSellFormFrame != null) {
			changeSellFormFrame.dispose();
		}
		if (delSellFormFrame != null) {
			delSellFormFrame.dispose();
		}
		this.dispose();
		parent.show();
	}

}
