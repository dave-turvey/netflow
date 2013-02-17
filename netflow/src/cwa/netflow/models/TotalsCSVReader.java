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

/** TotalsCSVReader 
 * 
 * Reads and maps Netflow data from a CSV file produced by the Netflow server into a set of totals 
 * Can summarise by either protocol or IP address and supports octet totalling  
*
* @author David Turvey
* @version 1.0 6-10-12
*/

package cwa.netflow.models;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.Vector;

import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeriesDataItem;
	
	
public class TotalsCSVReader {
	/**
	 * Supported sample types 
	 */
	public static int PROTOCOL = 4;
	public static int IP = 2;
	/**
	 * Supported data types
	 */
	public static int OCTETS = 3;
	
	private HashMap<String, Integer> map = null;
	
	/** Description of TotalsCSVReadr(String fname, int sampletype, int datatype)
	 * 
	 * Constructor to build a set of totals from a CSV file given the sampletype and datatype
	 * 
	 * @param fname		full path to the file containing the CSV data produced by the Netflow server
	 * @param sampletype 	Either protocol or IP as defined in the static member variables of the class
	 * @param datatype 		Only Octets as defined in the static member variables of the class
	 *
	 */
	public TotalsCSVReader(String fname,int sampletype, int datatype)
	{
		map = new HashMap<String,Integer>();
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
				parsedatatotoal(strLine,sampletype,datatype);
			}
			// Close the input stream
			in.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	/** Description of parsedatatotal(String data, int sampletype, int datatype)
	 * 
	 * 	Method to convert a line from the CSV file into a simple time series data item
	 * 
	 * @param data Single line of data from the CSV file 
	 * @param sampletype index into the ip field to use 
	 * @param datatype index into the field value to total
	 *
	 */
	
	private void parsedatatotoal(String data,int sampletype, int datatype)
	{	
		String[] result = data.split(",");
		String key = new String(result[sampletype]);
		Integer value = new Integer(result[datatype]);
		Integer total;
		
		// Find the location of the src ippaddress in the address array
		if(map.containsKey(key)){
			total = map.get(key);	
		}else{
			// Add the timeseries to the hashmap
			total = new Integer(0);
		}
		map.put(key, total+value);
	}
	
	/** Description of getTotalItems()
	 * 
	 * 	Method to convert a line from the CSV file into a simple time series data item
	 * 
	 * @return HashMap of IP to total octets data values 
	 *
	 */
	
	public HashMap<String,Integer> getTotalsItems()
	{
		return map;
	}
}
