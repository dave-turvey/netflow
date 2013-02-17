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
	JPanel m_panel;
	
	public ChartView(ChartModel m_chart_model) {
		JPanel m_panel = new JPanel();
		m_panel.setLayout(new java.awt.BorderLayout());
		
		TimeSeriesCollection flowdata = new TimeSeriesCollection();
		HashMap<String,TimeSeries> mappeddata = m_chart_model.getTimeSeriesDataItems();
		
		for (String ipaddr: mappeddata.keySet())
		{
			flowdata.addSeries(mappeddata.get(ipaddr));
		}
		// Create the chart
		chart = ChartFactory.createTimeSeriesChart("Netflow data", "Time", "Octets", flowdata,  true, true, false);

		// Test the chart in its own window
		// create the frame to hold the chart
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//TODO: Work out why this dosn't show in the main window!!
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		ChartFrame frame = new ChartFrame("Test chart", chart);
		frame.pack();
		frame.setVisible(true);
		// Add it to this panel for display
		ChartPanel CP = new ChartPanel(chart);
		m_panel.add(CP,BorderLayout.CENTER);        
		m_panel.validate();
		
	}
	
	public JPanel getPanel()
	{
		return m_panel;
	}
}
