package cwa.netflow.gui;

import java.awt.BorderLayout;
import java.util.HashMap;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cwa.netflow.models.ChartModel;
import cwa.netflow.models.IPRangeFilter;

public class TimeChartView extends ChartView{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844638974670826173L;
	private JFreeChart m_chart;
	private IPRangeFilter m_IPRange;
	private ChartModel m_chart_model;
	private HashMap<String,TimeSeries> m_mappeddata;
	private TimeSeriesCollection m_flowdata;
	
	public TimeChartView(ChartModel chart_model) {
		m_chart_model = chart_model;
		m_flowdata = new TimeSeriesCollection();
		m_mappeddata = m_chart_model.getTimeSeriesDataItems();
		if(m_IPRange != null)
			m_IPRange.applyFilter(m_mappeddata);
		
		for (String ipaddr: m_mappeddata.keySet())
		{
			m_flowdata.addSeries(m_mappeddata.get(ipaddr));
		}
		// Create the chart
		m_chart = ChartFactory.createTimeSeriesChart("Netflow data", "Time", "Octets", m_flowdata,  true, true, false);

		m_chart.setTitle("Netflow chart");
		m_chart.fireChartChanged();
		setSize(300, 300);
		setLayout(new java.awt.BorderLayout());
		ChartPanel CP = new ChartPanel(m_chart);
		add(CP,BorderLayout.CENTER);        
		validate();
		
		
	}

	public void setIPRangeFilter(IPRangeFilter rf) {
		/* we need to create a new set of flow data from the original mapped data, filter it and replace
		 * the existing flow data with this new set of data. We also need to make sure that we keep the original
		 * data so we can re-filter if any changes are made at a future point
		 */
		m_flowdata.removeAllSeries();
		HashMap<String,TimeSeries> current_data = (HashMap<String, TimeSeries>) m_mappeddata.clone();
		rf.applyFilter(current_data);
		
		for (String ipaddr: current_data.keySet())
		{
			m_flowdata.addSeries(current_data.get(ipaddr));
		}
		
		// Once the data set has changed we can fire the chart change
		m_chart.fireChartChanged();
	}
	
}
