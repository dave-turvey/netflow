package cwa.netflow.gui;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import cwa.netflow.models.ChartModel;

public class ChartView extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8844638974670826173L;
	private JFreeChart chart;
	
	public ChartView(ChartModel m_chart_model) {
		
		TimeSeriesCollection flowdata = new TimeSeriesCollection();
		HashMap<String,TimeSeries> mappeddata = m_chart_model.getTimeSeriesDataItems();
		
		for (String ipaddr: mappeddata.keySet())
		{
			flowdata.addSeries(mappeddata.get(ipaddr));
		}
		// Create the chart
		chart = ChartFactory.createTimeSeriesChart("Netflow data", "Time", "Octets", flowdata,  true, true, false);

		chart.setTitle("Netflow chart");
		chart.fireChartChanged();
		setSize(300, 300);
		setLayout(new java.awt.BorderLayout());
		ChartPanel CP = new ChartPanel(chart);
		add(CP,BorderLayout.CENTER);        
		validate();
	}
}
