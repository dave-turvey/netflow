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

package cwa;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItemSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.LegendTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;
import org.jfree.data.xy.XYDataset;

import cwa.netflow.charting.NetflowTimeSeriesChart;
import cwa.netflow.charting.NetflowTotalByIPChart;
import cwa.netflow.charting.NetflowTotalByProtocol;
//import cwa.netflow.charting.NetflowTotalByProtocol;
import cwa.netflow.gui.ApplicationController;
import cwa.netflow.models.IPRangeFilter;
import cwa.netflow.models.TimeSeriesCSVReader;
import cwa.netflow.protocol.ConvertByte;

/** ChartApplication - Displays data captured by flow servers using the JFreechart tools 
*
* @author David Turvey
* @version 1.0 6-10-12
*/

public class ChartApplication {

	public static void main(String[] args) {
		String fname="";
		for (int i = 0; i < args.length; i++) {
	        switch (args[i].charAt(0)) {
		        case '-':
		        	if(args[i].compareToIgnoreCase("-f")==0)
		        	{
		        		fname=args[i+1];
		        		i++;
		        	}
		        	break;
		        default:
		        	fname = args[i];
		        	break;
	        }
	     }

		if(fname == "")
		{
			LogManager lm = LogManager.getLogManager();
			Logger l = lm.getLogger(lm.getLoggerNames().nextElement());
			l.log(Level.ALL, "Usage -f [filename]");
			System.out.println("Usage -f [filename]");
			System.exit(-1);
		}

	
		/*NetflowTimeSeriesChart tsc=new NetflowTimeSeriesChart("Time Series data by IP",fname);
		tsc.setVisible(true);
		NetflowTotalByIPChart tbipc=new NetflowTotalByIPChart("Total by IP",fname);
		tbipc.setVisible(true);
		NetflowTotalByProtocol tipc=new NetflowTotalByProtocol("Total by protocol",fname);
		tipc.setVisible(true);*/

		// Create a controller to launch the application
		ApplicationController mc = new ApplicationController(args);

	}	

}
