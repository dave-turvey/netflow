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

package cwa.netflow.models;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesDataItem;
/** TimeSeriesCSVReader - Reads and maps Netflow data from a CSV file produced by the Netflow server  
*
* @author David Turvey
* @version 1.0 6-10-12
*/
public class TimeSeriesCSVReader {
	private HashMap<String, TimeSeries> records=null;
	private HashMap<String, Integer> totalOctets=null;
	private Class cl=null;
	
	/** Description of TimeSeriesCSVReader(String fname, Boolean totals)
	 * 
	 * Reads the CSV file and parses it into a format suitable for use by the Time series chart class
	 * 
	 * @param fname		full path to the file containing the CSV data produced by the Netflow server
	 * @param totals 	True to summarize data into totals since start of time period
	 *
	 */
	public TimeSeriesCSVReader(String fname, Boolean totals) {
		records = new HashMap<String,TimeSeries>();
		totalOctets = new HashMap<String,Integer>();
		
		// Initialise the class as it is used in every line read from the file
		// in parsedatatotimeseries
		
		try {
			cl = Class.forName("org.jfree.data.time.Second");
		} catch (ClassNotFoundException e1) {
			System.err.println("Error org.jfree.data.time.Second: " + e1.getMessage());
		}
		
		// Read each line from the file and add it to the time series data
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(fname);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				parsedatatotimeseries(strLine, totals);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error failed to parse data to time series: " + e.getMessage());
		}
		
		
	}
	

	/** Description of parsedatatotimeseries(String data,Boolean totals)
	 * 	
	 * Converts a line from the CSV file into a simple time series data item
	 * 
	 * @param data SIngle line of data from the CSV file 
	 * @param totals true to create a running total in the time series
	 *
	 */
	private void parsedatatotimeseries(String data, Boolean totals)
	{
		// Timeseries contains time,value pairs, we can construct a series of values by 
		// IP address, time series<list>
		
		String[] result = data.split(",");
		
		String ipaddr;
		Integer total;
		ipaddr = result[1];
		
		TimeSeries series;
		if(records.containsKey(ipaddr)){
			series = records.get(ipaddr);
			total = totalOctets.get(ipaddr);
		}else{
			series = new TimeSeries(ipaddr,cl);
			total = new Integer(0);
			// Add the timeseries to the hashmaps
			records.put(ipaddr, series);
			totalOctets.put(ipaddr, total);
		}
		// Time in the series is in seconds rather than milliseconds so needs to be converted
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
		Date d;
		try {
			d = formatter.parse(result[6]);
		} catch (ParseException e1) {
			System.out.println("Unable to parse date from file ");
			e1.printStackTrace();
			return;
		}
		// Add the data item to the time series - we may have a problem because the resolution
		// is in seconds which causes duplicate data items. To overcome this handle the exception and merge
		// the new data with the existing data
		TimeSeriesDataItem tsdi=null;
		
		if(totals)
		{
			total=new Integer(total.intValue()+Integer.parseInt(result[3]));
			totalOctets.put(ipaddr, total);
		}else{
			total = new Integer(Integer.parseInt(result[3]));
		}
		tsdi = new TimeSeriesDataItem(new Second(d),total.intValue());
		try{
			series.add(tsdi);
		}catch(Exception e)
		{
			series.addAndOrUpdate(new TimeSeries(tsdi));
		}
		
	}
	
	/** Description of getTimeSeriesItems()
	 * 	
	 * Converts a line from the CSV file into a simple time series data item
	 * 
	 * @return HashMap of IP to timeseries data values 
	 *
	 */
	
	public HashMap<String, TimeSeries> getTimeSeriesItems()
	{
		return records;
	}
}
