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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import cwa.netflow.models.TimeSeriesCSVReader;

/** NetflowTimeSeriesChart - Creates and displays netflow octets by time using the JFreechart tools 
*
* @author David Turvey
* @version 1.0 6-10-12
*/

public class NetflowTimeSeriesChart {
	private JFreeChart chart;
	private ChartFrame frame;
	
	/** Description of NetflowTimeSeriesChart(String title, String filename)
	 *  
	 *  Creates a Time Series chart from the given CSV file data
	 *  
	 * @param title			Title to be displayed on the chart frame
	 * @param filename		full path to the file containing the CSV data produced by the Netflow server
	 *
	 */

	public NetflowTimeSeriesChart(String title, String filename) {
		// Time series example
		TimeSeriesCollection flowdata = new TimeSeriesCollection();
		TimeSeries ts = new TimeSeries(title);
		TimeSeriesCSVReader tsreader=null;
		
		tsreader=new TimeSeriesCSVReader(filename,true);
		HashMap<String,TimeSeries> mappeddata = tsreader.getTimeSeriesItems();
		
		for (String ipaddr: mappeddata.keySet())
		{
			flowdata.addSeries(mappeddata.get(ipaddr));
		}
		
		chart = ChartFactory.createTimeSeriesChart("Netflow data", "Time", "Octets", flowdata,  true, true, false);
	
		// create the frame to hold the chart
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

	public void setVisible(boolean show)
	{
		frame.setVisible(show);
	}

}
