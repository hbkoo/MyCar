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
import com.example.frame.admin.AddStaffFrame;
import com.example.frame.admin.ChangeStaffRoleFrame;
import com.example.frame.admin.DelStaffFrame;
import com.example.frame.admin.InFormAnalyzeFrame;
import com.example.frame.admin.QueryCarsFrame;
import com.example.frame.admin.QueryCustomFrame;
import com.example.frame.admin.SellFormAnalyzeFrame;
import com.example.frame.admin.ServeFormAnalyzeFrame;
import com.example.frame.salesman.AddCustomFrame;
import com.example.frame.salesman.AddInFormFrame;
import com.example.frame.salesman.AddSellFormFrame;
import com.example.frame.salesman.ChangeInformFrame;
import com.example.frame.salesman.ChangeSellFormFrame;
import com.example.frame.salesman.DelInformFrame;
import com.example.frame.salesman.DelSellFormFrame;
import com.example.frame.salesman.QueryInformFrame;
import com.example.frame.salesman.QuerySellFormFrame;

public class AdminFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JFrame parent;
	private Staff staff;
	private JMenuItem Inform_mi, sellform_mi, serve_mi, cars_mi;
	private JMenuItem addStaff_mi, changeStaff_mi, delStaff_mi, queryCustom_mi;
	private JMenuItem myInfo;
	// JLabel bg_label;
	// JPanel panel;
	private JButton exit;

	private AddStaffFrame addStaffFrame = null;
	private ChangeStaffRoleFrame changeStaffRoleFrame = null;
	private DelStaffFrame delStaffFrame = null;
	private QueryCustomFrame queryCustomFrame = null;

	private InFormAnalyzeFrame inFormAnalyzeFrame = null;
	private SellFormAnalyzeFrame sellFormAnalyzeFrame = null;
	private ServeFormAnalyzeFrame serveFormAnalyzeFrame = null;
	private QueryCarsFrame queryCarsFrame = null;

	private MyInfoFrame myInfoFrame = null;

	/**
	 * Create the frame.
	 */
	public AdminFrame(Staff staff,JFrame parent) {
		
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
		setTitle("系统管理员---" + staff.getName());
		setVisible(true);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu inFormMenu = new JMenu("报表统计");
		inFormMenu.setFont(font_yahei);
		menuBar.add(inFormMenu);

		Inform_mi = new JMenuItem("进车订单统计");
		Inform_mi.setFont(font_kai);
		inFormMenu.add(Inform_mi);

		inFormMenu.addSeparator();
		sellform_mi = new JMenuItem("售车订单统计");
		sellform_mi.setFont(font_kai);
		inFormMenu.add(sellform_mi);

		inFormMenu.addSeparator();
		serve_mi = new JMenuItem("售后单统计");
		serve_mi.setFont(font_kai);
		inFormMenu.add(serve_mi);

		inFormMenu.addSeparator();
		cars_mi = new JMenuItem("轿车库存查看");
		cars_mi.setFont(font_kai);
		inFormMenu.add(cars_mi);

		JMenu sellFormMenu = new JMenu("用户管理");
		sellFormMenu.setFont(font_yahei);
		menuBar.add(sellFormMenu);

		addStaff_mi = new JMenuItem("添加新职工");
		addStaff_mi.setFont(font_kai);
		sellFormMenu.add(addStaff_mi);

		sellFormMenu.addSeparator();
		changeStaff_mi = new JMenuItem("修改职工部门");
		changeStaff_mi.setFont(font_kai);
		sellFormMenu.add(changeStaff_mi);

		sellFormMenu.addSeparator();
		delStaff_mi = new JMenuItem("删除职工");
		delStaff_mi.setFont(font_kai);
		sellFormMenu.add(delStaff_mi);

		sellFormMenu.addSeparator();
		queryCustom_mi = new JMenuItem("查看顾客信息");
		queryCustom_mi.setFont(font_kai);
		sellFormMenu.add(queryCustom_mi);

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

		Inform_mi.addActionListener(this);
		sellform_mi.addActionListener(this);
		serve_mi.addActionListener(this);
		cars_mi.addActionListener(this);
		addStaff_mi.addActionListener(this);
		changeStaff_mi.addActionListener(this);
		delStaff_mi.addActionListener(this);
		queryCustom_mi.addActionListener(this);
		myInfo.addActionListener(this);
		exit.addActionListener(this);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		System.out.println(e.getActionCommand());
		JPanel panel;
		
		
		switch (e.getActionCommand()) {
		
		case "进车订单统计":			
			contentPane.removeAll();
			if (inFormAnalyzeFrame == null) {
				inFormAnalyzeFrame = new InFormAnalyzeFrame(staff, parent);
			}
			panel = inFormAnalyzeFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();			
			break;
			
		case "售车订单统计":			
			contentPane.removeAll();
			if (sellFormAnalyzeFrame == null) {
				sellFormAnalyzeFrame = new SellFormAnalyzeFrame(staff, parent);
			}
			panel = sellFormAnalyzeFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();			
			break;
			
		case "售后单统计":			
			contentPane.removeAll();
			if (serveFormAnalyzeFrame == null) {
				serveFormAnalyzeFrame = new ServeFormAnalyzeFrame(staff, parent);
			}
			panel = serveFormAnalyzeFrame.getContentPanel();
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
			
		case "添加新职工":			
			contentPane.removeAll();
			if (addStaffFrame == null) {
				addStaffFrame = new AddStaffFrame(staff, parent);
			}
			panel = addStaffFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();			
			break;
		case "修改职工部门":			
			contentPane.removeAll();
			if (changeStaffRoleFrame == null) {
				changeStaffRoleFrame = new ChangeStaffRoleFrame(staff, parent);
			}
			panel = changeStaffRoleFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();			
			break;
		case "删除职工":			
			contentPane.removeAll();
			if (delStaffFrame == null) {
				delStaffFrame = new DelStaffFrame(staff, parent);
			}
			panel = delStaffFrame.getContentPanel();
			panel.setVisible(true);
			contentPane.add(panel);
			contentPane.updateUI();			
			break;
		case "查看顾客信息":			
			contentPane.removeAll();
			if (queryCustomFrame == null) {
				queryCustomFrame = new QueryCustomFrame(staff, parent);
			}
			panel = queryCustomFrame.getContentPanel();
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
			if (addStaffFrame != null) {
				addStaffFrame.dispose();
			}
			if (changeStaffRoleFrame != null) {
				changeStaffRoleFrame.dispose();
			}
			if (delStaffFrame != null) {
				delStaffFrame.dispose();
			}
			if (queryCustomFrame != null) {
				queryCustomFrame.dispose();
			}
			if (inFormAnalyzeFrame != null) {
				inFormAnalyzeFrame.dispose();
			}
			if (serveFormAnalyzeFrame != null) {
				serveFormAnalyzeFrame.dispose();
			}
			if (sellFormAnalyzeFrame != null) {
				sellFormAnalyzeFrame.dispose();
			}
			if (queryCarsFrame != null) {
				queryCarsFrame.dispose();
			}
			this.dispose();
			parent.show();
		}


}
