package cwa.netflow.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import cwa.netflow.models.ChartModel;

public class ProtocolTotalsView extends ChartView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3277473856896241731L;
	HashMap <String, Integer> m_mappeddata;
	ChartModel m_chart_model;
	JFreeChart m_chart;
	
	public ProtocolTotalsView(ChartModel chart_model) {
		m_chart_model = chart_model;
		m_mappeddata = m_chart_model.getProtocolSeriesDataItems();

		Integer val;		
		/* Create a simple Bar chart		 */		
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		for (String protocol: m_mappeddata.keySet())			
		{				
			val = m_mappeddata.get(protocol);				
			dataset.setValue(val.intValue(), "Netflow", protocol);			
		}		
		m_chart = ChartFactory.createBarChart("Total by IP", "IP Address", "Octets", dataset,PlotOrientation.VERTICAL, false, true, false);		
		
		m_chart.setTitle("Netflow chart");
		m_chart.fireChartChanged();
		setSize(300, 300);
		setLayout(new java.awt.BorderLayout());
		ChartPanel CP = new ChartPanel(m_chart);
		add(CP,BorderLayout.CENTER);        
		validate();
	}
}
