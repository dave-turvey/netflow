/* Copyright (c) 2012, David Turvey
*  All rights reserved.

*  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
*  Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
*  Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation 
*  and/or other materials provided with the distribution.

*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
*  THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
*  BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
*  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER 
*  IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
*  THE POSSIBILITY OF SUCH DAMAGE.
*/

package cwa.netflow.charting;

import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;

import cwa.netflow.models.TotalsCSVReader;
/** NetflowTotalByProtocl
 * 
 * Creates and displays netflow octets by Protocol using the JFreechart tools 
 * Basically a utility wrapper class for the TotalsCSVReader class
 *
 * @author David Turvey
 * @version 1.0 6-10-12
*/

public class NetflowTotalByProtocol {
	private JFreeChart chart;
	private ChartFrame frame;
	
	public NetflowTotalByProtocol(String title, String filename){
		
		TotalsCSVReader reader = new TotalsCSVReader(filename,TotalsCSVReader.PROTOCOL,TotalsCSVReader.OCTETS);
		HashMap <String, Integer>mappeddata = reader.getTotalsItems();
		Integer val;
		
		/* Create a simple Bar chart
		 */
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (String protocol: mappeddata.keySet())
			{
				val = mappeddata.get(protocol);
				dataset.setValue(val.intValue(), "Netflow", protocol);
			}
		
		chart = ChartFactory.createBarChart(
				"Comparison by protocol", "Protocol", "Octets", dataset,
				PlotOrientation.VERTICAL, false, true, false);
		// create the chart to display
		frame = new ChartFrame(title, chart);
		frame.pack();
	}
	
	/** Description of setVisible(boolean show)
	 * 
	 * Displays or hides the chart 
	 * 
	 * @param show			True to display the chart, false to hide the chart
	 * 
	 */
	public void setVisible(boolean visible)
	{
		frame.setVisible(visible);
	}
	
	public JPanel getChartPanel()
	{
		return frame.getChartPanel();
	}
}
