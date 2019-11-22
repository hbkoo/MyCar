package com.example.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
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
import com.example.frame.serve.AddServeFrame;
import com.example.frame.serve.ChangeServeFrame;
import com.example.frame.serve.QueryServeFrame;

public class ServerFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JMenuItem addServeform, queryServeform, changeServeform;
	private JMenuItem myInfo;
	// JLabel bg_label;
	// JPanel panel;
	private JButton exit;

	private AddServeFrame addServeFrame = null;
	private QueryServeFrame queryServeFrame = null;
	private ChangeServeFrame changeServeFrame = null;
	private MyInfoFrame myInfoFrame = null;

	/**
	 * Create the frame.
	 */
	public ServerFrame(Staff staff, JFrame parent) {

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
		setTitle("售后服务员---" + staff.getName());
		setVisible(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu inFormMenu = new JMenu("售后服务管理");
		inFormMenu.setFont(font_yahei);
		menuBar.add(inFormMenu);

		addServeform = new JMenuItem("新增售后单");
		addServeform.setFont(font_kai);
		inFormMenu.add(addServeform);

		inFormMenu.addSeparator();
		queryServeform = new JMenuItem("售后单查询");
		queryServeform.setFont(font_kai);
		inFormMenu.add(queryServeform);

		inFormMenu.addSeparator();
		changeServeform = new JMenuItem("售后单修改");
		changeServeform.setFont(font_kai);
		inFormMenu.add(changeServeform);

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

		addServeform.addActionListener(this);
		queryServeform.addActionListener(this);
		changeServeform.addActionListener(this);
		myInfo.addActionListener(this);
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		System.out.println(e.getActionCommand());
		JPanel panel;

		switch (e.getActionCommand()) {
		case "新增售后单":
			contentPane.removeAll();
			if (addServeFrame == null) {
				addServeFrame = new AddServeFrame(staff, parent);
			}
			panel = addServeFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "售后单查询":
			contentPane.removeAll();
			if (queryServeFrame == null) {
				queryServeFrame = new QueryServeFrame(staff, parent);
			}
			panel = queryServeFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "售后单修改":
			contentPane.removeAll();
			if (changeServeFrame == null) {
				changeServeFrame = new ChangeServeFrame(staff, parent);
			}
			panel = changeServeFrame.getContentPanel();
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
		if (addServeFrame != null) {
			addServeFrame.dispose();
		}
		if (queryServeFrame != null) {
			queryServeFrame.dispose();
		}
		if (changeServeFrame != null) {
			changeServeFrame.dispose();
		}
		if (myInfoFrame != null) {
			myInfoFrame.dispose();
		}
		this.dispose();
		parent.show();
	}

}
