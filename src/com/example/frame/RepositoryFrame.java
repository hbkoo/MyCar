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
import com.example.frame.admin.QueryCarsFrame;
import com.example.frame.repository.InFormCheckFrame;
import com.example.frame.repository.QueryHistoryFrame;
import com.example.frame.repository.SellFormCheckFrame;
import com.example.frame.salesman.AddCustomFrame;
import com.example.frame.salesman.AddInFormFrame;
import com.example.frame.salesman.AddSellFormFrame;
import com.example.frame.salesman.ChangeInformFrame;
import com.example.frame.salesman.ChangeSellFormFrame;
import com.example.frame.salesman.DelInformFrame;
import com.example.frame.salesman.DelSellFormFrame;
import com.example.frame.salesman.QueryInformFrame;
import com.example.frame.salesman.QuerySellFormFrame;

public class RepositoryFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JMenuItem inform_mi, sellForm_mi, history_mi, cars_mi;
	private JMenuItem myInfo;
	// JLabel bg_label;
	// JPanel panel;
	private JButton exit;

	private InFormCheckFrame inFormCheckFrame = null;
	private SellFormCheckFrame sellFormCheckFrame = null;
	private QueryCarsFrame queryCarsFrame = null;
	private QueryHistoryFrame queryHistoryFrame = null;
	private MyInfoFrame myInfoFrame = null;

	/**
	 * Create the frame.
	 */
	public RepositoryFrame(Staff staff, JFrame parent) {

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
		setTitle("仓库管理员---" + staff.getName());
		setVisible(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu inFormMenu = new JMenu("仓库管理");
		inFormMenu.setFont(font_yahei);
		menuBar.add(inFormMenu);

		inform_mi = new JMenuItem("进车订单审核");
		inform_mi.setFont(font_kai);
		inFormMenu.add(inform_mi);

		inFormMenu.addSeparator();
		sellForm_mi = new JMenuItem("售车订单审核");
		sellForm_mi.setFont(font_kai);
		inFormMenu.add(sellForm_mi);

		inFormMenu.addSeparator();
		history_mi = new JMenuItem("仓库进出记录");
		history_mi.setFont(font_kai);
		inFormMenu.add(history_mi);

		inFormMenu.addSeparator();
		cars_mi = new JMenuItem("轿车库存查看");
		cars_mi.setFont(font_kai);
		inFormMenu.add(cars_mi);

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

		inform_mi.addActionListener(this);
		sellForm_mi.addActionListener(this);
		history_mi.addActionListener(this);
		cars_mi.addActionListener(this);
		myInfo.addActionListener(this);
		exit.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		System.out.println(e.getActionCommand());
		JPanel panel;

		switch (e.getActionCommand()) {

		case "进车订单审核":
			contentPane.removeAll();
			if (inFormCheckFrame == null) {
				inFormCheckFrame = new InFormCheckFrame(staff, parent);
			}
			panel = inFormCheckFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "售车订单审核":
			contentPane.removeAll();
			if (sellFormCheckFrame == null) {
				sellFormCheckFrame = new SellFormCheckFrame(staff, parent);
			}
			panel = sellFormCheckFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "仓库进出记录":
			contentPane.removeAll();
			if (queryHistoryFrame == null) {
				queryHistoryFrame = new QueryHistoryFrame(staff, parent);
			}
			panel = queryHistoryFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();
			break;
		case "轿车库存查看":
			contentPane.removeAll();
			if (queryCarsFrame == null) {
				queryCarsFrame = new QueryCarsFrame(staff, parent);
			}
			panel = queryCarsFrame.getContentPanel();
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
		if (inFormCheckFrame != null) {
			inFormCheckFrame.dispose();
		}
		if (sellFormCheckFrame != null) {
			sellFormCheckFrame.dispose();
		}
		if (queryCarsFrame != null) {
			queryCarsFrame.dispose();
		}
		if (queryHistoryFrame != null) {
			queryHistoryFrame.dispose();
		}
		this.dispose();
		parent.show();
	}

}
