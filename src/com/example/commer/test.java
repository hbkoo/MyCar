package com.example.commer;

import java.awt.Color;

import org.jfree.chart.ChartColor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class test {
	public static void main(String[] args) {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("A", new Double(20));
		dataset.setValue("B", new Double(20));
		dataset.setValue("C", new Double(40));
		dataset.setValue("D", new Double(10));
		dataset.setValue("E", new Double(10));
		dataset.setValue("F", new Double(10));
		dataset.setValue("G", new Double(10));
		dataset.setValue("H", new Double(10));
		dataset.setValue("I", new Double(10));
		dataset.setValue("J", new Double(10));
		dataset.setValue("K", new Double(10));

		JFreeChart chart = ChartFactory.createPieChart("Mobile Sales", // chart
				dataset, // data
				true, // include legend
				true, false);
		setChart(chart);
		PiePlot pieplot = (PiePlot) chart.getPlot();
		pieplot.setSectionPaint("A", Color.decode("#749f83"));
		pieplot.setSectionPaint("B", Color.decode("#2f4554"));
		pieplot.setSectionPaint("C", Color.decode("#61a0a8"));
		pieplot.setSectionPaint("D", Color.decode("#d48265"));
		pieplot.setSectionPaint("E", Color.decode("#91c7ae"));
		pieplot.setSectionPaint("F", Color.decode("#c23531"));
		pieplot.setSectionPaint("G", Color.decode("#ca8622"));
		pieplot.setSectionPaint("H", Color.decode("#bda29a"));
		pieplot.setSectionPaint("I", Color.decode("#6e7074"));
		pieplot.setSectionPaint("J", Color.decode("#546570"));
		pieplot.setSectionPaint("K", Color.decode("#c4ccd3"));
	}
	
	
	public static void setChart(JFreeChart chart) {
		chart.setTextAntiAlias(true);
		
		PiePlot pieplot = (PiePlot) chart.getPlot();
		// 设置图表背景颜色
		pieplot.setBackgroundPaint(ChartColor.WHITE);
 
 
		pieplot.setLabelBackgroundPaint(null);// 标签背景颜色
 
 
		pieplot.setLabelOutlinePaint(null);// 标签边框颜色
 
 
		pieplot.setLabelShadowPaint(null);// 标签阴影颜色
 
 
		pieplot.setOutlinePaint(null); // 设置绘图面板外边的填充颜色
 
 
		pieplot.setShadowPaint(null); // 设置绘图面板阴影的填充颜色
		
		pieplot.setSectionOutlinesVisible(false);        
		pieplot.setNoDataMessage("没有可供使用的数据！");        
 
 
	}

	
	
}
